package com.demo.disc.ProductApp.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.demo.disc.ProductApp.model.Product;
import com.demo.disc.ProductApp.service.ProductService;

@Path("/products")
public class ProductResource {

	ProductService productService = new ProductService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts() {
		return productService.getProductsFromDb();
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Product getTodoById(@PathParam("id") int id) {
		return productService.getProductById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProduct(Product product) {
		productService.addProductToDb(product);
	}

	@PUT
	@Path("/{quantity}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateProduct(@PathParam("quantity") int quantity, Product product) {
		product.setQuantity(quantity);
		productService.updateProductInDb(product);
	}

	@DELETE
	@Path("/{id}")
	public void deleteProduct(@PathParam("id") int id) {
		System.out.println("called from delete resource with id: "+ id);
		productService.deleteProductInDb(id);
	}

}
