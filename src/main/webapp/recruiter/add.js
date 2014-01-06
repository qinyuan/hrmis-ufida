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
	validateTel();
	validateResumeNo();
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
var validateResumeNo = function(callBack) {
	var resumeNo = $('#resumeNo').val();
	postSearchResumeNo(resumeNo);
};
var postSearchResumeNo = function(resumeNo, callBack) {
	$.post('add-resume.action', {
		'searchResumeNo' : resumeNo
	}, function(data) {
		if (isAjaxData(data)) {
			data = parseAjaxData(data);
			if (data.indexOf('table') > 0) {
				var $telSearchTableDiv = $('#resumeNoSearchTableDiv');
				$telSearchTableDiv.html(data);
				decorateTable($('#resumeNoSearchTableDiv'), true);
				CURTAIN.show("resumeNoSearchDiv");
				if (callBack) {
					callBack(true);
				}
			} else if (data.length > 0) {
				alert(data);
			} else {
				if (callBack) {
					callBack(false);
				}
			}
		}
	});
};
var searchResumeNoAction = function() {
	var searchResumeNo = $('#searchResumeNoText').val();
	var $searchRepeatResultDiv = $('#searchRepeatResultDiv');
	if ($.trim(searchResumeNo) == '') {
		$searchRepeatResultDiv.html('未输入');
		return;
	}
	postSearchResumeNo(searchResumeNo, function(repeat) {
		if (!repeat) {
			$searchRepeatResultDiv.html('编号未被录入');
		} else {
			$searchRepeatResultDiv.html('&nbsp;');
		}
	});
};
var validateTel = function() {
	var tel = $('#tel').val();
	$.post('add-resume.action', {
		'searchTel' : tel
	}, function(data) {
		if (isAjaxData(data)) {
			data = parseAjaxData(data);
			if (data.indexOf('table') > 0) {
				var $telSearchTableDiv = $('#telSearchTableDiv');
				$telSearchTableDiv.html(data);
				decorateTable($('#telSearchTableDiv'), true);
				CURTAIN.show("telSearchDiv");
			} else if (data.length > 0) {
				alert(data);
			}
		}
	});
};
var addSubmitAction = function() {
	if (!validateResumeInput()) {
		return false;
	}
	addResume();

	function validateResumeInput() {
		var $applicant = $('#applicant');
		var applicant = $.trim($applicant.val());
		if (applicant == "") {
			alert("姓名未填写");
			$applicant.focus();
			return false;
		}

		var $experience = $('#experience');
		var experience = $experience.val();
		if (experience != "" && (!numeric(experience))) {
			alert("工作年限应为数字格式");
			$experience.select();
			return false;
		}

		var $resumeNo = $('#resumeNo');
		var resumeNo = $.trim($resumeNo.val());
		var sourceId = $('#sourceId').val();
		// 2 and 3 represent 51job and hr.zhaopin
		if (resumeNo == "" && (sourceId == 2 || sourceId == 3)) {
			alert("简历编号未填写(前程无忧与智联招聘均要求填写简历编号)");
			$resumeNo.focus();
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

	initSourceMapArray();
	$('#sourceId').change(sourceIdChangeAction);
	$('#parseResumeButton').click(function(e) {
		e.preventDefault();
		parseResumeAction();
	});
	$('#tel').blur(function() {
		validateTel();
	});
	$('#resumeNo').blur(function() {
		validateResumeNo();
	});
	$('a[id$="CloseButton"]').click(function(e) {
		e.preventDefault();
		CURTAIN.hide();
	});
	$('#searchResumeNoButton').click(function(e) {
		e.preventDefault();
		searchResumeNoAction();
	});
	$('#searchResumeNoText').keyup(function(e) {
		if (e.keyCode === 86 && e.ctrlKey) {
			setTimeout(searchResumeNoAction, 200);
		}
	});
});