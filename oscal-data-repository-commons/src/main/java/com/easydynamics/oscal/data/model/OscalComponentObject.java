package com.easydynamics.oscal.data.model;

/**
 * OscalComponentObject models an OSCAL component.
 */
public class OscalComponentObject extends OscalObject {
  
  /**
   * Constructs an OscalObject representing an OSCAL component.
   * 
   * @param uuid Component identifier
   * @param content the OSCAL Component content
   */
  public OscalComponentObject(String uuid, String content) {
    super(uuid, content);
  }
}
