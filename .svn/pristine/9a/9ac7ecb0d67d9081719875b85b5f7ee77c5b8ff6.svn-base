
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree,swfupload"></eve:resources>
<script type="text/javascript"
	src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	 <script type="text/javascript" src="<%=path%>/plug-in/jqueryUpload/AppUtilNew.js"></script> 
	

<script type="text/javascript">
    $(function() {
        AppUtil.initWindowForm("flowConfigTypeForm", function(form, valid) {
            if($("#FLOW_PIC_ID").val()==''){
	            $("#FLOW_PIC_ID").val($("#FLOW_PIC_ID_OLD").val());
            }
            if($("#FLOW_PIC_ID").val()==''){
                parent.art.dialog({
                    content : '请上传流程图',
                    icon : "error",
                    ok : true
                });
            }else{
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#flowConfigTypeForm").serialize();
                var url = $("#flowConfigTypeForm").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "succeed",
                                time : 3,
                                ok : true
                            });
//                             parent.$.fn.zTree.getZTreeObj("flowTypeTree")
//                                     .reAsyncChildNodes(null, "refresh");

                            // 							parent.$("#flowConfigTypeCateGrid").datagrid("reload");
                            // 							parent.$("#flowConfigTypeTypeGrid").datagrid("reload");
                            // 							parent.$("#flowConfigTypeTypeGrid2").datagrid("reload");
                            parent.$("#FlowTypeGrid").datagrid("reload");
                            AppUtil.closeLayer();
                        } else {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "error",
                                ok : true
                            });
                        }
                    }
                });
            }
            }
        }, "MZT_SYSTEM_flowConfigType");
        /* 
        AppUtilNew.initUploadControl({
    		file_types : "*.jpg;*.png;",
    		file_upload_limit : 1,
    		file_queue_limit : 0,
    		uploadPath : "system",
    		busTableName : "T_MSJW_SYSTEM_NOTICE",
    		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
    		uploadButtonId : "RESULT_FILE_BTN",
    		singleFileDivId : "RESULT_FILE_DIV",
    		limit_size : "20 MB",
    		uploadTableId:"AUDID_UPLOADTABLE",
    		uploadSuccess : function(resultJson) {
    		    var fileurl=$("input[name='NOTICE_FILE_URLS']").val();
    			var fileid=$("input[name='FLOW_PIC_ID']").val();
    				if(fileurl!=""&&fileurl!=null){
    					$("input[name='NOTICE_FILE_URLS']").val(fileurl+";"+resultJson.filePath);
    					$("input[name='FLOW_PIC_ID']").val(fileid+";"+resultJson.fileId);
    				}else{
    					$("input[name='NOTICE_FILE_URLS']").val(resultJson.filePath);
    					$("input[name='FLOW_PIC_ID']").val(resultJson.fileId);
    				}
    				
    				//alert($("#RESULT_FILE_DIV").children("a").eq(1).html());
    				//$("#RESULT_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
    				//获取文件ID
    		        var fileId = resultJson.fileId;
    	            //获取文件名称
    	            var fileName = resultJson.fileName;
    	            //获取材料KEY
    	            var attachKey =resultJson.attachKey;
    	            //获取文件路径
    	            var filePath = resultJson.filePath;
    	            //获取文件的类型
    	            var fileType = resultJson.fileType;
    	            //获取是否是图片类型
    	            var isImg = resultJson.isImg;
    				var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
    	            spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
    	            spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
    	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
    	            $("#fileListDiv").append(spanHtml); 
    		}
    	}); */ 
        
    	var fileids=$("#FLOW_PIC_ID_OLD").val();
		$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    		 if(resultJson!="websessiontimeout"){
	    		 	$("#fileListDiv").html("");
	    		 	var newhtml = "";
	    		 	var list=resultJson.rows;
	    		 	for(var i=0; i<list.length; i++){
	    		 		newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 29px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 		newhtml+=list[i].FILE_NAME+'</a>';
	    		 		newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\''+list[i].ATTACH_KEY+'\');"><font color="red">删除</font></a>';
	    		 		newhtml+='</p>';
	    		 	} 
	    		 	$("#fileListDiv").html(newhtml);
	    		 }
         });	

    });
    
	
/**
 * 标题附件上传对话框
 */
function openFlowTypeFileUploadDialog(){
	var materType = "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.zip;*.rar;*.pdf;";
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType='
	+materType+'&busTableName=T_MSJW_SYSTEM_NOTICE', {
		title: "上传(附件)",
		width: "620px",
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
					
					
    				$("input[name='NOTICE_FILE_URLS']").val(uploadedFileInfo.filePath);
    				$("input[name='FLOW_PIC_ID']").val(uploadedFileInfo.fileId);
    				
    				var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
    	            spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
    	            spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
    	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
    	            $("#fileListDiv").html(spanHtml); 					
				
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}

function delUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		$("input[name='NOTICE_FILE_URLS']").val("");
		$("input[name='FLOW_PIC_ID']").val("");
	});
}
    	function onSelectClass(o){
    		if(o==1){
    			$("#resultcontent_tr").show();
    			$("#resultcontent").attr("disabled",false); 
    		}else{
    			$("#resultcontent_tr").hide();
    			$("#resultcontent").attr("disabled",true); 
    		}
    	}
</script>
</head>

<body class="eui-diabody" style="margin: 0px;">
	<form id="flowConfigTypeForm" method="post"
		action="flowConfigTypeController.do?saveOrUpdate">
		<div id="flowConfigTypeFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="TYPE_ID" value="${flowConfigType.TYPE_ID}">
			<input type="hidden" name="FLOW_PIC_ID" id="FLOW_PIC_ID" >
			<input type="hidden" name="FLOW_PIC_ID_OLD" id="FLOW_PIC_ID_OLD" value="${flowConfigType.FLOW_PIC_ID}">
			<input type="hidden" name="NOTICE_FILE_URLS" id="NOTICE_FILE_URLS" value="${noticeInfo.NOTICE_FILE_URLS}">
			
			<!--==========隐藏域部分结束 ===========-->
			<table style="width: 100%; border-collapse: collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td  class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">流程名称：</span> <input
						type="text" style="width: 400px; float: left;" maxlength="20"
						class="eve-input validate[required]" value="${flowConfigType.TYPE_NAME}"
						name="TYPE_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">审批流程类型：</span>
						<eve:eveselect clazz="eve-input validate[required]"
							dataParams="SPLCLX" value="${flowConfigType.SPLCLX}"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="==选择审批流程类型==" name="SPLCLX"></eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">审批流程：</span> 
						<eve:eveselect clazz="eve-input validate[required]"
							dataParams="LCPZ" value="${flowConfigType.FLOW_TYPE_DIC_CODE}"
							dataInterface="dicTypeService.findByParentCodeForSelect"
							defaultEmptyText="==选择审批流程==" name="FLOW_TYPE_DIC_CODE"></eve:eveselect>
							<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">审批说明：</span> <input
						type="text" style="width: 400px; float: left;" maxlength="98"
						class="eve-input " value="${flowConfigType.SPLCSM}"
						name="SPLCSM" /></td>
				</tr>
				
				<tr>
					<td ><span style="width: 120px;float:left;text-align:right;">流程示意图：</span>
					<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div>
					<a href="javascript:void(0);" onclick="openFlowTypeFileUploadDialog()">
						<img id="RESULT_FILE_BTN" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
					</a></td>
				</tr>
				<tr>
					<td >
					<span style="width: 120px;float:left;text-align:right;">已上传示意图：</span>
						<div style="width:100%;" id=fileListDiv></div>
					</td>
				</tr>
			
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

