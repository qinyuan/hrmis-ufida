var validateResumeInput=function() {
	var $experience = $('#m_experience');
	var experience = $experience.val();
	if (experience != "" && (!numeric(experience))) {
		alert("工作年限应为数字格式");
		$experience.select();
		return false;
	}
	return true;
};
$(document).ready(function() {
	var ic = new InputCurtain();
	ic.add("hiddenDiv");
	decorateImages($('div.easyui-panel'));
	$('#addResumeButton').click(function(){
		location.href="add.jsp";
	});
	$('#resumeMdfCancel').aclick(function(){
		getContentMdfDiv().empty();
		getDetailMdfDiv().empty();
		CURTAIN.hide();
	});
	$('#resumeMdfOk').aclick(function(){
		if(validateResumeInput()){
			submitForm("resumeMdfForm");
		}
	});
	$('#resumeInfoButton').hide();
	$('#resumeContentButton').aclick(function(){
		$('#resumeInfoButton').show();
		mdfResumeContent($('#m_resumeId').val());
		$(this).hide();
	});
	$('#resumeInfoButton').aclick(function(){
		$('#resumeContentButton').show();
		mdfResume($('#resumeContentId').val());
		$(this).hide();
	});
	$('#availableCusCancel').click(function(){
		$('#availableCusDiv').hide();
		ic.hide();
	});
	function delResume(resumeId){
		if(numeric(resumeId) && confirm("确定删除？")){
			$.post("my-resume.action",{
				'delId':resumeId
			}, ajaxCallBack);
		}
	}
	function mdfResumeContent(resumeId){
		if(!numeric(resumeId))
			return;
		displayResumeMdfDiv();
		getDetailMdfDiv().hide();
		var $contentMdfDiv=getContentMdfDiv();
		$contentMdfDiv.show();
		if($contentMdfDiv.text()==''){
			$.post("resume-mdf-table.action", {
				"resumeContentId" : resumeId
			}, function(data) {
				if(isAjaxData(data)){
					data=parseAjaxData(data);
					var tempHTML='<textarea name="editor" id="editor"></textarea>'+
						'<input type="hidden" name="resumeContentId" id="resumeContentId" value="'
						+resumeId+'" />';
					$contentMdfDiv.html(tempHTML);
					CKEDITOR.replace('editor',{
						height:500,
					});
					CKEDITOR.instances.editor.setData(data);
				}
			});
		}	
	}
	function mdfResume(resumeId){
		if(!numeric(resumeId))
			return;
		displayResumeMdfDiv();
		getContentMdfDiv().hide();
		var $detailMdfDiv=getDetailMdfDiv();
		$detailMdfDiv.show();
		if($detailMdfDiv.text()==''){
			$.post("resume-mdf-table.action", {
				"resumeId" : resumeId
			}, function(data) {
				if(isAjaxData(data)){
					data=parseAjaxData(data);
					$detailMdfDiv.html(data);
				}
			});
		}		
	}
	function getResumeId(obj){
		if(obj.id){
			return obj.id.replace(/\D/g,'');
		}else if(obj.attr && obj.attr('id')){
			return obj.attr('id').replace(/\D/g,'');
		}
	}
	$('table img').click(function(){
		var id=this.id;
		var resumeId=getResumeId(this);
		var jtr=$(this).parent().parent();
		
		if(id.match(/del.*/)){
			delResume(resumeId);
		}else if(id.match(/mdf.*/)){
			mdfResume(resumeId);
		}else if(id.match(/recommend.*/)){
			recommend(resumeId);
		}else if(id.match(/detail.*/)){
			loadRecommendDetail(jtr);
			$.cookie('resume-'+resumeId,true);
		}else if(id.match(/hidDetail.*/)){
			$.cookie('resume-'+resumeId,null);
			jtr.next().remove();
			switchDetailImage(this);
		}
	});
	function switchDetailImage(image){
		if(image.id.match(/detail.*/g)){
			image.id=image.id.replace('detail','hidDetail');
			image.alt=image.alt.replace('查看','隐藏');
			image.title=image.title.replace('查看','隐藏');
			image.src=image.src.replace('down.gif','up.gif');
		}else{
			image.id=image.id.replace('hidDetail','detail');
			image.alt=image.alt.replace('隐藏','查看');
			image.title=image.title.replace('隐藏','查看');
			image.src=image.src.replace('up.gif','down.gif');
		}
	}
	$('table tr[id]').each(function(){
		if($.cookie(this.id.replace('resume','resume-'))){
			loadRecommendDetail($(this));
		}
	});
	function loadRecommendDetail(jTr){
		var resumeId=getResumeId(jTr);
		$.post("recommend-table.action",{
			'resumeId':resumeId
		},function(data){
			if(isAjaxData(data)){
				data=parseAjaxData(data);
				if(data.indexOf('无推荐记录')>0	){
					return;
				}
				jTr.after('<tr><td colspan="'+jTr.children('td').size()+'"></td></tr>');
				switchDetailImage(jTr.find('img[id^="detail"]').get(0));
				var jTd=jTr.next().children('td').eq(0);
				jTd.html(data).css('padding-left','30px');
				decorateTable(jTd);
				addRecDetailAction(jTd);
			}
		});
	}
	function addRecDetailAction(jTd){
		jTd.find('a').each(function(){
			var href=rightStr(this.href, "#");
			if(href=='rm-again'){
				$(this).click(function(e){
					e.preventDefault();
					var resumeId=$(this).parent().parent().prev().
						attr('id').replace('resume','');
					recommend(resumeId);
				});
			}else if(href=="nextStep"){
				$(this).click(function(e){
					var recommendId=getRecommendId(this);
					$.post('next-step.action',{
						'recommendId':recommendId
					},ajaxCallBack);
					e.preventDefault();
				});
			}else if(href="prevStep"){
				$(this).click(function(e){
					var recommendId=getRecommendId(this);
					$.post('prev-step.action',{
						'recommendId':recommendId
					},ajaxCallBack);
					e.preventDefault();
				});
			}			
		});
		function getRecommendId(aObj){
			var jTr=$(aObj).parent().parent();
			while((!jTr.attr('id')) && jTr.is('tr')){
				jTr=jTr.prev();
			}
			return jTr.attr('id').substring(2);
		}
		jTd.find('span.recStepDealTime').attr('title','双击修改')
			.css('cursor','pointer').hover(function(){
				$(this).css({
					'color' :'red'
				});
			},function(){
				$(this).css({
					'color' :'black'
				});
			}).dblclick(function(){
			var recStepId=this.id.replace(/\D/g,'');
			CURTAIN.show('mdfRecStepDealTimeDiv');
			$('#mdfRecStepId').val(recStepId);
			$('#mdfRecStepDealTime').val($(this).text()).select();
		});
		jTd.find('span.feedback').attr('title','双击修改')
			.css('cursor','pointer').hover(function(){
			$(this).css({
				'color' :'red'
			});
		},function(){
			$(this).css({
				'color' :'black'
			});
		}).dblclick(function(){
			var recommendId=getRecommendId(this);
			$('#contentDiv').html(
					'<input type="hidden" id="feedbackMdfRecommendId" value="'+
					recommendId+'" />'+
					'<textarea id="feedbackText" cols="50" rows="5">'+
					$(this).text()+
					'</textarea>'
			);
			$('#mdfFeedbackDiv').show();
			ic.show();
			$('#feedbackText').select();
		});
	}
	$('#mdfFeedbackOk').click(function(e){
		e.preventDefault();
		$.post('mdf-feedback.action',{
			'recommendId':$('#feedbackMdfRecommendId').val(),
			'feedback':$('#feedbackText').val()
		},ajaxCallBack);
	});
	$('#mdfFeedbackCancel').click(function(e){
		e.preventDefault();
		$('#mdfFeedbackDiv').hide();
		ic.hide();
	});
	$('#mdfRecStepDealTimeOk').click(function(e){
		e.preventDefault();
		submitForm("mdfRecStepDealTimeForm");
	});
	$('#mdfRecStepDealTimeCancel').click(CURTAIN.hide);
	function recommend(resumeId){
		$.post("available-cus-of-resume.action",{
			"resumeId":resumeId
		},function(data){
			if(!isAjaxData(data))
				return;
			data=parseAjaxData(data);
			$('#contentDiv').html(data);
			$('#availableCusDiv').show();
			ic.show();
			$('#recommendCancel').click(function(){
				ic.hide();
			});
			$('#contentDiv a').click(function(){
				var demandId=rightStr(this.href.toString(), "#cus");
				$.post("recommend.action",{
					"resumeId":resumeId,
					"demandId":demandId
				},ajaxCallBack);
			});
		});
	}
});
function displayResumeMdfDiv(){
	if(getContentMdfDiv().text()=='' && getDetailMdfDiv().text()==''){
		CURTAIN.show('resumeMdfDiv');
	}
}
function getContentMdfDiv(){
	return $('#resumeContentMdfDiv');
}
function getDetailMdfDiv(){
	return $('#resumeDetailMdfDiv');
}