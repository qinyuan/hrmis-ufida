<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<%@include file="chead.jsp"%>
<q:js src="mdf-password" />
</head>
<body>
	<div class="body">
		<%@include file="cbody.jsp"%>
		<div class="main">
			<form action="mdf-password.action" method="post">
				<table>
					<tr>
						<td>请输入新密码：</td>
						<td><input type="password" name="password1" id="password1" /></td>
					</tr>
					<tr>
						<td>确认新密码：</td>
						<td><input type="password" name="password2" id="password2" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" name="mdfSubmit" id="mdfSubmit"
							value="确认修改" />
					</tr>
					<tr>
						<td></td>
						<td>${result}</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>