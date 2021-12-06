package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import com.easydynamics.oscal.data.model.OscalCatalogObject;

/**
 * OscalCatalogRepositoryTests runs tests of the OscalCatalogRepository class.
 */
public class OscalCatalogRepositoryTests extends BaseOscalRepositoryTests<OscalCatalogObject> {

  private OscalCatalogRepositoryTests() {
    this.repository = new OscalCatalogRepoFilePassImpl("oscal-content/catalogs");
    this.defaultId = ExampleContentConstants.CATALOG_ID_80053r5;
  }
}
