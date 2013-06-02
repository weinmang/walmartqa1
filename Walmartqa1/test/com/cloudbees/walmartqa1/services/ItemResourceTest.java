package com.cloudbees.walmartqa1.services;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;

public class ItemResourceTest {

	private ItemResource resource = null;

	@BeforeClass
	public void setUpTest() {
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void getItemTest(Map<String,String> parms) {
		String itemId = parms.get("itemId");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void createItemTest(Map<String,String> parms) {
		String itemId = parms.get("itemId");
		String itemDescr = parms.get("itemDescr");
		String itemPriceStr = parms.get("itemPrice");
		Double itemPrice = Double.valueOf(itemPriceStr);
		String localeId = parms.get("localeId");
		Item item = new Item();
		item.setItemId(itemId);
		item.setItemDescr(itemDescr);
		item.setItemPrice(itemPrice);
		item.setLocaleId(localeId);
	}
}
