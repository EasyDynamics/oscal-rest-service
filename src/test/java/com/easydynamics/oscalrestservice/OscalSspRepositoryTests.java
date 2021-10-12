package com.easydynamics.oscalrestservice;

import com.easydynamics.oscalrestservice.repository.OscalSspRepository;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends OscalRepositoryTests {

  public static final String SSP_EXAMPLE_ID = "66c2a1c8-5830-48bd-8fdd-55a1c3a52888";

  private OscalSspRepositoryTests() {
    this.repository = new OscalSspRepository("oscal-content/system-security-plans");
    this.defaultId = SSP_EXAMPLE_ID;
  }

}
