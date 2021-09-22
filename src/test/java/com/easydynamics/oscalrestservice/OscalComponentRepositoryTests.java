package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.ComponentControllerTests.EXAMPLE_COMPONENT_ID;

import com.easydynamics.oscalrestservice.repository.OscalComponentRepository;

/**
 * OscalComponentRepositoryTests runs tests of the OscalComponentRepository class.
 */
public class OscalComponentRepositoryTests extends OscalRepositoryTests {

  private OscalComponentRepositoryTests() {
    this.repository = new OscalComponentRepository("oscal-content/components");
    this.defaultId = EXAMPLE_COMPONENT_ID;
  }

}
