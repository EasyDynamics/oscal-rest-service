package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.OscalCatalogRepositoryTests.CATALOG_ID_80053r5;

/**
 * OscalCatalogControllerTests runs tests of the OscalCatalogController class.
 */
public class CatalogControllerTests extends OscalControllerTests {

  private CatalogControllerTests() {
    this.oscalType = "catalogs";
    this.defaultId = CATALOG_ID_80053r5;
  }

}