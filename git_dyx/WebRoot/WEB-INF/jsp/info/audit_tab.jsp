<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="tools" prefix="t"%>

<link type="text/css" rel="stylesheet" href="css/zyImage.css?t=${t:config('token.css')}" />
<script type="text/javascript" src="js/e-smart-zoom-jquery.min.js?t=${t:config('token.script')}"></script>
<script type="text/javascript" src="js/zyImage.js?t=${t:config('token.script')}"></script>
<script type="text/javascript" src="js/dyx/business.util.js?t=${t:config('token.script')}"></script>

<c:set var="metr" value="${ord.dyxOrdMetr}" />

<table cellpadding="0" cellspacing="0" class="listTb infoList mTop">
	<thead>
		<tr>
			<th>
				<c:if test="${ord.ordType eq ORD_TYPE_ZZBL}">
					资料审核（第一步-申请资料审核）
				</c:if>
				<c:if test="${ord.ordType eq ORD_TYPE_KFDB}">
					客服代办（第一步-申请资料填写与审核）
				</c:if>
				<input type="hidden" name="metr.tabAudt" id="tabAudt" value="${empty metr.tabAudt ? (ord.ordType eq ORD_TYPE_KFDB ? '00000' : '11111') : metr.tabAudt}" />
			</th>
		</tr>
	</thead>
</table>

