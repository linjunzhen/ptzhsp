<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
    
function dosubmit(){
        var fpara = $("#MATER_FPARA").val();
        art.dialog.data("filterMaterInfo", {
        	fpara:fpara
        });
        AppUtil.closeLayer();
    }


</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
		<div id="ApplyMaterFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">材料过滤参数：</span>
						<input type="text" style="width:150px;float:left;" maxlength="100"
						class="eve-input"
						value="" id="MATER_FPARA" name="MATER_FPARA" />
					</td>
				</tr>
				
			</table>

		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons" >
				<input value="确定" type="button" onclick="dosubmit();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>

</body>

