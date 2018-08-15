package com.typeqast.mr.model;

import java.io.Serializable;
import java.util.List;

public class RatiosRequest implements Serializable{
	
	private static final long serialVersionUID = 4217873888173464488L;
	private List<Ratio> ratios;

	public List<Ratio> getRatios() {
		return ratios;
	}

	public void setRatios(List<Ratio> ratios) {
		this.ratios = ratios;
	}
}
