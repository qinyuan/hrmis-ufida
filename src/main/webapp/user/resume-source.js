$(document).ready(function() {
	decorateImages($('body'));
	$('#addButton').click(function(){
		CURTAIN.show('addDiv');
	});
	$('img[id^="mdf"]').click(function() {
		var mdfId = this.id.replace('mdf', '');
		$tr = $(this).parent();
		while (!$tr.is('tr'))
			$tr = $tr.parent();
		var mdfName = $tr.children('td:eq(0)').text();
		var mdfIdPattern = $tr.children('td:eq(1)').text();
		var mdfHrefPrefix = $tr.children('td:eq(2)').attr('title');
		var mdfHrefSuffix = $tr.children('td:eq(3)').attr('title');
		var mdfSign=$tr.children('td:eq(4)').text();
		var mdfDownloaded=$tr.children('td:eq(5)').text()=='是';
		$('#mdfId').val(mdfId);
		$('#mdfName').val(mdfName);
		$('#mdfIdPattern').val(mdfIdPattern);
		$('#mdfHrefPrefix').val(mdfHrefPrefix);
		$('#mdfHrefSuffix').val(mdfHrefSuffix);
		$('#mdfSign').val(mdfSign);
		$('#mdfDownloaded')[0].checked=mdfDownloaded;
		CURTAIN.show('mdfDiv');
	});
	$('img[id^="del"]').click(function() {
		if (!confirm("确定删除？"))
			return;
		var delId = this.id.replace('del', '');
		$.post('resume-source.action', {
			delId : delId
		}, ajaxCallBack);
	});
	$('a[id$="Cancel"]').click(function(){
		CURTAIN.hide();
	});
	$('a[id$="Ok"]').click(function(){
		var formId=this.id.substring(0,3)+"Form";
		submitForm(formId);
	});
});