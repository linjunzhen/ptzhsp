
<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
${ComboTreeFnAndSetContent}
${uploadTableFnContent}
$(function() {
	AppUtil.initWindowForm("${MainClassName}Form", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#${MainClassName}Form").serialize();
			var url = $("#${MainClassName}Form").attr("action");
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
						parent.$("#${MainClassName}Grid").datagrid("reload");
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
	},"${MainTableName}");
	${InitComboTreeContent}
	${initUploadContent}
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="${MainClassName}Form" method="post" action="${MainClassName?uncap_first}Controller.do?saveOrUpdate">
	    <div  id="${MainClassName}FormDiv">
		    <!--==========隐藏域部分开始 ===========-->
		    ${hiddenContent}
		    <!--==========隐藏域部分结束 ===========-->
		    ${formContent}
		    
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
	${comboTreeDiv}
</body>
