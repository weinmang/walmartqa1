package com.cloudbees.walmartqa1.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="item")
public class Item {

	@Id
	@XmlElement(name="itemId")
	private String itemId = null;
	@XmlElement(name="itemDescr")
	private String itemDescr = null;
	@XmlElement(name="itemPrice")
	private Double itemPrice = null;
	@XmlElement(name="localeId")
	private String localeId = null;

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}
	
	@Override
	public String toString() {
		return "Item:{itemId:" + itemId + ",itemDescr:" + itemDescr
				+ ",itemPrice:" + itemPrice.toString() + ",localeId:" + localeId + "}";
	}
}