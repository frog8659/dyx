<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="tools" prefix="t"%>

<script type="text/javascript" src="js/dyx/validate.audit.js?t=${t:config('token.script')}"></script>

<c:set var="metr" value="${ord.dyxOrdMetr}" />

<form id="ordForm" action="${base}ord/audit" method="post">
	<input type="hidden" name="token" id="token" value="${ord.token}" />
	<input type="hidden" name="ord.ordId" value="${ord.ordId}" />
	<input type="hidden" name="flow.flowId" value="${flowId}" />
	<input type="hidden" name="flow.flowSeg" value="${ord.audtStat}" />
	<input type="hidden" name="flow.actnId" id="actnId" />
	<input type="hidden" name="memo" id="memo" />
	
	<table cellpadding="0" cellspacing="0" class="listTb infoList mTop">
		<thead>
			<tr>
				<th colspan="8">订单详情<span>订单号：${fn:trim(ord.ordNo)}</span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="10%">商品型号：</th>
				<td colspan="3">${fn:trim(ord.prdName)}</td>
				<th width="10%">品牌：</th>
				<td>${fn:trim(ord.prdBrand)}</td>
				<th width="10%">商品类别：</th>
				<td>${fn:trim(ord.prdType)}</td>
			</tr>
			<tr>
				<th>配置规格：</th>
				<td>${fn:trim(ord.prdSpec)}</td>
				<th width="10%">颜色：</th>
				<td>${fn:trim(ord.prdColor)}</td>
				<th>数量：</th>
				<td>${fn:trim(ord.prdCount)}</td>
				<th>联系人：</th>
				<td>${fn:trim(ord.rec)}</td>
			</tr>
			<tr>
				<th>商品原价（元）：</th>
				<td>${ord.prdOriPric}</td>
				<th>商品现价（元）：</th>
				<td>${ord.prdCurPric}</td>
				<th>订单总额（元）：</th>
				<td>${ord.ordAmt}</td>
				<th>联系方式：</th>
				<td>${fn:trim(ord.recTel)}</td>
			</tr>
		</tbody>
	</table>
	
	<table cellpadding="0" cellspacing="0" class="listTb infoList mTop">
		<thead>
			<tr>
				<th colspan="11">分期详情<span rel="metrNo">分期申请书编号：${fn:trim(metr.metrNo)}</span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="10%">合作机构：</th>
				<td>${metr.instOrg}</td>
				<th>分期类型：</th>
				<td>${metr.instName}</td>
				<th width="10%">分期数：</th>
				<td>${metr.instPeriod}</td>
				<th width="10%">申请日期：</th>
				<td><fmt:formatDate value="${metr.metrDate}" pattern="yyyy年M月d日" /></td>
				<th width="10%">分期总额（元）：</th>
				<td colspan="2">${metr.instAmt}</td>
			</tr>
			<tr>
				<th>自付金额（元）：</th>
				<td>${metr.instOopAmt}</td>
				<th>月利率：</th>
				<td>${metr.instMonIntrRate}${empty metr.instMonIntrRate ? "" : "%"}</td>
				<th>月服务费率：</th>
				<td>${metr.instMonServRate}${empty metr.instMonServRate ? "" : "%"}</td>
				<th>保险费率：</th>
				<td>${metr.instPermRate}${empty metr.instPermRate ? "" : "%"}</td>
				<th>月还款日期：</th>
				<td colspan="2">${metr.instRepDate}</td>
			</tr>
			<tr>
				<th>月还款额（元）：</th>
				<td>${metr.instRepAmt}</td>
				<th>申请人：</th>
				<td>${metr.apl}</td>
				<th>联系方式：</th>
				<td>${metr.aplTel}</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				<th>&nbsp;</th>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<th>代办门店编号：</th>
				<td>${fn:trim(ord.shopNo)}</td>
				<th>门店简称：</th>
				<td colspan="3">${fn:trim(ord.shopName)}</td>
				<th>代办员编号：</th>
				<td>${fn:trim(ord.agtNo)}</td>
				<th>代办员：</th>
				<td colspan="2">${fn:trim(ord.agt)}</td>
			</tr>
			<tr>
				<th>门店地址：</th>
				<td colspan="7">${fn:trim(ord.shopAddr)}</td>
				<th>联系方式：</th>
				<td colspan="2">${fn:trim(ord.agtTel)}</td>
			</tr>
		</tbody>
	</table>

	<jsp:include page="audit_tab.jsp" flush="true" />
	
	<jsp:include page="audit_eval.jsp" flush="true" />
	
	<c:forEach var="proc" items="${ord.dyxOrdProcSet}">
		<div class="sesult">
			${dicSeg[proc.opSeg]}员：${proc.opUserRole}-${proc.opUser}&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
			${dicSeg[proc.opSeg]}日期：<fmt:formatDate value="${proc.opDate}" pattern="M月d日 hh:mm:dd" />&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
			${dicSeg[proc.opSeg]}结果：${dicActn[proc.opActn]}&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty proc.opMemo}">备注详情：${proc.opMemo}</c:if>
		</div>
	</c:forEach>

	<div class="btnBox">
		<c:forEach var="flow" items="${flowList}">
			<input type="button" class="btn blueBtn" value="${dicActn[flow.actnId]}" onclick="audit('${flow.actnId}', '${dicActn[flow.actnId]}');" />
			&nbsp;&nbsp;&nbsp;&nbsp;
		</c:forEach>
	</div>
</form>

<script type="text/javascript">
	$(function() {
		<%-- 初始化必输项样式 --%>
		validateMark(".important", "star-outer-aft");
	});
	
	<%-- 提交表单 --%>
	function formSubmit() {
		$("#ordForm").unbind("submit").submit();
	}
	
	<%-- 提交预审 --%>
	function audit(actnId, actnName, memo) {
		var form = $("#ordForm");
		validateForm(form, function(result) {
			if(result) {
				callback();
			}
		});
		
		var callback = function() {
			if(/(不通过|修改)/.test(actnName)) {
				var html = "<div><textarea id='popupMemo'></textarea></div>";
				var content = dialogMessage(html, null, true, {
					buttons: {
						"确认": function() {
							var popupMemo = $("#popupMemo").val();
							if($.trim(popupMemo) == "") {
								alert("请选择操作意见！");
							} else {
								audit(actnId, null, popupMemo);
								content.dialog("destroy").remove();
							}
						},
						"取消": function() {
							content.dialog("destroy").remove();
						}
					},
					title: "不通过/需修改原因"
				})
			} else if (actnName === null) {
				$("#actnId").val(actnId);
				$("#memo").val(memo || "");
				formSubmit();
			} else {
				dialogMessage("确认提交？", function(content) {
					audit(actnId, null, null);
					content.dialog("destroy").remove();
				}, true);
			}
		}
	}
</script>