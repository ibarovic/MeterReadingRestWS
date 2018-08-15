package com.typeqast.mr.service;

import java.util.List;

import com.typeqast.mr.model.MeterReading;

public interface MeterReadingService {
	
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
	 * Method that inserts prepared meter readings to database, batch update, transactional
	 * @param meterReadings
	 */
	public void createMeterReading(List<MeterReading> meterReadings);
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
}
