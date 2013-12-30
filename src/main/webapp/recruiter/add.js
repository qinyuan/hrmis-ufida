var editorKeyAction = function(e) {
	var keyCode = e.data.keyCode;
	// 1114198 represent ctrl+v
	if (keyCode === 1114198) {
		setTimeout(parseResumeAction, 500);
	}
};
var sourceMapArray = null;
var getEditor = function() {
	return CKEDITOR.instances.editor;
};
var parseResumeAction = function() {
	console.log('parseResumeAction');
	$('span.parseResult').remove();
	if (sourceMapArray == null)
		return false;
	var content = getEditor().getData();
	var sourceMap = getSourceMap(content);
	if (sourceMap == null) {
		return false;
	}
	$('#sourceId').val(sourceMap.id);
	sourceIdChangeAction();
	var map = sourceMap.map;
	for ( var source in map) {
		var target = map[source];
		var value = null, $target = $(target);
		try {
			eval('value=$("<div>"+content+"</div>")' + source);
			value = $.trim(value);
			$target.val(value);
			if (value == '') {
				$target.after(getParseResultSpan(false));
			} else {
				$target.after(getParseResultSpan(true));
			}
		} catch (err) {
			console.log(err);
			$target.after(getParseResultSpan(false));
		}
		// console.log("target: "+target+";source: "+source+";value:
		// "+value);
	}
	$(document).scrollTop(document.body.scrollHeight);
};
var initSourceMapArray = function() {
	$.post('resume-source.action', {}, function(data) {
		if (isAjaxData(data)) {
			data = parseAjaxData(data);
			sourceMapArray = createArrayByStr(data);
			sourceIdChangeAction();
		}
	});
};
var resumeLinkChangeAction = function(resumeLink) {
	if (resumeLink.indexOf('zhaopin.com') > 0) {
		var start = resumeLink.search(/J[A-Z]/);
		var end = resumeLink.indexOf('_', start);
		$('#sourceId').val(3);
		$('#resumeNo').val(resumeLink.substring(start, end));
	} else if (resumeLink.indexOf('51job.com') > 0) {
		var startStr = 'hidUserID=';
		var start = resumeLink.indexOf(startStr) + startStr.length;
		var end = resumeLink.indexOf('&', start);
		$('#sourceId').val(2);
		$('#resumeNo').val(resumeLink.substring(start, end));
	}
};
var addSubmitAction = function() {
	if (!validateResumeInput()) {
		return false;
	}
	var tel = $('#tel').val();
	if (tel != '') {
		validateTel(tel, addResume);
	} else {
		addResume();
	}

	function validateResumeInput() {
		var applicantObj = $('#applicant');
		var applicant = $.trim(applicantObj.val());
		if (applicant == "") {
			alert("姓名未填写");
			applicantObj.focus();
			return false;
		}

		var $experience = $('#experience');
		var experience = $experience.val();
		if ($.trim(experience) == "") {
			alert("工作年限未填写");
			$experience.focus();
			return false;
		} else if (!numeric(experience)) {
			alert("工作年限应为数字格式");
			$experience.select();
			return false;
		}

		var targetPlaceId = $('#targetPlaceId').val();
		if (targetPlaceId === '1') {
			if (!confirm("如果“目标地点”选择“其他”，可能造成导出的周报不准确。\n确定继续添加？")) {
				return false;
			}
		}

		return true;
	}
	function validateTel(tel, callback) {
		$.post('add-resume.action', {
			'searchTel' : $('#tel').val()
		}, function(data) {
			if (isAjaxData(data)) {
				data = parseAjaxData(data);
				if (data == 'true') {
					if (confirm("该号码已经存在，是否还要添加")) {
						callback();
					}
				} else if (data == 'false') {
					callback();
				} else {
					alert(data);
				}
			}
		});
	}
	function addResume() {
		submitForm("addForm", null, function(data) {
			if (isAjaxData(data)) {
				data = parseAjaxData(data);
				if (data == "success") {
					location.href = "resumeList.jsp";
				} else {
					alert(data);
				}
			}
		});
	}
};
var getParseResultSpan = function(success) {
	var str = ' <span class="parseResult" style="color:';
	if (success) {
		str += 'green;">解析成功！';
	} else {
		str += 'red;">解析失败！';
	}
	str += '</span> ';
	return str;
};
var getSourceMap = function(content) {
	for ( var index in sourceMapArray) {
		var sourceMap = sourceMapArray[index];
		var sign = sourceMap['sign'];
		if (content.indexOf(sign) > 0) {
			return sourceMap;
		}
	}
	return null;
};
var sourceIdChangeAction = function() {
	var sourceId = $('#sourceId').val();
	for ( var index in sourceMapArray) {
		var sourceMap = sourceMapArray[index];
		if (sourceId == sourceMap.id) {
			$('#downloaded')[0].checked = (sourceMap.downloaded == 'true');
		}
	}
};
$(function() {
	persistStatus("postId");
	persistStatus("targetPlaceId");
	CKEDITOR.replace('editor', {
		height : 500,
		on : {
			instanceReady : function() {
				this.focus();
			},
			key : editorKeyAction
		}
	});
	$('#addSubmit').click(function(e) {
		e.preventDefault();
		addSubmitAction();
	});
	$('#resumeLink').change(function() {
		resumeLinkChangeAction(this.value);
	});

	initSourceMapArray();
	$('#sourceId').change(sourceIdChangeAction);
	$('#parseResumeButton').click(function(e) {
		e.preventDefault();
		parseResumeAction();
	});
});