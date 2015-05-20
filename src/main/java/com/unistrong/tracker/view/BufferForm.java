package com.unistrong.tracker.view;

import java.io.Serializable;

public class BufferForm implements Serializable{
	
	private static final long serialVersionUID = 5253855709341691970L;

	private String type;
	
	private String region;
	
	private String radius;
	
	private String center;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}
	
	

}
