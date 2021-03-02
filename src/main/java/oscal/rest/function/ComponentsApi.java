/*
    Swagger codegen of the API function, will be implemented when reading requests to objects next sprint

package io.swagger.api;

import io.swagger.model.OSCALComponent;
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
public interface ComponentsApi {

    @Operation(summary = "Add a new OSCAL component", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "405", description = "Invalid input") })
    @RequestMapping(value = "/components",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addComponent(@Parameter(in = ParameterIn.DEFAULT, description = "OSCAL components to be added", required=true, schema=@Schema()) @Valid @RequestBody OSCALComponent body);


    @Operation(summary = "Deletes an OSCAL component", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Component not found") })
    @RequestMapping(value = "/components/{componentId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteComponent(@Parameter(in = ParameterIn.PATH, description = "Component id to delete", required=true, schema=@Schema()) @PathVariable("componentId") Long componentId, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="api_key", required=false) String apiKey);


    @Operation(summary = "Finds OSCAL components by name", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OSCALComponent.class)))),

            @ApiResponse(responseCode = "400", description = "Invalid status value") })
    @RequestMapping(value = "/components/findByName",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<OSCALComponent>> findComponentsByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Terms to search for in component names" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "search-string", required = true) String searchString);


    @Operation(summary = "Find an OSCAL component by ID", description = "Returns a single OSCAL component", security = {
            @SecurityRequirement(name = "api_key")    }, tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = OSCALComponent.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "cCmponent not found") })
    @RequestMapping(value = "/components/{componentId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<OSCALComponent> getComponentById(@Parameter(in = ParameterIn.PATH, description = "ID of component to return", required=true, schema=@Schema()) @PathVariable("componentId") Long componentId);


    @Operation(summary = "Retruns all OSCAL components", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OSCALComponent.class)))) })
    @RequestMapping(value = "/components",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<OSCALComponent>> getComponents();


    @Operation(summary = "Update an existing OSCAL component", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Component not found"),

            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @RequestMapping(value = "/components",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateComponent(@Parameter(in = ParameterIn.DEFAULT, description = "Component object to be updated", required=true, schema=@Schema()) @Valid @RequestBody OSCALComponent body);

}

*/