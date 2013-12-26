<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.table.ResumeTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/sess/search-name.jsp"%>

&nbsp;&nbsp;&nbsp;&nbsp;
<%@include file="/sess/post-select.jsp"%>

&nbsp;&nbsp;&nbsp;&nbsp;
<%@include file="/sess/resume-status.jsp"%>

&nbsp;&nbsp;&nbsp;&nbsp;
<%@include file="/sess/mark-status.jsp"%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%@include file="/sess/target-place-select.jsp"%>

<br />
<br />

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%@include file="/sess/recruiter-select.jsp"%>

<br /><br />
&nbsp;&nbsp;&nbsp;&nbsp;
<%@include file="resumeDateFilter.jsp"%>

<script src="/hrmis/recruiter/resume-filter.js"></script>
<%
	ResumeTable table = PaginatedTableUtil.get(request, ResumeTable.class)
	.setPostId(postId).setStartAddDate(startAddDate)
	.setEndAddDate(endAddDate).setStartMdfDate(startMdfDate)
	.setEndMdfDate(endMdfDate).setStatusId(sessResumeStatusId)
	.setSearchName(sessSearchName).setMarkStatus(markStatus)
	.setTargetPlaceId(targetPlaceId)
	.setSearchTel(sessSearchTel)
	.setStartHandleDate(startHandleDate)
	.setEndHandleDate(endHandleDate)
	.setRecruiterId(recruiterId);
%>