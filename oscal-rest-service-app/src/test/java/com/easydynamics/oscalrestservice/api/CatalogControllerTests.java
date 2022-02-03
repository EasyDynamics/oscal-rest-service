package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalCatalogControllerTests runs tests of the OscalCatalogController class.
 */
public class CatalogControllerTests extends BaseOscalControllerTests {

  private CatalogControllerTests() {
    this.oscalObjectType = OscalObjectType.CATALOG;
    this.exampleContent = ExampleContent.CATALOG_80053r5;
  }

}