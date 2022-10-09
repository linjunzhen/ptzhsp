<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">
var swUploadObj;
var backFileUploadSwf
	$(function(){
		//获取提交的变量对象
    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
    	//转换成对象
    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		var uploadUserId = $("input[name='uploadUserId']").val();
		var uploadUserName = $("input[name='uploadUserName']").val();
		var depId = $("input[name='depId']").val();
		var depName = $("input[name='depName']").val();
		 swUploadObj = AppUtil.bindSwfUpload({
			file_types : "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.zip;*.rar;",
			file_size_limit :"20 MB",
			post_params : {
		           "uploadPath":"appmaterials",
		           "SFHZD":$("#spclType").val(),
		           "uploadUserId":uploadUserId,
		           "uploadUserName":encodeURI(uploadUserName),
		           "FLOW_TASKID":flowVars.EFLOW_CURRENTTASK_ID,
		           "FLOW_TASKNAME":encodeURI(flowVars.EFLOW_CUREXERUNNINGNODENAMES),
		           "UPLOADER_DEPID":depId,
		           "UPLOADER_DEPNAME":encodeURI(depName)
	        },
            file_upload_limit :3,
            button_placeholder_id:"uploadMaterImg",
            successful_uploads:0,
            upload_success_handler:function(resultJson){
            	if(resultJson.success==true){
            		//获取文件ID
	            	var fileId = resultJson.fileId;
            		//获取文件名称
            		var fileName = resultJson.fileName;
            		var divHtml = "<div id=\""+fileId+"\"><a href=\"javascript:void(0);\" onclick=\"AppUtil.downLoadFile('";
            		divHtml+=(fileId+"');\">");
            		divHtml+=("<font color=\"blue\">"+"["+$("#spclType").find("option:selected").text()+"]"+fileName+"</font></a>&nbsp;&nbsp;");
            		divHtml+="<a href=\"javascript:void(0);\" onclick=\"AppUtil.delUploadMater('"+fileId+"','uploadMaterImg','UploadFilesTd');\" ><font color=\"red\">删除</font></a></div>";
            		$("#UploadFilesTd").append(divHtml);
            	}
            }
		});
		
		 backFileUploadSwf = AppUtil.bindSwfUpload({
			file_types : "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.zip;*.rar;",
			file_size_limit :"20 MB",
			post_params : {
		           "uploadPath":"appmaterials",
		           "SFHZD":$("#hzdType").val(),
		           "uploadUserId":uploadUserId,
		           "uploadUserName":encodeURI(uploadUserName),
		           "FLOW_TASKID":flowVars.EFLOW_CURRENTTASK_ID,
		           "FLOW_TASKNAME":encodeURI(flowVars.EFLOW_CUREXERUNNINGNODENAMES),
		           "UPLOADER_DEPID":depId,
		           "UPLOADER_DEPNAME":encodeURI(depName)
	        },
            file_upload_limit :3,
            button_placeholder_id:"uploadBackFileImg",
            successful_uploads:0,
            upload_success_handler:function(resultJson){
            	if(resultJson.success==true){
            		//获取文件ID
	            	var fileId = resultJson.fileId;
            		//获取文件名称
            		var fileName = resultJson.fileName;
            		var divHtml = "<div id=\""+fileId+"\"><a href=\"javascript:void(0);\" onclick=\"AppUtil.downLoadFile('";
            		divHtml+=(fileId+"');\">");
            		divHtml+=("<font color=\"blue\">"+"["+$("#hzdType").find("option:selected").text()+"]"+fileName+"</font></a>&nbsp;&nbsp;");
            		divHtml+="<a href=\"javascript:void(0);\" onclick=\"AppUtil.delUploadMater('"+fileId+"','uploadBackFileImg','UploadBackFileTd');\" ><font color=\"red\">删除</font></a></div>";
            		$("#UploadBackFileTd").append(divHtml);
            	}
            }
		});
	});
	function changeSpclType(){
		//获取提交的变量对象
        var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
        //转换成对象
        var flowVars = JSON2.parse(flowSubmitInfoJson);
        var uploadUserId = $("input[name='uploadUserId']").val();
        var uploadUserName = $("input[name='uploadUserName']").val();
        var depId = $("input[name='depId']").val();
        var depName = $("input[name='depName']").val();
		var postobj = {
                "uploadPath":"appmaterials",
                "SFHZD":$("#spclType").val(),
                "uploadUserId":uploadUserId,
                "uploadUserName":encodeURI(uploadUserName),
                "FLOW_TASKID":flowVars.EFLOW_CURRENTTASK_ID,
                "FLOW_TASKNAME":encodeURI(flowVars.EFLOW_CUREXERUNNINGNODENAMES),
                "UPLOADER_DEPID":depId,
                "UPLOADER_DEPNAME":encodeURI(depName)
         };
		swUploadObj.setPostParams(postobj);
	}
	function changeHzdType(){
		//获取提交的变量对象
        var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
        //转换成对象
        var flowVars = JSON2.parse(flowSubmitInfoJson);
        var uploadUserId = $("input[name='uploadUserId']").val();
        var uploadUserName = $("input[name='uploadUserName']").val();
        var depId = $("input[name='depId']").val();
        var depName = $("input[name='depName']").val();
		var postobj = {
                "uploadPath":"appmaterials",
                "SFHZD":$("#hzdType").val(),
                "uploadUserId":uploadUserId,
                "uploadUserName":encodeURI(uploadUserName),
                "FLOW_TASKID":flowVars.EFLOW_CURRENTTASK_ID,
                "FLOW_TASKNAME":encodeURI(flowVars.EFLOW_CUREXERUNNINGNODENAMES),
                "UPLOADER_DEPID":depId,
                "UPLOADER_DEPNAME":encodeURI(depName)
         };
		backFileUploadSwf.setPostParams(postobj);
	}
