<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south {
	border-top-width: 0px !important;
}

.eve_buttons {
	position: relative !important;
}
</style>
<script type="text/javascript">

	function formatActionName(val, row) {
		var action = "";
		if (val == "SIGN_FLOW_FINISH") {
			action = "<font color='blue'><b>流程完结</b></font>";
		} else if (val == "SIGN_FLOW_UPDATE") {
			action = "<font color='green'><b>流程更新</b></font>";
		} else if (val == "SIGN_FLOW_NOTIFY") {
			action = "<font color='purple'><b>签署通知</b></font>";
		} else {
			return "<font color='purple'><b></b></font>";
		}
		return action;
	}
    
	function formatSignStatus(val, row) {
		//获取回调类型
		var action = row.ACTION;
		var status = "";
		if (action == "SIGN_FLOW_FINISH") { //签署完结
			if (val == "2") {
				status = "<font color='blue'><b>签署完成</b></font>";
			} else if (val == "5") {
				status = "<font color='red'><b>过期作废</b></font>";
			} else if (val == "7") {
				status = "<font color='red'><b>拒签</b></font>";
			} else if (val == "8") {
				status = "<font color='red'><b>签署完成</b></font>";
			} else {
				status = "<font color='green'><b></b></font>";
			}
		} else if (action == "SIGN_FLOW_UPDATE") { //签署更新
			if (val == "2") {
				status = "<font color='blue'><b>签署完成</b></font>";
			} else if (val == "3") {
				status = "<font color='red'><b>冻结</b></font>";
			} else if (val == "4") {
				status = "<font color='red'><b>解冻</b></font>";
			} else if (val == "5") {
				status = "<font color='red'><b>静默签署失败</b></font>";
			} else {
				status = val;
			}
		} else if (action == "SIGN_FLOW_NOTIFY") { //签署通知
			status = "<font color='blue'><b>已通知</b></font>";
		} else {
			status = "<font color='blue'><b></b></font>";
		}
		return status;
	}

	//去除字符串的空串(前后空格)
	function Trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber() {
		var grid = $('#signFlowGrid');
		var options = grid.datagrid('getPager').data("pagination").options;
		var maxnum = options.pageNumber * options.pageSize;
		var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">' + maxnum + '</span>').hide().appendTo(document.body);
		$(currentObj).css('font', '12px, Microsoft YaHei');
		var width = currentObj.width();
		var panel = grid.datagrid('getPanel');
		if (width > 25) {
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
			grid.datagrid("resize");
		} else {
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
			grid.datagrid("resize");
		}
	}
	$('#signFlowGrid').datagrid({
		onLoadSuccess : fixRownumber
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<input type="hidden" name="allowCount" value="${allowCount}">
		<div class="easyui-layout" fit="true">
			<div data-options="region:'center',split:false">
				<!-- =========================表格开始==========================-->
				<table class="easyui-datagrid" rownumbers="true" pagination="false"
					id="signFlowGrid" fitColumns="true" toolbar="#fdchtbaxxcxToolbar"
					method="post" idField="FWMMHTH" checkOnSelect="false"
					selectOnCheck="false" fit="true" border="false" nowrap="false"
					url="bdcQlcMaterGenAndSignController.do?datagrid&SIGN_FLOWID=${flowId}">
					<thead>
						<tr>
							<th data-options="field:'ACTION',align:'left'" width="100" formatter="formatActionName">签署流程名称</th>
							<th data-options="field:'SIGN_NAME',align:'left'" width="150">签署人</th>
							<th data-options="field:'STATUS',align:'left'" width="150" formatter="formatSignStatus">签署状态</th>
							<th data-options="field:'SIGN_TIME',align:'left'" width="100">签署时间</th>
							<th data-options="field:'BEGINTIME',align:'left'" width="150">合同创建时间</th>
							<th data-options="field:'ENDTIME',align:'left'" width="150">合同结束时间</th>
							<th data-options="field:'RESDESCRIPTION',align:'left'" width="250">结果描述</th>
						</tr>
					</thead>
				</table>
			</div>

			<div data-options="region:'south',split:true,border:false">
				<div class="eve_buttons">
					<input value="关闭" type="button"
						class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</div>
			</div>
		</div>

</body>
</html>

