package com.db;

import java.sql.Connection;
import java.time.*;
import java.time.format.DateTimeFormatter;
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

public class reviewSave {

	final static String DB_URL = "jdbc:oracle:thin:@shortcut_medium?TNS_ADMIN=C:/wallet/Wallet_shortcut";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	
	private static reviewSave instance = new reviewSave();
	
    String returns = "a";
	
	public reviewSave(){
		
	}
	
	 public static reviewSave getInstance() {
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
	 public String connectionDB(String title, String content, String lon, String lat, String nick, String add) {
		 int s = 0;
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            LocalDateTime now = LocalDateTime.now();
	            String fNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
	            
	            String sql = "SELECT REVIEWNUM FROM REVIEW";
                String sql2 = "INSERT INTO REVIEW VALUES(?,?,?,?,?,?,?,?,?)";
                pstmt=conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                
                if(rs.next()) {
                	s = rs.getInt(1);
                	while(rs.next()) {
                		s = rs.getInt(1);
                	}
                }
                s+=1;
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1, s);
                pstmt2.setString(2, title);
                pstmt2.setString(3, content);
                pstmt2.setString(4, lon);
                pstmt2.setString(5, lat);
                pstmt2.setString(6, nick);       
                pstmt2.setString(7,  add);
                pstmt2.setString(8, fNow);
                pstmt2.setInt(8, 0);
                pstmt2.executeUpdate();
                returns = "작성 완료 !";
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	            if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	        }
	        return returns;
	    }
	 
	public static void printEmployees() throws SQLException {
		 	Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

		   //SQL문을 실행하기 위해 Statement와 SQL 작성 및 셋하기
		   Statement stmt = conn.createStatement();
		   String str = "insert into TESTTABLE values('rlawhddnjs', 'rlawhdxmzhdls')";
		   //실행
		   int affect = stmt.executeUpdate(str);

		   System.out.println(affect + "건 적용 되었습니다");

		   //닫기

		   stmt.close();

		   conn.close();

		  
	}
}
