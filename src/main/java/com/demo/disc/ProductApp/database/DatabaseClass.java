package com.demo.disc.ProductApp.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseClass {

	public static Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("here");
		}
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/myDB", "root", "inf124");
		} catch (SQLException e) {
			throw new RuntimeException("here2 " + e.getMessage());
		}
	}

	public static ResultSet retrieveQueryResults(Connection connection, String sql) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return resultSet;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public static void addToDatabase(Connection connection, String sql, int productId, String productName,
									 String productDesc, BigDecimal price, int quantity) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			preparedStatement.setString(2, productName);
			preparedStatement.setString(3, productDesc);
			preparedStatement.setBigDecimal(4, price);
			preparedStatement.setInt(5, quantity);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateInDatabase(Connection connection, String sql, int productId, int productQuant) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productQuant);
			preparedStatement.setInt(2, productId);
			preparedStatement.executeUpdate();
			System.out.println("updated in database");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteInDatabase(Connection connection, String sql, int productId) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
