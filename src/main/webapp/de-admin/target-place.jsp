<%@page import="qinyuan.hrmis.domain.table.TargetPlaceTable"%>
<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职位类型</title>
<%@include file="/chead.jsp"%>
<q:css href="target-place" />
<q:js src="target-place" />
<q:js src="/lib/showResult" />
</head>
<body>
	<input type="hidden" id="result" value="${result}" />
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<div id="postTableDiv">
				<%=PaginatedTableUtil.get(request, TargetPlaceTable.class)%>
			</div>
			<div class="add">
				<form id="addForm" action="TargetPlace.action" method="post">
					添加新职位：
					<br />
					<q:text id="newTargetPlaceName" />
					<br />
					<q:add id="addButton" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>