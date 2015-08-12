$(document).ready(function() {
	postAfterSelectChange('recruiterId');
});
function getRecruiterId(){
	return $('#recruiterId').val();
}