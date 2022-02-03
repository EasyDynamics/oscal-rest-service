package com.easydynamics.oscalrestservice.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class ErrorHandlingController implements ErrorController {
 
  @GetMapping("/error")
  public String handleError() {
    System.out.println("Testing error handler controller!");
    return "index.html";
  }
}