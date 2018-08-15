package com.typeqast.mr.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.typeqast.mr.constants.ValidationConstants;
import com.typeqast.mr.model.MeterReading;
import com.typeqast.mr.model.MeterReadingRequest;
import com.typeqast.mr.service.MeterReadingService;
import com.typeqast.mr.service.RatioService;

@Component
public class MeterReadingRequestValidator{

	private static final Logger logger = LoggerFactory.getLogger(MeterReadingRequestValidator.class);
	
	@Autowired
	MessageSource messageSource;
	@Autowired
	MeterReadingService meterReadingService;
	@Autowired
	RatioService ratioService;
	

	public Map<String, List<MeterReading>> validate(Object target, Errors errors) {
		logger.debug("Validating MeterReadingRequest");
		MeterReadingRequest meterReadingRequest = (MeterReadingRequest) target;
		List<MeterReading> meterReadingList = (List<MeterReading>) meterReadingRequest.getMeterReadings();
				
		if(meterReadingList.isEmpty()) {
			errors.reject("meterReadingRequestValidator.validation.001", messageSource.getMessage("meterReadingRequestValidator.validation.001", null, null));
		}
		

		Map<String, List<MeterReading>> validatedData = new HashMap<>();

		if(!errors.hasErrors()) {
			List<MeterReading> validMeterReadingsList = new ArrayList<>();
			List<MeterReading> invalidMeterReadingsList = new ArrayList<>();

			// Group all meter readings by meter and profile
			Map<String, Map<String, List<MeterReading>>> meterToProfileToReadings = meterReadingList
													.stream()
													.collect(Collectors.groupingBy(MeterReading::getMeterId, Collectors.groupingBy(MeterReading::getProfile)));
			logger.debug("Grouping all meter readings by meter and profile and atempting to validate data: [{}]", meterToProfileToReadings);
			meterToProfileToReadings.forEach((meterId, profileToReadings) -> {
				boolean meterIdValid = this.validateMeterId(meterId, errors);
				for (Entry<String, List<MeterReading>> profileToReadingsEntry : profileToReadings.entrySet()) {
					String profile = profileToReadingsEntry.getKey();
					List<MeterReading> readings = profileToReadingsEntry.getValue();
					
					boolean profileValid = this.validateProfile(meterId, profile, errors);
					boolean monthsValid = this.validateMonths(meterId, profile, readings, errors);
					boolean readingsValid = false;
					if(meterIdValid && profileValid && monthsValid) {
				    	// Validate readings only if there are no error for meterid and profile and we have data for all months
						readingsValid = this.validateReadings(meterId, profile, readings, errors);
					}
					
					if(meterIdValid && profileValid && monthsValid && readingsValid) {
						logger.debug("VALID -> [{}]", readings);			
						validMeterReadingsList.addAll(readings);
					}else {
						logger.debug("INVALID -> [{}]", readings);			
						invalidMeterReadingsList.addAll(readings);
					}
				}
			});
			validatedData.put(ValidationConstants.DATA_VALID, validMeterReadingsList);
			validatedData.put(ValidationConstants.DATA_INVALID, invalidMeterReadingsList);
		}
		return validatedData;
	}
	
	private boolean validateMeterId(String meterId, Errors errors) {
		logger.debug("Validating data -> meterId [{}]", meterId);			
		boolean valid = true;
		// Validate meterID, isNumber, is between 0 and 9999 
		if(NumberUtils.isCreatable(meterId) == false) {
			errors.reject("meterReadingRequestValidator.validation.002", messageSource.getMessage("meterReadingRequestValidator.validation.002", new Object[]{meterId}, null));
			valid = false;
		}
		if(valid) {
			Integer meterIdInteger = Integer.parseInt(meterId);
			if(meterIdInteger < 0 || meterIdInteger > 9999) { 
				errors.reject("meterReadingRequestValidator.validation.003", messageSource.getMessage("meterReadingRequestValidator.validation.003", new Object[]{meterId}, null));
				valid = false;
			}
		}
		return valid;
	}
	
