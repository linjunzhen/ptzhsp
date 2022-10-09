<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String isWebsite = request.getParameter("isWebsite");
	request.setAttribute("isWebsite", isWebsite);
%>
<script type="text/javascript">
	$(function(){
		$("input[name='S_ITEM_CODE']").click(function(){
			//获取值
			getCheckBoxValue();
		});
	});
	/**
	*
	**/
	function addApplyItemMaterList(itemCode){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			$.post("flowConfigDistributeController/applyItemMaterList.do",{
				itemCode:itemCode,
				exeId:flowSubmitObj.EFLOW_EXEID,
				STAGE_ID:$("[name='STAGE_ID']").val(),
				PROJECT_CODE:$("[name='PROJECT_CODE']").val(),
				isWebsite:'${isWebsite}'
			}, function(responseText, status, xhr) {	
				if($("#"+itemCode+"_MATER").length==0){
					$("#itemMaterDiv").prepend(responseText);
				}
			});
		}
	}
	function removeApplyItemMaterList(itemCode){		
		$("#"+itemCode+"_MATER").remove();
	}
	 
	function getCheckBoxValue() {
		//获取checkBox的元素
		var sItemCode = $("input[name='S_ITEM_CODE']");
		var data = '';
		sItemCode.each(function () {
			//获取当前元素的勾选状态
			if ($(this).is(':checked')) {
				data = data + $(this).val() + ",";
				addApplyItemMaterList($(this).val());
			} else{				
				removeApplyItemMaterList($(this).val());
			}
		});
		//去最后的点
		data = data.substring(0, data.length - 1);
		return data;
	}
    //
	function verifyCheckBoxValue() {
		//获取checkBox的元素
		var sItemCode = $("input[name='S_ITEM_CODE']");
		var isok = true;
		sItemCode.each(function () {
			var datakey = $(this).attr("datakey");
			//获取当前元素的勾选状态
			if (!$(this).is(':checked') && datakey==1) {
				isok = false;
			} 
		});
		return isok;
	}     
	function checkItemMaterHandleType(val,currentTime){
		var index = 1;
		var tds = $("#"+currentTime+"materCheckList").find(".busClass");
		tds.each(function(){
			var tabtext = $(this).html();
			if(tabtext.indexOf(val)=='-1'){
				$(this).parent().attr("style","display:none");
			}else{
				$(this).parent().attr("style","");
				$(this).parent().find(".materIndex").html(index);
				index++;
			}
		});
	}
</script>

<div class="syj-title1" style="height:30px;">
	<span>事项列表</span>
</div>
<table cellpadding="0" cellspacing="1" class="syj-table2 tmargin2" id="itemCheckList">
	<tr>
		<th style="width:5%">选择</th>
		<th style="width:15%">事项编码</th>
		<th>事项名称</th>		
		<c:if test="${!empty busRecord.STAGE_ID}"><th style="width:30%;">汇总意见</th></c:if>
		<th style="width:10%">服务</th>
	</tr>
	<c:if test="${!empty STAGE_ID}">
		<e:for filterid="${STAGE_ID}" end="100" var="efor" dsid="1000">
			<tr>
				<td style="text-align: center;" >
					<input type="checkbox" name="S_ITEM_CODE" class="checkbox" value="${efor.ITEM_CODE}" dataKey="${efor.IS_KEY_ITEM}" 
					<c:if test="${efor.IS_KEY_ITEM=='1'}">checked</c:if>
					>
				</td>
				<td style="text-align: center;">${efor.ITEM_CODE}</td>
				<td style="text-align: center;">
					${efor.ITEM_NAME} 
					<c:if test="${efor.IS_KEY_ITEM=='1'}">
						<font style="float: right;color:red">*必须办理</font>						
						<script type="text/javascript">
							$(function(){
								$.post("flowConfigDistributeController/applyItemMaterList.do",{
									itemCode:'${efor.ITEM_CODE}',
									exeId:'${efor.S_EXE_ID}',
									STAGE_ID:$("[name='STAGE_ID']").val(),
									PROJECT_CODE:$("[name='PROJECT_CODE']").val(),
									isWebsite:'${isWebsite}'
								}, function(responseText, status, xhr) {	
									if($("#${efor.ITEM_CODE}_MATER").length==0){
										$("#itemMaterDiv").append(responseText);
									}
								});
							});
						</script>
					</c:if>
				</td>
				<td style="text-align: center;">
					<a class="eflowbutton" href="<%=path%>/serviceItemController/bsznDetail.do?itemCode=${efor.ITEM_CODE}" target="_blank">办事指南</a>
				</td>
			</tr>
		</e:for>
	</c:if>
	<c:if test="${!empty busRecord.STAGE_ID}">
		<e:for paras="${exeId}:${busRecord.STAGE_ID}:100:1" filterid="${busRecord.STAGE_ID}" end="100" var="efor" dsid="1001">
			<tr>
				<td style="text-align: center;" >
					<input type="checkbox" name="S_ITEM_CODE" class="checkbox" value="${efor.ITEM_CODE}" dataKey="${efor.IS_KEY_ITEM}" 
					<c:if test="${!empty efor.S_ITEM_CODE}"> checked</c:if>>
					<c:if test="${!empty efor.S_ITEM_CODE}">
					<script type="text/javascript">
						$(function(){
							$.post("flowConfigDistributeController/applyItemMaterList.do",{
								itemCode:'${efor.S_ITEM_CODE}',
								exeId:'${efor.S_EXE_ID}',
								STAGE_ID:$("[name='STAGE_ID']").val(),
								PROJECT_CODE:$("[name='PROJECT_CODE']").val(),
								isWebsite:'${isWebsite}'
							}, function(responseText, status, xhr) {	
								if($("#${efor.S_ITEM_CODE}_MATER").length==0){
									$("#itemMaterDiv").append(responseText);
									$("#itemMaterDiv").find('select').prop("disabled", "disabled");
								}
							});
						});
					</script>
					</c:if>
				</td>
				<td style="text-align: center;">${efor.ITEM_CODE}</td>
				<td style="text-align: center;">
					${efor.ITEM_NAME} 
					<c:if test="${efor.IS_KEY_ITEM=='1'}">
						<font style="float: right;color:red">*必须办理</font>
					</c:if>
				</td>
				<td style="text-align: center;">${efor.HANDLE_OPINION}</td>
				<td style="text-align: center;">
					<c:if test="${!empty efor.S_ITEM_CODE}">
						<a class="eflowbutton" href="<%=path%>/executionController.do?goDetail&exeId=${efor.S_EXE_ID}" target="_blank">办件详情</a>
					</c:if>
					<c:if test="${empty efor.S_ITEM_CODE}">
					<a class="eflowbutton" href="<%=path%>/serviceItemController/bsznDetail.do?itemCode=${efor.ITEM_CODE}" target="_blank">办事指南</a>
					</c:if>
				</td>
			</tr>
		</e:for>
	</c:if>
<table>
<div id="itemMaterDiv">
</div>
