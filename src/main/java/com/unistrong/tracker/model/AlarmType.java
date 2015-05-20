package com.unistrong.tracker.model;



/**
 * @author capcare
 */
public enum AlarmType {
	
	/** 未识别报警  */
	UNDIFINE(-1, "未识别报警","UNDIFINE"),
	
	/** 低速报警  */
	SPDLO(9, "低速报警","low speed"),

	/** 防盗报警  */
	BAT(8, "防盗报警","burglar alarm"),

	/** 拔出报警  */
	EPD(7, "拔出报警","Pull out"),

	/** 移动报警  */
	VIB(6, "移动报警","move"),

	/** SOS报警  */
	SOS(5,"SOS报警","SOS"),

	/** 超速报警  */
	SPDHI(4,"超速报警","over speed"),

	/** 进围栏报警  */
	BNDIN(2, "进围栏报警","in fence"),

	/** 出围栏报警  */
	BNDOUT(1, "出围栏报警","out fence"),
	
	/** 低电报警  */
	PowerLow(3, "低电报警","low power"),
	
	/** 断电报警  */
	PowerOFF(10, "断电报警","power off"),
	
	/** 震动报警  */
	SHAKE(11, "震动报警","shake"),
	
	GPSOPEN(12, "GPS天线开路报警","GPS OPEN"),
	
	GPSCLOSE(13, "GPS天线短路报警","GPS CLOSE"),
	
	/** 开锁报警*/
	OPENLOCK(14, "开锁报警","open lock"),
	
	/** 开箱报警*/
	OPENBOX(15, "开箱报警","open box"),
	
	/** 离开报警*/
	LEAVEOUT(16, "离开报警","leave out"),
	
	OBDERROR(100, "OBD故障","OBD error");
	
	private int num;

	private String info;
	private String eninfo;

	private AlarmType(int num, String info,String eninfo) {
		this.num = num;
		this.info = info;
		this.eninfo=eninfo;
	}

	public int getNum() {
		return this.num;
	}

	public String getInfo() {
		return this.info;
	}

	public String getEninfo() {
		return eninfo;
	}
	
	public static AlarmType getByNum(int num){
		AlarmType[] types = AlarmType.values();
		for(AlarmType type : types){
			if(type.getNum() == num){
				return type;
			}
		}
		return UNDIFINE;
	}
}
