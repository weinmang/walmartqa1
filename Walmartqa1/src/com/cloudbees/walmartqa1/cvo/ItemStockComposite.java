package com.cloudbees.walmartqa1.cvo;

import java.util.List;

import com.cloudbees.walmartqa1.RequestStatus;
import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.dto.Locale;

public class ItemStockComposite {

	Item item = null;
	Locale locale = null;
	List<StoreStockComposite> inventory = null;

	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public List<StoreStockComposite> getInventory() {
		return inventory;
	}
	public void setInventory(List<StoreStockComposite> inventory) {
		this.inventory = inventory;
	}

}
