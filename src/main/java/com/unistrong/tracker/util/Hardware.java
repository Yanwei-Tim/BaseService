package com.unistrong.tracker.util;

public enum Hardware {

	MT90(1, 3), M2616(1, 3), phone(1, 0);

	// -------------------------------------------

	public static void main(String[] args) {
		// 二进制转int
		int version = Integer.valueOf("0011", 2);
		System.out.println(version & getYu(3));
	}

	/** 与 第几位功能 零为不支持 0<n<32 */
	public static int getYu(int n) {
		// 2的n-1次方
		return (int) Math.pow(2, n - 1);
		// 0001 1 是否支持圆形围栏
		// 0010 2 是否支持多边形围栏
		// 0100 4
		// 1000 8
	}

	// -------------------------------------------

	private Integer index;

	private Integer support;

	private Hardware(Integer index, Integer support) {
		this.index = index;
		this.support = support;
	}

	public static Integer getByIndex(Integer index) {
		for (Hardware c : Hardware.values()) {
			if (c.getIndex() == index) {
				return c.support;
			}
		}
		return null;
	}

	public Integer getSupport() {
		return support;
	}

	public void setSupport(Integer support) {
		this.support = support;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
