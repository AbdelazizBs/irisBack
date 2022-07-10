package com.iris.irisback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Document(collection = "machine")
public class Machine {
  @Id private String id;
  private String nomMachine;
  private String reference;
  private EtapeProduction etapeProduction;
  private String nombreConducteur;
  private Date dateMaintenance;
  private Date dateCreation;
  private String etat;

  public Machine() {}
  // private List<Personnel> personnel;

}
