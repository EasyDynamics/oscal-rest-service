package com.easydynamics.oscal.data.marshalling;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * OscalObjectMarshaller defines an interface to marshall and unmarshall
 * OSCAL objects to and from different formats.
 */
public interface OscalObjectMarshaller<T> {

  /**
   * Serializes the given OSCAL object into JSON on the given output stream.
   */
  public void toJson(T oscalObject, OutputStream outputStream);

  /**
   * Deserializes the given input stream into an OSCAL POJO object.
   *
   * @param inputStream the input stream to deserialize
   * @return the OSCAL object
   */
  public T toObject(InputStream inputStream);
}