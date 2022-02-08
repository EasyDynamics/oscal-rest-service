package com.easydynamics.oscalrestservice.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.easydynamics.oscal.data.example.ExampleContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Test to validate behavior of a misconfigured persistence path on the service.
 */
@SpringBootTest(properties = {"persistence.file.parent.path=bogus-path"})
@AutoConfigureMockMvc
public class CatalogControllerMisconfiguredPersistenceTests {

  @Autowired
  private MockMvc mockMvc;

  protected OscalObjectType oscalObjectType;
  protected String defaultId;

  private CatalogControllerMisconfiguredPersistenceTests() {
    this.oscalObjectType = OscalObjectType.CATALOG;
    this.defaultId = ExampleContent.CATALOG_80053r5.uuid;
  }

    /**
   * Tests if the GET Request to /<oscalTypes> will retrieve a valid list of OSCAL objects.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testMisconfiguredFindAllOscalObjects() throws Exception {
    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/" + oscalObjectType.restPath))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the expected empty response
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").isEmpty());
  }

}