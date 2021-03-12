package com.easydynamics.oscalrestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RecordNotFoundException is used to set response messages for 404 errors.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
  public RecordNotFoundException(String exception) {
    super(exception);
  }
}