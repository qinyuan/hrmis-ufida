var userTree, moveTree;
var getUserId = function() {
	return filterInt(userTree.lastCheckedId);
};
var hidePrivilege = function() {
	$('#priDiv').hide();
};
$(document).ready(function() {
	userTree = new Tree("userTreeDiv");
	moveTree = new Tree("moveDiv");
	userTree.onclick(function() {
		$('#moveButton').linkbutton('enable');
		var userId = getUserId();
		if (userId) {
			showPrivilege(getUserId());
		} else {
			hidePrivilege();
		}
	});
	$('#addSubmit').click(function() {
		submitForm("addForm");
	});
	$('a[id$="Cancel"]').click(CURTAIN.hide);
	$('#addButton').click(function() {
		CURTAIN.show('addDiv');
		var userId = getUserId();
		if (userId) {
			$('#superiorId').val(userId);
		}
	});
	$('#moveButton').click(function() {
		var checkedId = userTree.lastCheckedId;
		if (checkedId == null) {
			return;
		}
		var userId = filterInt(checkedId);
		$('#moveDiv *').show();
		$('#supUser' + userId).hide();
		CURTAIN.show('moveDiv');
	});
	$('#moveOk').click(function() {
		var subUserId = getUserId();
		var supUserId = filterInt(moveTree.lastCheckedId);
		if (supUserId == null) {
			supUserId = 0;
		}
		$.post('User.action', {
			subUserId : subUserId,
			supUserId : supUserId
		}, ajaxCallBack);
	});
	$('#moveCancel').click(CURTAIN.hide);
	$('#mdfButton').click(function(e) {
		e.preventDefault();
		var userId = getUserId();
		if (userId == null) {
			return;
		}
		$('#mdfId').val(userId);
		CURTAIN.show('mdfDiv');
	});
	$('#delButton').click(function(e) {
		e.preventDefault();
		var userId = getUserId();
		if (userId == null) {
			return;
		}
		if (!confirm("确定删除？"))
			return;
		$.post('User.action', {
			delId : userId
		}, ajaxCallBack);
	});
	$('#mdfOK').click(function() {
		submitForm("mdfForm");
	});

});
function showPrivilege(userId) {
	$('#priDiv').show();
	$.post("pri-panel.action", {
		"userId" : userId
	}, afterCheckBoxDisplay);
	function afterCheckBoxDisplay(data) {
		if (!isAjaxData(data))
			return;
		data = parseAjaxData(data);

		$('div#priContent').empty().append(data).find('input:checkbox').each(
				function() {
					if (!this.checked) {
						$(this).parent().css('color', 'gray');
					}
				}).click(function() {
			var priId = this.id.substring(3);
			$.post("mdf-pri.action", {
				'userId' : userId,
				'priId' : priId,
				'status' : this.checked
			}, function(data) {
				if (!isAjaxData(data))
					return;
				data = parseAjaxData(data);
				$('div#result').text(data);
			});

			if (this.checked) {
				$(this).parent().css('color', 'black');
			} else {
				$(this).parent().css('color', 'gray');
			}
		});
		var $h4 = $('#priContent').find('h4');
		var title = $h4.text();
		$h4.remove();
		$('#priDiv > h4').text(title);
	}
}