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
import oscal.rest.model.OSCALSSP;
import oscal.rest.repo.CatalogsRepository;
import oscal.rest.repo.SSPRepository;

@RestController
@RequestMapping("/oscal/v1")
public class SSPApiController {

    @Autowired
    private SSPRepository repository;

    //specific ssp
    @Operation(summary = "Find an OSCAL system security plan by ID", description = "Returns a single OSCAL system security plan", tags={ "OSCAL System Security Plan" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation =OSCALSSP.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "System security plan not found", content = @Content) })
    @GetMapping("/ssps/{sspId}")
    public OSCALSSP findById(@Parameter(description = "ID of system security plan to be returned") @PathVariable String sspId) {
        return repository.findById(sspId)
                .orElseThrow(BookNotFoundException::new);
    }

    //get all ssps
    @Operation(summary = "Returns all OSCAL system security plans", description = "", tags={ "OSCAL System Security Plan" })
    @GetMapping("/ssps")
    public Collection<OSCALSSP> findSSPs() {
        return repository.getSsps();
    }


    //make new ssp
    @Operation(summary = "Add a new OSCAL system security plan", description = "", tags={ "OSCAL System Security Plan" })
    @PostMapping("/ssps")
    @ResponseStatus(HttpStatus.CREATED)
    public OSCALSSP postSSP(@NotNull @Valid @RequestBody final OSCALSSP ssp) {
        repository.add(ssp);
        return ssp;
    }



}