<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="tools" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <tiles:insertAttribute name="head" />
    <script type="text/javascript">
    	$(function() {
			<%-- 表格隔行奇偶换色 --%>
			$(".trColor tr").each(function(i){  
				var className = ["deep", ""][i % 2];  
				$(this).addClass(className);  
				$(this).hover(function() {  
				     $(this).removeClass(className);  
				     $(this).addClass("hover");
				}, function() {
					$(this).removeClass("hover");  
					$(this).addClass(className);
		        });  
		    });
    	});
	</script>
</head>
<body>
	<script type="text/javascript" src="js/loading/loading.bind.js?t=${t:config('token.script')}"></script>
	
	<div class="box">
		<jsp:include page="../common/location.jsp" flush="true" />
		<tiles:insertAttribute name="main" />
	</div>
</body>
</html>