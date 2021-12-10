package com.easydynamics.oscalrestservice;

import com.easydynamics.oscal.data.example.ExampleContentConstants;

/**
 * OscalComponentControllerTests runs tests of the OscalComponentController class.
 */
public class ComponentControllerTests extends BaseOscalControllerTests {

  private ComponentControllerTests() {
    this.oscalType = "component-definitions";
    this.defaultId = ExampleContentConstants.EXAMPLE_COMPONENT_ID;
  }

}