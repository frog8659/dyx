$(function() {
	$.validator.addMethod("tabAudt", function(value, element) {
		return value != "" && !/0/.test(value) && !/1/.test(value);
	}, "请先确保所有资料均已审核！");
	
	$("#ordForm").validate({
		ignore: ".info :hidden, .ignore",
		rules: {
			"metr.metrNo": "required",
			"metr.aplName": "required",
			"metr.aplIdc": "required",
			"metr.aplMob": "required",
			"metr.aplBank": "required",
			"metr.aplBankCard": "required",
			"metr.aplBankMob": "required",
			"metr.aplIdcExp": "required",
			"metr.aplAge": "required",
			"metr.tabAudt": "tabAudt"
		},
   		messages: {
			"metr.metrNo": {
				required: validateMessage("分期申请书编号", "input")
			},
			"metr.aplName": {
				required: validateMessage("姓名", "input")
			},
			"metr.aplIdc": {
				required: validateMessage("身份证号", "input")
			},
			"metr.aplMob": {
				required: validateMessage("手机号码", "input")
			},
			"metr.aplBank": {
				required: validateMessage("发卡银行", "select")
			},
			"metr.aplBankCard": {
				required: validateMessage("银行卡号", "input")
			},
			"metr.aplBankMob": {
				required: validateMessage("银行预留手机号", "input")
			},
			"metr.aplIdcExp": {
				required: validateMessage("身份证有效期", "select")
			},
			"metr.aplAge": {
				required: validateMessage("年龄", "input")
			}
   		}
	});
});