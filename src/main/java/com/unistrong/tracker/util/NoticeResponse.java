/**
 * 
 */
package com.unistrong.tracker.util;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author fss
 *
 */
public class NoticeResponse {

	private String versionCode;
	
	private String content;
	
	private String title;
	
	//1:无公告无更新；2：公告；3：提示更新；4：强制更新
	private int type;
	
	@JsonIgnore
	private Map<String, String> urlmap;
	
	private String url;

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public Map<String, String> getUrlmap() {
		return urlmap;
	}

	public void setUrlmap(Map<String, String> urlmap) {
		this.urlmap = urlmap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
