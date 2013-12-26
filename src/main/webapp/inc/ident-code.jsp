<%@page import="qinyuan.lib.web.IdentifyCode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (IdentifyCode.isBlack(request.getRemoteAddr())) {
%>
<div id="identCodeTextDiv">
	<img alt="验证码" src="lib/img/ident-code-text.png" width="20%"
		height="20%" />
</div>
<div id="identInputBackDiv">
	<img src="lib/img/form_input.png" />
</div>
<div id="identInputDiv">
	<input type="text" id="identCode" name="identCode" />
</div>
<div id="identImageDiv">
	<img id="ident_image" src="/hrmis/inc/ident-image.jsp" title="看不清楚，换一张">
</div>
<%
	} else {
		session.setAttribute("identCode", null);
	}
%>