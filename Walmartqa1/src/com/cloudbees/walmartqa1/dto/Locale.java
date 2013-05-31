package com.cloudbees.walmartqa1.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="locale")
public class Locale {

	@Id
	@XmlElement(name="localeId")
	private String localeId = null;
	@XmlElement(name="localeDescr")
	private String localeDescr  = null;

	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}
	public String getLocaleDescr() {
		return localeDescr;
	}
	public void setLocaleDescr(String localeDescr) {
		this.localeDescr = localeDescr;
	}

}