<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>debug sql</title>
<%@include file="/chead.jsp"%>
</head>
<body>
	<textarea id="debugSql" rows="3" cols="50"></textarea>
	<br />
	<q:button text="debug" id="debugButton" />
	<br />
	<textarea rows="5" cols="50" id="debugConsole"></textarea>
</body>
<script type="text/javascript">
	$('#debugButton').click(function(e){
		e.preventDefault();
		$.post('DebugSql.action',{
			"debugSql":$('#debugSql').val()
		},function(data){
			data=parseAjaxData(data);
			$('#debugConsole').val(data);	
		});
	});
</script>
</html>