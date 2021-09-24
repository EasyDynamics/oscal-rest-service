package com.easydynamics.oscalrestservice;

import com.easydynamics.oscalrestservice.repository.OscalComponentRepository;

/**
 * OscalComponentRepositoryTests runs tests of the OscalComponentRepository class.
 */
public class OscalComponentRepositoryTests extends OscalRepositoryTests {

  public static final String EXAMPLE_COMPONENT_ID = "aabcfa61-c6eb-4979-851f-35b461f6a0ef";

  private OscalComponentRepositoryTests() {
    this.repository = new OscalComponentRepository("oscal-content/components");
    this.defaultId = EXAMPLE_COMPONENT_ID;
  }

}
