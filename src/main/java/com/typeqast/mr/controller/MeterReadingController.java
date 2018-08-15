package com.typeqast.mr.controller;

import java.util.HashMap;
import java.util.List;
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

import com.typeqast.mr.constants.ValidationConstants;
import com.typeqast.mr.model.Error;
import com.typeqast.mr.model.MeterReading;
import com.typeqast.mr.model.MeterReadingRequest;
import com.typeqast.mr.model.RestResponse;
import com.typeqast.mr.service.MeterReadingService;
import com.typeqast.mr.validation.MeterReadingRequestValidator;
import com.typeqast.mr.validation.ValidationUtils;

@RestController
public class MeterReadingController {
	private static final Logger logger = LoggerFactory.getLogger(MeterReadingController.class);
	
	@Autowired
	MessageSource messageSource;
	@Autowired 
	MeterReadingService meterReadingService;
	@Autowired
	MeterReadingRequestValidator meterReadingRequestValidator;
	
	/**
	 * Get all meter readings
	 * @param profile
	 * @return
	 */
	@GetMapping(value = "/meter-readings", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> getMeterReadings(){
		logger.info("getMeterReading() START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("meterReadings", meterReadingService.getMeterReading());
		response.setData(data);
		logger.info("getMeterReading() END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Get meter readings for meterId
	 * @param profile
	 * @return
	 */
	@GetMapping(value = "/meter-readings", params = "meterId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> getMeterReadings(@RequestParam String meterId){
		logger.info("getMeterReading(meterId) START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("meterReadings", meterReadingService.getMeterReading(meterId));
		response.setData(data);
		logger.info("getMeterReading(meterId) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Get meter readings for meterId and profile
	 * @param meterId
	 * @param profile
	 * @return
	 */
	@GetMapping(value = "/meter-readings", params = {"meterId", "profile"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> getMeterReadings(@RequestParam String meterId, @RequestParam String profile){
		logger.info("getMeterReading(meterId, profile) START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("meterReadings", meterReadingService.getMeterReading(meterId, profile));
		response.setData(data);
		logger.info("getMeterReading(meterId, profile) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Get meter readings for meterId and profile and month
	 * @param meterId
	 * @param profile
	 * @param month
	 * @return
	 */
	@GetMapping(value = "/meter-readings", params = {"meterId", "profile", "month"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> getMeterReadings(@RequestParam String meterId, @RequestParam String profile, @RequestParam String month){
		logger.info("getMeterReading(meterId, profile) START");
		long time1 = System.currentTimeMillis();
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		data.put("meterReadings", meterReadingService.getMeterReading(meterId, profile, month));
		response.setData(data);
		logger.info("getMeterReading(meterId, profile) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Add meter readings (validate, prepare and save data to database). Return valid data, invalid data and associated errors.
	 * @param meterReadingRequest
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/meter-readings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse> createMeterReadings(@RequestBody MeterReadingRequest meterReadingRequest, BindingResult result){
		logger.info("createMeterReadings START");
		long time1 = System.currentTimeMillis();
		// Validate data
		Map<String, List<MeterReading>> validatedData = meterReadingRequestValidator.validate(meterReadingRequest, result);
		List<MeterReading> validMeterReadingsList = validatedData.get(ValidationConstants.DATA_VALID);
		List<MeterReading> invalidMeterReadingsList = validatedData.get(ValidationConstants.DATA_INVALID);
		
		RestResponse response = new RestResponse();
		Map<String, Object> data = new HashMap<>();
		// Prepare and save valid data and return them to user
		if(!validMeterReadingsList.isEmpty()) {
			meterReadingService.createMeterReading(validMeterReadingsList);
			data.put("validMeterReadings", validMeterReadingsList);
		}
		// Return all invalid data and associated errors to user
		if(!invalidMeterReadingsList.isEmpty()) {
			data.put("invalidMeterReadings", invalidMeterReadingsList);
			response.setError(new Error(HttpStatus.OK, "Validation failed.", ValidationUtils.fromBindingErrors(result)));
		}
		response.setData(data);
		logger.info("createMeterReadings END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
		
	/**
	 * Delete meter readings by meterId
	 * @param meterId
	 * @return
	 */
	@DeleteMapping(value = "/meter-readings/{meterId}")
	public ResponseEntity<RestResponse> deleteMeterReading(@PathVariable String meterId){
		logger.info("deleteMeterReading(meterId) START");
		long time1 = System.currentTimeMillis();
		meterReadingService.deleteMeterReading(meterId);
		logger.info("deleteMeterReading(meterId) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Delete meter readings by meterId and profile
	 * @param meterId
	 * @param profile
	 * @return
	 */
	@DeleteMapping(value = "/meter-readings/{meterId}/{profile}")
	public ResponseEntity<RestResponse> deleteMeterReading(@PathVariable String meterId, @PathVariable String profile){
		logger.info("deleteMeterReading(meterId, profile) START");
		long time1 = System.currentTimeMillis();
		meterReadingService.deleteMeterReading(meterId, profile);
		logger.info("deleteMeterReading(meterId, profile) END (duration: {}ms)", System.currentTimeMillis() - time1);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}