package com.easydynamics.oscalrestservice.api;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.easydynamics.oscal.data.example.ExampleContent;

import net.minidev.json.JSONArray;

/**
 * OscalControllerTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalController types.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseOscalControllerTests {

  @Autowired
  protected MockMvc mockMvc;

  protected OscalObjectType oscalObjectType;
  protected ExampleContent exampleContent;

  private String originalFileContents;

  @BeforeEach
  public void setup() throws IOException {
    originalFileContents = Files.readString(Paths.get(
        "target/oscal-demo-content-main",
        oscalObjectType.jsonField + "s",
        exampleContent.fileName),
        Charset.forName("UTF-8"));
  }

  @AfterEach
  public void reset() throws IOException {
    Files.writeString(Paths.get(
        "target/oscal-demo-content-main",
        oscalObjectType.jsonField + "s",
        exampleContent.fileName),
        originalFileContents, Charset.forName("UTF-8"));
  }

  /**
   * Tests if the GET Request to /<oscalType>/{id} will retrieve a valid default <oscalType> object.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testGetOscalObject() throws Exception {
    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/"
        + oscalObjectType.restPath + "/{id}", exampleContent.uuid))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the content and UUID
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.*.uuid").value(exampleContent.uuid));
  }

  /**
   * Tests if the GET Request to /<oscalType>/{id} by file name
   * will retrieve a valid default <oscalType> object.
   *
   * @throws Exception the exception thrown by the REST request.
   */
  @Test
  public void testGetOscalObjectByFileName() throws Exception {
    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/"
        + oscalObjectType.restPath + "/{id}", exampleContent.fileName))
        .andExpect(request().asyncStarted())
        .andReturn();

    // On async result check the content and UUID
    this.mockMvc.perform(asyncDispatch(asyncResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.*.uuid").value(exampleContent.uuid));
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

    this.mockMvc.perform(patch("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(updatedObject))
        .andExpect(status().isOk());

    // With StreamingResponseBody this is async so we start the request here
    MvcResult asyncResult = this.mockMvc.perform(get("/oscal/v1/"
        + oscalObjectType.restPath + "/{id}", exampleContent.uuid))
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
        + "    \"uuid\": \"" + exampleContent.uuid + "\",\n"
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
    this.mockMvc.perform(patch("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
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

    this.mockMvc.perform(patch("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
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
        .andExpect(jsonPath("$[*].*.uuid", hasItem(exampleContent.uuid)));
  }

  @Test
  public void testPutOscalObjectNotFound() throws Exception {
    String id = "0300753c-563b-4956-b6f6-3a68950a5279";
    this.mockMvc.perform(put("/oscal/v1/" + oscalObjectType.restPath + "/{id}", id)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")
      .content(generateOscalObject(oscalObjectType.jsonField, id)))
      .andExpect(status().isNotFound());
  }

  @Test
  public void testPutOscalObjectUnsupportedMediaType() throws Exception {
    this.mockMvc.perform(put("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
      .contentType(MediaType.APPLICATION_XML_VALUE)
      .accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")
      .content(generateOscalObject(oscalObjectType.jsonField, exampleContent.uuid)))
      .andExpect(status().isUnsupportedMediaType());
  }

  @Test
  public void testPutMismatchOscalObject() throws Exception {
    String badId = "8A7D0A6D-024E-416A-956A-FDEB8816EEA1";
    this.mockMvc.perform(put("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")
      .content(generateOscalObject(oscalObjectType.jsonField, badId)))
      .andExpect(status().isConflict())
      .andExpect(content().string(containsStringIgnoringCase(badId)))
      .andExpect(content().string(containsStringIgnoringCase(exampleContent.uuid)));
  }

  @Test
  public void testPutNullIdMismatchOscalObject() throws Exception {
    String badId = null;
    this.mockMvc.perform(put("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")
      .content(generateOscalObject(oscalObjectType.jsonField, badId)))
      .andExpect(status().isBadRequest());
  }


  @Test
  public void testPutOscalObjectSuccess() throws Exception {
    this.mockMvc.perform(put("/oscal/v1/" + oscalObjectType.restPath + "/{id}", exampleContent.uuid)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")
      .content(generateOscalObject(oscalObjectType.jsonField, exampleContent.uuid)))
      .andExpect(status().isOk());
  }

  private static String generateOscalObject(String objectType, String id) {
      return "{\n"
      + "  \"" + objectType + "\": {\n"
      + "    \"uuid\":" + (id == null? "null" : "\"" + id + "\"") + ",\n"
      + "    \"metadata\": {\n"
      + "      \"title\": \"Some New Title\",\n"
      + "      \"version\": \"2.0.0\",\n"
      +"       \"oscal-version\": \"1.0.0\"\n"
      + "    }\n"
      + "  }\n"
      + "}";
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
