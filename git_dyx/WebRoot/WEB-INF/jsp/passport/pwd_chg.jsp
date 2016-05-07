<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c" %>
<%@ taglib uri="tools" prefix="t"%>

<script type="text/javascript" src="js/dyx/validate.pwd.chg.js?t=${t:config('token.script')}"></script>

<form id="form" action="${base}passport/pwd/change" method="post">
	<ul class="changepw">
		<li>
			<label>原密码：</label>
			<input type="password" name="oriPwd" class="inp" />
		</li>
		<li>
			<label>新密码：</label>
			<input type="password" name="newPwd" class="inp" />
		</li>
		<li>
			<label>重复新密码：</label>
			<input type="password" name="newPwd2" class="inp" />
			<p>密码长度6-16位，其中数字、字母和符号至少包含两种</p>
		</li>
		<li>
			<label>验证码：</label>
			<input type="text" name="captcha" class="inp inp100" />
			<img src="captcha" onclick="this.src='captcha?t='+(new Date()).getTime()" align="absmiddle"/>
		</li>
   		<li style="margin-top:20px;">
		    <input type="button" value="确定" class="btn" onclick="finish()" />
		</li>
	</ul>
</form>

<script type="text/javascript">
	$(function() {
		<%-- 后台校验信息 --%>
		<c:if test="${not empty msg}">
			window.top.dialogMessage("${msg}");
		</c:if>
	});
	
	<%-- 密码修改 --%>
	function finish() {
		var form = $("#form");
		validateForm(form, function(result) {
			if(result) {
				form.submit();
			}
		});
	}
</script>