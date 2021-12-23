package com.easydynamics.oscalrestservice.api;

import org.junit.jupiter.api.Disabled;

import com.easydynamics.oscal.data.example.ExampleContentConstants;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
@Disabled // See https://github.com/usnistgov/liboscal-java/issues/7
public class ProfilesControllerTests extends BaseOscalControllerTests {

  private ProfilesControllerTests() {
    this.oscalType = "profiles";
    this.defaultId = ExampleContentConstants.EXAMPLE_PROFILE_ID;
  }

}