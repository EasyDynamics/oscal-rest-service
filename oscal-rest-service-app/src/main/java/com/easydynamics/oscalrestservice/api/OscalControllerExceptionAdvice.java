package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshallingException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

  @ExceptionHandler(ClientAbortException.class)
  public void handleClientAbort(ClientAbortException exception, HttpServletRequest request) {
    logger.warn("ClientAbortException in request {} {} from {}",
        request.getMethod(), request.getRequestURL(), request.getRemoteAddr());
  }
}
