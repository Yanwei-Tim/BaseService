/**
 * 
 */
package com.unistrong.tracker.handle.manual;

/**
 * @author fss
 *
 */
public class Config {

	private int interval;
	
	private int layers;
	
	private String share;
	
	private String env;

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}	
	
}
