package com.easydynamics.oscal.data.repository.file;

import com.easydynamics.oscal.data.example.ExampleContent;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends BaseOscalRepositoryTests<SystemSecurityPlan> {

  private OscalSspRepositoryTests() {
    this.exampleContent = ExampleContent.SSP_EXAMPLE;
  }

}
