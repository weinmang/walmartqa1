package com.cloudbees.walmartqa1.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseConnectionUtilTest {

	@Test(expectedExceptions = NamingException.class)
	public void getConnectionTest() {
		DatabaseConnectionUtil ds = new DatabaseConnectionUtil();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (NamingException e) {
			// do nothing exception excpected
		} catch (SQLException e) {
			// do nothing exception excpected
		}
		Assert.assertNotNull(conn, "Connection failure");
	}
}
