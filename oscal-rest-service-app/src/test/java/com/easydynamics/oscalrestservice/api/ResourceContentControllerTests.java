package com.easydynamics.oscalrestservice.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Tests manipulating resource content.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ResourceContentControllerTests {

  private static final String EXPECTED_NAME = "authz-boundary.png";
  private static final String EXPECTED_TYPE = "image/png";

  @Autowired
  private MockMvc mockMvc;


  /**
   * Tests if the GET Request to /<oscalType>/{id} will retrieve a valid default <oscalType> object.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testGetOscalObject() throws Exception {
    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc
        .perform(get("/oscal/v1/resource-content/{id}", EXPECTED_NAME))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the content
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType(EXPECTED_TYPE));
  }
}
