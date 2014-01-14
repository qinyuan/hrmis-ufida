var tree, clickedSpan, supTree;
var initCustomerTree = function() {
	tree = new Tree("leftDiv");
	tree.getCusId = function() {
		var lastCheckedId = tree.lastCheckedId;
		return lastCheckedId ? lastCheckedId.substring(3) : null;
	};
	tree.onclick(function() {
		var cusId = tree.getCusId();
		if (cusId) {
			loadDemands(cusId);
		} else {
			unloadDemands();
		}
		$('#remarkDemand').linkbutton('disable');
	});
	supTree = new Tree('moveCustomerDiv');
};

function addEventToDemandList(checkedSpanId) {
	clickedSpan = new ClickedSpan($("#demandsDiv span"));
	clickedSpan.getDemandId = function() {
		var checkedSpan = clickedSpan.checkedSpan;
		return checkedSpan ? filterInt(checkedSpan.id) : null;
	};
	clickedSpan.select(function() {
		loadDetail(clickedSpan.getDemandId());
	}).unselect(function() {
		unloadDetail();
	});
	if (checkedSpanId) {
		clickedSpan.setSelect($('#' + checkedSpanId).get(0));
		$('#remarkDemand').linkbutton('enable');
	} else {
		$('#remarkDemand').linkbutton('disable');
		unloadDetail();
	}
	$('img[id^="deRemark"]').css('cursor', 'pointer').click(function(event) {
		var demandId = this.id.replace('deRemark', '');
		window.open('demand-remark.jsp?demandId=' + demandId);
		event.stopPropagation();
	});
}
function activeCustomerButton(bool) {
	var status = bool ? 'enable' : 'disable';
	$('#addDemand').linkbutton(status);
	$('#remarkCustomer').linkbutton(status);
	$('#delCustomer').linkbutton(status);
	$('#mdfCustomer').linkbutton(status);
	$('#moveCustomer').linkbutton(status);
}
function loadDemands(cusId, checkedSpanId) {
	activeCustomerButton(true);
	if (cusId == null)
		return;
	$.post('demand.action', {
		'getDemandByCusId' : cusId
	}, function(data) {
		if (!isAjaxData(data))
			return;
		data = parseAjaxData(data);
		$('#demandsDiv').html(data);
		addEventToDemandList(checkedSpanId);
	});
}
function unloadDemands() {
	$('#demandsDiv').empty();
	activeCustomerButton(false);
	unloadDetail();
}
function loadDetail(demandId) {
	$.post("demand.action", {
		"getDemandDetailByDemandId" : demandId
	}, function(data) {
		if (!isAjaxData(data))
			return;
		data = parseAjaxData(data);
		$('#detailDiv').html(data);
		decorateTable($('#detailDiv'));
		activeDemandButtons(true);
	});
}
function activeDemandButtons(bool) {
	var status = bool ? 'enable' : 'disable';
	$.each([ 'delDemand', 'remarkDemand', 'mdfDemand', 'openDemand',
			'pauseDemand', 'closeDemand' ], function() {
		$('#' + this).linkbutton(status);
	});
	$('#mdfDiv').hide();
}
function unloadDetail() {
	$('#detailDiv').empty();
	$('#mdfDiv').hide();
	activeDemandButtons(false);
}
function openCustomerRemark(cusId) {
	if (cusId)
		window.open('customer-remark.jsp?cusId=' + cusId);
}
function openRemarkDemand(demandId) {
	if (demandId)
		window.open('demand-remark.jsp?demandId=' + demandId);
}
function displayAddForm() {
	if (tree.getCusId()) {
		CURTAIN.show("inputDiv");
		$('#n_cusId').val(tree.getCusId());
		$('#n_name').focus();
		$('#n_startDate').val(getCurrentDate());
	}
}
function deleteDemandAction() {
	if (!clickedSpan)
		return;
	var demandId = clickedSpan.getDemandId();
	if (!demandId)
		return;
	if (!confirm("确定删除？"))
		return;

	$.post('demand.action', {
		'delDemandId' : demandId
	}, function(data) {
		if (isAjaxData(data)) {
			data = parseAjaxData(data);
			if (data == "success") {
				$(clickedSpan.checkedSpan).remove();
				unloadDetail();
			} else {
				alert(data);
			}
		}
	});
}
function displayDemandMdfDiv() {
	if (clickedSpan && clickedSpan.getDemandId()) {
		$.post('GetDemandMdfTable.action', {
			'demandId' : clickedSpan.getDemandId()
		}, function(data) {
			if (!isAjaxData(data)) {
				return;
			}
			data = parseAjaxData(data);
			$('#detailDiv').html(data);
			$('#mdfDemand').linkbutton('disable');
			$('#mdfDiv').show();
		});
	}
}
function modifyDemandAction() {
	submitForm("mdfForm", null, function(data) {
		if (isAjaxData(data)) {
			loadDemands(tree.getCusId(), clickedSpan.checkedSpan.id);
		}
	});
}
function addDemandAction() {
	var $name = $('#n_name');
	if ($.trim($name.val()) == '') {
		alert("需求名称未填！");
		$name.focus();
		return;
	}
	var $postNumber = $('#n_postNumber');
	var postNumber = $.trim($postNumber.val());
	if (postNumber != "" && !numeric(postNumber)) {
		alert("需求人数只能填数字！");
		$postNumber.select();
		return;
	}

	submitForm("addForm", null, function(data) {
		if (isAjaxData(data)) {
			data = parseAjaxData(data);
			if (data == "success") {
				loadDemands(tree.getCusId());
				CURTAIN.hide();
			} else {
				alert(data);
			}
		}
	});
}
function getCustomerNameInput(defaultValue) {
	if (defaultValue == undefined) {
		defaultValue = '';
	}
	var customerName = prompt("输入客户名称", defaultValue);
	if (customerName != null) {
		if ($.trim(customerName) == "") {
			alert("用户名不能为空");
		} else {
			return customerName;
		}
	}
	return null;
}
function addCustomerAction() {
	var cusName = getCustomerNameInput();
	if (cusName) {
		var cusId = tree.getCusId();
		if (cusId) {
			$.post('Customer.action', {
				'supCusId' : cusId,
				'subCusName' : cusName
			}, ajaxCallBack);
		} else {
			$.post('Customer.action', {
				'newDepenCusName' : cusName
			}, ajaxCallBack);
		}
	}
}
function deleteCustomerAction() {
	var cusId = tree.getCusId();
	if (cusId && confirm("确定删除？")) {
		$.post('Customer.action', {
			'delCusId' : cusId
		}, ajaxCallBack);
	}
}
function modifyCustomerAction() {
	var cusId = tree.getCusId();
	if (cusId) {
		var oldName = $('#' + tree.lastCheckedId).text();
		var newName = getCustomerNameInput(oldName);
		if (newName) {
			$.post('Customer.action', {
				'mdfCustomerId' : cusId,
				'mdfCustomerName' : newName
			}, ajaxCallBack);
		}
	}
}
function displayCustomerMoveDiv() {
	if (tree.getCusId()) {
		$('#moveCustomerDiv *').show();
		$('#supCus' + tree.getCusId()).hide();
		CURTAIN.show('moveCustomerDiv');
	}
}
function moveCustomerAction() {
	var subCusId = tree.getCusId();
	if (subCusId == null) {
		return;
	}
	var checkedId = supTree.lastCheckedId;
	if (checkedId == null) {
		$.post('Customer.action', {
			'depenCusId' : subCusId
		}, ajaxCallBack);
		console.log(subCusId);
	} else {
		var supCusId = checkedId.substring(6);
		$.post('Customer.action', {
			'subCusId' : subCusId,
			'supCusId' : supCusId
		}, ajaxCallBack);
	}
}
function setDemandStatusAction(active, pause) {
	if (clickedSpan && clickedSpan.getDemandId()) {
		$.post('Demand.action', {
			'changeDemandStatus' : 'true',
			'active' : active,
			'pause' : pause,
			'demandId' : clickedSpan.getDemandId()
		}, function(data) {
			if (isAjaxData(data)) {
				loadDemands(tree.getCusId(), clickedSpan.checkedSpan.id);
			}
		});
	}
}
$(function() {
	initCustomerTree();
	$('a[id^="add"]').attr('title', '添加');
	$('a[id^="mdf"]').attr('title', '修改');
	$('a[id^="del"]').attr('title', '删除');
	$('img[id^="cusRemark"]').click(function(e) {
		openCustomerRemark(this.id.replace(/\D/g, ''));
		e.stopPropagation();
	}).css('backgroundColor', 'white');
	$('#remarkDemand').click(function() {
		if (clickedSpan && clickedSpan.getDemandId())
			openRemarkDemand(clickedSpan.getDemandId());
	});
	$('#remarkCustomer').click(function() {
		openCustomerRemark(tree.getCusId());
	});
	$('#addDemand').aclick(displayAddForm);
	$('#delDemand').aclick(deleteDemandAction);
	$('#mdfDemand').aclick(displayDemandMdfDiv);
	$('#mdfCancel').aclick(function() {
		$('#mdfDiv').hide();
		loadDetail(clickedSpan.getDemandId());
	});
	$('#mdfOK').aclick(modifyDemandAction);
	$('#addCancel').aclick(CURTAIN.hide);
	$('#addOK').aclick(addDemandAction);
	$('#addCustomer').aclick(addCustomerAction);
	$('#delCustomer').aclick(deleteCustomerAction);
	$('#mdfCustomer').aclick(modifyCustomerAction);
	$('#moveCustomer').aclick(displayCustomerMoveDiv);
	$('#moveCancel').aclick(CURTAIN.hide);
	$('#moveOK').aclick(moveCustomerAction);
	$('#openDemand').aclick(function() {
		setDemandStatusAction(true, false);
	});
	$('#closeDemand').aclick(function() {
		setDemandStatusAction(false, false);
	});
	$('#pauseDemand').aclick(function() {
		setDemandStatusAction(true, true);
	});
});