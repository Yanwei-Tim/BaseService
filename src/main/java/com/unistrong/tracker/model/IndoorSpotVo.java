package com.unistrong.tracker.model;

import java.io.Serializable;

public class IndoorSpotVo implements Serializable {

	private static final long serialVersionUID = 7129153746826875594L;

	private String system;

	private Double x;

	private Double y;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "IndoorSpotVo [system=" + system + ",x=" + x + ", y=" + y + "]";
	}

}
