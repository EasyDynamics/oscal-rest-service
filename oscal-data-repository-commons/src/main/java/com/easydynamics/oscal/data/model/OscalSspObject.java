package com.easydynamics.oscal.data.model;

/**
 * OscalSspObject models an OSCAL system security plan.
 */
public class OscalSspObject extends OscalObject {

  /**
   * Constructs an OSCALObject representing an OSCAL SSP, a
   * system security plan.
   * 
   * @param uuid SSP identifier
   * @param content the OSCAL SSP content
   */
  public OscalSspObject(String uuid, String content) {
    super(uuid, content);
  }
}
