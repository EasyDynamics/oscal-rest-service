package com.easydynamics.oscal.data.model;

/**
 * OscalProfileObject models an OSCAL profile.
 */
public class OscalProfileObject extends OscalObject {
  
  /**
   * Constructs an OscalObject representing an OSCAL profile.
   * 
   * @param uuid Profile identifier
   * @param content the OSCAL Profile content
   */
  public OscalProfileObject(String uuid, String content) {
    super(uuid, content);
  }
}
