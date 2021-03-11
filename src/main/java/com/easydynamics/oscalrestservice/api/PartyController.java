package com.easydynamics.oscalrestservice.api;


import com.easydynamics.oscalrestservice.model.OscalParty;
import com.easydynamics.oscalrestservice.repository.PartyRepository;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Party Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class PartyController {

  @Autowired
  private PartyRepository repository;

  /**
   * Defines a POST request to create a new party.
   *
   * @return the party simple object
   */

  @PostMapping("/parties")
  public ResponseEntity<OscalParty> addParty(@Valid @RequestBody OscalParty party) {

    repository.save(party);
    return ResponseEntity.ok(party);
  }

  /**
   * Defines a GET request to return all parties.
   *
   * @return all parties
   */

  @GetMapping("/parties")
  public ResponseEntity<List<OscalParty>> findAllParties() {

    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
  }

  /**
   * Defines a GET request for party by ID.
   *
   * @param id the party uuid.
   * @return the party simple object
   */

  @GetMapping("/parties/{id}")
  public ResponseEntity<OscalParty> findById(@Parameter @PathVariable String id) {
    if (repository.findByUuid(id) != null) {
      return new ResponseEntity<OscalParty>(repository.findByUuid(id), HttpStatus.OK);
    } else {
      return new ResponseEntity<OscalParty>(HttpStatus.NOT_FOUND);
    }
  }
}
