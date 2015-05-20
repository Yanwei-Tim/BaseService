package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import module.util.JsonUtils;

/**
 * 
 * @author sujinxuan
 *
 */
@Entity
@Table(name = "us_buffer")
public class Buffer implements Serializable{

	private static final long serialVersionUID = 5771779840259381934L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "buffer_id")
	private Integer bufferId;

    @Column(name = "create_time")
    private Long createTime;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "buffer_json")
	private String bufferJson;
	
	public Fence getBuffer() {
		Fence f = null;
		if (this.bufferJson != null && !"".equals(bufferJson)) {
			f = JsonUtils.str2Obj(bufferJson, Fence.class);
		}
		return f;
	}

	public void setBuffer(Fence f) {
		this.bufferJson = JsonUtils.getJson(f);
	}
	
	public Integer getBufferId() {
		return bufferId;
	}

	public void setBufferId(Integer bufferId) {
		this.bufferId = bufferId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBufferJson() {
		return bufferJson;
	}

	public void setBufferJson(String bufferJson) {
		this.bufferJson = bufferJson;
	}

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
