autoDecorateTable = false;
$(document).ready(function() {
	var $table = $('table');
	var $table1 = $table.eq(0);
	$table1.find('td:even').addClass('title');
	$table1.find('td:odd').addClass('content');
	decorateTable($table1, false);
	decorateTable($table.eq(1), true);

	$("#exportButton").click(function() {
		CURTAIN.show("exportOptionDiv");
	});
	$('#exportOk').click(function(e) {
		e.preventDefault();
		var resumeId = rightStr(location.href.toString(), '?');
		resumeId = resumeId.replace(/\D/g, '');
		var href = "ExportResumeDetail?resumeId=" + resumeId;
		href += '&exportTel=' + $('#exportTel').get(0).checked;
		CURTAIN.hide();
		window.open(href);
	});
});