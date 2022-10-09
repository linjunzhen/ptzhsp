
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript"
	src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
    $(function() {
        AppUtil.initWindowForm("flowStageConfigForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#flowStageConfigForm").serialize();
                var url = $("#flowStageConfigForm").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "succeed",
                                time : 3,
                                ok : true
                            });
                            parent.$("#FlowTypeGrid").datagrid("reload");
                            parent.$("#FlowConfigFlowGrid").datagrid("reload");
                            AppUtil.closeLayer();
                        } else {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "error",
                                ok : true
                            });
							 $("input[type='submit']").removeAttr("disabled");
                        }
                    }
                });
            }
        }, "T_FLOW_CONFIG_STAGE");

    });
	
	 /**
     * 显示对话框
     */
    function showItemSelectorWindow() {
        var url = "flowTemplateController.do?itemSelector&STAGE_ID="+'${flowConfigItem.STAGE_ID}';
        $.dialog.open(url, {
            title : "选择事项",
            width : "90%",
            lock : true,
            resize : false,
            height : "90%",
        }, false);
    };
</script>
</head>

<body class="eui-diabody" style="margin: 0px;">
	<form id="flowStageConfigForm" method="post"
		action="flowTemplateController.do?stageSaveOrUpdate">
		<div id="flowConfigFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="STAGE_ID" value="${flowStage.STAGE_ID}"> 
			
			<input type="hidden" name="TYPE_ID" value="${flowStage.TYPE_ID}">
			<input type="hidden" name="ITEM_ID" value="${flowStage.ITEM_ID}" id='ITEM_ID'/>

			<!--==========隐藏域部分结束 ===========-->
			<table style="width: 100%; border-collapse: collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td  class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">名称：</span> <input
						type="text" style="width: 280px; float: left;" maxlength="20"
						class="eve-input validate[required]" value="${flowStage.NAME}"
						name="NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">阶段时限：</span> <input
						type="text" style="width: 280px; float: left;" maxlength="20"
						class="eve-input validate[required],custom[number]" value="${flowStage.SPJDSX}"
						name="SPJDSX" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td width="130px">
						<span style="width: 120px;float:left;text-align:right;">绑定事项：</span>
						<input id='ITEM_NAME' name="ITEM_NAME" type="text" style="width:280px;float:left;" maxlength="20"
						class="eve-input validate[required]" value="${flowStage.ITEM_NAME}" readonly="true"
						/><font class="dddl_platform_html_requiredFlag">*</font>
						<input type="button" value="选择" class="eve-button" onclick="showItemSelectorWindow()" style="margin-top: 3px;margin-left: 5px;">
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">排序：</span>
						<input type="text" style="width: 280px; float: left;"
						maxlength="3" class="eve-input validate[required],custom[number]"
						value="${flowStage.TREE_SN}" name="TREE_SN" /><font
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

