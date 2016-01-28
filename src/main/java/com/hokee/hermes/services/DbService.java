package com.hokee.hermes.services;

import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {

	private static final Logger logger = LoggerFactory.getLogger(DbService.class);
	private static final String CONNECTION_STRING = "jdbc:postgresql://hermes.cq8xsjkup0br.us-east-1.rds.amazonaws.com:5432/hermes";
	private static final String DB_USERNAME = "hermesftw";
	private static final String DB_PASSWORD = "3ehtTJwEP3xhQZNr";

	private static boolean driverLoaded = false;

	private DbService() {

		// Static Class
	}

	private static void initializeDriver() {

		try {
			Driver.class.newInstance();
			driverLoaded = true;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Error loading PostgreSQL Driver", e);
		}
	}

	private static Connection openConnection() {

		if (!driverLoaded) {
			initializeDriver();
		}

		try {
			return DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			logger.error("Error getting DB connection", e);
			return null;
		}
	}

	public static ResultSet executeQuery(final String sql) {

		ResultSet retVal = null;

		logger.debug("Opening db connection");
		final Connection db = openConnection();
		try {
			final Statement stmt = db.createStatement();
			logger.debug("executing sql: " + sql);
			retVal = stmt.executeQuery(sql);
		} catch (final SQLException e) {
			logger.error("Error executing SQL statement", e);
		} finally {
			try {
				db.close();
			} catch (final SQLException e) {
				logger.error("Error closing SQL connection. YOU CANNOT ESCAPE!", e);
			}
		}

		return retVal;
	}

	public static void execute(final String sql) {

		final Connection db = openConnection();
		try {
			final Statement stmt = db.createStatement();
			stmt.execute(sql);
		} catch (final SQLException e) {
			logger.error("Error executing SQL statement", e);
		} finally {
			try {
				db.close();
			} catch (final SQLException e) {
				logger.error("Error closing SQL connection. YOU CANNOT ESCAPE!", e);
			}
		}
	}
}
