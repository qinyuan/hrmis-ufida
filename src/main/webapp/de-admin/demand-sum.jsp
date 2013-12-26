<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.table.DemandTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推送统计</title>
<%@include file="/chead.jsp"%>
<q:css href="/de-admin/demand-sum" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<%@include file="/sess/post-select.jsp"%>
			<div id="tableDiv">
				<%=PaginatedTableUtil.get(request, DemandTable.class).setPostId(
					postId)%>
			</div>
		</div>
	</div>
</body>
</html>