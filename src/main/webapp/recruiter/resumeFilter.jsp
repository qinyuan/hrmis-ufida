<%@taglib uri="http://853609848.qzone.qq.com" prefix="q"%>
<%@page import="qinyuan.lib.web.html.pagination.PaginatedTableUtil"%>
<%@page import="qinyuan.hrmis.domain.table.ResumeTable"%>
<%@page import="qinyuan.lib.web.SessParam"%>
<%@page import="qinyuan.hrmis.domain.recruitor.RecruiterSelect"%>
<%@page import="qinyuan.hrmis.domain.targetplace.TargetPlaceSelect"%>
<%@page import="qinyuan.hrmis.domain.user.User"%>
<%@page import="qinyuan.lib.web.html.Select"%>
<%@page import="qinyuan.hrmis.domain.recommend.StatusSelect"%>
<%@page import="qinyuan.hrmis.domain.post.PostSelect"%>
<%@page import="qinyuan.hrmis.domain.resume.ResumeFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ResumeFilter filter = (ResumeFilter) session
			.getAttribute("resumeFilter");
	if(filter==null){
		filter=new ResumeFilter();
	}

	SessParam sp = new SessParam(request);
	sp.setPrefix("resumeList");

	String searchName = sp.getStr("searchName");
	filter.setSearchName(searchName);

	String searchTel = sp.getStr("searchTel");
	filter.setSearchTel(searchTel);

	int postId = sp.getInt("postId");
	filter.setPostId(postId);

	int statusId = sp.getInt("statusId");
	filter.setStatusId(statusId);

	int markStatus = sp.getInt("markStatus");
	filter.setMarkStatus(markStatus);

	int targetPlaceId = sp.getInt("targetPlaceId");
	filter.setTargetPlaceId(targetPlaceId);
%>
<div id="searchBoxDiv">
	<span class="label">姓名搜索：</span><input class="easyui-searchbox"
		data-options="prompt:'在此输入姓名',searcher:doSearchName"
		style="width: 120px" value="<%=searchName%>"></input> <br /> <br />
	<span class="label">电话搜索：</span><input class="easyui-searchbox"
		data-options="prompt:'在此输入电话',searcher:doSearchTel"
		style="width: 120px" value="<%=searchTel%>"></input> <br /> <br />
	<q:cancel id="clearFilterButton" text="清空筛选条件" />
</div>

&nbsp;&nbsp;&nbsp;&nbsp;
职位类型：<%=new PostSelect(postId).setTotal(true)%>

&nbsp;&nbsp;&nbsp;&nbsp;
简历状态：<%=new StatusSelect("statusId").setStatusId(statusId)%>

&nbsp;&nbsp;&nbsp;&nbsp;
<%
	Select select = new Select();
	select.setId("markStatus");
	select.addOption(0, "（全部）").addOption(1, "已标记").addOption(2, "未标记");
	select.select(markStatus);
%>
标记状态：<%=select%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
目标地点：<%=new TargetPlaceSelect().setTagId("targetPlaceId")
					.setDefaultId(targetPlaceId).setWithTotal(true)%>
<br />
<br />

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%
	int recruiterId = 0;
	if (sp.hasKey("recruiterId")) {
		recruiterId = sp.getInt("recruiterId");
	} else {
		User localUser = (User) session.getAttribute("user");
		if (localUser.hasPri(6)) {
			recruiterId = localUser.getId();
		}
	}
	filter.setRecruiterId(recruiterId);
%>
创建者：<%=new RecruiterSelect(recruiterId)%>

<br />
<br />
&nbsp;&nbsp;&nbsp;&nbsp;
<%
	String startAddDate = null, endAddDate = null, startMdfDate = null, endMdfDate = null;
	String startHandleDate = null, endHandleDate = null;
	if (statusId == 0) {
		startAddDate = sp.getStr("startAddDate");
		filter.setStartAddDate(startAddDate);

		endAddDate = sp.getStr("endAddDate");
		filter.setEndAddDate(endAddDate);

		startMdfDate = sp.getStr("startMdfDate");
		filter.setStartMdfDate(startMdfDate);

		endMdfDate = sp.getStr("endMdfDate");
		filter.setEndMdfDate(endMdfDate);
%>
添加日期：<input type="text" id="startAddDate" value="<%=startAddDate%>"
	readonly="readonly" />
-
<input type="text" id="endAddDate" value="<%=endAddDate%>"
	readonly="readonly" />
<a id="todayAddDate" href="#">今天</a>
<a id="thisWeekAddDate" href="#">本周</a>

&nbsp;&nbsp;&nbsp;&nbsp;
修改日期：<input type="text" id="startMdfDate" value="<%=startMdfDate%>"
	readonly="readonly" />
-
<input type="text" id="endMdfDate" value="<%=endMdfDate%>"
	readonly="readonly" />
<a id="todayMdfDate" href="#">今天</a>
<a id="thisWeekMdfDate" href="#">本周</a>
<%
	} else {
		startHandleDate = sp.getStr("startHandleDate");
		filter.setStartHandleDate(startHandleDate);

		endHandleDate = sp.getStr("endHandleDate");
		filter.setEndHandleDate(endHandleDate);
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
	
	ResumeTable table = PaginatedTableUtil.get(request,
			ResumeTable.class);
	table.setFilter(filter);
%>