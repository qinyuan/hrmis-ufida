<%@page import="qinyuan.lib.web.SessParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/hrmis/sess/start-date.css" />
<script src="/hrmis/sess/start-date.js"></script>
<%
	String sessStartDate=SessParam.getStr(request, "sessStartDate");
%>
开始日期：<input type="text" id="sessStartDate" value="<%=sessStartDate%>" readonly="readonly" />