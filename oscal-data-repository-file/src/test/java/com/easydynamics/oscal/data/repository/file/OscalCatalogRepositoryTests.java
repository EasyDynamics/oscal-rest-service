package com.easydynamics.oscal.data.repository.file;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import gov.nist.secauto.oscal.lib.model.Catalog;

/**
 * OscalCatalogRepositoryTests runs tests of the OscalCatalogRepository class.
 */
public class OscalCatalogRepositoryTests extends BaseOscalRepositoryTests<Catalog> {

  private OscalCatalogRepositoryTests() {
    this.defaultId = ExampleContentConstants.CATALOG_ID_80053r5;
  }
}
