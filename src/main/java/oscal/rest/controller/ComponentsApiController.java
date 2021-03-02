package oscal.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import oscal.rest.exception.BookNotFoundException;
import oscal.rest.model.OSCALComponent;
import oscal.rest.repo.ComponentRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/oscal/v1")
public class ComponentsApiController {

    @Autowired
    private ComponentRepository repository;

    //specific component
    @Operation(summary = "Find an OSCAL component by ID", description = "Returns a single OSCAL component", tags={ "OSCAL Component" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation =OSCALComponent.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Component not found", content = @Content) })
    @GetMapping("/components/{componentId}")
    public OSCALComponent findById(@Parameter(description = "ID of component to be returned") @PathVariable String componentId) {
        return repository.findById(componentId)
                .orElseThrow(BookNotFoundException::new);
    }

    //get all components
    @Operation(summary = "Returns all OSCAL components", description = "", tags={ "OSCAL Component" })
    @GetMapping("/components")
    public Collection<OSCALComponent> findComponentss() {
        return repository.getComponents();
    }


    //make new component
    @Operation(summary = "Add a new OSCAL component", description = "", tags={ "OSCAL Component" })
    @PostMapping("/components")
    @ResponseStatus(HttpStatus.CREATED)
    public OSCALComponent postComponents(@NotNull @Valid @RequestBody final OSCALComponent component) {
        repository.add(component);
        return component;
    }



}