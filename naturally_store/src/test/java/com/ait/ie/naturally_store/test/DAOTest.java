package com.ait.ie.naturally_store.test;

import static org.junit.Assert.*;

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
public class DAOTest {
	
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
		List<Product> productsList = productDAO.getAllProducts();
		assertEquals("Data fetch = data persisted", productsList.size(), 2);
	}
	
	@Test
	public void testProductByProductCode() {
		Product product = productDAO.getProduct(1);
		assertEquals(product.getProductCode(), 1);
		assertEquals(product.getName(), "Cocoa body lotion");
		assertEquals(product.getManufacturer(), "Ziaja");
		assertEquals(product.getSuitableFor(), "woman&man");
		assertEquals(product.getSkinType(), "dry and normal skin");
		assertEquals(product.getCapacity(), "200 ml");
		assertEquals(product.getDescription(), "Intensely moisturises and regenerates dry and normal skin. Visibly softens, smoothes and makes the skin more elastic. Eliminates the feeling of roughness and effectively soothes irritations.");
		assertEquals(product.getPicture(), "cocoa_body_lotion.png");
		product = productDAO.getProduct(2);
		assertEquals(product.getProductCode(), 2);
		assertEquals(product.getName(), "Eucalyptus essential oil");
		assertEquals(product.getManufacturer(), "Bioaroma");
		assertEquals(product.getSuitableFor(), "woman&man");
		assertEquals(product.getSkinType(), "NA");
		assertEquals(product.getCapacity(), "5 ml");
		assertEquals(product.getDescription(), "Breathe freely during colds by adding 8 drops of eucalyptus essential oil in a bowl with boiling water and inhale the vapors.");
		assertEquals(product.getPicture(), "Eucalyptus_Oil.png");
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
		productDAO.saveProduct(product);
		List<Product> productList = productDAO.getAllProducts();
		assertEquals(productList.size(), 3);
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
	public void testRamoveProduct() {
		List<Product> productList = productDAO.getAllProducts();
		assertEquals(productList.size(), 2);
		productDAO.delete(2);
		productList = productDAO.getAllProducts();
		assertEquals(productList.size(), 1);
		assertEquals(null, productDAO.getProduct(2));
	}
	
	@Test
	public void testUpdateProduct() {
		Product product = productDAO.getProduct(1);
		product.setPicture("newPic.jpg");
		product.setCapacity("250 ml");
		productDAO.update(product);
		productDAO.getProduct(2);
		assertEquals(product.getPicture(), "newPic.jpg");
		assertEquals(product.getCapacity(), "250 ml");
		assertEquals(product.getName(),"Cocoa body lotion");
	}
	
	@Test
	public void testSearchProductByName(){
		List<Product> productList = productDAO.getAllProducts();
		assertEquals(productList.size(), 2);
		Product product = productList.get(0);
		assertEquals("Cocoa body lotion", product.getName());
		assertEquals("Ziaja", product.getManufacturer());
	}
	
	@Test
	public void testSearchProductByManufacurer(){
		List<Product> productList = productDAO.getAllProducts();
		assertEquals(productList.size(), 2);
		Product product = productList.get(0);
		assertEquals("Ziaja", product.getManufacturer());
		assertEquals("200 ml", product.getCapacity());
	}
	
	@Test
	public void testSearchProductBySuitableFor(){
		List<Product> productList = productDAO.getAllProducts();
		assertEquals(productList.size(), 2);
		Product product = productList.get(0);
		assertEquals("Cocoa body lotion", product.getName());
		assertEquals("Ziaja", product.getManufacturer());
		assertEquals("woman&man", product.getSuitableFor());
	}
}
