package com.easydynamics.oscal.data.marshalling.filepassimpl;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.data.model.OscalObject;
import org.springframework.stereotype.Component;

/**
 * Defines an OscalObjectMarshaller implementation for file
 * pass-through implementation.
 */
@Component
public class OscalObjectMarshallerFilePassImpl implements OscalObjectMarshaller {

  /**
   * Serializes the given OSCAL object into JSON.
   *
   * @return the OSCAL object's JSON representation.
   */
  public String toJson(Object oscalObject) {
    if (oscalObject == null || !(oscalObject instanceof OscalObject)) {
      throw new IllegalArgumentException("Invalid oscalObject");
    }
    return ((OscalObject) oscalObject).getContent();
  }

}
