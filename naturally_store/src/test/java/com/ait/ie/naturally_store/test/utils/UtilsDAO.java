package com.ait.ie.naturally_store.test.utils;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.ie.naturally_store.model.*;

@Stateless
@LocalBean
public class UtilsDAO {
	
	   @PersistenceContext
	    private EntityManager em;
	    
		public void deleteTable(){
			em.createQuery("DELETE FROM Product").executeUpdate();
			em.createNativeQuery("ALTER TABLE product AUTO_INCREMENT=1")
			.executeUpdate();
			
		}
	      
}
