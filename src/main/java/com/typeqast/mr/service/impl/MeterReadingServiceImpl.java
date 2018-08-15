package com.typeqast.mr.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.typeqast.mr.dao.MeterReadingDao;
import com.typeqast.mr.exception.ResourcesNotFoundException;
import com.typeqast.mr.model.MeterReading;
import com.typeqast.mr.service.MeterReadingService;

@Service
public class MeterReadingServiceImpl implements MeterReadingService {

	@Autowired
	MeterReadingDao meterReadingDao;
	@Autowired
	MessageSource messageSource;
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#getMeterReading()
	 */
	@Override
	public List<MeterReading> getMeterReading() {
		return meterReadingDao.getMeterReading();
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#getMeterReading(java.lang.String)
	 */
	@Override
	public List<MeterReading> getMeterReading(String meterId) {
		return meterReadingDao.getMeterReading(meterId);
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#getMeterReading(java.lang.String, java.lang.String)
	 */
	@Override
	public List<MeterReading> getMeterReading(String meterId, String profile) {
		return meterReadingDao.getMeterReading(meterId, profile);
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#getMeterReading(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<MeterReading> getMeterReading(String meterId, String profile, String month) {
		return meterReadingDao.getMeterReading(meterId, profile, month);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#createMeterReading(java.util.List)
	 */
	@Transactional
	@Override
	public void createMeterReading(List<MeterReading> meterReadings) {
		this.prepareConsumption(meterReadings);
		meterReadingDao.insertMeterReading(meterReadings);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#deleteMeterReading(java.lang.String)
	 */
	@Override
	public void deleteMeterReading(String meterId) {
		if(meterReadingDao.isMeterReadingExist(meterId) == false) {
		   	throw new ResourcesNotFoundException(messageSource.getMessage("meterReadingService.delete.validation.001", new Object[]{meterId}, null));
		}
		meterReadingDao.deleteMeterReading(meterId);
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#deleteMeterReading(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteMeterReading(String meterId, String profile) {
		if(meterReadingDao.isMeterReadingExist(meterId, profile) == false) {
		   	throw new ResourcesNotFoundException(messageSource.getMessage("meterReadingService.delete.validation.002", new Object[]{meterId, profile}, null));
		}
		meterReadingDao.deleteMeterReading(meterId, profile);
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#isMeterReadingExist(java.lang.String)
	 */
	public boolean isMeterReadingExist(String meterId) {
		return meterReadingDao.isMeterReadingExist(meterId);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.MeterReadingService#isMeterReadingExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isMeterReadingExist(String meterId, String profile) {
		return meterReadingDao.isMeterReadingExist(meterId, profile);
	}

	private void prepareConsumption(List<MeterReading> meterReadings) {
		// Group all readings per month and do validation
	    Map<String, List<MeterReading>> monthToReadings = meterReadings.stream().collect(Collectors.groupingBy(MeterReading::getMonth));
	    
		Integer readingJAN = monthToReadings.get("JAN").get(0).getReading();
		Integer readingFEB = monthToReadings.get("FEB").get(0).getReading();
		Integer readingMAR = monthToReadings.get("MAR").get(0).getReading();
		Integer readingAPR = monthToReadings.get("APR").get(0).getReading();
		Integer readingMAY = monthToReadings.get("MAY").get(0).getReading();
		Integer readingJUN = monthToReadings.get("JUN").get(0).getReading();
		Integer readingJUL = monthToReadings.get("JUL").get(0).getReading();
		Integer readingAUG = monthToReadings.get("AUG").get(0).getReading();
		Integer readingSEP = monthToReadings.get("SEP").get(0).getReading();
		Integer readingOCT = monthToReadings.get("OCT").get(0).getReading();
		Integer readingNOV = monthToReadings.get("NOV").get(0).getReading();
		Integer readingDEC = monthToReadings.get("DEC").get(0).getReading();
		
		for (MeterReading meterReading : meterReadings) {
			if("JAN".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingJAN);
			}else if("FEB".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingFEB - readingJAN);
			}else if("MAR".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingMAR - readingFEB);
			}else if("APR".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingAPR - readingMAR);
			}else if("MAY".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingMAY -readingAPR);
			}else if("JUN".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingJUN - readingMAY);
			}else if("JUL".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingJUL - readingJUN);
			}else if("AUG".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingAUG - readingJUL);
			}else if("SEP".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingSEP - readingAUG);
			}else if("OCT".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingOCT - readingSEP);
			}else if("NOV".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingNOV - readingOCT);
			}else if("DEC".equals(meterReading.getMonth())) {
				meterReading.setConsumption(readingDEC - readingNOV);
			}
		}
		
		
	}
}
