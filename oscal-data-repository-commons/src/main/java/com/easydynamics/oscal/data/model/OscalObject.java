package com.easydynamics.oscal.data.model;

import javax.validation.constraints.NotBlank;

/**
 * OscalObject is the superclass for all OSCAL types. This class contains behavior common to all
 * OSCAL objects - SSPs, Profiles, Components, and Catalogs. It allows for the use of generics at a
 * location when any of the OSCAL object types could be used.
 */
public class OscalObject {

  @NotBlank(message = "Type cannot be null")
  private String uuid;
  
  private String content;

  /**
   * Constructs an OscalObject.
   * 
   * @param uuid identifier of the OSCAL object
   * @param content OSCAL data
   */
  protected OscalObject(String uuid, String content) {
    this.uuid = uuid;
    this.content = content;
  }

  /**
   * Retrieves the OSCAL Object uuid.
   * 
   * @return uuid of OSCAL Object.
   */
  public String getUuid() {
    return this.uuid;
  }

  /**
   * Retrieves the content of the Oscal Object. The content
   * can vary differently in structure among the Oscal Objects
   * but will always be returned as a string.
   * 
   * @return the object's OSCAL data.
   */
  public String getContent() {
    return this.content;
  }

}
