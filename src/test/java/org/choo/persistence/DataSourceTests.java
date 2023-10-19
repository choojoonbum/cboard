package org.choo.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.choo.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class DataSourceTests {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	

	@Test
	public void testConnection() {
		try (Connection con = dataSource.getConnection()) {
			log.info("testConnection");
			log.info(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMyBatis() {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				Connection con = session.getConnection();
				) {
			log.info(session);
			log.info(con);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}


}