</script>

<table style="width:100%;border-collapse:collapse;" 
	class="dddl-contentBorder dddl_table" id="UploadFlowFileTable">
	<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="depId" value="${sessionScope.curLoginUser.depId}"/>
    <input type="hidden" name="depName" value="${sessionScope.curLoginUser.depName}"/>
    <input type="hidden" id="flowSubmitInfoJson" value="${flowSubmitInfoJson}"/>
	<tr style="height:29px;">
		<td colspan="3" class="dddl-legend" style="font-weight:bold;">审批材料上传</td>
	</tr>
	<tr>
	    <td style="width: 110px;" ><eve:eveselect clazz="eve-input validate[required]" dataParams="spclType"
                        dataInterface="dictionaryService.findDatasForSelect" 
                         name="spclType" id="spclType" onchange="changeSpclType()" ></eve:eveselect></td>
		<td id="UploadFilesTd">
		</td>
		<td><img id="uploadMaterImg" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
		&nbsp;&nbsp;
		<a target="_blank" href="attachFiles/spmb/jlh.doc" style="cursor: pointer;">交流函下载</a>
		&nbsp;&nbsp;
		<a target="_blank" href="attachFiles/spmb/yjs.doc" style="cursor: pointer;">意见书下载</a>
		</td>
	</tr>
	
	<tr style="height:29px;">
		<td colspan="3" class="dddl-legend" style="font-weight:bold;">回执单上传</td>
	</tr>
	<tr>
	    <td style="width: 110px;" ><eve:eveselect clazz="eve-input validate[required]" dataParams="hzdType"
                        dataInterface="dictionaryService.findDatasForSelect" 
                         name="hzdType" id="hzdType" onchange="changeHzdType()"></eve:eveselect></td>
		<td id="UploadBackFileTd">
		      <%-- 
		      <div>
				<a href="javascript:void(0);" onclick="AppUtil.downLoadFile('');" >
				<font color="blue"> 统计图1.xlsx </font>
				</a>
				<a href="javascript:void(0);" onclick="AppUtil.delUploadMater('');" >
				<font color="red">删除</font>
				</a>
			  </div>
			  --%>
		</td>
		<td><img id="uploadBackFileImg" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
		</td>
	</tr>
	
	
</table>
