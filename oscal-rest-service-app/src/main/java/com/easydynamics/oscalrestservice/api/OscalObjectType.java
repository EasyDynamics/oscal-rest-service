package com.easydynamics.oscalrestservice.api;

/**
 * Defines the top-level supported OSCAL REST object types.
 */
public enum OscalObjectType {
  CATALOG("catalogs", "catalog"),
  PROFILE("profiles", "profile"),
  COMPONENT_DEFINITION("component-definitions", "component-definition"),
  SSP("system-security-plans", "system-security-plan");

  final String restPath;
  final String jsonField;

  OscalObjectType(String restPath, String jsonField) {
    this.restPath = restPath;
    this.jsonField = jsonField;
  }

}
