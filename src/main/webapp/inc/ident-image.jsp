<%@page import="qinyuan.lib.web.IdentifyCode"%>
<%@page contentType="image/jpeg"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%
	String str = IdentifyCode.createImage(response);
	session.setAttribute("identCode", str);
	out.clear();
%>