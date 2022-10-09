
<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		
		<div id="${MainClassName}TabFormDiv" class="easyui-layout" fit="true">
			<div data-options="region:'center'" fit="true">
				<div class="easyui-tabs eve-tabs" fit="true" >
				    
				    <#list evetabs as tab>
				     <div title="${tab.CONTROL_NAME}" style="padding:10px">
					 </div>
				    </#list>
					
				</div>
			</div>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="button" onclick="submitInfo();"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
</body>
