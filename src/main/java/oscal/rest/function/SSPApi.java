/*
SSP Api from codegen to be used later,
when I am actually mapping it to the model with liboscal

package io.swagger.api;

import io.swagger.model.OSCALSsp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
*/
/*
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-22T21:37:05.909Z[GMT]")
public interface SspsApi {

    @Operation(summary = "Add a new OSCAL system security slan", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "405", description = "Invalid input") })
    @RequestMapping(value = "/ssps",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addSsp(@Parameter(in = ParameterIn.DEFAULT, description = "OSCAL system security plan object to be added", required=true, schema=@Schema()) @Valid @RequestBody OSCALSsp body);


    @Operation(summary = "Deletes an OSCAL system security plan", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "System security plan not found") })
    @RequestMapping(value = "/ssps/{sspId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSsp(@Parameter(in = ParameterIn.PATH, description = "System security plan id to delete", required=true, schema=@Schema()) @PathVariable("sspId") Long sspId, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="api_key", required=false) String apiKey);


    @Operation(summary = "Finds OSCAL system security plans by name", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OSCALSsp.class)))),

            @ApiResponse(responseCode = "400", description = "Invalid status value") })
    @RequestMapping(value = "/ssps/findByName",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<OSCALSsp>> findSspsByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Terms to search for in system security plan names" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "search-string", required = true) String searchString);


    @Operation(summary = "Find an OSCAL system security plan by ID", description = "Returns a single OSCAL system security plan", security = {
            @SecurityRequirement(name = "api_key")    }, tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = OSCALSsp.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "System security plan not found") })
    @RequestMapping(value = "/ssps/{sspId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<OSCALSsp> getSspById(@Parameter(in = ParameterIn.PATH, description = "ID of system security plan to return", required=true, schema=@Schema()) @PathVariable("sspId") Long sspId);


    @Operation(summary = "Retruns all OSCAL system security plans", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OSCALSsp.class)))) })
    @RequestMapping(value = "/ssps",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<OSCALSsp>> getSsps();


    @Operation(summary = "Update an existing OSCAL system security plan", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "System security plan not found"),

            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @RequestMapping(value = "/ssps",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateSsp(@Parameter(in = ParameterIn.DEFAULT, description = "System security plan object to be updated", required=true, schema=@Schema()) @Valid @RequestBody OSCALSsp body);

}



 */