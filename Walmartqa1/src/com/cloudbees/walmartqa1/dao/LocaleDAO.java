package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Locale;

public class LocaleDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Locale findById(String localeId) {
		Locale locale = em.find(Locale.class, localeId);

		return locale;
	}

	public Locale createLocale(Locale locale) {
		em.persist(locale);

		return locale;
	}
}
