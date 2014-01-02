<%@page import="qinyuan.lib.file.PFile"%>
<%@page import="qinyuan.hrmis.domain.table.SourceMapTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
<%@include file="/chead.jsp"%>
<q:css href="test" />
</head>
<body>
<%=request.getRequestURI()%>
<br />
<%=request.getRequestURL()%>
<br />
<%=request.getServerName()%>
<br />
<%=request.getServerPort()%>
<br />
<%=request.getHeader("User-Agent")%>
</body>
<q:js src="/lib/ckeditor/ckeditor.js" />
<q:js src="test" />
</html>