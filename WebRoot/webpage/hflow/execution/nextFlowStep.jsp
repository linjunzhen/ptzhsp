<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">

var selectedNodeHandlers = {};

function selectNextHandler(flowNodeName,selectorUrl,selectorHeight,selectorWeight,selectorVars,selectorRule,selectorName){
	//获取提交的变量对象
	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
	//转换成对象
	var flowVars = JSON2.parse(flowSubmitInfoJson);
	flowVars.EFLOW_SELECTORVARS = selectorVars;
	flowVars.EFLOW_SELECTORRULE = selectorRule;
	flowVars.EFLOW_FLOWNODENAME = flowNodeName;
	flowSubmitInfoJson = JSON2.stringify(flowVars);
	$.post("executionController.do?storeFlowSubmitInfoJson",{
		flowSubmitInfoJson:flowSubmitInfoJson
	}, function(responseText, status, xhr) {
		// 存储
		$.dialog.open(selectorUrl, {
			title : selectorName,
	        width:selectorWeight+"px",
	        lock: true,
	        resize:false,
	        height:selectorHeight+"px",
	        close: function () {
				var selectUserInfo = art.dialog.data("EFLOW_SELECTHANDLERS");
				if(selectUserInfo&&selectUserInfo.handlers){
					//先判断下一步环节信息对象是否为空
					if(!flowVars.EFLOW_NEXTSTEPS){
						flowVars.EFLOW_NEXTSTEPS = {};
					}
					//将已经选择的下一环节审核人加入到提交变量里面
					flowVars.EFLOW_NEXTSTEPS[flowNodeName] = selectUserInfo.handlers;
					$("#flowSubmitInfoJson").val(JSON2.stringify(flowVars));
					var handlers = selectUserInfo.handlers;
					var handlerNames = "";
					var handlerJson = JSON2.stringify(selectUserInfo.handlers);
					var publicDocDivLength = $("#publicDocDiv").length;
					var PUBLIC_DOC_JSON = $("input[name='PUBLIC_DOC_JSON']").val();
					if(publicDocDivLength!=0&&PUBLIC_DOC_JSON&&PUBLIC_DOC_JSON!=""){
						$("#publicDocDiv").html("");
						refreshPublicDoc(handlerJson,PUBLIC_DOC_JSON);
					}
					for(var index in handlers){
						if(index>0){
							handlerNames+=",";
						}
						handlerNames+=handlers[index].nextStepAssignerName;
					}
					selectedNodeHandlers[flowNodeName] = handlerNames;
					$("input[name='"+flowNodeName+"']").val(handlerNames);
					art.dialog.removeData("EFLOW_SELECTHANDLERS");
				}
			}
		}, false);
	});
	
}

function refreshPublicDoc(handlerJson,PUBLIC_DOC_JSON){
	var publicDocRule = $("#publicDocRule").val();
	$.post("applyMaterController.do?refreshPublicDoc",{
		handlerJson:handlerJson,
		publicDocRule:publicDocRule,
		PUBLIC_DOC_JSON:PUBLIC_DOC_JSON,
		IS_MARTER:${nodeConfig.IS_MARTER}
	}, function(responseText, status, xhr) {
		$("#publicDocDiv").html(responseText);
		//initUploadAN();
		//设置审核材料为空
		$("#FLOW_USER_MATER_ID").val("");
	});
}

