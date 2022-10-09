<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	$(function() {
		initMater($("input[name='LICENSE_PATH']").val(),'LICENSE_PATH_DIV');
		initMater($("input[name='CARD_PATH']").val(),'CARD_PATH_DIV');
		initMater($("input[name='APPLY_PATH']").val(),'APPLY_PATH_DIV');
	});
	
	function initMater(fileId,divId){	
		$.post("fileAttachController.do?get", {
			fileId : fileId
		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			var fileName = resultJson.FILE_NAME;
			var fileSize = resultJson.FILE_SIZE;
			var fileId = resultJson.FILE_ID;
			if(fileName.length>30){
				fileName = fileName.substr(0,30)+"...";
				$("#"+divId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+fileName+"&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;&nbsp;</div>");
				$("#"+divId).append("<a href='javascript:void(0);' style='float:left;height:28px; line-height:28px;' onclick='downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
			}else{
				$("#"+divId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+fileName+"&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;&nbsp;</div>");
				$("#"+divId).append("<a href='javascript:void(0);' style='float:left;height:28px; line-height:28px;' onclick='downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
			}
		});
	}
	
	/* function downloadApply(){		
		var exeId = $("input[name='EXE_ID']").val();
		var itemCode = $("input[name='itemCode']").val();
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApply.do?exeId="+exeId
			+"&itemCode="+itemCode;
	} */
	
	function downLoadFile(fileId){
		
		window.location.href=__ctxPath+"/bankDealController.do?downLoadMarkImage&fileId="+fileId;
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="downloadInfoForm" method="post" action="">
		<div id="downloadInfoFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="EXE_ID" value="${uploadInfo.EXE_ID}">
			<input type="hidden" name="itemCode" value="${uploadInfo.itemCode}">
			<input type="hidden" name="LICENSE_PATH" value="${uploadInfo.LICENSE_PATH}">
			<input type="hidden" name="CARD_PATH" value="${uploadInfo.CARD_PATH}">
			<input type="hidden" name="APPLY_PATH" value="${uploadInfo.APPLY_PATH}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">申报号：</span>
					    ${uploadInfo.EXE_ID}
					</td>
				</tr>
				<!-- <tr>
					<td><span style="width: 120px;float:left;text-align:right;">开户申请表：</span>
						<div id="apply">
							<a href="javascript:void(0);" onclick="javascript:downloadApply();" style='float:left;height:28px; line-height:28px;' >下载</a>
						</div>
					</td>
				</tr> -->
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">开户申请书：</span>
						<div id="APPLY_PATH_DIV"></div>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">营业执照：</span>
						<div id="LICENSE_PATH_DIV"></div>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">法人身份证：</span>
						<div id="CARD_PATH_DIV"></div>
					</td>
				</tr>
			</table>


		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons" >
				<input value="确定" type="button" onclick="AppUtil.closeLayer();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<!-- <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" /> -->
			</div>
		</div>
	</form>

</body>

