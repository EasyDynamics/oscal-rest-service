package com.easydynamics.oscal.service;

import org.springframework.data.repository.CrudRepository;

/**
 * Defines an extension of CrudRepository for OSCAL objects.
 *
 * @param <T> the OSCAL object type
 */
public interface BaseOscalObjectService<T> extends CrudRepository<T, String> {

}
