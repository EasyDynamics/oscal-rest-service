package com.easydynamics.oscalrestservice;

import com.easydynamics.oscal.data.example.ExampleContentConstants;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
public class ProfilesControllerTests extends BaseOscalControllerTests {

  private ProfilesControllerTests() {
    this.oscalType = "profiles";
    this.defaultId = ExampleContentConstants.EXAMPLE_PROFILE_ID;
  }

}