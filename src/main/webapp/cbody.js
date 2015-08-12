$(document).ready(function() {
	$('div.navi li').hover(function(){
		$(this).find('div').show();
		$(this).find('span').css('background-color','deepskyblue');
	},function(){
		$(this).find('div').hide();
		if(!this.currentLi){
			$(this).find('span').css('background-color','transparent');
		}
	});

	var currentHref = location.href.replace('.jsp', '');
	var reg = new RegExp('([?].*)|([#].*)');
	currentHref = currentHref.replace(reg, '');

	$('div.currentNavi a').each(function() {
		if (currentHref == this.href.toString().replace('.jsp', '')) {
			$(this).parent().show();
			$(this).after('<span>'+$(this).text()+'</span>');
			$(this).remove();
		}
	});
	$('div.navi a').each(function(){
		if(currentHref==this.href.toString().replace('.jsp','')){
			var currentSpan=$(this).parent().prev();
			if(currentSpan.is('span')){
				currentSpan.css('background-color','deepskyblue');
				currentSpan.parent().get(0).currentLi=true;
				$('div.currentPosition span').text(currentSpan.text()+">>"+$(this).text());
			}
		}
	});
});