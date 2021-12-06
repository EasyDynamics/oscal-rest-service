package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import com.easydynamics.oscal.data.model.OscalProfileObject;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
public class OscalProfileRepositoryTests extends BaseOscalRepositoryTests<OscalProfileObject> {

  private OscalProfileRepositoryTests() {
    this.repository = new OscalProfileRepoFilePassImpl("oscal-content/profiles");
    this.defaultId = ExampleContentConstants.EXAMPLE_PROFILE_ID;
  }

}
