package lu.lllc;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {
	public static Connection getConnection() throws SQLException {
		DataSource dataSource = lookupDataSource();

		Connection connection = dataSource.getConnection();

		return connection;
	}

	private static DataSource lookupDataSource() {
		try {
			Context initialContext = new InitialContext();
			try {
				return (DataSource) initialContext.lookup(System.getenv("DB_JNDI"));
			} catch (NameNotFoundException e) {
				Context envContext = (Context) initialContext.lookup("java:comp/env"); // Tomcat places datasources
																						// inside java:comp/env
				return (DataSource) envContext.lookup(System.getenv("DB_JNDI"));
			}
		} catch (NamingException e) {
			throw new RuntimeException("Could not look up datasource", e);
		}
	}

}
