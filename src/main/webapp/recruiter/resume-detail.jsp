<%@page import="qinyuan.hrmis.dao.ResumeDao"%>
<%@page import="qinyuan.hrmis.domain.recommend.SimpleRecommendTable"%>
<%@page import="qinyuan.lib.lang.MyMath"%>
<%@page import="qinyuan.hrmis.domain.resume.ResumeDetailTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	int resumeId = Integer.parseInt(request.getParameter("id"));
%>
<title>简历明细-<%=ResumeDao.getApplicantById(resumeId)%></title>
<%@include file="/chead.jsp"%>
<q:css href="/recruiter/resume-detail" />
<q:js src="/recruiter/resume-detail" />
</head>
<body>
	<div class="body">
		<%
			String idStr = request.getParameter("id");
			if (!MyMath.isNumeric(idStr))
				return;
			int id = Integer.parseInt(idStr);
		%>
		<q:panel title="简历明细">
			<%=new ResumeDetailTable(id)%>
		</q:panel>
		<q:panel title="推荐记录">
			<%=new SimpleRecommendTable(id)%>
		</q:panel>
		<q:panel title="简历内容">
			<%=ResumeDao.getContentById(id)%>
		</q:panel>
	</div>

	<div id="exportButtonDiv">
		<img alt="导出Word" src="/hrmis/recruiter/exportWordButton.png"
			id="exportButton">
	</div>

	<div id="exportOptionDiv">
		<div>
			<fieldset>
				<legend>导出选项</legend>
				<input type="checkbox" id="exportTel" checked="checked" />导出联系方式
			</fieldset>
			<br />
			<q:ok id="exportOk" />
		</div>
	</div>
</body>
</html>