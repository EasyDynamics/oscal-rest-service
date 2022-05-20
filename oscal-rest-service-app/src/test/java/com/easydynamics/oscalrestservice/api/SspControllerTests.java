package com.easydynamics.oscalrestservice.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.easydynamics.oscal.data.example.ExampleContent;

/**
 * OscalSspControllerTests runs tests of the OscalSspController class.
 */
public class SspControllerTests extends BaseOscalControllerTests {

  private static final String ADD_SSP_IMPL_REQ_URL =
      "/oscal/v1/system-security-plans/{id}/control-implementation/implemented-requirements";
  private static final String UPDATE_SSP_IMPL_REQ_URL =
      "/oscal/v1/system-security-plans/{id}/control-implementation/implemented-requirements/{implementedRequirementId}";
  private static final String EXISTING_IMPL_REQ_UUID = "aaadb3ff-6ae8-4332-92db-211468c52af2";
  private static final String EXISTING_CONTROL_ID = "au-1";
  private static final String EXISTING_STATEMENT_ID = "au-1_smt.a";
  private static final String EXISTING_COMPONENT_UUID = "795533ab-9427-4abe-820f-0b571bacfe6d";
  private static final String EXISTING_PARAM_ID = "au-1_prm_1";
  private static final String NEW_IMPL_REQ_UUID = UUID.randomUUID().toString();
  private static final String NEW_CONTROL_ID = "some-control";
  private static final String NEW_STATEMENT_ID = NEW_CONTROL_ID + "_smt";
  private static final String NEW_COMPONENT_UUID = UUID.randomUUID().toString();
  private static final String NEW_PARAM_ID = NEW_CONTROL_ID + "_prm_1";
  private static final String EXPECTED_PARAM_VALUE = "Some Param Value";


  private SspControllerTests() {
    this.oscalObjectType = OscalObjectType.SSP;
    this.exampleContent = ExampleContent.SSP_EXAMPLE;
  }

  /**
   * Builds an SSP Implemented Requirement JSON string with the given
   * values.
   *
   * @param implReqUuid
   * @param controlId
   * @param statementId
   * @param componentUuid
   * @param paramId
   * @param paramValue
   * @return the SSP Implemented Requirement JSON string
   */
  private String buildImplementedRequirementJson(
      String implReqUuid,
      String controlId,
      String statementId,
      String componentUuid,
      String paramId,
      String paramValue
      ) {
    return "{\n"
        + "  \"implemented-requirement\": {\n"
        + "    \"uuid\": \"" + implReqUuid + "\",\n"
        + "    \"control-id\": \"" + controlId + "\",\n"
        + "    \"statements\": [\n"
        + "      {\n"
        + "         \"statement-id\": \"" + statementId + "\",\n"
        + "         \"by-components\": [\n"
        + "           {\n"
        + "             \"component-uuid\": \"" + componentUuid + "\",\n"
        + "             \"set-parameters\": [\n"
        + "               {\n"
        + "                 \"param-id\": \"" + paramId + "\",\n"
        + "                 \"values\": [ \"" + paramValue + "\" ]\n"
        + "               }\n"
        + "             ]\n"
        + "           }\n"
        + "         ]\n"
        + "       }\n"
        + "    ]\n"
        + "  }\n"
        + "}";
  }

  /**
   * Does the work of actually performing the mock MVC request for
   * updating an SSP Impl Req.
   *
   * @param httpMethod
   * @param implReqUuid
   * @param controlId
   * @param statementId
   * @param componentUuid
   * @param paramId
   * @param expectedParamValue
   * @param expectedStatus
   * @param isUpdateExpected
   * @throws Exception
   */
  private void testSspImplReq(
      HttpMethod httpMethod,
      String implReqUuid,
      String controlId,
      String statementId,
      String componentUuid,
      String paramId,
      String expectedParamValue,
      ResultMatcher expectedStatus,
      boolean isUpdateExpected
      ) throws Exception {

    String implReq = buildImplementedRequirementJson(
        implReqUuid, controlId, statementId, componentUuid, paramId, expectedParamValue);

    if (httpMethod.equals(HttpMethod.PUT)) {
      this.mockMvc.perform(
          put(UPDATE_SSP_IMPL_REQ_URL,
              exampleContent.uuid,
              implReqUuid)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(implReq))
        .andExpect(expectedStatus);
    } else if (httpMethod.equals(HttpMethod.POST)){
      this.mockMvc.perform(
          post(ADD_SSP_IMPL_REQ_URL,
              exampleContent.uuid)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(implReq))
        .andExpect(expectedStatus);
    } else {
      throw new UnsupportedOperationException();
    }

    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/"
        + oscalObjectType.restPath + "/{id}", exampleContent.uuid))
        .andExpect(request().asyncStarted())
        .andReturn();

    if (isUpdateExpected) {
      // On async result check the content and title
      this.mockMvc.perform(asyncDispatch(asyncResult))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$.*.control-implementation"
              + ".implemented-requirements[?(@.uuid == \"" + implReqUuid + "\")]"
              + ".statements[?(@.statement-id == \"" + statementId + "\")]"
              + ".by-components[?(@.component-uuid == \"" + componentUuid + "\")]"
              + ".set-parameters[?(@.param-id == \"" + paramId + "\")]"
              + ".values[0]").value(expectedParamValue));
    }
  }

  /**
   * Tests if the request to add a new SSP Implemented Requirement updates the SSP via POST.
   *
   * @throws Exception
   */
  @Test
  public void testCreateSspImplReqViaPost() throws Exception {
    testSspImplReq(
        HttpMethod.POST,
        NEW_IMPL_REQ_UUID,
        NEW_CONTROL_ID,
        NEW_STATEMENT_ID,
        NEW_COMPONENT_UUID,
        NEW_PARAM_ID,
        EXPECTED_PARAM_VALUE,
        status().isOk(),
        true);
  }

  /**
   * Tests a conflicting request to add a new SSP Implemented Requirement via POST.
   *
   * @throws Exception
   */
  @Test
  public void testImplAlreadyExistsViaPost() throws Exception {

    testSspImplReq(
        HttpMethod.POST,
        EXISTING_IMPL_REQ_UUID,
        EXISTING_CONTROL_ID,
        EXISTING_STATEMENT_ID,
        EXISTING_COMPONENT_UUID,
        EXISTING_PARAM_ID,
        EXPECTED_PARAM_VALUE,
        status().isConflict(),
        false);
  }

  /**
   * Tests if the request to add a new SSP Implemented Requirement updates the SSP via PUT.
   *
   * @throws Exception
   */
  @Test
  public void testCreateSspImplReqViaPut() throws Exception {
    testSspImplReq(
        HttpMethod.PUT,
        NEW_IMPL_REQ_UUID,
        NEW_CONTROL_ID,
        NEW_STATEMENT_ID,
        NEW_COMPONENT_UUID,
        NEW_PARAM_ID,
        EXPECTED_PARAM_VALUE,
        status().isOk(),
        true);
  }

  /**
   * Tests if the request to update an existing SSP Implemented Requirement updates the SSP via PUT.
   *
   * @throws Exception
   */
  @Test
  public void testUpdateExistingSspImplReq() throws Exception {
    testSspImplReq(
        HttpMethod.PUT,
        EXISTING_IMPL_REQ_UUID,
        EXISTING_CONTROL_ID,
        EXISTING_STATEMENT_ID,
        EXISTING_COMPONENT_UUID,
        EXISTING_PARAM_ID,
        EXPECTED_PARAM_VALUE,
        status().isOk(),
        true);
  }
}