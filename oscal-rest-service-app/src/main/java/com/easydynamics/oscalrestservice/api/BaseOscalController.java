package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.service.BaseOscalObjectService;

/**
 * BaseOscalController is the superclass for all Controllers that handles request to an Oscal
 * endpoint. It uses its repository field to perform CRUD operations on files of the
 * corresponding Oscal type.
 */
public class BaseOscalController<T> {

  private final BaseOscalObjectService<T> oscalObjectService;

  protected BaseOscalController(
      BaseOscalObjectService<T> oscalObjectService
  ) {
    this.oscalObjectService = oscalObjectService;
  }

  /**
   * Defines how a get request is handled by an Oscal controller.
   *
   * @param id uuid of the file to open
   * @return HTTP response containing file contents, error message and
   *     status code returned if file cannot be opened.
   */
  public T findById(String id) {
    return oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));
  }
}
