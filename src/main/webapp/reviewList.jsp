<%@page import="com.db.reviewList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
   reviewList DB = reviewList.getInstance();

	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");

   
   
   String returns = DB.ConDB();

   System.out.println(returns);

   // 안드로이드로 전송
   out.println(returns);

%>