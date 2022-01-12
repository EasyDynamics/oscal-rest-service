package com.easydynamics.oscalrestservice.api;

/**
 * Thrown when a request to change an OSCAL object would
 * result in a conflict.
 */
public class OscalObjectConflictException extends RuntimeException {

  private static final long serialVersionUID = -8066072607254448385L;

  OscalObjectConflictException(String message) {
    super(message);
  }

}
