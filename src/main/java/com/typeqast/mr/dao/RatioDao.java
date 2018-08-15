package com.typeqast.mr.dao;

import java.util.List;

import com.typeqast.mr.model.Ratio;

public interface RatioDao {
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
	public List<Ratio> getRatios( String profile, String month);
	/**
	 * Insert ratio
	 * @param month
	 * @param profile
	 * @param ratio
	 */
	public void insertRatio(String month, String profile, Double ratio);
	/**
	 * Insert ratios, batch update
	 * @param ratios
	 */
	public void insertRatios(List<Ratio> ratios);
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
	 * Delete ratios by profile and month
	 * @param month
	 * @param profile
	 */
	public void deleteRatio(String month, String profile);
	/**
	 * Check if ratio exists by profile 
	 * @param profile
	 * @return
	 */
	public boolean isRatiosExist(String profile);
	/**
	 * Check if ratio exists by profile and month
	 * @param profile
	 * @param month
	 * @return
	 */
	public boolean isRatiosExist(String profile, String month);
}
