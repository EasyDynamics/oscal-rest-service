package com.easydynamics.oscalrestservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;

/**
 * Dummy controller class to verify REST requests and CORS settings.
 * 
 * TODO - Delete me
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class DummyController {

	/**
	 * Defines a dummy get request for catalog by ID
	 * 
	 * @param id 
	 * @return the dummy catalog response
	 */
	@GetMapping("/catalogs/{id}")
    public ResponseEntity<String> findById(@Parameter @PathVariable String id) {
		return new ResponseEntity<String>("{ \"value\": \"coming soon\" }", HttpStatus.OK);
    }
	
}
