/*
Complex implementation for next sprint



package oscal.rest.function;


import oscal.rest.model.OSCALCatalog;
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
public interface CatalogsApi {

    @Operation(summary = "Add a new OSCAL control catalog", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "405", description = "Invalid input") })
    @RequestMapping(value = "/catalogs",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addCatalog(@Parameter(in = ParameterIn.DEFAULT, description = "OSCAL catalog object to be added", required=true, schema=@Schema()) @Valid @RequestBody OSCALCatalog body);


    @Operation(summary = "Deletes an OSCAL control catalog", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Catalog not found") })
    @RequestMapping(value = "/catalogs/{catalogId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCatalog(@Parameter(in = ParameterIn.PATH, description = "Catalog id to delete", required=true, schema=@Schema()) @PathVariable("catalogId") Long catalogId, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="api_key", required=false) String apiKey);


    @Operation(summary = "Finds OSCAL control catalogs by name", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OSCALCatalog.class)))),

            @ApiResponse(responseCode = "400", description = "Invalid status value") })
    @RequestMapping(value = "/catalogs/findByName",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<OSCALCatalog>> findCatalogsByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Terms to search for in catalog names" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "search-string", required = true) String searchString);

    //Get specific catalog
    @Operation(summary = "Find an OSCAL control catalog by ID", description = "Returns a single OSCAL control catalog", security = {
            @SecurityRequirement(name = "api_key")    }, tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = OSCALCatalog.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Catalog not found") })
    @RequestMapping(value = "/catalogs/{catalogId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<OSCALCatalog> getCatalogById(@Parameter(in = ParameterIn.PATH, description = "ID of catalog to return", required=true, schema=@Schema()) @PathVariable("catalogId") String catalogId);

    //GET all catalogs
    @Operation(summary = "Retruns all OSCAL control catalogs", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OSCALCatalog.class)))) })
    @RequestMapping(value = "/catalogs",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<OSCALCatalog>> getCatalogs();


    @Operation(summary = "Update an existing OSCAL control catalog", description = "", security = {
            @SecurityRequirement(name = "oscal_auth", scopes = {
                    ""        })    }, tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Catalog not found"),

            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @RequestMapping(value = "/catalogs",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateCatalog(@Parameter(in = ParameterIn.DEFAULT, description = "Catalog object to be updated", required=true, schema=@Schema()) @Valid @RequestBody OSCALCatalog body);

}


*/