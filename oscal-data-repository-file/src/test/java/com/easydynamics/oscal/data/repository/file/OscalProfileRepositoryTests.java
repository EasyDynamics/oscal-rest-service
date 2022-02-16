package com.easydynamics.oscal.data.repository.file;

import com.easydynamics.oscal.data.example.ExampleContent;
import gov.nist.secauto.oscal.lib.model.Profile;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
public class OscalProfileRepositoryTests extends BaseOscalRepositoryTests<Profile> {

  private OscalProfileRepositoryTests() {
    this.exampleContent = ExampleContent.PROFILE_80053r4MOD;
  }

}
