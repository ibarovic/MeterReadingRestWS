package com.typeqast.mr.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ValidationUtils {
	
	public static final String[] monthList = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	public static final Set<String> monthSet = new HashSet<>(Arrays.asList(monthList));
	
	/**
	 * No instantiation.
	 */
	private ValidationUtils(){}
	
    public static boolean isReadingValid(Integer currentMonthReading, Integer previousMonthReading) {
    	if(currentMonthReading.compareTo(previousMonthReading) == -1) {
			return false;
		}
    	return true;
    }
    
    
    public static List<String> fromBindingErrors(BindingResult result) {
    	List<String> errors = new ArrayList<>();
    	for (ObjectError objectError : result.getAllErrors()) {
    		errors.add(objectError.getDefaultMessage());
		}
    	return errors;
    }
}
