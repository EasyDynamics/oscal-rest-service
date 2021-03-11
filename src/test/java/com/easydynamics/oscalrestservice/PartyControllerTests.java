package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.api.CatalogController.CATALOG_ID_80053r5;
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
   * Test to see if the POST Request to /parties will be valid when provided a good request
   * @throws Exception
   */

  @Test
  public void getRequestTest() throws Exception {
    OscalParty party = new OscalParty("organization", "IT Department");
    testRepository.save(party);
    this.mockMvc.perform(get("/oscal/v1/parties"))
        .andDo(print())
        .andExpect(status().isOk());
  }

}