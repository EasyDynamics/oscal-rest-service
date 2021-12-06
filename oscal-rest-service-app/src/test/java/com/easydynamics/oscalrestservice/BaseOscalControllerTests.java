package com.easydynamics.oscalrestservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * OscalControllerTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalController types.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseOscalControllerTests {

  @Autowired
  private MockMvc mockMvc;

  protected String oscalType;
  protected String defaultId;

  /**
   * Tests if the GET Request to /<oscalType>/{id} will retrieve a valid default <oscalType> json file.
   * 
   * @throws Exception
   */
  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/oscal/v1/" + oscalType + "/{id}", defaultId))
        .andExpect(status().isOk());
  }

  /**
   * Tests if the GET Request to /<oscalType>/{id} will fail if provided an invalid id.
   * 
   * @throws Exception
   */
  @Test
  public void isNotFound() throws Exception {
    String id="bad-id-this-will-not-work-123";
    this.mockMvc.perform(get("/oscal/v1/" + oscalType + "/{id}", id))
        .andExpect(status().isNotFound());
  }
}
