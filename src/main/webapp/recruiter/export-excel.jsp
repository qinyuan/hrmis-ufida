<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出Excel</title>
<%@include file="/chead.jsp"%>
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<%@include file="/sess/recruiter-select.jsp" %>
			<%@include file="/sess/start-date.jsp"%>
			<%@include file="/sess/end-date.jsp"%>
			<p>
				<q:button text="导出日报表" id="exportDailyButton" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<q:button text="导出周报表" id="exportWeeklyButton" />
			</p>
		</div>
	</div>
</body>
<q:js src="/recruiter/export-excel"/>
</html>