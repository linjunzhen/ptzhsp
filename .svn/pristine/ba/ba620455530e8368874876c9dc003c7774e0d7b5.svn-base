<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		
		
		AppUtil.initWindowForm("jurisdictionForm", function(form, valid) {
			if (valid) {
				$('#jurisdictionForm').find('input,textarea').prop("disabled", false);
				$('#jurisdictionForm').find('select').prop("disabled", false);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#jurisdictionForm").serialize();
				var url = $("#jurisdictionForm").attr("action");
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
							parent.$("#jurisdictionGrid").datagrid("reload");
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
		}, "T_COMMERCIAL_JURISDICTION");
		
		//清除前后空格
		$("input,textarea").on('blur', function(event) { 
			$(this).val(trim($(this).val()));
		});
		var ARMY_REGISTER_HOURSE = '${jurisdiction.ARMY_REGISTER_HOURSE}';
		if(ARMY_REGISTER_HOURSE){			
			setRequired(ARMY_REGISTER_HOURSE,'ARMYHOURSE_REGISTER_REMARKS','03');
		}
	});
	//清除前后空格
	function trim(str){ 
	  return str.replace(/(^\s*)|(\s*$)/g,""); 
	}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	.eflowbutton {
		background: #3a81d0;
		border: none;
		padding: 0 10px;
		line-height: 26px;
		cursor: pointer;
		height: 26px;
		color: #fff;
		border-radius: 5px;
	}
	.whf_input{width:97%!important;;height:25px;float:left;}
	.btn1 {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 27px;
		line-height: 27px;
		margin-left: 10px;
		text-align: center;
		width: 50px;
	}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="jurisdictionForm" method="post"
		action="jurisdictionController.do?saveOrUpdate">
		<div region="center" style="min-height:260px;">
			<div class="eui-slidebox" style="width:96%">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="XQ_ID" value="${jurisdiction.XQ_ID}">
				<!--==========隐藏域部分结束 ===========-->
				
				<div class="syj-title1" style="height:30px;">
					<span>基本信息(<font color="#ff0101">*</font>为必填)</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:100px;"><font class="dddl_platform_html_requiredFlag">*</font>辖区分局名称</th>
						<td colspan="3">
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" id="XQ_NAME"
								class="eve-input validate[required]" placeholder="请输入辖区分局名称"  value="${jurisdiction.XQ_NAME}" name="XQ_NAME" />
						</td>
					</tr>				
					<tr>
						<th ><font class="syj-color2">*</font>分局地址关键字：</th>
						<td colspan="3">
							<textarea rows="3" cols="200" name="XQ_KEYWORD"
								class="eve-textarea validate[validate[required],maxSize[1024]]"
								style="width:97%;height:100px;" placeholder="请输入关键字" >${jurisdiction.XQ_KEYWORD}</textarea> 
							<div style="color:red;width:97%;">注意：关键字之间用英文逗号隔开</div>
						</td>
					</tr>	
				</table>
			</div>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="submit"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

