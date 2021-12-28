package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.example.ExampleContentConstants;

/**
 * OscalCatalogControllerTests runs tests of the OscalCatalogController class.
 */
public class CatalogControllerTests extends BaseOscalControllerTests {

  private CatalogControllerTests() {
    this.oscalObjectType = OscalObjectType.CATALOG;
    this.defaultId = ExampleContentConstants.CATALOG_ID_80053r5;
  }

}