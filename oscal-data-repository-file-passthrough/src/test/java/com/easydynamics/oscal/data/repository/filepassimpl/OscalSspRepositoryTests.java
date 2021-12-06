package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import com.easydynamics.oscal.data.model.OscalSspObject;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends BaseOscalRepositoryTests<OscalSspObject> {

  private OscalSspRepositoryTests() {
    this.repository = new OscalSspRepoFilePassImpl("oscal-content/system-security-plans");
    this.defaultId = ExampleContentConstants.SSP_EXAMPLE_ID;
  }

}
