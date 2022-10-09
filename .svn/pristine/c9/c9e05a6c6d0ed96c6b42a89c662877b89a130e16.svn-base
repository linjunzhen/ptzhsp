<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@page import="net.evecom.core.util.FileUtil"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
	String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
	request.setAttribute("nowDate", nowDate);
	String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	request.setAttribute("nowTime", nowTime);
	Properties properties = FileUtil.readProperties("project.properties");
    String attachFileFolderPath = properties.getProperty("AttachFileUrl") + "attachFiles/";
    request.setAttribute("attachFileFolderPath", attachFileFolderPath);
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
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/qslyshbsy/js/qslyshbsy.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	$(function(){
	     //alert('${attachFileFolderPath}');
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
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_QSLYSHBSY_FORM");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_QSLYSHBSY_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){					
				  $("#signTr").show();//开放签章
				  //受理信息不可编辑
				  $("#slxx input[type=text]").each(function(index){
						$(this).attr("disabled","disabled");
				  });
				}				
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='办结'){
				   $("#signBtn").hide("");//隐藏签章按钮
				}
								
				if ($("input[name='SBMC']").val()) {
				} else {
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
				};
    		}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}

			//窗口领取方式
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
	});
	


    function setGrBsjbr(){
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
        }
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

</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDC_QSLYSHBSY_FORM" method="post">
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
		
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="slxx">
			<tr>
				<th colspan="4">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 宗地编号：</td>
				<td colspan="3"><input type="text"
					class="tab_text validate[required]" style="width: 50%" onblur="validate(this.value);"
					maxlength="220" name="ZDBH" /><span style="color: red;"><strong>格式规范：前六位数固定（351001）+六位数字+两位字母+五位数字</strong>
				</span>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 宗地面积：</td>
				<td ><input type="text" class="tab_text validate[required]" 
					maxlength="64" name="ZDMJ" />
				</td>
				<td class="tab_width"><font class="tab_color">*</font> 建筑面积：</td>
				<td ><input type="text" class="tab_text validate[required]" 
					maxlength="64" name="JZMJ" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 房屋层数：</td>
				<td colspan="3"><input type="text"
					class="tab_text validate[required]" maxlength="64" name="FWCS"/>			
				</td>
			</tr>
			<tr id="signTr" style="display:none">
			    <%-- <c:if test="${!empty busRecord.SIGN_VERSION}">
				    <input name="SIGN_VERSION" type="hidden" value="${busRecord.SIGN_VERSION}"/>
				</c:if>	 --%> 
				<input name="SIGN_FILE_TYPE" type="hidden" value="${busRecord.SIGN_FILE_TYPE}"/>			
				<td class="tab_width"><font class="tab_color">签批文件签章</font></td>
				<td colspan="3" >
				<a href="javascript:void(0);" id="downloadBtn" onclick="AppUtil.downLoadFile('${busRecord.SIGN_FILEID}')" style="cursor: pointer;">
					<font color="blue">不动产权属来源审核表</font>
				</a>								 
				<input type="button" id="signBtn" class="eflowbutton" onclick="ktSignRemoteFileView();" value="签章"/>  			
				</td>
			</tr>	   
		</table>
		
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		
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
				<td><textarea name="REMARK" cols="140" rows="10"
						class="validate[[],maxSize[500]]"></textarea>
				</td>
				<td/>
			</tr>
		</table>		
	</form>
</body>
</html>
