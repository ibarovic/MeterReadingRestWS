package com.typeqast.mr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.typeqast.mr.model.Error;
import com.typeqast.mr.model.RatiosRequest;
import com.typeqast.mr.model.RestResponse;
import com.typeqast.mr.service.RatioService;
import com.typeqast.mr.validation.RatioRequestValidator;
import com.typeqast.mr.validation.ValidationUtils;

@RestController
public class RatioController {
	
	private static final Logger logger = LoggerFactory.getLogger(RatioController.class);
	
	@Autowired
	RatioService ratioService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	RatioRequestValidator ratioRequestValidator;
	

	/**
	 * Get all ratios
	 * @param profile
	 * @return
	 */
	@GetMapping(value = "/ratios", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Object> getRatios(){
		logger.info("getRatios START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("ratios", ratioService.getRatios());
		response.setData(data);
		logger.info("getRatios END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Get ratios for profile
	 * @param profile
	 * @return
	 */
	@GetMapping(value = "/ratios", params = "profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRatios(@RequestParam String profile){
		logger.info("getRatios(profile) START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("ratios", ratioService.getRatios(profile));
		response.setData(data);
		logger.info("getRatios(profile) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Get ratios for profile for specific month
	 * @param month
	 * @param profile
	 * @return
	 */
	@GetMapping(value = "/ratios", params = {"profile", "month"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRatios(@RequestParam String profile, @RequestParam String month){
		logger.info("getRatios(profile, month) START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("ratios", ratioService.getRatios(profile, month));
		response.setData(data);
		logger.info("getRatios(profile, month) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Create ratios for profiles
	 * @param ratioRequest
	 * @param pHttpRequest
	 * @return
	 */
	@PostMapping(value = "/ratios", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Object> createRatio(@RequestBody RatiosRequest ratioRequest, BindingResult result){
		logger.info("createRatio START");
		long time1 = System.currentTimeMillis();
		ratioRequestValidator.validate(ratioRequest, result);
		if(result.hasErrors()) {
			RestResponse tResponse = new RestResponse();
			tResponse.setError(new Error(HttpStatus.BAD_REQUEST, "Validation failed.", ValidationUtils.fromBindingErrors(result)));
			return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
		}
		ratioService.createRatios(ratioRequest.getRatios());
		logger.info("createRatio END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * Delete ratios per profile
	 * @param profile
	 * @param pHttpRequest
	 * @return
	 */
	@DeleteMapping(value = "/ratios/{profile}")
	public ResponseEntity<Object> deleteRatio(@PathVariable String profile){
		logger.info("deleteRatio START");
		long time1 = System.currentTimeMillis();
		ratioService.deleteRatio(profile);
		logger.info("deleteRatio END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
