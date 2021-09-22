package com.easydynamics.oscalrestservice;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
public class ProfilesControllerTests extends OscalControllerTests {

  public static final String EXAMPLE_PROFILE_ID = "0f2814d7-a9a1-4b1f-aec8-eb7b10c1ef06";

  private ProfilesControllerTests() {
    this.oscalType = "profiles";
    this.defaultId = EXAMPLE_PROFILE_ID;
  }

}