package com.javapos.jdbc;

import java.sql.*;

public class ConnectionFactory {
	static String dbms = "mysql";
	static String dbName = "oop";
	static String user = "root";
	static String serverName = "localhost";
	static String portNumber = "3306";
	static String password = "1234";
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;

		if (dbms.equals("mysql")) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:" + dbms + "://" +
					serverName +
					":" + portNumber + "/" + dbName,
					user, password);
		} else if (dbms.equals("derby")) {
			conn = DriverManager.getConnection(
					"jdbc:" + dbms + ":" +
					dbName +
					";create=true",
					user, password);
		}
		System.out.println("Connected to database");
		return conn;
	}
}				