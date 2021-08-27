package com.easydynamics.oscalrestservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * OscalParty is the model used to map requests to the /parties endpoint.
 * This is a temporary class, used until we find a better
 * way to directly generate from the OSCAL Schema
 */

@Entity
@Table(name = "parties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OscalParty {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(length = 36)
  private String uuid;

  @NotBlank(message = "Type cannot be null")
  private String type;

  @NotBlank(message = "Name cannot be null")
  private String name;

  /**
   * Default constructor for Oscal Party, used in the PartyControllerTests.
   */

  public OscalParty(String type, String name) {

    this.type = type;
    this.name = name;
  }

  public String getUuid() {
    return this.uuid;
  }

}
