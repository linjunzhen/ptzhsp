<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!-- 单位信息查询弹窗界面 -->
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/zgjbylbx/js/ylbx.js"></script>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	function doSelectRows(){
		var entityId = AppUtil.getEditDataGridRecord("dwxxcxSelectorGrid");
		if(entityId!=null && entityId!=""){
			var row = $('#dwxxcxSelectorGrid').datagrid('getSelected');
			var unitFile = row.aab511;
			var unitInsurance = row.aab999;
			var unitName = row.aab004;
			var unitNum = row.aae516;
    		art.dialog.data("selectInfo", {
    			unitFile:unitFile,
    			unitInsurance:unitInsurance,
    			unitName:unitName,
    			unitNum:unitNum
			});
    		AppUtil.closeLayer();
		}
	}
	//数据格式化
	function dataFormat(value,json){
		var data = JSON.parse(json);
		var rtn = "";
		$.each(data, function(idx, dic) {
			if(value==dic.DIC_CODE){
				rtn = dic.DIC_NAME;
				return false;
			}
		});
		return rtn;
	}
	//是与否格式化
	var yesNoObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'YESNO'}
	});
	function formatYesNo(value){
		var json = yesNoObj.responseText;
		return dataFormat(value,json);
	}
	//隶属关系格式化
	var affiliationObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'Affiliation'}
	});
	function formatAffiliation(value){
		var json = affiliationObj.responseText;
		return dataFormat(value,json);
	}
	//分中心格式化
	var subCenterObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'subCenter'}
	});
	function formatSubCenter(value){
		var json = subCenterObj.responseText;
		return dataFormat(value,json);
	}
	//单位类型格式化
	var unitTypeObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'TypeOfUnit'}
	});
	function formatUnitType(value){
		var json = unitTypeObj.responseText;
		return dataFormat(value,json);
	}
	//单位类别格式化
	var unitCategoryObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'UnitCategory'}
	});
	//单位类别格式化
	function formatUnitCategory(value){
		var json = unitCategoryObj.responseText;
		return dataFormat(value,json);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="dwxxcxSelectorToolbar">
				<form action="#" name="dwxxcxSelectorForm" id="dwxxcxSelectorForm">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">单位保险号：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="dwbxh" id="dwbxh"/>
							</td>
							<td style="width:68px;text-align:right;">单位名称：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="dwmc" id="dwmc"/>
							</td>
							<td style="width:68px;text-align:right;">单位档案号：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="dwdah" id="dwdah"/>
							</td>
							<td style="width:68px;text-align:right;">税号：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="xh" id="xh"/>
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">核对标志：</td>
							<td style="width:135px;">
								<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="选择核对标志" name="HDBS">
								</eve:eveselect>
							</td>
							<td style="width:120px;text-align:right;">分中心：</td>
							<td style="width:135px;">
								<eve:eveselect clazz="tab_text validate[required]" dataParams="subCenter"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="选择分中心" name="">
								</eve:eveselect>
							</td>
							<td colspan="2">
								<input type="button" value="查询" class="eve-button"
								onclick="AppUtil.gridDoSearch('dwxxcxSelectorToolbar','dwxxcxSelectorGrid')" />
								<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('dwxxcxSelectorForm');" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="dwxxcxSelectorGrid" fitColumns="true" toolbar="#dwxxcxSelectorToolbar"
				method="post" idField="aab001" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="zgjbylbxController.do?findDWXXInfo">
				<thead>
					<tr>
						<th field="ck" checkbox="true" ></th>
						<!-- <th data-options="field:'Aab030',align:'center'" width="5%">审核标志</th> -->
						<th data-options="field:'verified',align:'center',formatter:formatYesNo" width="5%">核对标志</th>
						<th data-options="field:'aae516',align:'center'" width="5%">单位人数</th>
						<th data-options="field:'aab002',align:'center'" width="10%">社保登记证编码</th>
						<th data-options="field:'aab999',align:'center'" width="10%">单位保险号</th>
						<th data-options="field:'tbrqi0',align:'center'" width="8%">参保日期</th>
						<th data-options="field:'aab004',align:'center'" width="8%">单位名称</th>
						<th data-options="field:'aab511',align:'center'" width="8%">单位档案号</th>
						<th data-options="field:'aab019',align:'center',formatter:formatUnitType" width="8%">单位类型</th>
						<th data-options="field:'aab001',align:'center'" width="5%">单位编号</th>
						<th data-options="field:'Aab030',align:'center'" width="5%">税号</th>
						<th data-options="field:'aab021',align:'center',formatter:formatAffiliation" width="5%">隶属关系</th>
						<th data-options="field:'aab020',align:'center',formatter:formatUnitCategory" width="5%">单位类别</th>
						<th data-options="field:'orgcode',align:'center',formatter:formatSubCenter" width="10%" >分中心</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="取消" type="button" onclick="AppUtil.closeLayer();" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<c:if test="${hmccx == true}">
					<input type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight"
					 name="DWHMCCX" value="单位花名册查询" onclick="dwhmccx();">
				</c:if>
			</div>
		</div>
	</div>
</body>

