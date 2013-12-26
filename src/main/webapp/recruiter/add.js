$(document).ready(function() {
	persistStatus("postId");
	persistStatus("targetPlaceId");
	CKEDITOR.replace('editor',{
		height:500,
		on : {
			instanceReady : function() {
				this.focus();
			}
		}
	});
	$('#addSubmit').click(function(e) {
		if (!validate()){
			e.preventDefault();
			return false;
		}
		if($('#tel').val()==''){
			addResume();
			return;
		}
		$.post('add-resume.action',{
			'searchTel':$('#tel').val()
		},function(data){
			if(isAjaxData(data)){
				data=parseAjaxData(data);
				if(data=='true'){
					if(confirm("该号码已经存在，是否还要添加")){
						addResume();
					}
				}else if(data=='false'){
					addResume();
				}else{
					alert(data);
				}
			}
		});
		function addResume(){
			submitForm("addForm", 'add-resume.action', function(data){
				if(isAjaxData(data)){
					data=parseAjaxData(data);
					if(data=="success"){
						location.href="resumeList.jsp";
					}else{
						alert(data);
					}
				}
			});
		}		
	});
	$('#resumeLink').change(function(){
		var resumeLink=this.value;
		if(resumeLink.indexOf('zhaopin.com')>0){
			var start=resumeLink.search(/J[A-Z]/);
			var end=resumeLink.indexOf('_',start);
			$('#sourceId').val(3);
			$('#resumeNo').val(resumeLink.substring(start,end));
		}else if(resumeLink.indexOf('51job.com')>0){
			var startStr='hidUserID=';
			var start=resumeLink.indexOf(startStr)+startStr.length;
			var end=resumeLink.indexOf('&',start);
			$('#sourceId').val(2);
			$('#resumeNo').val(resumeLink.substring(start,end));
		}
	});
	var sourceMapArray=null;
	$.post('resume-source.action',{},function(data){
		if(isAjaxData(data)){
			data=parseAjaxData(data);
			sourceMapArray=createArrayByStr(data);
			afterSourceIdChange();
		}
	});
	$('#sourceId').change(function(){
		afterSourceIdChange();
	});
	$('#parseResumeButton').click(function(e){
		e.preventDefault();
		$('span.parseResult').remove();
		if(sourceMapArray==null)
			return false;
		var content=getEditor().getData();
		var sourceMap=getSourceMap(content);
		if(sourceMap==null){
			return false;
		}
		$('#sourceId').val(sourceMap.id);
		afterSourceIdChange();
		var map=sourceMap.map;
		for(var source in map){
			var target=map[source];
			var value,$target=$(target);
			try{
				eval('value=$("<div>"+content+"</div>")'+source);
				value=$.trim(value);
				$target.val(value);
				if(value==''){
					$target.after(getParseResultSpan(false));
				}else{
					$target.after(getParseResultSpan(true));
				}
			}catch(err){
				console.log(err);
				$target.after(getParseResultSpan(false));
			}
			//console.log("target: "+target+";source: "+source+";value: "+value);
		}
		return false;
	});
	function getEditor(){
		return CKEDITOR.instances.editor;
	}
	function validate() {
		var applicantObj = $('#applicant');
		var applicant = $.trim(applicantObj.val());
		if (applicant == "") {
			alert("姓名未填写");
			applicantObj.focus();
			return false;
		}

		var experienceObj = $('#experience');
		var experience = experienceObj.val();
		if ($.trim(experience) == "") {
			alert("工作年限未填写");
			experienceObj.focus();
			return false;
		} else if (!numeric(experience)) {
			alert("工作年限应为数字格式");
			experienceObj.select();
			return false;
		}
		return true;
	}
	function getParseResultSpan(success){
		var str=' <span class="parseResult" style="color:';
		if(success){
			str+='green;">解析成功！';
		}else{
			str+='red;">解析失败！';
		}
		str+='</span> ';
		return str;
	}
	function getSourceMap(content){
		for(var index in sourceMapArray){
			var sourceMap=sourceMapArray[index];
			var sign=sourceMap['sign'];
			if(content.indexOf(sign)>0){
				return sourceMap;
			}
		}
		return null;
	}
	function afterSourceIdChange(){
		var sourceId=$('#sourceId').val();
		for(var index in sourceMapArray){
			var sourceMap=sourceMapArray[index];
			if(sourceId==sourceMap.id){
				$('#downloaded')[0].checked=(sourceMap.downloaded=='true');
			}
		}
	}
});