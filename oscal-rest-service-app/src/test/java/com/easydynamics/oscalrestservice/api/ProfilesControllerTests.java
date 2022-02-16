package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
public class ProfilesControllerTests extends BaseOscalControllerTests {

  private ProfilesControllerTests() {
    this.oscalObjectType = OscalObjectType.PROFILE;
    this.exampleContent = ExampleContent.PROFILE_80053r4MOD;
  }

}