<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DataAbutmentForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#DataAbutmentForm").serialize();
				var url = $("#DataAbutmentForm").attr("action");
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
							parent.$("#DataAbutmentGrid").datagrid("reload");
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
		}, "T_BSFW_DATAABUTMENT");

	});
	
	function changeAbutType(){
		var AABUT_TYPE = $("select[name='AABUT_TYPE']").val();
		if(AABUT_TYPE=="1"){
			$("#WEBSERVICE_CONFIG").attr("style","display:none");
			$("#DB_CONFIG").attr("style","");
		}else{
			$("#DB_CONFIG").attr("style","display:none");
			$("#WEBSERVICE_CONFIG").attr("style","");
		}
	}
</script>
</head>

<body class="easyui-layout eui-diabody" fit="true" style="margin:0px;">
	<form id="DataAbutmentForm" method="post"
		action="dataAbutmentController.do?saveOrUpdate">
		<div id="DataAbutmentFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="AABUT_ID" value="${dataAbutment.AABUT_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">配置编码：</span>
					    <c:if test="${dataAbutment.AABUT_ID!=null}">
						   ${dataAbutment.AABUT_CODE}
						</c:if>
						<c:if test="${dataAbutment.AABUT_ID==null}">
						<input type="text" style="width:180px;float:left;" maxlength="30"
						class="eve-input validate[required,custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]]"
						value="${dataAbutment.AABUT_CODE}" id="AABUT_CODE"
						name="AABUT_CODE" />
						</c:if>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td><span style="width: 120px;float:left;text-align:right;">配置名称：</span>
						<input type="text" style="width:180px;float:left;" maxlength="30"
						class="eve-input validate[required]"
						value="${dataAbutment.AABUT_NAME}" name="AABUT_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">对接方式：</span>
					    <eve:eveselect clazz="eve-input" dataParams="AABUTTYPE" onchange="changeAbutType();"
					         dataInterface="dictionaryService.findDatasForSelect" value="${dataAbutment.AABUT_TYPE}"
					         name="AABUT_TYPE">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">对接实现接口：</span>
						<input type="text" style="width:180px;float:left;" maxlength="64"
						class="eve-input validate[required]"
						value="${dataAbutment.AABUT_INTERFACE}" name="AABUT_INTERFACE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">调用方式：</span>
					    <eve:eveselect clazz="eve-input" dataParams="InterfaceInvokeType" 
					         dataInterface="dictionaryService.findDatasForSelect" value="${dataAbutment.INVOKE_TYPE}"
					         name="INVOKE_TYPE">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">网络环境：</span>
					    <eve:eveselect clazz="eve-input" dataParams="NetworkType" 
					         dataInterface="dictionaryService.findDatasForSelect" value="${dataAbutment.NETWORK_TYPE}"
					         name="NETWORK_TYPE">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
			
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>链接配置</a></div></td>
				</tr>
				<tbody id="DB_CONFIG" <c:if test="${dataAbutment.AABUT_TYPE!='1'}">style="display: none;"</c:if> >
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">数据库连接地址：</span>
						<input type="text" style="width:575px;float:left;" maxlength="62"
						class="eve-input" value="${dataAbutment.DB_URL}" name="DB_URL" /></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">数据库用户名：</span>
						<input type="text" style="width:240px;float:left;" maxlength="30"
						class="eve-input" value="${dataAbutment.DB_USERNAME}"
						name="DB_USERNAME" /></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">数据库密码：</span>
						<input type="text" style="width:240px;float:left;" maxlength="30"
						class="eve-input" value="${dataAbutment.DB_PASSWORD}"
						name="DB_PASSWORD" /></td>
				</tr>
				</tbody>
				<tbody id="WEBSERVICE_CONFIG" <c:if test="${dataAbutment.AABUT_TYPE!='2'}">style="display: none;"</c:if> >
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">WEB服务地址：</span>
						<input type="text" style="width:575px;float:left;" maxlength="126"
						class="eve-input" value="${dataAbutment.WEBSERVICE_URL}"
						name="WEBSERVICE_URL" /></td>
				</tr>
				</tbody>
			</table>
			
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>XML配置</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">配置XML：</span>
						<textarea rows="5" cols="5" class="eve-textarea"
							style="width: 575px;height:200px;" name="CONFIG_XML">${dataAbutment.CONFIG_XML}</textarea></td>
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

