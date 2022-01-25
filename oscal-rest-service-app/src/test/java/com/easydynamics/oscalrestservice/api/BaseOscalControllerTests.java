package com.easydynamics.oscalrestservice.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.ZonedDateTime;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import net.minidev.json.JSONArray;

/**
 * OscalControllerTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalController types.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseOscalControllerTests {

  @Autowired
  private MockMvc mockMvc;

  protected OscalObjectType oscalObjectType;
  protected String defaultId;


  /**
   * Tests if the GET Request to /<oscalType>/{id} will retrieve a valid default <oscalType> object.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testGetOscalObject() throws Exception {
    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/" + oscalObjectType.restPath + "/{id}", defaultId))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the content and UUID
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.*.uuid").value(defaultId));
  }

  /**
   * Tests if the GET Request to /<oscalType>/{id} will fail if provided an invalid id.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testOscalObjectNotFound() throws Exception {
    String id="bad-id-this-will-not-work-123";
    this.mockMvc.perform(get("/oscal/v1/" + oscalObjectType.restPath + "/{id}", id))
        .andExpect(status().isNotFound());
  }

  protected void testPatchOscalObject(String updatedObject, String expectedTitle) throws Exception {
    ZonedDateTime startDateTime = ZonedDateTime.now();

    this.mockMvc.perform(patch("/oscal/v1/" + oscalObjectType.restPath + "/{id}", defaultId)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(updatedObject))
        .andExpect(status().isOk());

    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/" + oscalObjectType.restPath + "/{id}", defaultId))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the content and title
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.*.metadata.title").value(expectedTitle))
        .andExpect(jsonPath("$.*.metadata.oscal-version").value("1.0.0"))
        .andExpect(jsonPath("$.*.metadata.last-modified",
            new DateGreaterMatcher(startDateTime)));
  }

  /**
   * Tests if the PATCH Request to /<oscalType>/{id} will update an <oscalType> object.
   *
   * @throws Exception
   */
  @Test
  public void testPatchOscalObject() throws Exception {
    String expectedTitle = "Some New Title";
    String updatedObject = "{\n"
        + "  \"" + oscalObjectType.jsonField + "\": {\n"
        + "    \"uuid\": \"" + defaultId + "\",\n"
        + "    \"metadata\": {\n"
        + "      \"title\": \"" + expectedTitle + "\",\n"
        + "      \"version\": \"1.0\"\n"
        + "    }\n"
        + "  }\n"
        + "}";

    testPatchOscalObject(updatedObject, expectedTitle);
  }

  /**
   * Tests if the PATCH Request to /<oscalType>/{id} will update an <oscalType> object.
   *
   * @throws Exception
   */
  @Test
  public void testPatchOscalObjectNoUuid() throws Exception {
    String expectedTitle = "Some New Title";
    String updatedObject = "{\n"
        + "  \"" + oscalObjectType.jsonField + "\": {\n"
        + "    \"metadata\": {\n"
        + "      \"title\": \"" + expectedTitle + "\",\n"
        + "      \"version\": \"1.0\"\n"
        + "    }\n"
        + "  }\n"
        + "}";

    testPatchOscalObject(updatedObject, expectedTitle);
  }

  /**
   * Tests that an empty PATCH request fails.
   *
   * @throws Exception
   */
  @Test
  public void testPatchEmptyOscalObject() throws Exception {
    this.mockMvc.perform(patch("/oscal/v1/" + oscalObjectType.restPath + "/{id}", defaultId)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
        .andExpect(status().is4xxClientError());
  }

  /**
   * Tests that a PATCH request with an UUID mismatch fails.
   *
   * @throws Exception
   */
  @Test
  public void testPatchMismatchOscalObject() throws Exception {
    String expectedTitle = "Some New Title";
    String updatedObject = "{\n"
        + "  \"" + oscalObjectType.jsonField + "\": {\n"
        + "    \"uuid\": \"8A7D0A6D-024E-416A-956A-FDEB8816EEA1\",\n"
        + "    \"metadata\": {\n"
        + "      \"title\": \"" + expectedTitle + "\",\n"
        + "      \"version\": \"1.0\"\n"
        + "    }\n"
        + "  }\n"
        + "}";

    this.mockMvc.perform(patch("/oscal/v1/" + oscalObjectType.restPath + "/{id}", defaultId)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(updatedObject))
        .andExpect(status().is4xxClientError());
  }

  /**
   * Tests if the GET Request to /<oscalTypes> will retrieve a valid list of OSCAL objects.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testFindAllOscalObjects() throws Exception {
    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/" + oscalObjectType.restPath))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the content and UUID
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].*.uuid").value(defaultId));
  }

  class DateGreaterMatcher extends BaseMatcher<ZonedDateTime> {
    private ZonedDateTime floor;
    DateGreaterMatcher(ZonedDateTime floor) {
      this.floor = floor;
    }

    @Override
    public boolean matches(Object actual) {
      JSONArray jsonArray = (JSONArray) actual;
      ZonedDateTime actualDateTime = ZonedDateTime.parse(jsonArray.get(0).toString());
      return actualDateTime.isAfter(floor);
    }

    @Override
    public void describeTo(Description description) {
      description.appendText(
          String.format("last-modified should have been greater than %s",
          floor));
    }
  }
}
