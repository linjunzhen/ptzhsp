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
	
	$(function() {
		AppUtil.initWindowForm("setform", function(form, valid) {
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
				var formData = $("#setform").serialize();
				var url = $("#setform").attr("action");
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
							parent.$("#appointTimeGrid").datagrid("reload");
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
		}, "T_CKBS_APPOINTMENT_SET");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="setform" method="post"
		action="callSetController.do?saveOrUpdateSet">
		<div id="setformDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="RECORD_ID" id="RECORD_ID" value="${info.RECORD_ID}">
		<input type="hidden" name="DEPART_ID" id="DEPART_ID" value="${info.DEPART_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<!-- <tr style="height:29px;">
				<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr> -->

			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">开始时间：</span>
					<input type="text" maxlength="16" name="BEGIN_TIME" id="BEGIN_TIME" class="easyui-timespinner" data-options="showSeconds:false,value:'${info.BEGIN_TIME }'" editable="false" style="width:190px; height:26px;" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">结束时间：</span>
					<input type="text" maxlength="16" name="END_TIME" id="END_TIME" class="easyui-timespinner" data-options="showSeconds:false,value:'${info.END_TIME }'" editable="false" style="width:190px; height:26px;" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">该时段可预约数：</span>
					<input type="text" style="width:190px;float:left;" maxlength="3"
					class="eve-input validate[required,custom[onlyNumberSp]]"
					value="${info.ALLOW_BESPEAK_NUMBER}" name="ALLOW_BESPEAK_NUMBER" id="ALLOW_BESPEAK_NUMBER"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>

		</table>
		</div>

		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

