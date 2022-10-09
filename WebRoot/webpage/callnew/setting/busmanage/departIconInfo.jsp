<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,swfupload"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DepartIconInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#DepartIconInfoForm").serialize();
				var url = $("#DepartIconInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
						    parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							parent.$("#DepartIconGrid").datagrid("reload");
							AppUtil.closeLayer();
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
		}, "T_WSBS_iconInfo");
		
		AppUtil.initUploadControl({
            file_types : "*.png;*.jpg;*.bmp;",
            file_upload_limit : 0,
            file_queue_limit : 1,
            uploadPath : "CKBS_iconInfo",
            busTableName : "T_CKBS_BUSINESSDATA_ICON",
            uploadUserId : $("input[name='CREATE_USERID']").val(),
            uploadButtonId : "ICON_PATH_BTN",
            singleFileDivId : "ICON_PATH_DIV",
            singleFileId : $("input[name='ICON_ID']").val(),
            singleFileFieldName : "ICON_ID",
            limit_size : "1 MB",
            uploadSuccess : function(resultJson) {
                $("input[name='ICON_PATH']").val(resultJson.filePath);
                $("input[name='ICON_ID']").val(resultJson.fileId);
                $("#iconLogo").attr("style","border:0;background-color: #1c294b");
                $("#iconLogo").attr("src","<%=path%>/"+resultJson.filePath);
            }
        });
	});
/**
* 标题附件上传对话框
*/
function openCkbsIconFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=image&materType=image&busTableName=T_MSJW_SYSTEM_SYSUSER', {
		title: "上传",
		width: "620px",
		height: "300px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					$("input[name='ICON_PATH']").val(uploadedFileInfo.filePath);
					$("input[name='ICON_ID']").val(uploadedFileInfo.fileId);
					$("#iconLogo").attr("style","border:0;background-color: #1c294b");
					$("#iconLogo").attr("src",__file_server + uploadedFileInfo.filePath);
				}else{
					$("#iconLogo").attr("src",'<%=path%>/webpage/cms/module/images/picture404.gif');						
					$("input[name='ICON_PATH']").val("");
					$("input[name='ICON_ID']").val("");
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DepartIconInfoForm" method="post" action="callSetController.do?saveOrUpdateDepIcon">
	    <div id="DepartIconInfoFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="RECORD_ID" value="${iconInfo.RECORD_ID}">
	    <input type="hidden" name="CREATE_USERID" value="${sessionScope.curLoginUser.userId}">      
        <input type="hidden" name="CREATE_USER" value="${sessionScope.curLoginUser.fullname}"> 
        <input type="hidden" name="ICON_PATH" value="${iconInfo.ICON_PATH}">
        <input type="hidden" name="ICON_ID" value="${iconInfo.ICON_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
						
			<tr>
				<td>
				<span style="width: 120px;float:left;text-align:right;">单位/部门名称：</span>
				${iconInfo.DEPART_NAME}
				</td>
			</tr>
			<tr>
                <td colspan="2">
                <span style="width: 120px;float:left;text-align:right;">图标：</span>
                    <div style="float:left;">         
                    <c:if test="${iconInfo.ICON_PATH!=null}">                   
                        <img id="iconLogo" width="120" height="95" border="0" src="${_file_Server }${iconInfo.ICON_PATH}" style="border:0;background-color: #1c294b;"/>
                    </c:if>
                    <c:if test="${iconInfo.ICON_PATH==null}">
                        <img id="iconLogo" width="120" height="95" border="0" src="<%=path%>/webpage/cms/module/images/picture404.gif" style="border:0;"/>
                    </c:if>
                    </div>					
					<a href="javascript:void(0);" onclick="openCkbsIconFileUploadDialog()">
						<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
                    <div style="width:100%;display:none;" id="ICON_PATH_DIV"></div>
                </td>
            </tr>
			<%-- <tr>
				<td><span
					style="width: 100px;float:left;text-align:right;">所属大厅编号：</span>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="roomNo"
					dataInterface="dictionaryService.findDatasForSelect" value="${iconInfo.BELONG_ROOM}"
					defaultEmptyText="选择大厅编号"  name="BELONG_ROOM">
					</eve:eveselect>	
					<font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr> --%>
			
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
