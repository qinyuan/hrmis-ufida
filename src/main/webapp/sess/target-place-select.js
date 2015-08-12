$(document).ready(function() {
	postAfterSelectChange('sessTargetPlaceId');
});
function getRecruiterId(){
	return $('#sessTargetPlaceId').val();
}