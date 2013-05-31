package com.cloudbees.walmartqa1.dao;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.dto.Locale;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;

public class LocaleDAOTest {

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void findByIdTest(Map<String,String> parms) {
		String localeId = parms.get("localeId");
		LocaleDAO dao = new LocaleDAO();
		Assert.assertNotNull(dao, "Could not instantiate data access object");
		Locale locale = dao.findById(localeId);
		Assert.assertNotNull(locale, "Locale Not Found");
		Assert.assertEquals(locale.getLocaleId(), localeId, "Returned locale inconsistant with search");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void createLocaleTest(Map<String,String> parms) {
		String localeId = parms.get("localeId");
		String localeDescr = parms.get("localeDescr");
		Locale locale = new Locale();
		locale.setLocaleId(localeId);
		locale.setLocaleDescr(localeDescr);
		LocaleDAO dao = new LocaleDAO();
		Locale newLocale = dao.createItem(locale);
		Assert.assertNotNull(newLocale, "Locale creation failure");
		Assert.assertEquals(newLocale.getLocaleId(), localeId, "Locale creation failure");
	}
}
