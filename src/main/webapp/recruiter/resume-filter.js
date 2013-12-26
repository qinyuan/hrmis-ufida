$(function() {
	var cookieParamName='resumeFilterDivCollapsed';
	if ($('#filterDiv').size() == 1) {
		var obj = {
			title : '简历筛选',
			collapsible : true,
			onCollapse : function() {
				filterDivToggle(true);
			},
			onExpand : function() {
				filterDivToggle(false);
			}
		};
		var collapsed=$.cookie(cookieParamName);
		if(collapsed && collapsed=="true"){
			obj['collapsed']=true;
		}
		$('#filterDiv').panel(obj);
		$('#filterDiv').css({
			"padding-top" : "10px",
			"padding-bottom" : "10px",
			"height" : "110px"
		});
	}
	function filterDivToggle(collapse) {
		$.cookie(cookieParamName, collapse);
	}
	$('#clearFilterButton').click(function(e){
		var obj={};
		$('#filterDiv input[id]').each(function(){
			obj[this.id]='';
		});
		$('#filterDiv select[id]').each(function(){
			obj[this.id]=0;
		});
		obj['sessSearchName']='';
		obj['sessSearchTel']='';
		delete obj.recruiterId;
		$.post('/hrmis/sess/update',obj,function(data){
			if(isAjaxData(data)){
				data=parseAjaxData(data);
				if(data==''){
					var href=location.href.toString();
					if(href.indexOf('?')<0){
						href=href+"?";
					}
					href=href.replace(/pageNumber=\d+/g,'')+"&pageNumber=1&";
					href=href.replace('&&','&').replace('?&','?').replace(/&$/g,'');
					if(href==location.href.toString()){
						location.reload();
					}else{
						location.href=href;
					}
				}else{
					alert(data);
				}
			}
		});
		e.preventDefault();
	}).attr("title",'清空“创建者”以外的筛选项');	
});