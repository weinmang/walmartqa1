package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Store;
import com.cloudbees.walmartqa1.exception.ApiException;

public class StoreDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Store findById(String storeId) throws ApiException {
		try {
			em.getTransaction().begin();
			Store store = em.find(Store.class, storeId);
			em.getTransaction().commit();
			return store;
		} catch (EntityNotFoundException nf) {
			return (Store)null;
		} catch (Exception e) {
			throw new ApiException("Exception retrieving Store",e);
		}
	}

	public Store createStore(Store store) throws ApiException {
		try {
			em.getTransaction().begin();
			em.persist(store);
			em.getTransaction().commit();
			return store;
		} catch (EntityExistsException ee) {
			return store;
		} catch (Exception e) {
			throw new ApiException("Exception presisting Store",e);
		}
	}
}
