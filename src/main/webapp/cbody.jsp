<%@page import="qinyuan.hrmis.domain.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	User user = (User) session.getAttribute("user");
	if (user == null)
		return;
%>
<div class="currentPosition">
	当前位置：<span></span>
</div>
<div class="user">
	[${user.name}] <a href="/hrmis/mdf-password.jsp">修改密码</a> <a
		href="/hrmis/logout.action">退出</a>
</div>
<div class="navi">${user.navi}</div>
<div class="currentNavi">${user.naviDetails}</div>