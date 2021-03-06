package com.iris.irisback.service;

import com.iris.irisback.dto.CommandeDTO;
import com.iris.irisback.exception.NotFoundException;
import com.iris.irisback.mapper.CommandeMapper;
import com.iris.irisback.model.Article;
import com.iris.irisback.model.Client;
import com.iris.irisback.model.Commande;
import com.iris.irisback.repository.ArticleRepository;
import com.iris.irisback.repository.ClientRepository;
import com.iris.irisback.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CommandeService {
  private final CommandeRepository commandeRepository;
  private final ClientRepository clientRepository;
  private final ArticleRepository articleRepository;

  public CommandeService(
      final CommandeRepository commandeRepository,
      final ClientRepository clientRepository,
      final ArticleRepository articleRepository) {
    this.commandeRepository = commandeRepository;
    this.clientRepository = clientRepository;
    this.articleRepository = articleRepository;
  }

  public CommandeDTO addCommande(final CommandeDTO commandeDTO) throws IOException {
    final Commande commande1 = CommandeMapper.MAPPER.toCommande(commandeDTO);
    final List<Article> articles = new ArrayList<>();
    commandeDTO
        .getCodeArticles()
        .forEach(
            codeArticle -> articles.add(articleRepository.findArticleByCodeArticle(codeArticle)));
    commande1.setArticles(articles);
    final Client client = clientRepository.findClientByNom(commandeDTO.getNomClient());
    commande1.setClient(client);
    return CommandeMapper.MAPPER.toCommandeDTO(commandeRepository.save(commande1));
  }

  public List<CommandeDTO> myCommandes(final String clientId) throws IOException {
    final List<Commande> commandeByClientId = commandeRepository.findCommandeByClientId(clientId);
    return commandeByClientId.stream()
        .map(commande -> CommandeMapper.MAPPER.toCommandeDTO(commande))
        .collect(Collectors.toList());
  }

  public CommandeDTO getCmdById(final String idCmd) throws IOException {
    final Commande commande = commandeRepository.findCommandeById(idCmd);
    return CommandeMapper.MAPPER.toCommandeDTO(commande);
  }

  public List<CommandeDTO> Commandes() throws IOException {
    final List<Commande> commandes = commandeRepository.findAll();
    return commandes.stream()
        .map(commande -> CommandeMapper.MAPPER.toCommandeDTO(commande))
        .collect(Collectors.toList());
  }

  public void deleteCommande(final String id) throws IOException {
    commandeRepository.deleteById(id);
  }

  public CommandeDTO updateCommande(final CommandeDTO commandeDTO, final String commandeId) {
    return commandeRepository
        .findById(commandeId)
        .map(
            commande -> {
              commande.setTypeCmd(commandeDTO.getTypeCmd());
              commande.setNumCmd(commandeDTO.getNumCmd());
              commande.setDateCmd(commandeDTO.getDateCmd());
              final List<Article> list =
                  commandeDTO.getCodeArticles().stream()
                      .map((codeArticle) -> articleRepository.findArticleByCodeArticle(codeArticle))
                      .collect(toList());
              commande.setArticles(list);
              return CommandeMapper.MAPPER.toCommandeDTO(commandeRepository.save(commande));
            })
        .orElseThrow(() -> new NotFoundException("Commande Id  " + commandeId + " not found"));
  }
}
