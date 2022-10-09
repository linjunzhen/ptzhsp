<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<base href="<%=basePath%>">
<meta name="renderer" content="webkit">
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south {
	border-top-width: 0px !important;
}

.eve_buttons {
	position: relative !important;
}
</style>
<script type="text/javascript">
    function doSubmitRows(){
    	var rows = $("#rightItemGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return;
		}else{
    		var rightId = "";
    		var rightName = "";
    		var subitem = "";
    		var rightSource = "";
    		var rightSourceOhter = "";
    		var exerciseLevel = "";
			for(var i = 0;i<rows.length;i++){
				rightId+=rows[i].RIGHT_ID;
				rightName+=rows[i].RIGHT_NAME;
				subitem+=rows[i].SUBITEM_NAME;
				rightSource+=rows[i].RIGHT_SOURCE;
				rightSourceOhter+=rows[i].RIGHT_SOURCE_OTHER;
				exerciseLevel += rows[i].EXERCISE_LEVEL;
			}
    		art.dialog.data("selectItemInfo", {
    			rightId:rightId,
    			rightName:rightName,
    			subitem:subitem,
    			rightSource:rightSource,
    			rightSourceOhter:rightSourceOhter,
    			exerciseLevel:exerciseLevel
			}); 
    		AppUtil.closeLayer();
    	}
    	
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true">
		<div data-options="region:'center',split:false">
			<div id="ItemToolbar">
				<form action="#" name="BillOfRightPublishForm" id="BillOfRightPublishForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:68px;text-align:right;">项目名称</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.RIGHT_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">子项名称</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.SUBITEM_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">权责类别</td>
								<td style="width:135px;"><input class="easyui-combobox"
									style="width:128px;" name="Q_T.RIGHT_TYPE_="
									data-options="
	url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=qzlb',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
								</td>
								<td colspan="2"><input type="button" value="查询"
									class="eve-button"
									onclick="AppUtil.gridDoSearch('ItemToolbar','rightItemGrid')" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('BillOfRightPublishForm')" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->

			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="rightItemGrid" fitColumns="true" toolbar="#ItemToolbar" method="post"
				idField="RIGHT_ID" checkOnSelect="true" selectOnCheck="true"
				fit="true" border="false" nowrap="false"
				url="billOfRightController.do?datagrid&Q_T.STATUS_EQ=1">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'RIGHT_ID',hidden:true" width="80">RIGHT_ID</th>
						<th data-options="field:'IMPL_DEPART_ID',hidden:true" width="80">IMPL_DEPART_ID</th>
						<th data-options="field:'RIGHT_SOURCE',hidden:true" width="80">RIGHT_SOURCE</th>
						<th data-options="field:'RIGHT_SOURCE_OTHER',hidden:true" width="80">RIGHT_SOURCE_OTHER</th>
						<th data-options="field:'EXERCISE_LEVEL',hidden:true" width="80">EXERCISE_LEVEL</th>
						<th data-options="field:'RIGHT_NAME',align:'left'" width="250">权责清单目录</th>
						<th data-options="field:'SUBITEM_NAME',align:'left'" width="250">权责清单子目录</th>
						<th data-options="field:'RIGHT_TYPE',align:'left'" width="100">权责类别</th>
						<th data-options="field:'IMPL_DEPART_NAME',align:'left'"
							width="120">实施部门</th>
					</tr>
				</thead>
			</table>

		</div>

		<div data-options="region:'south',split:true,border:false">
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doSubmitRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>


</body>
</html>
