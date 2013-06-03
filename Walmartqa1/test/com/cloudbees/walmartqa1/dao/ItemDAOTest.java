package com.cloudbees.walmartqa1.dao;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.dao.ItemDAO;
import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.exception.ApiException;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;

public class ItemDAOTest {

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions=ApiException.class)
	public void findByIdTest(Map<String,String> parms) throws ApiException {
		String itemId = parms.get("itemId");
		ItemDAO dao = new ItemDAO();
		Assert.assertNotNull(dao, "Could not instantiate data access object");
		Item item = dao.findById(itemId);
		Assert.assertNotNull(item, "Item Not Found");
		Assert.assertEquals(item.getItemId(), itemId, "Returned item inconsistant with search");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions=ApiException.class)
	public void createItemTest(Map<String,String> parms) throws ApiException {
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
		ItemDAO dao = new ItemDAO();
		Item newItem = dao.createItem(item);
		Assert.assertNotNull(newItem, "Item creation failure");
		Assert.assertEquals(newItem.getItemId(), itemId, "Item creation failure");
	}
}
