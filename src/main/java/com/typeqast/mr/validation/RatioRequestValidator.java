package com.typeqast.mr.validation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.typeqast.mr.model.Ratio;
import com.typeqast.mr.model.RatiosRequest;
import com.typeqast.mr.service.RatioService;

@Component
public class RatioRequestValidator implements Validator{

	private static final Logger logger = LoggerFactory.getLogger(RatioRequestValidator.class);
	
	@Autowired
	MessageSource messageSource;
	@Autowired
	RatioService ratioService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RatiosRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("Validating RatiosRequest data");
		RatiosRequest ratioRequest = (RatiosRequest) target;
		List<Ratio> ratiosList = (List<Ratio>) ratioRequest.getRatios();
		
		if(ratiosList.isEmpty()) {
			errors.reject("ratioRequestValidator.validation.001", messageSource.getMessage("ratioRequestValidator.validation.001", null, null));
			return;
		}
		
		// Group all ratios per profiles and do validation
		Map<String, List<Ratio>> profileToRatios = ratiosList.stream().collect(Collectors.groupingBy(Ratio::getProfile));
		profileToRatios.forEach((profile, ratios) -> {			
			boolean profileValid = this.validateProfile(profile, errors);
	    	boolean monthsValid = this.validateMonths(profile, ratios, errors);
	    	if(profileValid && monthsValid) {
		    	// Validate ratios only if there are no error for profile and we have data for all months
	    		this.validateRatioSum(profile, ratios, errors);
	    	}
		});
	}
	
	private boolean validateProfile(String profile, Errors errors) {
		logger.debug("Validating data -> profile [{}]", profile);			
		boolean valid = true;
		// validate profile, length for now
    	if(profile.length() != 1) { 
    		errors.reject("ratioRequestValidator.validation.002", messageSource.getMessage("ratioRequestValidator.validation.002", new Object[]{profile}, null));
    		valid = false;
    	}
    	if(valid) {
			//check if data all ready exists for profile
	    	if(ratioService.isRatiosExist(profile)) {
	    		errors.reject("ratioRequestValidator.validation.003", messageSource.getMessage("ratioRequestValidator.validation.003", new Object[]{profile}, null));
	    		valid = false;
	    	}
    	}
    	return valid;
	}
	
	private boolean validateMonths(String profile, List<Ratio> ratios, Errors errors) {
    	// Get all months for profile and do validation
	    List<String> months = ratios.stream().collect(Collectors.mapping(Ratio::getMonth, Collectors.toList()));
		logger.debug("Validating data -> months [{}] for profile [{}]", months, profile);			
		boolean valid = true;
		// Must be 12 months per profile
    	if(months.size() != 12) {
    		errors.reject("ratioRequestValidator.validation.004",  messageSource.getMessage("ratioRequestValidator.validation.004", new Object[]{profile}, null));
    		valid = false;
    	}
    	// Remove possible duplicate months
    	Set<String> monthsSetTmp = months.stream().collect(Collectors.toSet());
    	// and do validation
    	if(!monthsSetTmp.containsAll(ValidationUtils.monthSet)){
    		errors.reject("ratioRequestValidator.validation.005",  messageSource.getMessage("ratioRequestValidator.validation.005", new Object[]{profile}, null));
    		valid = false;
    	}
    	return valid;
	}

	private boolean validateRatioSum(String profile, List<Ratio> ratios, Errors errors) {
	    // Sum all ratios per months for profile and do validation
	    Double ratiosSum = ratios.stream().collect(Collectors.summingDouble(Ratio::getRatio));
		logger.debug("Validating data -> ratiosSum [{}] for profile [{}]", ratiosSum, profile);	
		boolean valid = true;

    	if(ratiosSum.compareTo(new Double(1)) != 0)  {
    		errors.reject("ratioRequestValidator.validation.001",  messageSource.getMessage("ratioRequestValidator.validation.006", new Object[]{profile,  String.format( "%.2f", ratiosSum )}, null));
    		valid = false;
    	}	
    	return valid; 
	}
}
