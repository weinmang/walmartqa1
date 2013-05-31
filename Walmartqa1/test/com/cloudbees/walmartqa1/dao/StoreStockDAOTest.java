package com.cloudbees.walmartqa1.dao;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.cvo.ItemStockComposite;
import com.cloudbees.walmartqa1.dao.StoreStockDAO;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;

public class StoreStockDAOTest {
	
	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void findByIdTest(Map<String,String> parms) {
		String itemId = parms.get("itemId");
		String localeId = parms.get("localeId");
		StoreStockDAO dao = new StoreStockDAO();
		Assert.assertNotNull(dao, "Could not instantiate data access object");
		ItemStockComposite itemComposite = dao.findById(itemId, localeId);
		Assert.assertNotNull(itemComposite.getItem(), "Item Not Found");
		Assert.assertEquals(itemComposite.getItem().getItemId(), itemId, "Returned item inconsistant with search");
		Assert.assertEquals(itemComposite.getItem().getLocaleId(), localeId, "Returned item inconsistant with search");
		Assert.assertNotNull(itemComposite.getInventory(), "Stock Not Found");
	}

}
