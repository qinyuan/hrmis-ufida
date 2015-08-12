<%@page import="qinyuan.lib.web.SessParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/hrmis/sess/end-date.css" />
<script src="/hrmis/sess/end-date.js"></script>
<%
	String sessEndDate = SessParam.getStr(request, "sessEndDate");
%>
结束日期：<input type="text" id="sessEndDate" value="<%=sessEndDate%>"
	readonly="readonly" />