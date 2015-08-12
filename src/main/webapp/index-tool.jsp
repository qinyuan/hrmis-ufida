<%@page import="qinyuan.hrmis.domain.tool.TaxTool"%>
<%@page import="qinyuan.hrmis.domain.tool.SalaryTool"%>
<%@taglib prefix="q" uri="http://853609848.qzone.qq.com"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	SalaryTool st = new SalaryTool();
	TaxTool tt = new TaxTool();
%>
<link rel="stylesheet" href="index-tool.css" type="text/css" />
<script type="text/javascript" src="index-tool.js"></script>
<input type="hidden" id="taxLevels" value="<%=tt.getTaxLevelsStr()%>" />
<input type="hidden" id="taxRates" value="<%=tt.getTaxRatesStr()%>" />
<input type="hidden" id="calNums" value="<%=tt.getCalNumsStr()%>" />
<input type="hidden" id="taxBase" value="<%=tt.getBase()%>" />
<div id="toolDiv" class="blackWhitePanel">
	<h3>工资计算器</h3>
	<div id="toolBodyDiv">
		<table class="result">
			<tr>
				<td class="title">社保基数</td>
				<td><input class="wide" type="text" id="securityBase"
					value="<%=st.getSecurityBase()%>" /></td>
			</tr>
			<tr>
				<td class="title">公积金基数</td>
				<td><input class="wide" type="text" id="fundsBase"
					value="<%=st.getFundsBase()%>" /></td>
			</tr>
			<tr>
				<td class="title">养老</td>
				<td><input class="narrow" type="text" id="endowmentRate"
					value="<%=st.getEndowmentRate()%>" />%</td>
			</tr>
			<tr>
				<td class="title">医疗</td>
				<td><input class="narrow" type="text" id="medicareRate"
					value="<%=st.getMedicareRate()%>" />%</td>
			</tr>
			<tr>
				<td class="title">医疗额外</td>
				<td><input class="narrow" type="text" id="medicareFare"
					value="<%=st.getMedicareFare()%>" /></td>
			</tr>
			<tr>
				<td class="title">失业</td>
				<td><input class="narrow" type="text" id="idlenessRate"
					value="<%=st.getIdlenessRate()%>" />%</td>
			</tr>
			<tr>
				<td class="title">公积金</td>
				<td><input class="narrow" type="text" id="fundsRate"
					value="<%=st.getFundsRate()%>" />%</td>
			</tr>
		</table>
		<table class="result">
			<tr>
				<td class="title">税前工资</td>
				<td><input class="wide" type="text" id="salaryBeforeTax" /></td>
			</tr>
			<tr>
				<td class="title">养老</td>
				<td><b><span id="endowment"></span></b></td>
			</tr>
			<tr>
				<td class="title">医疗</td>
				<td><b><span id="medicare"></span></b></td>
			</tr>
			<tr>
				<td class="title">失业</td>
				<td><b><span id="idleness"></span></b></td>
			</tr>
			<tr>
				<td class="title">公积金</td>
				<td><b><span id="funds"></span></b></td>
			</tr>
			<tr>
				<td>五险一金</td>
				<td><b><span id="deduction"></span></b></td>
			</tr>
			<tr>
				<td>个人所得税</td>
				<td><b><span id="totalTax"></span></b></td>
			</tr>
			<tr>
				<td>税后工资</td>
				<td><b><span id="salaryAfterTax"></span></b></td>
			</tr>
		</table>
		<div id="taxTableDiv" class="blackWhitePanel">
			<h3>税率表</h3>
			<%=tt.toTable()%>
			<span style="padding: 15px;">个税起征点：<b><i><%=tt.getBase()%></i></b></span>
		</div>
	</div>
</div>