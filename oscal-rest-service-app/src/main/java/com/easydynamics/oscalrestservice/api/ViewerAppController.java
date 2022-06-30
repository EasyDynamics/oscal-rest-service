package com.easydynamics.oscalrestservice.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewerAppController {

  @GetMapping({"/system-security-plan/**","/catalog/**","/component-definition/**","/profile/**"})
  public String getViewerPage() {
    return "/index.html";
  }

}
