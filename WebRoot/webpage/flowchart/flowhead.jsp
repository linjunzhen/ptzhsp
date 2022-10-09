<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@page import="net.evecom.platform.flowchart.model.TacheFlow"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>

</style>
<script type="text/javascript">

</script>

<div id="titlediv" style="top: 20px;padding-top: 10px;padding-left: 10px;">
	${busInfo.busName}&nbsp;&nbsp;
    <button style="cursor: pointer;" onclick="window.parent.tempSaveFlow();" 
    	id="tempSave" disabled="disabled">暂存</button>
	<button style="cursor: pointer;" onclick="window.parent.submitAudit();" id="submitAudit">提交审批</button>
</div>
<div id="courseDiv" style="padding-top: 10px;padding-left: 160px;" >
	<c:if test="${empty tacheInfoList}">暂无数据</c:if>
	<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
			 <span style="cursor: pointer;background:silver;width:auto;" id="span_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='silver';"
			 onclick="window.parent.loadFlowInfo('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
			 <span style="">&clubs;></span> <sapn></span>
	</c:forEach>
</div>

<div id="toolbarDiv" style="float: right;position:fixed; top: 6px; right: 320px;">
    	
</div> 

