<%@page import="qinyuan.lib.web.SessParam"%>
<%@page import="qinyuan.hrmis.domain.recommend.StatusSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="/hrmis/sess/resume-status.js"></script>
<%
	int sessResumeStatusId = SessParam
			.getInt(request, "sessResumeStatusId");
%>
简历状态：<%=new StatusSelect("sessResumeStatusId")
					.setStatusId(sessResumeStatusId)%>