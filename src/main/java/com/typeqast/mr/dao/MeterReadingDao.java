package com.typeqast.mr.dao;

import java.util.List;

import com.typeqast.mr.model.MeterReading;

public interface MeterReadingDao {
	
	/**
	 * Get all meter readings by meterId
	 * @return
	 */
	public List<MeterReading> getMeterReading();
	/**
	 * Get meter readings by meterId
	 * @param meterId
	 * @return
	 */
	public List<MeterReading> getMeterReading(String meterId);
	/**
	 * Get meter readings by meterId and profile
	 * @param meterId
	 * @param profile
	 * @return
	 */
	public List<MeterReading> getMeterReading(String meterId, String profile);
	/**
	 * Get meter readings by meterId and profile and month
	 * @param meterId
	 * @param profile
	 * @param month
	 * @return
	 */
	public List<MeterReading> getMeterReading(String meterId, String profile, String month);
	/**
	 * Insert meter reading
	 * @param meterId
	 * @param profile
	 * @param month
	 * @param reading
	 * @param consumption
	 */
	public void insertMeterReading(String meterId, String profile, String month, Integer reading, Integer consumption);
	/**
	 * Insert meter readings, batch update
	 * @param meterReading
	 */
	public void insertMeterReading(List<MeterReading> meterReading);
	/**
	 * Delete meter reading by meterId
	 * @param meterId
	 */
	public void deleteMeterReading(String meterId);
	/**
	 * Delete meter reading by meterId and profile
	 * @param meterId
	 * @param profile
	 */
	public void deleteMeterReading(String meterId, String profile);
	/**
	 * Check if meter reading exists by metrId
	 * @param meterId
	 * @return
	 */
	public boolean isMeterReadingExist(String meterId);
	/**
	 * Check if meter reading exists by metrId and profile
	 * @param meterId
	 * @param profile
	 * @return
	 */
	public boolean isMeterReadingExist(String meterId, String profile);
	/**
	 * Check if meter reading exists by profile
	 * @param profile
	 * @return
	 */
	public boolean isMeterReadingExistByProfile(String profile);

}
