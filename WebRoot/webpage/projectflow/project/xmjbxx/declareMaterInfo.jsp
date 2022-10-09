<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">

<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
  <div class="layui-tab-content">
	<e:for filterid="${info.TYPE_ID}" end="100" var="efor" dsid="1004">
	    <div 
		<c:if test="${efor.STAGE_ID==info.STAGE_ID}">class="layui-tab-item layui-show"</c:if> 
		<c:if test="${efor.STAGE_ID!=info.STAGE_ID}">class="layui-tab-item"</c:if>
		>
		<e:for filterid="${efor.STAGE_ID}" end="100" var="efor1" dsid="1005">
			<c:if test="${!empty efor1.S_ITEM_CODE}">
				<script type="text/javascript">
					$(function(){
						$.post("flowConfigDistributeController/applyItemMaterList.do",{
							itemCode:'${efor1.S_ITEM_CODE}',
							exeId:'${efor1.S_EXE_ID}',
							STAGE_ID:'${efor1.STAGE_ID}',
							PROJECT_CODE:'${efor1.PROJECT_CODE}',
							isWebsite:'2'
							//materType:'declareMater'
						}, function(responseText, status, xhr) {	
							if($("#${efor1.S_ITEM_CODE}_MATER").length==0){
								$("#${efor.STAGE_ID}declareMaterDiv").append(responseText);
								$("#${efor.STAGE_ID}declareMaterDiv").find('select').prop("disabled", "disabled");
							}
						});
					});
				</script>
			</c:if>
		</e:for>
		<div id="${efor.STAGE_ID}declareMaterDiv">
		</div>
	</div>
	</e:for>
  </div>
</div> 
