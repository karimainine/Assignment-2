package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
	private static String url = "jdbc:oracle:thin:@emu.cs.rmit.edu.au:1521:GENERAL";
	private static String username = "s3314713";
	private static String password = "Password1";
	private static Connection conn;
	private static Statement stmt;

	private static PreparedStatement prep_stmt;

	public DatabaseConnection() {
		super();
	}

	public static ResultSet executeQuery(String query) throws Exception {
		conn = DriverManager.getConnection(url, username, password);

		conn.setAutoCommit(false);
		stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		return rset;
	}

	public static PreparedStatement getPreparedStatememt(String sql) throws Exception{
		if (conn.isClosed()) {
			conn = DriverManager.getConnection(url, username, password);
		}
		prep_stmt = conn.prepareStatement(sql);
		return prep_stmt;
	}

	public static void closeConnections() throws Exception {
		stmt.close();
		prep_stmt.close();
		conn.close();
	}
}
