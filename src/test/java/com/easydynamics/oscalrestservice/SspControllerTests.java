package com.easydynamics.oscalrestservice;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends OscalControllerTests{

  public static final String SSP_EXAMPLE_ID = "66c2a1c8-5830-48bd-8fdd-55a1c3a52888";

  private SspControllerTests() {
    this.oscalType = "ssps";
    this.defaultId = SSP_EXAMPLE_ID;
  }

}