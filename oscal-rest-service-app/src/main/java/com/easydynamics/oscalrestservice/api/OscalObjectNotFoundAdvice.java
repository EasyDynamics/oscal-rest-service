package com.easydynamics.oscalrestservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OscalObjectNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(OscalObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String oscalObjectNotFoundHandler(OscalObjectNotFoundException exception) {
    return exception.getMessage();
  }
}
