var autoDecorateTable;
$(function() {
	if (autoDecorateTable == undefined || autoDecorateTable == true) {
		decorateTable($('div.easyui-panel'));
	}
});
jQuery.fn.extend({
	aclick : function(callback) {
		return this.click(function(e) {
			e.preventDefault();
			callback.call(this, e);
		});
	}
});
function ClickedSpan($span) {
	var self = this, select = null, unselect = null;
	this.checkedSpan = null;
	this.select = function(func) {
		select = func;
		return self;
	};
	this.unselect = function(func) {
		unselect = func;
		return self;
	};
	this.setSelect = function(spanObj) {
		spanClickEvent(spanObj);
	};

	$span.mouseover(function() {
		$(this).css('cursor', 'pointer');
	}).click(function() {
		spanClickEvent(this);
	});

	function spanClickEvent(spanObj) {
		if (spanObj == self.checkedSpan) {
			unSelectSpan(spanObj);
			self.checkedSpan = null;
			if (unselect) {
				unselect(spanObj);
			}
		} else {
			if (self.checkedSpan) {
				unSelectSpan(self.checkedSpan);
			}
			spanObj.style.backgroundColor = "lightblue";
			self.checkedSpan = spanObj;
			if (select) {
				select(spanObj);
			}
		}
	}

	function unSelectSpan(spanObj) {
		spanObj.style.backgroundColor = null;
	}
}
function Tree(id) {
	var self = this;
	this.lastCheckedId = null;

	var onclick = null;
	this.onclick = function(func) {
		onclick = func;
	};

	$('#' + id + ' ul').addClass('tree');
	var clickedSpan = new ClickedSpan($('#' + id + ' ul span'));
	clickedSpan.select(function(span) {
		self.lastCheckedId = $(span).parent().attr('id');
		if (onclick)
			onclick();
	}).unselect(function(span) {
		self.lastCheckedId = null;
		if (onclick)
			onclick();
	});

	// DIV event
	$('#' + id + ' ul div').click(function() {
		var imageUrl = $(this).css('background-image');
		var newUrl;
		if (imageUrl.indexOf('minus') > 0) {
			newUrl = imageUrl.replace('minus', 'plus');
			$(this).parent().find('li').hide();
		} else {
			newUrl = imageUrl.replace('plus', 'minus');
			$(this).parent().find('li').show();
		}
		$(this).css('background-image', newUrl);
	});
}
function decorateTable($obj, zebra) {
	if (!$obj.is('table')) {
		$obj.find('table').each(function() {
			decorateTable($(this), zebra);
		});
	} else {
		$obj.css({
			'border-collapse' : 'collapse',
			'font-size' : '10pt'
		});
		$obj.find('thead tr').css({
			'background-color' : '#CCDDFF'
		});
		var tdStyle = {
			'padding' : '5px',
			'border' : '1px solid lightgray'
		};
		$obj.find('th').css(tdStyle);
		$obj.find('td').css(tdStyle);
		var tBody = $obj.find('tbody');
		if (zebra != false) {
			tBody.find('tr:odd').css('background-color', 'gainsboro');
			tBody.find('tr:even').css('background-color', 'white');
		}
		var bgColor = null;
		tBody.find('tr').hover(function() {
			bgColor = String(this.style.backgroundColor);
			this.style.backgroundColor = "yellow";
		}, function() {
			if (bgColor != null) {
				this.style.backgroundColor = bgColor;
			}
		});
	}
}
function decorateImages(jParent) {
	if (typeof (jParent) == 'string')
		jParent = $(jParent);
	jParent.find('img').filter(function() {
		return this.id || $(this).attr('data-options');
	}).css({
		'cursor' : 'pointer',
	}).hover(function() {
		$(this).css({
			'background-color' : 'lime',
			'outline' : '3px solid lime'
		});
	}, function() {
		$(this).css({
			'background-color' : 'transparent',
			'outline' : 'none'
		});
	});
}
function createObjectByStr(str) {
	var obj = null;
	eval('obj=' + str);
	return obj;
}
function createArrayByStr(str) {
	var array = null;
	eval('array=' + str);
	return array;
}
function addLocationParamAndReload(paramObj) {
	var href = location.pathname;
	var hash = location.hash;
	var search = location.search;

	function pushSearch(name, value) {
		search = search.replace('?', '');
		var searchArr = null;
		if (search == '') {
			searchArr = new Array();
			searchArr[0] = name + "=" + value;
		} else {
			searchArr = search.split('&');
			for (var i = 0; i < searchArr.length; i++) {
				if (searchArr[i].split('=')[0] == name) {
					searchArr[i] = name + "=" + value;
					break;
				}
				if (i == searchArr.length - 1) {
					searchArr[i + 1] = name + "=" + value;
				}
			}
		}
		search = '';
		for (var i = 0; i < searchArr.length; i++) {
			search += (i == 0) ? "?" : "&";
			search += searchArr[i];
		}
	}

	for ( var name in paramObj) {
		var value = paramObj[name];
		if (name && value) {
			pushSearch(name, value);
		}
	}
	href += search + hash;
	location.href = href;
}
function postAfterDateChange(id) {
	$('#' + id).click(function() {
		WdatePicker({
			onpicked : function() {
				dateChange($dp.cal.getDateStr());
			},
			oncleared : function() {
				dateChange("");
			}
		});
		function dateChange(dateStr) {
			var href = '/hrmis/sess/update';
			var data = new Object();
			data[id] = dateStr;
			$.post(href, data, reloadAfterPost);
		}
	});
}
function postAfterTextChange(id) {
	var $obj = $('#' + id);
	var href = '/hrmis/sess/update';
	if ($obj.is('input')) {
		$obj.change(function() {
			var data = {};
			data[id] = $obj.val();
			$.post(href, data, reloadAfterPost);
		});
	}
}
function postAfterSelectChange(id) {
	var $obj = $('#' + id);
	var href = '/hrmis/sess/update';
	if ($obj.is('select')) {
		$obj.change(function() {
			var data = new Object();
			data[id] = $obj.val();
			$.post(href, data, reloadAfterPost);
		});
	}
}
function reloadAfterPost(data) {
	if (isAjaxData(data)) {
		data = parseAjaxData(data);
		if (data == "") {
			var href = location.href.toString();
			if (href.indexOf('useSessionParam=false') >= 0) {
				href = href.replace('useSessionParam=false', '');
				location.href = href;
			} else {
				location.reload();
			}
		} else {
			alert(data);
		}
	}
}
function ajaxCallBack(data) {
	if (isAjaxData(data)) {
		data = parseAjaxData(data);
		if (data == "") {
			location.reload();
		} else {
			alert(data);
		}
	}
}
function parseAjaxData(data) {
	if (isAjaxData(data)) {
		data = $.trim(data);
		return $.trim(data.substring(5));
	} else {
		return data;
	}
}
/**
 * if receive "ab123", will return 123
 */
