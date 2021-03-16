package com.easydynamics.oscalrestservice.exception;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * ErrorResponse is the model used to build the default error message response.
 * An ErrorResponse includes the error message, and a list of details
 */
@Data
@XmlRootElement(name = "error")
public class ErrorResponse {

  /**
   * Error Response Constructor.
   */

  public ErrorResponse(String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
  }

  //General error message about nature of error
  private String message;

  //Specific errors in API request processing
  private List<String> details;

}