package com.typeqast.mr.model;

import java.io.Serializable;
import java.util.List;

public class MeterReadingRequest implements Serializable{
	
	private static final long serialVersionUID = -8065372508948878871L;
	private List<MeterReading> meterReadings;

	public List<MeterReading> getMeterReadings() {
		return meterReadings;
	}

	public void setMeterReadings(List<MeterReading> meterReadings) {
		this.meterReadings = meterReadings;
	}
}
