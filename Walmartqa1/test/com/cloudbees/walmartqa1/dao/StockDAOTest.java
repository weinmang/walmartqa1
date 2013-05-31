package com.cloudbees.walmartqa1.dao;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.dto.Stock;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;

public class StockDAOTest {

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void findByIdTest(Map<String,String> parms) {
		String itemId = parms.get("itemId");
		String storeId = parms.get("storeId");
		StockDAO dao = new StockDAO();
		Assert.assertNotNull(dao, "Could not instantiate data access object");
		Stock stock = dao.findById(itemId, storeId);
		Assert.assertNotNull(stock, "Stock Not Found");
		Assert.assertEquals(stock.getItemId(), itemId, "Returned stock item inconsistant with search");
		Assert.assertEquals(stock.getStoreId(), storeId, "Returned stock store inconsistant with search");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void findByItemIdTest(Map<String,String> parms) {
		String itemId = parms.get("itemId");
		StockDAO dao = new StockDAO();
		Assert.assertNotNull(dao, "Could not instantiate data access object");
		List<Stock> stockList = dao.findByItemId(itemId);
		Assert.assertNotNull(stockList, "Stock list Not Found");
		Assert.assertEquals(stockList.get(0).getItemId(), itemId, "Returned stock item in list inconsistant with search");
	}


	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void createStockTest(Map<String,String> parms) {
		String itemId = parms.get("itemId");
		String storeId = parms.get("storeId");
		String itemQtyStr = parms.get("itemQty");
		Integer itemQty = Integer.valueOf(itemQtyStr);
		Stock stock = new Stock();
		stock.setItemId(itemId);
		stock.setStoreId(storeId);
		stock.setItemQty(itemQty);
		StockDAO dao = new StockDAO();
		Stock newStock = dao.createStock(stock);
		Assert.assertNotNull(newStock, "Stock creation failure");
		Assert.assertEquals(newStock.getItemId(), itemId, "Stock creation failure");
		Assert.assertEquals(newStock.getStoreId(), storeId, "Stock creation failure");
	}
}
