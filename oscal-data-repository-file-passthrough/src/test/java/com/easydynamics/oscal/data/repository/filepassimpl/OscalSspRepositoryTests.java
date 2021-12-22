package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends BaseOscalRepositoryTests<SystemSecurityPlan> {

  private OscalSspRepositoryTests() {
    this.repository = new OscalSspRepoFilePassImpl("oscal-content/system-security-plans");
    this.defaultId = ExampleContentConstants.SSP_EXAMPLE_ID;
  }

}
