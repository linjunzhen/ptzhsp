
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
	    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("flowConfigLinkForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#flowConfigLinkForm").serialize();
				var url = $("#flowConfigLinkForm").attr("action");
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
							parent.$("#FlowConfigLinkGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
							 $("input[type='submit']").removeAttr("disabled");
						}
					}
				});
			}
		}, "MZT_SYSTEM_flowConfig");

	});
	
	 /**
     * 显示系统用户信息对话框
     */
    function showChooseWindow() {
        //获取组织机构的ID
        // 		var departId = $("#FlowConfigToolbar input[name='DEPART_ID']").val();
        var url = "flowConfigLinkController.do?xzyw&cate_id="+'${flowConfigLink.CATE_ID}';
        // 		if(departId&&departId!="0"){
        // 			url+="&departId="+departId;
        // 		}
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

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="flowConfigLinkForm" method="post"
		action="flowConfigLinkController.do?saveOrUpdate">
		<div id="flowConfigLinkFormDiv">
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td>
					<input type="hidden" style="width:180px;float:left;" maxlength="20" value="${flowConfigLink.ID}"
						name="ID" />
					<input id='ITEM_ID' type="hidden" style="width:180px;float:left;" maxlength="20" value="${flowConfigLink.ITEM_ID}"
						name="ITEM_ID" />
					<input id='CATE_ID' type="hidden" style="width:180px;float:left;" maxlength="20" value="${flowConfigLink.CATE_ID}"
						name="CATE_ID" />
					<span style="width: 120px;float:left;text-align:right;">事项名称：</span>
						<input id='ITEM_NAME' type="text" style="width:180px;float:left;" maxlength="20"
						class="eve-input validate[required]" value="${flowConfigLink.ITEM_NAME}" readonly="true"
						/><font class="dddl_platform_html_requiredFlag">*</font>
						<a href="javascript:showChooseWindow()" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a>
<!-- 						<input type="button" value="选择" -->
<!-- 								class="eve-button" -->
<!-- 								onclick="AppUtil.gridDoSearch('SysUserToolbar','SysUserGrid')" /> -->
						</td>
				</tr>
				<tr>
					<td>
					<span style="width: 120px;float:left;text-align:right;">排序：</span>
						<input type="text" style="width:180px;float:left;" maxlength="3"
						class="eve-input validate[required],custom[number]" value="${flowConfigLink.SORT}"
						name="SORT" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td>
					<span style="width: 120px;float:left;text-align:right;" >是否里程碑事项：</span>
						<input type="radio"  name="IS_KEY_ITEM" value="1" <c:if test="${flowConfigLink.IS_KEY_ITEM=='1'}">checked="checked"</c:if>>是</input>
						<input type="radio" name="IS_KEY_ITEM" value="0" <c:if test="${flowConfigLink.IS_KEY_ITEM!='1'}"> checked="checked"</c:if> >否</input>
						<font class="dddl_platform_html_requiredFlag">*</font>
						</td>
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

