<%@page import="qinyuan.lib.web.html.Table"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<%@include file="/chead.jsp"%>
<q:css href="login" />
<q:js src="login" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			${user.naviBoxes}
			<%@include file="index-tool.jsp" %>
		</div>		
	</div>
</body>
</html>