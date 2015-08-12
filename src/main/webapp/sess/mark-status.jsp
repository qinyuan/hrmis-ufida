<%@page import="qinyuan.lib.web.html.Select"%>
<%@page import="qinyuan.lib.web.SessParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="/hrmis/sess/mark-status.js"></script>
<%
	String paramName = "sessMarkStatus";
	int markStatus = SessParam.getInt(request, paramName);
	Select select = new Select();
	select.setId(paramName);
	select.addOption(0, "（全部）").addOption(1, "已标记").addOption(2, "未标记");
	select.select(markStatus);
%>
标记状态：<%=select%>