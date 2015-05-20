package com.unistrong.tracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import module.util.JsonUtils;

/**
 * @author fyq
 */
@Entity
@Table(name = "us_device")
public class Device implements Serializable {

	private static final long serialVersionUID = -2639680569543322880L;

	@Id
	@Column(name = "f_sn", length = 28)
	private String sn;

	@Column(name = "f_name")
	private String name;

	@Column(name = "f_phone")
	private String phone;

	// --------指令

	// 上传频率
	@Column(name = "f_tick")
	private Integer tick;

	// 超速报警开关：1开; 2关
	@Column(name = "f_speeding_switch")
	private Integer speedingSwitch = 1;

	// 移动报警开关：1开; 2关
	@Column(name = "f_moveing_switch")
	private Integer moveSwitch = 1;

	// 围栏报警开关：1开; 2关
	@Column(name = "f_fence_switch")
	private Integer fenceSwitch = 1;

	// sos号码1-3个{135..,136..,180..}
	@Column(name = "f_sos_num")
	private String sosNum;

	// 流量报警阈值
	@Column(name = "f_flow")
	private Float flow;

	// 超速报警阈值
	@Column(name = "f_speed_threshold")
	private Integer speedThreshold;

	// 电量报警阈值
	@Column(name = "f_battery")
	private Integer battery;

	// --------指令 终

	// 图标相对地址
	@Column(name = "f_icon")
	private String icon;

	@Column(name = "f_car")
	private String car;

	// 服务开通时间
	@Column(name = "f_begin")
	private Long begin;

	// 服务结束时间
	@Column(name = "f_end")
	private Long end;

	/**
	 * 设备类型(车，宠物，人等) UNKNOW = 0; DEVICE_CAR = 1; DEVICE_PET = 2; DEVICE_PERSON =
	 * 3;DEVICE_HANDSET = 4;
	 */
	@Column(name = "f_type")
	private Integer type;

//	/**
//	 * 人物属性（1老人，2护工)
//	 */
//	@Column(name = "f_person_type")
//	private Integer personType;

	/** MT90 M2616 */
	@Column(name = "f_hardware")
	private String hardware;

	@Column(name = "f_email")
	private String email;

	// 身高cm
	@Column(name = "f_height")
	private Float height = 0f;

	// 体重（克）
	@Column(name = "f_weight")
	private Float weight = 0f;

	// 性别 男1 女2
	@Column(name = "f_gender")
	private Integer gender;

	// 年龄-年
	@Column(name = "f_age")
	private Integer age;

	// 年龄-月
	@Column(name = "f_month")
	private Integer month;

	// 年龄(string类型年龄)
	@Column(name = "f_sage")
	private String sage;

	// 图像更新时间戳
	@Column(name = "f_stamp")
	private Long stamp;

	// @Column(name = "f_fence", columnDefinition = "blob")
	// @JsonIgnore
	// private Fence fenceOld;

	@Column(name = "f_fence_json")
	private String fenceJson;

	@Column(name = "f_app_name")
	private String appName;

	@Column(name = "f_birth")
	private Long birth;

	@Column(name = "f_dog_figure")
	private String dogFigure;

	@Column(name = "f_dog_breed")
	private String dogBreed;

	/**
	 * 设备是否支持OBD数据读取
	 */
	@Column(name = "f_isobd")
	private Short isobd = 0;

	/**
	 * 是否开启短信告警功能
	 */
	@Column(name = "f_enable_sms_alarm")
	private Integer enableSmsAlarm = 0;

	/**
	 * 余额查询指令
	 */
	@Column(name = "f_fee_check_cmd")
	private String feeCheckCmd = "";

	@Column(name = "f_fee_check_no")
	private String feeCheckNo = "";

	/**
	 * 联系人手机号, 短信告警会发给这个手机
	 */
	@Column(name = "f_contact")
	private String contact;

	/**
	 * 多边形围栏开关：1开; 2关
	 */
	@Column(name = "f_polygon_fence_switch")
	private Integer polygonFenceSwitch = 2;

	/**
	 * 是否开启余额查询功能
	 */
	@Column(name = "f_enable_fee_check")
	private Integer enableFeeCheck = 0;

	/**
	 * 设备状态(启用:1,禁用:2)
	 */
	@Column(name = "f_status")
	private Integer status;

	/**
	 * 设备所属服务用户(跟绑定用户不是一个概念)
	 */
	@Column(name = "f_service_id")
	private Long serviceId;

	/**
	 * 是否虚拟设备（0：虚拟设备，1:真实设备）
	 */
	@Column(name = "f_isvirtual")
	private Integer isvirtual;

	/**
	 * 设备协议(1:M2616、2:OBD、3:MT90、4:T808)
	 */
	@Column(name = "f_protocol")
	private Integer protocol;

	/**
	 * 设备创建时间
	 */
	@Column(name = "f_time")
	private Long time;

	@Transient
	private Position position;

	/**
	 * 启用
	 */
	public static final int ENABLE = 1;
	/**
	 * 禁用
	 */
	public static final int DISABLE = 2;
	/**
	 * 删除
	 */
	public static final int DELETE = 3;

	// ******************************************

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPolygonFenceSwitch() {
		return polygonFenceSwitch;
	}

