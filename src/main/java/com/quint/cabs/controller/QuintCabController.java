/**
 * 
 */
package com.quint.cabs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quint.cabs.beans.Client;
import com.quint.cabs.beans.RideDetails;
import com.quint.cabs.service.QuintCabsService;
import com.quint.cabs.utils.QuintCabsResponse;

/**
 * @author LENOVO
 *
 */
@RestController
public class QuintCabController {
	@Autowired
	private QuintCabsService quintCabsService;

	private static final Logger logger = LogManager
			.getLogger(QuintCabController.class);

	@PostMapping(value = "/get-cab", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuintCabsResponse<RideDetails>> processGetCabRequest(@RequestBody final Client client) {
		logger.info("Fetching cab for client - ",client.getClientId());
		QuintCabsResponse<RideDetails> response = new QuintCabsResponse<>();
		try {
			if(client!= null) {
				RideDetails ride = quintCabsService.processRequest(client);
				response.setResponse(ride);
				if(ride != null) {					
					response.setStatus(700);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}else {
					response.setStatus(705);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			}else {
				response.setResponse(null);
				response.setStatus(500);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
	}catch(Exception e) {
			logger.error(e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Bad Request",e);
		}
	}
	
	@PostMapping(value = "/start-ride",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuintCabsResponse<String>> processStartRideRequest (final RideDetails ride) {
		logger.info("Start Ride  - ",ride.getCabId() );
		QuintCabsResponse<String> response = new QuintCabsResponse<>();
		try {
			if(ride!= null) {
				this.quintCabsService.startRide(ride);
				if(ride != null) {
					response.setResponse("Started");
					response.setStatus(700);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}else {
					response.setResponse("Ride not found");
					response.setStatus(705);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			}else {
				response.setResponse("");
				response.setStatus(500);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
	}catch(Exception e) {
			logger.error(e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Bad Request",e);
		}
	}

}
