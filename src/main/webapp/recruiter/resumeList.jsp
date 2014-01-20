<%@page import="qinyuan.hrmis.domain.table.ResumeTable"%>
<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.resume.ResumeFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历列表</title>
<%@include file="/chead.jsp"%>
<%@include file="resumeListHead.jsp"%>
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>

		<div class="main">
			<div id="filterDiv">
				<%@include file="resumeFilter.jsp"%>
			</div>
			<%=table.setUser(user)%>
		</div>

		<div id="addResumeDiv">
			<img alt="添加简历" src="/hrmis/recruiter/toAddPageButton.png"
				id="addResumeButton">
		</div>

		<div id="mdfFeedbackDiv">
			<form id="myResumeForm" action="my-resume.action" method="post">
				<textarea id="feedbackText" name="feedbackText" rows="5" cols="50"></textarea>
				<q:hidden id="feedbackMdfRecommendId" />
				<p>
					<q:ok id="mdfFeedbackOk" />
					<q:cancel id="mdfFeedbackCancel" />
				</p>
			</form>
		</div>

		<div id="recommendDiv">
			<div id="recommendListDiv"></div>
			<q:cancel id="recommendCancel" />
		</div>

		<div id="resumeMdfDiv">
			<form id="resumeMdfForm" action="my-resume.action" method="post">
				<div id="resumeDetailMdfDiv"></div>
				<div id="resumeContentMdfDiv">
					<textarea name="editor" id="editor"></textarea>
					<q:hidden id="resumeContentId" />
				</div>
			</form>
			<p>
				<q:ok id="resumeMdfOk" />
				<q:cancel id="resumeMdfCancel" />
				<q:button text="查看简历内容" id="resumeContentButton" />
				<q:button text="查看简历信息" id="resumeDetailButton" />
			</p>
		</div>

		<div id="mdfRecStepDealTimeDiv">
			<form id="mdfRecStepDealTimeForm" action="my-resume.action"
				method="post">
				<p>输入新的日期和时间：</p>
				<p>
					<q:text id="mdfRecStepDealTime" />
					<q:hidden id="mdfRecStepId" />
				</p>
				<p>
					<q:ok id="mdfRecStepDealTimeOk" />
					<q:cancel id="mdfRecStepDealTimeCancel" />
				</p>
			</form>
		</div>
	</div>
</body>
</html>