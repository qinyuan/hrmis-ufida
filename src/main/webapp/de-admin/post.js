$(document).ready(function() {
	decorateImages('table');
	var addText=new InfoText("postName", "在些输入新职位名称...");
	$('#addButton').click(function() {
		var postName=addText.val();
		if (postName == "") {
			alert("职位名称不能为空");
			addText.focus();
		} else {
			$.post('post.action', {
				'postName' : postName
			}, ajaxCallBack);
		}
	});
	function initPostName() {
		$('#postName').val('在此输入新职位...').css('color', 'gray');
		postNameUpdate = false;
	}
	initPostName();
	$('#postName').click(function() {
		if (postNameUpdate) {
			return;
		} else {
			postNameUpdate = true;
			this.style.color = "black";
			this.value = "";
		}
	}).blur(function() {
		if ($.trim($('#postName').val()) == '') {
			initPostName();
		}
	});
	$('table img[id]').click(function() {
		var postId = this.id.substring(3);
		if (this.id.match(/mdf.*/)) {
			var oldPostName = $(this).parent().parent().children('td:eq(0)').text();
			var postName = prompt("请输入新的职位名称：", oldPostName);
			if (postName != null) {
				postName = $.trim(postName);
				if (postName == "") {
					alert("职位名不能为空");
				} else {
					$.post("post.action", {
						"mdfId" : postId,
						"newName" : postName
					}, ajaxCallBack);
				}
			}
		} else if (this.id.match(/del.*/)) {
			$.post("post.action", {
				"delId" : postId
			}, ajaxCallBack);
		}
	});
});