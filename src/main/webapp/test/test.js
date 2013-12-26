CKEDITOR.replace('editor', {
	height : 300,
	width : 900,
	on : {
		instanceReady : function() {
			console.log(this.name);
		}
	}
});