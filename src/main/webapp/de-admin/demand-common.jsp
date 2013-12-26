<%@taglib prefix="q" uri="http://853609848.qzone.qq.com"%>
<%@page import="qinyuan.hrmis.domain.customer.CustomerTree"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<q:button text="客户信息" id="remarkCustomer" disable="true" />
<q:button text="需求信息" id="remarkDemand" disable="true" />

<div class="easyui-layout" style="width: 900px; height: 500px;">

	<div id="leftDiv"
		data-options="region:'west',split:true,collapsible:false" title="客户"
		style="width: 180px;">
		<%
			CustomerTree cusTree = new CustomerTree();
			out.print(cusTree);
		%>
	</div>

	<div id="centerDiv" data-options="region:'center',split:true"
		title="需求" style="width: 200px;">
		<div id="demandsDiv"></div>
	</div>

	<div id="rightDiv"
		data-options="region:'east',split:true,collapsible:false" title="需求明细"
		style="width: 520px;">
		<div id="detailDiv"></div>
		<div id="mdfDiv">
			<q:ok id="mdfOK" text="修改" />
			&nbsp;&nbsp;
			<q:cancel id="mdfCancel" />
		</div>
	</div>
</div>