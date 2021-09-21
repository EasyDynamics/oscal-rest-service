package com.easydynamics.oscalrestservice;

import org.junit.jupiter.api.Test;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
public class ProfilesControllerTest extends OscalControllerTests {

  public static final String EXAMPLE_PROFILE_ID = "0f2814d7-a9a1-4b1f-aec8-eb7b10c1ef06";

  /**
   * Runs the OscalControllerTests superclass tests.
   */
  @Test
  public void superTestsProfilesController() throws Exception {
    super.shouldReturnDefaultMessage("profiles", EXAMPLE_PROFILE_ID);
    super.isNotFound("profiles");
  }

}