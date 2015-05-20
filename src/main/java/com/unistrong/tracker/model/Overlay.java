package com.unistrong.tracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "us_overlay")
public class Overlay implements Serializable{

	private static final long serialVersionUID = -8369863181614181000L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "overlay_id")
	private Integer overlayId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "overlay_json")
	private String overlayJson;

    @Column(name = "create_time")
    private Long createTime;
	
	public List<Fence> getOverlay() {
		List<Fence> overlayList = new ArrayList<Fence>();
		if (this.overlayJson != null && !"".equals(overlayJson)) {
			String[] overlays=overlayJson.split(";");
			for(String overlay:overlays){
				Fence f = JsonUtils.str2Obj(overlay, Fence.class);
				overlayList.add(f);
			}	
		}
		return overlayList;
	}

	public void setOverlay(List<Fence> overlayList) {
		String overlay="[";
		for(Fence f: overlayList){
			overlay= overlay+JsonUtils.getJson(f)+",";
		}
        this.overlayJson = overlay.substring(0,overlay.length()-1)+"]";
	}

	public Integer getOverlayId() {
		return overlayId;
	}

	public void setOverlayId(Integer overlayId) {
		this.overlayId = overlayId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOverlayJson() {
		return overlayJson;
	}

	public void setOverlayJson(String overlayJson) {
		this.overlayJson = overlayJson;
	}

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
