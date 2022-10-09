<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("MerchantInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#MerchantInfoForm").serialize();
				var url = $("#MerchantInfoForm").attr("action");
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
	
	
	function selectItem(){
		var itemCodes = $("input[name='BIND_ITEMCODES']").val();
		$.dialog.open("departServiceItemController.do?selector&allowCount=10&itemCodes="
				+ itemCodes, {
			title : "事项选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectItemInfo = art.dialog.data("selectItemInfo");
				if (selectItemInfo) {
					$("textarea[name='BIND_ITEMNAMES']").val(selectItemInfo.itemNames);
					$("input[name='BIND_ITEMCODES']").val(selectItemInfo.itemCodes);
					art.dialog.removeData("selectItemInfo");
				}
			}
		}, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="MerchantInfoForm" method="post"
		action="departServiceItemController.do?saveOrUpdateMerchantInfo">
		<div id="MerchantInfoFormDiv" data-options="region:'center',split:true,border:false">
			<%--==========隐藏域部分开始 ===========--%>
			<input type="hidden" name="RECORD_ID" value="${merchant.RECORD_ID}">
			<input type="hidden" name="BIND_ITEMCODES" value="${merchant.BIND_ITEMCODES}">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">支付渠道：</span>
						<eve:radiogroup typecode="payWay" width="130px" defaultvalue="01"
							value="${merchant.PAY_WAY}" fieldname="PAY_WAY">
						</eve:radiogroup></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">商户号：</span>
						<input type="text"
						maxlength="16" class="eve-input validate[required]" value="${merchant.MERCHANT_NO}"
						name="MERCHANT_NO" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">商户代码：</span>
						<input type="text"
						maxlength="16" class="eve-input validate[required]" value="${merchant.MERCHANT_CODE}"
						name="MERCHANT_CODE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">绑定支付事项：</span>
						<textarea style="width: 400px;height: 100px;" readonly="readonly" onclick="selectItem();"
							class="eve-textarea validate[required]" name="BIND_ITEMNAMES">${merchant.BIND_ITEMNAMES}</textarea>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">是否启用：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="1"
							value="${merchant.IS_USE}" fieldname="IS_USE">
						</eve:radiogroup>
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
