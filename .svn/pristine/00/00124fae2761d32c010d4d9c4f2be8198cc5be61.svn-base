<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("billCatalogForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#billCatalogForm").serialize();
				var url = $("#billCatalogForm").attr("action");
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
							parent.$("#billRightGrid").datagrid("reload");
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
		var departId = $("input[name='DEPART_ID']").val();
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='DEPART_ID']").val(selectDepInfo.departIds);
                    $("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
                }
            }
        }, false);
	}
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="billCatalogForm" method="post" action="billRightController.do?saveOrUpdateCataLog">
	    <div  id="CatalogFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="BILL_ID" value="${billCatalog.BILL_ID}">
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
                    value="${billCatalog.BILL_NAME}" 
                     class="eve-input validate[required,maxSize[400]]" name="BILL_NAME" /> 
                    <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
                <td>
                <span style="width: 100px;float:left;text-align:right;">清单性质：</span>
                <eve:eveselect clazz="eve-input validate[required]" dataParams="ServiceItemKind"
                                  value="${billCatalog.ITEM_KIND}"
                                 dataInterface="dictionaryService.findDatasForSelect"  defaultEmptyText="${billCatalog.ITEM_KIND}"
                                 name="ITEM_KIND" id="ITEM_KIND">
                </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">所属部门：</span>
                <input type="hidden" value="${billCatalog.DEPART_ID}"  name="DEPART_ID"/>
                <input
                    type="text" style="width:350px;float:left;"
                    value="${billCatalog.DEPART_NAME}" 
                     class="eve-input validate[required]]" name="DEPART_NAME" readonly="readonly" onclick="selectChildDepartName();" /> 
                     <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr> 
            
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">是否公开：</span>
                <eve:radiogroup typecode="YesOrNo" width="130px"
							value="${billCatalog.IS_PUBLIC}" fieldname="IS_PUBLIC">
				</eve:radiogroup> <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
            </tr> 
            
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">自贸区属性：</span>
                <eve:radiogroup typecode="isFta" width="130px"
							value="${billCatalog.FTA_FLAG}" fieldname="FTA_FLAG" defaultvalue="3">
				</eve:radiogroup> <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
            </tr>
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">服务事项数：</span>
                <c:if test="${billCatalog.ITEM_NUM==null||billCatalog.ITEM_NUM==''}">
	                0个
	            </c:if>
                <c:if test="${billCatalog.ITEM_NUM!=null&&billCatalog.ITEM_NUM!=''}">
	                ${billCatalog.ITEM_NUM}
	            </c:if>
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
