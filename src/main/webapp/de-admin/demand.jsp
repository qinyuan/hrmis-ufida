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
			<fieldset>
				<legend>客户管理</legend>
				<q:add id="addCustomer" text="" />
				<q:edit id="mdfCustomer" text="" disable="true" />
				<q:delete id="delCustomer" text="" disable="true" />
				<q:button text="移动" id="moveCustomer" disable="true" />
			</fieldset>
			<fieldset>
				<legend>需求管理</legend>
				<q:add id="addDemand" text="" disable="true" />
				<q:edit text="" id="mdfDemand" disable="true" />
				<q:delete id="delDemand" text="" disable="true" />
				<q:button text="开启" id="openDemand" disable="true" />
				<q:button text="暂停" id="pauseDemand" disable="true" />
				<q:button text="关闭" id="closeDemand" disable="true" />
			</fieldset>
			<fieldset>
				<legend>备注</legend>
				<%@include file="demandCommonButton.jsp"%>
			</fieldset>

			<%@include file="demandCommonContent.jsp"%>

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