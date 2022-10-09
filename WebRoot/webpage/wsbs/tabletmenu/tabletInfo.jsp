<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("tabletForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#tabletForm").serialize();
				var url = $("#tabletForm").attr("action");
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
							parent.$("#tabletMenuGrid").datagrid("reload");
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
        parent.$.dialog.open("departmentController/yjselector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
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
	
	//选择所属服务事项
    function selectCatalog(){
    	var entityId = $("input[name='TABLET_ID']").val();
    	var kind = $("#ITEM_KIND").val();
    	var tableKind = $("input[name='table_kind']").val();
    	var departId = $("input[name='DEPART_ID']").val();
    	if((!kind||kind=='')&&(!tableKind||tableKind=='')){
			$.messager.alert('警告',"请先选择清单性质！");
			return false;
		}else if(!departId||departId== ''){
			$.messager.alert('警告',"请先选择所属部门！");
			return false;
		}else{
			parent.$.dialog.open("tabletMenuController.do?sxselector&allowCount=20&tabletId="
                + entityId+"&itemKind="+kind+"&departId="+departId, {
            title : "服务事项选择器",
            width : "1000px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectItemInfo = art.dialog.data("selectItemInfo");
                if(selectItemInfo&&selectItemInfo.itemIds){
	    				//setSelectItems(selectItemInfo.itemIds,selectItemInfo.itemNames,selectItemInfo.itemCodes);
	    				$("input[name='ITEM_IDS']").val(selectItemInfo.itemIds);
	    				$("input[name='ITEM_NUM']").val(selectItemInfo.itemNum+"个");
	    				//var array=selectItemInfo.itemNames.split(",");
	    				//$("input[name='TABLET_NAME']").val(array[0]);
	    				$("input[name='TABLET_NAME']").val(selectItemInfo.catalogName);
	    				$("input[name='ITEM_CATALOGCODE']").val(selectItemInfo.catalogCode);
	    				art.dialog.removeData("selectItemInfo");
	    		}
            }
        	}, false);
		}
    }
    
	function selectChildDepartNameBak(){
		var departId = $("input[name='DEPART_ID']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
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
	<form id="tabletForm" method="post" action="tabletMenuController.do?saveOrUpdate">
	    <div  id="CatalogFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TABLET_ID" value="${tablet.TABLET_ID}">
	    <input type="hidden" name="ITEM_CATALOGCODE" value="${tablet.ITEM_CATALOGCODE}"/>
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
                    value="${tablet.TABLET_NAME}" 
                     class="eve-input validate[required,maxSize[400]]" name="TABLET_NAME" /> 
                    <font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
                <td>
                <span style="width: 100px;float:left;text-align:right;">清单性质：</span>
                <input type="hidden" value="${tablet.DEPART_ID}"  name="table_kind" value="${tablet.ITEM_KIND}"/>
                <c:if test="${tablet.ITEM_KIND==null||tablet.ITEM_KIND==''}">
                <eve:eveselect clazz="eve-input validate[required]" dataParams="ServiceItemKind"
                                  value="${tablet.ITEM_KIND}"
                                 dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择性质"
                                 name="ITEM_KIND" id="ITEM_KIND">
                </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
                </c:if>
                <c:if test="${tablet.ITEM_KIND!=null&&tablet.ITEM_KIND!=''}">
                ${tablet.ITEM_KIND}
                </c:if>
                </td>
            </tr>
            
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">所属部门：</span>
                <input type="hidden" value="${tablet.DEPART_ID}"  name="DEPART_ID"/>
                <input
                    type="text" style="width:350px;float:left;"
                    value="${tablet.DEPART_NAME}" 
                     class="eve-input validate[required]]" name="DEPART_NAME" readonly="readonly" onclick="selectChildDepartName();" /> 
                     <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                <span style="width: 100px;float:left;text-align:right;">服务事项数：</span>
                <input type="hidden" value="${tablet.ITEM_IDS}"  name="ITEM_IDS"/>
                <input
                    type="text" style="width:350px;float:left;"
                    value="${tablet.ITEM_NUM}" 
                     class="eve-input validate[required]" name="ITEM_NUM" readonly="readonly" onclick="selectCatalog();" /> 
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
