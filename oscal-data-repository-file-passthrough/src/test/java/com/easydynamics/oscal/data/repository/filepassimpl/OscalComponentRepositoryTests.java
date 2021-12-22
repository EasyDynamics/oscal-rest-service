package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import gov.nist.secauto.oscal.lib.model.ComponentDefinition;

/**
 * OscalComponentRepositoryTests runs tests of the OscalComponentRepository class.
 */
public class OscalComponentRepositoryTests extends BaseOscalRepositoryTests<ComponentDefinition> {

  private OscalComponentRepositoryTests() {
    this.repository = new OscalComponentRepoFilePassImpl("oscal-content/component-definitions");
    this.defaultId = ExampleContentConstants.EXAMPLE_COMPONENT_ID;
  }

}
