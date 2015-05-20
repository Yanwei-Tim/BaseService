package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 手持机与指挥机关系
 * 
 * @author wyr
 *
 */
@Entity
@Table(name = "rd_handset_to_conductor")
public class HandSetToConductor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4143331597580485012L;

	@Id
	@Column(name="id")
	private long id;

	/**
	 * 手持机设备id
	 */
	@Column(name = "device_sn")
	private String deviceSn;

	/**
	 * 指挥机sn号
	 */
	@Column(name = "conductor_id")
	private String conductorId;

	public String getConductorId() {
		return conductorId;
	}

	public void setConductorId(String conductorId) {
		this.conductorId = conductorId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conductorId == null) ? 0 : conductorId.hashCode());
		result = prime * result
				+ ((deviceSn == null) ? 0 : deviceSn.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HandSetToConductor other = (HandSetToConductor) obj;
		if (conductorId == null) {
			if (other.conductorId != null)
				return false;
		} else if (!conductorId.equals(other.conductorId))
			return false;
		if (deviceSn == null) {
			if (other.deviceSn != null)
				return false;
		} else if (!deviceSn.equals(other.deviceSn))
			return false;
		if (id != other.id)
			return false;
		return true;
	}


}
