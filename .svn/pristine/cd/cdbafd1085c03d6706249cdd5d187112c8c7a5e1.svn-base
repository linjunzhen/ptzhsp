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
	
	
<!-- <div class="syj-title1" style="height:30px;">
	<span>审批结果信息</span>
</div> -->
<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
  <ul class="layui-tab-title">
  
	<e:for filterid="${info.TYPE_ID}" end="100" var="efor" dsid="1004">
	<li <c:if test="${efor.STAGE_ID==info.STAGE_ID}">class="layui-this"</c:if> >${efor.NAME}</li>
	</e:for>
  </ul>
  <div class="layui-tab-content">
	<e:for filterid="${info.TYPE_ID}" end="100" var="efor" dsid="1004">
    <div 
	<c:if test="${efor.STAGE_ID==info.STAGE_ID}">class="layui-tab-item layui-show"</c:if> 
	<c:if test="${efor.STAGE_ID!=info.STAGE_ID}">class="layui-tab-item"</c:if>
	>
	
		<div class="layui-form">
		  <table class="layui-table">
			<colgroup>
			  <col width="200">
			  <col width="200">
			  <col>
			  <col width="200">
			  <col width="120">
			</colgroup>
			<thead>
			  <tr>
				<th>办件编号</th>
				<th>审批事项</th>
				<th>事项编码</th>
				<th>受理时间</th>
				<th>办理结果</th>
			  </tr> 
			</thead>
			<tbody>
			<e:for filterid="${efor.STAGE_ID}" end="100" var="efor1" dsid="1005">
			
				<c:if test="${efor1.ROWNUM_=='1'}">
				<script type="text/javascript">
					$(function(){
						$.post("flowConfigDistributeController/applyItemMaterList.do",{
							itemCode:'${efor1.M_ITEM_CODE}',
							exeId:'${efor1.M_EXE_ID}',
							STAGE_ID:'${efor1.STAGE_ID}',
							PROJECT_CODE:'${efor1.PROJECT_CODE}',
							isWebsite:'2'
						}, function(responseText, status, xhr) {	
							if($("#${efor1.S_ITEM_CODE}_MATER").length==0){
								$("#${efor.STAGE_ID}itemMaterDiv").prepend(responseText);
								$("#${efor.STAGE_ID}itemMaterDiv").find('select').prop("disabled", "disabled");
							}
						});
					});
				</script>
				</c:if>
			  <tr>	
				<td>${efor1.EXE_ID}</td>
				<td>${efor1.S_ITEM_CODE}
				
					<c:if test="${!empty efor1.S_ITEM_CODE}">
					<script type="text/javascript">
						$(function(){
							$.post("flowConfigDistributeController/applyItemMaterList.do",{
								itemCode:'${efor1.S_ITEM_CODE}',
								exeId:'${efor1.S_EXE_ID}',
								STAGE_ID:'${efor1.STAGE_ID}',
								PROJECT_CODE:'${efor1.PROJECT_CODE}',
								isWebsite:'2'
							}, function(responseText, status, xhr) {	
								if($("#${efor1.S_ITEM_CODE}_MATER").length==0){
									$("#${efor.STAGE_ID}itemMaterDiv").append(responseText);
									$("#${efor.STAGE_ID}itemMaterDiv").find('select').prop("disabled", "disabled");
								}
							});
						});
					</script>
					</c:if>
				</td>
				<td><a style="text-decoration:underline;color:green;" href="<%=path%>/executionController.do?goDetail&exeId=${efor1.EXE_ID}" target="_blank">${efor1.ITEM_NAME}</a></td>				
				<td>${efor1.SLSJ}</td>
				<td>
					<c:if test="${efor1.RUN_STATUS=='0'}"><font color='#ff4b4b'>草稿</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='1'}"><font color='#0368ff'>正在办理</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='2'}"><font >已办结(正常结束)</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='3'}"><font >已办结(审核通过)</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='4'}"><font >已办结(审核不通过)</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='5'}"><font color='#ff4b4b'>已办结(退回)</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='6'}"><font color='black'>强制结束</font></c:if>
					<c:if test="${efor1.RUN_STATUS=='7'}"><font color='#ff4b4b'>预审不通过</font></c:if>
				</td>
			  </tr>
			</e:for>
			</tbody>
		  </table>
		</div>
		<%-- <div id="${efor.STAGE_ID}itemMaterDiv">
		</div> --%>
	</div>
	</e:for>
  </div>
</div> 
