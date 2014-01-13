<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出Excel</title>
<%@include file="/chead.jsp"%>
<q:css href="/recruiter/exportExcel" />
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
						<fieldset>
							<legend>导出列</legend>
							<%
								String[] strs = new String[] { "日期", "公司", "名字", "职位", "电话", "邮箱",
										"QQ", "来源", "意向", "工作年限", "薪水", "备注", "推送客户" };
								for (String str : strs) {
							%>
							<input type="checkbox" checked="checked" disabled="disabled" /><%=str%>&nbsp;
							<%
								}
							%>
							<br />
							<br /> <input type="checkbox" checked="checked" id="jhReason" />离职原因&nbsp;
							<input type="checkbox" checked="checked" id="education" />教育情况&nbsp;
							<input type="checkbox" checked="checked" id="skill" />技术能力&nbsp;
							<input type="checkbox" checked="checked" id="prevJob" />工作经历&nbsp;
							<input type="checkbox" checked="checked" id="prevProj" />项目经历&nbsp;
							<br />
							<br /> <input type="checkbox" checked="checked" id="selectAll" />全选/全不选
						</fieldset>
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
<q:js src="recruiter/exportExcel" />
</html>