<%@page import="qinyuan.hrmis.domain.user.UserTree"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
<%@include file="/chead.jsp"%>
<q:css href="user" />
<q:js src="user" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<q:add id="addButton" />
			<q:button text="移动" id="moveButton" disable="true" />
			<q:edit id="mdfButton" />
			<q:delete id="delButton" />

			<%
				String userTree = new UserTree().toString();
			%>

			<div id="userTreeDiv" class="blackWhitePanel">
				<h4>用户列表</h4>
				<%=userTree%>
			</div>

			<div id="moveDiv">
				<b>选择用户上级</b>
				<hr>
				<%=userTree.replace("user", "supUser")%>
				<hr>
				<q:ok id="moveOk" />
				<q:cancel id="moveCancel" />
			</div>

			<div id="addDiv">
				<form id="addForm" action="User.action" method="post">
					<q:hidden id="superiorId" value="-1" />
					用户名：<br />
					<q:text id="username" required="true" />
					<br /> <br /> 密码：<br />
					<q:text id="password" required="true" />
					<br /> <br />
					<q:ok id="addSubmit" />
					<q:cancel id="addCancel" />
				</form>
			</div>

			<div id="priDiv" class="blackWhitePanel">
				<h4></h4>
				<div id="priContent"></div>
				<div id="result"></div>
			</div>

			<div id="mdfDiv">
				<form id="mdfForm" action="User.action" method="post">
					<input type="hidden" id="mdfId" name="mdfId" />
					<p>
						新用户名：<br /> <input type="text" id="mdfName" name="mdfName" />
					</p>
					<p>
						新密码： <br /> <input type="text" id="mdfPassword" name="mdfPassword" />
					</p>
					<p>
						<q:ok id="mdfOK" />
						<q:cancel id="mdfCancel" />
					</p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>