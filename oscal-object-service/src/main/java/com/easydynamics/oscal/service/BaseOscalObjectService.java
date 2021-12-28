package com.easydynamics.oscal.service;

import org.springframework.data.repository.CrudRepository;

/**
 * Defines an extension of CrudRepository for OSCAL objects.
 *
 * @param <T> the OSCAL object type
 */
public interface BaseOscalObjectService<T> extends CrudRepository<T, String> {

  /**
   * Merges all fields/properties from the given source OSCAL object
   * to the given target OSCAL object.
   *
   * @param source the source OSCAL object
   * @param target the target OSCAL object
   * @return the result of the merge
   */
  public T merge(T source, T target);
}
