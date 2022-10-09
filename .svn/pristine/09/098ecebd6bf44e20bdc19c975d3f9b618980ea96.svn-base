<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("modelDsForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#modelDsForm").serialize();
				var url = $("#modelDsForm").attr("action");
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
							parent.reloadModelDsList(resultJson.msg);
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
		}, "T_CMS_TPL_MODELDS");
	});
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="modelDsForm" method="post"
		action="modelDsController.do?saveOrUpdate">
		<div region="center" style="min-height:360px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="DSID" value="${modelDs.DSID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:35px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 100px;float:left;text-align:right;">数据源名称：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="30" type="text" name="DSNAME" value="${modelDs.DSNAME}" style="width:480px;"/>
					</td>
				</tr>
				
				<tr style="height:100px;">
					<td style="width:80px;">
						<span style="width: 100px;float:left;text-align:right;height:100px;">数据源代码：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[2000]" rows="3" cols="200" style="width:480px;height:100px;" 
						  name="DSCODE">${modelDs.DSCODE}</textarea>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 100px;float:left;text-align:right;">返回结果集：</span>
					</td>
					<td colspan="3">
					<select defaultemptytext="请选择结果集" class="eve-input validate[required]" name="DSRETURNDATATYPE" >
						<option value="">请选择结果集</option>						
						<option value="1" <c:if test="${modelDs.DSRETURNDATATYPE==1}">selected="selected"</c:if>>单个</option>
						<option value="2" <c:if test="${modelDs.DSRETURNDATATYPE==2}">selected="selected"</c:if>>多个</option>	
					</select>	
					</td>
				</tr>
				<tr id="resultcontent_tr" style="height:100px;">
					<td>
						<span style="width: 100px;float:left;text-align:right;height:100px;">数据源描述：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea maxSize[500]" rows="3" cols="200" style="width:480px;height:100px;" 
						  name="DSDESC">${modelDs.DSDESC}</textarea>
					</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 100px;float:left;text-align:right;">数据源表：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="100" type="text" name="DSCACHEKEYS" value="${modelDs.DSCACHEKEYS}" style="width:480px;"/>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
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

