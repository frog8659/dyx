$(function() {
	$.validator.addMethod("tabAudt", function(value, element) {
		return value != "" && !/0/.test(value) && !/1/.test(value);
	}, "请先确保所有资料均已审核！");
	
	$("#ordForm").validate({
		rules: {
			"metr.tabAudt": "tabAudt"
		},
   		messages: {
   		}
	});
});