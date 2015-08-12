$(document).ready(function() {
	$('#mdfSubmit').click(function(e) {
		var password1 = $('#password1').val();
		var password2 = $('#password2').val();
		if (password1 != password2) {
			alert("两次输入的密码不相等");
			e.preventDefault();
		}else if(password1==""){
			alert("密码不能为空");
			e.preventDefault();
		}
	});
});