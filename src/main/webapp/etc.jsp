<%@page import="com.db.etc"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	etc et = etc.getInstance();

	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");
	
   String sel = request.getParameter("sel");
   String el1 = request.getParameter("element1");
   String el2 = request.getParameter("element2");
   String el3 = request.getParameter("element3");
   String el4 = request.getParameter("element4");
	String returns ="";
   //1=추천, 2=신고하기, 3=내리뷰, 4=내정보
   
   if(sel.equals("1")){
	   returns = et.up(el1, el2);
   }
   else if(sel.equals("2")){
	   returns = et.report(el1,el2,el3,el4);
   }
   else if(sel.equals("3")){
	   
   }
   else{
	   
   }
  	

   
   System.out.println(returns);

   // 안드로이드로 전송
   out.println(returns);
%>