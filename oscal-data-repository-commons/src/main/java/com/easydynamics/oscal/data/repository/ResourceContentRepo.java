package com.easydynamics.oscal.data.repository;

import java.util.Optional;
import org.springframework.core.io.Resource;

/**
 * Defines methods for manipulating the content of resources
 * such as images associated with OSCAL objects.
 */
public interface ResourceContentRepo {

  /**
   * Finds the resource content with the given id.
   *
   * @param id the content id
   * @return an optional resource
   */
  public Optional<Resource> findById(String id);

}
