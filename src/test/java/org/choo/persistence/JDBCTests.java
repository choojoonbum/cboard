package org.choo.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	
	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		try(Connection con = 
				DriverManager.getConnection("jdbc:mariadb://localhost:3306/bootex","bootuser","bootuser")) {
			log.info(con);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
