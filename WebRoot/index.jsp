<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="sh.ricky.core.constant.GlobalConstants"%>
<%@ page import="sh.ricky.core.constant.ConfigConstants"%>
<%@ taglib uri="tools" prefix="t"%>
<%
    boolean debug = GlobalConstants.YES_VALUE.equals(ConfigConstants.getInstance().get("debug.on"));
%>
<jsp:forward page='<%=debug ? "backdoor.jsp" : "passport/login"%>' />
