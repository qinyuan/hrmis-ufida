<%@page import="qinyuan.lib.web.SessParam"%>
<%@page import="qinyuan.hrmis.domain.demand.DemandSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="/hrmis/sess/demand-select.js"></script>
<%@include file="post-select.jsp"%>
<%
	int demandId=SessParam.getInt(request, "demandId");
%>
推送需求：<%=new DemandSelect().setPostId(postId).setDemandId(demandId)%>