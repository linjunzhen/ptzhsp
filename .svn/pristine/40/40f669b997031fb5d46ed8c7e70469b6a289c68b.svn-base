<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">

	

    //计算收费金额
    function countTotalPrice(val){
       var value = $("#OrderItemRule").find("option:selected").val();
       if(value==null || value=="" || value==undefined){
           parent.art.dialog({
				content: "请先选择收费标准！",
				icon:"succeed",
				time:3,
			    ok: true
			});
       }else{
       		var  totalPrice = 0;
       		var sfbz = Number(value);
       		var sfsl = Number(val);
       		//精确到小数点后两位
			$("input[name='OrderItemPrice']").val(Math.ceil(Number(sfbz*sfsl)*100)/100);
       }
    }
    
    
		
	$(function() {
	
	    //收费编码改变事件
		$("#OrderItemCode").change(function(){
			var value = $("#OrderItemCode").find("option:selected").val();
			if(value =="452001"){
				$("select[name='OrderItemName'] option[value='住宅类不动产登记费']").attr("selected",true);
				$("select[name='OrderItemRule'] option[value='80']").attr("selected",true);
				$("select[name='OrderItemUnit'] option[value='件']").attr("selected",true);
				$("select[name='OrderBillCode'] option[value='00019901']").attr("selected",true);
			}else if(value =="452002"){
				$("select[name='OrderItemName'] option[value='非住宅类不动产登记费']").attr("selected",true);
				$("select[name='OrderItemRule'] option[value='550']").attr("selected",true);
				$("select[name='OrderItemUnit'] option[value='件']").attr("selected",true);
				$("select[name='OrderBillCode'] option[value='00010101']").attr("selected",true);
			}else if(value =="452003"){
				$("select[name='OrderItemName'] option[value='不动产权属证书工本费']").attr("selected",true);
				$("select[name='OrderItemRule'] option[value='10']").attr("selected",true);
				$("select[name='OrderBillCode'] option[value='00010301']").attr("selected",true);
			}
		});
	
		AppUtil.initWindowForm("BdcOrderItemForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BdcOrderItemForm").serialize();
				var url = $("#BdcOrderItemForm").attr("action");
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
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
							$("input[type='submit']").attr("disabled", false);
						}
					}
				});
			}
		}, "T_BDC_ORDERDETAILINFO");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BdcOrderItemForm" method="post"
		action="bdcGyjsjfwzydjController.do?saveOrUpdateOrderItem">		
		<div id="BdcOrderItemFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="EXE_ID" value="${exeId}">
		<input type="hidden" name="entityId" value="${entityId}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>科目信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>科目编码：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="bdcSfKmbm" 
						dataInterface="dictionaryService.findDatasForSelect" value="${orderCharge.OrderItemCode}"
						defaultEmptyText="===请选择==="  name="OrderItemCode" id="OrderItemCode"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>票据种类代码：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="bdcPjzldm" 
						dataInterface="dictionaryService.findDatasForSelect" value="${orderCharge.OrderBillCode}"
						defaultEmptyText="===请选择==="  name="OrderBillCode" id="OrderBillCode"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>科目名称：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="bdcSfKmmc"
						dataInterface="dictionaryService.findDatasForSelect" value="${orderCharge.OrderItemName}"
						defaultEmptyText="===请选择==="  name="OrderItemName"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>科目标准：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="bdcSfBz"
						dataInterface="dictionaryService.findDatasForSelect" value="${orderCharge.OrderItemRule}"
						defaultEmptyText="===请选择==="  name="OrderItemRule" id="OrderItemRule"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>数量：</span>
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[required,custom[number]]" onblur="countTotalPrice(this.value);"
							value="${orderCharge.OrderItemNum}" name="OrderItemNum" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>计量单位：</span>
							<eve:eveselect clazz="eve-input validate[required]" dataParams="bdcSfJldw"
							dataInterface="dictionaryService.findDatasForSelect" value="${orderCharge.OrderItemUnit}"
							defaultEmptyText="===请选择==="  name="OrderItemUnit"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>收费金额：</span>
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[required,custom[number]]"
							value="${orderCharge.OrderItemPrice}" name="OrderItemPrice" />
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

