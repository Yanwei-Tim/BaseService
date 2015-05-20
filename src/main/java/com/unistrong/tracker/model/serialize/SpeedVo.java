package com.unistrong.tracker.model.serialize;

public class SpeedVo {
	private String show;
	private Double percent;

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public SpeedVo(String show) {
		super();
		this.show = show;
	}

	@Override
	public String toString() {
		return "SpeedVo [show=" + show + ", percent=" + percent + "]";
	}
}