package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshallingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice for object not found exceptions.
 */
@ControllerAdvice
public class OscalControllerExceptionAdvice {

  @ResponseBody
  @ExceptionHandler(OscalObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String oscalObjectNotFoundHandler(OscalObjectNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(OscalObjectConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String oscalObjectConflictHandler(OscalObjectConflictException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(OscalObjectMarshallingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String oscalObjectMarshallingHandler(OscalObjectMarshallingException exception) {
    return exception.getMessage();
  }
}
