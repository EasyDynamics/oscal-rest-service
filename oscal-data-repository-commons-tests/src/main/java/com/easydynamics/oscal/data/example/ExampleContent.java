package com.easydynamics.oscal.data.example;

/**
 * Defines known IDs and file names for the example NIST OSCAL documents.
 */
public enum ExampleContent {

  CATALOG_80053r5("1da16966-2d5b-4e8a-9056-0fe09d35412b",
      "NIST_SP-800-53_rev5_catalog.json"),
  COMPONENT_EXAMPLE("8223d65f-57a9-4689-8f06-2a975ae2ad72",
      "example-component.json"),
  PROFILE_80053r4MOD("8b3beca1-fcdc-43e0-aebb-ffc0a080c486",
      "NIST_SP-800-53_rev4_MODERATE-baseline_profile.json"),
  SSP_EXAMPLE("cff8385f-108e-40a5-8f7a-82f3dc0eaba8",
      "ssp-example.json");

  public final String uuid;
  public final String fileName;

  private ExampleContent(String uuid, String fileName) {
    this.uuid = uuid;
    this.fileName = fileName;
  }
}
