<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog,swfupload"></eve:resources>
<script type="text/javascript" src="<%=path%>/webpage/cms/article/scripts/infoForm.js"></script>

	<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ModuleForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ModuleForm").serialize();
				var url = $("#ModuleForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("moduleTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_MSJW_SYSTEM_DICTYPE");
        /**
         * 分享栏目--点击事件
         */
        $("#refShares").click(function(_e) {
            infoForm.shareModuleClickHandler(_e);
        });
		/* AppUtil.initUploadControl({
			file_types : "*.png;*.jpg;*.bmp;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "module",
			busTableName : "T_CMS_ARTICLE_MODULE",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "MODULE_PATH_BTN",
			singleFileDivId : "MODULE_PATH_DIV",
			singleFileId : $("input[name='MODULE_LOGO_ID']").val(),
			singleFileFieldName : "MODULE_LOGO_ID",
			limit_size : "1 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='MODULE_LOGO']").val(resultJson.filePath);
				$("input[name='MODULE_LOGO_ID']").val(resultJson.fileId);
				$("#moduleLogo").attr("src","<%=path%>/"+resultJson.filePath);
			}
		}); */
	});
	function delModuleLogo(){
		$("input[name='MODULE_LOGO']").val('');
		$("input[name='MODULE_LOGO_ID']").val('');
		$("#moduleLogo").attr("src","<%=path%>/webpage/cms/module/images/picture404.gif");
	}

	function onSelectClass(o){
		if(o==5){
			$("#link_url_tr").show();
			$("#linkurl").attr("disabled",false); 
		}else{
			$("#link_url_tr").hide();
			$("#linkurl").attr("disabled",true); 
		}
	}
	function openImageUploadDialog(){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传(图片)",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#moduleLogo").attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='MODULE_LOGO']").val(uploadedFileInfo.filePath);
					}else{
						$("#moduleLogo").attr("src",'<%=path%>/webpage/cms/module/images/picture404.gif');
						$("input[name='MODULE_LOGO']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ModuleForm" method="post" action="moduleController.do?saveOrUpdate">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="MODULE_ID" value="${module.MODULE_ID}">
	    <input type="hidden" name="PARENT_ID" value="${module.PARENT_ID}">
		<input type="hidden" name="MODULE_LOGO" value="${module.MODULE_LOGO}">
		<input type="hidden" name="MODULE_LOGO_ID" value="${module.MODULE_LOGO_ID}">
		<input type="hidden" name="WEBSITE" value="1">
		<input type="hidden" name="MODULE_DELETE" value="0">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${module.PARENT_NAME}
				</td>
				<td>
				<span style="width: 100px;float:left;text-align:right;">栏目ID：</span>
				${module.MODULE_ID}
				</td>
			</tr>
			
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">栏目名称：</span>
					<input type="text" style="width:150px;float:left;" value="${module.MODULE_NAME}" maxlength="15" class="eve-input validate[required]" name="MODULE_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">栏目别名：</span>
					<input type="text" style="width:150px;float:left;" value="${module.MODULE_ALIAS}" maxlength="15" class="eve-input validate[]" name="MODULE_ALIAS" />
				</td>
			</tr>
			<tr>
				<td >
					<span style="width:100px;float:left;text-align:right;">栏目类型：</span>
					<select defaultemptytext="请选择栏目类型" dataparams="SerialSeqType" datainterface="dictionaryService.findDatasForSelect" class="eve-input validate[required]" name="PROPERTY_CLASS" id="form-validation-field-0" onChange="onSelectClass(this.value)">
						<option value="">请选择栏目类型</option>
						<option value="1" <c:if test="${module.PROPERTY_CLASS==1}"> selected = "selected"</c:if>>普通栏目</option>
						<option value="2" <c:if test="${module.PROPERTY_CLASS==2}"> selected = "selected"</c:if>>专栏栏目</option>						
						<option value="3" <c:if test="${module.PROPERTY_CLASS==3}"> selected = "selected"</c:if>>链接栏目</option>						
						<option value="4" <c:if test="${module.PROPERTY_CLASS==4}"> selected = "selected"</c:if>>多媒体栏目</option>
						<option value="5" <c:if test="${module.PROPERTY_CLASS==5}"> selected = "selected"</c:if>>外链栏目</option>
					</select>		
					<font class="dddl_platform_html_requiredFlag">*</font>		
				</td>
				<td>
				<span style="width: 100px;float:left;text-align:right;">状态：</span>
					可用<input type="radio" <c:if test="${module.MODULE_STATUS==1||module.MODULE_STATUS==null}">checked="checked"</c:if> value="1" name="MODULE_STATUS">
					不可用<input type="radio" <c:if test="${module.MODULE_STATUS==0}">checked="checked"</c:if> value="0" name="MODULE_STATUS">
				</td>
			</tr>
			<tr id="link_url_tr" <c:if test="${module.PROPERTY_CLASS!=5||module.PROPERTY_CLASS==null}">style="display:none;"</c:if>>
				<td colspan="2" >					
					<span style="width: 100px;float:left;text-align:right;">外链URL：</span>
					<input type="text" style="width:455px;float:left;"
						value="${module.LINKURL}" maxlength="256"
						class="eve-input validate[required],custom[url]" name="LINKURL" id="linkurl" <c:if test="${module.PROPERTY_CLASS!=5||module.PROPERTY_CLASS==null}">disabled="disabled"</c:if>/> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">列表页模版：</span>
					<input type="text" style="width:150px;float:left;" value="${module.LIST_TEMPLATE}" maxlength="30" class="eve-input validate[]" name="LIST_TEMPLATE" /> 
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">详细页模版：</span>
					<input type="text" style="width:150px;float:left;" value="${module.DETAIL_TEMPLATE}" maxlength="30" class="eve-input validate[]" name="DETAIL_TEMPLATE" />
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">分享栏目：</span>
					<input type="text"   id="refShares" style="width:150px;float:left;" value="${module.SHARE_MODULENAME}" maxlength="300" class="eve-input validate[]" name="SHARE_MODULENAME" />
					<input type="text"   hidden="hidden" style="width:150px;float:left;" value="${module.SHARE_MODULEID}" maxlength="300" class="eve-input validate[]" name="SHARE_MODULEID" />
				</td>

			</tr>
			<tr>
				<td colspan="2">
					<span style="width: 100px;float:left;text-align:right;">栏目LOGO：</span>
					<div style="float:left;">				
					<c:if test="${module.MODULE_LOGO!=null}">					
						<img id="moduleLogo" width="120" height="95" border="0" src="${_file_Server }${module.MODULE_LOGO}" style="border:0;"/>
					</c:if>
					<c:if test="${module.MODULE_LOGO==null}">
						<img id="moduleLogo" width="120" height="95" border="0" src="<%=path%>/webpage/cms/module/images/picture404.gif" style="border:0;"/>
					</c:if>
					</div>
					<div>
					<input type="button" value="上传图片..." onclick="openImageUploadDialog();">
					<input style="width: 42px; height: 22px; color: #336699;" type="button" onclick="delModuleLogo();" class="z-dlg-button z-dialog-cancelbutton" value="删除">
					</div>
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
