package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscalrestservice.exception.RecordNotFoundException;
import com.easydynamics.oscalrestservice.model.OscalParty;
import com.easydynamics.oscalrestservice.repository.PartyRepository;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    OscalParty party = repository.findByUuid(id)
        .orElseThrow(() -> new RecordNotFoundException(
            "Error, Party with specified UUID not found"));
    return new ResponseEntity<OscalParty>(party, HttpStatus.OK);
  }
  /**
   * Defines a GET request for party by type.
   *
   * @param searchByType the party type.
   * @return the party simple object
   */

  @GetMapping("/parties/type")
  public ResponseEntity<List<OscalParty>> findPartyByType(@RequestParam String searchByType) {

    List<OscalParty> results = repository.findByType(searchByType);
    System.out.println(results);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  /**
   * Defines a GET request for party by name.
   *
   * @param searchByName the party name.
   * @return the party simple object
   */

  @GetMapping("/parties/name")
  public ResponseEntity<List<OscalParty>> findPartyByName(@RequestParam String searchByName) {

    List<OscalParty> results = repository.findByName(searchByName);
    System.out.println(results);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  /**
   * Defines a POST request to create a new party.
   *
   * @return the party simple object
   */

  @PostMapping("/parties")
  public ResponseEntity<OscalParty> addParty(@Valid @RequestBody OscalParty party) {

    repository.save(party);
    return new ResponseEntity<OscalParty>(party,HttpStatus.CREATED);
  }
}
