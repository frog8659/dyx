<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="tools" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<jsp:include page="title.jsp" flush="true" />
	<jsp:include page="head.jsp" flush="true" />
	<style type="text/css">
	<!--
		html, body { overflow: hidden; }
	-->
	</style>
	
	<script type="text/javascript">
		<%-- 框架大小改变时触发事件 --%>
		function rsize() {
			<%-- 浏览器当前窗口可视区域高度 --%>
			var k = $(window).height();
			<%-- 判断框架主区高度 --%>
			$("#wrap, #side, #main").css({
				"height" : k - 150
			});
		}
		
		$(function() {
			rsize();
			window.onresize = rsize;
		});
	</script>
</head>
<body>
	<script type="text/javascript" src="js/loading/loading.bind.js?t=${t:config('token.script')}"></script>

	<div id="head">
		<h1 id="logo">${t:config('prop.sys.name')}</h1>
		<div class="userInfo">
			<i class="iconfont">&#xe60a;</i> ${dicRole[user.userRole]}-${user.nickName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${base}passport/logout"><i class="iconfont">&#xe609;</i> 安全退出</a>
		</div>
	</div>

	<div id="wrap">
  		<div id="side">
    		<div class="menu">
    			<jsp:include page="menu.jsp" flush="true" />
			</div>
  		</div>
  		
		<div id="main">
			<iframe name="frame" src="${base}home" width="100%" height="100%" frameborder="0" scrolling="yes" ></iframe>
		</div>
	</div>
	
	<div id="footer">CopyRight <span>&copy;</span> 2016-2019 All Rights Reserved 版权所有</div>
</body>
</html>