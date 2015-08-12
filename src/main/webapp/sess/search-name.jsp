<%@taglib uri="http://853609848.qzone.qq.com" prefix="q"%>
<%@page import="qinyuan.lib.web.SessParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="/hrmis/sess/search-name.css" />
<script src="/hrmis/sess/search-name.js"></script>
<%
	String sessSearchName = SessParam.getStr(request, "sessSearchName");
	String sessSearchTel = SessParam.getStr(request, "sessSearchTel");
%>
<div id="searchBoxDiv">
	<span class="label">姓名搜索：</span><input class="easyui-searchbox"
		data-options="prompt:'在此输入姓名',searcher:doSearchName"
		style="width: 120px" value="<%=sessSearchName%>"></input> <br /> <br />
	<span class="label">电话搜索：</span><input class="easyui-searchbox"
		data-options="prompt:'在此输入电话',searcher:doSearchTel"
		style="width: 120px" value="<%=sessSearchTel%>"></input> <br />
	<br />
	<q:cancel id="clearFilterButton" text="清空筛选条件" />
</div>