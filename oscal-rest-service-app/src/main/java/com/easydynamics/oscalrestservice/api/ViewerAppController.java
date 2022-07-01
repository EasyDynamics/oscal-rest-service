package com.easydynamics.oscalrestservice.api;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerMapping;

@Controller
public class ViewerAppController {

  /**
   * Get the underlying asset for a request.
   * @param request the request
   * @return the file
   */
  @GetMapping({ "system-security-plan/**", "catalog/**", "component-definition/**", "profile/**" })
  public String getOscalViewerAsset(HttpServletRequest request) {
    String fullRequestPath =
        (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String baseMapping =
        (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    String wildcardComponent =
        new AntPathMatcher().extractPathWithinPattern(baseMapping, fullRequestPath);
    try {
      UUID.fromString(wildcardComponent);
      return "/index.html";
    } catch (IllegalArgumentException iae) {
      return (wildcardComponent.isBlank() ? "/index.html" : "/" + wildcardComponent);
    }
  }
}
