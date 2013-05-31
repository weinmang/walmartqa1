package com.cloudbees.walmartqa1.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.cloudbees.walmartqa1.dto.Stock;
import com.cloudbees.walmartqa1.dto.StockPK;

public class StockDAO {
	@PersistenceUnit
	EntityManagerFactory emf;

	EntityManager em = emf.createEntityManager(); 
	
	public Stock findById(String itemId, String storeId) {
		StockPK stockPK = new StockPK();
		stockPK.setItemId(itemId);
		stockPK.setStoreId(storeId);
		Stock stock = em.find(Stock.class, stockPK);
		return stock;
	}

	public List<Stock> findByItemId(String itemId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
		Root<Stock> stock = cq.from(Stock.class);
		ParameterExpression<String> parm = cb.parameter(String.class);
		cq.select(stock).where(cb.equal(stock.get("itemId"),parm));
		TypedQuery<Stock> query = em.createQuery(cq);
		query.setParameter(parm, itemId);
		List<Stock> stockList = query.getResultList();
		return stockList;
	}

	public Stock createStock(Stock stock) {
		em.persist(stock);

		return stock;
	}
}
