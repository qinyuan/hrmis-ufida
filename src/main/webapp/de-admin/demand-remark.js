autoDecorateTable=false;
$(function() {
	CKEDITOR.replace('editor',{
		height:300,
	});
	decorateTable($('#demandDetail,#recommendList'), false);
	var demandId=$('#hidDemandId').val();
	var remarkId=null;
	var add=true;
	$('a[href$="#add"]').click(function(e){
		e.preventDefault();
		add=true;
		CURTAIN.show('editorDiv');
		var editor=getEditor();
		editor.setData("");
		setTimeout(function(){
			editor.focus();
		}, 500);
	});
	$('a[href$="#mdf"]').click(function(e){
		e.preventDefault();
		add=false;
		remarkId=this.id.replace('mdf','');
		getEditor().setData($(this).parent().prev().html());
		CURTAIN.show('editorDiv');
	});
	$('a[href$="#del"]').click(function(e){
		e.preventDefault();
		if(confirm("确定删除？")){
			var delId=this.id.replace('del','');
			$.post('demand-remark.action',{
				'delId':delId
			},ajaxCallBack);
		}
	});
	
	$('#submitButton').click(function(e){
		e.preventDefault();
		if(add){
			$.post('demand-remark.action',{
				'addDemandId':demandId,
				'addContent':getContent()
			},ajaxCallBack);
		}else if(remarkId){
			$.post('demand-remark.action',{
				'mdfId':remarkId,
				'mdfContent':getContent()
			},ajaxCallBack);
		}
	});
	$('#cancelButton').click(function(e){
		e.preventDefault();
		CURTAIN.hide();
	});
	function getContent(){
		return getEditor().getData();
	}
	function getEditor(){
		return CKEDITOR.instances.editor;
	}
});