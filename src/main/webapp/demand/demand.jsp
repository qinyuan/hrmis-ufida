<%@page import="qinyuan.hrmis.domain.post.PostSelect"%>
<%@page import="qinyuan.hrmis.domain.customer.CustomerTree"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>需求列表</title>
<%@include file="/chead.jsp"%>
<q:css href="../de-admin/demand" />
<q:js src="/de-admin/demand" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<%@include file="/de-admin/demand-common.jsp"%>
		</div>
	</div>
</body>
</html>