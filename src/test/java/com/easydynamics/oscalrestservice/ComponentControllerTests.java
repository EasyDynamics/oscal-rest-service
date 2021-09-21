package com.easydynamics.oscalrestservice;

import org.junit.jupiter.api.Test;

/**
 * OscalComponentControllerTests runs tests of the OscalComponentController class.
 */
public class ComponentControllerTests extends OscalControllerTests {

  public static final String EXAMPLE_COMPONENT_ID = "aabcfa61-c6eb-4979-851f-35b461f6a0ef";

  /**
   * Runs the OscalControllerTests superclass tests.
   */
  @Test
  public void superTestsComponentController() throws Exception {
    super.shouldReturnDefaultMessage("components", EXAMPLE_COMPONENT_ID);
    super.isNotFound("components");
  }

}