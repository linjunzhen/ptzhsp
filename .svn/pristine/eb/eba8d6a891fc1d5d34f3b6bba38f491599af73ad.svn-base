<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("billItemForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#billItemForm").serialize();
				var url = $("#billItemForm").attr("action");
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
							parent.$("#billItemGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		},null);
	});
	
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="billItemForm" method="post" action="billRightController.do?saveOrUpdateItem">
	    <div  id="billItemFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="BILL_ID" value="${itemInfo.BILL_ID}">
	    <input type="hidden" name="DEPART_ID" value="${itemInfo.DEPART_ID}">
	    <input type="hidden" name="CATALOG_BILL_ID" value="${itemInfo.CATALOG_BILL_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">清单名称：</span>
                <input
                    type="text" style="width:350px;float:left;"
                    value="${itemInfo.BILL_NAME}" 
                     class="eve-input validate[required,maxSize[400]]" name="BILL_NAME" /> 
                    <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
            
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">是否公开：</span>
                <eve:radiogroup typecode="YesOrNo" width="130px"
							value="${itemInfo.IS_PUBLIC}" fieldname="IS_PUBLIC">
				</eve:radiogroup> <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
            </tr> 
            
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">自贸区属性：</span>
                <eve:radiogroup typecode="isFta" width="130px"
							value="${itemInfo.FTA_FLAG}" fieldname="FTA_FLAG">
				</eve:radiogroup> <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
            </tr> 
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">入驻方式：</span>
                	<c:if test="${null==itemInfo||itemInfo.RZBSDTFS==null||itemInfo.RZBSDTFS==''}">
						<eve:radiogroup typecode="RZBSDTFS" width="150px"
							value="in04" fieldname="RZBSDTFS">
						</eve:radiogroup>
					</c:if>
					<c:if test="${null!=itemInfo&&itemInfo.RZBSDTFS!=null&&itemInfo.RZBSDTFS!=''}">
						<eve:radiogroup typecode="RZBSDTFS" width="150px"
							value="${itemInfo.RZBSDTFS}" fieldname="RZBSDTFS">
						</eve:radiogroup>
					</c:if>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
            </tr> 
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
