$(document).ready(function() {
	postAfterDateChange("sessEndDate");
});
function getEndDate(){
	return $('#sessEndDate').val();
}