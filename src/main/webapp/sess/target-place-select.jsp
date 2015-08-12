<%@page import="qinyuan.hrmis.domain.targetplace.TargetPlaceSelect"%>
<%@page import="qinyuan.lib.web.SessParam"%>
<%@page import="qinyuan.hrmis.domain.recruitor.RecruiterSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="/hrmis/sess/target-place-select.js"></script>
<%
	int targetPlaceId = SessParam.getInt(request, "sessTargetPlaceId");
%>
目标地点：<%=new TargetPlaceSelect().setTagId("sessTargetPlaceId")
					.setDefaultId(targetPlaceId).setWithTotal(true)%>