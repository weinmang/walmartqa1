package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Locale;
import com.cloudbees.walmartqa1.exception.ApiException;

public class LocaleDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Locale findById(String localeId) throws ApiException {
		try {
			em.getTransaction().begin();
			Locale locale = em.find(Locale.class, localeId);
			em.getTransaction().commit();
			return locale;
		} catch (EntityNotFoundException nf) {
			return (Locale)null;
		} catch (Exception e) {
			throw new ApiException("Exception retrieving Locale",e);
		}
	}

	public Locale createLocale(Locale locale) throws ApiException {
		try {
			em.getTransaction().begin();
			em.persist(locale);
			em.getTransaction().commit();
			return locale;
		} catch (EntityExistsException ee) {
			return locale;
		} catch (Exception e) {
			throw new ApiException("Exception persisting Locale",e);
		}
	}
}
