autoDecorateTable = false;
$(document).ready(function() {
	var $table = $('div.main table:eq(0)');
	decorateTable($table, false);
	decorateImages($table);

	$table.find('img[data-options]').click(function() {
		var img = this;
		var jTr = $(this).parent().parent();
		if (img.src.indexOf('down') > 0) {
			img.src = img.src.replace('down', 'up');
			img.title = img.title.replace('展开', '隐藏');
			img.alt = img.alt.replace('展开', '隐藏');
			var parameters=createObjectByStr('{'+$(img).attr('data-options') + '}');
			loadResumeSum(parameters, jTr);
		} else {
			img.src = img.src.replace('up', 'down');
			img.title = img.title.replace('隐藏', '展开');
			img.alt = img.alt.replace('隐藏', '展开');
			switchDetailRows(jTr, 'none');
		}
	});
	setQueryButton($table.find('a[data-options]'));

	function getTableRowByImgId(id) {
		return $('#' + id).parent().parent();
	}

	function switchDetailRows(jTr, display) {
		jTr = jTr.next();
		while (jTr.attr('class')) {
			jTr.css('display', display);
			jTr = jTr.next();
		}
	}

	function loadResumeSum(parameters, jTr) {
		$.post('statistics.action', parameters, function(data) {
			if (isAjaxData(data)) {
				data = parseAjaxData(data);
				var $data=$(data);
				setQueryButton($data.find('a[data-options]'));
				$data.insertAfter(jTr);
			}
		});
	}

	$('#startDate').click(WdatePicker);
	$('#endDate').click(WdatePicker);
	$('#queryButton').click(function(e) {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		if (!startDate) {
			alert("开始日期未设置");
		} else if (!endDate) {
			alert("结束日期未设置");
		} else if (startDate > endDate) {
			alert("开始日期早于结束日期");
		} else {
			$.post('statistics.action', {
				'startDate' : startDate,
				'endDate' : endDate
			}, function(data) {
				if (isAjaxData(data)) {
					data = parseAjaxData(data);
					if (data != "") {
						var periodTable = $('#periodTable');
						periodTable.html(data);
						decorateTable(periodTable);
					}
				}
			});
		}
		e.preventDefault();
	});
	$('#closeHiddenDivButton').click(function(){
		CURTAIN.hide();
	});
});

function setQueryButton($anchor){
	$anchor.linkbutton().click(function(e){
		var dataOptions=createObjectByStr('{'+$(this).attr('data-options')+'}');
		dataOptions.recruiterId=$('#recruiterId').val();
		dataOptions.postId=$('#postId').val();
		
		$.post('line-chart.action',dataOptions,function(data){
			if(isAjaxData(data)){
				data=parseAjaxData(data);
				if(data.charAt(0)=="{"){
					var dataObj=createObjectByStr(data);
					$('#contentDiv').highcharts(dataObj);
					CURTAIN.show("hiddenDiv");
				}else if(data.match(/^<table>.*<\/table>$/g)){
					$('#contentDiv').html(data);
					decorateTable($('#contentDiv'),false);
					CURTAIN.show("hiddenDiv");
				}else if(data!=""){
					alert(data);
				}
			}
		});
		e.preventDefault();
	});
}

$(document).ready(function() {
	$('#dayChartButton').click(function(e){
		postQuery('day');
		e.preventDefault();
	});
	$('#dayTableButton').click(function(e){
		postQuery('dayTable');
		e.preventDefault();
	});
	$('#weekChartButton').click(function(e){
		postQuery('week');
		e.preventDefault();
	});
	$('#weekTableButton').click(function(e){
		postQuery('weekTable');
		e.preventDefault();
	});
	function postQuery(style){
		if(!checkData())
			return;
		var obj=getQueryObj();
		if(obj!=null){
			obj.style=style;
			$.post('line-chart.action',obj,function(data){
				if(isAjaxData(data)){
					data=parseAjaxData(data);
					if(data.charAt(0)=="{"){
						eval("$('#"+style+"Div').highcharts("+data+")");
					}else if(data.match(/^<table>.*<\/table>$/g)){
						var jDiv=$('#'+style.replace('Table','')+'Div');
						jDiv.html(data);
						decorateTable(jDiv);
					}else if(data!=""){
						alert(data);
					}
				}
			});
		}
	}
	function checkData(){
		if($.trim(getStartDate())==""){
			alert('开始日期未设置');
			return false;
		}else if($.trim(getEndDate())==""){
			alert('结束日期未设置');
			return false;
		}else{
			return true;
		}
	}
	function getQueryObj(){
		var obj={};
		obj.recruiterId=$('#recruiterId').val();
		obj.postId=$('#postId').val();
		obj.startDate=getStartDate();
		obj.endDate=getEndDate();
		return obj;
	}
});