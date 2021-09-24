package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.OscalSspRepositoryTests.SSP_EXAMPLE_ID;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends OscalControllerTests {

  private SspControllerTests() {
    this.oscalType = "ssps";
    this.defaultId = SSP_EXAMPLE_ID;
  }

}