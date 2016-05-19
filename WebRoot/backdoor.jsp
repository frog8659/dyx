<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="sh.ricky.core.constant.GlobalConstants" %>
<%@ page import="sh.ricky.core.constant.ConfigConstants" %>
<%@ page import="sh.ricky.dyx.constant.UserConstants"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="tools" prefix="t" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	// 服务器地址
	String host = request.getServerName();
	// 传送协议
	String schema = ConfigConstants.getInstance().get("scheme.default");
	schema = StringUtils.isBlank(schema) ? request.getScheme() : schema;
	schema = ConfigConstants.getInstance().get("scheme.special.http").contains(host) ? "http" : schema;
	schema = ConfigConstants.getInstance().get("scheme.special.https").contains(host) ? "https" : schema;
	// 服务器端口
	String port = ConfigConstants.getInstance().get("port.default");
	port = StringUtils.isBlank(port) ? String.valueOf(request.getServerPort()) : port;
	port = ConfigConstants.getInstance().get("port.special.empty").contains(host) ? "" : port;
	String ports = ConfigConstants.getInstance().get("port.special.list");
	if (StringUtils.isNotBlank(ports)) {
	    for (String p : ports.split(",")) {
	        String hosts = ConfigConstants.getInstance().get("port.special." + p);
	        port = StringUtils.isNotBlank(hosts) && hosts.contains(host) ? p : port;
	    }
	}
	port = StringUtils.equals("80", port) ? "" : ":" + port;
	// 资源路径
	String path = request.getContextPath();
	
	// 设置URL请求根路径
	String base = schema + "://" + host + port + path + "/";

	boolean debug = GlobalConstants.YES_VALUE.equals(ConfigConstants.getInstance().get("debug.on"));
%>
<head>
	<title>测试登录</title>
	<base href="<%=base%>" />
	<link type="text/css" rel="stylesheet" href="js/ui/skin/smoothness/jquery-ui.css?t=${t:config('token.css')}" />
	<link type="text/css" rel="stylesheet" href="css/base.css?t=${t:config('token.css')}" />
	<script type="text/javascript" src="js/jquery.min.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/jquery.bgiframe.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/ui/jquery-ui.min.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/common.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/validate/validate.common.js?t=${t:config('token.script')}"></script>
	<style type="text/css">
    	body, html { overflow:auto; height:100%; }
    	.main { margin:10% auto; width:600px; line-height:200%; padding:15px; }
    	.btn-bar { margin:0 auto; padding:10px; text-align:center; }
    </style>
    <!--[if lt IE 10]>
   	<style type="text/css">
   		.btn-01 { behavior:url(js/PIE.htc); }
	</style>
   	<![endif]-->
</head>

<body>
	<%
		// 如果未开启debug模式，则禁用测试登录
		if(debug) {
	%>
	<div class="main">
		<form id="backdoorForm" action="<%=base%>passport/backdoor">
			<table cellspacing="0" cellpadding="0" class="info">
				<col width="20%" /><col width="80%" />
				<tr>
					<th colspan="2" class="title">测试登录</th>
				</tr>
				<tr>
					<th>登录用户类型：</th>
					<td>
						<select name="userRole" id="userRole" class="select" onchange="toggleType(this)">
							<option value="<%=UserConstants.USER_ROLE_CJGLY%>">超级管理员</option>
							<option value="<%=UserConstants.USER_ROLE_KFZY%>">客服专员</option>
							<option value="<%=UserConstants.USER_ROLE_KFZG%>">客服主管</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>测试用户：</th>
					<td>
						<div name="users" rel="<%=UserConstants.USER_ROLE_CJGLY%>">
							<select onchange="toggleUser(this)" class="select">
								<option value="">--</option>
								<option value="1">张三</option>
							</select>
						</div>
						<div name="users" rel="<%=UserConstants.USER_ROLE_KFZY%>">
							<select onchange="toggleUser(this)" class="select">
								<option value="">--</option>
								<option value="1">李四</option>
							</select>
						</div>
						<div name="users" rel="<%=UserConstants.USER_ROLE_KFZG%>">
							<select onchange="toggleUser(this)" class="select">
								<option value="">--</option>
								<option value="1">王五</option>
							</select>
						</div>
					</td>
				</tr>
				<tr name="test-info" rel="<%=UserConstants.USER_ROLE_CJGLY%>|<%=UserConstants.USER_ROLE_KFZY%>|<%=UserConstants.USER_ROLE_KFZG%>%>">
					<th>用户姓名：</th>
					<td><input type="text" name="userName" id="personName" size="40" class="input" /></td>
				</tr>
			</table>
		</form>
		
		<div class="btn-bar">
			<input type="button" class="btn-01" value="登录" onclick="login()" />
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			// 初始化页面
			toggleType("#userRole");
			
			// 初始化校验
			$("#backdoorForm").validate({
				// 提交时不自动校验
				onsubmit: false,
				//为控件添加验证方法
				rules: {
					"userName": "needed"
				},
				// 设置校验信息
				messages: {
					"userName": {
						needed: function() {
							var type = $("#userRole").find("option:selected").val();
							var name = "";
							if(type == "<%=UserConstants.USER_ROLE_CJGLY%>" || type == "<%=UserConstants.USER_ROLE_KFZY%>" || type == "<%=UserConstants.USER_ROLE_KFZG%>") {
								name = "用户姓名";
							}
							return "请输入" + name + "！";
						}
					}
				},
				// 设置校验信息显示形式
		 		showErrors: function(errorMap, errorList) {
					// 执行默认操作
					this.defaultShowErrors();
					
					// 自定义校验信息显示形式
					_showErrors(errorMap, errorList);
		 		},
		 		// 设置有出错信息时的操作
		 		invalidHandler: function(e, validator) {
					// 自定义出错反馈
					_invalidHandler(e, validator);
		 		}
			});
		});
		
		// 切换用户类型
		function toggleType(expr) {
			var type = $(expr).find("option:selected").val();
			$("[name=users][rel!=" + type + "]").hide().find(":input").val("").attr("disabled", "disabled");
			$("[name=users][rel=" + type + "]").show().find(":input").removeAttr("disabled");
			toggleTR($("[name=test-info]").not("[rel*=" + type + "]"), "hide").find(":input").val("").attr("disabled", "disabled");
			toggleTR($("[name=test-info]").filter("[rel*=" + type + "]"), "show").find(":input").removeAttr("disabled");
		}
		
		// 切换测试用户
		function toggleUser(expr) {
			var selected = $(expr).find("option:selected");
			var type = selected.parents("[name=users]").attr("rel");
			
			if("<%=UserConstants.USER_ROLE_CJGLY%>|<%=UserConstants.USER_ROLE_KFZY%>|<%=UserConstants.USER_ROLE_KFZG%>".indexOf(type) > -1) {
				var personName = selected.val() == "" ? "" : selected.text();
				$("#personName").val(personName);
			}
		}
		
		// 后门登录
		function login() {
			// 校验并提交表单
			var form = $("#backdoorForm");
			validateForm(form, function(valid) {
				if(valid) {
					form.submit();
				}
			});
		}
	</script>
	<%	} %>
</body>
</html>