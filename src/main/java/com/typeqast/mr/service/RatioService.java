package com.typeqast.mr.service;

import java.util.List;

import com.typeqast.mr.model.Ratio;

public interface RatioService {
	
	/**
	 * Get all ratios
	 * @return list of ratios 
	 */
	public List<Ratio> getRatios();
	/**
	 * Get all ratios by profile
	 * @param profile
	 * @return list of ratios 
	 */
	public List<Ratio> getRatios(String profile);
	/**
	 * Get all ratios by profile and month
	 * @param profile
	 * @param month
	 * @return list of ratios 
	 */
	public List<Ratio> getRatios(String profile, String month);
	/**
	 * Method that inserts ratios to database, batch update, transactional
	 * @param ratios
	 */
	public void createRatios(List<Ratio> ratios);
	/**
	 * Delete all ratios
	 */
	public void deleteRatio();
	/**
	 * Delete ratios by profile
	 * @param profile
	 */
	public void deleteRatio(String profile);
	/**
	 * Check if ratio exists by profile 
	 * @param profile
	 * @return
	 */
	public boolean isRatiosExist(String profile);
}
