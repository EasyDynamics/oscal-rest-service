package com.easydynamics.oscalrestservice.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalProfilesControllerTests runs tests of the OscalProfilesController class.
 */
public class ProfilesControllerTests extends BaseOscalControllerTests {
  
  private static final String EXISTING_RESOURCE_UUID = "dc380596-027f-423b-83f2-82757554ee27"; 

  private ProfilesControllerTests() {
    this.oscalObjectType = OscalObjectType.PROFILE;
    this.exampleContent = ExampleContent.PROFILE_80053r4MOD;
  }

  @Test
  public void testPatchBackMatterWithExistingUUID() throws Exception {
    this.testPatchBackMatterResource(status().isOk(), EXISTING_RESOURCE_UUID);
  }
}
