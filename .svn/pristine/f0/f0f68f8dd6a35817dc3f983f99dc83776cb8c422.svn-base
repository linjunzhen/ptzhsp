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
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	$(function(){
		//申请人（单位）非必填
		$("#sqrId").attr("style","display:none");
		$('input[name="APPLICANT_UNIT"]').removeClass("validate[required]");
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
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDCQLC_LSYLPLJINFO_FORM");
    		$("#upload").hide();
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_LSYLPLJINFO_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
				}
				
				//受理、办结环节信息受理信息不可编辑
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="受理" ||
					 flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="办结"){
					 $("#slxx select , #slxx input").each(function(index){
						$(this).attr("disabled","disabled");
					});
				}
				
				
				if('${serviceItem.DEF_KEY}'=="cyjb12340"||'${serviceItem.DEF_KEY}'=="cyjb12346"||'${serviceItem.DEF_KEY}'=="cyjb120"||'${serviceItem.DEF_KEY}'=="cyjb1230"){					
					if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="办结"){
						$("input[name='SBMC']").removeAttr("disabled");
						$("input[name='SBMC']").removeAttr("readonly");
						$("input[name='SQRMC']").removeAttr("disabled");
						$("input[name='SQRMC']").removeAttr("readonly");
						$("input[name='SQRSFZ']").removeAttr("disabled");
						$("input[name='SQRSFZ']").removeAttr("readonly");
						$("input[name='SQRSJH']").removeAttr("disabled");
						$("input[name='SQRSJH']").removeAttr("readonly");
						$("input[name='SQRDHH']").removeAttr("disabled");
						$("input[name='SQRDHH']").removeAttr("readonly");
					}
				}
    		}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}

			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
    		
			
			if($("input[name='FINISH_GETTYPE']:checked").val()=='02'){				
				$("#addr").attr("style","");
				$("#addressee").attr("style","");
				$("#addrmobile").attr("style","");
				$("#addrprov").attr("style","");
				$("#addrcity").attr("style","");
				$("#addrpostcode").attr("style","");
				$("#addrremarks").attr("style","");
			}
		    $("input[name='FINISH_GETTYPE']").click(function(){
		    	var radio = document.getElementsByName("FINISH_GETTYPE"); 
		        for (i=0; i<radio.length; i++) { 
		            if (radio[i].checked) { 
						var str=radio[i].value;
						if("02"==str){
							$("#addr").attr("style","");
							$("#addressee").attr("style","");
							$("#addrmobile").attr("style","");
							$("#addrprov").attr("style","");
							$("#addrcity").attr("style","");
							$("#addrpostcode").attr("style","");
							$("#addrremarks").attr("style","");
						}else{	
							$("#addr").attr("style","display:none;");
							$("#addressee").attr("style","display:none;");
							$("#addrmobile").attr("style","display:none;");
							$("#addrprov").attr("style","display:none;");
							$("#addrcity").attr("style","display:none;");
							$("#addrpostcode").attr("style","display:none;");
							$("#addrremarks").attr("style","display:none;");
						}
		            } 
		        }  
			});
	
    	}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_PTJINFO"
		//});
		var start = {
		    elem: "#start",
		    format: "YYYY-MM-DD",
		    istime: false,
			min:laydate.now(),
		    choose: function(datas){
		    	if(datas==undefined){
					end.min=laydate.now();		
					end.start = datas;
				}else{
					end.min=datas;
					end.start = datas;
				}
		    }
		};
		var end = {
		    elem: "#end",
		    format: "YYYY-MM-DD",
		    istime: false,
			min:laydate.now(),
		    choose: function(datas){
		    	if(datas==undefined){
					start.max=null;
				}else{
					start.max=datas;
				}
		    }
		};
		laydate(start);
		laydate(end);
		
		
		if(flowSubmitObj.EFLOW_EXEID){
			$("#plryGrid").datagrid("loadData", []);
			$('#plryGrid').datagrid({
				url:'bdcExecutionController.do?loadImpPerson',    
				queryParams:{'Q_t.EXE_ID_EQ':flowSubmitObj.EFLOW_EXEID}
			});
		}
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="开始"){
			
			$("input[name='impPersons']").attr("disabled",true);
			$("input[name='impPersons']").attr("style","background:#bbb !important;");
		}
	});
	

	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BDCQLC_LSYLPLJINFO_FORM").validationEngine("validate");
		 if(validateResult){
			 setGrbsJbrInfo();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BDCQLC_LSYLPLJINFO_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 //console.dir(flowSubmitObj);	
				return flowSubmitObj;
			 }else{
				 return null;
			 }
		 }else{
			 return null;
		 }
	}
    function setGrbsJbrInfo(){
        var val=$('input:checkbox[name="SFXSJBRXX"]:checked').val();
        var lxval=$('input:radio[name="BSYHLX"]:checked').val();
        if(val==null&&lxval=="1"){
            $('input[name="JBR_NAME"]').val($('input[name="SQRMC"]').val());
            var zjlx = $('#SQRZJLX option:selected').val();
            $("#JBR_ZJLX").find("option[value='"+zjlx+"']").attr("selected",true);
            $('input[name="JBR_ZJHM"]').val($('input[name="SQRSFZ"]').val());
            $('input[name="JBR_MOBILE"]').val($('input[name="SQRSJH"]').val());
            $('input[name="JBR_LXDH"]').val($('input[name="SQRDHH"]').val());
            $('input[name="JBR_ADDRESS"]').val($('input[name="SQRLXDZ"]').val());
            $('input[name="JBR_EMAIL"]').val($('input[name="SQRYJ"]').val());
            var sqrxb = $('#SQRXB option:selected').val();
            $("#JBR_SEX").find("option[value='"+sqrxb+"']").attr("selected",true);
        }
    }
	function FLOW_TempSaveFun(flowSubmitObj){
		//先判断表单是否验证通过
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("T_BDCQLC_LSYLPLJINFO_FORM");
		for(var index in formData){
			flowSubmitObj[eval("index")] = formData[index];
		}				 
		return flowSubmitObj;
			 
	}
	
	function FLOW_BackFun(flowSubmitObj){
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
	
	function impPerson(){
		var flowSubmitObj = FlowUtil.getFlowObj();
		var url = "ybCommonController.do?toImpPerson";
		var impTableName = "T_BDCQLC_LSYLPLBJRY";
		url += "&impTableName="+impTableName;
		if(flowSubmitObj.EFLOW_EXEID){
			url += "&exeId="+flowSubmitObj.EFLOW_EXEID;
		}
		$.dialog.open(url, {
			title : "人员导入",
			width : "350px",
			height : "150px",
			lock : true,
			resize : false,
            close: function () {
    			var backInfo = art.dialog.data("backInfo");
    			if(backInfo){
    				var jsonObj = eval('(' + backInfo.jsonString + ')');
    				
    				//$('#plryGrid').datagrid('loadData',jsonObj);
    				var personIds = "";
    				for(var i=0;i<jsonObj.rows.length;i++){
    					if(i>0){
    						personIds += ",";
    					}
    					personIds += jsonObj.rows[i].RECORD_ID;
    				}
    				$("input[name='personIds']").val(personIds);
    				art.dialog.removeData("backInfo");
    				
					$("#plryGrid").datagrid("loadData", []);
    				$('#plryGrid').datagrid({
						url:'bdcExecutionController.do?loadImpPerson',    
						queryParams:{'Q_t.RECORD_ID_IN':personIds}
					});
    			}
    		}
		}, false);
	}

	function rowformaterxb(value,row,index){
		if(value=='1'){
			return "男";
		}else if(value=='2'){
			return "女";
		}
	}
	
	function rowformaterOper(value,row,index){
		if(value=='0'){
			var flowSubmitObj = FlowUtil.getFlowObj();
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!=undefined && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
				return "<input type=\"button\" class=\"eflowbutton\" value=\"受理\" onclick=\"acceptExe('"+row.RECORD_ID+"');\">";
			}
		}else if(value=='1'){
			var exeId = row.GEN_EXE_ID;
			return "<a href=\"executionController.do?goDetail&exeId="+exeId+"\" target='_blank'><font style=\"color:blue;\">办件详情</font></a>";
		}else{
			return "未保存";
		}
	}
	
	function acceptExe(RECORD_ID){
		AppUtil.ajaxProgress({
			url : "bdcExecutionController.do?acceptPersonExe",
			params : {"recordId" : RECORD_ID},
			callback : function(resultJson) {
				if(resultJson.success){
				  	art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:5,
						ok: true
					});
					$("#plryGrid").datagrid("reload");
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			}
		});
	}
	
	function downloadTmp(){
		window.location.href="<%=basePath%>/webpage/bsdt/applyform/ybqlc/common/template.xlsx";
	}
	
	
	/*更改测绘公司*/
	function changeDrawOrg(val) {
	    var LOCATED = $("input[name='LOCATED']").val();
	    var CHGS_ID = findDrawOrg(LOCATED);
	    if (CHGS_ID && CHGS_ID != "") {
	        art.dialog.confirm("该坐落已有测绘公司测绘，是否要更改？",
	            function () {
	                $("#CHGS_ID").val(val);
	            },
	            function () {
	                $("#CHGS_ID").val(CHGS_ID);
	            });
	    }
	}
	
	
	function findDrawOrg(val) {
	    var CHGS_ID = "";
	    $.ajax({
	        method: 'post',
	        url: 'bdcExecutionController.do?findDrawOrgByLocated',
	        async: false,
	        data: {
	            located: val
	        },
	        success: function (data) {
	            if (data) {
	                var json = JSON.parse(data);
	                if (json.success) {
	                    $("#CHGS_ID").val(json.surveyUserId);
	                    CHGS_ID = json.surveyUserId;
	                }
	            }
	        }
	    });
	    return CHGS_ID;
	}
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_LSYLPLJINFO_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" /> 
		<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
		<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
		<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
		<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
		<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
		
		<input type="hidden" name="personIds" />
		
		 <%--测绘材料ID--%>
        <input type="hidden" name="DRAW_FILE_ID" id="DRAW_FILE_ID"/>
        
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
					maxlength="220" name="SBMC" /><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
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
		
		<%--开始引入受理信息--%>
        <jsp:include page="./bdcqlc/lsylwtcz/lsylwtczSlxx.jsp" />
        <%--结束引入受理信息--%>
		
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
			<tr>
				<th colspan="3">批量办件人员信息</th>
				<td class="tab_width">
					<input type="button" class="eflowbutton"  name="impPersons" value="人员导入" onclick="impPerson();"> 
					<input type="button" class="eflowbutton"  name="impPersons" value="模板下载" onclick="downloadTmp();"> 
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="1" style="width:100%">
			<tr>
				<td >
					<div id="plryToolBar" style="width: 100%;">
						<div class="right-con">
							<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							</div>
						</div>
					</div>
					<div style="height:300px;" id="sfmxtable">
					<table class="easyui-datagrid" rownumbers="true" pagination="true"
						id="plryGrid" fitcolumns="true" toolbar="#plryToolBar"
						checkonselect="true"
						selectoncheck="true" fit="true" border="false"
						url=""
					>
					<thead>
						<tr>
							<!-- <th field="ck" checkbox="true"></th> -->
							<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
							<th data-options="field:'SQRMC',align:'center'" width="10%">姓名</th>
							<th data-options="field:'SQRSFZ',align:'center'" width="15%">身份证</th>
							<th data-options="field:'SQRXB',align:'center',formatter:rowformaterxb" width="5%">性别</th>
							<th data-options="field:'SQRSJH',align:'center'" width="10%">手机号码</th>
							<th data-options="field:'SQRLXDZ',align:'center'" width="30%">联系地址</th>
							<th data-options="field:'GEN_EXE_ID',align:'center'" width="13%">关联申报号</th>
							<th data-options="field:'GEN_STATUS',align:'center',formatter:rowformaterOper" width="10%">操作</th>
						</tr>
					</thead>
					</table>
					</div>
				</td>
			</tr>
		</table>
		
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>


		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
			id="YWCZ_TABLE" style="display: none;">
			<tr>
				<th colspan="4">业务操作</th>
			</tr>
			<tr id="SH_TR" style="display: none;">
				<td class="tab_width"><font class="tab_color">*</font>处室领导审核结果：</td>
				<td><eve:radiogroup typecode="SHSFTG" width="130px"
						fieldname="CSSHJG" defaultvalue="1"></eve:radiogroup>
				</td>
			</tr>
			<tr id="SP_TR" style="display: none;">
				<td class="tab_width"><font class="tab_color">*</font>分管领导审批结果：</td>
				<td><eve:radiogroup typecode="SHSFTG" width="130px"
						fieldname="FGSHJG" defaultvalue="1"></eve:radiogroup>
				</td>
			</tr>
			<tr id="DZ_TR" style="display: none;">
				<td class="tab_width"><font class="tab_color">*</font>是否推送电子证照系统：</td>
				<td><eve:radiogroup typecode="isSend" width="130px"
						onclick="if(this.value=='1')$('#DZZZ_TABLE').attr('style','');else $('#DZZZ_TABLE').attr('style','display:none;');"
						fieldname="IS_SEND" defaultvalue="-1"></eve:radiogroup>
				</td>
			</tr>
		</table>

		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
			id="DZZZ_TABLE" style="display: none;">
			<tr>
				<th colspan="4">电子证照信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 证书编号<br>
				<span style="color: red">(格式：闽X危经〔XXXX〕X号)</span>
				</td>
				<td><input type="text" class="tab_text validate[required]"
					maxlength="32" name="SERIALNUMBER"
					onChange="javascript:setFileNumber(this.value);" /></td>
				<td class="tab_width"><font class="tab_color">*</font> 企业名称：</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="64" name="OWNER" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 发证机关</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="64" name="PUBLISHER" />
				</td>
				<td class="tab_width"><font class="tab_color">*</font> 发证日期：</td>
				<td><input type="text" name="PUBLISHDATE"
					class="laydate-icon validate[required]"
					onclick="laydate({istime: false, min:laydate.now(), format: 'YYYY-MM-DD'})"
					style="width:170px; height:26px;" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 有效期</td>
				<td colspan="3">自 <input type="text" name="VALIDFROMDATE"
					class="laydate-icon validate[required]" id="start"
					readonly="readonly" style="width:182px; height:26px;" /> 至 <input
					type="text" name="VALIDUNTILDATE"
					class="laydate-icon validate[required]" id="end"
					readonly="readonly" style="width:182px; height:26px;" /></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 经营方式</td>
				<td colspan="3"><textarea rows="5" cols="80" name="JYFS"
						maxlength="1998" class="validate[required]"></textarea></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 许可范围：</td>
				<td colspan="3"><textarea rows="5" cols="80" name="XKFW"
						maxlength="1998" class="validate[required]"></textarea></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 电子证照编号</td>
				<td colspan="3"><input type="text" class="tab_text"
					id="fileNumber" name="FILENUMBER" maxlength="64" style="width: 41%"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 持证者证件类型：</td>
				<td colspan="3"><eve:radiogroup typecode="ownerType"
						width="130px" fieldname="OWNERTYPE" defaultvalue="P1"></eve:radiogroup>
				</td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
			<tr>
				<th colspan="2">办理结果领取方式</th>
			</tr>
			<tr>
				<td><input type="radio" name="FINISH_GETTYPE" value="02"
					<c:if test="${execution.FINISH_GETTYPE=='02' }">checked="checked"</c:if> />快递送达
				</td>
				<td><input type="radio" name="FINISH_GETTYPE" value="01"
					<c:if test="${execution.FINISH_GETTYPE=='01'||execution.FINISH_GETTYPE==null }">checked="checked"</c:if> />窗口领取
				</td>
			</tr>
			<tr id="addressee" style="display: none;">
				<td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>收件人姓名：</span>
					<input type="text" maxlength="16" class="tab_text validate[required]"
					class="tab_text" value="${execution.RESULT_SEND_ADDRESSEE}"
					name="RESULT_SEND_ADDRESSEE" />
				</td>
				<td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>电话号码：</span>
					<input type="text" maxlength="11" class="tab_text validate[required,custom[mobilePhoneLoose]]"
					class="tab_text" value="${execution.RESULT_SEND_MOBILE}"
					name="RESULT_SEND_MOBILE" />
				</td>
			</tr>
			<tr id="addrprov" style="display: none;">
				<td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>所属省市：</span>
					<input name="RESULT_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#city').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);  
							   }
							}
	                " value="${execution.RESULT_SEND_PROV}" />					
					<input name="RESULT_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#county').combobox('reload',url);  
							   }
							}
	                " value="${execution.RESULT_SEND_CITY}" />	
					<input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);  
							   }
							}
	                " value="${execution.RESULT_SEND_COUNTY}" />	   				
				</td>
				<td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>邮政编码：</span>
					<input type="text" maxlength="6" class="tab_text validate[[required],custom[chinaZip]]"
					class="tab_text" value="${execution.RESULT_SEND_POSTCODE}"
					name="RESULT_SEND_POSTCODE" />
				</td>
			</tr>
			<tr id="addr" style="display: none;">
				<td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>详细地址：</span>
					<textarea rows="5" cols="80" name="RESULT_SEND_ADDR" value="${execution.RESULT_SEND_ADDR}"
						maxlength="1998" class="validate[required]"></textarea></td>
				</td>
				<td/>
			</tr>
			<tr id="addrremarks" style="display: none;">
				<td><span style="width: 90px;float:left;text-align:right;">邮递备注：</span>
					<textarea rows="5" cols="80" name="RESULT_SEND_REMARKS" value="${execution.RESULT_SEND_REMARKS}"
						maxlength="1998" class="validate[]"></textarea></td>
				</td>
				<td/>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
			<tr>
				<th colspan="2">其他信息</th>
			</tr>
			<tr>
				<td class="tab_width">备注：</td>
				<td><textarea name="BDC_REMARK" cols="140" rows="10"
						class="validate[[],maxSize[500]]"></textarea>
				</td>
				<td/>
			</tr>
		</table>
	</form>
</body>
</html>
