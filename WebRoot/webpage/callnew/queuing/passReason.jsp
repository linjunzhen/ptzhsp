<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("PassReasonForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#PassReasonForm").serialize();
				var url = $("#PassReasonForm").attr("action");
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
							parent.$("#callingNewGrid").datagrid("reload");
							parent.$("#QueuingDayGrid").datagrid("reload");
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
		},null);
	});
	//选中常用意见时更换意见内容
	function selectCommonOpinion() {
		var opinionContent = $("select[name='OPINION_CONTENT']").children('option:selected').text();
		if ($("select[name='OPINION_CONTENT']").children('option:selected').val() == "")
			opinionContent = "";
		$("textarea[name='PASS_REASON']").val(opinionContent);
	}
	//增加为常用意见
	function addCommonOpinion() {
		var opinionContent = $("textarea[name='PASS_REASON']").val();
		if (opinionContent.replace(/\s/g, "") == "") {
			parent.art.dialog({
				content : "请填写您的意见!",
				icon : "error",
				ok : true
			});
		} else {
			url = "commonOpinionController.do?saveCommonOpinion";
			formData = "&BUSINESS_NAME=passReason&BUSINESS_TYPE=过号原因&OPINION_CONTENT=" + opinionContent;
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if (resultJson.success) {
						$("select[name='OPINION_CONTENT']").find("option:first").remove();
						$("select[name='OPINION_CONTENT']").prepend("<option value='" + resultJson.jsonString + "'>" + opinionContent + "</option>");
						$("select[name='OPINION_CONTENT']").prepend("<option value=''>==选择常用意见==</option>");
						$("select[name='OPINION_CONTENT']").val(resultJson.jsonString);
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
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
	//删除选中的常用意见
	function removeCommonOpinion() {
		if ($("select[name='OPINION_CONTENT']").children('option:selected').val() == "") {
			parent.art.dialog({
				content : "请选择要删除的常用意见!",
				icon : "error",
				ok : true
			});
		} else {
			art.dialog.confirm("您确定要删除选择的常用意见吗?", function() {
				var opinionContent = $("select[name='OPINION_CONTENT']").children('option:selected').text();
				url = "commonOpinionController.do?removeCommonOpinion";
				formData = "&BUSINESS_NAME=passReason&BUSINESS_TYPE=过号原因&OPINION_CONTENT=" + opinionContent;
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							$("select[name='OPINION_CONTENT']").children("option:selected").remove();
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			});
		}
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="PassReasonForm" method="post" action="newCallController.do?queuingPass">
	    <div id="PassReasonFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="recordId" value="${recordId}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<!-- <tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">过号原因</td>
				</tr> -->
				<tr>
					<td>
						<div style="margin: 0 0 5px 6px;">
						<eve:eveselect clazz="eve-input eve-select-width" dataParams="passReason,过号原因" 
							 dataInterface="commonOpinionService.findCommonOpinionList" value=""
							  defaultEmptyText="==选择常用意见==" onchange="selectCommonOpinion();" name="OPINION_CONTENT">
						</eve:eveselect>
						<span style="width:30px;display:inline-block;text-align:right;">
						<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="addCommonOpinion();"
							style="cursor:pointer;vertical-align:middle;" title="添加到常用意见">
						</span>
						<span style="width:30px;display:inline-block;text-align:right;">
						<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeCommonOpinion();" 
							style="cursor:pointer;vertical-align:middle;" title="删除选中的常用意见">
						</span>
						</div>
						<textarea rows="6" cols="60" name="PASS_REASON" maxlength="200" class="validate[[required],maxSize[200]]" style="margin-left:7px;"></textarea><font
						class="dddl_platform_html_requiredFlag">*</font>
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
