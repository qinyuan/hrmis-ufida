function InputCurtain() {
	$("<div class='inputCurtain'></div>").appendTo('body');
	this.curtain = $('.inputCurtain').css({
		'position' : 'fixed',
		'left' : '0px',
		'top' : '0px',
		'height' : '2000px',
		'width' : '2000px',
		'opacity' : '0.5',
		'zIndex' : '5',
		'backgroundColor' : '#222222',
		'display' : 'none'
	});
	this.elements = new Array();
}
InputCurtain.prototype.show = function() {
	this.curtain.show(250);
	for ( var index in this.elements) {
		this.elements[index].show(250);
	}
};
InputCurtain.prototype.hide = function() {
	this.curtain.hide(250);
	for ( var index in this.elements) {
		this.elements[index].hide(250);
	}
};
InputCurtain.prototype.add = function(id) {
	this.elements.push($('#' + id).css({
		'zIndex' : 6,
		'display' : 'none',
		'backgroundColor' : 'white',
		'padding' : '10px',
		'border-radius' : '10px'
	}));
	return this;
};