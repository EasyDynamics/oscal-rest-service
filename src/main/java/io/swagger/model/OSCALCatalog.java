package io.swagger.model;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OSCALCatalog
 */
@Validated
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-22T21:37:05.909Z[GMT]")

@JsonIgnoreProperties(ignoreUnknown = true)
public class OSCALCatalog   {

  private String uuid;
  private String title;

  public String getUuid() {
    return uuid;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public String toString() {
    return "OSCALCatalog{" +
            "uuid='" + uuid + '\'' +
            ", title=" + title +
            '}';
  }
  @SuppressWarnings("unchecked")
  @JsonProperty("metadata")
  private void unpackNested(Map<String,Object> metadata) {
    this.title = (String)metadata.get("title");

  }

/*
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OSCALCatalog {\n");
    
    sb.append("}");
    return sb.toString();
  }


  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

 */
}
/*
public class Metadata{
  public String title;
  public Date published;
  @JsonProperty("last-modified")
  public Date lastModified;
  public String version;
  @JsonProperty("oscal-version")
  public String oscalVersion;
}*/