package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.ProfilesControllerTests.EXAMPLE_PROFILE_ID;

import com.easydynamics.oscalrestservice.repository.OscalProfileRepository;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
public class OscalProfileRepositoryTests extends OscalRepositoryTests {

  private OscalProfileRepositoryTests() {
    this.repository = new OscalProfileRepository("oscal-content/profiles");
    this.defaultId = EXAMPLE_PROFILE_ID;
  }

}
