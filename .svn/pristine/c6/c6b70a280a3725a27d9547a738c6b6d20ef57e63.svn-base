<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("WinScreenForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#WinScreenForm").serialize();
				var url = $("#WinScreenForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							parent.$("#WinScreenGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
							$("input[type='submit']").attr("disabled",false);
						}
					}
				});
			}
		},"T_CKBS_WIN_SCREEN");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="WinScreenForm" method="post" action="callSetController.do?saveOrUpdateWinScreen">
	    <div  id="WinScreenFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${winScreenInfo.RECORD_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>					
					<td><span style="width: 100px;float:left;text-align:right;">窗口编号：</span>
						<input type="text" style="width:180px;float:left;"
						value="${winScreenInfo.WIN_NO}" maxlength="4" id="WIN_NO"
						class="eve-input validate[required],custom[onlyNumberSp]"
						name="WIN_NO" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">屏编号：</span>
						<input type="text" style="width:180px;float:left;"
						value="${winScreenInfo.SCREEN_NO}" maxlength="4" <c:if test="${winScreenInfo.RECORD_ID!=null}">disabled="disabled"</c:if>
						class="eve-input validate[required],custom[onlyNumberSp],ajax[ajaxVerifyValueExist]"
						name="SCREEN_NO" id="SCREEN_NO"/> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">显示单位：</span>
						<input type="text" style="width:180px;float:left;"
						value="${winScreenInfo.DEPARTINFO}" maxlength="16"
						class="eve-input validate[required]"
						name="DEPARTINFO" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">显示字符数：</span>
						<input type="text" style="width:180px;float:left;"
						value="${winScreenInfo.WORD_NUM}" maxlength="2"
						class="eve-input validate[required],custom[integerplus]"
						name="WORD_NUM" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">所属大厅编号：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="roomNo"
						dataInterface="dictionaryService.findDatasForSelect" value="${winScreenInfo.BELONG_ROOM}"
						defaultEmptyText="选择大厅编号"  name="BELONG_ROOM">
						</eve:eveselect>	
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>

			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
