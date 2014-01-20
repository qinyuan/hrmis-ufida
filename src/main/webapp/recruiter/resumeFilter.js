$(function() {
	initFilterDiv();
	initDateInput();
	initSelectAction();
	$('#clearFilterButton').aclick(clearFilterAction).attr("title",
			'清空“创建者”以外的筛选项');
});
var clearFilterAction = function() {
	$filterDiv = getFilterDiv();
	var obj = {};
	$filterDiv.find('input[id]').each(function() {
		obj[this.id] = '';
	});
	$filterDiv.find('select[id]').each(function() {
		obj[this.id] = 0;
	});
	obj['searchName'] = '';
	obj['searchTel'] = '';
	delete obj.recruiterId;
	obj.pageNumber = 1;
	filterPost.post(obj);
};
var filterDivCookieParamName = 'resumeFilterDivCollapsed';
var filterPost = new FilterPost().setHref("ResumeFilter.action");
var getFilterDiv = function() {
	return $('#filterDiv');
};
var initSelectAction = function() {
	getFilterDiv().find('select').change(function() {
		var obj = {};
		obj[this.id] = $(this).val();
		filterPost.post(obj);
	});
};
var initDateInput = function() {
	getFilterDiv().find('input:text[id$="Date"]').click(function() {
		var id = this.id;
		var obj = {};
		WdatePicker({
			onpicked : function() {
				obj[id] = $dp.cal.getDateStr();
				filterPost.post(obj);
			},
			oncleared : function() {
				obj[id] = '';
				filterPost.post(obj);
			}
		});
	});
	getFilterDiv().find('a[id$="Date"]').aclick(function() {
		var obj = {};
		if (this.id.indexOf('today') >= 0) {
			obj.today = this.id;
		} else {
			obj.thisWeek = this.id;
		}
		filterPost.post(obj);
	});
};
var initFilterDiv = function() {
	$filterDiv = getFilterDiv();
	if ($filterDiv.size() == 1) {
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
		var collapsed = $.cookie(filterDivCookieParamName);
		if (collapsed && collapsed == "true") {
			obj['collapsed'] = true;
		}
		$filterDiv.panel(obj);
		$filterDiv.css({
			"padding-top" : "10px",
			"padding-bottom" : "10px",
			"height" : "110px"
		});
	}
};
function filterDivToggle(collapse) {
	$.cookie(filterDivCookieParamName, collapse);
}
var resetPageNumber = function() {
	var oldHref = location.href.toString();
	var newHref = new Href().addParam({
		pageNumber : 1
	}).toString();
	if (oldHref == newHref) {
		location.reload();
	} else {
		location.href = newHref;
	}
};
function doSearchName(value) {
	$.post('ResumeFilter.action', {
		'searchName' : value
	}, ajaxCallBack);
}
function doSearchTel(value) {
	$.post('ResumeFilter.action', {
		'searchTel' : value
	}, ajaxCallBack);
}