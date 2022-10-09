<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,swfupload"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("HeadPortraitInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#HeadPortraitInfoForm").serialize();
				var url = $("#HeadPortraitInfoForm").attr("action");
				var photo = $("input[name='PHOTO_PATH']").val();
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
							var photoPath = photo;
							parent.$("#userphoto").attr("src",photoPath);
							parent.$("#userphoto2").attr("src",photoPath);
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
		}, null);
		
	});
		
/**
* 标题附件上传对话框
*/
function openPortraitFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=image&materType=image&busTableName=T_MSJW_SYSTEM_SYSUSER', {
		title: "上传头像",
		width: "620px",
		height: "240px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					$("input[name='PHOTO_PATH']").val(uploadedFileInfo.filePath);
					$("input[name='PHOTO_ID']").val(uploadedFileInfo.fileId);
					$("#iconLogo").attr("src",__file_server + uploadedFileInfo.filePath);
				}else{
					$("#iconLogo").attr("src",'<%=path%>/webpage/cms/module/images/picture404.gif');						
					$("input[name='PHOTO_PATH']").val("");
					$("input[name='PHOTO_ID']").val("");
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="HeadPortraitInfoForm" method="post" action="sysUserController.do?updateHeadPortrait">
	    <div  id="HeadPortraitInfoFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="USER_ID" value="${sessionScope.curLoginUser.userId}">  
        <input type="hidden" name="PHOTO_PATH" value="${PHOTO_PATH}">  
        <input type="hidden" name="PHOTO_ID" value="${PHOTO_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>头像信息</a></div></td>
			</tr>
			
			<tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">头像：</span>
                    <div style="float:left;">         
                    <c:if test="${PHOTO_PATH!=null}">                   
                        <img id="iconLogo" width="120" height="95" border="0" src="${_file_Server }${PHOTO_PATH}" style="border:0;"/>
                    </c:if>
                    <c:if test="${PHOTO_PATH==null}">
                        <img id="iconLogo" width="120" height="95" border="0" src="<%=path%>/webpage/cms/module/images/picture404.gif" style="border:0;"/>
                    </c:if>
                    </div>						
					<a href="javascript:void(0);" onclick="openPortraitFileUploadDialog()">
						<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
                </td>
            </tr>
			
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
