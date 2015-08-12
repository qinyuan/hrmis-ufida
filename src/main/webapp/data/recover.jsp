<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据恢复</title>
<%@include file="/chead.jsp"%>
<q:js src="recover"/>
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<form action="recover" enctype="multipart/form-data"
				method="post">
				<table>
					<tr>
						<td>选择需要恢复的文件：</td>
						<td><input type="file" name="uploadFile" id="uploadFile"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" id="submit" value="恢复" /></td>
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