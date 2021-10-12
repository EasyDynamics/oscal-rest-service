package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.OscalComponentRepositoryTests.EXAMPLE_COMPONENT_ID;

/**
 * OscalComponentControllerTests runs tests of the OscalComponentController class.
 */
public class ComponentControllerTests extends OscalControllerTests {

  private ComponentControllerTests() {
    this.oscalType = "component-definitions";
    this.defaultId = EXAMPLE_COMPONENT_ID;
  }

}