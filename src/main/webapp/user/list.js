$(document).ready(function() {
	decorateImages($('body'));
	$('img[id^="pri"]').click(function() {
		if($('#priDiv').css('display')=='none'){
			$('#priDiv').show();
			$('#priPanel').panel({
				title:"用户权限设置",
				iconCls:'icon-ok'
			});
		}
		var userId = this.id.replace('pri', '');
		$.post("pri-panel.action", {
			"userId" : userId
		}, afterCheckBoxDisplay);
		function afterCheckBoxDisplay(data) {
			if (!isAjaxData(data))
				return;
			data = parseAjaxData(data);
			
			$('div#priContent').empty().append(data)
				.find('input:checkbox').each(function() {
				if (!this.checked) {
					$(this).parent().css('color', 'gray');
				}
			}).click(function() {
				var priId = this.id.substring(3);
				$.post("mdf-pri.action", {
					'userId' : userId,
					'priId' : priId,
					'status' : this.checked
				}, function(data) {
					if (!isAjaxData(data))
						return;
					data = parseAjaxData(data);
					$('div#result').text(data);
				});

				if (this.checked) {
					$(this).parent().css('color', 'black');
				} else {
					$(this).parent().css('color', 'gray');
				}
			});
		}
	});
	$('img[id^="mdf"]').click(function() {
		var mdfId = this.id.replace('mdf', '');
		jTr = $(this).parent();
		while(!jTr.is('tr'))
			jTr=jTr.parent();
		var mdfName = jTr.children('td:eq(0)').text();
		var mdfPassword = jTr.children('td:eq(1)').text();
		$('#mdfName').val(mdfName);
		$('#mdfPassword').val(mdfPassword);
		$('#mdfId').val(mdfId);
		if($('#mdfDiv').css('display')=='none'){
			$('#mdfDiv').show();
			$('#mdfPanel').panel({
				title:"修改用户信息",
				iconCls: 'icon-ok'
			});
		}
	});
	$('img[id^="del"]').click(function() {
		if (!confirm("确定删除？"))
			return;
		var delId = this.id.replace('del', '');
		$.post('list.action', {
			delId : delId
		}, ajaxCallBack);
	});
	$('#mdfCancel').click(function() {
		$('#mdfDiv').hide(250);
	});
	$('#mdfOK').click(function() {
		var mdfId = $('#mdfId').val();
		var mdfName = $('#mdfName').val();
		var mdfPassword = $('#mdfPassword').val();
		$.post('list.action', {
			mdfId : mdfId,
			mdfName : mdfName,
			mdfPassword : mdfPassword
		}, ajaxCallBack);
	});
});