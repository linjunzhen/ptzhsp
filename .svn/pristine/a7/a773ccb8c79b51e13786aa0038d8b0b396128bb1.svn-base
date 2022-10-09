<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="com.alibaba.fastjson.JSON"%>
<%@ page language="java" import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ page language="java" import="net.evecom.platform.hflow.service.MaterConfigService"%>
<%@ page language="java" import="net.evecom.platform.hflow.model.FlowNextStep"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<%
	String flowSubmitInfoJson = request.getParameter("flowSubmitInfoJson");
    String isBackBj = request.getParameter("isBackBj");
    String infoJson = StringEscapeUtils.unescapeHtml3(flowSubmitInfoJson);
	Map<String,Object> flowSubmitInfo = JSON.parseObject(infoJson,Map.class);
	String defId = (String)flowSubmitInfo.get("EFLOW_DEFID");
	String exeId = (String)flowSubmitInfo.get("EFLOW_EXEID");
	String nodeName = (String)flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
	MaterConfigService materConfigService = (MaterConfigService)AppUtil.getBean("materConfigService");
	if(isBackBj.equals("false")){
	    //获取公文流转类型
	    String publicDocRule = request.getParameter("publicDocRule");
	    //获取下一环节的信息
 		List<FlowNextStep> nextTrans = (List<FlowNextStep>)request.getAttribute("nextTrans");
 		//开始获取公文的数据
 		List<Map<String,Object>> publicDocList = materConfigService.findNextStepPublicDocs(defId, nodeName, nextTrans,publicDocRule,exeId);
 		request.setAttribute("publicDocList", publicDocList);
 		//开始获取公文的数据
 		String publicDocJson = materConfigService.getPublicDocJson(defId, nodeName,exeId);
 		request.setAttribute("publicDocJson", publicDocJson);
 		request.setAttribute("publicDocRule", publicDocRule);
	}
	List<Map<String,Object>> backDocList = new ArrayList<Map<String,Object>>();
	if(!nodeName.equals("填写公示结果")){
		//开始获取回执单的数据
		backDocList = materConfigService.findList(defId, nodeName, "1",exeId);
	}else{
		String gssftg = (String)flowSubmitInfo.get("GSSFTG");
		String xtsftg = (String)flowSubmitInfo.get("XTSFTG");
		if(gssftg.equals("-1")&&xtsftg.equals("-1")){
			backDocList = materConfigService.findList(defId, nodeName, "1",exeId);
		}
	}
	request.setAttribute("backDocList", backDocList);
	request.setAttribute("isBackBj", isBackBj);
	request.setAttribute("flowSubmitInfo", flowSubmitInfo);
%>

