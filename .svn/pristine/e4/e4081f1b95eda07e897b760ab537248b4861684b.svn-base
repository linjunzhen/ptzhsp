<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("luceneConfigForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#luceneConfigForm").serialize();
				var url = $("#luceneConfigForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.reloadluceneConfigList(resultJson.msg);
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_HD_luceneConfig");
	});
	
</script>

</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="luceneConfigForm" method="post"
		action="luceneConfigController.do?saveOrUpdate">
		<div region="center" style="min-height:500px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="CONFIG_ID" value="${luceneConfig.CONFIG_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">配置名称：</span>
					</td>
					<td style="width: 197px;">
						<input type="text" style="width:150px;float:left;" value="${luceneConfig.CONFIG_TITLE}" maxlength="30" class="eve-input validate[required]" name="CONFIG_TITLE" /> 
					</td>
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">索引路径：</span>
					</td>
					<td>						
						<input type="text" style="width:150px;float:left;" value="${luceneConfig.CONFIG_INDEXDIRECTORY}" maxlength="32" class="eve-input validate[required]" name="CONFIG_INDEXDIRECTORY" /> 
					</td>
				</tr>
				
				<tr style="height:29px;" id="tr_dept">
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">索引名称：</span>
					</td>
					<td style="width: 197px;">						
						<input type="text" style="width:150px;float:left;" value="${luceneConfig.CONFIG_LUCENENAME}" maxlength="32" class="eve-input validate[required]" name="CONFIG_LUCENENAME" /> 
					</td>
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">对象主键：</span>
					</td>
					<td>						
						<input type="text" style="width:150px;float:left;" value="${luceneConfig.CONFIG_IDFIELD}" maxlength="32" class="eve-input validate[required]" name="CONFIG_IDFIELD" />
					</td>
				</tr>
				<tr style="height:29px;" id="tr_dept">
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">排序字段：</span>
					</td>
					<td style="width: 197px;">				
						<input type="text" style="width:150px;float:left;" value="${luceneConfig.CONFIG_SORTFIELD}" maxlength="32" class="eve-input" name="CONFIG_SORTFIELD" />
					</td>
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">每页显示数：</span>
					</td>
					<td>				
						<input type="text" style="width:150px;float:left;" value="${luceneConfig.CONFIG_LISTCOUNT}" maxlength="32" class="eve-input validate[required],custom[onlyNumberSp]" name="CONFIG_LISTCOUNT" />
					</td>
				</tr>
				<tr style="height:100px;">
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">索引字段：</span>
					</td>
					<td  colspan="3">						
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:470px;height:100px;" 
						  name="CONFIG_INDEXFIELDS">${luceneConfig.CONFIG_INDEXFIELDS}</textarea>
					</td>
				</tr>
				<tr style="height:100px;">
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">存储字段：</span>
					</td>
					<td  colspan="3">						
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:470px;height:100px;" 
						  name="CONFIG_STOREFIELDS">${luceneConfig.CONFIG_STOREFIELDS}</textarea>
					</td>
				</tr>
				<tr style="height:29px;">
					<td style="width:120px;">
						<span style="width: 80px;float:left;text-align:right;">高亮字段：</span>
					</td>
					<td  colspan="3">						
						
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_HIGHLIGHTEDFIELDS}" maxlength="100" class="eve-input validate[required]" name="CONFIG_HIGHLIGHTEDFIELDS" />
					</td>
				</tr>
				</table>
				<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>数据库信息</a></div></td>
				</tr>	
				
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">连接名称：</span>
					</td>
					<td>						
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_ALIAS}" maxlength="16" class="eve-input validate[required]" name="CONFIG_ALIAS" />
					</td>
				</tr>
				
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">连接类型：</span>
					</td>
					<td>
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_DBTYPE}" maxlength="16" class="eve-input validate[required]" name="CONFIG_DBTYPE" />
					</td>
				</tr>
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">取数据量：</span>
					</td>
					<td>
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_PAGESIZE}" maxlength="6" class="eve-input validate[required],custom[onlyNumberSp]" name="CONFIG_PAGESIZE" />
					</td>
				</tr>
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">数据库驱动：</span>
					</td>
					<td>
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_CLASSNAME}" maxlength="32" class="eve-input validate[required]" name="CONFIG_CLASSNAME" />
					</td>
				</tr>
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">连接地址：</span>
					</td>
					<td>
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_URL}" maxlength="512" class="eve-input validate[required]" name="CONFIG_URL" />
					</td>
				</tr>
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">用户名：</span>
					</td>
					<td>
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_USER}" maxlength="32" class="eve-input validate[required]" name="CONFIG_USER" />
					</td>
				</tr>
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">密码：</span>
					</td>
					<td>
						<input type="text" style="width:470px;float:left;" value="${luceneConfig.CONFIG_PASSWORD}" maxlength="32" class="eve-input validate[required]" name="CONFIG_PASSWORD" />
					</td>
				</tr>
				<tr>
					<td style="width: 120px;">
						<span style="width: 80px;float:left;text-align:right;">SQL：</span>
					</td>
					<td colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[2000]" rows="3" cols="200" style="width:470px;height:100px;" 
						  name="CONFIG_SQL">${luceneConfig.CONFIG_SQL}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'"  style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

