autoDecorateTable = false;
$(document).ready(function() {
	var $table = $('table');
	var $table1 = $table.eq(0);
	$table1.find('td:even').addClass('title');
	$table1.find('td:odd').addClass('content');
	decorateTable($table1, false);
	decorateTable($table.eq(1), true);

	$("#exportButton").click(function() {
		var resumeId = rightStr(location.href.toString(), '?');
		resumeId = resumeId.replace(/\D/g, '');
		var href = "ExportResumeDetail?resumeId=" + resumeId;
		window.open(href);
	});
});