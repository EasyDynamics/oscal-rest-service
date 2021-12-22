package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import gov.nist.secauto.oscal.lib.model.Profile;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
public class OscalProfileRepositoryTests extends BaseOscalRepositoryTests<Profile> {

  private OscalProfileRepositoryTests() {
    this.repository = new OscalProfileRepoFilePassImpl("oscal-content/profiles");
    this.defaultId = ExampleContentConstants.EXAMPLE_PROFILE_ID;
  }

}
