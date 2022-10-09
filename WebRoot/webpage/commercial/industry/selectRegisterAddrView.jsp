<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<base href="<%=basePath%>/">
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">
	AppUtil.extendsEasyDataGrid();
	function doSelectRows(){
		var rows = $("#RegisterGrid").datagrid("getChecked");
		if(rows.length!=1){
			alert("请只选择一条记录!");
		}else{
			var ALL_FULL_ADDR =rows[0].ALL_FULL_ADDR;
			var SYSTEMID =rows[0].SYSTEMID;
			var SXZJD=rows[0].SXZJD;
			art.dialog.data("selectInfo", {
				ALL_FULL_ADDR:ALL_FULL_ADDR,
				SXZJD:SXZJD,
				SYSTEMID:SYSTEMID
			});
			AppUtil.closeLayer();
		}
	}

	
	//查询
	function search(){
		var jsnr = $("[name='jsnr']").val();
		if(jsnr){			
			$('#RegisterGrid').datagrid('clearChecked')
			AppUtil.gridDoSearch('RegisterToolbar','RegisterGrid');
		} else{			
			art.dialog({
				content: "请先输入需要查询的地址!",
				icon:"warning",
				ok: true
			});
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="RegisterToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="PARENT_ID" value="${entityId}">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con"></div>
			<form action="#" name="RegisterForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">注册地址</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="jsnr" /></td>
							<td ><input type="button" value="查询"
								class="eve-button"
								onclick="search()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('RegisterForm')" />
							<span style="color: red;">地址尽量详细；可以采用地名+空格+门牌号</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="RegisterGrid" fitcolumns="true" toolbar="#RegisterToolbar"
			method="post" idfield="SYSTEMID"
			nowrap="false"
			   data-options="pageSize:15,pageList:[15]" nowrap="false" singleSelect="true"
			 fit="true" border="false"
			url="zzhyDataController/findAddrInfoByA13.do">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SYSTEMID',hidden:true">SYSTEMID</th>
					<th data-options="field:'SXZJD',align:'left'" width="10%">辖区</th>
					<th data-options="field:'QUXCUN',align:'left'" width="10%">区域</th>
					<th data-options="field:'ORG_NAME',align:'left'" width="30%">派出所</th>
					<th data-options="field:'ALL_FULL_ADDR',align:'left'" width="50%">地址</th>
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