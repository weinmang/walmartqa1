package com.cloudbees.walmartqa1.cvo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="storestock")
public class StoreStockComposite {

	@XmlElement(name="storeDescr")
	private String storeDescr  = null;
	@XmlElement(name="storeAddress")
	private String storeAddress  = null;
	@XmlElement(name="itemQty")
	private Integer itemQty  = null;

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
	public Integer getItemQty() {
		return itemQty;
	}
	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}

}
