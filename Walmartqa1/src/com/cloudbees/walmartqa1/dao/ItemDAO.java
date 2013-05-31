package com.cloudbees.walmartqa1.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.cloudbees.walmartqa1.dto.Item;

public class ItemDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Item findById(String itemId) {
		Item item = em.find(Item.class, itemId);

		return item;
	}

	public Item createItem(Item item) {
		em.persist(item);

		return item;
	}
}
