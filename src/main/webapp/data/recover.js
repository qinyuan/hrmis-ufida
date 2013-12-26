$(document).ready(function() {
	$("#submit").click(function(e) {
		var fileName = $('#uploadFile').val();
		if (fileName == "") {
			alert("文件未选择");
			e.preventDefault();
		} else if (!fileName.match('.+[.]rar')) {
			alert("数据备份文件应该以.rar结尾");
			e.preventDefault();
		} else if (!confirm("若恢复数据备份文件的数据，当然数据库的数据将被覆盖，确定恢复数据？")) {
			e.preventDefault();
		}
	});
});