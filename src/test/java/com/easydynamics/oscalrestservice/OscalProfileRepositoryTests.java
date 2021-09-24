package com.easydynamics.oscalrestservice;

import com.easydynamics.oscalrestservice.repository.OscalProfileRepository;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
public class OscalProfileRepositoryTests extends OscalRepositoryTests {

  public static final String EXAMPLE_PROFILE_ID = "0f2814d7-a9a1-4b1f-aec8-eb7b10c1ef06";

  private OscalProfileRepositoryTests() {
    this.repository = new OscalProfileRepository("oscal-content/profiles");
    this.defaultId = EXAMPLE_PROFILE_ID;
  }

}
