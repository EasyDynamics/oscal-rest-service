package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import com.easydynamics.oscal.data.model.OscalComponentObject;

/**
 * OscalComponentRepositoryTests runs tests of the OscalComponentRepository class.
 */
public class OscalComponentRepositoryTests extends BaseOscalRepositoryTests<OscalComponentObject> {

  private OscalComponentRepositoryTests() {
    this.repository = new OscalComponentRepoFilePassImpl("oscal-content/component-definitions");
    this.defaultId = ExampleContentConstants.EXAMPLE_COMPONENT_ID;
  }

}
