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
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>
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

<style type="">
.z_tab_width{width:135px; background:#fbfbfb;}
</style>
<script type="text/javascript">
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
    	var flowSubmitObj = FlowUtil.getFlowObj();
    	if(flowSubmitObj){
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_ZFTZYDYX_FORM");
    			
    			//获取表单字段权限控制信息
        		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        		 //初始化表单字段权限控制
        		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_ZFTZYDYX_FORM");
    		}
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
    			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="汇总意见"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="协调结果"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    				$("#SFXTYZ_TR2").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="填写意见"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    				$("#SFXTYZ_TR2").attr("style","");
    			}

    			/*签发环节添加签发是否通过按钮*/
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "签发") {
					$("#QFSFTG").attr("style", "");
					$("#YWCZ_TABLE").attr("style", "");
				}

				/*S受理环节添加签发是否通过按钮*/
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理") {
					$("#licenseFileId").attr("style", "");
					$("#SFUPLOADLIECENSE").attr("style", "");
				}else if((flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="审查"||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="决定"||
					flowSubmitObj.EFLOW_CURUSEROPERNODENAME==""||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="结束"
				)&&flowSubmitObj.busRecord.SFUPLOADLIECENSE==1){
					$("#licenseFileId").attr("style", "");
					$("#LIECENSE_FILEID").attr("style", "");
				}
    		}
    	}
	
		//初始化材料列表
		// AppUtil.initNetUploadMaters({
			// busTableName:"T_BSFW_ZFTZYDYX"
		// });
		document.getElementById("fjbdDiv").style.display="none";
	});
	
	
	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_ZFTZYDYX_FORM").validationEngine("validate");
		var SFUPLOADLIECENSE=$("[name='SFUPLOADLIECENSE']:checked").val();

		var LIECENSE_FILEID=$("input[name='LIECENSE_FILEID']").val();
		if(SFUPLOADLIECENSE=='1'&&LIECENSE_FILEID==''&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="审查"&&flowSubmitObj.IS_HANDUP!=1){
			parent.art.dialog({
				content : "请先上传证照附件！",
				icon : "warning",
				ok : true
			});
			validateResult=false;
		}
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_ZFTZYDYX_FORM");
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
	
	function FLOW_TempSaveFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_ZFTZYDYX_FORM").validationEngine("validate");
		 if(validateResult){
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_ZFTZYDYX_FORM");
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
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
	function FLOW_ViewSumOpinionFun(flowSubmitObj){
		return flowSubmitObj;
	}

	$(function () {
		fetchHPFile();
	})
	function fetchHPFile() {
		var busRecordID = '${busRecord.YW_ID}'
		$.post("executionController/fetchHPFile.do",{busRecordID:busRecordID,attachKey:"569262478QS00203_A"},function (data) {
			var data = JSON.parse(data);
			if (data.status == 'success') {
                var files = data.files
                var str = "<tr><th colspan=\"6\">云平台材料</th></tr>";
                str += "<tr><td class=\"tab_width1\" width=\"200px\">材料名称</td><td class=\"tab_width1\" width=\"200px\">材料下载地址</td></tr>";
                for (var i = 0; i < files.length; i++) {
                    str += '<tr><td class="tab_width1" width="200px">' + files[i].FILE_NAME + '</td>';
                    str += '<td class="tab_width1" width="200px" style="color:blue;cursor: pointer;"><a onclick="AppUtil.downLoadFile('+"'"+files[i].FILE_ID+"'"+')">http://xzfwzx.pingtan.gov.cn:8083/ptzhspFile/' + files[i].FILE_PATH + '</a></td></tr>';
                }
                $("#HPFile").html(str);
            }
		})
	}


	/**
	 * 标题附件上传对话框
	 */
	function openDyqdjFileUploadDialog(){
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=attach&busTableName=T_BSFW_ZFTZYDYX', {
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
						var fileurl=$("input[name='DYQDJ_FILE_URL']").val();
						var fileid=$("input[name='LIECENSE_FILEID']").val();
						if(fileid!=""&&fileid!=null){
							$("input[name='DYQDJ_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
							$("input[name='LIECENSE_FILEID']").val(fileid+";"+uploadedFileInfo.fileId);
						}else{
							$("input[name='DYQDJ_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
							$("input[name='LIECENSE_FILEID']").val(uploadedFileInfo.fileId);
						}
						var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
								+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
								+"&attachType="+attachType+"\" ";
						spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"DYQDJdelUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
						$("#DYQDJ_fileListDiv").append(spanHtml);
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function DYQDJdelUploadFile(fileId, attacheKey) {
		//AppUtil.delUploadMater(fileId,attacheKey);
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			//移除目标span
			$("#" + fileId).remove();
			var fileurl = $("input[name='DYQDJ_FILE_URL']").val();
			var fileid = $("input[name='LIECENSE_FILEID']").val();
			var arrayIds = fileid.split(";");
			for (var i = 0; i < arrayIds.length; i++) {
				if (arrayIds[i] == fileId) {
					arrayIds.splice(i, 1);
					break;
				}
			}
			$("input[name='LIECENSE_FILEID']").val(arrayIds.join(";"));
		});
	}

	$(function() {
		//初始化材料列表
		var fileids = $("input[name='LIECENSE_FILEID']").val();
		$.post("executionController.do?getResultFiles&fileIds=" + fileids, {
			fileIds : fileids
		}, function(resultJson) {
			if (resultJson != "websessiontimeout") {
				$("#DYQDJ_fileListDiv").html("");
				var newhtml = "";
				var list = resultJson.rows;
				for (var i = 0; i < list.length; i++) {
					newhtml += '<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;" id="'+list[i].FILE_ID +'">';
					newhtml += '<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\'' + list[i].FILE_ID + '\');">';
					newhtml += list[i].FILE_NAME + '</a>';
					newhtml +="<a href=\"javascript:void(0);\"  onclick=\"DYQDJdelUploadFile('"+list[i].FILE_ID+"','');\" ><font color=\"red\">删除</font></a></p>"
					newhtml += '</p>';
				}
				$("#DYQDJ_fileListDiv").html(newhtml);
			}
		});
	});
</script>
</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
   <form id="T_BSFW_ZFTZYDYX_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <%--===================重要的隐藏域内容=========== --%>


		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="licenseFileId" style="display: none;">
			<tr>
				<th colspan="4">证照附件</th>
			</tr>
			<tr id="SFUPLOADLIECENSE" style="display: none;">
				<td class="tab_width" ><font class="tab_color">*</font>下一环节提交证照附件：</td>
				<td ><eve:radiogroup typecode="SHSFXYXT" width="130px" value="${busRecord.SFUPLOADLIECENSE}"
									fieldname="SFUPLOADLIECENSE" defaultvalue="-1"></eve:radiogroup></td>
			</tr>
			<tr id="LIECENSE_FILEID" style="display: none;">
				<td class="tab_width" colspan="1"><font class="tab_color">*</font>证照附件上传：</td>
				<td colspan="3">
					<div style="width:100%;display: none;" id="DYQDJ_FILE_DIV"></div>
					<a href="javascript:void(0);" onclick="openDyqdjFileUploadDialog()">
						<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
					<div style="width:100%;" id=DYQDJ_fileListDiv></div>
				</td>
			</tr>
			<input   type="hidden"  name="LIECENSE_FILEID"   value="${busRecord.LIECENSE_FILEID}" />
		</table>



	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./tzjbxx.jsp" />
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入规划选址及用地对接表信息部分 --%>
		<jsp:include page="./annexUseland.jsp" />
	<%--结束引入规划选址及用地对接表信息部分 --%>

    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>
    
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./matterListZF.jsp" >
              <jsp:param value="T_BSFW_ZFTZYDYX_FORM" name="formName"/>
    </jsp:include>


	<table cellpadding="0" cellspacing="1" class="tab_tk_pro1" id="HPFile">

	</table>
	<div class="tab_height" ></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="YWCZ_TABLE" style="display: none;">
		<tr>
			<th colspan="4">业务操作</th>
		</tr>
		<tr id="SFXYXT_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>是否需要协调：</td>
			<td><eve:radiogroup typecode="SHSFXYXT" width="130px"
					fieldname="SFXYXT" defaultvalue="-1"></eve:radiogroup></td>
<%--			<td><input type="radio" value="1">需要</td>--%>
		</tr>
		
		<tr id="SFXTYZ_TR2" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>协调是否一致：</td>
			<td><eve:radiogroup typecode="SHSFXTYZ" width="130px"
					fieldname="SFXTYZ" defaultvalue="1"></eve:radiogroup></td>
		</tr>
		<tr id="SFXTYZ_TR3" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>公示是否有异议：</td>
			<td><eve:radiogroup typecode="SHSFXTYZ" width="130px"
								fieldname="SFXTYZ" defaultvalue="1"></eve:radiogroup></td>
		</tr>
		<tr id="QFSFTG" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>签发是否通过：</td>
			<td><eve:radiogroup typecode="QFSFTG" width="130px"
								fieldname="QFSFTG" defaultvalue="1"></eve:radiogroup></td>
		</tr>
	</table>
	</form>
</body>
</html>
