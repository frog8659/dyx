$(function() {
	$.validator.addMethod("tabAudt", function(value, element) {
		return value != "" && !/0/.test(value) && !/1/.test(value);
	}, "请先确保所有资料均已审核！");
	
	$.validator.addMethod("bankCard", function(value, element) {
		return value != "" && !/^\d{16}|\d{19}$/.test(value);
	}, "银行卡号必须为16或19位数字！");
	

	$("#ordForm").validate({
		ignore: ".ignore",
		rules: {
			"metr.metrNo": "required",
			"metr.aplName": "required",
			"metr.aplIdc": {
				required: true,
				idcard: true
			},
			"metr.aplMob": {
				required: true,
				mobile: true
			},
			"metr.aplBank": "required",
			"metr.aplBankCard": {
				required: true,
				bankCard: true
			},
			"metr.aplBankMob": {
				required: true,
				mobile: true
			},
			"metr.aplIdcExp": "required",
			"metr.aplAge": {
				required: true,
				digits: true,
				range: [18, 65]
			},
			"metr.aplSex": "required",
			"metr.aplEdu": "required",
			"metr.aplMari": "required",
			"metr.aplProv": "required",
			"metr.aplCity": "required",
			"metr.aplDist": "required",
			"metr.aplAddr": "required",
			"metr.aplHomeStat": "required",
			"metr.aplResdPeriod": {
				required: true,
				digits: true
			},
			"metr.aplMail": {
				required: true,
				email: true
			},
			"metr.aplEmpType": "required",
			"metr.aplCompany": "required",
			"metr.aplCompNatu": "required",
			"metr.aplJobTitle": "required",
			"metr.aplJobIncome": {
				required: true,
				digits: true
			},
			"metr.aplJobYear": {
				required: true,
				digits: true
			},
			"metr.aplCompTel": "required",
			"metr.aplCompAddr": "required",
			"metr.aplJobSpec": "required",
			"metr.aplCompSize": "required",
			/** 根据最新需求，后台不再支持附件上传
			"attach[2].atchId": "required",
			"attach[3].atchId": "required",
			"attach[4].atchId": "required",
			"attach[5].atchId": "required",*/
			"validEval": {
				required: function() {
					return $("[name^='eval['][name$='].evalCont']").filter("[value='']").size() > 0;
				}
			},
			"validContact": {
				required: function() {
					return $("[name^='contact['][name$='].name']").size() < 5;
				}
			},
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
				required: validateMessage("身份证号", "input"),
				idcard: validateMessage("身份证号", "format")
			},
			"metr.aplMob": {
				required: validateMessage("手机号码", "input"),
				mobile: validateMessage("手机号码", "format")
			},
			"metr.aplBank": {
				required: validateMessage("发卡银行", "select")
			},
			"metr.aplBankCard": {
				required: validateMessage("银行卡号", "input")
			},
			"metr.aplBankMob": {
				required: validateMessage("银行预留手机号", "input"),
				mobile: validateMessage("银行预留手机号", "format")
			},
			"metr.aplIdcExp": {
				required: validateMessage("身份证有效期", "select")
			},
			"metr.aplAge": {
				required: validateMessage("年龄", "input"),
				digits: validateMessage("年龄", "number"),
				range: "“年龄”必须在18岁至65岁之间！"
			},
			"metr.aplSex": {
				required: validateMessage("性别", "select")
			},
			"metr.aplEdu": {
				required: validateMessage("教育程度", "select")
			},
			"metr.aplMari": {
				required: validateMessage("婚姻状态", "select")
			},
			"metr.aplProv": {
				required: validateMessage("现居住地址所在省", "select")
			},
			"metr.aplCity": {
				required: validateMessage("现居住地址所在市", "select")
			},
			"metr.aplDist": {
				required: validateMessage("现居住地址所在县区", "select")
			},
			"metr.aplAddr": {
				required: validateMessage("现居住地址", "input")
			},
			"metr.aplHomeStat": {
				required: validateMessage("现居住宅状况", "select")
			},
			"metr.aplResdPeriod": {
				required: validateMessage("本地居住年限", "input"),
				digits: validateMessage("本地居住年限", "number")
			},
			"metr.aplMail": {
				required: validateMessage("电子邮箱", "input"),
				email: validateMessage("电子邮箱", "format")
			},
			"metr.aplEmpType": {
				required: validateMessage("受雇类型", "select")
			},
			"metr.aplCompany": {
				required: validateMessage("现单位名称", "input")
			},
			"metr.aplCompNatu": {
				required: validateMessage("单位性质", "select")
			},
			"metr.aplJobTitle": {
				required: validateMessage("职务级别", "select")
			},
			"metr.aplJobIncome": {
				required: validateMessage("税后月收入", "input"),
				digits: validateMessage("税后月收入", "integer")
			},
			"metr.aplJobYear": {
				required: validateMessage("现单位工作年限", "input"),
				digits: validateMessage("现单位工作年限", "number")
			},
			"metr.aplCompTel": {
				required: validateMessage("单位电话", "input")
			},
			"metr.aplCompAddr": {
				required: validateMessage("单位地址", "input")
			},
			"metr.aplJobSpec": {
				required: validateMessage("工作性质", "select")
			},
			"metr.aplCompSize": {
				required: validateMessage("单位规模", "select")
			},
			/** 根据最新需求，后台不再支持附件上传
			"attach[2].atchId": {
				required: "请拍摄“身份证正面”照片并上传！"
			},
			"attach[3].atchId": {
				required: "请拍摄“身份证反面”照片并上传！"
			},
			"attach[4].atchId": {
				required: "请拍摄“储蓄卡正面”照片并上传！"
			},
			"attach[5].atchId": {
				required: "请拍摄“手持身份证照”并上传！"
			}*/
			"validEval": {
				required: "请完成所有分期订单评价！"
			},
			"validContact": {
				required: "请至少添加5名紧急联系人！"
			}
   		}
	});
});