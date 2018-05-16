package com.ait.ie.naturally_store.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.ie.naturally_store.data.ProductDAO;
import com.ait.ie.naturally_store.model.Product;
import com.ait.ie.naturally_store.rest.JaxRsActivator;
import com.ait.ie.naturally_store.rest.ProductWS;
import com.ait.ie.naturally_store.test.utils.UtilsDAO;


	//	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
		@RunWith(Arquillian.class)
		public class IntegrationTest {
			
			@Deployment
			public static Archive<?> createTestArchive() {
				return ShrinkWrap
						.create(JavaArchive.class, "Test.jar")
						.addClasses(ProductDAO.class, Product.class,
								JaxRsActivator.class,ProductWS.class,
								UtilsDAO.class)
								//this line will pick up the production db
						.addAsManifestResource("META-INF/persistence.xml",
								"persistence.xml")
						.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

			}

			 
			@EJB
			private ProductWS productWS;
			
			@EJB
			private ProductDAO productDAO;
			
			@EJB
			private UtilsDAO utilsDAO;
			 
			@Before
			public void setUp() {
				utilsDAO.deleteTable();
				Product product=new Product();
				product.setName("Cocoa body lotion");
				product.setManufacturer("Ziaja");
				product.setSuitableFor("woman&man");
				product.setSkinType("dry and normal skin");
				product.setCapacity("200 ml");
				product.setDescription("Intensely moisturises and regenerates dry and normal skin. Visibly softens, smoothes and makes the skin more elastic. Eliminates the feeling of roughness and effectively soothes irritations.");
				product.setPicture("cocoa_body_lotion.png");
				productDAO.saveProduct(product);
			}
			
			@Test
			public void testGetAllProducts() {
				List<Product> productsList = productDAO.getAllProducts();
				assertEquals("Data fetch = data persisted", productsList.size(), 1);
			}
			
			
			
}
