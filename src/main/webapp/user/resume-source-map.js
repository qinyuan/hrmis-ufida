$(function() {
	decorateImages($('body'));
	$('a[id$="OK"]').click(function(){
		var formId=this.id.substring(0,3)+"Form";
		submitForm(formId,'resume-source-map.action', ajaxCallBack);
	});
	$('img[id^="mdf"]').click(function(){
		var mdfId = this.id.replace('mdf', '');
		var jTr = $(this).parent();
		while (!jTr.is('tr'))
			jTr = jTr.parent();
		$('#mdfId').val(mdfId);
		var sourceId=jTr.children('td:eq(0)').find('span:eq(0)').attr('id').replace('source','');
		$('#mdfSourceId').val(sourceId);
		$('#mdfSourceSelector').val(jTr.children('td:eq(1)').text());
		$('#mdfTargetSelector').val(jTr.children('td:eq(2)').text());
		$('#mdfRemark').val(jTr.children('td:eq(3)').text());
		if ($('#mdfDiv').css('display') == 'none') {
			$('#mdfDiv').show();
			$('#mdfPanel').panel({
				title : "修改简历来源信息",
				iconCls : 'icon-ok'
			});
		}
	});
	$('img[id^="del"]').click(function() {
		if (!confirm("确定删除？"))
			return;
		var delId = this.id.replace("del", "");
		$.post('resume-source-map.action', {
			delId : delId
		}, ajaxCallBack);
	});
	$('#mdfCancel').click(function() {
		$('#mdfDiv').hide(250);
	});
	
	$('#closeDebugButton').click(function(e){
		$('#debugDiv > div').hide();
		$('#openDebugButton').show();
		$(this).hide();
		e.preventDefault();
	}).hide();
	$('#openDebugButton').click(function(e){
		$('#debugDiv > div').show();
		$('#closeDebugButton').show();
		$(this).hide();
		e.preventDefault();
	});
	$('#debugResultButton').click(function(e){
		var selector=$('#debugSelector').val();
		if($.trim(selector)!=''){
			var value=null;
			try{
				eval('value=$("<div>"+getEditorContent()+"</div>")'+selector+';');
			}catch(err){
				alert(err);
			}
			$('#debugResultText').val(value);
		}else{
			alert("选择器未填写");
		}
		e.preventDefault();
	});
	CKEDITOR.replace('ckeditor',{
		height:300,
	});
	function getEditorContent(){
		return CKEDITOR.instances.ckeditor.getData();
	}
});