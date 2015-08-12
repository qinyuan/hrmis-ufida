<%@page import="qinyuan.hrmis.domain.recommend.PaginatedRecommendTable"%>
<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.recommend.RecommendTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推送列表</title>
<%@include file="/chead.jsp"%>
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<%@include file="/sess/demand-select.jsp"%>
			<q:panel title="推荐记录" collapsible="false">
				<%=PaginatedTableUtil
						.get(request, PaginatedRecommendTable.class)
						.setPostId(postId).setDemandId(demandId)%>
			</q:panel>
		</div>
	</div>
</body>
<script>
autoDecorateTable = false;
$(function(){
	decorateTable($('.easyui-panel table').eq(0), false);
});
</script>
</html>