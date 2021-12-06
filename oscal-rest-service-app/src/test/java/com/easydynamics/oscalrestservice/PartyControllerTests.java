package com.easydynamics.oscalrestservice;

import com.easydynamics.oscalrestservice.model.OscalParty;
import com.easydynamics.oscalrestservice.repository.PartyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PartyControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PartyRepository testRepository;

  /**
   * Test to see if the OSCAL Party is created with POST to /parties,
   * and test to see if that Party can be retrieved by a request to GET /parties/{id}
   * @throws Exception
   */

  @Test
  public void partyPostAndGetTest() throws Exception {
    OscalParty testParty = new OscalParty("testing", "testing the id");
    testRepository.save(testParty);
    this.mockMvc.perform(get("/oscal/v1/parties/{id}", testParty.getUuid()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.type").value("testing"));
  }

  /**
   * Test to see if all parties can be returned by a GET /parties request
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
   * Test to see if a GET Request to /parties/{id} will be invalid if provided a bad uuid.
   * @throws Exception
   */

  @Test
  public void getPartyByIdTestInvalidRequest() throws Exception {
    this.mockMvc.perform(get("/oscal/v1/parties/{id}", "not a uuid"))
        .andExpect(status().isNotFound());
  }

  /**
   * Test to see if the POST Request to /parties will be valid if provided a good request body.
   * @throws Exception
   */

  @Test
  public void giveValidRequest_thenVerifyResponse() throws Exception {
    this.mockMvc.perform(post("/oscal/v1/parties")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"type\": \"bob\", \"name\" : \"the name\"}"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.type").value("bob"));
  }

  /**
   * Test to see if a POST Request to /parties will be invalid when provided a bad request body.
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