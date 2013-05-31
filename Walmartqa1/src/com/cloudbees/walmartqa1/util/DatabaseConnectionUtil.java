package com.cloudbees.walmartqa1.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnectionUtil {

	private Connection conn = null;
	
	public Connection getConnection() throws NamingException, SQLException {
		if (conn!=null) return conn;
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/testDB");
		conn = ds.getConnection();
		return conn;
	}
}