function filterInt(str) {
	return str ? str.replace(/\D/g, '') : null;
}
function persistStatus(id) {
	var paramName = location.href + '_' + id;
	paramName = paramName.replace(/\W/g, '_');
	var $obj = $('#' + id);
	if (isCheckBox()) {
		$obj.click(function() {
			$.cookie(paramName, this.checked);
		});
		if ($.cookie(paramName)) {
			$obj.each(function() {
				this.checked = ($.cookie(paramName) == 'true' ? true : false);
			});
		}
	} else {
		$obj.change(function() {
			$.cookie(paramName, $(this).val());
		});
		if ($.cookie(paramName)) {
			$obj.val($.cookie(paramName));
		}
	}
	function isCheckBox() {
		return $obj.is('input') && $obj.attr('type') == 'checkbox';
	}
}
function isAjaxData(data) {
	data = $.trim(data);
	if (data != null && data.match(/ajax:.*/g)) {
		return true;
	} else {
		if (data != ""
				&& confirm('您已经有一段时间未活动，需要重新登录\n' + '如要转到登录页，则选择“确定”\n'
						+ '如要停留在本页，则选择“取消”')) {
			location.reload();
		}
		return false;
	}
}
function InfoText(id, info) {
	var $obj = $('#' + id);
	var input = false;
	this.val = function() {
		if (input) {
			return $obj.val();
		} else {
			return "";
		}
	};
	this.select = function() {
		$obj.select();
	};
	this.focus = function() {
		$obj.focus();
	};
	function echoInfo() {
		$obj.val(info).css('color', 'gray');
		input = false;
	}
	echoInfo();
	$obj.focus(function() {
		if (!input) {
			$obj.css("color", "black");
			$obj.val("");
		}
	}).blur(function() {
		if ($.trim($obj.val()) == '') {
			echoInfo();
		} else {
			input = true;
		}
	});
}
function rightStr(str, startStr) {
	var index = str.indexOf(startStr);
	if (index >= 0) {
		return str.substring(index + startStr.length);
	} else {
		return null;
	}
}
function numeric(num) {
	if (num == undefined || num == null) {
		return false;
	}
	var r = /^([-|+]?\d+(\.\d+)?)$/g;
	if (num.match(r)) {
		return true;
	} else {
		return false;
	}
}
function getCurrentDate() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	var day = date.getDate();
	return year + "-" + month + "-" + day;
}
function submitForm(formId, url, callback) {
	if (url == null) {
		url = $('#' + formId).attr('action');
	}
	if (callback == null) {
		callback = ajaxCallBack;
	}
	$('#' + formId).form('submit', {
		url : url,
		success : callback
	});
}
var CURTAIN = {
	$inputCurtain : null,
	$content : null
};
CURTAIN.hide = function() {
	if (CURTAIN.$content) {
		CURTAIN.$content.hide(250);
	}
	if (CURTAIN.$inputCurtain) {
		CURTAIN.$inputCurtain.hide(250);
	}
};
CURTAIN.show = function(id) {
	if (CURTAIN.$inputCurtain == null) {
		init();
	}

	CURTAIN.$content = $('#' + id);
	$obj = CURTAIN.$content;

	var zIndex = $obj.css('zIndex');
	if (zIndex == 'auto' || zIndex < 6) {
		$obj.css('zIndex', 6);
	}

	var bgColor = $obj.css('backgroundColor');
	if (bgColor == 'transparent' || bgColor == 'rgba(0, 0, 0, 0)') {
		$obj.css('backgroundColor', 'white');
	}

	if ($obj.css('position') == 'static') {
		$obj.css('position', 'absolute');
	}

	CURTAIN.$inputCurtain.show(250, function() {
		$obj.show(250);
	});
	function init() {
		CURTAIN.$inputCurtain = $('<div></div>').appendTo('body');
		CURTAIN.$inputCurtain.css({
			'position' : 'fixed',
			'left' : '0px',
			'top' : '0px',
			'height' : '2000px',
			'width' : '2000px',
			'opacity' : '0.5',
			'zIndex' : '5',
			'backgroundColor' : '#222222',
			'display' : 'none'
		});
	}
};