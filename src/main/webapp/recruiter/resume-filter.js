$(function() {
	var cookieParamName = 'resumeFilterDivCollapsed';
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
		var collapsed = $.cookie(cookieParamName);
		if (collapsed && collapsed == "true") {
			obj['collapsed'] = true;
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
	$('#clearFilterButton').aclick(function() {
		var obj = {};
		$('#filterDiv input[id]').each(function() {
			obj[this.id] = '';
		});
		$('#filterDiv select[id]').each(function() {
			obj[this.id] = 0;
		});
		obj['sessSearchName'] = '';
		obj['sessSearchTel'] = '';
		delete obj.recruiterId;
		$.post('/hrmis/sess/update', obj, function(data) {
			if (isAjaxData(data)) {
				data = parseAjaxData(data);
				if (data == '') {
					resetPageNumber();
				} else {
					alert(data);
				}
			}
		});
	}).attr("title", '清空“创建者”以外的筛选项');
	$('#filterDiv').change(function() {
		resetPageNumber();
	});
});
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