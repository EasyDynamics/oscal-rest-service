package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.example.ExampleContentConstants;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends BaseOscalControllerTests {

  private SspControllerTests() {
    this.oscalObjectType = OscalObjectType.SSP;
    this.defaultId = ExampleContentConstants.SSP_EXAMPLE_ID;
  }

}