package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.exception.ApiException;

public class ItemDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Item findById(String itemId) throws ApiException {
		try {
			em.getTransaction().begin();
			Item item = em.find(Item.class, itemId);
			em.getTransaction().commit();
			return item;
		} catch (EntityNotFoundException nf) {
			return (Item)null;
		} catch (Exception e) {
			throw new ApiException("Exception retrieving Item",e);
		}
	}

	public Item createItem(Item item) throws ApiException {
		try {
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			return item;
		} catch (EntityExistsException ee) {
			return item;
		} catch (Exception e) {
			throw new ApiException("Exception persisting Item",e);
		}
	}
}
