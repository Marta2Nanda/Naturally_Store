package com.ait.ie.naturally_store.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
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

@RunWith(Arquillian.class)
public class WSTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "Test.jar")
				.addClasses(ProductDAO.class, Product.class,
						JaxRsActivator.class,ProductWS.class, UtilsDAO.class)
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
		product=new Product();
		product.setName("Eucalyptus essential oil");
		product.setManufacturer("Bioaroma");
		product.setSuitableFor("woman&man");
		product.setSkinType("NA");
		product.setCapacity("5 ml");
		product.setDescription("Breathe freely during colds by adding 8 drops of eucalyptus essential oil in a bowl with boiling water and inhale the vapors.");
		product.setPicture("Eucalyptus_Oil.png");
		productDAO.saveProduct(product);
	}
	@Test
	public void testGetAllProducts() {
	Response response=productWS.findAll();
	List<Product> productList = (List<Product>) response.getEntity();
	assertEquals(HttpStatus.SC_OK, response.getStatus());
	assertEquals("Data fetch = data persisted", productList.size(), 2);
	Product product=productList.get(0);
	assertEquals("Cocoa body lotion",product.getName());
	product=productList.get(1);
	assertEquals("Eucalyptus essential oil",product.getName());
	} 
	 
	
	@Test
	public void testProductByProductCode() {
		Response response=productWS.findProductByProductCode("1");
		Product product = (Product) response.getEntity();
		assertEquals(product.getProductCode(), 1);
		assertEquals(product.getName(), "Cocoa body lotion");
		assertEquals(product.getManufacturer(), "Ziaja");
		assertEquals(product.getSuitableFor(), "woman&man");
		assertEquals(product.getSkinType(), "dry and normal skin");
		assertEquals(product.getCapacity(), "200 ml");
		assertEquals(product.getDescription(), "Intensely moisturises and regenerates dry and normal skin. Visibly softens, smoothes and makes the skin more elastic. Eliminates the feeling of roughness and effectively soothes irritations.");
		assertEquals(product.getPicture(), "cocoa_body_lotion.png");
	}
	
	@Test
	public void testAddProduct() {
		Product product = new Product();
		product.setName("New test product");
		product.setManufacturer("New test manufacturer");
		product.setSuitableFor("woman&man");
		product.setSkinType("normal");
		product.setCapacity("500 ml");
		product.setDescription("newly added in arquillian");
		product.setPicture("test_product.jpg");
		Response response=productWS.saveProduct(product);
		assertEquals(HttpStatus.SC_CREATED, response.getStatus());
		product = (Product) response.getEntity();
		assertEquals(product.getProductCode(), 3);
		assertEquals(product.getName(), "New test product");
		assertEquals(product.getManufacturer(), "New test manufacturer");
		assertEquals(product.getSuitableFor(), "woman&man");
		assertEquals(product.getSkinType(), "normal");
		assertEquals(product.getCapacity(), "500 ml");
		assertEquals(product.getDescription(), "newly added in arquillian");
		assertEquals(product.getPicture(), "test_product.jpg");
	}
	
	@Test
	public void testRemoveProduct() {
		Response response=productWS.findAll();
		List<Product> productList = (List<Product>) response.getEntity();
		assertEquals(productList.size(), 2);
		productWS.deleteProduct(2);
		productList = productList = (List<Product>) response.getEntity();
		assertEquals(2, productList.size());
		response=productWS.findProductByProductCode("2");
		Product product = (Product) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(null, product);
		
	}
	
	@Test
	public void testUpdateProduct() {
		Response response=productWS.findProductByProductCode("1");
		Product product = (Product) response.getEntity();
		product.setPicture("newPic.jpg");
		product.setCapacity("250 ml");
		response=productWS.updateProduct(product);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		product = (Product) response.getEntity();
		assertEquals(product.getPicture(), "newPic.jpg");
		assertEquals(product.getCapacity(), "250 ml");
	}
	
	@Test
	public void testSearchProductByName() {
		Response response=productWS.findProductByName("Cocoa body lotion");
		List<Product> productList = (List<Product>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(productList.size(), 1);
		Product product=productList.get(0);
		assertEquals(product.getName(), "Cocoa body lotion");
		assertEquals(product.getManufacturer(), "Ziaja");
		
	} 
	@Test
	public void testSearchProductBySuitableFor() {
		Response response=productWS.findProductBySuitableFor("woman&man");
		List<Product> productList = (List<Product>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(2,productList.size());
		Product product=productList.get(0);
		assertEquals(product.getName(), "Cocoa body lotion");
		assertEquals(product.getManufacturer(), "Ziaja");
		assertEquals(product.getSkinType(), "dry and normal skin");
	} 
	@Test
	public void testSearchProductBySkinType() {
		Response response=productWS.findProductByManufacturer("Ziaja");
		List<Product> productList = (List<Product>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(productList.size(), 1);
		Product product=productList.get(0);
		assertEquals(product.getName(), "Cocoa body lotion");
		assertEquals(product.getProductCode(), 1);
		
	} 
}
