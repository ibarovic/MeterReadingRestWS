package com.typeqast.mr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.typeqast.mr.dao.RatioDao;
import com.typeqast.mr.model.Ratio;


@Repository
public class RatioDaoImpl implements RatioDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// TODO all sqls separate in one class or file for better maintanance
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#getRatios()
	 */
	@Override
	public List<Ratio> getRatios() {
		String tSql = "SELECT month, profile, ratio FROM ratio";
		return jdbcTemplate.query(tSql, new BeanPropertyRowMapper<>(Ratio.class));
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#getRatios(java.lang.String)
	 */
	@Override
	public List<Ratio> getRatios(String profile) {
		String tSql = "SELECT month, profile, ratio FROM ratio WHERE profile = ?";
		return jdbcTemplate.query(tSql, new Object[]{profile}, new BeanPropertyRowMapper<>(Ratio.class));
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#getRatios(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Ratio> getRatios(String profile, String month) {
		String tSql = "SELECT month, profile, ratio FROM ratio WHERE profile = ? AND month = ?";
		return jdbcTemplate.query(tSql, new Object[]{profile, month}, new BeanPropertyRowMapper<>(Ratio.class));
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#insertRatio(java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void insertRatio(String month, String profile, Double ratio) {
		String tSql = "INSERT INTO ratio (month, profile, ratio, tstamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
		jdbcTemplate.update(tSql, new Object[] {month, profile, ratio});  
		
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#insertRatios(java.util.List)
	 */
	@Override
	public void insertRatios(List<Ratio> ratios) {
		String tSql = "INSERT INTO ratio (month, profile, ratio, tstamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
		List<Object[]> tParamsList = new ArrayList<>();
		
		for (Ratio ratio : ratios){
			Object[] tArgs = new Object[]{ratio.getMonth(), ratio.getProfile(), ratio.getRatio()};
			tParamsList.add(tArgs);
		}
		
		jdbcTemplate.batchUpdate(tSql, tParamsList);		
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#deleteRatio()
	 */
	@Override
	public void deleteRatio() {
		jdbcTemplate.update("DELETE FROM ratio");  
	}
	
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#deleteRatio(java.lang.String)
	 */
	@Override
	public void deleteRatio(String profile) {
		String tSql = "DELETE FROM ratio WHERE profile = ?";
		jdbcTemplate.update(tSql, new Object[] {profile});  
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#deleteRatio(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteRatio(String month, String profile) {
		String tSql = "DELETE FROM ratio WHERE profile = ? AND month = ?";
		jdbcTemplate.update(tSql, new Object[] {month, profile});  
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#isRatiosExist(java.lang.String)
	 */
	@Override
	public boolean isRatiosExist(String profile) {
		String tSql = "SELECT COUNT(profile) FROM ratio WHERE profile = ?";
		Integer count = jdbcTemplate.queryForObject(tSql, new Object[]{profile}, Integer.class);
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.dao.RatioDao#isRatiosExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isRatiosExist(String profile, String month) {
		String tSql = "SELECT COUNT(profile) FROM ratio WHERE profile = ? AND month = ?";
		Integer count = jdbcTemplate.queryForObject(tSql, new Object[]{profile, month}, Integer.class);
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}
}
