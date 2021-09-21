package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.CatalogControllerTests.CATALOG_ID_80053r5;

import com.easydynamics.oscalrestservice.repository.OscalCatalogRepository;
import org.junit.jupiter.api.Test;

/**
 * OscalCatalogRepositoryTests runs tests of the OscalCatalogRepository class.
 */
public class OscalCatalogRepositoryTests extends OscalRepositoryTests {
  
  private static final OscalCatalogRepository repository = new OscalCatalogRepository("oscal-content/catalogs");

  /**
   * Runs the OscalRepositoryTests superclass tests.
   */
  @Test
  public void superTestsCatalogRepository() {
    super.nullFindById(repository);
    super.badIdOptionalEmpty(repository);
    super.getGoodId(repository, CATALOG_ID_80053r5, "\"613fca2d-704a-42e7-8e2b-b206fb92b456\"");
  }
}
