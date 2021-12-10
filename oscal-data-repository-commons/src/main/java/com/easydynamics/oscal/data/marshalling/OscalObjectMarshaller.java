package com.easydynamics.oscal.data.marshalling;

/**
 * OscalObjectMarshaller defines an interface to marshall and unmarshall
 * OSCAL objects to and from different formats.
 */
public interface OscalObjectMarshaller {

  /**
   * Serializes the given OSCAL object into JSON.
   *
   * @return the OSCAL object's JSON representation.
   */
  public String toJson(Object oscalObject);

}
