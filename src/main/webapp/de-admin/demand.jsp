<%@page import="qinyuan.hrmis.domain.targetplace.TargetPlaceSelect"%>
<%@page import="qinyuan.hrmis.domain.post.PostSelect"%>
<%@page import="qinyuan.hrmis.domain.customer.CustomerTree"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>需求列表</title>
<%@include file="/chead.jsp"%>
<q:css href="demand" />
<q:js src="demand" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<q:add id="addCustomer" text="添加客户" />
			<q:delete id="delCustomer" text="删除客户" disable="true" />
			<q:edit id="mdfCustomer" text="修改客户" disable="true" />
			<q:button text="移动客户" id="moveCustomer" disable="true" />
			<q:add id="addDemand" text="添加需求" disable="true" />
			<q:delete id="delDemand" text="删除需求" disable="true" />
			<q:edit text="修改需求" id="mdfDemand" disable="true" />

			<%@include file="demand-common.jsp"%>

			<div class="input" id="inputDiv">
				<form id="addForm" action="demand.action" method="post">
					<q:hidden id="n_cusId" />
					<table>
						<tr>
							<td>需求全称</td>
							<td><q:text id="n_name" /></td>
						</tr>
						<tr>
							<td>职位类型</td>
							<td><%=new PostSelect().toSelect().setId("n_postId")%></td>
						</tr>
						<tr>
							<td>工作地点</td>
							<td><%=new TargetPlaceSelect().setTagId("n_targetPlaceId")%></td>
						<tr>
							<td>岗位职责</td>
							<td><textarea id="n_duty" name="n_duty" rows="4" cols="70"></textarea></td>
						</tr>
						<tr>
							<td>岗位要求</td>
							<td><textarea id="n_qualification" name="n_qualification"
									rows="6" cols="70"></textarea></td>
						</tr>
						<tr>
							<td>薪资状况</td>
							<td><q:text id="n_salary" /></td>
						</tr>
						<tr>
							<td>需求人数</td>
							<td><q:text id="n_postNumber" /></td>
						</tr>
						<tr>
							<td>发布日期</td>
							<td><q:text id="n_startDate" /></td>
						</tr>
						<tr>
							<td>结束日期</td>
							<td><q:text id="n_endDate" /></td>
						</tr>
						<tr>
							<td></td>
							<td><q:ok id="addOK" /> <q:cancel id="addCancel" /></td>
						</tr>
					</table>
				</form>
			</div>

			<div id="moveCustomerDiv">
				<%=cusTree.setDisplayRemark(false).toString()
					.replace("cus", "supCus")%>
				<div id="moveCustomerButtonsDiv">
					<q:ok id="moveOK" />
					&nbsp;&nbsp;
					<q:cancel id="moveCancel" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>