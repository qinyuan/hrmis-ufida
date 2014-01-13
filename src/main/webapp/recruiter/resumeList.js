var resumeModifier = {
	display : 0,
	resumeId : null,
	contentLoaded : false,
	show : function() {
		if (!numeric(this.resumeId))
			return;
		var self = this;
		if (this.display === 0) {
			this.loadResumeDetail(function() {
				CURTAIN.show('resumeMdfDiv');
				self.display = 1;
				self.setDetailMdfVisible(true);
				self.setContentMdfVisible(false);
			});
		}
	},
	getContentMdfDiv : function() {
		return $('#resumeContentMdfDiv');
	},
	loadResumeDetail : function(callBack) {
		var $detailMdfDiv = this.getDetailMdfDiv();
		$.post("resume-mdf-table.action", {
			"resumeId" : this.resumeId
		}, function(data) {
			if (isAjaxData(data)) {
				data = parseAjaxData(data);
				$detailMdfDiv.html(data);
				if (callBack) {
					callBack();
				}
			}
		});
	},
	loadResumeContent : function(callBack) {
		if (!numeric(this.resumeId))
			return;
		var self = this;
		$.post("resume-mdf-table.action", {
			"resumeContentId" : this.resumeId
		}, function(data) {
			if (isAjaxData(data)) {
				data = parseAjaxData(data);
				self.getCKEditor().setData(data);
				self.setResumeContentId(self.resumeId);
				if (callBack) {
					callBack();
				}
			}
		});
	},
	setDetailMdfVisible : function(visible) {
		if (visible) {
			this.getDetailMdfDiv().show();
			this.getContentButton().show();
		} else {
			this.getDetailMdfDiv().hide();
			this.getContentButton().hide();
		}
	},
	setContentMdfVisible : function(visible) {
		if (visible) {
			this.getContentMdfDiv().show();
			this.getDetailButton().show();
		} else {
			this.getContentMdfDiv().hide();
			this.getDetailButton().hide();
		}
	},
	getDetailButton : function() {
		return $('#resumeDetailButton');
	},
	getContentButton : function() {
		return $('#resumeContentButton');
	},
	getDetailMdfDiv : function() {
		return $('#resumeDetailMdfDiv');
	},
	setResumeId : function(resumeId) {
		this.resumeId = resumeId;
	},
	toggle : function() {
		if (this.display === 1) {
			if (!this.contentLoaded) {
				this.loadResumeContent();
				this.contentLoaded = true;
			}
			this.setContentMdfVisible(true);
			this.setDetailMdfVisible(false);
			this.display = 2;
		} else if (this.display === 2) {
			this.setContentMdfVisible(false);
			this.setDetailMdfVisible(true);
			this.display = 1;
		}
	},
	hide : function() {
		this.contentLoaded = false;
		this.display = 0;
		this.resumeId = null;
		this.getCKEditor().setData('');
		this.setContentMdfVisible(false);
		this.setDetailMdfVisible(false);
		this.setResumeContentId(null);
		CURTAIN.hide();
	},
	setResumeContentId : function(resumeId) {
		$('#resumeContentId').val(resumeId);
	},
	getCKEditor : function() {
		return CKEDITOR.instances.editor;
	},
	submit : function() {
		var $experience = $('#m_experience');
		var experience = $experience.val();
		if (experience != "" && (!numeric(experience))) {
			alert("工作年限应为数字格式");
			$experience.select();
		} else {
			submitForm("resumeMdfForm");
		}
	}
};
var deleteResumeAction = function(resumeId) {
	if (numeric(resumeId) && confirm("确定删除？")) {
		$.post("my-resume.action", {
			'delId' : resumeId
		}, ajaxCallBack);
	}
};
var parseResumeId = function(obj) {
	if (obj.id) {
		return obj.id.replace(/\D/g, '');
	} else if (obj.attr && obj.attr('id')) {
		return obj.attr('id').replace(/\D/g, '');
	} else {
		return null;
	}
};
var imageClickAction = function() {
	var id = this.id;
	var resumeId = parseResumeId(this);
	var $tr = $(this).parent().parent();

	if (id.match(/del.*/)) {
		deleteResumeAction(resumeId);
	} else if (id.match(/mdf.*/)) {
		resumeModifier.setResumeId(resumeId);
		resumeModifier.show();
	} else if (id.match(/recommend.*/)) {
		recommend(resumeId);
	} else if (id.match(/detail.*/)) {
		loadRecommendDetail($tr);
		$.cookie('resume-' + resumeId, true);
	} else if (id.match(/hidDetail.*/)) {
		$.cookie('resume-' + resumeId, null);
		$tr.next().remove();
		switchDetailImage(this);
	}
};
var switchDetailImage = function(image) {
	if (image.id.match(/detail.*/g)) {
		image.id = image.id.replace('detail', 'hidDetail');
		image.alt = image.alt.replace('查看', '隐藏');
		image.title = image.title.replace('查看', '隐藏');
		image.src = image.src.replace('down.gif', 'up.gif');
	} else {
		image.id = image.id.replace('hidDetail', 'detail');
		image.alt = image.alt.replace('隐藏', '查看');
		image.title = image.title.replace('隐藏', '查看');
		image.src = image.src.replace('up.gif', 'down.gif');
	}
};
var loadRecommendDetail = function($tr) {
	var resumeId = parseResumeId($tr);
	$.post("recommend-table.action", {
		'resumeId' : resumeId
	}, function(data) {
		if (data.indexOf('无推荐记录') > 0)
			return;
		if (isAjaxData(data)) {
			data = parseAjaxData(data);
			$tr.after('<tr><td colspan="' + $tr.children('td').size()
					+ '"></td></tr>');
			switchDetailImage($tr.find('img[id^="detail"]').get(0));
			var $td = $tr.next().children('td').eq(0);
			$td.html(data).css('padding-left', '30px');
			decorateTable($td);
			addRecDetailAction($td);
		}
	});
};
var recommend = function(resumeId) {
	$.post("available-cus-of-resume.action", {
		"resumeId" : resumeId
	}, function(data) {
		if (!isAjaxData(data))
			return;
		data = parseAjaxData(data);
		$('#recommendListDiv').html(data);
		CURTAIN.show('recommendDiv');
		$('#recommendListDiv a').click(function() {
			var demandId = rightStr(this.href.toString(), "#cus");
			$.post("recommend.action", {
				"resumeId" : resumeId,
				"demandId" : demandId
			}, ajaxCallBack);
		});
	});
};
function addRecDetailAction($td) {
	$td.find('a').aclick(function() {
		var href = rightStr(this.href, "#");
		if (href == 'rm-again') {
			var $prevTr = $(this).parent().parent().prev();
			var resumeId = $prevTr.attr('id').replace(/\D/g, '');
			recommend(resumeId);
		} else if (href == "nextStep") {
			var recommendId = getRecommendId(this);
			$.post('next-step.action', {
				'recommendId' : recommendId
			}, ajaxCallBack);
		} else if (href = "prevStep") {
			var recommendId = getRecommendId(this);
			$.post('prev-step.action', {
				'recommendId' : recommendId
			}, ajaxCallBack);
		}
	});
	function getRecommendId(aObj) {
		var $tr = $(aObj).parent().parent();
		while ((!$tr.attr('id')) && $tr.is('tr')) {
			$tr = $tr.prev();
		}
		return $tr.attr('id').substring(2);
	}
	$td.find('span.recStepDealTime').attr('title', '双击修改').css('cursor',
			'pointer').hover(function() {
		$(this).css({
			'color' : 'red'
		});
	}, function() {
		$(this).css({
			'color' : 'black'
		});
	}).dblclick(function() {
		var recStepId = this.id.replace(/\D/g, '');
		CURTAIN.show('mdfRecStepDealTimeDiv');
		$('#mdfRecStepId').val(recStepId);
		$('#mdfRecStepDealTime').val($(this).text()).select();
	});
	$feedback = $td.find('span.feedback');
	$feedback.attr('title', '双击修改').css('cursor', 'pointer');
	$feedback.hover(function() {
		$(this).css({
			'color' : 'red'
		});
	}, function() {
		$(this).css({
			'color' : 'black'
		});
	});
	$feedback.dblclick(function() {
		var recommendId = getRecommendId(this);
		$('#feedbackMdfRecommendId').val(recommendId);
		$('#feedbackText').val($(this).text());
		CURTAIN.show('mdfFeedbackDiv');
		setTimeout(function() {
			$('#feedbackText').select();
		}, 250);
	});
}
$(document).ready(function() {
	decorateImages($('div.easyui-panel'));
	$('#addResumeButton').click(function() {
		location.href = "add.jsp";
	});
	$('#resumeMdfCancel').aclick(function() {
		resumeModifier.hide();
	});
	$('#resumeMdfOk').aclick(function() {
		resumeModifier.submit();
	});
	$('#resumeContentButton,#resumeDetailButton').aclick(function() {
		resumeModifier.toggle();
	});
	$('a[id$="Cancel"]').filter(function() {
		return this.id != "resumeMdfCancel";
	}).aclick(CURTAIN.hide);
	$('table img').click(imageClickAction);
	$('table tr[id]').each(function() {
		if ($.cookie(this.id.replace('resume', 'resume-'))) {
			loadRecommendDetail($(this));
		}
	});
	$('#mdfFeedbackOk').aclick(function() {
		$.post('mdf-feedback.action', {
			'recommendId' : $('#feedbackMdfRecommendId').val(),
			'feedback' : $('#feedbackText').val()
		}, ajaxCallBack);
	});
	$('#mdfRecStepDealTimeOk').aclick(function() {
		submitForm("mdfRecStepDealTimeForm");
	});
	CKEDITOR.replace('editor', {
		height : 450,
	});
});