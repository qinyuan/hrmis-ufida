$(document).ready(function() {
	postAfterDateChange("resumeStartAddDate");
	postAfterDateChange("resumeStartMdfDate");
	postAfterDateChange("resumeEndAddDate");
	postAfterDateChange("resumeEndMdfDate");
	postAfterDateChange("startHandleDate");
	postAfterDateChange("endHandleDate");
	$('a[id^="thisWeek"]').click(function(e) {
		e.preventDefault();
		var id = this.id;
		console.log(id);
		$.post('/hrmis/sess/update', {
			thisWeek : id
		}, ajaxCallBack);
	});
});