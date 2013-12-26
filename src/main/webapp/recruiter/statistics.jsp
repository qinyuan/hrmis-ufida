<%@page import="qinyuan.hrmis.domain.resume.statistics.ResumeStatistics"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历统计</title>
<%@include file="/chead.jsp"%>
<q:css href="/recruiter/statistics" />
<q:js src="/recruiter/statistics" />
<q:js src="/lib/HighCharts/js/highcharts.js" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<div>
				<%@include file="/sess/recruiter-select.jsp"%>
				<%@include file="/sess/post-select.jsp"%>
			</div>

			<div id="fullDiv" class="blackWhitePanel">
				<h3>常规统计</h3>
				<%=new ResumeStatistics().setUserId(recruiterId).setPostId(
					postId)%>
			</div>

			<div id="hiddenDiv">
				<div id="contentDiv"></div>
				<div id="closeHiddenDiv">
					<q:cancel id="closeHiddenDivButton" text="关闭" />
				</div>
			</div>

			<div id="periodDiv" class="blackWhitePanel">
				<h3>区间统计</h3>
				开始日期：<input type="text" id="startDate" readonly="readonly" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				结束日期：<input type="text" id="endDate" readonly="readonly" />
				&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;
				<q:query id="queryButton" />
				<div id="periodTable"></div>
			</div>

			<div id="chartDiv" class="blackWhitePanel">
				<h3>自定义统计图(表)</h3>
				<%@include file="/sess/start-date.jsp"%>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%@include file="/sess/end-date.jsp"%>
				<q:accordion>
					<q:accPage title="日统计">
						<q:query id="dayChartButton" text="统计图" />
						<q:query id="dayTableButton" text="统计表" />
						<div id="dayDiv"></div>
					</q:accPage>
					<q:accPage title="周统计">
						<q:query id="weekChartButton" text="统计图" />
						<q:query id="weekTableButton" text="统计表" />
						<div id="weekDiv"></div>
					</q:accPage>
				</q:accordion>
			</div>

		</div>
	</div>
</body>
</html>