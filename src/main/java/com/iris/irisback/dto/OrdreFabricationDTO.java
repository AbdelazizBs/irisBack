package com.iris.irisback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class OrdreFabricationDTO {
  private String id;
  private Date dateLancement;
  private Date debutHeure;
  private Date finHeure;
  private String commentaire;
  private String qtePremierChoix;
  private String qteNonConforme;
  private String codeArticles;

  public OrdreFabricationDTO() {}
}
