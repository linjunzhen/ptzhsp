<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,swfupload"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("GuideTypeInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#GuideTypeInfoForm").serialize();
				var url = $("#GuideTypeInfoForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("busTypeTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_WSBS_BUSTYPE");
		
		/* AppUtil.initUploadControl({
            file_types : "*.png;*.jpg;*.bmp;",
            file_upload_limit : 0,
            file_queue_limit : 1,
            uploadPath : "BUSTYPE",
            busTableName : "T_WSBS_BUSTYPE",
            uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
            uploadButtonId : "ICON_PATH_BTN",
            singleFileDivId : "ICON_PATH_DIV",
            singleFileId : $("input[name='ICON_ID']").val(),
            singleFileFieldName : "ICON_ID",
            limit_size : "1 MB",
            uploadSuccess : function(resultJson) {
                $("input[name='ICON_PATH']").val(resultJson.filePath);
                $("input[name='ICON_ID']").val(resultJson.fileId);
                $("#iconLogo").attr("src","<%=path%>/"+resultJson.filePath);
            }
        }); */
	});
	function openImageUploadDialog(){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传(图片)",
			width: "480px",
			height: "240px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#iconLogo").attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='ICON_PATH']").val(uploadedFileInfo.filePath);
						$("input[name='ICON_ID']").val(uploadedFileInfo.fileId);
					}else{
						$("#iconLogo").attr("src",'<%=path%>/webpage/cms/module/images/picture404.gif');
						$("input[name='ICON_PATH']").val('');
						$("input[name='ICON_ID']").val(uploadedFileInfo.fileId);
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function delBusTypeLogo(){		
		$("input[name='ICON_PATH']").val('');
		$("input[name='ICON_ID']").val('');
		$("#iconLogo").attr("src","<%=path%>/webpage/cms/module/images/twQualificationPhoto.jpg");
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="GuideTypeInfoForm" method="post" action="busTypeController.do?saveOrUpdate">
	    <div  id="GuideTypeInfoFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TYPE_ID" value="${busType.TYPE_ID}">
	    <input type="hidden" name="PARENT_ID" value="${busType.PARENT_ID}">
	           <input type="hidden" name="CREATE_USERID" value="${sessionScope.curLoginUser.userId}">      
        <input type="hidden" name="CREATE_USER" value="${sessionScope.curLoginUser.fullname}"> 
        <input type="hidden" name="ICON_PATH" value="${busType.ICON_PATH}">
        <input type="hidden" name="ICON_ID" value="${busType.ICON_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${busType.PARENT_NAME}
				</td>
				<td>
					<c:if test="${busType.PARENT_ID=='402883494fd4f3aa014fd4fc68260003'}">
					<span style="width: 100px;float:left;text-align:right;">前台是否显示：</span>
					<eve:eveselect clazz="eve-input" dataParams="YesOrNo" 
				         value="${busType.IS_SHOW}" 
				         dataInterface="dictionaryService.findDatasForSelect" 
				         name="IS_SHOW">
				    </eve:eveselect>
					
					</c:if> 
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">类别名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${busType.TYPE_NAME}" maxlength="30"
					 class="eve-input validate[required]" name="TYPE_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td >
				<span style="width:100px;float:left;text-align:right;">类别编码：</span>
				<c:if test="${busType.TYPE_CODE!=null}">
					   ${busType.TYPE_CODE}
				</c:if> 
				<c:if test="${busType.TYPE_CODE==null}">
				<input
					type="text" style="width:150px;float:left;" 
					value="${busType.TYPE_CODE}" maxlength="30" id="TYPE_CODE"
					class="eve-input validate[custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]]" name="TYPE_CODE" /> 
				</c:if>
				</td>
			</tr>
			<tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">图标：</span>
                    <div style="float:left;">         
                    <c:if test="${busType.ICON_PATH!=null}">                   
                        <img id="iconLogo" width="120" height="95" border="0" src="${_file_Server }${busType.ICON_PATH}" style="border:0;"/>
                    </c:if>
                    <c:if test="${busType.ICON_PATH==null}">
                        <img id="iconLogo" width="120" height="95" border="0" src="<%=path%>/webpage/cms/module/images/picture404.gif" style="border:0;"/>
                    </c:if>
                    </div>
					<input type="button" value="上传图片..." onclick="openImageUploadDialog();">
					<input style="width: 42px; height: 22px; color: #336699;" type="button" onclick="delBusTypeLogo();"
					 class="z-dlg-button z-dialog-cancelbutton" value="删除">
                    
                    <div style="width:100%;display:none;" id="ICON_PATH_DIV"></div>
                </td>
            </tr>
            <tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">网格APP图标：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${busType.APP_ICON_NAME}" maxlength="30"
					 class="eve-input" name="APP_ICON_NAME" /> 
					
				</td>
			</tr>
			
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
