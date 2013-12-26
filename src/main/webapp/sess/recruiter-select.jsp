<%@page import="qinyuan.hrmis.domain.user.User"%>
<%@page import="qinyuan.lib.lang.MyMath"%>
<%@page import="qinyuan.lib.web.SessParam"%>
<%@page import="qinyuan.hrmis.domain.recruitor.RecruiterSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="/hrmis/sess/recruiter-select.js"></script>
<%
	int recruiterId = 0;
	if (session.getAttribute("recruiterId") == null
			&& request.getParameter("recruiterId") == null) {
		User localUser = (User) session.getAttribute("user");
		if (localUser.hasPri(6)) {
			recruiterId = localUser.getId();
		}
	} else {
		recruiterId = SessParam.getInt(request, "recruiterId");
	}
%>
创建者：<%=new RecruiterSelect(recruiterId)%>