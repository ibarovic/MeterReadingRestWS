package com.typeqast.mr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.typeqast.mr.dao.MeterReadingDao;
import com.typeqast.mr.dao.RatioDao;
import com.typeqast.mr.exception.RatioDeleteException;
import com.typeqast.mr.exception.ResourcesNotFoundException;
import com.typeqast.mr.model.Ratio;
import com.typeqast.mr.service.RatioService;

@Service
public class RatioServiceImpl implements RatioService {
	
	@Autowired
	RatioDao ratioDao;
	@Autowired
	MeterReadingDao meterReadingDao;
	@Autowired
	MessageSource messageSource;

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#getRatios()
	 */
	@Override
	public List<Ratio> getRatios() {
		return ratioDao.getRatios();
	}
	
	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#getRatios(java.lang.String)
	 */
	@Override
	public List<Ratio> getRatios(String profile) {
		return ratioDao.getRatios(profile);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#getRatios(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Ratio> getRatios(String profile, String month) {
		return ratioDao.getRatios(profile, month);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#createRatios(java.util.List)
	 */
	@Transactional
	@Override
	public void createRatios(List<Ratio> ratios) {
		ratioDao.insertRatios(ratios);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#deleteRatio()
	 */
	@Override
	public void deleteRatio() {
		ratioDao.deleteRatio();
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#deleteRatio(java.lang.String)
	 */
	@Override
	public void deleteRatio(String profile){
		if(ratioDao.isRatiosExist(profile) == false) {
		   	throw new ResourcesNotFoundException(messageSource.getMessage("ratioService.delete.validation.001", new Object[]{profile}, null));
		}
		if(meterReadingDao.isMeterReadingExistByProfile(profile) == true) {
		   	throw new RatioDeleteException(messageSource.getMessage("ratioService.delete.validation.002", new Object[]{profile}, null));

		}
		ratioDao.deleteRatio(profile);
	}

	/* (non-Javadoc)
	 * @see com.typeqast.mr.service.RatioService#isRatiosExist(java.lang.String)
	 */
	@Override
	public boolean isRatiosExist(String profile) {
		return ratioDao.isRatiosExist(profile);
	}
	
}
