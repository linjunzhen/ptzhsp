
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript"
	src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
    $(function() {
        AppUtil.initWindowForm("flowConfigTaskChooseForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#flowConfigTaskChooseForm").serialize();
                var url = $("#flowConfigTaskChooseForm").attr("action");
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
                        }
                    }
                });
            }
        }, "MZT_SYSTEM_flowConfig");

    });
</script>
</head>

<body style="margin: 0px; background-color: #f7f7f7;">
	<form id="flowConfigTaskChooseForm" method="post"
		action="flowConfigController.do?doAddDefaultTask">
		<div id="flowConfigTaskChooseFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="PARENT_ID" value="${PARENT_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width: 100%; border-collapse: collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span
						style="width: 120px; float: left; text-align: right;">选择流程类型：</span>
						<eve:eveselect clazz="eve-input validate[required]"
							dataParams="LCPZ"
							dataInterface="dicTypeService.findByParentCodeForSelect"
							defaultEmptyText="==选择流程类型==" name="DICT_TYPE_CODE"></eve:eveselect> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

