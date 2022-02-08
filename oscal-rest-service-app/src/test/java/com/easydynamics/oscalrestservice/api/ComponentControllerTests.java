package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalComponentControllerTests runs tests of the OscalComponentController class.
 */
public class ComponentControllerTests extends BaseOscalControllerTests {

  private ComponentControllerTests() {
    this.oscalObjectType = OscalObjectType.COMPONENT_DEFINITION;
    this.exampleContent = ExampleContent.COMPONENT_EXAMPLE;
  }

}