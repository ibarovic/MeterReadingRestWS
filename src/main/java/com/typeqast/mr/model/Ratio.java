package com.typeqast.mr.model;

import java.io.Serializable;

public class Ratio implements Serializable{
	
	private static final long serialVersionUID = -1144857428910416819L;
	private String month;
	private String profile;
	private Double ratio;
	
	public Ratio() {}
	
	public Ratio(String month, String profile, Double ratio){
		this.month = month;
		this.profile = profile;
		this.ratio = ratio;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return "Ratio [month=" + month + ", profile=" + profile + ", ratio=" + ratio + "]";
	}
	
}
