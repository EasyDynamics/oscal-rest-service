package com.easydynamics.oscal.data.marshalling;

/**
 * Thrown when an OSCAL object marshaller encounters errors marshalling
 * or unmarshalling.
 */
public class OscalObjectMarshallingException extends RuntimeException {

  private static final long serialVersionUID = -1459099564725908531L;

  public OscalObjectMarshallingException() {
    super();
  }

  public OscalObjectMarshallingException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public OscalObjectMarshallingException(String message, Throwable cause) {
    super(message, cause);
  }

  public OscalObjectMarshallingException(String message) {
    super(message);
  }

  public OscalObjectMarshallingException(Throwable cause) {
    super(cause);
  }

}
