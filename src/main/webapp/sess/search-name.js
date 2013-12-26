function doSearchName(value) {
	$.post('/hrmis/sess/update',{
		'sessSearchName':value
	},ajaxCallBack);
}
function doSearchTel(value) {
	$.post('/hrmis/sess/update',{
		'sessSearchTel':value
	},ajaxCallBack);
}