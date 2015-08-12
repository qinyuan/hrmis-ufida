<%@page import="qinyuan.hrmis.domain.user.User"%>
<%@page import="qinyuan.hrmis.domain.remark.DemandRemarkList"%>
<%@page import="qinyuan.hrmis.domain.recommend.RecommendTable"%>
<%@page import="qinyuan.hrmis.domain.demand.WideDemandDetailTable"%>
<%@page import="qinyuan.lib.lang.MyMath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>需求详细信息</title>
<%@include file="/chead.jsp"%>
<q:css href="/de-admin/demand-remark" />
<q:js src="/lib/ckeditor/ckeditor.js" />
<q:js src="/lib/inputCurtain" />
<q:js src="/de-admin/demand-remark" />
</head>
<body>
	<div class="body">
		<%@include file="getUser.jsp" %>
		<%
			String demandIdStr = request.getParameter("demandId");
			if (!MyMath.isNumeric(demandIdStr))
				return;
			int demandId = Integer.parseInt(demandIdStr);
		%>
		<q:panel title="岗位描述" collapsed="true">
			<div id="demandDetail">
				<%=new WideDemandDetailTable(demandId)%>
			</div>
		</q:panel>
		<q:panel title="推荐记录" collapsed="true">
			<div id="recommendList">
				<%=new RecommendTable().setDemandId(demandId)%>
			</div>
		</q:panel>
		<q:panel title="需求备注">
			<div id="remarkDiv">
				<%=new DemandRemarkList(demandId, user.getId())%>
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
		<input type="hidden" id="hidDemandId" value="<%=demandId%>" />
	</div>
</body>
</html>