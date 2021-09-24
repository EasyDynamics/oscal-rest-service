package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.OscalProfileRepositoryTests.EXAMPLE_PROFILE_ID;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
public class ProfilesControllerTests extends OscalControllerTests {

  private ProfilesControllerTests() {
    this.oscalType = "profiles";
    this.defaultId = EXAMPLE_PROFILE_ID;
  }

}