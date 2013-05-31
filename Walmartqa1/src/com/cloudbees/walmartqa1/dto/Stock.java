package com.cloudbees.walmartqa1.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@IdClass(value=StockPK.class)
@XmlRootElement(name="stock")
public class Stock {

	@Id
	@XmlElement(name="itemId")
	private String itemId = null;
	@Id
	@XmlElement(name="storeId")
	private String storeId  = null;
	@XmlElement(name="itemQty")
	private Integer itemQty  = null;

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Integer getItemQty() {
		return itemQty;
	}
	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}

}