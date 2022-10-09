<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">备案信息</th>
	</tr>
</table>
<%--开始引入董事信息界面 --%>
<jsp:include page="./T_COMMERCIAL_OLD_DIRECTOR.jsp" />
<div id="dsxx" >
<jsp:include page="./T_COMMERCIAL_DIRECTOR.jsp" />
</div>
<%--结束引入董事信息界面 --%>
<div class="tab_height"></div>
<%--开始引入监事信息界面 --%>
<jsp:include page="./T_COMMERCIAL_OLD_SUPERVISOR.jsp" />
<div id="jsxx" >
<jsp:include page="./T_COMMERCIAL_SUPERVISOR.jsp" />
</div>
<%--结束引入监事信息界面 --%>
<div class="tab_height"></div>
<%--开始引入经理信息界面 --%>
<jsp:include page="./T_COMMERCIAL_OLD_MANAGER.jsp" />
<div id="jlxx" >
<jsp:include page="./T_COMMERCIAL_MANAGER.jsp" />
</div>
<%--结束引入经理信息界面 --%>