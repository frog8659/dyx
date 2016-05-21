$(function() {
	$("#instForm").validate({
		rules: {
			"inst.instName": "required"
		},
		messages: {
			"inst.instName": {
				required: validateMessage("分期名称", "input")
			}
		},
		invalidHandler: function(e, validator) {
			// 当前表单对象
			var form = validator.currentForm;
			// 校验结果准备完毕，触发调用错误处理程序
			if($.data(form, "invokeInvalidHandler")) {
				// 弹出错误信息
				window.top.dialogMessage("请先完善分期业务相关信息！");
			}
		}
	});
});