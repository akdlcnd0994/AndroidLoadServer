package com.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.catalina.connector.Connector;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class loginDB {

	final static String DB_URL = "jdbc:oracle:thin:@shortcut_medium?TNS_ADMIN=C:/wallet/Wallet_shortcut";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	
	private static loginDB instance = new loginDB();
	
    String returns = "a";
	
	public loginDB(){
		
	}
	
	 public static loginDB getInstance() {
	        return instance;
	 }
	
	public void start() throws SQLException {
		Properties info = new Properties();
		info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
		info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
		info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, 20);

		OracleDataSource ods = new OracleDataSource();
		ods.setURL(DB_URL);
		ods.setConnectionProperties(info);

		// With AutoCloseable, the connection is closed automatically.
		try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
			// Get the JDBC driver name and version
			DatabaseMetaData dbmd = connection.getMetaData();
			System.out.println("Driver Name: " + dbmd.getDriverName());
			System.out.println("Driver Version: " + dbmd.getDriverVersion());
			// Print some connection properties
			System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
			System.out.println("Database Username is: " + connection.getUserName());
			System.out.println();
			// Perform a database operation
		}
	}
	
	
	

	/*
	 * Displays first_name and last_name from the employees table.
	 */
	 public String ConDB(String id, String pw) {
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            String sql = "SELECT NICKNAME FROM ACCOUNT WHERE id = ? and pw = ?";
	            String sql2 = "SELECT POINT, BAN FROM ACCOUNT WHERE id = ?";
	            String Point= null;
	            String Ban= null;
	            pstmt = conn.prepareStatement(sql);	
	            pstmt.setString(1, id);
	            pstmt.setString(2, pw);
	            pstmt2 = conn.prepareStatement(sql2);
	            pstmt2.setString(1, id);
	            ResultSet rs = pstmt.executeQuery();
	            ResultSet rs2 = pstmt2.executeQuery();
	            if (rs.next()) {
	            	String name = rs.getString(1);
	            	if(rs2.next()) {
	            		Point = rs2.getString("POINT");
	            		Ban = rs2.getString("BAN");
	            	}
	            	if(Point==null) {
	            		Point = "0";
	            	}
	            	
	            	if(Ban.equals("1")) {
	            		returns = "정지된 계정입니다.";
	            	}
	            	else {
	            	returns = "로그인 성공!" + name + "!"+ Point;
	            	}
	            } else {
	                returns = "아이디 또는 비밀번호를 확인해주세요...";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	            if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	        }
	        return returns;
	    }
	 
}
