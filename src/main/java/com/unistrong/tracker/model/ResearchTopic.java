/**
 * 
 */
package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fss
 *
 */
@Entity
@Table(name = "us_research_topic")
public class ResearchTopic implements Serializable {

	private static final long serialVersionUID = -3261367063305632658L;

	@Id
	@Column(name = "f_id")
	private Long id;
	
	@Column(name = "f_name")
	private String name;
	
	@Column(name = "f_content")
	private String content;
	
	@Column(name = "f_type")
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}	
}
