package com.easydynamics.oscalrestservice;

import org.junit.jupiter.api.Test;

/**
 * OscalCatalogControllerTests runs tests of the OscalCatalogController class.
 */
public class CatalogControllerTests extends OscalControllerTests {

  public static final String CATALOG_ID_80053r5 = "62f21617-b40f-4e89-bf3b-01b04b68f473";

  /**
   * Runs the OscalControllerTests superclass tests.
   */
  @Test
  public void superTestsCatalogController() throws Exception {
    super.shouldReturnDefaultMessage("catalogs", CATALOG_ID_80053r5);
    super.isNotFound("catalogs");
  }

}