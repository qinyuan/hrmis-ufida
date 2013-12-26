<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.resume.source.SourceSelect"%>
<%@page import="qinyuan.hrmis.domain.table.SourceMapTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历来源映射</title>
<%@include file="/chead.jsp"%>
<q:css href="resume-source-map" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<div id="tableDiv">
				<%=PaginatedTableUtil.get(request, SourceMapTable.class)%>
			</div>

			<div id="debugDiv">
				<q:button text="打开调试" id="openDebugButton" /><q:button text="关闭调试" id="closeDebugButton" />
				<div>
					来源选择器:<br />
					<textarea id="debugSelector" rows="3" cols="130"></textarea>
					<br />
					<q:button text="查看调试结果" id="debugResultButton" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<textarea rows="5" cols="130" id="debugResultText"></textarea>
					<br /><br />
					<textarea name="ckeditor" id="ckeditor"></textarea>
				</div>
			</div>
		</div>

		<div id="addDiv">
			<q:panel title="添加简历来源" collapsible="false">
				<form id="addForm" method="post">
					<p>
						简历来源：<br />
						<%=new SourceSelect().toSelect().setId("addSourceId")%>
					</p>
					<p>
						来源选择器： <br />
						<q:text id="addSourceSelector" required="true" />
					</p>
					<p>
						目标选择器: <br />
						<q:text id="addTargetSelector" required="true" />
					</p>
					<p>
						备注：<br />
						<q:text id="addRemark" />
					</p>
					<p>
						<q:add id="addOK" />
					</p>
				</form>
			</q:panel>
		</div>

		<div id="mdfDiv">
			<div id="mdfPanel">
				<form id="mdfForm" action="resume-source.action" method="post">
					<q:hidden id="mdfId" />
					<p>
						简历来源：<br />
						<%=new SourceSelect().toSelect().setId("mdfSourceId")%>
					</p>
					<p>
						来源选择器： <br />
						<q:text id="mdfSourceSelector" required="true" />
					</p>
					<p>
						目标选择器: <br />
						<q:text id="mdfTargetSelector" required="true" />
					</p>
					<p>
						备注：<br />
						<q:text id="mdfRemark" />
					</p>
					<p>
						<q:edit id="mdfOK" />
						<q:cancel id="mdfCancel" />
					</p>
				</form>
			</div>
		</div>
	</div>
</body>
<q:js src="/lib/ckeditor/ckeditor.js" />
<q:js src="resume-source-map" />
</html>