<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,ztree,json2"></eve:resources>
<script type="text/javascript">
function dialogalert(msg,flag){
	art.dialog({
		content : msg,
		icon : flag==null?"warning":flag,
		ok : true
	});
}
	/**
	 * 显示对接配置信息对话框
	 */
	 function showSelectDeparts(){
		parent.$.dialog.open("departmentController/selector.do?allowCount=1&checkCascadeY=&checkCascadeN=", {
			title : "组织机构选择器",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					saveSelectDept(selectDepInfo.departIds,selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
 	function saveSelectDept(departIds,departNames){
		$("#DEPART_NAME").val(departNames);
		$("#DEPART_ID").val(departIds);
	}
	$(function() {
		
		AppUtil.initWindowForm("myform", function(form, valid) {
			if (valid) {
				var bt = $("#BEGIN_TIME").val();
				var et = $("#END_TIME").val();
				if(!bt){
					dialogalert("请输入开始时间","error");
					return;
				}
				if(!et){
					dialogalert("请输入结束时间","error");
					return;
				}
				var bts = bt.split(":");
				var ets = et.split(":");
				var bh = parseInt(bts[0]);
				var bm = parseInt(bts[1]);
				var eh = parseInt(ets[0]);
				var em = parseInt(ets[1]);
				if(bh>eh || (bh==eh && bm>=em)){
					dialogalert("开始时间不得大于等于结束时间","error");
					return;
				}
				var formData = $("#myform").serialize();
				var url = $("#myform").attr("action");
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
							//将提交按钮禁用,防止重复提交
							$("input[type='submit']").attr("disabled", "disabled");
							parent.$("#bespeakTimeGrid").datagrid("reload");
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
		}, "T_BESPEAK_ONLINE_CONFIG");
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="myform" method="post"
		action="bespeakOnlineController.do?doEditForTime">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="ID" id="ID" value="${info.ID}">
		<input type="hidden" name="ONLINE_ID" id="ONLINE_ID" value="${info.ONLINE_ID }">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>

			<tr>
				<td><span style="width: 200px;float:left;text-align:right;margin-right: 5px;">开始时间：</span>
					<input type="text" maxlength="16" name="BEGIN_TIME" id="BEGIN_TIME" class="easyui-timespinner" data-options="showSeconds:false,value:'${info.BEGIN_TIME }'" editable="false" style="width:190px; height:26px;" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 200px;float:left;text-align:right;margin-right: 5px;">结束时间：</span>
					<input type="text" maxlength="16" name="END_TIME" id="END_TIME" class="easyui-timespinner" data-options="showSeconds:false,value:'${info.END_TIME }'" editable="false" style="width:190px; height:26px;" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 200px;float:left;text-align:right;">该时段可预约数：</span>
					<input type="text" style="width:190px;float:left;" maxlength="50"
					class="eve-input validate[required,custom[onlyNumberSp]]"
					value="${info.ALLOW_BESPEAK_NUMBER}" name="ALLOW_BESPEAK_NUMBER" id="ALLOW_BESPEAK_NUMBER"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>

		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>

