package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends BaseOscalControllerTests {

  private SspControllerTests() {
    this.oscalObjectType = OscalObjectType.SSP;
    this.exampleContent = ExampleContent.SSP_EXAMPLE;
  }

}