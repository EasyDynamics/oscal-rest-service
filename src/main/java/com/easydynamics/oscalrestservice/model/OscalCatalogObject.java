package com.easydynamics.oscalrestservice.model;

/**
 * OscalCatalogObject models an OSCAL catalog.
 */
public class OscalCatalogObject extends OscalObject {
  
  /**
   * Constructs an OscalObject representing an OSCAL catalog.
   * 
   * @param uuid Catalog identifier
   * @param content the OSCAL Catalog content
   */
  public OscalCatalogObject(String uuid, String content) {
    super(uuid, content);
  }
}
