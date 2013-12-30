<%@page import="qinyuan.lib.web.SessParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="/hrmis/recruiter/resumeDateFilter.css" />
<script src="/hrmis/recruiter/resumeDateFilter.js"></script>
<%
	int statusId = SessParam.getInt(request, "sessResumeStatusId");
	String startAddDate = null, endAddDate = null, startMdfDate = null, endMdfDate = null;
	String startHandleDate = null, endHandleDate = null;
	if (statusId == 0) {
		startAddDate = SessParam.getStr(request, "resumeStartAddDate");
		endAddDate = SessParam.getStr(request, "resumeEndAddDate");
		startMdfDate = SessParam.getStr(request, "resumeStartMdfDate");
		endMdfDate = SessParam.getStr(request, "resumeEndMdfDate");
%>
添加日期：
<input type="text" id="resumeStartAddDate" value="<%=startAddDate%>"
	readonly="readonly" />
-
<input type="text" id="resumeEndAddDate" value="<%=endAddDate%>"
	readonly="readonly" />
<a id="todayAddDate" href="#">今天</a>
<a id="thisWeekAddDate" href="#">本周</a>

&nbsp;&nbsp;&nbsp;&nbsp; 修改日期：
<input type="text" id="resumeStartMdfDate" value="<%=startMdfDate%>"
	readonly="readonly" />
-
<input type="text" id="resumeEndMdfDate" value="<%=endMdfDate%>"
	readonly="readonly" />
<a id="todayMdfDate" href="#">今天</a>
<a id="thisWeekMdfDate" href="#">本周</a>
<%
	} else {
		startHandleDate = SessParam.getStr(request, "startHandleDate");
		endHandleDate = SessParam.getStr(request, "endHandleDate");
%>
处理日期：
<input type="text" id="startHandleDate" value="<%=startHandleDate%>"
	readonly="readonly" />
-
<input type="text" id="endHandleDate" value="<%=endHandleDate%>"
	readonly="readonly" />
<a id="todayHandleDate" href="#">今天</a>
<a id="thisWeekHandleDate" href="#">本周</a>
<%
	}
%>