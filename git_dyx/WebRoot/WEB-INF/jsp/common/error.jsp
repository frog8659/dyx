<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="sh.ricky.core.constant.GlobalConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="tools" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=session.getAttribute(GlobalConstants.SESSION_BASE)%>" />
	<title>出错了</title>
	<script type="text/javascript" src="js/jquery.min.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/jquery.bgiframe.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/loading/loading.js?t=${t:config('token.script')}"></script>
	<script type="text/javascript" src="js/loading/loading.bind.ajax.js?t=${t:config('token.script')}"></script>
	<style type="text/css">
		body { text-align:center; }
		.error { width:463px; margin:15% auto 0; }
	</style>
</head>
<body>
	<script type="text/javascript" src="js/loading/loading.bind.js?t=${t:config('token.script')}"></script>
	
	<div class="error">
		<img src="images/system_error.gif" width="463" height="150" border="0" usemap="#map" />
	</div>
	<map name="map">
		<area href="javascript:" onclick="forward();return false;" shape="rect" coords="363,110,438,137" />
	</map>
	
	<script type="text/javascript">
		<%-- 弹出错误信息 --%>
		<c:if test="${not empty exception.message}">
			alert("${exception.message}");
		</c:if>
		
		<%-- 点击“确定”按钮触发跳转事件 --%>
		function forward() {
			window.history.back();
		}
	</script>
</body>
</html>
