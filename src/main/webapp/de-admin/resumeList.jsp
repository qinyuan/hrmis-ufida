<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历列表</title>
<%@include file="/chead.jsp"%>
<%@include file="/recruiter/resumeListHead.jsp"%>
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>

		<div class="main">
			<div id="filterDiv">
				<%@include file="/recruiter/resumeFilter.jsp"%>
			</div>
			<%=table.setEditable(false)%>
		</div>
	</div>
</body>
</html>