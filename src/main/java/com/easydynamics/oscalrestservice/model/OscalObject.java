package com.easydynamics.oscalrestservice.model;

import javax.validation.constraints.NotBlank;

public class OscalObject {

  @NotBlank(message = "Type cannot be null")
  private String uuid;
  
  private String content;

  protected OscalObject(String uuid, String content) {
    this.uuid = uuid;
    this.content = content;
  }

  public String getUuid() {
    return this.uuid;
  }

  public String getContent() {
    return this.content;
  }

}