<script type="text/javascript">
function showDocumentTemplateWindow(templateId,userCode,sfhzd,docName,sfhtml) {
	var filePathName = templateId+"_"+userCode+"_FILE_PATH";
	var aName = templateId+"_"+userCode+"_A";
	var ylName = templateId+"_"+userCode+"_YL";
	var divName = templateId+"_"+userCode+"_DIV";
	var flowExeId = $("input[name='FLOW_EXEID']").val();
	if(sfhtml=='0'){
		var params = "&templateId="+templateId+"&templateTable=T_WSBS_DOCUMENTTEMPLATE&userCode="+userCode+"&flowExeId="+flowExeId;
		//params+="&userCode="+userCode;
		var templatePath = $("input[name='"+filePathName+"']").val();
		if(templatePath&&templatePath!=""){
			params+="&templatePath="+templatePath;
		}
	    $.dialog.open("documentTemplateController.do?edit"+params, {
	        title : "在线编辑",
	        width: "100%",
	        height: "100%",
	        left: "0%",
	        top: "0%",
	        fixed: true,
	        lock : true,
	        resize : false,
	        close: function () {
	            var saveTemplateInfo = art.dialog.data("saveTemplateInfo");
	            if(saveTemplateInfo&&saveTemplateInfo.templatePath){
	            	var filePath = saveTemplateInfo.templatePath;
	            	var fileType = filePath.substring(filePath.lastIndexOf("."),filePath.length);
	            	$("input[name='"+filePathName+"']").val(saveTemplateInfo.templatePath);
	            	$("input[name='"+filePathName+"']").attr("filename",docName+fileType);
	            	$("#"+aName).attr("style","");
	            	$("#"+divName).attr("style","display:none;");
	            }
	            art.dialog.removeData("saveTemplateInfo");
	        }
	    }, false);
	}else if(sfhtml=='1'){
		var params = "";
		var taskName = $("input[name='FLOW_TASKNAME']").val();
		/* var templatePath = $("input[name='"+filePathName+"']").val();
		if(templatePath&&templatePath!=""){
			params+="&templatePath="+templatePath;
		} */
		var xnbId = $("input[name='"+filePathName+"']").attr("xnbid");
		if(xnbId&&xnbId!=""){
			params+="&xnbId="+xnbId;
		}
		params +="&documentId="+templateId+"&exeId="+flowExeId
				+"&toUserName="+userCode+"&nodeName="+encodeURI(taskName);
		$.dialog.open("variableController.do?openHtmlTemplate"+params, {
	        title : "在线编辑",
	        width: "100%",
	        height: "100%",
	        left: "0%",
	        top: "0%",
	        fixed: true,
	        lock : true,
	        resize : false,
	        close: function () {
	            var saveTemplateInfo = art.dialog.data("saveTemplateInfo");
	            if(saveTemplateInfo&&saveTemplateInfo.templatePath){
	            	var filePath = saveTemplateInfo.templatePath;
	            	var fileType = filePath.substring(filePath.lastIndexOf("."),filePath.length);
	            	$("input[name='"+filePathName+"']").val(saveTemplateInfo.templatePath);
	            	$("input[name='"+filePathName+"']").attr("filename",docName+fileType);
	            	$("input[name='"+filePathName+"']").attr("xnbid",saveTemplateInfo.xnb_id);
	            	$("#"+aName).attr("style","");
	            	$("#"+ylName).attr("style","");
	            	$("#"+divName).attr("style","display:none;");
	            }
	            art.dialog.removeData("saveTemplateInfo");
	        }
	    }, false);
	} 
};


function deleteDocTempalte(templateId,authDepId){
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		var ylName = templateId+"_"+authDepId+"_YL";
		var aName = templateId+"_"+authDepId+"_A";
		var divName = templateId+"_"+authDepId+"_DIV";
		var filePathName = templateId+"_"+authDepId+"_FILE_PATH";
		$("input[name='"+filePathName+"']").val("");
		$("input[name='"+filePathName+"']").attr("xnbid","");
		$("#"+aName).attr("style","display:none;");
		$("#"+ylName).attr("style","display:none;");
		$("#"+divName).attr("style","");
	});
}

function getSubmitFlowMatersJson(){
	var publicDocRule = $("#publicDocRule").val();
	var materArray = [];
	var inputs = $("input[name$='FILE_PATH']");
	var UPLOADER_ID = $("input[name='UPLOADER_ID']").val();
	var UPLOADER_NAME = $("input[name='UPLOADER_NAME']").val();
	var UPLOADER_DEPID = $("input[name='UPLOADER_DEPID']").val();
	var UPLOADER_DEPNAME = $("input[name='UPLOADER_DEPNAME']").val();
	var FLOW_EXEID = $("input[name='FLOW_EXEID']").val();
	var FLOW_TASKID = $("input[name='FLOW_TASKID']").val();
	var FLOW_TASKNAME = $("input[name='FLOW_TASKNAME']").val();
	$.each(inputs,function(index,span) { 
		 var authusercode =  $(this).attr("authusercode");
		 var authusername = $(this).attr("authusername");
		 var SFHZD = $(this).attr("sfhzd");
		 var FILE_PATH = $(this).val();
		 var FILE_NAME = $(this).attr("filename");
		 var tplId = $(this).attr("tplid");
		 var xnbId = $(this).attr("xnbid");
		 var mater = {};
	 	 mater.UPLOADER_ID= UPLOADER_ID;
	 	 mater.UPLOADER_NAME= UPLOADER_NAME;
	 	 mater.UPLOADER_DEPID= UPLOADER_DEPID;
	 	 mater.UPLOADER_DEPNAME= UPLOADER_DEPNAME;
	 	 mater.FLOW_EXEID= FLOW_EXEID;
	 	 mater.FLOW_TASKID= FLOW_TASKID;
	 	 mater.FLOW_TASKNAME= FLOW_TASKNAME;
	 	 mater.SFHZD = SFHZD;
	 	 mater.AUTH_USERCODES = authusercode;
	 	 mater.AUTH_USERNAMES = authusername;
	 	 mater.FILE_PATH = FILE_PATH;
	 	 mater.FILE_NAME = FILE_NAME;
	 	 mater.TPL_ID = tplId;
	 	 mater.XNB_ID = xnbId;
	 	 if($(this).val()!=null&&$(this).val()!=""){
	 		materArray.push(mater);
	 	 }
		 
    }); 
	if(materArray.length>0){
		return JSON2.stringify(materArray);
	}else{
		return "";
	}
}

