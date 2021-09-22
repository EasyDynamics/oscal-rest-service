package com.easydynamics.oscalrestservice;

/**
 * OscalComponentControllerTests runs tests of the OscalComponentController class.
 */
public class ComponentControllerTests extends OscalControllerTests {

  public static final String EXAMPLE_COMPONENT_ID = "aabcfa61-c6eb-4979-851f-35b461f6a0ef";

  private ComponentControllerTests() {
    this.oscalType = "components";
    this.defaultId = EXAMPLE_COMPONENT_ID;
  }

}