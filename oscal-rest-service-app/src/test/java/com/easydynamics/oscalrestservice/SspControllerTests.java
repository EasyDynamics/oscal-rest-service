package com.easydynamics.oscalrestservice;

import com.easydynamics.oscal.data.example.ExampleContentConstants;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends BaseOscalControllerTests {

  private SspControllerTests() {
    this.oscalType = "ssps";
    this.defaultId = ExampleContentConstants.SSP_EXAMPLE_ID;
  }

}