<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="net.evecom.platform.wsbs.service.ApplyMaterService"%>
<%@page import="net.evecom.platform.wsbs.service.ServiceItemService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="com.alibaba.fastjson.JSON"%>
<%@ page language="java" import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ page language="java" import="net.evecom.platform.hflow.service.MaterConfigService"%>
<%@ page language="java" import="net.evecom.platform.hflow.model.FlowNextStep"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<%
	String flowSubmitInfoJson = request.getParameter("flowSubmitInfoJson");
	String infoJson = StringEscapeUtils.unescapeHtml3(flowSubmitInfoJson);
	Map<String,Object> flowSubmitInfo = JSON.parseObject(infoJson,Map.class);
	String itemCode = (String)flowSubmitInfo.get("ITEM_CODE");
	String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
	Map<String,Object> busRecord = (Map<String,Object>)flowSubmitInfo.get("busRecord");
	
	String stageId = (String) busRecord.get("STAGE_ID");
	request.setAttribute("flowSubmitInfo", flowSubmitInfo);
%>

<script type="text/javascript">
	function setNextAuditUsers(exeid) {
		var userIds = $("[name='"+exeid+"_AUDITDISTRIBUTE_USERID']").val();
		$.dialog.open("handlerConfigController.do?selectUser&noAuth=true&allowCount=0&userIds="
				+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
				var selectUserInfo = art.dialog.data("EFLOW_SELECTHANDLERS");
				if(selectUserInfo&&selectUserInfo.handlers){
					var handlers = selectUserInfo.handlers;
					var handlerCodes = "";
					var handlerNames = "";
					var handlerJson = JSON2.stringify(selectUserInfo.handlers);
					for(var index in handlers){
						if(index>0){
							handlerCodes+=",";
						}
						handlerCodes+=handlers[index].nextStepAssignerCode;
						if(index>0){
							handlerNames+=",";
						}
						handlerNames+=handlers[index].nextStepAssignerName;
					}
					$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").val(handlerNames);	
					$("[name='"+exeid+"_AUDITDISTRIBUTE_USERACCOUNT']").val(handlerCodes);
					$("[name='"+exeid+"_AUDITDISTRIBUTE_HANDLERS']").val(handlerJson);	
					art.dialog.removeData("EFLOW_SELECTHANDLERS");
				}
			}
		}, false);
	}  
	function auditDistributeResults(val,exeid){
		if(""!=val && val==0){	
			$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").val("");	
			$("[name='"+exeid+"_AUDITDISTRIBUTE_USERACCOUNT']").val("");
			$("[name='"+exeid+"_AUDITDISTRIBUTE_HANDLERS']").val("");	
			$("#"+exeid+"_SZ").hide();
			$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").hide();
			$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").removeClass(" validate[required]");
			
			var itemName = $("[name='"+exeid+"_ITEM_NAME']").val();
			var html = '<div id="'+exeid+'_YJNR" style="margin-top: 10px;">';
			html +='<div class="syj-title1" style="height:30px;padding-top:5px;">';
			html +='<span>'+itemName+'，预审不通过</span>';
			html +='</div>';
			html +='<table cellpadding="0" cellspacing="0" class="syj-table2" style="background: #fff;">';
			html +='<tr>';
			html +='<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>意见内容</th>';
			html +='<td colspan="3">';
			html +='<textarea class="eve-textarea validate[required,maxSize[1500]]" rows="3" cols="200" ';
			html +='name="'+exeid+'_HANDLE_OPINION" style="width:81.6%;height:75px;"  placeholder="请输入预审不通过意见"></textarea>';
			html +='</td>';
			html +='</tr>';
			html +='</table>';
			html +='</div>';
			$("#resultDiv").append(html);
			$.post("flowConfigDistributeController/getMatersList.do",{
				exeId:exeid,
				itemCode:$("[name='"+exeid+"_ITEM_CODE']").val()	
			}, function(responseText, status, xhr) {	
				if(responseText){				
					$("[name='"+exeid+"_applyMatersJson']").val(responseText);		
					var responseJson = JSON2.parse(responseText);
					var mhtml = '<table cellpadding="0" id="'+exeid+'_BJCLLB" cellspacing="1" class="syj-table2 tmargin2" style="margin-top: 15px;">';
					mhtml += '<tr>';
					mhtml += '<th style="width:300px">材料名称</th>';
					mhtml += '<th style="width:100px">是否通过</th>';
					mhtml += '<th style="width:200px">不通过意见</th>';
					mhtml += '</tr>';
					for(var index in responseJson){
						mhtml += '<tr>';
						mhtml += '<td style="text-align: center;">';
						mhtml += '<input type="hidden" id="mc_'+responseJson[index].MATER_CODE+'" value="'+responseJson[index].MATER_CODE+'" /> ';
						mhtml += '<span id="mn_'+responseJson[index].MATER_CODE+'">'+responseJson[index].MATER_NAME+'</span>';
						mhtml += '</td>';
						mhtml += '<td>';
						mhtml += '<input type="radio" name="SFBJ_'+responseJson[index].MATER_CODE+'" value="1" />是';
						mhtml += '<input type="radio" name="SFBJ_'+responseJson[index].MATER_CODE+'" value="-1" checked="checked" />否';
						mhtml += '</td>';
						mhtml += '<td>';
						mhtml += '<input type="text" id="bjyq_'+responseJson[index].MATER_CODE+'" class="eve-input" maxlength="1000" style="width:95%;" />';
						mhtml += '</td>';
						mhtml += '</tr>';
					}
					mhtml += '</table>';
					$("#resultDiv").append(mhtml);
				}
			});
		}else if(""!=val &&  val==1){			
			$("#"+exeid+"_SZ").show();
			$("#"+exeid+"_YJNR").remove();
			$("#"+exeid+"_BJCLLB").remove();
			$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").show();
			$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").addClass(" validate[required]");

			$.post("flowConfigDistributeController/getNextsteps.do",{
				exeId:exeid
			}, function(responseText, status, xhr) {	
				var responseJson = JSON2.parse(responseText);
				if(null != responseJson.nextTrans && responseJson.nextTrans.length>0 ){						
					$("[name='"+exeid+"_AUDITDISTRIBUTE_FLOWNODENAME']").val(responseJson.nextTrans[0].flowNodeName);	
					if(null != responseJson.nextTrans[0].handlers && responseJson.nextTrans[0].handlers.length>0){
						var handlers = responseJson.nextTrans[0].handlers;
						var handlerCodes = "";
						var handlerNames = "";
						var handlerJson = JSON2.stringify(handlers);
						for(var index in handlers){
							if(index>0){
								handlerCodes+=",";
							}
							handlerCodes+=handlers[index].nextStepAssignerCode;
							if(index>0){
								handlerNames+=",";
							}
							handlerNames+=handlers[index].nextStepAssignerName;
						}
						$("[name='"+exeid+"_AUDITDISTRIBUTE_USERNAME']").val(handlerNames);	
						$("[name='"+exeid+"_AUDITDISTRIBUTE_USERACCOUNT']").val(handlerCodes);
						$("[name='"+exeid+"_AUDITDISTRIBUTE_HANDLERS']").val(handlerJson);						
					}
				}	
			});
		}
	}
