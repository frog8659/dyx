<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="tools" prefix="t"%>

<script type="text/javascript" src="js/page/page.search.js?t=${t:config('token.script')}"></script>

<div class="search">
	<form id="srForm" action="${base}ord/audit/list/${condition.segSort}?ordType=${condition.ordType}" method="post">
		<table cellpadding="0" cellspacing="0" class="srTb">
			<tr>
				<th>订单日期：</th>
				<td width="10%">
					<input type="text" name="ordDate" class="inpSr Wdate" value="${condition.ordDate}"  />
				</td>
				<th>地区：</th>
				<td width="10%">
					<select name="shopProv" class="prov" data-first-title="-请选择省份-" data-first-value="" data-value="${condition.shopProv}"></select>
				</td>
				<td width="10%">
					<select name="shopCity" class="city" data-first-title="-请选择城市-" data-first-value="" data-value="${condition.shopCity}"></select>
				</td>
				<td width="10%">
					<select name="shopDist" class="dist" data-first-title="-请选择区县-" data-first-value="" data-value="${condition.shopDist}"></select>
				</td>
				<th>审核状态：</th>
				<td width="10%">
					<select name="audtStat">
						<option value="">-请选择审核状态-</option>
						<c:forEach var="dic" items="${dicAudtStat[condition.segSort]}">
							<option value="${dic.key}" ${dic.key eq condition.audtStat ? "selected" : ""}>${dic.key}</option>
						</c:forEach>
					</select>
				</td>
				<th>其他：</th>
				<td width="20%">
					<input type="text" name="keyword" class="inpSr" value="${condition.keyword}" />
				</td>
				<td>
					<input type="hidden" name="pageNo" id="pageNo" value="${condition.pageNo}" />
					<input type="submit" name="button" value="查询" class="btn" onclick="$('#pageNo').val('1')" />
				</td>
			</tr>
		</table>
	</form>
</div>

<table cellpadding="0" cellspacing="0" class="listTb trColor mTop">
	<c:choose>
		<c:when test="${empty ordPage.result}">
			<tbody>
				<tr><td>没有符合条件的数据</td></tr>
			</tbody>
		</c:when>
		<c:otherwise>
			<thead>
				<tr>
					<th width="40">序号</th>
					<th>订单编号</th>
					<th>订单日期</th>
					<th>省</th>
					<th>市</th>
					<th>区/县</th>
					<th>收货方</th>
					<th>联系电话</th>
					<th>门店名称</th>
					<th>代办员</th>
					<th>联系方式</th>
					<th>审核状态</th>
					<th>银行状态</th>
					<th>操作时间</th>
					<th>操作人</th>
					<th>备注详情</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ord" items="${ordPage.result}" varStatus="stat">
					<tr onclick="toggleHighlight(this)">
						<td>${stat.count}</td>
						<td><a href="javascript:" onclick="detail('${ord.ordId}', '${condition.segSort}', event);">${fn:trim(ord.ordNo)}</a></td>
						<td><fmt:formatDate value="${ord.ordDate}" pattern="yyyy-M-d" /></td>
						<td>${dicRegion[ord.shopProv]}</td>
						<td>${dicRegion[ord.shopCity]}</td>
						<td>${dicRegion[ord.shopDist]}</td>
						<td>${fn:trim(ord.rec)}</td>
						<td>${fn:trim(ord.recTel)}</td>
						<td>${fn:trim(ord.shopName)}</td>
						<td>${fn:trim(ord.agt)}</td>
						<td>${fn:trim(ord.agtTel)}</td>
						<td>${dicSegStat[ord.audtStat]}</td>
						<td>${fn:trim(ord.bankStat)}</td>
						<td><fmt:formatDate value="${ord.lastOpDate}" pattern="M月d日 hh:mm:ss" /></td>
						<td>${fn:trim(ord.lastOpUser)}</td>
						<td>
							<c:if test="${not empty ord.lastDyxOrdProc.opMemo}">
								<a href="javascript:" onclick="memo($(this).next(':hidden'), event);return false;">${fn:substring(fn:trim(ord.lastDyxOrdProc.opMemo), 0, 4)}...</a>
								<input type="hidden" value="${fn:trim(ord.lastDyxOrdProc.opMemo)}" />
							</c:if>
						</td>
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
			page: "${ordPage.pageCount}",
			total: "${ordPage.count}",
			container: ".page-container"
		});
		
		<%-- 初始化行政区划联动 --%>
		$(".search").cxSelect({
			url: "js/cityData.min.json",
			selects: ["prov", "city", "dist"],
			jsonValue: "v"
		});
	})
	
	<%-- 点击行显示高亮 --%>
	function toggleHighlight(tr) {
		if($(tr).hasClass("highlight")) {
			$(tr).removeClass("highlight");
		} else {
			$(tr).addClass("highlight").siblings().removeClass("highlight");
		}
	}
	
	<%-- 进入订单详情界面 --%>
	function detail(id, sort, event) {
		window.location = "${base}ord/audit/detail/" + sort + "?id=" + id;

		<%-- 禁止事件冒泡 --%>
		event.stopPropagation();
	}
	
	<%-- 查看备注详情 --%>
	function memo(obj, event) {
		window.top.dialogMessage(obj.val(), null, false, {
			title: "备注详情",
			width: 300,
			height: "auto",
		});
		
		<%-- 禁止事件冒泡 --%>
		event.stopPropagation();
	}
</script>