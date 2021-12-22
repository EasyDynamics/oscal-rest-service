package com.easydynamics.oscalrestservice.api;

public class OscalObjectNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8066072607254448385L;

  OscalObjectNotFoundException(String id) {
    super("Oscal object with ID " + id + " was not found");
  }

}
