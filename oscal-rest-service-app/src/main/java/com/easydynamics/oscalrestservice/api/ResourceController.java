package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.repository.ResourceContentRepo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Resource Controller for OSCAL Rest Service. This class handles all requests to the
 * /resource-content endpoint to support fetching and storing supporting resources in OSCAL
 * documents (such as images).
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class ResourceController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private ResourceContentRepo repository;

  @Autowired(required = true)
  public ResourceController(
      ResourceContentRepo repository
  ) {
    this.repository = repository;
  }

  /**
   * Defines a GET request for resource content by ID.
   *
   * @param id the resource ID
   * @return the supporting resource
   * @throws IOException thrown if there is an issue determining content length
   */
  @GetMapping("/resource-content/{id}")
  public ResponseEntity<StreamingResponseBody> findById(
      @PathVariable String id) throws IOException {
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));
    Path resourcePath = resource.getFile().toPath();

    MediaType contentType = MediaType.parseMediaType(Files.probeContentType(resourcePath));
    if (contentType == null) {
      // Using application/octet-stream ensurres that the resulting data is at least treated
      // as binary content.
      contentType = MediaType.APPLICATION_OCTET_STREAM;
      logger.debug("Failed to automatically determine content type for {}. Falling back to {}",
          id, contentType);
    }

    StreamingResponseBody responseBody = (outputStream) -> {
      Files.copy(resourcePath, outputStream);
    };

    return ResponseEntity.ok()
    .contentLength(resource.contentLength())
    .contentType(contentType)
    .body(responseBody);
  }

}
