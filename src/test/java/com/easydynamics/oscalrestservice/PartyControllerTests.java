package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.api.CatalogController.CATALOG_ID_80053r5;
import static com.easydynamics.oscalrestservice.api.SspController.SSP_EXAMPLE_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.easydynamics.oscalrestservice.model.OscalParty;
import com.easydynamics.oscalrestservice.repository.PartyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PartyControllerTests {

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private PartyRepository testRepository;

  /**
   * Test to see if the POST Request to /parties will be valid when provided a good request.
   * @throws Exception
   */

  @Test
  public void getRequestTest() throws Exception {
    OscalParty party = new OscalParty("organization", "IT Department");
    testRepository.save(party);
    this.mockMvc.perform(get("/oscal/v1/parties"))
        .andExpect(status().isOk());
  }

  /**
   * Test to see if the GET Request to /parties/{id} will be invalid when provided a bad uuid.
   * @throws Exception
   */

  @Test
  public void getPartyByIdTestInvalidRequest() throws Exception {
    this.mockMvc.perform(get("/oscal/v1/parties/{id}", "not a uuid"))
        .andExpect(status().isNotFound());
  }

  /**
   * Test to see if the POST Request to /parties will be valid when provided a good request
   * @throws Exception
   */

  @Test
  public void getPartyByIdValidRequest() throws Exception {
    OscalParty testParty = new OscalParty("testing", "testing the id");
    testRepository.save(testParty);
    this.mockMvc.perform(get("/oscal/v1/parties/{id}", testParty.getUuid()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.type").value("testing"));
  }

  /**
   * Test to see if the GET Request to /parties/{id} will be valid when provided a good uuid.
  */
  
  @Test
  public void giveValidRequest_thenVerifyResponse() throws Exception {
    this.mockMvc.perform(post("/oscal/v1/parties")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"type\": \"bob\", \"name\" : \"the name\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.type").value("bob"));
  }

  /**
   * Test to see if the POST Request to /parties will be invalid when provided a bad request
   * @throws Exception
   */

  @Test
  public void giveInvalidRequest_thenVerifyResponse() throws Exception {
    this.mockMvc.perform(post("/oscal/v1/parties")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"name\" : \"the name\"}"))
        .andDo(print())
        .andExpect(status().isBadRequest());

  }
}