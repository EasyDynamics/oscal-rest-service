package com.easydynamics.oscalrestservice;

import org.junit.jupiter.api.Test;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends OscalControllerTests{

  public static final String SSP_EXAMPLE_ID = "66c2a1c8-5830-48bd-8fdd-55a1c3a52888";

  /**
   * Runs the OscalControllerTests superclass tests.
   */
  @Test
  public void superTestsSspController() throws Exception {
    super.shouldReturnDefaultMessage("ssps", SSP_EXAMPLE_ID);
    super.isNotFound("ssps");
  }

}