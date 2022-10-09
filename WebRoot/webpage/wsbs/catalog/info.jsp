<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("CatalogForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#CatalogForm").serialize();
				var url = $("#CatalogForm").attr("action");
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
							parent.$("#catalogGrid").datagrid("reload");
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
	
	function selectChildDepartName(){
		var departId = $("input[name='CHILD_DEPART_ID']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属子部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='CHILD_DEPART_ID']").val(selectDepInfo.departIds);
                    $("input[name='CHILD_DEPART_NAME']").val(selectDepInfo.departNames);
                }
    			art.dialog.removeData("selectDepInfo");
            }
        }, false);
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="CatalogForm" method="post" action="catalogController.do?saveOrUpdate">
	    <div  id="CatalogFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="CATALOG_ID" value="${catalog.CATALOG_ID}">
	    <input type="hidden" name="DEPART_ID" value="${catalog.DEPART_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">目录名称：</span>
                <input
                    type="text" style="width:350px;float:left;"
                    value="${catalog.CATALOG_NAME}" 
                     class="eve-input validate[required,maxSize[400]]" name="CATALOG_NAME" /> 
                    <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
                <td>
                <span style="width: 100px;float:left;text-align:right;">目录性质：</span>
                <c:if test="${catalog.SXXZ==null||catalog.SXXZ==''}">
                <eve:eveselect clazz="eve-input validate[required]" dataParams="ServiceItemXz"
                                  value="${catalog.SXXZ}"
                                 dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择目录性质"
                                 name="SXXZ">
                </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
                </c:if>
                <c:if test="${catalog.SXXZ!=null&&catalog.SXXZ!=''}">
                ${catalog.SXXZ}
                </c:if>
                </td>
            </tr>
            <c:if test="${catalog.CATALOG_CODE!=''&&catalog.CATALOG_CODE!=null}">
			<tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">编码：</span>
                ${catalog.CATALOG_CODE}
                </td>
            </tr>
            </c:if>
			<tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">所属部门：</span>
                ${catalog.DEPART_NAME}
                </td>
            </tr>
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">所属子部门：</span>
                <input type="hidden" value="${catalog.CHILD_DEPART_ID}"  name="CHILD_DEPART_ID"/>
                <input
                    type="text" style="width:350px;float:left;"
                    value="${catalog.CHILD_DEPART_NAME}" 
                     class="eve-input" name="CHILD_DEPART_NAME" readonly="readonly" onclick="selectChildDepartName();" /> 
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
