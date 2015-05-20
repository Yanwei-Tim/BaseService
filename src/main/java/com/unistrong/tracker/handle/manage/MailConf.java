package com.unistrong.tracker.handle.manage;

import org.springframework.stereotype.Component;

@Component
public class MailConf {

	private String url;

	private String from;

	private String icon;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
