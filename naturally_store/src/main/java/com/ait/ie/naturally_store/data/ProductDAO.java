package com.ait.ie.naturally_store.data;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.ie.naturally_store.model.Product;

@Stateless
@LocalBean
public class ProductDAO {

	 @PersistenceContext
	private EntityManager em;

	public List<Product> getAllProducts() {
		Query query = em.createQuery("SELECT p FROM Product p");
		return query.getResultList();
	}

	public Product getProduct(int productCode) {
		return em.find(Product.class, productCode);
	}

	public void saveProduct(Product product) {
		em.merge(product);	
	}

	public void update(Product product) {
		em.merge(product);
	}

	public void delete(int productCode) {
		em.remove(getProduct(productCode));
	}

	public List<Product> getProductByName(String name) {
		Query q = em.createQuery("SELECT p FROM Product p WHERE p.name = :name");
		q.setParameter("name", name); 
		return q.getResultList();
	}

	public List<Product> getProductBySuitableFor(String suitableFor) {
		Query q = em.createQuery("SELECT p FROM Product p WHERE p.suitableFor = :suitableFor");
		q.setParameter("suitableFor", suitableFor); 
		return q.getResultList();
	}

	public List<Product> getProductByManufacturer(String manufacturer) {
		Query q = em.createQuery("SELECT p FROM Product p WHERE p.manufacturer = :manufacturer");
		q.setParameter("manufacturer", manufacturer); 
		return q.getResultList();
	}

}
