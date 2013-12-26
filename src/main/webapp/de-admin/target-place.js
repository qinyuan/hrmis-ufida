$(document).ready(function() {
	decorateImages('table');
	var addText=new InfoText("newTargetPlaceName", "此处输入新目标地点...");
	$('#addButton').click(function() {
		if(addText.val()==""){
			alert("新的目标地点不能为空");
			addText.focus();
		}else{
			submitForm("addForm");
		}
	});
	$('table img[id]').click(function() {
		var id = this.id.replace(/\D/g,'');
		var href="TargetPlace.action";
		if (this.id.match(/mdf.*/)) {
			var oldName = $(this).parent().parent().children('td:eq(0)').text();
			var mdfName = prompt("请输入新的目标地点：", oldName);
			if (mdfName != null) {
				mdfName = $.trim(mdfName);
				if (mdfName == "") {
					alert("目标地点不能为空");
				} else {
					$.post(href,{
						"mdfId" : id,
						"mdfName" : mdfName
					},ajaxCallBack);
				}
			}
		} else if (this.id.match(/del.*/)) {
			if(!confirm("确认删除？")){
				return;
			}
			$.post(href, {
				"delId" : id
			}, ajaxCallBack);
		}
	});
});