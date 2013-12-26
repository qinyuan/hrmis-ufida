<%@page import="qinyuan.hrmis.domain.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	User user = (User) session.getAttribute("user");
	if (user == null)
		return;
%>