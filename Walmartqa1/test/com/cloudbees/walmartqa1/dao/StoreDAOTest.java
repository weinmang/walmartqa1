package com.cloudbees.walmartqa1.dao;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.dto.Store;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;

public class StoreDAOTest {

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void findByIdTest(Map<String,String> parms) {
		String storeId = parms.get("storeId");
		StoreDAO dao = new StoreDAO();
		Assert.assertNotNull(dao, "Could not instantiate data access object");
		Store store = dao.findById(storeId);
		Assert.assertNotNull(store, "Store Not Found");
		Assert.assertEquals(store.getStoreId(), storeId, "Returned store inconsistant with search");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void createStoreTest(Map<String,String> parms) {
		String storeId = parms.get("storeId");
		String storeDescr = parms.get("storeDescr");
		String storeAddress = parms.get("storeAddress");
		String localeId = parms.get("localeId");
		Store store = new Store();
		store.setStoreId(storeId);
		store.setStoreDescr(storeDescr);
		store.setStoreAddress(storeAddress);
		store.setLocaleId(localeId);
		StoreDAO dao = new StoreDAO();
		Store newStore = dao.createStore(store);
		Assert.assertNotNull(newStore, "Store creation failure");
		Assert.assertEquals(newStore.getStoreId(), storeId, "Store creation failure");
	}
}
