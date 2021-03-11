package com.easydynamics.oscalrestservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "parties")
@Data
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

}
