<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.table.SourceTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历来源</title>
<%@include file="/chead.jsp"%>
<q:css href="resume-source" />
<q:js src="resume-source" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">

			<q:add id="addButton" />
			<div id="tableDiv">
				<%=PaginatedTableUtil.get(request, SourceTable.class)%>
			</div>

			<div id="addDiv">
				<form id="addForm" action="resume-source.action" method="post">
					<p>
						来源：<br />
						<q:text id="addName" required="true" />
					</p>
					<p>
						简历ID模式： <br />
						<q:text id="addIdPattern" />
					</p>
					<p>
						简历前缀: <br />
						<q:text id="addHrefPrefix" />
					</p>
					<p>
						简历后缀：<br />
						<q:text id="addHrefSuffix" />
					</p>
					<p>
						唯一标识：<br />
						<q:text id="addSign" />
					</p>
					<p>
						<input type="checkbox" id="addDownloaded" name="addDownloaded" />默认已下载
					</p>
					<p>
						<q:ok id="addOk" />
						<q:cancel id="addCancel" />
					</p>
				</form>
			</div>

			<div id="mdfDiv">
				<form id="mdfForm" action="resume-source.action" method="post">
					<input type="hidden" name="mdfId" id="mdfId" />
					<p>
						来源：<br />
						<q:text id="mdfName" />
					</p>
					<p>
						简历ID模式： <br />
						<q:text id="mdfIdPattern" />
					</p>
					<p>
						简历前缀: <br />
						<q:text id="mdfHrefPrefix" />
					</p>
					<p>
						简历后缀：<br />
						<q:text id="mdfHrefSuffix" />
					</p>
					<p>
						唯一标识：<br />
						<q:text id="mdfSign" />
					</p>
					<p>
						<input type="checkbox" id="mdfDownloaded" name="mdfDownloaded" />默认已下载
					</p>
					<p>
						<q:edit id="mdfOk" />
						<q:cancel id="mdfCancel" />
					</p>
				</form>
			</div>

		</div>
	</div>
</body>
</html>