package com.cloudbees.walmartqa1.dto;

import java.io.Serializable;

public class StockPK implements Serializable {

	private String itemId = null;
	private String storeId  = null;

	public StockPK() {
	}
	 
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StockPK){
        	StockPK StockPK = (StockPK) obj;
 
            if(!StockPK.getItemId().equals(itemId)){
                return false;
            }
            if(!StockPK.getStoreId().equals(storeId)){
                return false;
            }
            return true;
        }
        return false;
    }
 
    @Override
    public int hashCode() {
        return itemId.hashCode() + storeId.hashCode();
    }

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

}
