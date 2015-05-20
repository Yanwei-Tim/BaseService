package com.unistrong.tracker.util.upload;

public interface UploadImageInterface {

	String getIcon();
	
	Long getUserId();
	
	String getDeviceSn();
	
	void writeFile() throws Exception;
}
