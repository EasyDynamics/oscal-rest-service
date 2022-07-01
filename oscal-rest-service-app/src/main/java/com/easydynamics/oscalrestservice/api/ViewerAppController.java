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
   
    // This does a few things that are not exactly obvious why they are
    // necessary to be done. 
    //
    // UUID.fromString throws an exception when attempting to parse an invalud
    // UUID. So upon successfully parsing a uuid from the wildcard, /index.html
    // is returned. 
    //
    // When a uuid is not passed, there are two options to deal with. If a
    // "/" was added on the end with no actual wildcard, we just return
    // /index.html. In the case where something other than a uuid is in the
    // wildcard, we return /index.html/{wildCard} and let that get mapped if
    // needed.  
    //
    // All files returned must be prepended with "/". This is due to some
    // wonkiness with things being returned as absolute paths.
    //
    // Another very important thing to note is that if we try to add anything 
    // after the uuid, this will fail. 
    try {
      UUID.fromString(wildcardComponent);
      return "/index.html";
    } catch (IllegalArgumentException iae) {
      return (wildcardComponent.isBlank() ? "/index.html" : "/" + wildcardComponent);
    }
  }
}
