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
	function initDZZZ(){
		if($("input[name='SCDZZZ']:checked").val()=='02'){
			$("#refinedoilinfo").attr("style","");
		}else{
			$("#ZZBM").val("");
			$("#refinedoilinfo").attr("style","display:none;");
		}
	}
	
	$(function(){
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
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_PTJINFO_FORM");
    		
    		 if((flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"||
    		flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="受理")
    		&&'${serviceItem.DEF_KEY}'=='birthone'){
    		$("input[name='BIRTHCARDNO']").attr("disabled","disabled");
    		$("#birthcl").hide();
    		$("#birthcldiv").hide();
    		} else{
    		$("input[name='BIRTHCARDNO']").removeAttr("disabled");
    		$("#birthcl").show();
    		$("#birthcldiv").show();
    		}
    		//初始化表单字段值
    		//办结环节展示生成证照信息的模块
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="办结"){
				$("#createDZZZ").attr("style","block");
				setInterval("initDZZZ()","2000");
			}
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_PTJINFO_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
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
					// if(flowSubmitObj.EFLOW_EXERUNSTATUS=="2"){
						//产业奖补保存按钮去掉
						// $("#saveCyjb").remove();
					// }
				}
    		}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}

			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems&&dealItems!=""){
    			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="处室领导审核"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SH_TR").attr("style","");
    			}else if(dealItems!=undefined&&dealItems!=''&&dealItems.indexOf(eflowNodeName)>0){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SH_TR").attr("style","");
    				$("#SP_TR").attr("style","");
    				if('${serviceItem.ITEM_CODE}'=='${dealItem.DEALITEM_CODE}'){
    					console.info("=====" + '${dealItem.DEALITEM_NAME}' + ",=======" + '${dealItem.DEALITEM_CODE}');
        				$("#DZ_TR").attr("style","");
        				$("#DZZZ_TABLE :input[type='text']").each(function(i) {
								if (this.name == "ZTZ") {
									$("input[name='" + this.name + "']")
										.attr("class","tab_text validate[required,custom[number]]");
								} else if (this.name == "LXDH") {
									$("input[name='" + this.name + "']")
										.attr("class","tab_text validate[required,custom[mobilePhoneLoose]]");
								} else if (this.name == "EMAIL") {
									$("input[name='" + this.name + "']")
										.attr("class","tab_text validate[required,custom[email]]");
								} else if (this.name == "POSTCODE") {
									$("input[name='" + this.name + "']")
										.attr("class","tab_text validate[required,custom[chinaZip]]");
								} else if (this.name == "GSKSSJ"
										|| this.name == "GSJSSJ") {
									$("input[name='" + this.name + "']")
										.attr("class","easyui-datebox validate[required]");
								} else {
									$("input[name='" + this.name + "']")
										.attr("class","tab_text validate[required]");
								}
        				});
        				$("input[name='IS_SEND']").click(function(){
        					var flag = $(this).attr("value");
        					if (flag == 1){
        						$("input[name='SQJG_ADDR']").attr("class","tab_text validate[required]");
        					}
        				});
    				}
				}else if('${serviceItem.DEF_KEY}'=="jtyjsjygclc"&&(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="初审"&&
							flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="窗口会审"&&
							flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="处事复审"&&
							flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="分管领导审核")){
					$("#auditPassTable").hide();
				}
    		}
			//bmfw12340(cyjb)
			if('${serviceItem.DEF_KEY}'=="cyjb12340"||'${serviceItem.DEF_KEY}'=="cyjb12346"||'${serviceItem.DEF_KEY}'=="cyjb120"||'${serviceItem.DEF_KEY}'=="cyjb1230"){					
				if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
					$("#saveCyjb").show();
				}
				$("#cyjb").show();
			} 
			//房地产契税奖补
			if("345071904GF01201"=='${serviceItem.ITEM_CODE}'){
				$("#fdcqsjb").show();
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="开始"){
					$("#tableOfFdcqsjb").show();
				}
			}
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
			
		    $("input[name='SCDZZZ']").click(function(){
		    	var radio = document.getElementsByName("SCDZZZ"); 
		        for (i=0; i<radio.length; i++) { 
		            if (radio[i].checked) { 
						var str=radio[i].value;
						if("02"==str){
							$("#refinedoilinfo").attr("style","");
						}else{	
							$("#refinedoilinfo").attr("style","display:none;");
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
		var fileids=$('input[name="BIRTH_FILE_ID"]').val();
	    $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
			 if(resultJson!="websessiontimeout"){
				$("#BirthfileListDiv").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+='<p style=\"margin-left: 5px; margin-right: 5px;line-height: 20px;" id="'+list[i].FILE_ID+'\">';
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					if('${serviceItem.DEF_KEY}'=='birthone'&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='国内出生婚生子女随父或母出生登记'
					){
				    newhtml+="<a href=\"javascript:void(0);\"  onclick=\"delbirthUploadFileDB('"+list[i].FILE_ID+"');\" > <font color=\"red\">删除</font></a>";
					}
					newhtml+='</p>';
				}
				$("#BirthfileListDiv").html(newhtml);
			 }
	 });
		//一件事流程业务类型选择
		if("${serviceItem.DEF_KEY}"=='transfer'||"${serviceItem.DEF_KEY}"=='unemployment'||"${serviceItem.DEF_KEY}"=='retire'||"${serviceItem.DEF_KEY}"=='birthone'||"${serviceItem.DEF_KEY}"=='employment'){
			$("input[name='ONETHING_APPLYITEM']").click(function(){
				var ONETHING_APPLYITEM="";
				$("input[name='ONETHING_APPLYITEM']:checked").each(function(index){
					if(index>0){
						ONETHING_APPLYITEM+=",";
					}
					ONETHING_APPLYITEM+=$(this).attr("dicdesc");//业务类型对应环节名称
				});
				$("input[name='nextNodeNames']").val(ONETHING_APPLYITEM);
			});
			var ONETHING_APPLYITEM="";
			$("input[name='ONETHING_APPLYITEM']:checked").each(function(index){
				if(index>0){
					ONETHING_APPLYITEM+=",";
				}
				ONETHING_APPLYITEM+=$(this).attr("dicdesc");//业务类型对应环节名称
			});
			$("input[name='nextNodeNames']").val(ONETHING_APPLYITEM);
		}
	});
	function delbirthUploadFile(fileId){
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='BIRTH_FILE_URL']").val();
		var fileid=$("input[name='BIRTH_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='BIRTH_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='BIRTH_FILE_ID']").val(arrayIds.join(";"));
  
	});
}
function delbirthUploadFileDB(fileId){
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='BIRTH_FILE_URL']").val();
		var fileid=$("input[name='BIRTH_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='BIRTH_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='BIRTH_FILE_ID']").val(arrayIds.join(";"));
  
	});
	var exeId=$("#EXEID").text();
		$.ajax({
			url:"executionController/delbirthfile.do",
			data:{fileId:fileId,exeId:exeId},
			dataType:"json",
			type:"post",
			success:function(data){
			},
			error:function(data){}
		})
}

	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_PTJINFO_FORM").validationEngine("validate");
		 if(validateResult){
			 flowSubmitObj.CyjbJson = getCyjbJson();
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 //一件事流程并行任务
			 if("${serviceItem.DEF_KEY}"=='transfer'||"${serviceItem.DEF_KEY}"=='unemployment'||"${serviceItem.DEF_KEY}"=='retire'||"${serviceItem.DEF_KEY}"=='birthone'||"${serviceItem.DEF_KEY}"=='employment'){
			 	var ONETHING_APPLYITEM="";
				$("input[name='ONETHING_APPLYITEM']:checked").each(function(index){
					if(index>0){
						ONETHING_APPLYITEM+=",";
					}
					ONETHING_APPLYITEM+=$(this).attr("dicdesc");
				});
				flowSubmitObj.EFLOW_JOINPRENODENAMES=ONETHING_APPLYITEM;
			 }
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_PTJINFO_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				return flowSubmitObj;
			 }else{
				 return null;
			 }
		 }else{
			 return null;
		 }
	}
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
	function FLOW_TempSaveFun(flowSubmitObj){
		//先判断表单是否验证通过
		flowSubmitObj.CyjbJson = getCyjbJson();
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("T_BSFW_PTJINFO_FORM");
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
/**
* 标题附件上传对话框
*/
function openBirthFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=JBPM6_EXECUTION', {
		title: "上传(附件)",
		width: "650px",
		height: "300px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					var fileType = 'gif,jpg,jpeg,bmp,png';
					var attachType = 'attach';
					if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
						attachType = "image";
					}								
					var fileurl=$("input[name='BIRTH_FILE_URL']").val();
					var fileid=$("input[name='BIRTH_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='BIRTH_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='BIRTH_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='BIRTH_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='BIRTH_FILE_ID']").val(uploadedFileInfo.fileId);
					}					
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delbirthUploadFileDB('"+uploadedFileInfo.fileId+"');\" ><font color=\"red\">删除</font></a></p>"
					$("#BirthfileListDiv").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BSFW_PTJINFO_FORM" method="post">
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
		<!-- 根据业务办理项筛选下一步并行任务环节参数 -->	
		<input type="hidden" name="nextNodeNames"/>
		<!-- 出生一件事材料上传 -->
		<input type="hidden" name="BIRTH_FILE_URL"  value="${busRecord.BIRTH_FILE_URL}">
        <input type="hidden" name="BIRTH_FILE_ID" value="${busRecord.BIRTH_FILE_ID}">
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

		<%--开始引入产业奖补--%>
		<jsp:include page="./applycyjb.jsp" />
		<%--结束引入产业奖补 --%>

		<%--开始引入房地产契税奖补--%>
		<jsp:include page="./applyfdcqsjb.jsp" />
		<%--开始引入房地产契税奖补--%>
		
		<c:if test="${serviceItem.DEF_KEY=='birthone'}">		
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
				<tr>
					<th colspan="2">户口本信息</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font> 身份证号码：</td>
				<td colspan="3"><input type="text"
					class="tab_text validate[required]" style="width: 70%"
					maxlength="100" name="BIRTHCARDNO"  value="${busRecord.BIRTHCARDNO}" />
				</tr>
				<tr >
			<td id="birthcl"><span style="width: 125px;float:left;text-align:left;">材料上传：
			     </span>
			</td>
			<td colspan="3" id="birthcldiv">
				<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div>
				
				<a href="javascript:void(0);" onclick="openBirthFileUploadDialog()">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
				
				<tr >
			      <td></td>
			      <td colspan="3">
				  <div style="width:100%;" id="BirthfileListDiv"></div>
			      </td>
		        </tr>
			</td>
		</tr>
			</table>
		</c:if>
		<c:if test="${serviceItem.DEF_KEY=='transfer'||serviceItem.DEF_KEY=='unemployment'||
		serviceItem.DEF_KEY=='retire'||serviceItem.DEF_KEY=='employment'}">		
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="oneThing"  >
				<tr>
					<th colspan="2">“一件事”申请</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>申请业务类型：</td>
					<td>
						<eve:excheckbox
								dataInterface="dictionaryService.findDatasForSelect"
								name="ONETHING_APPLYITEM" width="650px;"
								clazz="checkbox validate[required]" dataParams="${serviceItem.DEF_KEY}"
								value="${serviceItem.ONETHING_APPLYITEM}">
						</eve:excheckbox>
					</td>
				</tr>
			</table>
		</c:if>

		<c:if test="${serviceItem.DEF_KEY=='jtyjsjygclc'}">
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
				id="auditPassTable">
				<tr>
					<th colspan="2">审核是否通过</th>
				</tr>
				<tr>
					<td><input type="radio" name="isAuditPass" value="1"
						checked="checked" />通过</td>
					<td><input type="radio" name="isAuditPass" value="-1" />不通过</td>
				</tr>
			</table>
		</c:if>
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
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="createDZZZ" style="display: none">
			<tr>
				<th colspan="2">是否生成证照信息</th>
			</tr>
			<tr>
				<td><input type="radio" name="SCDZZZ" value="02"
					<c:if test="${execution.SCDZZZ=='02' }">checked="checked"</c:if> />是
				</td>
				<td><input type="radio" name="SCDZZZ" value="01"
					<c:if test="${execution.SCDZZZ=='01'||execution.SCDZZZ==null }">checked="checked"</c:if> />否	
				</td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
			id="refinedoilinfo" style="display: none;">
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证照编码
 				<br>
				<span style="color: red">(格式：油零售证书第XXXXXX号)</span>
				</td>
				<td><input type="text" class="tab_text validate[required]"
					maxlength="64" name="ZZBM" id="ZZBM"
					/></td>
				<td class="tab_width"><font class="tab_color">*</font>证照名称：</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="64" name="ZZMC" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业名称</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="64" name="QYMC" />
				</td>
				<td class="tab_width"><font class="tab_color">*</font> 发证日期：</td>
				<td><input type="text" name="FZSJ"
					class="laydate-icon validate[required]"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
					style="width:170px; height:26px;" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 有效期</td>
				<td colspan="3">自<input type="text" name="YXQ_BEGIN" class="laydate-icon validate[required]" style="width:182px; height:26px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
					至<input type="text" name="YXQ_END" class="laydate-icon validate[required]" style="width:182px; height:26px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>法定代表人</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="64" name="FDDBR" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>批准从事业务</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="128" name="PZCSYW" />
				</td>
				<td class="tab_width"><font class="tab_color">*</font>企业编码</td>
				<td><input type="text validate[required]" class="tab_text"
					maxlength="128" name="QYBM" />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>地址</td>
				<td colspan="3"><textarea rows="5" cols="80" name="DZ"
					maxlength="200" class="validate[required]"></textarea></td>
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
