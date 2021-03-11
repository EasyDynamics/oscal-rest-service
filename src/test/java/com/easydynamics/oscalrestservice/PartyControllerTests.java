package com.easydynamics.oscalrestservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  /**
   * Test to see if the POST Request to /parties will be valid when provided a good request
   * @throws Exception
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