package com.easydynamics.oscalrestservice;

import com.easydynamics.oscalrestservice.repository.OscalCatalogRepository;

/**
 * OscalCatalogRepositoryTests runs tests of the OscalCatalogRepository class.
 */
public class OscalCatalogRepositoryTests extends OscalRepositoryTests {

  public static final String CATALOG_ID_80053r5 = "62f21617-b40f-4e89-bf3b-01b04b68f473";

  private OscalCatalogRepositoryTests() {
    this.repository = new OscalCatalogRepository("oscal-content/catalogs");
    this.defaultId = CATALOG_ID_80053r5;
  }
}
