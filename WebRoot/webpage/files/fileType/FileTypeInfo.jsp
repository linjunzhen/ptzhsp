<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
	<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
    <script type="text/javascript">
    	$(function(){
    		AppUtil.initWindowForm("FileTypeInfoForm", function(form, valid){
				if(valid){
					$("input[type='submit']").attr("disabled", "disabled");
					var formData = $("#FileTypeInfoForm").serialize();
					var url = $("#FileTypeInfoForm").attr("action");
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
						        if(parent.$.fn.zTree.getZTreeObj("videoFileTypeTree")){
						        	parent.$.fn.zTree.getZTreeObj("videoFileTypeTree").reAsyncChildNodes(null, "refresh");
						        }
						        if(parent.$.fn.zTree.getZTreeObj("attachFileTypeTree")){
						        	parent.$.fn.zTree.getZTreeObj("attachFileTypeTree").reAsyncChildNodes(null, "refresh");
						        }
						        if(parent.$.fn.zTree.getZTreeObj("audioFileTypeTree")){
						        	parent.$.fn.zTree.getZTreeObj("audioFileTypeTree").reAsyncChildNodes(null, "refresh");
						        }
						        if(parent.$.fn.zTree.getZTreeObj("imageFileTypeTree")){
						        	parent.$.fn.zTree.getZTreeObj("imageFileTypeTree").reAsyncChildNodes(null, "refresh");
						        }
						        if(parent.$.fn.zTree.getZTreeObj("swfFileTypeTree")){
						        	parent.$.fn.zTree.getZTreeObj("swfFileTypeTree").reAsyncChildNodes(null, "refresh");
						        }
								AppUtil.closeLayer();
							}else{
								parent.art.dialog({
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
    <form id="FileTypeInfoForm" method="post" action="fileTypeController.do?saveOrUpdateTree">
        <div id="FileTypeInfoFormDiv">
            <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="TYPE_ID" value="${fileType.TYPE_ID}"/>
		    <input type="hidden" name="PARENT_ID" value="${fileType.PARENT_ID}"/>
		    <%--==========隐藏域部分结束 ===========--%>
		    <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
		        <tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td>
						<span style="width:100px;float:left;text-align:right;">上级类别：</span>
						${fileType.PARENT_NAME}
					</td>
				</tr>
				<tr>
					<td>
						<span style="width:100px;float:left;text-align:right;">类别名称：</span>
						<input type="text" style="width:150px;float:left;" value="${fileType.TYPE_NAME}" maxlength="30"
					 		   class="eve-input validate[required]" name="TYPE_NAME" /> 
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
		    </table>
        </div>
        <div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight"/>
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
    </form>
</body>