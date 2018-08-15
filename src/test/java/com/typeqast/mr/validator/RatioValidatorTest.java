package com.typeqast.mr.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.typeqast.mr.model.Ratio;

public class RatioValidatorTest {

	   List<Ratio> ratios;

	    @Before
	    public void setup() {

	        ratios = new ArrayList<>();
	        ratios.add(new Ratio("JAN","C", new Double(1)));	 
	        ratios.add(new Ratio("DEC","B", new Double(1)));	
	        ratios.add(new Ratio("FEB","B", new Double(1)));	
	        ratios.add(new Ratio("JAN","A", new Double(1)));	 
	        ratios.add(new Ratio("JAN","A", new Double(1)));
	        ratios.add(new Ratio("FEB","A", new Double(1)));
	        ratios.add(new Ratio("MAR","A", new Double(1)));
	        ratios.add(new Ratio("APR","A", new Double(1)));
	        ratios.add(new Ratio("MAY","A", new Double(1)));
	        ratios.add(new Ratio("JUN","A", new Double(1)));
	        ratios.add(new Ratio("JUL","A", new Double(1)));
	        ratios.add(new Ratio("AUG","A", new Double(1)));
	        ratios.add(new Ratio("SEP","A", new Double(1)));
	        ratios.add(new Ratio("OCT","A", new Double(1)));
	        ratios.add(new Ratio("NOV","A", new Double(1)));
	        ratios.add(new Ratio("DEC","A", new Double(1)));


	        
	        
	        // STREAMS 
	        // sorted
	        // filter
	        // allMatch
	        // anyMatch
	        
	        // collect
	        // map
	    }
	    
	    @Test
	    public void single_sort() {

	    	Comparator<Ratio> byProfile = (Ratio r1, Ratio r2) -> r1.getProfile().compareTo(r2.getProfile());
	    	
	    	ratios
	                .stream()
	                .sorted(byProfile)
	                .forEach(e -> System.out.println(e));
	    }
	    
	    
	    @Test
	    public void multi_sort() {

	    	Comparator<Ratio> byProfile = (Ratio r1, Ratio r2) -> r1.getProfile().compareTo(r2.getProfile());
	    	Comparator<Ratio> byMonth = (Ratio r1, Ratio r2) -> r1.getMonth().compareTo(r2.getMonth());

	    	ratios
	                .stream()
	                .sorted(byProfile.thenComparing(byMonth))
	                .forEach(e -> System.out.println(e));
	    }
	    
	    @Test
	    public void month_sort() {

	    	
	    	Comparator<Ratio> sortByProfile = (Ratio r1, Ratio r2) -> r1.getProfile().compareTo(r2.getProfile());
	    	Comparator<Ratio> sortByMonth = (Ratio r1, Ratio r2) -> r1.getMonth().compareTo(r2.getMonth());

	    	ratios
	                .stream()
	                .sorted(sortByProfile.thenComparing(sortByMonth))
	                .forEach(e -> System.out.println(e));
	    }
	    
	    
	    @Test
	    public void single_filter() {

		    Predicate<Ratio> filterByProfile = (Ratio r) -> "A".equals(r.getProfile());

	    	ratios
	                .stream()
	                .filter(filterByProfile)
	                .forEach(e -> System.out.println(e));
	    }
	    

	    
	    @Test
	    public void multiple_filter() {

		    Predicate<Ratio> filterByProfile = (Ratio r) -> "A".equals(r.getProfile());
		    Predicate<Ratio> filterByMonth = (Ratio r) -> "DEC".equals(r.getMonth());

	    	List<Ratio> ratiosTmp =  ratios
	                .stream()
	                .filter(filterByProfile.and(filterByMonth))
	                .collect(Collectors.toList());
	    	
	    	System.out.println(ratiosTmp);
	    	
	    }
	    
	    @Test
	    public void collection_group_by() {
	    	
	    	// Collection grouping by profile
	    	Map<String, List<Ratio>> map1 = ratios.stream().collect(Collectors.groupingBy(Ratio::getProfile));	
		    System.out.println(map1);
		    System.out.println();
		    
		    // Collection grouping by profile and counting profiles
		    Map<String, Long> map2 = ratios.stream().collect(Collectors.groupingBy(Ratio::getProfile, Collectors.counting()));
		    System.out.println(map2);
		    System.out.println();

		    
		    // Collection grouping by profile and months and counting months 
		    Map<String, Map<String, Long>> map3 = ratios.stream().collect(Collectors.groupingBy(Ratio::getProfile, Collectors.groupingBy(Ratio::getMonth, Collectors.counting())));
		    System.out.println(map3);
		    System.out.println();
		    
		    Map<String, Double> map4 = ratios.stream().collect(Collectors.groupingBy(Ratio::getProfile, Collectors.summingDouble(Ratio::getRatio)));
		    map4.forEach((key, value)->{
		    	System.out.println("Key: " + key + " Value: " + value);
		    	if(value != 1) {
		    		System.out.println("Rise exception");
		    	}
		    });
		    
		    System.out.println(map4);
		    System.out.println();

		    String[] monthList = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
		    Set<String> monthSet = new HashSet<>(Arrays.asList(monthList));
	
		    
		    // Get all months 
		    Map<String, Set<String>> map6 = ratios.stream().collect(Collectors.groupingBy(Ratio::getProfile, Collectors.mapping(Ratio::getMonth, Collectors.toSet())));
		    map6.forEach((profile, months)->{
		    	System.out.println("Key: " + profile + " Value: " + months);
		    	if(months.size() != 12) {
		    		System.out.println("Rise exception - size");
		    	}
		    	
		    	if(!months.containsAll(monthSet)){
		    		System.out.println("Rise exception - months");
		    	}
		    });
		    
		    System.out.println(map6);
		    System.out.println();

	    
	    }

	
}
