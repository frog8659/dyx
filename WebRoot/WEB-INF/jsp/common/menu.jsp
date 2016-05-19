<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>

<dl>
	<dt class="current">
		<a href="${base}home" target="frame"><i class="iconfont">&#xe606;</i>首页</a>
	</dt>
	
	<c:forEach var="menu" items="${menuList}">
		<dt>
			<c:choose>
				<c:when test="${empty menu.menuLink}">
					<i class="iconfont">${menu.menuIcon}</i>${menu.menuName}
				</c:when>
				<c:otherwise>
					<a href="${base}${menu.menuLink}" onclick="loc('${menu.menuName}')" target="frame"><i class="iconfont">${menu.menuIcon}</i>${menu.menuName}</a>
				</c:otherwise>
			</c:choose>
		</dt>
		
		<c:if test="${not empty menu.subMenuList}">
			<dd>
				<ul>
					<c:forEach var="subMenu" items="${menu.subMenuList}">
						<c:choose>
							<c:when test="${empty subMenu.menuLink}">
								<li><a href="javascript:" onclick="return false;">&middot; ${subMenu.menuName}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${base}${subMenu.menuLink}" onclick="loc('${subMenu.menuName}')" target="frame">&middot; ${subMenu.menuName}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</dd>
		</c:if>
	</c:forEach>
</dl>

<script type="text/javascript">
	$(function() {
		<%-- 左侧标签上下切换 --%>
		$(".menu dt").click(function() {
			$(this).next('dd').slideDown().end().siblings().next('dd').slideUp();
			$(this).parent().find("dt").removeClass("current");
			$(this).addClass("current");	
			$(this).parent().find("dd").removeClass("current");
			$(this).next("dd").addClass("current");				 
		});
		$(".menu li").click(function() {
			$(this).parent().parent().parent().find("li").removeClass("current");
			$(this).addClass("current");			 
		});
	});
	
	<%-- 定位当前位置 --%>
	function loc(menu) {
		$(".menu").data("location", menu);
	}
</script>