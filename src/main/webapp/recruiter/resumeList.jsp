<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的简历</title>
<%@include file="/chead.jsp"%>
<q:css href="/recruiter/resumeList" />
<q:js src="/lib/showResult" />
<q:js src="/lib/inputCurtain" />
<q:js src="/lib/ckeditor/ckeditor.js" />
<q:js src="/recruiter/resumeList" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>

		<div class="main">
			<div id="filterDiv">
				<%@include file="resume-filter.jsp"%>
			</div>
			<%=table.setUser(user)%>
		</div>

		<div id="addResumeDiv">
			<img alt="添加简历" src="/hrmis/recruiter/toAddPageButton.png" id="addResumeButton">
		</div>

		<form id="myResumeForm" action="my-resume.action" method="post">
			<div id="hiddenDiv">
				<div id="contentDiv"></div>
				<div id="availableCusDiv">
					<q:cancel id="availableCusCancel" />
				</div>
				<div id="mdfFeedbackDiv">
					<q:ok id="mdfFeedbackOk" />
					<q:cancel id="mdfFeedbackCancel" />
				</div>
			</div>
		</form>

		<div id="resumeMdfDiv">
			<form id="resumeMdfForm" action="my-resume.action" method="post">
				<div id="resumeDetailMdfDiv"></div>
				<div id="resumeContentMdfDiv"></div>
			</form>
			<p>
				<q:ok id="resumeMdfOk" />
				<q:cancel id="resumeMdfCancel" />
				<q:button text="查看简历内容" id="resumeContentButton" />
				<q:button text="查看简历信息" id="resumeInfoButton" />
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