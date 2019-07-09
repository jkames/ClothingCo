package com.demo.disc.ProductApp.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

import com.demo.disc.ProductApp.database.DatabaseClass;
import com.demo.disc.ProductApp.model.Product;

public class ProductService {

	public List<Product> getProductsFromDb() {
		Connection connection = DatabaseClass.getConnection();
		ResultSet resultSet = DatabaseClass.retrieveQueryResults(connection, "SELECT * FROM clothes");
		List<Product> products = new ArrayList<Product>();
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt("id"));
					product.setProductName(resultSet.getString("name"));
					product.setProductDesc(resultSet.getString("description"));
					product.setPrice(resultSet.getBigDecimal("price"));
					product.setPic(resultSet.getString("pic"));
					products.add(product);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	public Product getProductById(int id) {
		Connection connection = DatabaseClass.getConnection();
		ResultSet resultSet = DatabaseClass.retrieveQueryResults(connection,
				"SELECT * FROM clothes WHERE id =" + id);
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt("id"));
					product.setProductName(resultSet.getString("name"));
					product.setProductDesc(resultSet.getString("description"));
					product.setPic(resultSet.getString("pic"));
					product.setPrice(resultSet.getBigDecimal("price"));
					return product;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void addProductToDb(Product product) {
		String sql = "INSERT INTO cart (id, name,description, price, quantity)" + "VALUES (?, ?, ?, ?, ?)";
		Connection connection = DatabaseClass.getConnection();
		DatabaseClass.addToDatabase(connection, sql, product.getProductId(), product.getProductName(),
				product.getProductDesc(), product.getPrice(), product.getQuantity());
		System.out.println("added to database");
	}

	public void updateProductInDb(Product product) {
		String sql = "UPDATE cart SET quantity=? WHERE id=?";
		Connection connection = DatabaseClass.getConnection();
		DatabaseClass.updateInDatabase(connection, sql, product.getProductId(), product.getQuantity());
	}

	public void deleteProductInDb(int id) {
		String sql = "DELETE FROM cart WHERE id=?";
		Connection connection = DatabaseClass.getConnection();
		DatabaseClass.deleteInDatabase(connection, sql, id);
	}
}
