<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<base href="<%=basePath%>/">
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">

	function doSelectRows(){
		var rows = $("#ChildIndustryGrid").datagrid("getChecked");
		if(rows.length!=1){
			alert("请只选择一条记录!");
		}else{
			var industryName =rows[0].INDUSTRY_NAME;
			var industryId =rows[0].INDUSTRY_ID
			art.dialog.data("selectInfo", {
				industryName:industryName,
				industryId:industryId
			});
			AppUtil.closeLayer();
		}

	}

	
	//查询
	function search(){
	    $('#ChildIndustryGrid').datagrid('clearChecked')=='none';
		AppUtil.gridDoSearch('ChildIndustryToolbar','ChildIndustryGrid');
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ChildIndustryToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="PARENT_ID" value="${entityId}">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con"></div>
			<form action="#" name="ChildIndustryForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">子行业名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.INDUSTRY_NAME_LIKE" /></td>
							<td ><input type="button" value="查询"
								class="eve-button"
								onclick="search()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ChildIndustryForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ChildIndustryGrid" fitcolumns="true" toolbar="#ChildIndustryToolbar"
			method="post" idfield="INDUSTRY_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="industryController/childIndustryForWebsite.do?Q_T.PARENT_ID_EQ=${entityId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'INDUSTRY_ID',hidden:true">INDUSTRY_ID</th>
					<th data-options="field:'INDUSTRY_NAME',align:'left'" width="60%">子行业名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'south',split:true,border:false"  style="height: 42px;margin-top: 5px;" >
		<div class="eve_buttons">
			<input value="确定" type="button" onclick="doSelectRows();"
				   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
		</div>
	</div>

</div>