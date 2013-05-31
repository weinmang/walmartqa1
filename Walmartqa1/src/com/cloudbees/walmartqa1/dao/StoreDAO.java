package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Store;

public class StoreDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Store findById(String storeId) {
		Store store = em.find(Store.class, storeId);

		return store;
	}

	public Store createStore(Store store) {
		em.persist(store);

		return store;
	}
}
