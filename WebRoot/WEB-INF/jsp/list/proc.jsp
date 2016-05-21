<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="tools" prefix="t"%>

<script type="text/javascript" src="js/page/page.search.js?t=${t:config('token.script')}"></script>

<c:forEach var="authId" items="${user.authList}">
	<c:if test="${authId eq USER_AUTH_GLSYYHCZRZ}">
		<c:set var="GLSYYHCZRZ" value="${YES_VALUE}" />
	</c:if>
</c:forEach>

<div class="search">
	<form id="srForm" action="${base}ord/proc/list" method="post">
		<table cellpadding="0" cellspacing="0" class="srTb">
			<tr>
				<th width="8%">订单日期：</th>
				<td width="10%">
					<input type="text" name="ordDate" class="inpSr Wdate" value="${condition.ordDate}"  />
				</td>
				<th width="8%">订单编号：</th>
				<td width="15%">
					<input type="text" name="ordNo" class="inpSr" value="${condition.ordNo}" />
				</td>
				<c:if test="${GLSYYHCZRZ eq YES_VALUE}">
					<th width="8%">操作员：</th>
					<td width="15%">
						<select name="opUserId">
							<option value="">-请选择-</option>
							<c:forEach var="dic" items="${dicOpUser}">
								<option value="${dic.key}" ${dic.key eq condition.opUserId ? "selected" : ""}>${dic.value}</option>	
							</c:forEach>
						</select>
					</td>
				</c:if>
				<th width="6%">其他：</th>
				<td width="18%">
					<input type="text" name="keyword" class="inpSr" value="${condition.keyword}" />
				</td>
				<td>
					<input type="hidden" name="pageNo" id="pageNo" value="${condition.pageNo}" />
					<input type="submit" name="button" value="查询" class="btn" />
				</td>
			</tr>
		</table>
	</form>
</div>

<table cellpadding="0" cellspacing="0" class="listTb trColor mTop">
	<c:choose>
		<c:when test="${empty procPage.result}">
			<tbody>
				<tr><td>没有符合条件的数据</td></tr>
			</tbody>
		</c:when>
		<c:otherwise>
			<thead>
				<tr>
					<th width="40">序号</th>
					<th>操作日期</th>
					<th>操作时间</th>
					<th>操作栏目</th>
					<th>操作内容</th>
					<c:if test="${GLSYYHCZRZ eq YES_VALUE}">
						<th>操作员</th>
					</c:if>
					<th>订单编号</th>
					<th>收货人</th>
					<th>代办员</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${procPage.result}" varStatus="stat">
					<c:set var="proc" value="${result[0]}" />
					<c:set var="ord" value="${result[1]}" />
					<tr>
						<td>${stat.count}</td>
						<td><fmt:formatDate value="${proc.opDate}" pattern="yyyy-M-d" /></td>
						<td><fmt:formatDate value="${proc.opDate}" pattern="h:mm:ss" /></td>
						<td>${fn:trim(proc.opMenu)}</td>
						<td>${dicActn[proc.opActn]}</td>
						<c:if test="${GLSYYHCZRZ eq YES_VALUE}">
							<td>${fn:trim(proc.opUser)}</td>
						</c:if>
						<td>${fn:trim(ord.ordNo)}</td>
						<td>${fn:trim(ord.rec)}</td>
						<td>${fn:trim(ord.agt)}</td>
						<td>${fn:trim(proc.opMemo)}</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:otherwise>
	</c:choose>
</table>

<div class="page-container"></div>

<script type="text/javascript">
	$(function() {
		<%-- 初始化分页控件 --%>
		paging.form.init({
			form: "#srForm",
			numb: "#pageNo",
			page: "${procPage.pageCount}",
			total: "${procPage.count}",
			container: ".page-container"
		});
	});
</script>