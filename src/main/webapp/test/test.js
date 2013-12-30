var testVar;
function test() {
	console.log(testVar);
}
$(function() {
	testVar = "testVal";
	test();
});