function changeTrans2(select){
	var flowNodeName = $(select).children("option:selected").val();
	if(flowNodeName==""){
		return;
	}
	var flowHandlerNames = $(select).children("option:selected").attr("flowHandlerNames");
	var selectorUrl = $(select).children("option:selected").attr("selectorUrl");
	var selectorHeight = $(select).children("option:selected").attr("selectorHeight");
	var selectorWeight = $(select).children("option:selected").attr("selectorWeight");
	var selectorVars = $(select).children("option:selected").attr("selectorVars");
	var selectorRule = $(select).children("option:selected").attr("selectorRule");
	var selectorName = $(select).children("option:selected").attr("selectorName");
	var isEndNode = $(select).children("option:selected").attr("isEndNode");
	if(isEndNode!=null&&isEndNode=="true"){
		$("#flowHandlerNames").attr("style","display:none;");
		$("#flowHandlerNames").attr("class","eve-input");
	}else{
		var style = $("#flowHandlerNames").attr("style");
		if(style=="display:none;"){
			$("#flowHandlerNames").attr("style","width:150px;float:left;");
    		$("#flowHandlerNames").attr("class","eve-input validate[required]");
		}
		var SELECTEDTRANSNODE = $("input[name='SELECTEDTRANSNODE']").val();
    	if(flowNodeName!=SELECTEDTRANSNODE){
    		//先获取是否已经选择过审核人
    		if(selectedNodeHandlers[flowNodeName]){
    			$("#flowHandlerNames").val(selectedNodeHandlers[flowNodeName]);
    		}else{
    			$("#flowHandlerNames").val("");
    		}
    		if(flowHandlerNames){
        		$("input[name='flowHandlerNames']").val(flowHandlerNames);
        	}else{
        		var onclickDesc = "selectNextHandler('"+flowNodeName+"','"+selectorUrl+"','"+selectorHeight+"','"
        				+selectorWeight+"','"+selectorVars+"','"+selectorRule+"','"+selectorName+"');";
        		$("#flowHandlerNames").attr("name",flowNodeName);
        		$("#flowHandlerNames").attr("onclick",onclickDesc);
        	}
    	}
    	$("input[name='SELECTEDTRANSNODE']").val(flowNodeName);
	}
}
</script>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="2">下一环节信息</th>
	</tr>
	<!-- ====================开始编写非并行流程的代码=============== -->
	<c:if test="${ISPARALLEL=='false'&&fn:length(nextTrans)>1}">
		<tr>
			<td><span style="width: 100px;float:left;text-align:right;">环节名称：</span>
				<select style="width:150px;float:left;" onchange="changeTrans2(this);"
				class="eve-input validate[required]" name="flowNodeName">
					<option value="">请选择环节</option>
					<c:forEach items="${nextTrans}" var="nextTran">
						<option
						    flowHandlerNames="${nextTran.flowHandlerNames}"
						    flowNodeName="${nextTran.flowNodeName}"
						    selectorUrl="${nextTran.selectorUrl}"
						    selectorHeight="${nextTran.selectorHeight}"
						    selectorWeight="${nextTran.selectorWeight}"
						    selectorVars="${nextTran.selectorVars}"
						    selectorRule="${nextTran.selectorRule}"
						    selectorName="${nextTran.selectorName}"
						    isEndNode="${nextTran.isEndNode}"
							value="${nextTran.flowNodeName}">${nextTran.flowNodeName}</option>
					</c:forEach>
			</select></td>
			<td><span style="width: 100px;float:left;text-align:right;"><font
				class="dddl_platform_html_requiredFlag">*</font>环节审核人：</span>
				<input type="text" style="width:150px;float:left;"
				readonly="readonly" id="flowHandlerNames"
				class="eve-input validate[required]" name="flowHandlerNames" /></td>
		</tr>
	</c:if>
	<!-- ====================结束编写非并行流程的代码=============== -->

	<c:if test="${ISPARALLEL=='true'||fn:length(nextTrans)==1}">
		<c:forEach items="${nextTrans}" var="nextTran">
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">环节名称：</span>
					<b>${nextTran.flowNodeName}</b></td>
				<c:if test="${nextTran.selectorUrl!=null}">
					<td><span style="width: 100px;float:left;text-align:right;"><font
						class="dddl_platform_html_requiredFlag">*</font>环节审核人：</span>
						<input type="text" style="width:150px;float:left;"
						readonly="readonly" class="eve-input validate[required]"
						onclick="selectNextHandler('${nextTran.flowNodeName}','${nextTran.selectorUrl}','${nextTran.selectorHeight}','${nextTran.selectorWeight}','${nextTran.selectorVars}','${nextTran.selectorRule}','${nextTran.selectorName}');"
						name="${nextTran.flowNodeName}"
						value="${nextTran.flowHandlerNames}" /></td>
				</c:if>
				<c:if test="${nextTran.selectorUrl==null&&nextTran.isEndNode==null}">
					<td><span style="width: 100px;float:left;text-align:right;"><font
						class="dddl_platform_html_requiredFlag">*</font>环节审核人：</span>
						<input type="text" style="width:150px;float:left;"
						readonly="readonly" class="eve-input validate[required]"
						value="${nextTran.flowHandlerNames}" name="flowHandlerNames" /></td>
				</c:if>
			</tr>
		</c:forEach>
	</c:if>
</table>