	private boolean validateProfile(String meterId, String profile, Errors errors) {
		logger.debug("Validating data -> profile [{}] for meterId [{}]", profile, meterId);			
		boolean valid = true;
		// Validate if data for meter and profile already exists in database
		if(meterReadingService.isMeterReadingExist(meterId, profile) == true) {
			errors.reject("meterReadingRequestValidator.validation.004", messageSource.getMessage("meterReadingRequestValidator.validation.004", new Object[]{meterId, profile}, null));
			valid = false;
		}
		
		if(valid) {
			// Validate if data for ratios of a profile exists in database, but only if data for meter and profile in question does not exist in database, because then maybe data for profile ratios does not exist 
			if(ratioService.isRatiosExist(profile) == false) {
				errors.reject("meterReadingRequestValidator.validation.005", messageSource.getMessage("meterReadingRequestValidator.validation.005", new Object[]{meterId, profile}, null));
				valid = false;
			}
		}
		return valid;
	}
	
	private boolean validateMonths(String meterId, String profile, List<MeterReading> readings, Errors errors) {
		// Get all months for a profile and do validation
	    List<String> months = readings.stream().collect(Collectors.mapping(MeterReading::getMonth, Collectors.toList()));	
		logger.debug("Validating data -> months [{}] for meterId [{}] and profile [{}]", months, meterId, profile);
		boolean valid = true;
		// Must be 12 months per profile
    	if(months.size() != 12) {
    		errors.reject("meterReadingRequestValidator.validation.006",  messageSource.getMessage("meterReadingRequestValidator.validation.006", new Object[]{meterId, profile}, null));
    		valid = false;
    	}
    	// Remove possible duplicate months
    	Set<String> monthsSetTmp = months.stream().collect(Collectors.toSet());
    	// and do validation
    	if(!monthsSetTmp.containsAll(ValidationUtils.monthSet)){
    		errors.reject("meterReadingRequestValidator.validation.007",  messageSource.getMessage("meterReadingRequestValidator.validation.007", new Object[]{meterId, profile}, null));
    		valid = false;
    	}
    	return valid;
	}

	private boolean validateReadings(String meterId, String profile, List<MeterReading> readings, Errors errors) {
		// Group all readings per month and do validation
	    Map<String, List<MeterReading>> monthToReadings = readings.stream().collect(Collectors.groupingBy(MeterReading::getMonth));		
		logger.debug("Validating data -> readings [{}] for meterId [{}] and profile [{}]", readings, meterId, profile);
		boolean valid = true;
		
		Integer readingJAN = monthToReadings.get("JAN").get(0).getReading();
		Integer readingFEB = monthToReadings.get("FEB").get(0).getReading();
		Integer readingMAR = monthToReadings.get("MAR").get(0).getReading();
		Integer readingAPR = monthToReadings.get("APR").get(0).getReading();
		Integer readingMAY = monthToReadings.get("MAY").get(0).getReading();
		Integer readingJUN = monthToReadings.get("JUN").get(0).getReading();
		Integer readingJUL = monthToReadings.get("JUL").get(0).getReading();
		Integer readingAUG = monthToReadings.get("AUG").get(0).getReading();
		Integer readingSEP = monthToReadings.get("SEP").get(0).getReading();
		Integer readingOCT = monthToReadings.get("OCT").get(0).getReading();
		Integer readingNOV = monthToReadings.get("NOV").get(0).getReading();
		Integer readingDEC = monthToReadings.get("DEC").get(0).getReading();
		
		
		if(ValidationUtils.isReadingValid(readingFEB, readingJAN) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "FEB", readingFEB, "JAN", readingJAN}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingMAR, readingFEB) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "MAR", readingMAR, "FEB", readingFEB}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingAPR, readingMAR) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "APR", readingAPR, "MAR", readingMAR}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingMAY, readingAPR) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "MAY", readingMAY, "APR", readingAPR}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingJUN, readingMAY) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "JUN", readingJUN, "MAY", readingMAY}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingJUL, readingJUN) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "JUL", readingJUL, "JUN", readingJUN}, null));	
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingAUG, readingJUL) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "AUG", readingAUG, "JUL", readingJUL}, null));	
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingSEP, readingAUG) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "SEP", readingSEP, "AUG", readingAUG}, null));	
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingOCT, readingSEP) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "OCT", readingOCT, "SEP", readingSEP}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingNOV, readingOCT) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "NOV", readingNOV, "OCT", readingOCT}, null));
    		valid = false;
		}
		if(ValidationUtils.isReadingValid(readingDEC, readingNOV) == false) {
    		errors.reject("meterReadingRequestValidator.validation.008",  messageSource.getMessage("meterReadingRequestValidator.validation.008", new Object[]{meterId, profile, "DEC", readingDEC, "NOV", readingNOV}, null));
    		valid = false;
		}
		return valid;
	}
}
