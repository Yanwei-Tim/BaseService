package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "us_obd")
@IdClass(value = IdSpot.class)
public class OBD  implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568661040063332566L;
	@Column(name = "f_systime")
	private Long systime = 0L;
	@Id
	private String deviceSn;
	@Column(name = "f_receive")
	@Id
	private Long receive = 0L;
	@Column(name = "f_engine_load")
	private Float load;//负荷计算 %
	
	@Column(name = "f_cool_temperature")
	private Float coolTemperature;//发动机冷却温度 摄氏度
	@Column(name = "f_inair_temperature")
	private Float  inAirtemperature;//进气温度   摄氏度
	
	@Column(name = "f_ambientTemperature")
	private Float ambientTemperature;//环境温度
	@Column(name = "f_engineOilTemperature")
	private Float engineOilTemperature;//机油温度	
	@Transient
	private Float[] longFuelCorrection;//长期燃油修正 %
	@Column(name = "f_fuel_pipepressure")
	private Float fuelPipePressure;   //燃油分配管压力
	
	@Column(name = "f_inair_pressure")
	private Float  inlAirPipePressure;//进气管压力   kpa
	@Column(name = "f_frozenFaultCode")
	private String frozenFaultCode;//冻结故障码
	@Column(name = "f_rpm")
	private Float  engineSpeed;//发动机转速  rpm
	@Column(name = "f_speed")
	private Float  speed;  //车速  km/h
	@Column(name = "f_fireAngle")
	private Float  fireAngle;//点火提前角  °
	
	@Column(name = "f_air_traffic")
	private Float  airTraffic;//空气流量  g/s
	
	
	@Column(name = "f_runtime")
	private Long  runTime;//运转时间 s
	@Column(name = "f_mtlDistance")
	private Long  mtlDistance;//故障灯后的行驶距离  km
	@Column(name = "f_mtlRunTime")
	private Long  mtlRunTime;//故障灯后的运转时间  minute
	 
	
	@Column(name = "f_controllerVoltage")
	private Float controllerVoltage;//电瓶电压  V
	
	@Column(name = "f_throttlePosition")
	private Float  throttlePosition;// 节气门绝对位置
	@Column(name = "f_fuelSystemStatus")
	private String fuelSystemStatus;//燃油系统状态
	
	@Transient
	private  Short fuelType;//燃料类型
	@Transient
	private  Float alcoholPercent;//酒精占比 %
	@Transient
	private  Float batteryPercent;//电池剩余电量 %
	@Transient
	private Float  fuelOutAngle;//燃油喷射点  °
	@Column(name = "f_engineFuelRate")
	private Float engineFuelRate;//发动机燃油率
	@Transient
	private Float torque;//实际发动机扭矩  %
	@Transient
	private Float fuleCostPerHour;//每小时油耗 L/H
	@Column(name = "f_fuleCostHundred")
	private Float fuleCostHundred;//每100km油耗 L/100km
	@Column(name = "f_leftFule")
	private String leftFule;//剩余油量
	@Column(name = "f_totalDistance")
	private Double totalDistance;//行驶里程  km
	
	@Column(name = "f_complyStandards")
	private String  complyStandards; //车辆转配OBD的类型
	
	public Float getLoad() {
		return load;
	}
	public void setLoad(Float load) {
		this.load = load;
	}
	public Float getCoolTemperature() {
		return coolTemperature;
	}
	public void setCoolTemperature(Float coolTemperature) {
		this.coolTemperature = coolTemperature;
	}
	
	public Float[] getLongFuelCorrection() {
		return longFuelCorrection;
	}
	public void setLongFuelCorrection(Float[] longFuelCorrection) {
		this.longFuelCorrection = longFuelCorrection;
	}
	public Float getFuelPipePressure() {
		return fuelPipePressure;
	}
	public void setFuelPipePressure(Float fuelPipePressure) {
		this.fuelPipePressure = fuelPipePressure;
	}
	public Float getInlAirPipePressure() {
		return inlAirPipePressure;
	}
	public void setInlAirPipePressure(Float inlAirPipePressure) {
		this.inlAirPipePressure = inlAirPipePressure;
	}
	public Float getEngineSpeed() {
		return engineSpeed;
	}
	public void setEngineSpeed(Float engineSpeed) {
		this.engineSpeed = engineSpeed;
	}
	public Float getSpeed() {
		return speed;
	}
	public void setSpeed(Float speed) {
		this.speed = speed;
	}
	public Float getFireAngle() {
		return fireAngle;
	}
	public void setFireAngle(Float fireAngle) {
		this.fireAngle = fireAngle;
	}
	public Float getInAirtemperature() {
		return inAirtemperature;
	}
	public void setInAirtemperature(Float inAirtemperature) {
		this.inAirtemperature = inAirtemperature;
	}
	public Float getAirTraffic() {
		return airTraffic;
	}
	public void setAirTraffic(Float airTraffic) {
		this.airTraffic = airTraffic;
	}
	
	public Long getRunTime() {
		return runTime;
	}
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}
	public Long getMtlDistance() {
		return mtlDistance;
	}
	public void setMtlDistance(Long mtlDistance) {
		this.mtlDistance = mtlDistance;
	}
	public Long getMtlRunTime() {
		return mtlRunTime;
	}
	public void setMtlRunTime(Long mtlRunTime) {
		this.mtlRunTime = mtlRunTime;
	}
	
	
	public Float getControllerVoltage() {
		return controllerVoltage;
	}
	public void setControllerVoltage(Float controllerVoltage) {
		this.controllerVoltage = controllerVoltage;
	}
	
	public Float getThrottlePosition() {
		return throttlePosition;
	}
	public void setThrottlePosition(Float throttlePosition) {
		this.throttlePosition = throttlePosition;
	}
	public Float getAmbientTemperature() {
		return ambientTemperature;
	}
	public void setAmbientTemperature(Float ambientTemperature) {
		this.ambientTemperature = ambientTemperature;
	}
	public Short getFuelType() {
		return fuelType;
	}
	public void setFuelType(Short fuelType) {
		this.fuelType = fuelType;
	}
	public Float getAlcoholPercent() {
		return alcoholPercent;
	}
	public void setAlcoholPercent(Float alcoholPercent) {
		this.alcoholPercent = alcoholPercent;
	}
	public Float getBatteryPercent() {
		return batteryPercent;
	}
	public void setBatteryPercent(Float batteryPercent) {
		this.batteryPercent = batteryPercent;
	}
	public Float getEngineOilTemperature() {
		return engineOilTemperature;
	}
	public void setEngineOilTemperature(Float engineOilTemperature) {
		this.engineOilTemperature = engineOilTemperature;
	}
	public Float getFuelOutAngle() {
		return fuelOutAngle;
	}
	public void setFuelOutAngle(Float fuelOutAngle) {
		this.fuelOutAngle = fuelOutAngle;
	}
	public Float getTorque() {
		return torque;
	}
	public void setTorque(Float torque) {
		this.torque = torque;
	}
	public Float getFuleCostPerHour() {
		return fuleCostPerHour;
	}
	public void setFuleCostPerHour(Float fuleCostPerHour) {
		this.fuleCostPerHour = fuleCostPerHour;
	}
	public Float getFuleCostHundred() {
		return fuleCostHundred;
	}
	public void setFuleCostHundred(Float fuleCostHundred) {
		this.fuleCostHundred = fuleCostHundred;
	}
	public String getLeftFule() {
		return leftFule;
	}
	public void setLeftFule(String leftFule) {
		this.leftFule = leftFule;
	}
	
	public Long getSystime() {
		return systime;
	}
	public void setSystime(Long systime) {
		this.systime = systime;
	}
	public Long getReceive() {
		return receive;
	}
	public void setReceive(Long receive) {
		this.receive = receive;
	}
	
	public Double getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	
	public Float getEngineFuelRate() {
		return engineFuelRate;
	}
	public void setEngineFuelRate(Float engineFuelRate) {
		this.engineFuelRate = engineFuelRate;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	
	public String getFrozenFaultCode() {
		return frozenFaultCode;
	}
	public void setFrozenFaultCode(String frozenFaultCode) {
		this.frozenFaultCode = frozenFaultCode;
	}
	public String getComplyStandards() {
		return complyStandards;
	}
	public void setComplyStandards(String complyStandards) {
		this.complyStandards = complyStandards;
	}
	
	
	
	public String getFuelSystemStatus() {
		return fuelSystemStatus;
	}
	public void setFuelSystemStatus(String fuelSystemStatus) {
		this.fuelSystemStatus = fuelSystemStatus;
	}
	@Override
	public String toString() {
		return "OBD [receive=" + receive + ", load=" + load + "%, speed="
				+ speed + "km/h, engineSpeed="+engineSpeed+"rpm, coolTemperature="+coolTemperature+"°C, controllerVoltage="+controllerVoltage+"V]";
	}
	
	
	
}
