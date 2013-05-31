package com.cloudbees.walmartqa1.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="store")
public class Store {

	@Id
	@XmlElement(name="storeId")
	private String storeId  = null;
	@XmlElement(name="storeDescr")
	private String storeDescr  = null;
	@XmlElement(name="storeAddress")
	private String storeAddress  = null;
	@XmlElement(name="localeId")
	private String localeId = null;

	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreDescr() {
		return storeDescr;
	}
	public void setStoreDescr(String storeDescr) {
		this.storeDescr = storeDescr;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

}