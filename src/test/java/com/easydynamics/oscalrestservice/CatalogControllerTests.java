package com.easydynamics.oscalrestservice;

/**
 * OscalCatalogControllerTests runs tests of the OscalCatalogController class.
 */
public class CatalogControllerTests extends OscalControllerTests {

  public static final String CATALOG_ID_80053r5 = "62f21617-b40f-4e89-bf3b-01b04b68f473";

  private CatalogControllerTests() {
    this.oscalType = "catalogs";
    this.defaultId = CATALOG_ID_80053r5;
  }

}