$(function(){
	//初始化上传控件
	//initUploadAN();
});
/**
 * 标题附件上传对话框
 */
function openAuditMatersFileUploadDialog(perId){
	var materType = "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.zip;*.rar;*.pdf;";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='UPLOADER_ID']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='UPLOADER_NAME']").val();
	var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType='
	+materType+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName)+'&busTableName='+EFLOW_BUSTABLENAME, {
		title: "上传(附件)",
		width: "700px",
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
					
                    var spanHtml = "<a href=\"javascript:void(0);\" class=\"tab_btn_ct1 tab_tk_pro2btn\" ";
                    spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
                    spanHtml +="下载</a>"
                    spanHtml +="<a href=\"javascript:void(0);\" class=\"tab_btn_ct1 tab_tk_pro2btn\"  onclick=\"delfile('"+uploadedFileInfo.fileId+"','"+perId+"');\" >删除</a>";
                    $("#"+perId+"DIV2").html(spanHtml);
                    $("#"+perId+"ZXBJ").attr("style","display:none;");
                    var filePathName = perId+"FILE_PATH";
                    $("input[name='"+filePathName+"']").val(uploadedFileInfo.filePath);
                    $("input[name='"+filePathName+"']").attr("filename",uploadedFileInfo.fileName);
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
function initUploadAN(){
	var dd = $("img[id$='SC']");
	dd.each(function(index){
		var id = $(this).attr("id");
		var zdFilePathName = id.substring(0,id.lastIndexOf("_")+1)+"FILE_PATH";
		var zdFilepath = $("input[name='"+zdFilePathName+"']").attr("filepath");
		var successfulUploads = 0;
		if(zdFilepath.length>0){
			successfulUploads = 1;
		}
		var fileUploadSwf = AppUtil.bindSwfUpload({
            file_types : "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.zip;*.rar;*.pdf;",
            file_size_limit :"20 MB",
            post_params : {
                "uploadPath":"auditMaters"
            },
            file_upload_limit :1,
            button_placeholder_id:id,
            successful_uploads:successfulUploads,
            upload_success_handler:function(resultJson){
                if(resultJson.success==true){
                    //获取文件ID
                    var fileId = resultJson.fileId;
                    //获取文件名称
                    var fileName = resultJson.fileName;
                    //获取文件路径
                    var filePath = resultJson.filePath;
                    //获取ID前赘
                    var perId = id.substring(0,id.lastIndexOf("_")+1);
                    
                    var spanHtml = "<a href=\"javascript:void(0);\" class=\"tab_btn_ct1 tab_tk_pro2btn\" ";
                    spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
                    spanHtml +="下载</a>"
                    spanHtml +="<a href=\"javascript:void(0);\" class=\"tab_btn_ct1 tab_tk_pro2btn\"  onclick=\"delfile('"+fileId+"','"+perId+"');\" >删除</a>";
                    $("#"+perId+"DIV2").append(spanHtml);
                    $("#"+perId+"ZXBJ").attr("style","display:none;");
                    var filePathName = perId+"FILE_PATH";
                    $("input[name='"+filePathName+"']").val(filePath);
                    $("input[name='"+filePathName+"']").attr("filename",fileName);
                    
                }
            }
        });
		
	});
}
function delfile(fileId,perId){
	var attachKey = perId+"SC";
	var filePathName = perId+"FILE_PATH";
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		$("#"+perId+"DIV2").html("");
		$("#"+perId+"ZXBJ").attr("style","");
		$("input[name='"+filePathName+"']").val("");
		$.each(SWFUpload.instances,function(n,swfObject) {   
			if(swfObject.settings.button_placeholder_id==attachKey){
				swfObject.setStats({successful_uploads:0});
			}
		}); 
    });
	
}
function bindMater(userName){	
	var taskId = $("input[name='FLOW_TASKID']").val();
	var exeId = $("input[name='FLOW_EXEID']").val();
	parent.$.dialog.open("applyMaterController.do?itemSelector&userName="+userName+"&taskId="+taskId+"&exeId="+exeId, {
		title : "申请材料选择器",
		width:"1000px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectMaterInfo = art.dialog.data("selectMaterInfo");
			if(selectMaterInfo){
				saveSelectMaters(selectMaterInfo.materIds,userName);
				art.dialog.removeData("selectMaterInfo");
			}
		}
	}, false);
}
function saveSelectMaters(materIds,userName){		
	var taskId = $("input[name='FLOW_TASKID']").val();
	var exeId = $("input[name='FLOW_EXEID']").val();
	var nodeName = $("input[name='FLOW_TASKNAME']").val();
	var layload = layer.load("正在提交请求中…");
	$.post("applyMaterController.do?flowUserMaterSaveOrUpdate",{
			materIds :materIds,
			userName :userName,
			exeId :exeId,
			nodeName :nodeName,
			taskId :taskId
		}, function(responseText, status, xhr) {
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success == true){				
				var FLOW_USER_MATER_ID = $("#FLOW_USER_MATER_ID").val();
				if(FLOW_USER_MATER_ID==null||FLOW_USER_MATER_ID==''){
					$("#FLOW_USER_MATER_ID").val(resultJson.ids);
				}else{
					$("#FLOW_USER_MATER_ID").val(FLOW_USER_MATER_ID+","+resultJson.ids);
				}
				parent.art.dialog({
					content : "指派审核材料成功",
					icon : "succeed",
					time : 3,
					ok : true
				});
			}else{
				 art.dialog({
					content: "指派审核材料失败",
					icon:"error",
					ok: true
				});
			}
	});
}

