<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		
		
		AppUtil.initWindowForm("commercialSealForm", function(form, valid) {
			if (valid) {
				var BAPZ = $("input[name='BAPZ']").val();
				var YZKZDJB = $("input[name='YZKZDJB']").val();				
				if(BAPZ==null||BAPZ==''){
					art.dialog({
						content : "请上传备案凭证！",
						icon : "warning",
						ok : true
					});
					return;
				}
				if(YZKZDJB==null||YZKZDJB==''){
					art.dialog({
						content : "请上传印章刻制登记表！",
						icon : "warning",
						ok : true
					});
					return;
				}
				$('#commercialSealForm').find('input,textarea').prop("disabled", false);
				$('#commercialSealForm').find('select').prop("disabled", false);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#commercialSealForm").serialize();
				var url = $("#commercialSealForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							parent.$("#commercialSealGysGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_COMMERCIAL_SEAL");
		
		//清除前后空格
		$("input,textarea").on('blur', function(event) { 
			$(this).val(trim($(this).val()));
		});
	});
	//清除前后空格
	function trim(str){ 
	  return str.replace(/(^\s*)|(\s*$)/g,""); 
	}
	
	$(function(){
		$.post("executionController.do?getResultFiles",{fileIds:'${commercialSeal.BAPZ}'}, function(resultJson) {
			if(resultJson!="websessiontimeout"){
				$("#fileListDiv1").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
					newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"','BAPZ');\" ><font color=\"red\">删除</font></a></p>"
					newhtml+='</p>';
				}
				$("#fileListDiv1").html(newhtml);
			}
		});
		$.post("executionController.do?getResultFiles",{fileIds:'${commercialSeal.YZKZDJB}'}, function(resultJson) {
			if(resultJson!="websessiontimeout"){
				$("#fileListDiv2").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
					newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"','YZKZDJB');\" ><font color=\"red\">删除</font></a></p>"
					newhtml+='</p>';
				}
				$("#fileListDiv2").html(newhtml);
			}
		});
	});
	
		/**
		* 标题附件上传对话框
		*/
		function openResultFileUploadDialog(name,divId){
			//定义上传的人员的ID
			var uploadUserId = $("input[name='uploadUserId']").val();
			//定义上传的人员的NAME
			var uploadUserName = $("input[name='uploadUserName']").val();
			//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
			art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_SEAL&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
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
							var fileid=$("input[name='"+name+"']").val();
							if(fileid!=""&&fileid!=null){
								$("input[name='"+name+"']").val(fileid+";"+uploadedFileInfo.fileId);
							}else{
								$("input[name='"+name+"']").val(uploadedFileInfo.fileId);
							}
							var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
							spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
							spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
							spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+uploadedFileInfo.fileId+"','"+name+"');\" ><font color=\"red\">删除</font></a></p>"
							$("#"+divId).append(spanHtml);
						}
					}
					art.dialog.removeData("uploadedFileInfo");
				}
			});
		}
		
		function delUploadFile1(fileId,name){
			//AppUtil.delUploadMater(fileId,attacheKey);
			art.dialog.confirm("您确定要删除该文件吗?", function() {
				//移除目标span
				$("#"+fileId).remove();
				var ids=$("input[name='"+name+"']").val();
				var arrayIds=ids.split(";");
				for(var i=0;i<arrayIds.length;i++){
					if(arrayIds[i]==fileId){
						arrayIds.splice(i,1); 
						break;
					}
				}
				$("input[name='"+name+"']").val(arrayIds.join(";"));
			});
		}
		function pushSealInfo(){
			var statusInput="<input name='STATUS' type='hidden' value='2' />";
			$("#commercialSealForm").append(statusInput);
			$("#commercialSealForm").submit();
		}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	.eflowbutton {
		background: #3a81d0;
		border: none;
		padding: 0 10px;
		line-height: 26px;
		cursor: pointer;
		height: 26px;
		color: #fff;
		border-radius: 5px;
	}
	.whf_input{width:97%!important;;height:25px;float:left;}
	.btn1 {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 27px;
		line-height: 27px;
		margin-left: 10px;
		text-align: center;
		width: 50px;
	}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="commercialSealForm" method="post"
		action="commercialSealController.do?saveOrUpdate">
		<div region="center" style="min-height:260px;">
			<div class="eui-slidebox" style="width:96%">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="SEAL_ID" value="${commercialSeal.SEAL_ID}">
				<input type='hidden' name='STATUS' value="3" />
				<input type='hidden' name='PAY_TIME' value="${commercialSeal.PAY_TIME}" />
				<input type='hidden' name='CREATE_TIME' value="${commercialSeal.CREATE_TIME}" />
				<input type='hidden' name='REMARK' value="${commercialSeal.REMARK}" />
				<!--==========隐藏域部分结束 ===========-->
				<div class="syj-title1" style="height:30px;">
					<span>附件信息</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th>备案凭证：</th>
						<td colspan="3"><input  name="BAPZ"  type="hidden"  value="${commercialSeal.BAPZ }">
								<a href="javascript:void(0);" onclick="openResultFileUploadDialog('BAPZ','fileListDiv1')">
									<img src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
							<div style="width:100%;" id="fileListDiv1"></div>
						</td>
					</tr>
					<tr>
						<th>印章刻制登记表：</th>
						<td colspan="3"><input  name="YZKZDJB"  type="hidden"  value="${commercialSeal.YZKZDJB }">
								<a href="javascript:void(0);" onclick="openResultFileUploadDialog('YZKZDJB','fileListDiv2')">
									<img src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
							<div style="width:100%;" id="fileListDiv2"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="提交" type="submit"
							class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

