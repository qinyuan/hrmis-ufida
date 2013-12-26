<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出Excel</title>
<%@include file="/chead.jsp"%>
<q:css href="exportExcel" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<div id="optionDiv" class="blackWhitePanel">
				<h4>导出选项</h4>
				<div>
					<%@include file="/sess/recruiter-select.jsp"%>
					<%@include file="/sess/start-date.jsp"%>
					<%@include file="/sess/end-date.jsp"%>
					<div id="columnsSelectDiv">
						<b>导出列：</b>
						<br /><br />
						<%
							String[] strs=new String[]{"日期","公司","名字","职位","电话","邮箱","QQ","来源","意向","工作年限","薪水","备注","推送客户"};
							for(String str:strs){
								%>
									
								<%
							}
						%>
						<input type="checkbox" checked="checked" disabled="disabled" />日期
						&nbsp;&nbsp;&nbsp;
						<input type="checkbox" checked="checked" disabled="disabled" />公司
						<input type="checkbox" checked="checked" disabled="disabled" />公司

					</div>
				</div>
			</div>
			<div id="buttonDiv">
				<q:button text="导出日报表" id="exportDailyButton" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<q:button text="导出周报表" id="exportWeeklyButton" />
			</div>
		</div>
	</div>
</body>
<q:js src="/recruiter/export-excel" />
</html>