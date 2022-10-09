
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("flowConfigItemForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				//var formData = $("#flowConfigItemForm").serialize();
				var formData = FlowUtil.getFormEleData("flowConfigItemForm");
				var url = $("#flowConfigItemForm").attr("action");
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
							parent.$("#flowConfigItemGrid").datagrid("reload");
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
		}, "T_FLOW_CONFIG_ITEM");

	});
	
	 /**
     * 显示对话框
     */
    function showChooseWindow() {
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

<body  class="eui-diabody" style="margin:0px;">
	<form id="flowConfigItemForm" method="post"
		action="flowTemplateController.do?itemSaveOrUpdate">
		<input type="hidden" style="width:180px;float:left;" maxlength="20" value="${flowConfigItem.ID}"
		name="ID" />
		<input id='ITEM_ID' type="hidden" style="width:180px;float:left;" maxlength="20" value="${flowConfigItem.ITEM_ID}"
		name="ITEM_ID" />
		<input id='STAGE_ID' type="hidden" style="width:180px;float:left;" maxlength="20" value="${flowConfigItem.STAGE_ID}"
		name="STAGE_ID" />
		<div id="flowConfigItemFormDiv"  data-options="region:'center',split:true,border:false">
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td width="130px">
						<span style="width: 160px;float:left;text-align:right;">事项名称：
						<font class="dddl_platform_html_requiredFlag">*</font></span>
					</td>
					<td>
						<input id='ITEM_NAME' name="ITEM_NAME" type="text" style="width:280px;float:left;" maxlength="20"
						class="eve-input validate[required]" value="${flowConfigItem.ITEM_NAME}" readonly="true"
						/>
						<input type="button" value="选择" class="eve-button" onclick="showChooseWindow()" style="margin-top: 3px;margin-left: 5px;">
					</td>
				</tr>
					<tr>
						<td>
							<span style="width: 160px; float: left; text-align: right;">标准审批事项：
							<font class="dddl_platform_html_requiredFlag">*</font></span>
						</td>
						<td>
							<eve:excheckbox
								dataInterface="dictionaryService.findDatasForSelect"
								name="DYBZSPSXBM" width="100%" clazz="checkbox validate[required]"
								dataParams="DYBZSPSXBM" value="${flowConfigItem.DYBZSPSXBM}">
							</eve:excheckbox>
							</td>
					</tr>
				<tr>
					<td>
						<span style="width: 160px;float:left;text-align:right;">排序：
						<font class="dddl_platform_html_requiredFlag">*</font></span>
					</td>
					<td>
						<input type="text" style="width:280px;float:left;" maxlength="3"
						class="eve-input validate[required],custom[number]" value="${flowConfigItem.SORT}"
						name="SORT" />
					</td>
				</tr>
				<tr>
					<td>
					<span style="width: 160px;float:left;text-align:right;" >是否里程碑事项：<font class="dddl_platform_html_requiredFlag">*</font></span>
					</td>
					<td>
						<input type="radio"  name="IS_KEY_ITEM" value="1" <c:if test="${flowConfigItem.IS_KEY_ITEM=='1'}">checked="checked"</c:if>>是</input>
						<input type="radio" name="IS_KEY_ITEM" value="0" <c:if test="${flowConfigItem.IS_KEY_ITEM!='1'}"> checked="checked"</c:if> >否</input>
						
						</td>
				</tr>
				<!--<tr>
					<td>
					<span style="width: 160px;float:left;text-align:right;" >是否可选择事项：<font class="dddl_platform_html_requiredFlag">*</font></span>
					</td>
					<td>
						<input type="radio"  name="IS_SITE_OPTIONAL" value="1" <c:if test="${flowConfigItem.IS_SITE_OPTIONAL=='1'}">checked="checked"</c:if>>是</input>
						<input type="radio" name="IS_SITE_OPTIONAL" value="0" <c:if test="${flowConfigItem.IS_SITE_OPTIONAL!='1'}"> checked="checked"</c:if> >否</input>
					</td>
				</tr>-->
				<tr>
					<td>
					<span style="width: 160px;float:left;text-align:right;" >是否实行告知承诺制：<font class="dddl_platform_html_requiredFlag">*</font></span>
					</td>
					<td>
						<input type="radio" name="SFSXGZCNZ" value="0" <c:if test="${flowConfigItem.SFSXGZCNZ!='1'}">checked="checked"</c:if>>实行审批制</input>
						<input type="radio" name="SFSXGZCNZ" value="1" <c:if test="${flowConfigItem.SFSXGZCNZ=='1'}">checked="checked"</c:if>>实行告知承诺制</input>
					</td>
				</tr>
				<!--<tr>
					<td><span
						style="width: 160px; float: left; text-align: right;">主题名称：
							<font class="dddl_platform_html_requiredFlag">*</font>
					</span></td>
					<td><eve:excheckbox
							dataInterface="dictionaryService.findDatasForSelect"
							name="TOPIC_NAME" width="100%"
							clazz="checkbox validate[required]" dataParams="TOPICNAME"
							value="${flowConfigItem.TOPIC_NAME}">
						</eve:excheckbox></td>
				</tr>-->
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

