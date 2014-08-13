<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.Config"%>
<% Config.setAllConfig(); %>

<% String url = "/admin/Login.do";%>

<jsp:forward page="<%=url%>"></jsp:forward>