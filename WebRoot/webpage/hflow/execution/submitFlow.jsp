<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources loadres="jquery,easyui,apputil,layer,laydate,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
   
	$(function() {
		//初始化验证引擎的配置
		AppUtil.initWindowForm("SubmitFlowForm", function(form, valid) {
			// var isok = false;
			// $("input[name$='__FILE_PATH']").each(function(i){
			// 	var val = $(this).val();
			// 	var documentname = $(this).attr("documentname");
			// 	if(documentname!=undefined&&documentname!=null&&documentname.indexOf("意见汇总表")>-1){
			// 		if(null==val||''==val){
			// 			isok = true;
			// 		}
			// 	}
			// });
			// if(isok){
			// 	alert("意见汇总表必须上传!");
			// 	return;
			// }
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = FlowUtil.getFormEleData("SHCZDIV");
				//获取提交的变量对象
		    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
		    	//转换成对象
		    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		    	flowVars = AppUtil.mergeObject(formData,flowVars);
				if(!flowVars.shzdshIsOk && flowVars.shzdshIsOk!=undefined){	
					var eflowIsPass = $("[name='EFLOW_ISPASS']:checked").val();
					if(eflowIsPass==1){						
						$("input[type='submit']").attr("disabled", false);
						art.dialog({
							content : "存在不通过的字段审核意见，不能选择通过！",
							icon : "warning",
							ok : true
						});
						return;
					}
				}
		    	//判断是否是多分支流程
		    	var isMultiTransFlow = $("#isMultiTransFlow").val();
		    	if(isMultiTransFlow=="true"){
		    		//获取下拉框中的选择节点名称
		    		var selectNodeName = $("select[name='flowNodeName']").val();
		    		var NEW_EFLOW_NEXTSTEPS = {};
		    		for(var index in flowVars.EFLOW_NEXTSTEPS){
		    			if(index==selectNodeName){
		    				NEW_EFLOW_NEXTSTEPS[index] = flowVars.EFLOW_NEXTSTEPS[index];
		    			}
		    		}
		    		flowVars.EFLOW_NEXTSTEPS = NEW_EFLOW_NEXTSTEPS;
		    	}
		    	//将审核人转换成JSON
		    	var EFLOW_NEXTSTEPSJSON =  JSON2.stringify(flowVars.EFLOW_NEXTSTEPS);
		    	flowVars.EFLOW_NEXTSTEPS = null;
		    	if(EFLOW_NEXTSTEPSJSON!=null&&EFLOW_NEXTSTEPSJSON!="undefined"){
		    		flowVars.EFLOW_NEXTSTEPSJSON = EFLOW_NEXTSTEPSJSON; 
		    	}else{
		    		//说明是办结
		    		flowVars.EFLOW_DESTTOEND = "true";
		    		flowVars.EFLOW_NEXTSTEPSJSON = "";
		    	}
		    	//办结结果附件判断（排除掉老年证）一窗通办  所属部门是否为区党工委宣传与影视发展部
		    	if('${flowDef.DEF_KEY}' != 'OldAgeCardNewFlow'&&'${isYctb}'!='1' && '${IS_QDGWXCYYSFZB}'!='1'){
		    		if(flowVars.EFLOW_DESTTOEND&&"${resultControll}"=="true"){
			    		var resulturls=$("input[name='RESULT_FILE_URL']").val();
			    		if(resulturls==null||resulturls==""){
			    			$.messager.alert('警告',"办件结果附件不能为空，请上传附件，谢谢！");
			    			$("input[type='submit']").attr("disabled", false);
							return ; 
			    		}
			    	}else{
			    		var EFLOW_APPMATERFILEJSON = getSubmitFlowMatersJson();
			    		if(EFLOW_APPMATERFILEJSON&&EFLOW_APPMATERFILEJSON!=""){
			    			flowVars.EFLOW_APPMATERFILEJSON = EFLOW_APPMATERFILEJSON;
			    		}
			    	}
		    	}
		    	if("${nodeConfig.SECOND_AUDIT}"==1){
		    		var EFLOW_AGAINMATERFILEJSON = getAgainbjcllbJson();
			    	if(EFLOW_AGAINMATERFILEJSON&&EFLOW_AGAINMATERFILEJSON!=""){
			    		flowVars.EFLOW_AGAINMATERFILEJSON = EFLOW_AGAINMATERFILEJSON;
			    	}
		    	}
		    	//获取传阅的人员IDS
		    	var EFLOW_PERULA_IDS = $("input[name='EFLOW_PERULA_IDS']").val();
		    	flowVars.EFLOW_PERULA_IDS = EFLOW_PERULA_IDS;
				//获取绑定审核材料ID
		    	var FLOW_USER_MATER_ID = $("input[name='FLOW_USER_MATER_ID']").val();
				flowVars.FLOW_USER_MATER_ID = FLOW_USER_MATER_ID;
				
				//获取事项分发信息JSON
				//var DISTRIBUTE_JSON =  getAuditDistribute();
				//flowVars.DISTRIBUTE_JSON = DISTRIBUTE_JSON;
				
		    	var submitFlowVars = $.param(flowVars);
				AppUtil.ajaxProgress({
					url : "executionController.do?exeFlow",
					params : submitFlowVars,
					callback : function(resultJson) {
						if(resultJson.OPER_SUCCESS){
							if(resultJson.EFLOW_CALLBACKFN){
								var flowVarsJson = JSON2.stringify(resultJson);
								var fnName = resultJson.EFLOW_CALLBACKFN;
								top.$("#EFLOWFORM_IFRAME")[0].contentWindow.eval(fnName+"('"+flowVarsJson+"')");
								AppUtil.closeLayer();
							}else{
								parent.art.dialog({
									content : resultJson.OPER_MSG,
									icon : "succeed",
									cancel:false,
									lock: true,
									ok: function(){
										if(top.opener.$("#MyApplyExecGrid").length>0){
											top.opener.$("#MyApplyExecGrid").datagrid('reload');
										}
										if(top.opener.$("#NeedMeHandleGrid").length>0){
											top.opener.$("#NeedMeHandleGrid").datagrid('reload');
										}
										if(top.opener.$("#callingGrid").length>0){
											top.opener.$("#callingGrid").datagrid('reload');
											top.opener.parent.reloadTabGrid("已受理记录","accept");
										}
										if(top.opener.$("#callingNewGrid").length>0){
											top.opener.afterRefresh();
										}
										if(top.opener.$("#ZzhyDwspGrid").length>0){
											top.opener.$("#ZzhyDwspGrid").datagrid('reload');
										}
										if(top.opener.$("#ZzhyDwysGrid").length>0){
											top.opener.$("#ZzhyDwysGrid").datagrid('reload');
										}										
										if(top.opener.$("#ZzhyNeedMeHandleGrid").length>0){
											top.opener.$("#ZzhyNeedMeHandleGrid").datagrid('reload');
										}
										if(top.opener.$("#NeedMeHandlePtjGrid").length>0){
											top.opener.$("#NeedMeHandlePtjGrid").datagrid('reload');
										}
										if(top.opener.$("#QueueItemDetailGrid").length>0){
											top.opener.afterDeal();
										}
										/**
										* 新增工程建设项目申报刷新列表
										**/
										if(top.opener.$("#ProjectItemGrid").length>0){
											top.opener.$("#ProjectItemGrid").datagrid('reload');
										}
										
										top.opener.loadByMeHandledTaskDatas();
										top.opener.loadByZzhyDwysDatas();
										top.opener.loadByZzhyMeHandledTaskDatas();
										top.opener.loadTzxmDatas();										
										top.window.opener = null;   
										top.window.open('','_self'); 
										top.window.close(); 
									}
								});
							}
						} else {
							parent.art.dialog({
								content : resultJson.OPER_MSG,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, "JBPM6_EXECUTION");
		
	});
	function getAuditDistribute(){
		var dataArray = [];
		if($("#itemAuditDistributeList").length>0){
			$("#itemAuditDistributeList tr").each(function(i){				
				var AUDITDISTRIBUTE_EXEID = $(this).find("[name$='AUDITDISTRIBUTE_EXEID']").val();
				var AUDITDISTRIBUTE_USERNAME = $(this).find("[name$='AUDITDISTRIBUTE_USERNAME']").val();
				var AUDITDISTRIBUTE_USERACCOUNT = $(this).find("[name$='AUDITDISTRIBUTE_USERACCOUNT']").val();
				var AUDITDISTRIBUTE_HANDLERS = $(this).find("[name$='AUDITDISTRIBUTE_HANDLERS']").val();
				var AUDITDISTRIBUTE_FLOWNODENAME = $(this).find("[name$='AUDITDISTRIBUTE_FLOWNODENAME']").val();
				var ITEM_CODE = $(this).find("[name$='ITEM_CODE']").val();
				var ITEM_NAME = $(this).find("[name$='ITEM_NAME']").val();
				var AUDITDISTRIBUTE_RESULTS = $(this).find("[name$='AUDITDISTRIBUTE_RESULTS']").val();
				if(AUDITDISTRIBUTE_EXEID){
					var data = {};
					data.AUDITDISTRIBUTE_EXEID = AUDITDISTRIBUTE_EXEID;
					data.AUDITDISTRIBUTE_USERNAME = AUDITDISTRIBUTE_USERNAME;
					data.AUDITDISTRIBUTE_USERACCOUNT = AUDITDISTRIBUTE_USERACCOUNT;
					data.AUDITDISTRIBUTE_HANDLERS = AUDITDISTRIBUTE_HANDLERS;
					data.AUDITDISTRIBUTE_FLOWNODENAME = AUDITDISTRIBUTE_FLOWNODENAME;
					data.ITEM_CODE = ITEM_CODE;
					data.ITEM_NAME = ITEM_NAME;
					data.AUDITDISTRIBUTE_RESULTS = AUDITDISTRIBUTE_RESULTS;
					if($("[name='"+AUDITDISTRIBUTE_EXEID+"_HANDLE_OPINION']")){						
						var HANDLE_OPINION = $("[name='"+AUDITDISTRIBUTE_EXEID+"_HANDLE_OPINION']").val();
						data.HANDLE_OPINION = HANDLE_OPINION;
					}
					if($("[name='"+AUDITDISTRIBUTE_EXEID+"_applyMatersJson']")){
						var bjcllbList = [];
						var applyMatersJson = $("[name='"+AUDITDISTRIBUTE_EXEID+"_applyMatersJson']").val();
						if(applyMatersJson){							
							var applyMatersObj = JSON2.parse(applyMatersJson);
							for(var index in applyMatersObj){
								//获取材料编码
								var MATER_CODE = applyMatersObj[index].MATER_CODE;
								//var selectValue = $("#SFBJ_"+MATER_CODE).val();
								var selectValue = $("input[name='SFBJ_"+MATER_CODE+"']:checked ").val();
								if(selectValue&&selectValue=="-1"){
									var bjcllb = {};
									bjcllb.MATER_CODE = $("#mc_"+MATER_CODE).val();
									bjcllb.MATER_NAME = $("#mn_"+MATER_CODE).text();
									bjcllb.BJYJ = $("#bjyq_"+MATER_CODE).val();
									bjcllbList.push(bjcllb);
								}
								
							 }
						}
						data.BJCLLB=JSON2.stringify(bjcllbList);
					}
					dataArray.push(data);
				}
			});
		}
		if(dataArray.length>0){
			var reg = /[\(]/g,reg2 = /[\)]/g;
			return JSON.stringify(dataArray).replace(reg,"（").replace(reg2,"）");
		}else{
			return "";
		}
	}
</script>
</head>

<body >
	<form id="SubmitFlowForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div region="center">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="ISPARALLEL" value="${ISPARALLEL}" />
				<input type="hidden" name="SELECTOR_NAMES" />
			    <input type="hidden" name="isMultiTransFlow" id="isMultiTransFlow" value="${isMultiTransFlow}" />
				<input type="hidden" name="SELECTEDTRANSNODE" />
				<input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson" value="${flowSubmitInfoJson}"/>
				<!--==========隐藏域部分结束 ===========-->
				
				<!-- ===================下一环节信息代码开始======================== -->
				<jsp:include page="./nextFlowStep.jsp" />
				<!-- ===================下一环节信息代码结束======================== -->
				
				<!-- ===================传阅信息代码开始=========================== -->	
				<jsp:include page="./nextStepPerual.jsp" />
				<!-- ===================传阅信息代码结束=========================== -->				
				
				<div id="SHCZDIV">
				<!-- ===================指定办理期限代码开始========================= -->
				<jsp:include page="./nextStepDeadTime.jsp" />
				<!-- ===================指定办理期限代码结束========================= -->
				
				<!-- ===================审核意见代码开始========================= -->
				<jsp:include page="./flowOpinion.jsp" />
				<!-- ===================审核意见代码结束========================= -->
				
				<!-- ===================办结结果代码开始========================= -->
				<%-- 增加排除老年证业务 --%>
		        <c:if test="${EFLOW_DESTTOEND=='true'}"> 
		        	<c:if test="${resultControll!=null&&resultControll=='true' && flowDef.DEF_KEY != 'OldAgeCardNewFlow'}"> 
						<jsp:include page="./flowHandleOverBT.jsp"/>
					</c:if>
				</c:if>
				 <!--===================办结结果代码结束========================= -->
				</div>
		        <!-- ===================再次审核材料界面开始========================= -->
		        <c:if test="${nodeConfig.SECOND_AUDIT==1}">
			        <jsp:include page="./auditAgainMaters.jsp" >
			           <jsp:param value="${flowSubmitInfoJson}" name="flowSubmitInfoJson"/>
			        </jsp:include>
		        </c:if>
		        <!-- ===================再次审核材料界面结束========================= -->
		        <!-- ===================审核材料代码开始========================= -->
		        <c:if test="${EFLOW_DESTTOEND!='true'}"> 
		        <jsp:include page="./auditMaters.jsp" >
		           <jsp:param value="${flowSubmitInfoJson}" name="flowSubmitInfoJson"/>
		           <jsp:param value="false" name="isBackBj"/>
		           <jsp:param value="${nodeConfig.PUBDOC_RULE}" name="publicDocRule"/>
		        </jsp:include>
		        </c:if>
		        <c:if test="${EFLOW_DESTTOEND=='true'&&itemType=='3'}"> 
		        <jsp:include page="./auditMaters.jsp" >
		           <jsp:param value="${flowSubmitInfoJson}" name="flowSubmitInfoJson"/>
		           <jsp:param value="false" name="isBackBj"/>
		           <jsp:param value="${nodeConfig.PUBDOC_RULE}" name="publicDocRule"/>
		        </jsp:include>
		        </c:if>
		        <!-- ===================审核材料代码结束========================= -->
		        
		        <!-- ===================工程建设子流程分发界面开始========================= -->
		       <%-- <c:if test="${nodeConfig.IS_DISTRIBUTE==1}">
			        <jsp:include page="./auditDistribute.jsp" >
			           <jsp:param value="${flowSubmitInfoJson}" name="flowSubmitInfoJson"/>
			        </jsp:include>
		        </c:if>--%>
		        <!-- ===================工程建设子流程分发界面结束========================= -->
		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

