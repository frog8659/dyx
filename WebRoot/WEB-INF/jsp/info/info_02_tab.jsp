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
				协议审核
				<input type="hidden" name="metr.tabAudt" id="tabAudt" value="${empty metr.tabAudt ? '1111' : metr.tabAudt}" />
			</th>
		</tr>
	</thead>
</table>

<div>
	<ul class="tabs clear">
		<li onclick="return false;" class="current" metr="1">协议资料<i id="metr-1"></i></li>
		<li onclick="return false;" metr="2">合格证<i id="metr-2"></i></li>
		<li onclick="return false;" metr="3">购买凭证<i id="metr-3"></i></li>
		<li onclick="return false;" metr="4">商品合影<i id="metr-4"></i></li>
	</ul>
	
	<div class="docBox">
		<div class="info">
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<c:if test="${empty metr.metrNo and not empty flowList}">
						<tr>
							<th width="30%">分期申请书编号：</th>
							<td><input type="text" name="metr.metrNo" id="metrNo" value="${fn:trim(metr.metrNo)}" class="inp important" /></td>
						</tr>
					</c:if>
					<tr>
						<th width="30%">姓名：</th>
						<td>${fn:trim(metr.aplName)}</td>
					</tr>
					<tr>
						<th>身份证号：</th>
						<td>${fn:trim(metr.aplIdc)}</td>
					</tr>
					<tr>
						<th>手机号码：</th>
						<td>${fn:trim(metr.aplMob)}</td>
					</tr>
					<tr>
						<th>发卡银行：</th>
						<td>${dicBank[metr.aplBank]}</td>
					</tr>
					<tr>
						<th>银行卡号：</th>
						<td>${fn:trim(metr.aplBankCard)}</td>
					</tr>
					<tr>
						<th>银行预留手机号：</th>
						<td>${fn:trim(metr.aplBankMob)}</td>
					</tr>
					<tr>
						<th>身份证有效期：</th>
						<td><fmt:formatDate value='${metr.aplIdcExp}' pattern='yyyy年M月d日' /></td>
					</tr>
					<tr>
						<th>年龄：</th>
						<td>${fn:trim(metr.aplAge)}</td>
					</tr>
				</tbody>
			</table>
			<br />
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<tr>
						<th width="30%">性别：</th>
						<td>${dicSex[metr.aplSex]}</td>
					</tr>
					<tr>
						<th>教育程度：</th>
						<td>${dicEdu[metr.aplEdu]}</td>
					</tr>
					<tr>
						<th>婚姻状态：</th>
						<td>${dicMari[metr.aplMari]}</td>
					</tr>
					<tr>
						<th>现居住地址：</th>
						<td>${metr.aplProv}
							${metr.aplProv eq metr.aplCity ? "" : metr.aplCity}
							${metr.aplDist}
							${fn:trim(metr.aplAddr)}
						</td>
					</tr>
					<tr>
						<th>现居住宅状况：</th>
						<td>${dicHomeStat[metr.aplHomeStat]}</td>
					</tr>
					<tr>
						<th>本地居住年限：</th>
						<td>${fn:trim(metr.aplResdPeriod)}</td>
					</tr>
					<tr>
						<th>电子邮箱：</th>
						<td>${fn:trim(metr.aplMail)}</td>
					</tr>
					<tr>
						<th>固定电话：</th>
						<td>${fn:trim(metr.aplTel)}</td>
					</tr>
				</tbody>
			</table>
			<br />
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody>
					<tr>
						<th width="30%">受雇类型：</th>
						<td>${dicEmpType[metr.aplEmpType]}</td>
					</tr>
					<tr>
						<th>现单位名称：</th>
						<td>${fn:trim(metr.aplCompany)}</td>
					</tr>
					<tr>
						<th>单位性质：</th>
						<td>${dicCompNatu[metr.aplCompNatu]}</td>
					</tr>
					<tr>
						<th>职务级别：</th>
						<td>${dicJobTitle[metr.aplJobTitle]}</td>
					</tr>
					<tr>
						<th>税后月收入：</th>
						<td>${metr.aplJobIncome}</td>
					</tr>
					<tr>
						<th>现单位工作年限：</th>
						<td>${fn:trim(metr.aplJobYear)}</td>
					</tr>
					<tr>
						<th>单位电话：</th>
						<td>${fn:trim(metr.aplCompTel)}</td>
					</tr>
					<tr>
						<th>单位地址：</th>
						<td>${fn:trim(metr.aplCompAddr)}</td>
					</tr>
					<tr>
						<th>工作性质：</th>
						<td>${dicJobSpec[metr.aplJobSpec]}</td>
					</tr>
					<tr>
						<th>单位规模：</th>
						<td>${dicCompSize[metr.aplCompSize]}</td>
					</tr>
				</tbody>
			</table>
			<br />
			<table cellpadding="0" cellspacing="0" class="listTb infoList nwTb">
				<tbody id="contTb">
					<c:forEach var="cont" items="${metr.dyxOrdMetrContactSet}" varStatus="stat">
						<tr><th colspan="2" class="thead">紧急联系人${stat.count}</th></tr>
						<tr>
							<th width="30%">姓名：</th>
							<td>${fn:trim(cont.name)}</td>
						</tr>
						<tr>
							<th>关系：</th>
							<td>${dicContRel[cont.rel]}</td>
						</tr>
						<tr>
							<th>联系电话：</th>
							<td>${fn:trim(cont.tel)}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="btnBox1">
			<c:if test="${not empty flowList}">
				<input type="button" class="btn grayBtn" value="通过" onclick="updateMetr('2')" />
				<input type="button" class="btn grayBtn" value="拒绝" onclick="updateMetr('3')" />
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
		
		<%-- 初始化页面内容展示 --%>
		deferredCall(function() {
			var audt = $("#tabAudt").val();
			for(var i in audt) {
				<%-- 初始化评审结果图标 --%>
				updateCheckIcon(Number(i) + 1, audt[i]);
			}
		});

		<%-- 初始化标签页切换函数 --%>
		$(".tabs li").click(function() {
			$(this).parent().find("li").removeClass("current"); 
      		$(this).addClass("current").siblings().removeClass();
		});

		<%-- 初始化信息展示 --%>
		$(".info").css({
			"max-height": "330px",
			"overflow": "auto"
		}).show();
		
		<%-- 初始化附件图片展示 --%>
		var images = new Array();
		<c:forEach var="attach" items="${metr.dyxOrdMetrAttachSet}">
			images.push({
				content: "${attach.atchTitle}",
				src: "${base}ord/attach/${attach.atchId}?t=" + (new Date()).getTime()
			});
		</c:forEach>
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
	});

	<%-- 更新资料 --%>
	function updateMetr(v) {
		var metr = $(".tabs .current").attr("metr");
		
		<%-- 更新评审结果 --%>
		var _updateAudt = function() {
			var audt = $("#tabAudt").val();
			$("#tabAudt").val(audt.substring(0, metr - 1) + v + audt.substring(metr, audt.length));
		};
		
		<%-- 更新评审结果图标 --%>
		updateCheckIcon(metr, v);
		<%-- 更新评审结果 --%>
		_updateAudt();
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
</script>