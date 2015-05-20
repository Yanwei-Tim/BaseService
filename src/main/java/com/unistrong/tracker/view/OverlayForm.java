package com.unistrong.tracker.view;

import java.io.Serializable;
import java.util.List;

public class OverlayForm implements Serializable{
	

	private static final long serialVersionUID = -5012835296363944849L;

	private List<BufferForm> overlayList;
	
	private String type;

	public List<BufferForm> getOverlayList() {
		return overlayList;
	}

	public void setOverlayList(List<BufferForm> overlayList) {
		this.overlayList = overlayList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
