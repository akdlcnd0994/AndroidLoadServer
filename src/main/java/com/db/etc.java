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

public class etc {

	final static String DB_URL = "jdbc:oracle:thin:@shortcut_medium?TNS_ADMIN=C:/wallet/Wallet_shortcut";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	
	private static etc instance = new etc();
    String returns = "a";
	
	public etc(){
		
	}
	
	 public static etc getInstance() {
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
	public String report(String title, String content, String time, String reviewnum) {
		
		int temp = 0;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String select = "select repnum from report";
            String sql = "insert into report values(?, ?, ?, ?, ?)";
            
            pstmt= conn.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
            	temp = rs.getInt(1);
            	while(rs.next()) {
            		temp = rs.getInt(1);
            	}
            }
        	temp+=1;
            
            pstmt = conn.prepareStatement(sql);
        
            pstmt.setInt(1, temp);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setString(4, time);
            pstmt.setString(5, reviewnum);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                returns = "신고처리되었습니다.";
            } else {
                returns = "존재하지 않는 정보입니다.";
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
	
	
	 public String up(String reviewNum, String nickname) {
		 String temp="";
		 int num=0, logNum=0;
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            String id = "select id from account where nickname = ?";
	            String sel = "select id from log where reviewNum = ?";
	            String n = "select lognum from log";
	            String sql = "update review set up = up+1 where reviewNum = ?";
	            String sql2 = "insert into log values(?,?,?)";
	            
	        
	            pstmt = conn.prepareStatement(id);
	            pstmt.setString(1, nickname);
	            ResultSet rs = pstmt.executeQuery();
	            while(rs.next()) {
	            	temp = rs.getString(1);
	            }
	            
	            pstmt = conn.prepareStatement(sel);
	            pstmt.setString(1, reviewNum);
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	            	String r = rs.getString(1);
	            	if(r.equals(temp)) {
	            		num=1;
	            	}
	            }
	            
	            if(num==1) {
	            	returns = "이미 추천한 게시글입니다.";
	            	return returns;
	            }
	            pstmt = conn.prepareStatement(sql);
	        
	            int rev = Integer.parseInt(reviewNum);
	            pstmt.setInt(1, rev);

	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                returns = "추천되었습니다.";
	            }
	            
	            pstmt = conn.prepareStatement(n);
	            
	            rs= pstmt.executeQuery();
	            if(rs.next()) {
	            	logNum = rs.getInt(1);
	            	while(rs.next()) {
	            		logNum = rs.getInt(1);
	            	}
	            }
	            logNum+=1;
	            
	            pstmt = conn.prepareStatement(sql2);
	            pstmt.setInt(1, logNum);
	            pstmt.setString(2, temp);
	            pstmt.setInt(3, Integer.parseInt(reviewNum));
	            rs=pstmt.executeQuery();
	            
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
