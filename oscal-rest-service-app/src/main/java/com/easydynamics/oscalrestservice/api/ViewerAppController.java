package com.easydynamics.oscalrestservice.api;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Serve the viewer/editor application from the REST service.
 */
@Controller
public class ViewerAppController {

  /**
   * Get the underlying asset map given a request url.
   *
   * @param request - the request given by the user 
   * @return the file within the service that the request maps to
   */
  @GetMapping({ "system-security-plan/**", "catalog/**", "component-definition/**", "profile/**" })
  public String getOscalViewerAsset(HttpServletRequest request) {
    String fullRequestPath =
        (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String baseMapping =
        (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    String wildcardComponent =
        new AntPathMatcher().extractPathWithinPattern(baseMapping, fullRequestPath);

    // This rewrites requests for the viewer/editor application (built as a single-page
    // application), making a few assumptiong along the way. First, the assumption is that every
    // valid route in the will begin with one of above base paths and end with an empty string or
    // a UUID and second, that every other path is a supporting static asset that needs to be
    // returned as an absolute URL (as a relative URL will be interpreted relative to the matching
    // base path, resulting in recursive/looping calls to this handler).
    // This logic will require reworking if the viewer application adds futher embedded paths or
    // non-UUID-ending paths.
    // An alternative solution might be to have a set of file extensions that are expected to be
    // static assets instead and to somewhat invert the logic; however, that would require an
    // expected set of asset extensions or prefixes.
    try {
      UUID.fromString(wildcardComponent);
      return "/index.html";
    } catch (IllegalArgumentException iae) {
      return (wildcardComponent.isBlank() ? "/index.html" : "/" + wildcardComponent);
    }
  }
}
