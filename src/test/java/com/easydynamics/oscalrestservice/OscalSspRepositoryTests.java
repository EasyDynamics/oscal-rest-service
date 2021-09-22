package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.SspControllerTests.SSP_EXAMPLE_ID;

import com.easydynamics.oscalrestservice.repository.OscalSspRepository;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends OscalRepositoryTests {

  private OscalSspRepositoryTests() {
    this.repository = new OscalSspRepository("oscal-content/system-security-plans");
    this.defaultId = SSP_EXAMPLE_ID;
  }

}
