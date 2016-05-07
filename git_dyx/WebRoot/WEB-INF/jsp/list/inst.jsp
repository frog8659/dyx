<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="search">
	<form id="srFrom" action="${base}inst/list" method="post">
		<table cellpadding="0" cellspacing="0" class="srTb">
			<tr>
				<th width="8%">分期名称：</th>
				<td width="14%">
					<select name="instId">
						<option value="">-请选择分期名称-</option>
						<c:forEach var="dic" items="${dicInstName}">
							<option value="${dic.key}" ${dic.key eq condition.instId ? "selected" : ""}>${dic.value}</option>	
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="submit" name="button" value="查询" class="btn" />
					<input type="button" name="button" value="新增" class="btn blueBtn" onclick="editInst();" />
				</td>
			</tr>
		</table>
	</form>
</div>

<table cellpadding="0" cellspacing="0" class="listTb trColor mTop">
	<c:choose>
		<c:when test="${empty instList}">
			<tbody>
				<tr><td>没有符合条件的数据</td></tr>
			</tbody>
		</c:when>
		<c:otherwise>
			<thead>
				<tr>
					<th width="40">序号</th>
					<th>分期名称</th>
					<th>月利率%</th>
					<th>手续费%</th>
					<th>首付金额（元）</th>
					<th>分期周期（月）</th>
					<th>还款方式</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="inst" items="${instList}" varStatus="stat1">
					<c:if test="${inst.dyxInstRuleCount gt 0}">
						<c:set var="rs" value="${inst.dyxInstRuleCount}" />
						<c:forEach var="rule" items="${inst.dyxInstRuleSet}" varStatus="stat2">
							<tr>
								<c:if test="${stat2.index eq 0}">
									<th rowspan="${rs}" class="thead">${stat1.count}</th>
									<th rowspan="${rs}" class="thead">${fn:trim(inst.instName)}</th>
								</c:if>
								<td>${rule.instMonRate}</td>
								<td>${rule.instFee}</td>
								<td>${rule.dwPayAmt}</td>
								<td>${fn:trim(rule.instPeriod)}</td>
								<td>${dicRepMethod[rule.repMethod]}</td>
								<td>${dicInstStat[rule.instStat]}</td>
								<td>
									<a href="javascript:" onclick="updInstStat('${rule.ruleId}', '${INST_STAT_YQD eq rule.instStat ? INST_STAT_YDJ : INST_STAT_YQD}');return false;">${INST_STAT_YQD eq rule.instStat ? "冻结" : "启动"}</a>
									<a href="javascript:" onclick="editInst('${inst.instId}');return false;">编辑</a>
									<a href="javascript:" onclick="delInst('${rule.ruleId}');return false;">删除</a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</c:forEach>
			</tbody>
		</c:otherwise>
	</c:choose>
</table>

<script type="text/javascript">
	<%-- 分期业务编辑 --%>
	function editInst(instId) {
		<%-- 弹出窗口参数 --%>
		var opt = {
			url: "inst/edit?instId=" + (instId || ""),
			width: 820,
			height: 420,
			title: "分期业务编辑",
			id: "dialog-iframe-inst",
			buttons: {
				"保存": "updateInst",
				"取消": undefined
			}
		};
		
		<%-- 弹出窗口 --%>
		dialogIframe(opt);
	}
	
	<%-- 调整分期业务规则状态 --%>
	function updInstStat(id, stat) {
		ajaxCommon({
			url: "${base}inst/rule/upd_stat",
			data: {
				ruleId: id,
				stat: stat
			}
		}, function(msg) {
			<%-- 失败提示 --%>
			if(msg != "") {
				alert(msg);
				return false;
			}
			
			<%-- 成功提示 --%>
			dialogMessage("操作成功！", function(content) {
				<%-- 刷新列表 --%>
				$("#srFrom").submit();
				
				<%-- 关闭提示窗口 --%>
				content.dialog("destroy").remove();
			});
		});
	}
	
	<%-- 删除分期业务规则 --%>
	function delInst(id) {
		dialogMessage("确定删除该条分期业务规则？", function(content) {
			<%-- 关闭提示窗口 --%>
			content.dialog("destroy").remove();
			
			ajaxCommon({
				url: "${base}inst/rule/del",
				data: {
					ruleId: id
				}
			}, function(msg) {
				<%-- 失败提示 --%>
				if(msg != "") {
					alert(msg);
					return false;
				}
				
				<%-- 成功提示 --%>
				dialogMessage("操作成功！", function(content2) {
					<%-- 刷新列表 --%>
					$("#srFrom").submit();
					
					<%-- 关闭提示窗口 --%>
					content2.dialog("destroy").remove();
				});
			});
		}, true);
	}
</script>