<%@page import="qinyuan.lib.db.HConn"%>
<%@page import="qinyuan.hrmis.lib.db.MyConn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据库连接测试</title>
</head>
<body>
	<%
		try {
			MyConn cnn = new MyConn();
			cnn.close();
			out.print("<h1 style='color:green'>JDBC 连接成功！</h1>");
		} catch (Exception e) {
			out.print("<h1 style='color:red'>JDBC 连接失败！</h1>");
		}
		try{
			HConn cnn=new HConn();
			cnn.close();
			out.print("<h1 style='color:green'>Hibernate 连接成功！</h1>");
		}catch(Exception e){
			out.print("<h1 style='color:red'>Hibernate 连接失败！</h1>");
		}
	%>
</body>
</html>