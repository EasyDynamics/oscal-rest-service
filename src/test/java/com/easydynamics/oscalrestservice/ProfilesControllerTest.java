package com.easydynamics.oscalrestservice;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfilesControllerTest {

  public static final String EXAMPLE_PROFILE_ID = "0f2814d7-a9a1-4b1f-aec8-eb7b10c1ef06";

  @Autowired
  private MockMvc mockMvc;

  /**
   * Test to see if the GET Request to /profiles/{id} will retrieve the profile.json from the oscal-content git hub when provided a valid id
   * @throws Exception
   */

  @Test
  public void shouldReturnDefaultMessage() throws Exception {

    this.mockMvc.perform(get("/oscal/v1/profiles/{id}", EXAMPLE_PROFILE_ID))
        .andExpect(status().isOk());
  }

  /**
   * Test to see if the GET Request to /profiles/{id} will fail if provided an invalid id
   * @throws Exception
   */

  @Test
  public void isNotFound() throws Exception {
    String id="bad-id-this-will-not-work-123";
    this.mockMvc.perform(get("/oscal/v1/profiles/{id}", id))
        .andExpect(status().isNotFound());
  }
}