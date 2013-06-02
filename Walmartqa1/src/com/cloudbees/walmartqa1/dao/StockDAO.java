package com.cloudbees.walmartqa1.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.cloudbees.walmartqa1.dto.Stock;
import com.cloudbees.walmartqa1.dto.StockPK;
import com.cloudbees.walmartqa1.exception.ApiException;

public class StockDAO {
	@PersistenceUnit(name="walmartqa1")
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	/**
	 * Simple find by id
	 */
	public Stock findById(String itemId, String storeId) throws ApiException {
		StockPK stockPK = new StockPK();
		stockPK.setItemId(itemId);
		stockPK.setStoreId(storeId);

		try {
			em.getTransaction().begin();
			Stock stock = em.find(Stock.class, stockPK);
			em.getTransaction().commit();
			return stock;
		} catch (EntityNotFoundException nf) {
			return (Stock)null;
		} catch (Exception e) {
			throw new ApiException("Exception retrieving Stock",e);
		}
	}

	/**
	 * Simple find list by partial id
	 */
	public List<Stock> findByItemId(String itemId) throws ApiException {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
			Root<Stock> stock = cq.from(Stock.class);
			ParameterExpression<String> parm = cb.parameter(String.class);
			cq.select(stock).where(cb.equal(stock.get("itemId"),parm));
			TypedQuery<Stock> query = em.createQuery(cq);
			query.setParameter(parm, itemId);
			List<Stock> stockList = query.getResultList();
			return stockList;
		} catch (EntityNotFoundException nf) {
			return (List<Stock>)null;
		} catch (Exception e) {
			throw new ApiException("Exception retrieving Stock",e);
		}
	}

	/**
	 * Simple insert entity
	 */
	public Stock createStock(Stock stock) throws ApiException {
		try {
			em.getTransaction().begin();
			em.persist(stock);
			em.getTransaction().commit();
			return stock;
		} catch (EntityExistsException ee) {
			return stock;
		} catch (Exception e) {
			throw new ApiException("Exception presisting Stock",e);
		}
	}
}
