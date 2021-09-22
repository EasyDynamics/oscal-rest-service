package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.CatalogControllerTests.CATALOG_ID_80053r5;

import com.easydynamics.oscalrestservice.repository.OscalCatalogRepository;

/**
 * OscalCatalogRepositoryTests runs tests of the OscalCatalogRepository class.
 */
public class OscalCatalogRepositoryTests extends OscalRepositoryTests {

  private OscalCatalogRepositoryTests() {
    this.repository = new OscalCatalogRepository("oscal-content/catalogs");
    this.defaultId = CATALOG_ID_80053r5;
  }
}
