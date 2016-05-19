$(function() {
	$.validator.addMethod("pwd", function(value, element) {
		var a = /\d/.test(value) ? 1 : 0;
		var b = /[a-zA-Z]/.test(value) ? 1 : 0;
		var c = /\W/.test(value) ? 1 : 0;
		return value != "" && (a + b + c) > 1;
	}, "“新密码”中数字、字母和符号至少包含两种！");
	
	$("#form").validate({
		rules: {
			"oriPwd": "required",
			"newPwd": {
				required: true,
				minlength: 6,
				maxlength: 16,
				pwd: true
			},
			"newPwd2": {
				equalTo: "[name=newPwd]",
			},
			"captcha": "required",
		},
   		messages: {
   			"oriPwd": {
   				required: validateMessage("原密码", "input")
   			},
   			"newPwd": {
   				required: validateMessage("新密码", "input"),
   				minlength: validateMessage(["新密码", "6位"], "minlength"),
   				maxlength: validateMessage(["新密码", "16位"], "maxlength")
   			},
   			"newPwd2": {
   				equalTo: "新密码两次输入不一致！"
   			},
   			"captcha": {
   				required: validateMessage("验证码", "input")
   			}
   		}
	});
});