<div>
	<ul class="tabs clear">
		<li onclick="toggleTab('1');return false;" class="current">注册信息<i id="metr-1"></i></li>
		<li onclick="toggleTab('2');return false;">基本信息<i id="metr-2"></i></li>
		<li onclick="toggleTab('3');return false;">工作情况<i id="metr-3"></i></li>
		<li onclick="toggleTab('4');return false;">联系人信息<i id="metr-4"></i></li>
		<li onclick="toggleTab('5');return false;">附件资料<i id="metr-5"></i></li>
	</ul>
	
	<div class="docBox">
		<div class="info" metr="1">
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<tr>
						<th width="30%">分期申请书编号：</th>
						<td><input type="text" name="metr.metrNo" id="metrNo" value="${fn:trim(metr.metrNo)}" class="inp" /></td>
					</tr>
					<tr>
						<th>姓名：</th>
						<td><input type="text" name="metr.aplName" value="${fn:trim(metr.aplName)}" class="inp" /></td>
					</tr>
					<tr>
						<th>身份证号：</th>
						<td><input type="text" name="metr.aplIdc" value="${fn:trim(metr.aplIdc)}" class="inp" /></td>
					</tr>
					<tr>
						<th>手机号码：</th>
						<td><input type="text" name="metr.aplMob" value="${fn:trim(metr.aplMob)}" class="inp" /></td>
					</tr>
					<tr>
						<th>发卡银行：</th>
						<td>
							<select name="metr.aplBank">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicBank}">
									<option value="${dic.key}" ${dic.key eq metr.aplBank ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>银行卡号：</th>
						<td><input type="text" name="metr.aplBankCard" value="${fn:trim(metr.aplBankCard)}" class="inp" /></td>
					</tr>
					<tr>
						<th>银行预留手机号：</th>
						<td><input type="text" name="metr.aplBankMob" value="${fn:trim(metr.aplBankMob)}" class="inp" /></td>
					</tr>
					<tr>
						<th>身份证有效期：</th>
						<td><input type="text" name="metr.aplIdcExp" value="<fmt:formatDate value='${metr.aplIdcExp}' pattern='yyyy-MM-dd' />" class="inp Wdate" /></td>
					</tr>
					<tr>
						<th>年龄：</th>
						<td><input type="text" name="metr.aplAge" value="${fn:trim(metr.aplAge)}" class="inp" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="info" metr="2">
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<tr>
						<th width="30%">性别：</th>
						<td>
							<select name="metr.aplSex">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicSex}">
									<option value="${dic.key}" ${dic.key eq metr.aplSex ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>教育程度：</th>
						<td>
							<select name="metr.aplEdu">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicEdu}">
									<option value="${dic.key}" ${dic.key eq metr.aplEdu ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>婚姻状态：</th>
						<td>
							<select name="metr.aplMari">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicMari}">
									<option value="${dic.key}" ${dic.key eq metr.aplMari ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th rowspan="2">现居住地址：</th>
						<td>
							<select name="metr.aplProv" class="prov" data-first-title="-省-" data-first-value="" data-value="${metr.aplProv}"></select>
							<select name="metr.aplCity" class="city" data-first-title="-市-" data-first-value="" data-value="${metr.aplCity}"></select>
							<select name="metr.aplDist" class="dist" data-first-title="-县/区-" data-first-value="" data-value="${metr.aplDist}"></select>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="metr.aplAddr" value="${fn:trim(metr.aplAddr)}" class="inp" />
						</td>
					</tr>
					<tr>
						<th>现居住宅状况：</th>
						<td>
							<select name="metr.aplHomeStat">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicHomeStat}">
									<option value="${dic.key}" ${dic.key eq metr.aplHomeStat ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>本地居住年限：</th>
						<td><input type="text" name="metr.aplResdPeriod" value="${fn:trim(metr.aplResdPeriod)}" class="inp" /></td>
					</tr>
					<tr>
						<th>电子邮箱：</th>
						<td><input type="text" name="metr.aplMail" value="${fn:trim(metr.aplMail)}" class="inp" /></td>
					</tr>
					<tr>
						<th>固定电话：</th>
						<td><input type="text" name="metr.aplTel" value="${fn:trim(metr.aplTel)}" class="inp" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="info" metr="3">
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<tr>
						<th width="30%">受雇类型：</th>
						<td>
							<select name="metr.aplEmpType">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicEmpType}">
									<option value="${dic.key}" ${dic.key eq metr.aplEmpType ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>现单位名称：</th>
						<td><input type="text" name="metr.aplCompany" value="${fn:trim(metr.aplCompany)}" class="inp" /></td>
					</tr>
					<tr>
						<th>单位性质：</th>
						<td>
							<select name="metr.aplCompNatu">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicCompNatu}">
									<option value="${dic.key}" ${dic.key eq metr.aplCompNatu ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>职务级别：</th>
						<td>
							<select name="metr.aplJobTitle">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicJobTitle}">
									<option value="${dic.key}" ${dic.key eq metr.aplJobTitle ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>税后月收入：</th>
						<td><input type="text" name="metr.aplJobIncome" id="aplJobIncome" value="${metr.aplJobIncome}" class="inp" /></td>
					</tr>
					<tr>
						<th>现单位工作年限：</th>
						<td><input type="text" name="metr.aplJobYear" value="${fn:trim(metr.aplJobYear)}" class="inp" /></td>
					</tr>
					<tr>
						<th>单位电话：</th>
						<td><input type="text" name="metr.aplCompTel" value="${fn:trim(metr.aplCompTel)}" class="inp" /></td>
					</tr>
					<tr>
						<th>单位地址：</th>
						<td><input type="text" name="metr.aplCompAddr" value="${fn:trim(metr.aplCompAddr)}" class="inp" /></td>
					</tr>
					<tr>
						<th>工作性质：</th>
						<td>
							<select name="metr.aplJobSpec">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicJobSpec}">
									<option value="${dic.key}" ${dic.key eq metr.aplJobSpec ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>单位规模：</th>
						<td>
							<select name="metr.aplCompSize">
								<option value="">--</option>
								<c:forEach var="dic" items="${dicCompSize}">
									<option value="${dic.key}" ${dic.key eq metr.aplCompSize ? "selected" : ""}>${dic.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="info" metr="4">
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb toggle-include">
				<tbody>
					<tr><th colspan="2"><a href="javascript:" onclick="addContact();return false;">【添加联系人】</a></th></tr>
				</tbody>
			</table>
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody id="contTb">
					<c:forEach var="cont" items="${metr.dyxOrdMetrContactSet}" varStatus="stat">
						<tr><th colspan="2" class="thead">紧急联系人${stat.count}</th></tr>
						<tr>
							<th width="30%">姓名：</th>
							<td>
								<input type="text" name="contact[${stat.index}].name" value="${fn:trim(cont.name)}" class="inp" />
								<input type="hidden" name="contact[${stat.index}].contId" value="${fn:trim(cont.contId)}" />
								<input type="hidden" name="contact[${stat.index}].dispOrd" value="${stat.count}" />
							</td>
						</tr>
						<tr>
							<th>关系：</th>
							<td>
								<select name="contact[${stat.index}].rel">
									<option value="">--</option>
									<c:forEach var="dic" items="${dicContRel}">
										<option value="${dic.key}" ${dic.key eq cont.rel ? "selected" : ""}>${dic.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>联系电话：</th>
							<td><input type="text" name="contact[${stat.index}].tel" value="${fn:trim(cont.tel)}" class="inp" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="info" metr="5">
			<input type="file" name="file" id="attach" class="out" />
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<tr>
						<th class="thead">&nbsp;</th>
						<th colspan="2" class="thead">身份证</th>
					</tr>
					<tr>
						<td width="50">&nbsp;</td>
						<td width="200">
							<c:set var="attach" value="${metr.dyxOrdMetrAttachMap['1']['1']}" />
							<input type="hidden" name="attach[0].atchId" x="1" y="1" value="${attach.atchId}" rel="${attach.atchTitle}" />
							<span class="photo" onclick="uploadAttach('0', $(this).find('img'));">
								<strong>正面</strong>
								<c:set var="src">data:image/${attach.atchExt};base64,${attach.atchPreview}</c:set>
								<img width="150" height="120" src="${empty attach.atchName ? '' : src}" class="toggle-exclude" />
							</span>
						</td>
						<td>
							<c:set var="attach" value="${metr.dyxOrdMetrAttachMap['1']['2']}" />
							<input type="hidden" name="attach[1].atchId" x="1" y="2" value="${attach.atchId}" rel="${attach.atchTitle}" />
							<span class="photo" onclick="uploadAttach('1', $(this).find('img'));">
								<strong>反面</strong>
								<c:set var="src">data:image/${attach.atchExt};base64,${attach.atchPreview}</c:set>
								<img width="150" height="120" src="${empty attach.atchName ? '' : src}" class="toggle-exclude" />
							</span>
						</td>
					</tr>
					<tr>
						<th class="thead">&nbsp;</th>
						<th class="thead">储蓄卡</th>
						<th class="thead">手持身份证照</th>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<c:set var="attach" value="${metr.dyxOrdMetrAttachMap['2']['1']}" />
							<input type="hidden" name="attach[2].atchId" x="2" y="1" value="${attach.atchId}" rel="${attach.atchTitle}" />
							<span class="photo" onclick="uploadAttach('2', $(this).find('img'));">
								<strong>正面</strong>
								<c:set var="src">data:image/${attach.atchExt};base64,${attach.atchPreview}</c:set>
								<img width="150" height="120" src="${empty attach.atchName ? '' : src}" class="toggle-exclude" />
							</span>
						</td>
						<td>
							<c:set var="attach" value="${metr.dyxOrdMetrAttachMap['3']['1']}" />
							<input type="hidden" name="attach[3].atchId" x="3" y="1" value="${attach.atchId}" rel="${attach.atchTitle}" />
							<span class="photo" onclick="uploadAttach('3', $(this).find('img'));">
								<strong>正面</strong>
								<c:set var="src">data:image/${attach.atchExt};base64,${attach.atchPreview}</c:set>
								<img width="150" height="120" src="${empty attach.atchName ? '' : src}" class="toggle-exclude" />
							</span>
						</td>
					</tr>
					<tr>
						<th class="thead">&nbsp;</th>
						<th colspan="2" class="thead">其他辅助资料（例：户口本、驾驶证、信用卡、工资单等）</th>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="2">
							<ul class="pts">
								<c:forEach var="attach" items="${metr.dyxOrdMetrAttachMap['4']}" varStatus="stat">
									<li>
										<input type="hidden" name="attach[${stat.index + 4}].atchId" x="4" y="${attach.key}" value="${attach.value.atchId}" rel="${attach.value.atchTitle}" />
										<span class="photo" onclick="uploadAttach('${stat.index + 4}', $(this).find('img'));">
											<c:set var="src">data:image/${attach.value.atchExt};base64,${attach.value.atchPreview}</c:set>
											<img width="150" height="120" src="${empty attach.value.atchName ? '' : src}" class="toggle-exclude" />
										</span>
									</li>
								</c:forEach>
								<li class="toggle-include"><span class="photo add" onclick="addAttach(this);"><strong>添加</strong></span></li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="btnBox1">
			<c:if test="${not empty flowList}">
				<c:if test="${ord.ordType eq ORD_TYPE_KFDB and dicSeg[ord.audtStat] eq '初审'}">
					<input type="button" class="btn grayBtn" value="取消" onclick="toggleTab()" editable="${YES_VALUE}" />
					<input type="button" class="btn grayBtn" value="保存" onclick="updateMetr('1')" editable="${YES_VALUE}" />
					<input type="button" class="btn grayBtn" value="修改" onclick="updateMetr('0')" editable="${NO_VALUE}" />
				</c:if>
				
				<c:if test="${ord.ordType eq ORD_TYPE_ZZBL or ord.ordType eq ORD_TYPE_KFDB}">
					<input type="button" class="btn grayBtn" value="通过" onclick="updateMetr('2')" editable="${NO_VALUE}" />
					<input type="button" class="btn grayBtn" value="拒绝" onclick="updateMetr('3')" editable="${NO_VALUE}" />
				</c:if>
			</c:if>
		</div>
		<div class="focus">
			<div id="panImage" class="pan_image"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		<%-- 初始化分期申请书编号 --%>
		$("#metrNo").change(function() {
			$("[rel=metrNo]").html("分期申请书编号：" + this.value);
		}).change();
		
		<%-- 初始化行政区划联动 --%>
		$(".info").cxSelect({
			url: "js/cityData.min.json",
			selects: ["prov", "city", "dist"],
			jsonValue: "v"
		});
		
		<%-- 初始化页面内容展示 --%>
		deferredCall(function() {
			var audt = $("#tabAudt").val();
			for(var i in audt) {
				<%-- 初始化评审结果图标 --%>
				updateCheckIcon(Number(i) + 1, audt[i]);
				
				<%-- 初始化标签页内容可编辑性 --%>
				toggleTabEditable(Number(i) + 1, audt[i]);
			}
		});

		<%-- 初始化标签页切换函数 --%>
		$(".tabs li").click(function() {
			$(this).parent().find("li").removeClass("current"); 
      		$(this).addClass("current").siblings().removeClass();
		});
		
		<%-- 初始化标签页显示 --%>
		toggleTab("0");

		<%-- 初始化附件图片展示 --%>
		loadImages();
		
		<%-- 初始化上传控件 --%>
		var file = $("#attach").ajaxfileupload({
			"action": "ord/upload",
			"params": {
				"atchId": function() {
					return file.data("inp").val();
				},
				"atchType": function() {
					return file.data("inp").attr("x");
				},
				"dispOrd": function() {
					return file.data("inp").attr("y");
				},
				"metrId": "${metr.metrId}"
			},
			"onComplete": function(data) {
				window.top.loaded();
				
				if(typeof data.status != "undefined" && !data.status) {
					alert("只支持jpg、jpeg、gif、png格式的图片附件！");
					return false;
				}
				
				if(typeof data.error != "undefined") {
					alert(data.error);
					return false;
				}
				
				if(typeof data.attach != "undefined") {
					this.data("inp").val(data.attach.atchId).attr("rel", data.attach.atchTitle);
					this.data("img").attr("src", "data:image/" + data.attach.atchExt + ";base64," + data.attach.atchPreview).show().prev("strong").hide();
					
					<%-- 重新加载附件图片 --%>
					loadImages();
				}
			},
			"onStart": function () {
				window.top.loading();
			},
			"onCancel": function() {
				window.top.loaded();
			}
	    });
	});

	<%-- 加载图片 --%>
	function loadImages() {
		var images = new Array();
		$("[name^='attach['][name$='].atchId']").each(function() {
			var uuid = $.trim($(this).val());
			if(uuid != "") {
				images.push({
					content: $(this).attr("rel"),
					src: "${base}ord/attach/" + $(this).val() + "?t=" + (new Date()).getTime()
				});
			}
		});
		
		if(images.length > 0) {
			$("#panImage").zyImage({
				imgList : images,		  // 数据列表
				mainImageWidth  : 600,    // 主图片区域宽度
				mainImageHeight : 330,    // 主图片区域高度
				thumImageWidth  : 110,    // 缩略图片区域宽度
				thumImageHeight : 110,    // 缩略图片区域高度
				thumbnails : false,       // 是否显示缩略图
				rotate : true,            // 是否旋转
				zoom : true,              // 是否放大和缩小
				print : false,            // 是否打印
				showNum : true,           // 是否显示总数量和索引
				del : false,              // 是否可以删除
				changeCallback : function(index, image){  // 切换回调事件
					return false;
				},
				deleteCallback : function(image){  // 删除回调事件
					return false;
				}
			});
		}
	}
	
	<%-- 更新资料 --%>
	function updateMetr(v, callback) {
		<%-- 更新评审结果 --%>
		var metr = $("[metr]:visible").first().attr("metr");
		var audt = $("#tabAudt").val();
		$("#tabAudt").val(audt.substring(0, metr - 1) + v + audt.substring(metr, audt.length));
		<%-- 保存资料信息 --%>
		ajaxSubmit(function() {
			<%-- 更新页面展示 --%>
			updateDisp(metr, v);
			
			<%-- 指定回调函数 --%>
			if(typeof callback == "function") {
				callback();
			}
		});
	}
	
	<%-- 更新页面展示 --%>
	function updateDisp(idx, v) {
		<%-- 更新评审结果图标 --%>
		updateCheckIcon(idx, v);

		<%-- 允许或禁止编辑 --%>
		toggleTabEditable(idx, v);
		
		<%-- 切换操作按钮显示 --%>
		toggleBtn(v);
	}
	
	<%-- 更新评审结果图标 --%>
	function updateCheckIcon(idx, v) {
		var i = $("#metr-" + idx);
		switch(v) {
			case "0":
			case "1":
				i.removeClass("no").removeClass("yes").hide();
				break;
			case "2":
				i.removeClass("no").addClass("yes").show();
				break;
			case "3":
				i.removeClass("yes").addClass("no").show();
				break;
		}
	}
	
	<%-- 切换标签页可编辑性 --%>
	function toggleTabEditable(idx, v) {
		var tab = $("[metr='" + idx + "']");
		toggleEditable(tab, v == "0");
		var span = tab.find(".photo").css({
			"border-color": (v == "0" ? "" : "#ddd"),
			"cursor": (v == "0" ? "pointer" : "default")
		}).each(function() {
			var img = $(this).find("img");
			var stg = $(this).find("strong");
			if(img.size() == 0 || img.attr("src") == "") {
				img.hide();
				stg.show();
			} else {
				img.show();
				stg.hide();
			}
			img.css("cursor", v == "0" ? "pointer" : "default");
		});
	}
	
	<%-- 切换操作按钮显示 --%>
	function toggleBtn(v) {
		var btns = $("[editable]");
		if(typeof v == "undefined") {
			btns.hide();
		} else if(v == "0") {
			btns.filter("[editable=${YES_VALUE}]").show();
			btns.filter("[editable=${NO_VALUE}]").hide();
		} else {
			btns.filter("[editable=${YES_VALUE}]").hide();
			btns.filter("[editable=${NO_VALUE}]").show();
		}
	}
	
	<%-- 切换标签页显示 --%>
	function toggleTab(idx) {
		if(typeof idx == "undefined") {
			<%-- 隐藏所有标签页 --%>
			$("[metr]").removeClass("block");
			<%-- 隐藏操作按钮 --%>
			toggleBtn();
		} else {
			var init = idx == "0";
			idx = init ? "1" : idx;
			var tab = $("[metr='" + idx + "']");
			if(tab.is(":visible") && !init) {
				<%-- 防止重复调用 --%>
				return;
			} else {
				<%-- 隐藏所有标签页 --%>
				$("[metr]").removeClass("block");
				<%-- 显示指定标签页 --%>
				tab.addClass("block");
				<%-- 初始化操作按钮 --%>
				toggleBtn(($("#tabAudt").val())[idx - 1]);
			}
		}
	}

	<%-- ajax提交表单 --%>
	function ajaxSubmit(callback) {
		$("#ordForm").unbind("submit").submit(function() {
			$(this).ajaxSubmit({
				url: "${base}ord/audit/upd",
				success: function(data) {
					if(typeof data.error != "undefined") {
						alert(data.error);
						return false;
					}
					
					<%-- 更新表单数据 --%>
					
					var ord = data.ord || {};
					var metr = ord.dyxOrdMetr || {}; 

					$("#token").val(ord.token);
					
					var map = ord.dyxOrdEvalMap;
					if(typeof map != "undefined") {
						for(var i in map) {
							$("[rel='" + i + "']").val(map[i].evalId);
						}
					}
					
					map = metr.dyxOrdMetrContactMap;
					if(typeof map != "undefined") {
						for(var i in map) {
							$("[name='contact[" + (Number(i) - 1) + "].contId']").val(map[i].contId);
						}
					}

					<%-- 执行回调函数 --%>
					if(typeof callback == "function") {
						callback();
					}
				},
				error: function() {
					alert("网络异常，数据提交失败！");
				}
			});
			return false;
		}).submit();
	}

	<%-- 添加紧急联系人 --%>
	function addContact() {
		var size = $("[name$='].contId']").size();
		var html = '<tr><th colspan="2" class="thead">紧急联系人' + (Number(size) + 1) + '</th></tr>'
				+ '<tr>'
					+ '<th width="30%">姓名：</th>'
					+ '<td>'
						+ '<input type="text" name="contact[' + size + '].name" class="inp" />'
						+ '<input type="hidden" name="contact[' + size + '].contId" />'
						+ '<input type="hidden" name="contact[' + size + '].dispOrd" value="' + (Number(size) + 1) + '" />'
					+ '</td>'
				+ '</tr>'
				+ '<tr>'
					+ '<th>关系：</th>'
					+ '<td>'
						+ '<select name="contact[' + size + '].rel">'
							+ '<option value="">--</option>'
							+ (function() {
								var opt = "";
								var dic = constants.dicContRel;
								for(var key in dic) {
									opt += '<option value="' + key + '">' + dic[key] + '</option>';
								}
								return opt;
							})()
						+ '</select>'
					+ '</td>'
				+ '</tr>'
				+ '<tr>'
					+ '<th>联系电话：</th>'
					+ '<td>'
						+ '<input type="text" name="contact[' + size + '].tel" class="inp" />'
					+ '</td>'
				+ '</tr>';
		$("#contTb").append(html);
	}
	
	<%-- 添加其他辅助资料 --%>
	function addAttach(obj) {
		var size = $("[name$='].atchId']").size();
		var html = '<li>'
					+ '<input type="hidden" name="attach[' + size + '].atchId" x="4" y="' + (Number(size) - 3) + '" />'
					+ '<span class="photo" onclick="uploadAttach(\'' + size + '\', $(this).find(\'img\'));">'
						+ '<img width="150" height="120" class="toggle-exclude hide" />'
					+ '</span>'
				+ '</li>';
		$(html).insertBefore($(obj).parent("li"));
	}
	
	<%-- 附件上传 --%>
	function uploadAttach(idx, img) {
		if($("#tabAudt").val()[4] != "0") {
			<%--  当前不允许编辑 --%>
			return false;
		}
		
		var inp = $("[name='attach[" + idx + "].atchId']");
		var img = $(img);
		return $("#attach").data({
			"inp": inp,
			"img": img
		}).click();
	}
</script>