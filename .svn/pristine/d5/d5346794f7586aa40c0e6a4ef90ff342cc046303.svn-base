
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("IndustryForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#IndustryForm").serialize();
				var url = $("#IndustryForm").attr("action");
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
							parent.$("#IndustryGrid").datagrid("reload");
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
		}, "T_COMMERCIAL_INDUSTRY");

	});
	
	 //企业类型选择器
	 function showSelectComtypeName(){
    	var comtypeId = $("input[name='COMTYPE_ID']").val();
    	parent.$.dialog.open("industryController/selector.do?comtypeId="+comtypeId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "企业类型选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectComtypeInfo = art.dialog.data("selectComtypeInfo");
    			if(selectComtypeInfo){
    				$("input[name='COMTYPE_ID']").val(selectComtypeInfo.comtypeIds);
        			$("input[name='COMTYPE_NAME']").val(selectComtypeInfo.comtypeNames);
    				art.dialog.removeData("selectComtypeInfo");
    			}
    		}
    	}, false);
    }
    
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="IndustryForm" method="post"
		action="industryController.do?saveOrUpdateMainIndus">
		<div id="IndustryFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="INDUSTRY_ID" value="${industry.INDUSTRY_ID}">
			<input type="hidden" name="COMTYPE_ID" value="${industry.COMTYPE_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">主行业名称：</span>
						<input type="text" style="width:250px;float:left;" maxlength="128"
						class="eve-input validate[required]" value="${industry.INDUSTRY_NAME}"
						name="INDUSTRY_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">企业类型：</span> <input
						type="text" style="width:250px;float:left;" readonly="readonly"
						value="${industry.COMTYPE_NAME}" onclick="showSelectComtypeName();"
						class="eve-input validate[required]" name="COMTYPE_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
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

