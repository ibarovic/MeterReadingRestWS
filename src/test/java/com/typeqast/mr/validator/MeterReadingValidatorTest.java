package com.typeqast.mr.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import com.typeqast.mr.model.MeterReading;
import com.typeqast.mr.validation.MeterReadingRequestValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeterReadingValidatorTest {
	
	
	List<MeterReading> meterReadings;
	
	@Autowired
	MeterReadingRequestValidator MeterReadingValidator;
	
    @Before
    public void setup() {

    	meterReadings = new ArrayList<>();
    	//meterReadings.add(new MeterReading(1, "C", "JAN", new Integer(1), null));	 
    	//meterReadings.add(new MeterReading(1, "B", "DEC", new Integer(1), null));	
    	//meterReadings.add(new MeterReading(1, "B", "FEB", new Integer(1), null));	
    	//meterReadings.add(new MeterReading(1, "A", "JAN", new Integer(12), null));	 
        meterReadings.add(new MeterReading("0001", "A", "JAN", new Integer(11), null));
        meterReadings.add(new MeterReading("0001", "A", "FEB", new Integer(10), null));
        meterReadings.add(new MeterReading("0001", "A", "MAR", new Integer(9), null));
        meterReadings.add(new MeterReading("0001", "A", "APR", new Integer(8), null));
        meterReadings.add(new MeterReading("0001", "A", "MAY", new Integer(7), null));
        meterReadings.add(new MeterReading("0001", "A", "JUN", new Integer(6), null));
        meterReadings.add(new MeterReading("0001", "A", "JUL", new Integer(5), null));
        meterReadings.add(new MeterReading("0001", "A", "AUG", new Integer(4), null));
        meterReadings.add(new MeterReading("0001", "A", "SEP", new Integer(3), null));
        meterReadings.add(new MeterReading("0001", "A", "OCT", new Integer(2), null));
        meterReadings.add(new MeterReading("0001", "A", "NOV", new Integer(1), null));
        meterReadings.add(new MeterReading("0001", "A", "DEC", new Integer(0), null));

    }
    
    @Test
    public void collection_group_by() {
    	
//    	Map<Integer, Map<String, List<MeterReading>>> map = meterReadings.stream()
//    	.collect(Collectors.groupingBy(MeterReading::getMeterID, Collectors.groupingBy(MeterReading::getProfile)));
//    	
//    	System.out.println(map);
//    	
//     	Map<Integer, Map<String, List<String>>> map2 = meterReadings.stream()
//     	    	.collect(Collectors.groupingBy(MeterReading::getMeterID, Collectors.groupingBy(MeterReading::getProfile, Collectors.mapping(MeterReading::getMonth, Collectors.toList()))));
//    
//    	System.out.println(map2);
//    	
//    
     	Map<String, Map<String, Map<String, List<MeterReading>>>> map3 =
     	meterReadings.stream()
     	    	.collect(Collectors.groupingBy(MeterReading::getMeterId, Collectors.groupingBy(MeterReading::getProfile, Collectors.groupingBy(MeterReading::getMonth))));
    	
    	//System.out.println(map3);
    	
    	map3.forEach((meter, profiles)->{
    		System.out.println("meter " + meter + " profiles " + profiles);
    		System.out.println();
    		profiles.forEach((profile, months)->{
        		System.out.println("profile " + profile + " months " + months);
        		System.out.println();
        		
        		Integer readingJAN = months.get("JAN").get(0).getReading();
        		Integer readingFEB = months.get("FEB").get(0).getReading();
        		Integer readingMAR = months.get("MAR").get(0).getReading();
        		Integer readingAPR = months.get("APR").get(0).getReading();
        		Integer readingMAY = months.get("MAY").get(0).getReading();
        		Integer readingJUN = months.get("JUN").get(0).getReading();
        		Integer readingJUL = months.get("JUL").get(0).getReading();
        		Integer readingAUG = months.get("AUG").get(0).getReading();
        		Integer readingSEP = months.get("SEP").get(0).getReading();
        		Integer readingOCT = months.get("OCT").get(0).getReading();
        		Integer readingNOV = months.get("NOV").get(0).getReading();
        		Integer readingDEC = months.get("DEC").get(0).getReading();

        		
        		validateReading(readingFEB, readingJAN);
        		validateReading(readingMAR, readingFEB);
        		validateReading(readingAPR, readingMAR);
        		validateReading(readingMAY, readingAPR);
        		validateReading(readingJUN, readingMAY);
        		validateReading(readingJUL, readingJUN);
        		validateReading(readingAUG, readingJUL);
        		validateReading(readingSEP, readingAUG);
        		validateReading(readingOCT, readingSEP);
        		validateReading(readingNOV, readingOCT);
        		validateReading(readingDEC, readingNOV);
        		
        		months.forEach((month, readings)->{
            		System.out.println("month " + month + " readings " + readings);
            		System.out.println();        		
            	});
        		
    		});
    		
    	});
	
    }
    
    @Test
    public void validate_test() {
    	Errors errors = null;
    	MeterReadingValidator.validate(meterReadings, errors);
    }
    
    private void validateReading(Integer currentMonthReading, Integer previousMonthReading) {
    	if(currentMonthReading.compareTo(previousMonthReading) == -1) {
			System.out.println("Rise exception");
		}
    }

}