function ylDocTempalte(templateId,userCode){
	var filePathName = templateId+"_"+userCode+"_FILE_PATH";
	var params ="";
	var templatePath = $("input[name='"+filePathName+"']").val();
	if(templatePath&&templatePath!=""){
		params+="&templatePath="+templatePath;
	}
	$.dialog.open("variableController.do?ylTemplate"+params, {
        title : "预览",
        width: "100%",
        height: "100%",
        left: "0%",
        top: "0%",
        fixed: true,
        lock : true,
        resize : false
    }, false);
}
</script>
<style>
.materBtnA {
    background: #62a1cf none repeat scroll 0 0;
    color: #fff;
    display: inline-block;
    height: 26px;
    left: -1px;
    line-height: 26px;
    margin-left: 5px;
    position: relative;
    text-align: center;
    top: 1px;
    width: 80px;
}
</style>

<input type="hidden" name="UPLOADER_ID" value="${sessionScope.curLoginUser.userId}"/>
<input type="hidden" name="UPLOADER_NAME" value="${sessionScope.curLoginUser.fullname}"/>
<input type="hidden" name="UPLOADER_DEPID" value="${sessionScope.curLoginUser.depId}"/>
<input type="hidden" name="UPLOADER_DEPNAME" value="${sessionScope.curLoginUser.depName}"/>
<input type="hidden" name="FLOW_EXEID" value="${flowSubmitInfo.EFLOW_EXEID}"/>
<input type="hidden" name="FLOW_TASKID" value="${flowSubmitInfo.EFLOW_CURRENTTASK_ID}"/>
<input type="hidden" name="FLOW_TASKNAME" value="${flowSubmitInfo.EFLOW_CURUSEROPERNODENAME}"/>
<input type="hidden" name="PUBLIC_DOC_JSON" value="${publicDocJson}"/>
<input type="hidden" id="publicDocRule" name="publicDocRule" value="${publicDocRule}"/>
<input type="hidden" id="FLOW_USER_MATER_ID" name="FLOW_USER_MATER_ID"/><!--绑定审核材料主键ID-->

