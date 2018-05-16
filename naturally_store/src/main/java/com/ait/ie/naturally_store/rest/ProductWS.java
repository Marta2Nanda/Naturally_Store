package com.ait.ie.naturally_store.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.ie.naturally_store.data.ProductDAO;
import com.ait.ie.naturally_store.model.Product;

@Path("/products")
@Stateless
@LocalBean
public class ProductWS {

	@EJB
	private ProductDAO productsDAO;
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll() {
		List<Product> products = productsDAO.getAllProducts();
		return Response.status(200).entity(products).build();
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public Response saveProduct(Product product) {
		productsDAO.saveProduct(product);
		return Response.status(201).entity(product).build();
	}
	
	@PUT
	@Path("{productCode}")
	@Consumes("application/json")
	@Produces({MediaType.APPLICATION_JSON})
	public Response updateProduct(Product product) {
		productsDAO.update(product);
		return Response.status(200).entity(product).build();
	}
	
	@DELETE
	@Path("{productCode}")
	public Response deleteProduct(@PathParam("productCode") int productCode) {
		productsDAO.delete(productCode);
		return Response.status(204).build();
	}
	
	@GET
	@Path("name/{name}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findProductByName(@PathParam("name") String name) {
		List<Product> product = productsDAO.getProductByName(name);
		return Response.status(200).entity(product).build();
	}
	
	@GET
	@Path("suitable/{suitableFor}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findProductBySuitableFor(@PathParam("suitableFor") String suitableFor) {
		List<Product> product = productsDAO.getProductBySuitableFor(suitableFor);
		return Response.status(200).entity(product).build();
	}
	
	@GET
	@Path("manufacturer/{manufacturer}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findProductByManufacturer(@PathParam("manufacturer") String manufacturer) {
		List<Product> product = productsDAO.getProductByManufacturer(manufacturer);
		return Response.status(200).entity(product).build();
	}
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("{productCode}")
	public Response findProductByProductCode(@PathParam("productCode") String productCode) {
		Product product = productsDAO.getProduct(Integer.parseInt(productCode));
		return Response.status(200).entity(product).build();
	}
	
}
