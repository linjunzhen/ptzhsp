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
							parent.$("#departCatalogGrid").datagrid("reload");
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
	
	function selectParentDepartName(){
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
					art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function selectChildDepartName(){
		var departId = $("input[name='CHILD_DEPART_ID']").val();
		var parentId = $("input[name='DEPART_ID']").val();
		if(parentId==null||parentId==""){
			parent.art.dialog({
				content: "请先所属部门",
				icon:"warning",
				ok: true
			});
			return false;
		}
        parent.$.dialog.open("departmentController/childSelector.do?rootParentId="+parentId+"&departIds="+departId+"&allowCount=1&checkCascadeY=&"
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
	
	function selectBillCatalog(){		
        parent.$.dialog.open("billRightController/catalogSelector.do", {
            title : "权责清单目录选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
                if(selectCatalogInfo){
                    $("input[name='DEPART_ID']").val(selectCatalogInfo.departId);
                    $("input[name='DEPART_NAME']").val(selectCatalogInfo.departName);
                    $("input[name='SXXZ']").val(selectCatalogInfo.sxxz);
                    $("input[name='SXXZC']").val(selectCatalogInfo.sxxzc);
                    $("input[name='CATALOG_NAME']").val(selectCatalogInfo.catalogName);
                    $("input[name='BILL_ID']").val(selectCatalogInfo.billId);
                    $("input[type=radio][name=FTA_FLAG][value="+selectCatalogInfo.fta+"]").attr("checked","checked");
                    
    				art.dialog.removeData("selectCatalogInfo");
                }
            }
        }, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="CatalogForm" method="post"
		action="departCatalogController.do?saveOrUpdate">
		<div id="CatalogFormDiv" data-options="region:'center',split:true,border:false">
			<%--==========隐藏域部分开始 ===========--%>
			<!-- <input type="hidden" name="BILL_ID"> -->
			<input type="hidden" name="CATALOG_ID" value="${catalog.CATALOG_ID}">
			<input type="hidden" name="DEPART_ID" value="${catalog.DEPART_ID}">
			<%-- <input type="hidden" name="SXXZ" value="${catalog.SXXZ}"> --%>
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">目录名称：</span> <input
						type="text" style="width:350px;float:left;"
						value="${catalog.CATALOG_NAME}"
						class="eve-input validate[required,maxSize[400]]"
						name="CATALOG_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font>
						<%-- <c:if test="${catalog.CATALOG_CODE==''||catalog.CATALOG_CODE==null}">
							<input value="选择目录" type="button" class="eve_inpbtn"
								onclick="selectBillCatalog();" />
						</c:if> --%>
					</td>
				</tr>
				<%-- <tr>
					<td><span style="width: 100px;float:left;text-align:right;">目录性质：</span>
						<input
						type="text" style="width:350px;float:left;"
						value="${catalog.SXXZC}" class="eve-input"
						name="SXXZC" readonly="readonly" /></td>
				</tr> --%>
				<tr>
	                <td>
	                <span style="width: 100px;float:left;text-align:right;">目录性质：</span>
	                <eve:eveselect clazz="eve-input validate[required]" dataParams="ServiceItemKind"
                        value="${catalog.SXXZ}"
                        dataInterface="dictionaryService.findDatasForSelect"  defaultEmptyText="${catalog.SXXZ}"
                        name="SXXZ">
	                </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
	                </td>
	            </tr>
				<%-- <tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">自贸区属性：</span>
						<eve:radiogroup typecode="isFta" width="130px"  defaultvalue="3"
							value="${catalog.FTA_FLAG}" fieldname="FTA_FLAG">
						</eve:radiogroup><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr> --%>
				<c:if test="${catalog.CATALOG_CODE!=''&&catalog.CATALOG_CODE!=null}">
					<tr>
						<td colspan="2"><span
							style="width: 100px;float:left;text-align:right;">编码：</span>
							${catalog.CATALOG_CODE}</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">所属部门：</span> <input
						type="text" style="width:350px;float:left;" onclick="selectParentDepartName();"
						value="${catalog.DEPART_NAME}" class="eve-input validate[required]"
						name="DEPART_NAME" readonly="readonly" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">所属子部门：</span> <input
						type="hidden" value="${catalog.CHILD_DEPART_ID}"
						name="CHILD_DEPART_ID" /> <input type="text"
						style="width:350px;float:left;"
						value="${catalog.CHILD_DEPART_NAME}" class="eve-input validate[required]"
						name="CHILD_DEPART_NAME" readonly="readonly"
						onclick="selectChildDepartName();" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
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