<div id="publicDocDiv">
<!-- 公文遍历,流转规则按个人 -->
<c:if test="${isBackBj=='false'&&publicDocList!=null&&fn:length(publicDocList)>0&&publicDocRule=='-1'}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" >
	<tr>
		<th colspan="3">公文流转至：</th>
	</tr>
	<c:forEach items="${publicDocList}" var="publicDoc">
		<tr>
			<td colspan="3">${publicDoc.ASSIGNER_NAME}(${publicDoc.ASSIGNER_DEPNAME})<c:if test="${nodeConfig.IS_MARTER==1}"><a class="materBtnA" style="color:#fff;float: right;" href="javascript:bindMater('${publicDoc.ASSIGNER_CODE}')" >指派审核材料</a></c:if></td>
		</tr>
		<c:forEach items="${publicDoc.materList}" var="mater" varStatus="varStatus">
		<tr>
			<td class="tab_widthnum">${varStatus.index+1}</td>
			<td>${mater.DOCUMENT_NAME}</td>
			<td width="260px">
			    <input type="hidden"  documentname="${mater.DOCUMENT_NAME}" sfhzd="-1" filepath="${mater.FILE_PATH}" value="${mater.FILE_PATH}" filename='${mater.FILE_NAME}' tplid="${mater.TPL_ID}" xnbid="" authusername="${publicDoc.ASSIGNER_NAME}" authusercode="${publicDoc.ASSIGNER_CODE}" name="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_FILE_PATH">
			    <a href="javascript:void(0);" <c:if test="${!empty mater.FILE_PATH}">style="display: none;" </c:if>
			    id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_ZXBJ" onclick="showDocumentTemplateWindow('${mater.TPL_ID}','${publicDoc.ASSIGNER_CODE}','-1','${mater.DOCUMENT_NAME}','${mater.SFHTML}');" class="tab_btn_tj1 tab_tk_pro2btn">
			           在线编辑
			    </a>
			    <span id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_DIV">
					<a href="javascript:void(0);" onclick="openAuditMatersFileUploadDialog('${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_')">
						<img id="${mater.TPL_ID}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				   </a>
                </span>
                <span id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_DIV2">
                    <c:if test="${!empty mater.FILE_PATH}">
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"
                        onclick="AppUtil.downLoadFile('${mater.FILE_ID}');">下载</a>
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"  onclick="delfile('${mater.FILE_ID}','${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_');" >删除</a>
                    </c:if>
                </span>
                <a id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_YL" onclick="ylDocTempalte('${mater.TPL_ID}','${publicDoc.ASSIGNER_CODE}');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">预览</a>
				<a id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_A" onclick="deleteDocTempalte('${mater.TPL_ID}','${publicDoc.ASSIGNER_CODE}','-1');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">删除</a>
			</td>
		</tr>
		</c:forEach>
	</c:forEach>