</script>


<div id="distributeDiv" style="margin: auto;width: 99%;">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="3">事项分发处理</th>
	</tr>
	</table>
	<table cellpadding="0" cellspacing="1" class="syj-table2 tmargin2" id="itemAuditDistributeList" style="background: #fff;">
		<tr>
			<th style="width:50px">序号</th>
			<th style="width:350px">事项名称</th>
			<th style="width:190px">预审结果</th>
			<th style="width:200px">下一步审批人员</th>
			<th style="width:80px">操作</th>
		</tr>
		<e:for paras="${flowSubmitInfo.EFLOW_EXEID}:${flowSubmitInfo.busRecord.STAGE_ID}:${sessionScope.curLoginUser.username}:100:1"
			filterid="${flowSubmitInfo.busRecord.STAGE_ID}" end="100" var="efor" dsid="1002">
			<tr>
				<td style="text-align: center;" >
					${efor.rownum_}
					<input type="hidden" name="${efor.S_EXE_ID}_AUDITDISTRIBUTE_EXEID" value="${efor.S_EXE_ID}">
					<input type="hidden" name="${efor.S_EXE_ID}_AUDITDISTRIBUTE_USERACCOUNT">
					<input type="hidden" name="${efor.S_EXE_ID}_AUDITDISTRIBUTE_HANDLERS">	
					<input type="hidden" name="${efor.S_EXE_ID}_AUDITDISTRIBUTE_FLOWNODENAME">	
					<input type="hidden" name="${efor.S_EXE_ID}_ITEM_CODE" value="${efor.ITEM_CODE}">	
					<input type="hidden" name="${efor.S_EXE_ID}_ITEM_NAME" value="${efor.ITEM_NAME}">	
					<input type="hidden" name="${efor.S_EXE_ID}_applyMatersJson">	
				</td>
				<td style="text-align: center;">
					${efor.ITEM_NAME}
				</td>
				<td style="text-align: center;">					
					<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="ysjg"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="auditDistributeResults(this.value,'${efor.S_EXE_ID}')" 
						defaultEmptyText="请选择预审结果" name="${efor.S_EXE_ID}_AUDITDISTRIBUTE_RESULTS" id="${efor.S_EXE_ID}_AUDITDISTRIBUTE_RESULTS">
					</eve:eveselect>
				</td>
				<td style="text-align: center;">
					<input type="text" style="width:90%;float:left;display:none;" readonly="readonly" class="eve-input" name="${efor.S_EXE_ID}_AUDITDISTRIBUTE_USERNAME" >
				</td>
				<td style="text-align: center;">
					<input id="${efor.S_EXE_ID}_SZ" type="button" value="设置" class="eve-button" onclick="setNextAuditUsers('${efor.S_EXE_ID}')" style="margin-top: 3px;margin-left: 5px;display:none;">
				</td>
			</tr>
		</e:for>
	<table>
	<div id="resultDiv" style="background: #fff;padding-bottom:20px;">
	</div>
</div>