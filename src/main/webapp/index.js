$(document).ready(function() {
	persistStatus('recordChkBox');
	$('#loginForm').keyup(function(e) {
		if (e.keyCode == 13 && validate()) {
			login();
		}
	});
	$('#submitImage').click(function() {
		if (validate()) {
			login();
		}
	});

	$('#ident_image').click(changeIdentImage);
	function changeIdentImage(){
		$('#ident_image').get(0).src="/hrmis/inc/ident-image.jsp";
	}
	function login() {
		submitForm("loginForm", null, function(data) {
			if(isAjaxData(data)){
				data=parseAjaxData(data);
				if(data.match(/^success.*/g)){
					var href=data.substring("success".length);
					if(href==""){
						location="login.jsp";
					}else{
						location=href;
					}
				}else{
					alert(data);
					location.reload();
				}
			}
		});
	}
	function validate() {
		if ($('#hrmisUsername').val() == "") {
			alert("用户名不能为空");
			return false;
		} else {
			return true;
		}
	}
});