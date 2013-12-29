<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>招聘管理系统</title>
<%@include file="/chead.jsp"%>
<q:css href="/index" />
<q:js src="/index" />
</head>
<body>
	<div class="body">
		<div id="inputFormDiv">
			<form id="loginForm" action="/hrmis/login.action" method="post">
				<div id="usernameDiv">
					<q:text id="hrmisUsername" />
				</div>
				<div id="passwordDiv">
					<q:password id="hrmisPassword" />
				</div>
				<div id="submitDiv">
					<img alt="登录" id="submitImage" src="lib/img/login_button.png"
						width="30%" height="30%">
				</div>
				<div id="recordDiv">
					<input type="checkbox" id="recordChkBox" name="recordChkBox" checked="checked" />自动登录					
				</div>
				<jsp:include page="inc/ident-code.jsp" />
			</form>
		</div>
		<%@include file="index-tool.jsp"%>
	</div>
</body>
</html>