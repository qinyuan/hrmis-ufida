<%@page import="qinyuan.hrmis.domain.remark.CustomerRemarkList"%>
<%@page import="qinyuan.lib.lang.MyMath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户备注</title>
<%@include file="/chead.jsp"%>
<q:css href="/de-admin/demand-remark" />
<q:js src="/lib/ckeditor/ckeditor.js" />
<q:js src="/lib/inputCurtain" />
<q:js src="/de-admin/customer-remark" />
</head>
<body>
	<div class="body">
		<%@include file="getUser.jsp"%>
		<%
			String cusIdStr = request.getParameter("cusId");
			if (!MyMath.isNumeric(cusIdStr))
				return;
			int cusId = Integer.parseInt(cusIdStr);
		%>
		<q:panel title="客户备注">
			<div id="remarkDiv">
				<%=new CustomerRemarkList(cusId, user.getId())%>
			</div>
		</q:panel>
		<div id="editorDiv">
			<textarea id="editor" name="editor"></textarea>
			<p>
				<q:ok id="submitButton" />
				&nbsp;&nbsp;
				<q:cancel id="cancelButton" />
			</p>
		</div>
		<input type="hidden" id="hidCusId" value="<%=cusId%>" />
	</div>
</body>
</html>