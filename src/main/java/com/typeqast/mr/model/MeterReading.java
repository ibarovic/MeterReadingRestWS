package com.typeqast.mr.model;

import java.io.Serializable;

public class MeterReading implements Serializable{
	
	private static final long serialVersionUID = 9195994693377307696L;
	private String meterId;
	private String profile;
	private String month;
	private Integer reading;
	private Integer consumption;
	
	public MeterReading() {}
	
	public MeterReading(String meterId, String profile, String month, Integer reading, Integer consumption) {
		this.meterId = meterId;
		this.profile = profile;
		this.month = month;
		this.reading = reading;
		this.consumption = consumption;
	}
	
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getReading() {
		return reading;
	}
	public void setReading(Integer reading) {
		this.reading = reading;
	}
	public Integer getConsumption() {
		return consumption;
	}
	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}

	@Override
	public String toString() {
		return "MeterReading [meterId=" + meterId + ", profile=" + profile + ", month=" + month + ", reading=" + reading
				+ ", consumption=" + consumption + "]";
	}
}