	public void setPolygonFenceSwitch(Integer polygonFenceSwitch) {
		this.polygonFenceSwitch = polygonFenceSwitch;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Transient
	private UserJiLin userJiLin;
	
	@Transient
	private List<UserGuarder> userGuarder;
	
	@Transient
	private String beginTime;

	// ******************************************

	public String getIcon() {
		return icon;
	}

	public Integer getEnableSmsAlarm() {
		return enableSmsAlarm;
	}

	public void setEnableSmsAlarm(Integer enableSmsAlarm) {
		this.enableSmsAlarm = enableSmsAlarm;
	}

	public String getFenceJson() {
		return fenceJson;
	}

	public void setFenceJson(String fenceJson) {
		this.fenceJson = fenceJson;
	}

	public String getFeeCheckCmd() {
		return feeCheckCmd;
	}

	public void setFeeCheckCmd(String feeCheckCmd) {
		this.feeCheckCmd = feeCheckCmd;
	}

	public String getFeeCheckNo() {
		return feeCheckNo;
	}

	public void setFeeCheckNo(String feeCheckNo) {
		this.feeCheckNo = feeCheckNo;
	}

	public Integer getEnableFeeCheck() {
		return enableFeeCheck;
	}

	public void setEnableFeeCheck(Integer enableFeeCheck) {
		this.enableFeeCheck = enableFeeCheck;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public Fence getFence() {
		Fence f = null;
		if (this.fenceJson != null && !"".equals(fenceJson)) {
			f = JsonUtils.str2Obj(fenceJson, Fence.class);
		}
		return f;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getIsvirtual() {
		return isvirtual;
	}

	public void setIsvirtual(Integer isvirtual) {
		this.isvirtual = isvirtual;
	}

	public void setFence(Fence f) {
		this.fenceJson = JsonUtils.getJson(f);
	}

	public Device() {
		super();
	}

	public Device(String sn) {
		super();
		this.sn = sn;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Float getFlow() {
		return flow;
	}

	public void setFlow(Float flow) {
		this.flow = flow;
	}

	public Integer getBattery() {
		return battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		if (height == null || "".equals(height)) {
			height = 0f;
		}
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		if (weight == null || "".equals(weight)) {
			weight = 0f;
		}
		this.weight = weight;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getSage() {
		return sage;
	}

	public void setSage(String sage) {
		this.sage = sage;
	}

	public Integer getSpeedThreshold() {
		return speedThreshold;
	}

	public void setSpeedThreshold(Integer speedThreshold) {
		this.speedThreshold = speedThreshold;
	}

	public Integer getTick() {
		return tick;
	}

	public void setTick(Integer tick) {
		this.tick = tick;
	}

	public Integer getSpeedingSwitch() {
		return speedingSwitch;
	}

	public void setSpeedingSwitch(Integer speedingSwitch) {
		this.speedingSwitch = speedingSwitch;
	}

	public Integer getMoveSwitch() {
		return moveSwitch;
	}

	public void setMoveSwitch(Integer moveSwitch) {
		this.moveSwitch = moveSwitch;
	}

	public String getSosNum() {
		return sosNum;
	}

	public void setSosNum(String sosNum) {
		this.sosNum = sosNum;
	}

	public Long getStamp() {
		return stamp;
	}

	public void setStamp(Long stamp) {
		this.stamp = stamp;
	}

	public Integer getFenceSwitch() {
		return fenceSwitch;
	}

	public void setFenceSwitch(Integer fenceSwitch) {
		this.fenceSwitch = fenceSwitch;
	}

	// public Fence getFenceOld() {
	// return fenceOld;
	// }

//	@Override
//	public String toString() {
//		return "Device [sn=" + sn + ", name=" + name + ", phone=" + phone
//				+ ", tick=" + tick + ", speedingSwitch=" + speedingSwitch
//				+ ", moveSwitch=" + moveSwitch + ", fenceSwitch=" + fenceSwitch
//				+ ", sosNum=" + sosNum + ", flow=" + flow + ", speedThreshold="
//				+ speedThreshold + ", battery=" + battery + ", icon=" + icon
//				+ ", car=" + car + ", type=" + type + ", hardware=" + hardware
//				+ ", email=" + email + ", height=" + height + ", weight="
//				+ weight + ", gender=" + gender + ", age=" + age + ",month="
//				+ month + ", stamp=" + stamp + ", fence=" + fenceJson
//				+ ", position=" + position + ",polygonFenceSwitch"
//				+ polygonFenceSwitch + "]";
//	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getBirth() {
		return birth;
	}

	public void setBirth(Long birth) {
		this.birth = birth;
	}

	public String getDogFigure() {
		return dogFigure;
	}

	public void setDogFigure(String dogFigure) {
		this.dogFigure = dogFigure;
	}

	public String getDogBreed() {
		return dogBreed;
	}

	public void setDogBreed(String dogBreed) {
		this.dogBreed = dogBreed;
	}

	public Short getIsobd() {
		return isobd;
	}

	public void setIsobd(Short isobd) {
		this.isobd = isobd;
	}

	public Integer getProtocol() {
		return protocol;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}
	
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public UserJiLin getUserJiLin() {
		return userJiLin;
	}

	public void setUserJiLin(UserJiLin userJiLin) {
		this.userJiLin = userJiLin;
	}

	public List<UserGuarder> getUserGuarder() {
		return userGuarder;
	}

	public void setUserGuarder(List<UserGuarder> list) {
		this.userGuarder = list;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

//	public Integer getPersonType() {
//		return personType;
//	}
//
//	public void setPersonType(Integer personType) {
//		this.personType = personType;
//	}
}