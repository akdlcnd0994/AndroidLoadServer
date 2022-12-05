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

public class reviewList {

	final static String DB_URL = "jdbc:oracle:thin:@shortcut_medium?TNS_ADMIN=C:/wallet/Wallet_shortcut";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	String num,title,content, nick, add, fNow, up;

	double lon, lat;
	String splitKey = "§§§§§§§%~#";
	String LineKey = "@#@~#!#@#~#%";
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	
	private static reviewList instance = new reviewList();
	
    String returns ="";
	
	public reviewList(){
		
	}
	
	 public static reviewList getInstance() {
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
	 public String ConDB() {
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            returns = "";
	            String sql = "SELECT * FROM REVIEW";
	            
	            String Point= null;
	            int check=0;
	            pstmt = conn.prepareStatement(sql);	
	            
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	check++;
	            	num = rs.getString(1);
	            	title = rs.getString(2);
	            	content = rs.getString(3);
	            	lon= rs.getDouble(4);
	            	lat = rs.getDouble(5);
	            	nick = rs.getString(6);
	            	add = rs.getString(7);
	            	fNow = rs.getString(8);
	            	up = rs.getString(9);
	            	
	            	
	            	returns += num + splitKey + title + splitKey + content  + splitKey + lon + splitKey+lat + splitKey+nick + splitKey+ add + splitKey + fNow + splitKey + up + LineKey;
	            }
	            if(check==0) {
	                returns = "작성된 글이 없습니다.";
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
