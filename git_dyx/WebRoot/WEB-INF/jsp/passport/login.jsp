<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c" %>
<%@ taglib uri="tools" prefix="t" %>

<c:if test="${not empty loginInfo.userId}">
	<script type="text/javascript">
		window.location = "${base}frame";
	</script>
</c:if>

<div class="loginHead"></div>

<div class="login">
	<form id="loginForm" action="${base}passport/login" method="post">
		<ul>
			<li>
				<span>用户名：</span><input type="text" name="userName" class="inp" value="${loginInfo.userName}" />
			</li>
			<li><span>密码：</span><input type="password" name="userPwd" class="inp" value="${loginInfo.userPwd}" /></li>
			<li><input type="button" name="button" value="登录" class="btn" onclick="login()" /></li>
			<li>忘记密码？请<a href="javascript:" onclick="resPwd();return false;">点击这里</a>重置密码！</li>
  		</ul>
	</form>
</div>

<div id="footer">CopyRight <span>&copy;</span> 2016-2019 All Rights Reserved 版权所有</div>

<script type="text/javascript">
	$(function() {
		$("body").css("background", "#1c59a1");
		
		<%-- 弹出消息 --%>
		<c:if test="${not empty message}">
			window.top.dialogMessage("${message}");
		</c:if>
		
		<%-- 初始化校验 --%>
		$("#loginForm").validate({
			rules: {
				"userName": "required",
				"userPwd": "required"
			},
			messages: {
				"userName": {
					required: validateMessage("用户名", "input")
				},
				"userPwd": {
					required: validateMessage("密码", "input")
				}
			}
		});
	});
	
	<%-- 系统登录 --%>
	function login() {
		<%-- 校验并提交表单 --%>
		var form = $("#loginForm");
		validateForm(form, function(valid) {
			if(valid) {
				form.submit();
			}
		});
	}
	
	<%-- 忘记密码 --%>
	function resPwd() {
		dialogIframe({
			id: "dialog-iframe-res-pwd",
			url: "passport/pwd/reset",
			height: 180,
			width: 250,
			titlebar: false,
			buttons: null
		});
	}
</script>
