<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="sh.ricky.core.constant.DicConstants"%>
<%@ page import="sh.ricky.core.constant.GlobalConstants"%>
<%@ page import="sh.ricky.dyx.constant.OrderConstants"%>
<%@ page import="sh.ricky.dyx.constant.UserConstants"%>
<%@ page import="sh.ricky.dyx.constant.InstConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="tools" prefix="t" %>

<c:set var="YES_VALUE" value="<%=GlobalConstants.YES_VALUE%>" scope="request" />
<c:set var="NO_VALUE" value="<%=GlobalConstants.NO_VALUE%>" scope="request" />

<%-- 根路径信息 --%>
<c:set var="base" value="<%=session.getAttribute(GlobalConstants.SESSION_BASE)%>" scope="request" />
<%-- 用户会话信息 --%>
<c:set var="user" value="<%=session.getAttribute(GlobalConstants.SESSION_USER)%>" scope="request" />
<%-- 会话信息索引 --%>
<c:set var="SESSION_TOKEN" value="<%=GlobalConstants.SESSION_TOKEN%>" scope="request" />

<%-- 订单类型 --%>
<c:set var="ORD_TYPE_ZZBL" value="<%=OrderConstants.ORD_TYPE_ZZBL%>" scope="request" />
<c:set var="ORD_TYPE_KFDB" value="<%=OrderConstants.ORD_TYPE_KFDB%>" scope="request" />

<%-- 分期业务状态 --%>
<c:set var="INST_STAT_YQD" value="<%=InstConstants.INST_STAT_YQD%>" scope="request" />
<c:set var="INST_STAT_YDJ" value="<%=InstConstants.INST_STAT_YDJ%>" scope="request" />

<%-- 权限编号 --%>
<c:set var="USER_AUTH_GLSYYHCZRZ" value="<%=UserConstants.USER_AUTH_GLSYYHCZRZ%>" scope="request" />

<%-- 字典：角色字典 --%>
<c:set var="dicRole" value="<%=DicConstants.getInstance().getDicRole()%>" scope="request" />
<%-- 字典：流程操作字典 --%>
<c:set var="dicActn" value="<%=DicConstants.getInstance().getDicActn()%>" scope="request" />
<%-- 字典：流程环节字典 --%>
<c:set var="dicSeg" value="<%=DicConstants.getInstance().getDicSeg()%>" scope="request" />
<%-- 字典：流程环节状态字典 --%>
<c:set var="dicSegStat" value="<%=DicConstants.getInstance().getDicSegStat()%>" scope="request" />
<%-- 字典：审核状态字典 --%>
<c:set var="dicAudtStat" value="<%=DicConstants.getInstance().getDicAudtStat()%>" scope="request" />
<%-- 字典：分期名称字典 --%>
<c:set var="dicInstName" value="<%=DicConstants.getInstance().getDicInstName()%>" scope="request" />
<%-- 字典：分期业务状态字典 --%>
<c:set var="dicInstStat" value="<%=DicConstants.getInstance().getDicInstStat()%>" scope="request" />
<%-- 字典：分期还款方式字典 --%>
<c:set var="dicRepMethod" value="<%=DicConstants.getInstance().getDicRepMethod()%>" scope="request" />
<%-- 字典：性别字典 --%>
<c:set var="dicSex" value="<%=DicConstants.getInstance().getDicSex()%>" scope="request" />
<%-- 字典：行政区划字典 --%>
<c:set var="dicRegion" value="<%=DicConstants.getInstance().getDicRegion()%>" scope="request" />
<%-- 字典：银行字典 --%>
<c:set var="dicBank" value="<%=DicConstants.getInstance().getDicBank()%>" scope="request" />
<%-- 字典：教育程度字典 --%>
<c:set var="dicEdu" value="<%=DicConstants.getInstance().getDicEdu()%>" scope="request" />
<%-- 字典：婚姻状况字典 --%>
<c:set var="dicMari" value="<%=DicConstants.getInstance().getDicMari()%>" scope="request" />
<%-- 字典：住宅状况字典 --%>
<c:set var="dicHomeStat" value="<%=DicConstants.getInstance().getDicHomeStat()%>" scope="request" />
<%-- 字典：受雇类型字典--%>
<c:set var="dicEmpType" value="<%=DicConstants.getInstance().getDicEmpType()%>" scope="request" />
<%-- 字典：工作性质字典 --%>
<c:set var="dicJobSpec" value="<%=DicConstants.getInstance().getDicJobSpec()%>" scope="request" />
<%-- 字典：单位规模字典 --%>
<c:set var="dicCompSize" value="<%=DicConstants.getInstance().getDicCompSize()%>" scope="request" />
<%-- 字典：单位性质字典 --%>
<c:set var="dicCompNatu" value="<%=DicConstants.getInstance().getDicCompNatu()%>" scope="request" />
<%-- 字典：职务级别字典 --%>
<c:set var="dicJobTitle" value="<%=DicConstants.getInstance().getDicJobTitle()%>" scope="request" />
<%-- 字典：联系人关系字典 --%>
<c:set var="dicContRel" value="<%=DicConstants.getInstance().getDicContRel()%>" scope="request" />
<%-- 字典：操作员字典 --%>
<c:set var="dicOpUser" value="<%=DicConstants.getInstance().getDicOpUser()%>" scope="request" />

<script type="text/javascript">
	// 常量类
	var constants = constants || {};
	
	// 是或否
	constants.yes = "${YES_VALUE}";
	constants.no = "${NO_VALUE}";
	
	// 配置常量
	constants.config = {
		// 默认错误描述
		"err.default": "${t:config('err.default')}"
	};

	// 字典：联系人关系
	constants.dicContRel = new Object();
	<c:forEach var="dic" items="${dicContRel}">
		constants.dicContRel["${dic.key}"] = "${dic.value}";
	</c:forEach>
	
	// 全局变量类
	var global = global || {};
	
	// 当前服务器时间
	global.current = '<fmt:formatDate value="${t:datetime()}" pattern="yyyy-MM-dd"/>';
	
	// 当前令牌对象
	global.token = {
		name: "${SESSION_TOKEN}",
		code: "${sessionScope[SESSION_TOKEN]}",
		data: {
			"${SESSION_TOKEN}": "${sessionScope[SESSION_TOKEN]}"
		}
	};
</script>