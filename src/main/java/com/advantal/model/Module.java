package com.advantal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MODULE")
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="module_id")
	private Integer moduleId;
	
	@Column(name="module_name", nullable = false)
	private String moduleName;
	

	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}