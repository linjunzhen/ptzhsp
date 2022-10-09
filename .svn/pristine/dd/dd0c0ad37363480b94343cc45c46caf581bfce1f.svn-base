<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">


	function doSelectRows(){
		var rows = $("#MainBussinessGrid").datagrid("getChecked");
		if(rows.length!=1){
			alert("请只选择一条记录!");
		}else{
			art.dialog.data("selectInfo", {
				BUSSCOPE_NAME:rows[0].BUSSCOPE_NAME,
				MAIN_BUSSINESS_ID:rows[0].ID,
				DL_NAME:rows[0].DL_NAME,
				ML_NAME:rows[0].ML_NAME,
				ZL_NAME:rows[0].ZL_NAME,
				XL_NAME:rows[0].XL_NAME
			});
			AppUtil.closeLayer();
		}

	}

	
	//查询
	function search(){
	    $('#MainBussinessGrid').datagrid('clearChecked')=='none';
		AppUtil.gridDoSearch('MainBussinessToolbar','MainBussinessGrid');
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="MainBussinessToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="PARENT_ID" value="${entityId}">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con"></div>
			<form action="#" name="ChildIndustryForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">主行业名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUSSCOPE_NAME_LIKE" /></td>
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
			id="MainBussinessGrid" fitcolumns="true" toolbar="#MainBussinessToolbar"
			method="post" idfield="BUSSCOPE_CODE" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="industryController/mainBussinessForWebsite.do?Q_T.CHILD_INDUSTRYIDS_LIKE=${entityId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'BUSSCOPE_CODE',hidden:true">BUSSCOPE_CODE</th>
					<th data-options="field:'BUSSCOPE_NAME',align:'left'" width="20%">经营范围名称</th>
					<th data-options="field:'ML_NAME',align:'left'" width="20%">行业门类名称</th>
					<th data-options="field:'DL_NAME',align:'left'" width="20%">行业大类名称</th>
					<th data-options="field:'ZL_NAME',align:'left'" width="20%">行业中类名称</th>
					<th data-options="field:'XL_NAME',align:'left'" width="20%">行业小类名称</th>
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