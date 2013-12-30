<%@page import="qinyuan.hrmis.domain.gender.GenderSelect"%>
<%@page import="qinyuan.hrmis.domain.targetplace.TargetPlaceSelect"%>
<%@page import="qinyuan.hrmis.dao.TargetPlace"%>
<%@page import="qinyuan.hrmis.domain.resume.source.SourceSelect"%>
<%@page import="qinyuan.hrmis.domain.post.PostSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加简历</title>
<%@include file="/chead.jsp"%>
<q:css href="/recruiter/add" />
<q:js src="/lib/ckeditor/ckeditor.js" />
<q:js src="/recruiter/add" />
</head>
<body>
	<div class="body">
		<%@include file="/cbody.jsp"%>
		<div class="main">
			<form id="addForm" action="add-resume.action" method="post">
				<q:panel title="简历内容">
					<textarea id="editor" name="editor"></textarea>
					<img alt="手动解析" src="/hrmis/recruiter/parseResumeButton.png" id="parseResumeButton">
					&nbsp;&nbsp;&nbsp;&nbsp;（注：解析简历是指抓取“简历内容”，自动填充到“简历信息”中）
				</q:panel>
				<q:accordion>
					<q:accPage title="简历信息">
						<table>
							<tr>
								<td>姓名</td>
								<td style="width:250px;"><q:text id="applicant" /><span>*</span></td>
								<td class="split"></td>
								<td>公司</td>
								<td><q:text id="company" />&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td>电话</td>
								<td><q:text id="tel" />&nbsp;&nbsp;</td>
								<td class="split"></td>
								<td>email</td>
								<td><q:text id="email" />&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td>性别</td>
								<td>
									<select id="genderId" name="genderId">
										<option value="未知">未知</option>
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</td>
								<td class="split"></td>
								<td>简历来源</td>
								<td><%=new SourceSelect()%>&nbsp;&nbsp;<input
									type="checkbox" id="downloaded" name="downloaded" />之前已被下载</td>
							</tr>
							<tr>
								<td>简历链接</td>
								<td><q:text id="resumeLink" /></td>
								<td class="split"></td>
								<td>简历编号</td>
								<td><q:text id="resumeNo" /></td>
							</tr>
							<tr>
								<td>职位</td>
								<td><%=new PostSelect()%></td>
								<td class="split"></td>
								<td>目标地点</td>
								<td><%=new TargetPlaceSelect().setTagId("targetPlaceId")%></td>
							</tr>
							<tr>
								<td>工作年限</td>
								<td><q:text id="experience" /><span>*</span></td>
								<td class="split"></td>
								<td>期望薪资</td>
								<td><q:text id="expectSalary" /></td>
							</tr>
							<tr>
								<td>qq</td>
								<td><q:text id="qq" /></td>
								<td class="split"></td>
								<td>意向</td>
								<td><q:text id="intention" /> <input type="checkbox"
									name="intentionRed" id="intentionRed" />标记红色</td>
							</tr>
							<tr>
								<td>离职原因</td>
								<td colspan="4"><textarea id="jhReason" name="jhReason"
										cols="70" rows="3"></textarea></td>
							</tr>
							<tr>
								<td>教育情况</td>
								<td colspan="4"><textarea id="education" name="education"
										rows="3" cols="70"></textarea></td>
							</tr>
							<tr>
								<td>技术能力</td>
								<td colspan="4"><textarea rows="3" cols="70" id="skill"
										name="skill"></textarea></td>
							</tr>
							<tr>
								<td>工作经历</td>
								<td colspan="4"><textarea rows="3" cols="70" id="prevJob"
										name="prevJob"></textarea></td>
							</tr>
							<tr>
								<td>项目经验</td>
								<td colspan="4"><textarea rows="3" cols="70" id="prevProj"
										name="prevProj"></textarea></td>
							</tr>
							<tr>
								<td>备注</td>
								<td colspan="4"><textarea rows="3" cols="70" id="other"
										name="other"></textarea></td>
							</tr>
						</table>
					</q:accPage>
				</q:accordion>
			</form>
		</div>
	</div>
	<div id="addButtonDiv">
		<img alt="添加" src="/hrmis/recruiter/addButton.png" id="addSubmit">
	</div>
</body>
</html>