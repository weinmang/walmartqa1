package com.cloudbees.walmartqa1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.cloudbees.walmartqa1.cvo.ItemStockComposite;
import com.cloudbees.walmartqa1.cvo.StoreStockComposite;
import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.dto.Locale;
import com.cloudbees.walmartqa1.util.DatabaseConnectionUtil;

public class StoreStockDAO {
	public static final String selById = "SELECT item.itemid, item.itemdescr, item.itemprice, " +
			                             "locale.localedescr, stock.itemQty, store.storedescr, store.storeaddress  " +
			                             "FROM item, locale, stock, store " +
			                             "WHERE item.itemid = '?'" +
			                             "AND item.itemid = stock.itemid " +
			                             "AND item.localeId = locale.localeId " +
			                             "AND item.localeId = store.localeId " +
			                             "AND stock.itemQty > 0"; 
	
	public ItemStockComposite findById(String itemId, String localeId) {
		List<StoreStockComposite> inventory = new ArrayList<StoreStockComposite>();
		Item item = null;
		Locale locale = null;
		StoreStockComposite storeStock = null; 
		ItemStockComposite composite = new ItemStockComposite();
		DatabaseConnectionUtil dbUtil = new DatabaseConnectionUtil();
		try (Connection conn = dbUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(selById);
			ps.setString(1, itemId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (item==null) {
					item = new Item();
					item.setItemId(itemId);
					item.setItemDescr(rs.getString("itemdescr"));
					item.setItemPrice(rs.getDouble("itemprice"));
					item.setLocaleId(localeId);
					composite.setItem(item);
				}
				if (locale==null) {
					locale = new Locale();
					locale.setLocaleId(localeId);
					locale.setLocaleDescr(rs.getString("localedescr"));
					composite.setLocale(locale);
				}
				storeStock = new StoreStockComposite();
				storeStock.setStoreDescr(rs.getString("storedescr"));
				storeStock.setStoreAddress(rs.getString("storeaddress"));
				storeStock.setItemQty(rs.getInt("itemQty"));
				inventory.add(storeStock);
			}
			composite.setInventory(inventory);
			
		} catch (NamingException|SQLException e) {
			e.printStackTrace();
		}
		return composite;
	}

}
