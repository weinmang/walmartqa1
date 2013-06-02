package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.exception.ApiException;
import com.cloudbees.walmartqa1.exception.NotFoundException;

public class ItemDAO {
	@PersistenceUnit(name="walmartqa1")
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	/**
	 * Simple find by id
	 */
	public Item findById(String itemId) throws ApiException {
		try {
			em.getTransaction().begin();
			Item item = em.find(Item.class, itemId);
			em.getTransaction().commit();
			return item;
		} catch (EntityNotFoundException nf) {
			throw new NotFoundException("Item with Id "+itemId+" NOT FOUND",nf);
		} catch (Exception e) {
			throw new ApiException("Exception retrieving Item",e);
		}
	}

	/**
	 * Simple insert entity
	 */
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
