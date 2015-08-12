<%@page import="qinyuan.lib.web.SessParam"%>
<%@page import="qinyuan.hrmis.domain.post.PostSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="/hrmis/sess/post-select.js"></script>
<%
	int postId=SessParam.getInt(request, "postId");
%>
职位类型：<%=new PostSelect(postId).setTotal(true)%>