<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("uploadInfoForm", function(form, valid) {
			if (valid) {
				var lic = $("input[name='LICENSE_PATH']").val();
				var card = $("input[name='CARD_PATH']").val();
				var apply = $("input[name='APPLY_PATH']").val();
				if(lic==""||lic==undefined){
					parent.art.dialog({
						content : "请先上传营业执照",
						icon : "succeed",
						time : 3,
						ok : true
					});
					return false;
				}else if(card==""||card==undefined){
					parent.art.dialog({
						content : "请先上传法人身份证",
						icon : "succeed",
						time : 3,
						ok : true
					});
					return false;
				}else if(apply==""||apply==undefined){
					parent.art.dialog({
						content : "请先上传申请书",
						icon : "succeed",
						time : 3,
						ok : true
					});
					return false;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#uploadInfoForm").serialize();
				var url = $("#uploadInfoForm").attr("action");
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
							parent.$("#UploadDataGrid").datagrid("reload");
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
		}, "T_COMMERCIAL_BANKFILE");

		AppUtil.initUploadControl({
			file_types : "*.jpg;*.jpeg;*.png;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "bankDeal",
			busTableName : "T_COMMERCIAL_BANKFILE",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "LICENSE_PATH_BTN",
			singleFileDivId : "LICENSE_PATH_DIV",
			singleFileId : $("input[name='LICENSE_PATH']").val(),
			singleFileFieldName : "LICENSE_PATH",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='LICENSE_PATH']").val(resultJson.fileId);
			}
		});
		AppUtil.initUploadControl({
			file_types : "*.jpg;*.jpeg;*.png;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "bankDeal",
			busTableName : "T_COMMERCIAL_BANKFILE",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "CARD_PATH_BTN",
			singleFileDivId : "CARD_PATH_DIV",
			singleFileId : $("input[name='CARD_PATH']").val(),
			singleFileFieldName : "CARD_PATH",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='CARD_PATH']").val(resultJson.fileId);
			}
		});
		AppUtil.initUploadControl({
			file_types : "*.jpg;*.jpeg;*.png;*.pdf;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "bankDeal",
			busTableName : "T_COMMERCIAL_BANKFILE",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "APPLY_PATH_BTN",
			singleFileDivId : "APPLY_PATH_DIV",
			singleFileId : $("input[name='APPLY_PATH']").val(),
			singleFileFieldName : "APPLY_PATH",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='APPLY_PATH']").val(resultJson.fileId);
			}
		});

	});
	
  
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="uploadInfoForm" method="post"
		action="bankDealController.do?saveOrUpdate">
		<div id="uploadInfoFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="EXE_ID" value="${uploadInfo.EXE_ID}">
			<input type="hidden" name="LICENSE_PATH" value="${uploadInfo.LICENSE_PATH}">
			<input type="hidden" name="CARD_PATH" value="${uploadInfo.CARD_PATH}">
			<input type="hidden" name="APPLY_PATH" value="${uploadInfo.APPLY_PATH}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>上传信息</a></div></td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">申报号：</span>
					    ${uploadInfo.EXE_ID}
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">申请书：</span>
						<a id="APPLY_PATH_BTN"></a>
						<div id="APPLY_PATH_DIV"></div>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">营业执照：</span>
						<a id="LICENSE_PATH_BTN"></a>
						<div id="LICENSE_PATH_DIV"></div>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">法人身份证：</span>
						<a id="CARD_PATH_BTN"></a>
						<div id="CARD_PATH_DIV"></div>
					</td>
				</tr>
			</table>


		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

