
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,ztree,swfupload,eweb"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ProductForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#ProductForm").serialize();
				var url = $("#ProductForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.layer.msg(resultJson.msg, 1, 1);
							parent.$("#ProductGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							layer.msg(resultJson.msg, 2, 1);
						}
					}
				});
			}
		}, "T_DEMO_PRODUCT");

		AppUtil.initUploadControl({
			file_types : "*.jpg;*.jpeg;*.gif;*.png;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "product",
			busTableName : "T_DEMO_PRODUCT",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "IMAGE_PATH_BTN",
			uploadFileType : "image",
			uploadImageId : "IMAGE_PATH_IMG",
			uploadImageFieldName : "IMAGE_PATH",
			limit_size : "10 MB",
			uploadSuccess : function(file, serverData, responseReceived) {
				var resultJson = $.parseJSON(serverData);
				var filePath = resultJson.filePath;
				$("#IMAGE_PATH_IMG").attr("src", __attachFileUrl + filePath);
				$("input[name='IMAGE_PATH']").val(filePath);
			}
		});
		AppUtil.initUploadControl({
			file_types : "*.docx;*.doc;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "product",
			busTableName : "T_DEMO_PRODUCT",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "MANUAL_BOOK_BTN",
			singleFileDivId : "MANUAL_BOOK_DIV",
			singleFileId : $("input[name='MANUAL_BOOK']").val(),
			singleFileFieldName : "MANUAL_BOOK",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='MANUAL_BOOK']").val(resultJson.fileId);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ProductForm" method="post"
		action="productController.do?saveOrUpdate">
		<div id="ProductFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<%-- 
            <input type="hidden" name="CURLOGIN_USERID" value="${session111Scope.curLoginUser.userId}">
            --%>
            <input type="hidden" name="MANUAL_BOOK" value="${product.MANUAL_BOOK}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">其它信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">产品图片：</span>
						<div
							style="float:left;width:150px; overflow:hidden; text-align:center;">
							<img id="IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
								height="107px" width="125px"><a id="IMAGE_PATH_BTN"></a>
						</div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">产品说明书：</span>
						<a id="MANUAL_BOOK_BTN"></a>
					<div id="MANUAL_BOOK_DIV"></div></td>
				</tr>
			</table>


		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