</table>
</c:if>
<!-- 公文遍历,流转规则按组织 -->
<c:if test="${isBackBj=='false'&&publicDocList!=null&&fn:length(publicDocList)>0&&publicDocRule=='1'}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" >
	<tr>
		<th colspan="3">公文流转</th>
	</tr>
	<c:forEach items="${publicDocList}" var="mater" varStatus="varStatus">
		<tr>
			<td class="tab_widthnum">${varStatus.index+1}</td>
			<td>${mater.DOCUMENT_NAME}</td>
			<td width="260px">
			    <input type="hidden" documentname="${mater.DOCUMENT_NAME}"  sfhzd="-1" filepath="${mater.FILE_PATH}" value="${mater.FILE_PATH}" filename='${mater.FILE_NAME}' tplid="${mater.TPL_ID}" xnbid="" authusername="${mater.AUTH_USER_NAMES}" authusercode="${mater.AUTH_USER_CODES}" name="${mater.TPL_ID}__FILE_PATH">
			    <a href="javascript:void(0);" <c:if test="${!empty mater.FILE_PATH}">style="display: none;" </c:if>
			    id="${mater.TPL_ID}__ZXBJ" onclick="showDocumentTemplateWindow('${mater.TPL_ID}','','-1','${mater.DOCUMENT_NAME}','${mater.SFHTML}');" class="tab_btn_tj1 tab_tk_pro2btn">
			           在线编辑
			    </a>
			    <span id="${mater.TPL_ID}__DIV">
					
					<a href="javascript:void(0);" onclick="openAuditMatersFileUploadDialog('${mater.TPL_ID}__')">
						<img id="${mater.TPL_ID}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				   </a>
                </span>
                <span id="${mater.TPL_ID}__DIV2">
                    <c:if test="${!empty mater.FILE_PATH}">
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"
                        onclick="AppUtil.downLoadFile('${mater.FILE_ID}');">下载</a>
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"  onclick="delfile('${mater.FILE_ID}','${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_');" >删除</a>
                    </c:if>
                </span>
                <a id="${mater.TPL_ID}__YL" onclick="ylDocTempalte('${mater.TPL_ID}','');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">预览</a>
				<a id="${mater.TPL_ID}__A" onclick="deleteDocTempalte('${mater.TPL_ID}','','-1');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
</c:if>


</div>

<!-- 回执单遍历 -->
<c:if test="${backDocList!=null&&fn:length(backDocList)>0}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="3">回执单流转至：</th>
	</tr>
	<tr>
		<td colspan="3">${flowSubmitInfo.EFLOW_CREATORNAME}</td>
	</tr>
	<c:forEach items="${backDocList}" var="backDoc" varStatus="varStatus">
	<tr>
		<td class="tab_widthnum">${varStatus.index+1}</td>
		<td>${backDoc.DOCUMENT_NAME}</td>
		<td width="260px">
		    <input type="hidden" sfhzd="1" filepath="${backDoc.FILE_PATH}" value="${backDoc.FILE_PATH}" tplid="${backDoc.TPL_ID}" xnbid="" filename='${backDoc.FILE_NAME}' authusername="" authusercode="" name="${backDoc.TPL_ID}__FILE_PATH">
		    <a href="javascript:void(0);" <c:if test="${!empty backDoc.FILE_PATH}">style="display: none;" </c:if>
		    id="${backDoc.TPL_ID}__ZXBJ" onclick="showDocumentTemplateWindow('${backDoc.TPL_ID}','','1','${backDoc.DOCUMENT_NAME}','${backDoc.SFHTML}');" class="tab_btn_tj1 tab_tk_pro2btn">在线编辑</a>
		    <span id="${backDoc.TPL_ID}__DIV">					
				<a href="javascript:void(0);" onclick="openAuditMatersFileUploadDialog('${backDoc.TPL_ID}__')">
					<img id="${backDoc.TPL_ID}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				</a>
		    </span>
		    <span id="${backDoc.TPL_ID}__DIV2" style="float: left;">
		          <c:if test="${!empty backDoc.FILE_PATH}">
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"
                        onclick="AppUtil.downLoadFile('${backDoc.FILE_ID}');">下载</a>
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"  onclick="delfile('${backDoc.FILE_ID}','${backDoc.TPL_ID}__');" >删除</a>
                    </c:if>
		    </span>
		    <a id="${backDoc.TPL_ID}__YL" onclick="ylDocTempalte('${backDoc.TPL_ID}','');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">预览</a>
			<a id="${backDoc.TPL_ID}__A" onclick="deleteDocTempalte('${backDoc.TPL_ID}','');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">删除</a>
		</td>
	</tr>
	</c:forEach>
</table>
</c:if>