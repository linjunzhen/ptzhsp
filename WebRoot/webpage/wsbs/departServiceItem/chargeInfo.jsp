<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ChargeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ChargeForm").serialize();
				var url = $("#ChargeForm").attr("action");
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
							//parent.$("#sfmxGrid").datagrid("reload");
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
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ChargeForm" method="post"
		action="departServiceItemController.do?saveOrUpdateCharge">
		<div id="ChargeFormDiv" data-options="region:'center',split:true,border:false">
			<%--==========隐藏域部分开始 ===========--%>
			<input type="hidden" name="RECORD_ID" value="${chargeInfo.RECORD_ID}">
			<input type="hidden" name="ITEM_ID" value="${chargeInfo.ITEM_ID}">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">执行单位名称：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILORGNAME}" name="CHARGEDETAILORGNAME" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">执行单位编码：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILORGCODE}" name="CHARGEDETAILORGCODE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">收费项目名称：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILNAME}" name="CHARGEDETAILNAME" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">收费项目代码：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILCODE}" name="CHARGEDETAILCODE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">计量单位：</span>
						<input type="text" style="width:180px;float:left;" maxlength="8"
							class="eve-input validate[required]"
							value="${chargeInfo.UNITS}" name="UNITS" />
						<font class="dddl_platform_html_requiredFlag">*</font>   &nbsp;例，元/科
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">执收标准：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILSTANDARD}" name="CHARGEDETAILSTANDARD" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">执收数量：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILAMOUNT}" name="CHARGEDETAILAMOUNT" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">排序号：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="64"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGEDETAILORDERID}" name="CHARGEDETAILORDERID" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">是否启用：</span>
						<eve:radiogroup typecode="YORN" width="150" defaultvalue="1"
							value="${chargeInfo.CHARGEDETAILSTATUS}" fieldname="CHARGEDETAILSTATUS">
						</eve:radiogroup>
					</td>
				</tr>
				
				<!-- 
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">收费明细：</span>						
						<input type="text" style="width:180px;float:left;" maxlength="32"
								class="eve-input validate[required]"
								value="${chargeInfo.CHARGE_DETAIL}" name="CHARGE_DETAIL" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">收费金额：</span>
						<input type="text" style="width:180px;float:left;" maxlength="8"
							class="eve-input validate[required,custom[numberplus],min[0]]"
							value="${chargeInfo.CHARGE_AMOUNT}" name="CHARGE_AMOUNT" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">执收数量：</span>
						<input type="text" style="width:180px;float:left;" maxlength="8"
							class="eve-input validate[required,custom[numberplus],min[0]]"
							value="${chargeInfo.RECEIPT_NUM}" name="RECEIPT_NUM" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">收入项目编码：</span>
						<input type="text" style="width:180px;float:left;" maxlength="32"
							class="eve-input"
							value="${chargeInfo.PROJECT_CODE}" name="PROJECT_CODE" />
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">是否启用：</span>
						<eve:radiogroup typecode="YesOrNo" width="150" defaultvalue="1"
							value="${chargeInfo.IS_USE}" fieldname="IS_USE">
						</eve:radiogroup>
					</td>
				</tr>
				 -->
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
