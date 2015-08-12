$(document).ready(function() {
	postAfterDateChange("sessStartDate");
});
function getStartDate(){
	return $.trim($('#sessStartDate').val());
}