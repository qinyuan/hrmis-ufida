<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.table.SimpleUserTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
<%@include file="/chead.jsp"%>
<q:css href="list" />
<q:js src="list" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<div id="userTableDiv">
				<%=PaginatedTableUtil.get(request, SimpleUserTable.class)%>
			</div>
		</div>
		<div id="priDiv">
			<div id="priPanel">
				<div id="priContent"></div>
			</div>
		</div>
		<div id="result"></div>
		<div id="mdfDiv">
			<div id="mdfPanel">
				<input type="hidden" id="mdfId" />
				<p>
					新用户名：<br /> <input type="text" id="mdfName" />
				</p>
				<p>
					新密码： <br /> <input type="text" id="mdfPassword" />
				</p>
				<p>
					<q:ok id="mdfOK" />
					<q:cancel id="mdfCancel" />
				</p>
			</div>
		</div>
	</div>
</body>
</html>