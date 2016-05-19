<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="instForm" action="${base}inst/upd" method="post">
	<input type="hidden" name="inst.instId" value="${inst.instId}" />
	
	<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb mTop" >
		<tbody>
			<tr>
				<th width="15%">分期名称：</th>
				<td>
					<c:choose>
					 	<c:when test="${empty inst.instName}">
					 		<input type="text" name="inst.instName" class="inp" />
					 	</c:when>
					 	<c:otherwise>${fn:trim(inst.instName)}</c:otherwise>
					</c:choose>
				</td>
				<td width="15%"><input type="button" value="添加规则" class="btn grayBtn" onclick="addRule()" /></td>
			</tr>
		</tbody>
	</table>
	
	<c:forEach var="rule" items="${inst.dyxInstRuleSet}" varStatus="stat">
		<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb mTop">
			<tbody>
				<tr>
					<th width="15%">月利率（%）：</th>
					<td width="35%"><input type="text" name="rule[${stat.index}].instMonRate" class="inp" value="${rule.instMonRate}" /></td>
					<th width="15%">手续费（%）：</th>
					<td colspan="3"><input type="text" name="rule[${stat.index}].instFee" class="inp" value="${rule.instFee}" /></td>
				</tr>
				<tr>
					<th>首付金额（元）：</th>
					<td><input type="text" name="rule[${stat.index}].dwPayAmt" class="inp" value="${rule.dwPayAmt}" /></td>
			        <th>还款方式：</th>
			        <td colspan="3">
						<c:forEach var="dic" items="${dicRepMethod}">
					        <label><input type="radio" name="rule[${stat.index}].repMethod" value="${dic.key}" class="radio" ${rule.repMethod eq dic.key ? "checked" : ""} />${dic.value}</label>
						</c:forEach>
			        </td>
	          	</tr>
	          	<tr>
			        <th>分期周期（月）：</th>
					<td colspan="3">
						<label><input type="radio" name="rule[${stat.index}].instPeriod" value="3" class="radio" ${rule.instPeriod eq "3" ? "checked" : ""} />3个月</label>
						<label><input type="radio" name="rule[${stat.index}].instPeriod" value="6" class="radio" ${rule.instPeriod eq "6" ? "checked" : ""} />6个月</label>
						<label><input type="radio" name="rule[${stat.index}].instPeriod" value="12" class="radio" ${rule.instPeriod eq "12" ? "checked" : ""} />12个月</label>
						<label><input type="radio" name="rule[${stat.index}].instPeriod" value="18" class="radio" ${rule.instPeriod eq "18" ? "checked" : ""} />18个月</label>
						<label><input type="radio" name="rule[${stat.index}].instPeriod" value="24" class="radio" ${rule.instPeriod eq "24" ? "checked" : ""} />24个月</label>
						<label><input type="radio" name="rule[${stat.index}].instPeriod" value="36" class="radio" ${rule.instPeriod eq "36" ? "checked" : ""} />36个月</label>
	          		</td>
					<td colspan="2" width="15%">
						<input type="button" value="删除" class="btn grayBtn" onclick="delRule(this)" />
						<input type="hidden" name="rule[${stat.index}].ruleId" value="${rule.ruleId}" />
						<input type="hidden" name="rule[${stat.index}].instStat" value="${rule.instStat}" />
						<input type="hidden" name="rule[${stat.index}].dispOrd" value="${stat.index}" />
					</td>
				</tr>
			</tbody>
		</table>
	</c:forEach>
</form>

<script type="text/javascript">
	<%-- 添加规则 --%>
	function addRule() {
		var form = $("#instForm");
		var idx = Number(form.data("idx") || "${inst.dyxInstRuleCount}");
		
		var html = '<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb mTop">'
					+ '<tbody>'
						+ '<tr>'
							+ '<th width="15%">月利率（%）：</th>'
							+ '<td width="35%"><input type="text" name="rule[' + idx + '].instMonRate" class="inp" /></td>'
							+ '<th width="15%">手续费（%）：</th>'
							+ '<td colspan="3"><input type="text" name="rule[' + idx + '].instFee" class="inp" /></td>'
						+ '</tr>'
						+ '<tr>'
							+ '<th>首付金额（元）：</th>'
							+ '<td><input type="text" name="rule[' + idx + '].dwPayAmt" class="inp" /></td>'
				        	+ '<th>还款方式：</th>'
				        	+ '<td colspan="3">'
							<c:forEach var="dic" items="${dicRepMethod}" varStatus="stat">
						        + '<label><input type="radio" name="rule[' + idx + '].repMethod" value="${dic.key}" class="radio" ${stat.index eq 0 ? "checked" : ""} />${dic.value}</label>'
							</c:forEach>
				       		+ '</td>'
		          		+ '</tr>'
		          		+ '<tr>'
					        + '<th>分期周期（月）：</th>'
							+ '<td colspan="3">'
								+ '<label><input type="radio" name="rule[' + idx + '].instPeriod" value="3" class="radio" />3个月</label>'
								+ '<label><input type="radio" name="rule[' + idx + '].instPeriod" value="6" class="radio" />6个月</label>'
								+ '<label><input type="radio" name="rule[' + idx + '].instPeriod" value="12" class="radio" />12个月</label>'
								+ '<label><input type="radio" name="rule[' + idx + '].instPeriod" value="18" class="radio" />18个月</label>'
								+ '<label><input type="radio" name="rule[' + idx + '].instPeriod" value="24" class="radio" />24个月</label>'
								+ '<label><input type="radio" name="rule[' + idx + '].instPeriod" value="36" class="radio" />36个月</label>'
			          		+ '</td>'
							+ '<td colspan="2" width="15%">'
			          			+ '<input type="button" value="删除" class="btn grayBtn" onclick="delRule(this)" />'
			          			+ '<input type="hidden" name="rule[' + idx + '].dispOrd" value="' + idx + '" />'
			          		+ '</td>'
						+ '</tr>'
					+ '</tbody>'
				+ '</table>';

		form.append(html).data("idx", idx + 1);
	}
	
	<%-- 删除规则 --%>
	function delRule(obj) {
		$(obj).parents("table").remove();
	}
	
	<%-- 保存更新分期业务 --%>
	function updateInst() {
		$("#instForm").unbind("submit").submit(function() {
			$(this).ajaxSubmit({
				url: "${base}inst/upd",
				success: function(msg) {
					<%-- 失败提示 --%>
					if(msg != "") {
						alert(msg);
						return false;
					}
					
					<%-- 成功提示 --%>
					dialogMessage("保存成功！", function(content) {
						<%-- 刷新列表页面 --%>
						parent.$("#srFrom").submit();
						
						<%-- 关闭提示窗口 --%>
						content.dialog("destroy").remove();
						
						<%-- 关闭编辑窗口 --%>
	                    parent.dialogIframeClose("dialog-iframe-inst");
					});
				},
				error: function() {
					alert("网络异常，数据提交失败！");
				}
			});
			return false;
		}).submit();
	}
</script>