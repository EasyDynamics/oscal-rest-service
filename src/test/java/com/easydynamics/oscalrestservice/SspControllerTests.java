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
public class SspControllerTests {

  @Autowired
  private MockMvc mockMvc;

  public static final String SSP_EXAMPLE_ID = "66c2a1c8-5830-48bd-8fdd-55a1c3a52888";

  /**
   * Test to see if the GET Request to /ssps/{id} will retrieve the ssp-example.json from the oscal-content git hub when provided a valid id
   * @throws Exception
   */

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/oscal/v1/ssps/{id}", SSP_EXAMPLE_ID))
        .andExpect(status().isOk());
  }

  /**
   * Test to see if the GET Request to /ssps/{id} will fail if provided an invalid id
   * @throws Exception
   */

  @Test
  public void isNotFound() throws Exception {
    String id="bad-id-this-will-not-work-123";
    
    this.mockMvc.perform(get("/oscal/v1/ssps/{id}", id))
        .andExpect(status().isNotFound());
  }
}