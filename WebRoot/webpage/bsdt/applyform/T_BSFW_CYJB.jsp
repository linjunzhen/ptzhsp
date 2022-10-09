<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
	$(function(){
		
		 //默认企业法人
		 //qyclick();
		 //$("input:radio[name='BSYHLX'][value='2']").attr("checked",true); 
		 $("input[name='JBR_NAME']").removeAttr('readonly');
		 $("input[name='JBR_ZJHM']").removeAttr('readonly');
	
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
    	var flowSubmitObj = FlowUtil.getFlowObj();
    	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
    	dealItems = "," + dealItems + ",";
    	if(flowSubmitObj){
    		//获取表单字段权限控制信息
    		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
    		var exeid = flowSubmitObj.EFLOW_EXEID;
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
    		 //初始化表单字段权限控制
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_CYJB_FORM");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_CYJB_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
					if($("input[name='SBMC']").val()){
					}else{
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
				}
				var  isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
				//设置文件是否合规
				if(isAuditPass == "-1"){
					$("input:radio[name='isAuditPass'][value='-1']").attr("checked",true); 
				}
    		}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}

			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems&&dealItems!=""){
    			$("#ptcyjb").attr("style","");
    			if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
    				$("input[name='JBR_NAME']").removeAttr('readonly');
					$("#saveCyjb").show();
				}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="网上预审" || flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="受理"){
    				$("#auditPassTable").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="区税务局经办填写意见"){
    				$("#qswjyjTable").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="区税务局审核"){
    			    $("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="审批局指派"){
    				$("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="行业主管部门经办人填写意见"){
    				$("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    				$("#jbmxqk_detail").attr("style","");
					$("#hyzgTable").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="行业主管部门审核"
    				||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="领导审核"){
    				$("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    				$("#jbmxqk_detail").attr("style","");
    				$("#jbmxqk_detail textarea").each(function(index){
						$(this).prop("readonly",true);
					});
					$("#hyzgTable").attr("style","");
					$("#hyzgTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});
    			}else if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == "区行政审批局经办人填写意见"){
    				$("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    				$("#jbmxqk_detail").attr("style","");
    				// $("#jbmxqk_detail textarea").each(function(index){
					// 	$(this).prop("readonly",true);
					// });
					$("#hyzgTable").attr("style","");
					$("#hyzgTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});
					$("#qxzspjTable").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == "审核人审核"){
    				$("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    				$("#jbmxqk_detail").attr("style","");
    				$("#jbmxqk_detail textarea").each(function(index){
						$(this).prop("readonly",true);
					});
					$("#hyzgTable").attr("style","");
					$("#hyzgTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});
					$("#qxzspjTable").attr("style","");
					$("#qxzspjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});
    			}else if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == "签字盖章、上传审批表"){
    				$("#qswjyjTable").attr("style","");
    			    $("#qswjyjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});					
    				$("#jbmxqk_detail").attr("style","");
    				$("#jbmxqk_detail textarea").each(function(index){
						$(this).prop("readonly",true);
					});
					$("#hyzgTable").attr("style","");
					$("#hyzgTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});
					$("#qxzspjTable").attr("style","");
					$("#qxzspjTable textarea").each(function(index){
						$(this).prop("readonly",true);
					});
    			}
    		}

			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审批局指派") {
					$("#FLOW_TXYJ").css("display","block")
				}
			}

			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME) {
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '行业主管部门经办人填写意见') {
					$("#FLOW_TXYJT").css("display", "block");
				}
			}
    	}
    	
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_CYJB"
		//});
		
	});
	

	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_CYJB_FORM").validationEngine("validate");
		 if(validateResult){
		     flowSubmitObj.CyjbJson = getCyjbJson();
		 	 var isAuditPass = $('input[name="isAuditPass"]:checked').val();
		     if(isAuditPass == "-1"){
		     	 parent.art.dialog({
					content : "检查上传的审批表扫描件不合规，请先退回补件。",
					icon : "warning",
					ok : true
				 });
				 return null;
		     }else{
		     	 setGrBsjbr();//个人不显示经办人设置经办人的值
				 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
				 if(submitMaterFileJson||submitMaterFileJson==""){
					 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
					 //先获取表单上的所有值
					 var formData = FlowUtil.getFormEleData("T_BSFW_CYJB_FORM");
					 for(var index in formData){
						 flowSubmitObj[eval("index")] = formData[index];
					 }
					 //console.dir(flowSubmitObj);	
					return flowSubmitObj;
				 }else{
					 return null;
				 }
		     }			 
		 }else{
			 return null;
		 }
	}
	
	function FLOW_TempSaveFun(flowSubmitObj){
		//先判断表单是否验证通过
		flowSubmitObj.CyjbJson = getCyjbJson();
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("T_BSFW_CYJB_FORM");
		for(var index in formData){
			flowSubmitObj[eval("index")] = formData[index];
		}				 
		return flowSubmitObj;
			 
	}
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
	/**
	* 产业奖补打印控件
	*/
	function FLOW_PrintCyjbFun(flowSubmitObj){
	    //window.open("cyjbPtController.do?print&busId="+flowSubmitObj.EFLOW_BUSRECORDID,"_blank");
	    var turnForm = document.createElement("form");   
	    turnForm.method = 'post';
	    turnForm.action = 'cyjbPtController.do?print';
	    turnForm.target = '_blank';
	    //创建隐藏表单
	    var newElement = document.createElement("input");
	    newElement.setAttribute("name","busId");
	    newElement.setAttribute("type","hidden");
	    newElement.setAttribute("value",flowSubmitObj.EFLOW_BUSRECORDID);
	    turnForm.appendChild(newElement);
	    document.body.appendChild(turnForm); 
	    turnForm.submit();
		return flowSubmitObj;
	}
	
	function setFileNumber(serialNum){
		/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
		var enterprise = '${execution.SQJG_CODE}';
		var idCard = '${execution.SQRSFZ}';
		alert(idCard + "," + enterprise);
		var fileNum;
		if (enterprise != ""){
			fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
		} else {
			fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
		}
		$("#fileNumber").val(fileNum);
	}
	
	function cyjb_preview(){
		window.open("cyjbPtController.do?print&busId=","_blank");
	}

</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BSFW_CYJB_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId"
			value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
			name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
			type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
			type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME"
			value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
			value="${serviceItem.SXLX}" /> <input type="hidden"
			name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
			<tr>
				<th colspan="4">基本信息</th>
			</tr>
			<tr>
				<td>审批事项：</td>
				<td>${serviceItem.ITEM_NAME}</td>
				<td class="tab_width">承诺时间(工作日)：</td>
				<td>${serviceItem.CNQXGZR}</td>
			</tr>
			<tr>
				<td>所属部门：</td>
				<td>${serviceItem.SSBMMC}</td>
				<td class="tab_width">审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>

			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text"
					class="tab_text validate[required]" style="width: 70%"
					maxlength="220" name="SBMC" value="${busRecord.SBMC}"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
				</span>
				</td>
			</tr>
			<tr>
				<td class="tab_width">申报号：</td>
				<td id="EXEID"></td>
				<td></td>
				<td></td>
			</tr>
		</table>



		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		
		<%--开始引入产业奖补--%>
		<jsp:include page="./applyptcyjb.jsp" />
		<%--结束引入产业奖补 --%>
		
		
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%-- 开始网上预审环节，及受理环节要检查上传的电子审批表是否符合下载 --%>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
			id="auditPassTable" style="display:none;">
			<tr>
				<th colspan="2">检查上传的审批表扫描件是否合规</th>
			</tr>
			<tr>
				<td><input type="radio" name="isAuditPass" value="1"
						checked="checked" />合规</td>
				<td><input type="radio" name="isAuditPass" value="-1"
						/>不合规</td>
			</tr>
		</table>
		<%-- 结束网上预审环节，及受理环节要检查上传的电子审批表是否符合下载 --%>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
		    id="qswjyjTable" style="display:none;">
		    <tr>
				<th colspan="2">区税务局填写意见</th>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;区税务局意见：</td>
				<td><textarea name="QTEX_JB_CONTENT" cols="140" rows="6"
						class="validate[[required],maxSize[500]]"></textarea></td>
			</tr>
		</table>
		<%--开始奖补明细情况 --%>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
			id="jbmxqk_detail" style="display:none;">
			<tr>
				<th colspan="2">奖补明细情况行业主管部门填写</th>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;经营贡献奖励：</td>
				<td><textarea name="JBMX_JYGXJL" cols="140" rows="5"
						class="validate[maxSize[500]]"></textarea></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;个人所得税补助：</td>
				<td><textarea name="JBMX_GRSDSBZ" cols="140" rows="5"
						class="validate[maxSize[500]]"></textarea></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;其他：</td>
				<td><textarea name="JBMX_OTHER" cols="140" rows="5"
						class="validate[maxSize[500]]"></textarea></td>
			</tr>
		</table>
		<!-- 行业主管部门填写审核意见 开始  -->
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
		   id="hyzgTable" style="display:none;">
		    <tr>
				<th colspan="2">行业主管部门填写审核意见</th>
			</tr>
			<tr>
				<td>行业主管部门意见：</td>
				<td><textarea name="HYZG_CONTENT" cols="140" rows="6"
						class="validate[maxSize[500]]"></textarea></td>
			</tr>
		</table>
		<!-- 行业主管部门填写审核意见结束  -->
		
		<!-- 区行政审批局意见 开始  -->
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" 
		    id="qxzspjTable" style="display:none;">
		    <tr>
				<th colspan="2">区行政审批局填写意见</th>
			</tr>
			<tr>
				<td>区行政审批局意见：</td>
				<td><textarea name="QXZSPJ_CONTENT" cols="140" rows="6"
						class="validate[[required],maxSize[500]]"></textarea></td>
			</tr>
		</table>
		<div id="FLOW_TXYJ" style="display: none;">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
				<tr>
					<th colspan="2">是否直接跳转到区行政审批局经办人填写意见环节：</th>
				</tr>
				<tr align="center">
					<td>
						<input type="radio" name="TO_TXYJ" value="1">是</input>
						<input type="radio" name="TO_TXYJ" value="0">否</input>
					</td>
				</tr>
			</table>
		</div>
		<div id="FLOW_TXYJT" style="display: none;">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
				<tr>
					<th colspan="2">是否直接跳转到区行政审批局经办人填写意见环节：</th>
				</tr>
				<tr align="center">
					<td>
						<input type="radio" name="TO_TXYJT" value="1">是</input>
						<input type="radio" name="TO_TXYJT" value="0">否</input>
					</td>
				</tr>
			</table>
		</div>
		<!-- 区行政审批局意见 结束  -->
		
		<!-- <div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
			<tr>
				<th colspan="2">其他信息</th>
			</tr>
			<tr>
				<td class="tab_width">备注：</td>
				<td><textarea name="REMARK" cols="140" rows="10"
						class="validate[[],maxSize[500]]"></textarea>
				</td>
				<td/>
			</tr>
		</table> -->
	</form>
</body>
</html>
