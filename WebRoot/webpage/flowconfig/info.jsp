
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript"
	src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
    $(function() {
        AppUtil.initWindowForm("flowConfigForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#flowConfigForm").serialize();
                var url = $("#flowConfigForm").attr("action");
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
                            parent.$.fn.zTree.getZTreeObj("flowTypeTree")
                                    .reAsyncChildNodes(null, "refresh");

                            // 							parent.$("#FlowConfigCateGrid").datagrid("reload");
                            // 							parent.$("#FlowConfigTypeGrid").datagrid("reload");
                            // 							parent.$("#FlowConfigTypeGrid2").datagrid("reload");
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
        }, "MZT_SYSTEM_flowConfig");

    });
</script>
</head>

<body class="eui-diabody" style="margin: 0px;">
	<form id="flowConfigForm" method="post"
		action="flowConfigController.do?saveOrUpdate">
		<div id="flowConfigFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ID" value="${flowConfig.ID}"> <input
				type="hidden" name="PARENT_ID" value="${flowConfig.PARENT_ID}">
			<input type="hidden" name="TYPE" value="${flowConfig.TYPE}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width: 100%; border-collapse: collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">名称：</span> <input
						type="text" style="width: 180px; float: left;" maxlength="32"
						class="eve-input validate[required]" value="${flowConfig.NAME}"
						name="NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<c:if test="${flowConfig.TYPE=='1' }">
					<tr>
						<td><span
							style="width: 120px; float: left; text-align: right;">排序：</span>
							<input type="text" style="width: 180px; float: left;"
							maxlength="3" class="eve-input validate[required],custom[number]"
							value="${flowConfig.TREE_SN}" name="TREE_SN" /><font
							class="dddl_platform_html_requiredFlag">*</font></td>
					</tr>
				</c:if>
				<c:if test="${flowConfig.TYPE!='1' }">
					<tr>
						<td><span
							style="width: 120px; float: left; text-align: right;">流程类型：</span>
							<eve:eveselect clazz="eve-input validate[required]"
								value="${flowConfig.FLOW_TYPE_ID}" dataParams="" 
								dataInterface="flowConfigTypeService.findForSelect"
								defaultEmptyText="==选择审批流程类型==" name="FLOW_TYPE_ID"></eve:eveselect>
							<font class="dddl_platform_html_requiredFlag">*</font></td>
					</tr>
				</c:if>
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

