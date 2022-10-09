<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
    <eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,json2"></eve:resources>
    <script type="text/javascript">
        function showFileTypeSelector(){
        	parent.$.dialog.open("fileTypeController.do?selector&needCheckIds="+$("#targetTypeId").val(), {
				title: "多媒体类别选择器",
				width: "320px",
				height: "500px",
				lock: true,
				resize: false,
				ok: function(){
					var returnObj = this.iframe.contentWindow.onArtDialogCallback();
					if(returnObj){
						setSelectFileType(returnObj.checkIds, returnObj.checkNames);
					}else{
						return false;
					}
	            },
	            cancel: true
			});
        }
        
        function setSelectFileType(checkIds, checkNames){
        	$("#targetTypeId").val(checkIds);
		   	$("#targetTypeName").val(checkNames);
        }
        
        $(document).ready(function(){
        	AppUtil.initWindowForm("AudioFileMoveForm", function(form, valid){
				if(valid){
					//将提交按钮禁用,防止重复提交
					$("input[type='submit']").attr("disabled", "disabled");
					var formData = $("#AudioFileMoveForm").serialize();
					var url = $("#AudioFileMoveForm").attr("action");
					AppUtil.ajaxProgress({
						url: url,
						params: formData,
						callback: function(resultJson){
							if(resultJson.success){
								parent.art.dialog({
									content: resultJson.msg,
									icon: "succeed",
									time: 3,
									ok: true
								});
								parent.$("#AudioFileViewGrid").datagrid("reload");
								AppUtil.closeLayer();
							}else{
								art.dialog({
									content: resultJson.msg,
									icon: "error",
									ok: true
								});
							}
						}
					});
				}
			});
        });
        
    </script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
    <form id="AudioFileMoveForm" method="post" action="audioFileController.do?saveMove">
        <div id="AudioFileMoveFormDiv">
            <!--==========隐藏域部分开始 ===========-->
            <input type="hidden" name="sourceFileIds" id="sourceFileIds" value="${requestScope.selectColNames }"/>
            <input type="hidden" name="targetTypeId" id="targetTypeId"/>
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">		
				<tr></tr>
				<tr>
					<td>
					    <span style="width: 100px;float:left;text-align:right;">移动至类别：</span>
						<input type="text" style="width:280px;float:left;" readonly="readonly" 
						       class="eve-input validate[required]" id="targetTypeName" onclick="showFileTypeSelector();"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				 </tr>			 
			</table>
        </div>
        <div class="eve_buttons">
			<input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
			<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();" />
		</div>
    </form>
</body>