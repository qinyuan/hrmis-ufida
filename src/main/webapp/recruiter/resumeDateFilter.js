$(document).ready(function() {
	postAfterDateChange("resumeStartAddDate");
	postAfterDateChange("resumeStartMdfDate");
	postAfterDateChange("resumeEndAddDate");
	postAfterDateChange("resumeEndMdfDate");
	postAfterDateChange("startHandleDate");
	postAfterDateChange("endHandleDate");
	$('a').filter(function(){
		return this.id.match(/thisWeek.*/g) || 
			this.id.match(/today.*/g);
	}).click(function(e){
		e.preventDefault();
		var obj={},id=this.id;
		if(id.match(/thisWeek.*/g)){
			obj.thisWeek=id;
		}else if(id.match(/today.*/g)){
			obj.today=id;
		}
		$.post('/hrmis/sess/update',obj,ajaxCallBack);
	});
});