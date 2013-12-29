<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历列表</title>
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
				<%@include file="/recruiter/resume-filter.jsp"%>
			</div>
			<%=table.setEditable(false)%>
		</div>
	</div>
</body>
</html>