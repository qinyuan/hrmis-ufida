$(document).ready(function() {
	$('div.currentPosition span').text("主页");
	$('tr').each(function() {
		var height = 0;
		$(this).find('div').each(function() {
			if (height < $(this).height()) {
				height = $(this).height();
			}
		});
		$(this).find('div').each(function() {
			$(this).height(height);
		});
	});
});