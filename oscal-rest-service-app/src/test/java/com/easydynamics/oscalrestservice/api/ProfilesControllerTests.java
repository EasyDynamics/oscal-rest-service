package com.easydynamics.oscalrestservice.api;

import org.junit.jupiter.api.Disabled;

import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
@Disabled // See https://github.com/usnistgov/liboscal-java/issues/7
public class ProfilesControllerTests extends BaseOscalControllerTests {

  private ProfilesControllerTests() {
    this.oscalObjectType = OscalObjectType.PROFILE;
    this.exampleContent = ExampleContent.PROFILE_80053r4MOD;
  }

}