package oscal.rest.controller;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import oscal.rest.exception.BookNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import oscal.rest.model.OSCALCatalog;
import oscal.rest.repo.CatalogsRepository;

@RestController
@RequestMapping("/oscal/v1")
public class CatalogsApiController {

    @Autowired
    private CatalogsRepository repository;

    @Operation(summary = "Find an OSCAL control catalog by ID", description = "Returns a single OSCAL control catalog", tags={ "OSCAL Catalog" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation =OSCALCatalog.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Catalog not found", content = @Content) })
    @GetMapping("/catalogs/{id}")
    public OSCALCatalog findById(@Parameter(description = "ID of catalog to be returned") @PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }
    @Operation(summary = "Retruns all OSCAL control catalogs", description = "", tags={ "OSCAL Catalog" })
    @GetMapping("/catalogs/")
    public Collection<OSCALCatalog> findCatalogs() {
        return repository.getCatalogs();
    }


    @Operation(summary = "Update an existing OSCAL control catalog", description = "",  tags={ "OSCAL Catalog" })
    @PutMapping("/catalogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OSCALCatalog updateCatalog(@PathVariable("id") final String catalogId, @RequestBody final OSCALCatalog catalog) {
        return catalog;
    }

    @Operation(summary = "Add a new OSCAL control catalog", description = "", tags={ "OSCAL Catalog" })
    @PostMapping("/catalogs")
    @ResponseStatus(HttpStatus.CREATED)
    public OSCALCatalog postCatalog(@NotNull @Valid @RequestBody final OSCALCatalog catalog) {
        repository.add(catalog);
        return catalog;
    }

    @Operation(summary = "Deletes an OSCAL control catalog", description = "", tags={ "OSCAL Catalog" })
    @DeleteMapping("/catalogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCatalog(@PathVariable final String id) {
        return id;
    }

}