package com.typeqast.mr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.typeqast.mr.dao.MeterReadingDao;
import com.typeqast.mr.model.MeterReading;

@Repository
public class MeterReadingDaoImpl implements MeterReadingDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#getMeterReading()
	 */
	@Override
	public List<MeterReading> getMeterReading() {
		String tSql = "SELECT meter_id, profile, month, reading, consumption FROM meter_reading";
		return jdbcTemplate.query(tSql, new Object[]{}, new BeanPropertyRowMapper<>(MeterReading.class));
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#getMeterReading(java.lang.String)
	 */
	@Override
	public List<MeterReading> getMeterReading(String meterId) {
		String tSql = "SELECT meter_id, profile, month, reading, consumption FROM meter_reading WHERE meter_id = ?";
		return jdbcTemplate.query(tSql, new Object[]{meterId}, new BeanPropertyRowMapper<>(MeterReading.class));
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#getMeterReading(java.lang.String, java.lang.String)
	 */
	@Override
	public List<MeterReading> getMeterReading(String meterId, String profile) {
		String tSql = "SELECT meter_id, profile, month, reading, consumption FROM meter_reading WHERE meter_id = ? and profile = ?";
		return jdbcTemplate.query(tSql, new Object[]{meterId, profile}, new BeanPropertyRowMapper<>(MeterReading.class));
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#getMeterReading(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<MeterReading> getMeterReading(String meterId, String profile, String month) {
		String tSql = "SELECT meter_id, profile, month, reading, consumption FROM meter_reading WHERE meter_id = ? and profile = ? and month = ?";
		return jdbcTemplate.query(tSql, new Object[]{meterId, profile, month}, new BeanPropertyRowMapper<>(MeterReading.class));
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#insertMeterReading(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void insertMeterReading(String meterId, String profile, String month, Integer reading, Integer consumption) {
		String tSql = "INSERT INTO meter_reading (meter_id, profile, month, reading, consumption, tstamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		jdbcTemplate.update(tSql, new Object[] {meterId, profile, month, reading, consumption});
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#insertMeterReading(java.util.List)
	 */
	@Override
	public void insertMeterReading(List<MeterReading> meterReadings) {
		String tSql = "INSERT INTO meter_reading (meter_id, profile, month, reading, consumption, tstamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		List<Object[]> tParamsList = new ArrayList<>();
		for (MeterReading meterReading : meterReadings){
			Object[] tArgs = new Object[]{meterReading.getMeterId(), meterReading.getProfile(), meterReading.getMonth(), meterReading.getReading(), meterReading.getConsumption()};
			tParamsList.add(tArgs);
		}
		jdbcTemplate.batchUpdate(tSql, tParamsList);			
	}
	
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#deleteMeterReading(java.lang.String)
	 */
	@Override
	public void deleteMeterReading(String meterId) {
		String tSql = "DELETE FROM meter_reading WHERE meter_id = ?";
		jdbcTemplate.update(tSql, new Object[] {meterId});  
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#deleteMeterReading(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteMeterReading(String meterId, String profile) {
		String tSql = "DELETE FROM meter_reading WHERE meter_id = ? and profile = ?";
		jdbcTemplate.update(tSql, new Object[] {meterId, profile});  
	}
	
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#isMeterReadingExist(java.lang.String)
	 */
	@Override
	public boolean isMeterReadingExist(String meterId) {
		String tSql = "SELECT COUNT(meter_id) FROM meter_reading WHERE meter_id = ?";
		Integer count = jdbcTemplate.queryForObject(tSql, new Object[]{meterId}, Integer.class);
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#isMeterReadingExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isMeterReadingExist(String meterId, String profile) {
		String tSql = "SELECT COUNT(meter_id) FROM meter_reading WHERE meter_id = ? and profile = ?";
		Integer count = jdbcTemplate.queryForObject(tSql, new Object[]{meterId, profile}, Integer.class);
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.MeterReadingDao#isMeterReadingExistByProfile(java.lang.String)
	 */
	@Override
	public boolean isMeterReadingExistByProfile(String profile) {
		String tSql = "SELECT COUNT(meter_id) FROM meter_reading WHERE profile = ?";
		Integer count = jdbcTemplate.queryForObject(tSql, new Object[]{profile}, Integer.class);
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}

}
