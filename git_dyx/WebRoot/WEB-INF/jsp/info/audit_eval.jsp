<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="tools" prefix="t"%>

<c:set var="metr" value="${ord.dyxOrdMetr}" />

<table cellpadding="0" cellspacing="0" class="listTb infoList mTop" id="evalTb">
	<thead>
		<tr>
			<th colspan="4">
				<c:if test="${ord.ordType eq ORD_TYPE_ZZBL}">
					资料审核（第二步-分期订单评价）
				</c:if>
				<c:if test="${ord.ordType eq ORD_TYPE_KFDB}">
					客服代办（第二步-分期订单评价）
				</c:if>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th>申请人类型：</th>
			<td>
				<label><input type="radio" name="radio" class="radio" />公司职员</label>
				<label><input type="radio" name="radio" class="radio" />务工人员</label>
				<label><input type="radio" name="radio" class="radio" />务农人员</label>
				<label><input type="radio" name="radio" class="radio" />学生</label>
				<label><input type="radio" name="radio" class="radio" />离退休人员</label>
				<label><input type="radio" name="radio" class="radio" />个体经营户</label>
				<label><input type="radio" name="radio" class="radio" />其他</label>
				<c:set var="evalName" value="申请人类型" />
				<c:set var="eval" value="${ord.dyxOrdEvalMap[evalName]}" />
				<input type="hidden" name="eval[0].evalName" value="${evalName}" />
				<input type="hidden" name="eval[0].evalId" value="${eval.evalId}" rel="${evalName}" />
				<input type="hidden" name="eval[0].evalCont" value="${eval.evalCont}" />
			</td>
			<th>是否已存在分期订单：</th>
			<td>
				<label><input type="radio" name="radio5" class="radio" ${isOrdExisted ? "checked" : ""} disabled />是</label>
				<label><input type="radio" name="radio5" class="radio" ${isOrdExisted ? "" : "checked"} disabled />否</label>
			</td>
		</tr>
		<tr>
			<th>身份信息：</th>
			<td>
				<label><input type="radio" name="radio2" class="radio" />门店所在区/县人口</label>
				<label><input type="radio" name="radio2" class="radio" />门店所在市人口</label>
				<label><input type="radio" name="radio2" class="radio" />流动人口</label>
				<c:set var="evalName" value="身份信息" />
				<c:set var="eval" value="${ord.dyxOrdEvalMap[evalName]}" />
				<input type="hidden" name="eval[1].evalName" value="${evalName}" />
				<input type="hidden" name="eval[1].evalId" value="${eval.evalId}" rel="${evalName}" />
				<input type="hidden" name="eval[1].evalCont" value="${eval.evalCont}" />
			</td>
			<th>月还款达月收入水平（%）：</th>
			<td rel="aplJobIncome"></td>
		</tr>
		<tr>
			<th>证明材料：</th>
			<td>
				<label><input type="radio" name="radio4" class="radio" />身份证明</label>
				<label><input type="radio" name="radio4" class="radio" />收入证明</label>
				<label><input type="radio" name="radio4" class="radio" />资产证明</label>
				<label><input type="radio" name="radio4" class="radio" />社保证明</label>
				<label><input type="radio" name="radio4" class="radio" />信用证明</label>
				<label><input type="radio" name="radio4" class="radio" />其他</label>
				<c:set var="evalName" value="证明材料" />
				<c:set var="eval" value="${ord.dyxOrdEvalMap[evalName]}" />
				<input type="hidden" name="eval[2].evalName" value="${evalName}" />
				<input type="hidden" name="eval[2].evalId" value="${eval.evalId}" rel="${evalName}" />
				<input type="hidden" name="eval[2].evalCont" value="${eval.evalCont}" />
			</td>
			<th>门店分期订单还款逾期率（单）：</th>
			<td></td>
		</tr>
		<tr>
			<th>申请人资格评价：</th>
			<td>
				<label><input type="radio" name="radio6" class="radio" />优秀</label>
				<label><input type="radio" name="radio6" class="radio" />良好</label>
				<label><input type="radio" name="radio6" class="radio" />中等</label>
				<label><input type="radio" name="radio6" class="radio" />及格</label>
				<label><input type="radio" name="radio6" class="radio" />不及格</label>
				<c:set var="evalName" value="申请人资格评价" />
				<c:set var="eval" value="${ord.dyxOrdEvalMap[evalName]}" />
				<input type="hidden" name="eval[3].evalName" value="${evalName}" />
				<input type="hidden" name="eval[3].evalId" value="${eval.evalId}" rel="${evalName}" />
				<input type="hidden" name="eval[3].evalCont" value="${eval.evalCont}" />
			</td>
			<th>门店分期订单业务数（单）：</th>
			<td>${stat1}</td>
		</tr>
		<tr>
			<th>门店分期资质：</th>
			<td>
				<label><input type="radio" name="radio7" class="radio" />优秀</label>
				<label><input type="radio" name="radio7" class="radio" />良好</label>
				<label><input type="radio" name="radio7" class="radio" />中等</label>
				<label><input type="radio" name="radio7" class="radio" />及格</label>
				<label><input type="radio" name="radio7" class="radio" />不及格</label>
				<label><input type="radio" name="radio7" class="radio" />无</label>
				<c:set var="evalName" value="门店分期资质" />
				<c:set var="eval" value="${ord.dyxOrdEvalMap[evalName]}" />
				<input type="hidden" name="eval[4].evalName" value="${evalName}" />
				<input type="hidden" name="eval[4].evalId" value="${eval.evalId}" rel="${evalName}" />
				<input type="hidden" name="eval[4].evalCont" value="${eval.evalCont}" />
			</td>
			<th>门店受理申请人所在区/县人员分期订单数（单）：</th>
			<td>${stat2}</td>
		</tr>
		<tr>
			<th>分期总额（元）：</th>
			<td>
				<input type="radio" name="radio1" class="radio" ${ord.ordAmt <= 3000 ? "checked" : ""} disabled />&le;3000
				<input type="radio" name="radio1" class="radio" ${ord.ordAmt > 3000 ? "checked" : ""} disabled />&gt;3000
			</td>
			<th>门店所在区/县受理申请人所在区/县人员分期订单数（单）：</th>
			<td>${stat3}</td>
		</tr>
		<tr>
			<th>居住信息：</th>
			<td>
				<label><input type="radio" name="radio3" class="radio" />户籍所在地与现居住地一致</label>
				<label><input type="radio" name="radio3" class="radio" />户籍所在地与现居住地不一致</label>
				<c:set var="evalName" value="居住信息" />
				<c:set var="eval" value="${ord.dyxOrdEvalMap[evalName]}" />
				<input type="hidden" name="eval[5].evalName" value="${evalName}" />
				<input type="hidden" name="eval[5].evalId" value="${eval.evalId}" rel="${evalName}" />
				<input type="hidden" name="eval[5].evalCont" value="${eval.evalCont}" />
			</td>
			<th>门店所在省级市受理申请人所在区/县人员分期订单数（单）：</th>
			<td>${stat4}</td>
		</tr>
	</tbody>
</table>

<script type="text/javascript">
	$(function() {
		<%-- 初始化“月还款达月收入水平” --%>
		$("#aplJobIncome").change(function() {
			$("[rel=aplJobIncome]").html(calcIncomeEval(this.value));
		}).change();
		
		$("#evalTb :radio").each(function() {
			<%-- 初始化选项值 --%>
			var label = $(this).parent();
			var cont = label.nextAll("[name$='evalCont']");
			if(cont.size() == 1) {
				if(cont.val() == label.text()) {
					$(this).attr("checked", "checked");
				} else {
					$(this).removeAttr("checked");
				}
			}
		}).change(function() {
			<%-- 绑定分期订单评价事件 --%>
			var label = $(this).parent();
			var cont = $.trim(label.text());
			label.nextAll("[name$='evalCont']").val(cont);
		});
	})
	
	<%-- 计算“月还款达月收入水平” --%>
	function calcIncomeEval(income) {
		var repAmt = $.trim("${metr.instRepAmt}");
		if(repAmt != "" && $.isNumeric(income) && Number(income) != 0) {
			return (repAmt * 100 / income).toFixed("2");
		} else {
			return "";
		}
	}
</script>