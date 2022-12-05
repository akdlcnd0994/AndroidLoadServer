<%@page import="com.db.reviewSave"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
   reviewSave connectDB = reviewSave.getInstance();

	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");

   String id = request.getParameter("title");
   String pw = request.getParameter("content");
   String lon = request.getParameter("longitude");
   String lat = request.getParameter("latitude");
   String nick = request.getParameter("nickname");
   String add = request.getParameter("address");
   
   String returns = connectDB.connectionDB(id, pw, lon,lat,nick, add);

   System.out.println(returns);

   // 안드로이드로 전송
   out.println(returns);

%>