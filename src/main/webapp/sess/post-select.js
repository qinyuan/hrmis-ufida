$(document).ready(function(){
	postAfterSelectChange('postId');
});
function getPostId(){
	return $('#postId').val();
}