<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

	function formatUrl(value,row,index){
        var fileId=row.FILE_ID;
        var href = "<a href='javascript:void(0);' onclick=AppUtil.downLoadFile('" + fileId + 
        "'); style='cursor: pointer;'> <font color='blue'>" + value + "</font></a>";
        return href;
	}
	function doSelectRows(){
		var rows = $("#HisMatGrid").datagrid("getChecked");
		if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
		}else{
			var FILE_ID = "";
			for(var i = 0;i<rows.length;i++){
				FILE_ID=FILE_ID + "\'" +rows[i].FILE_ID +"\'";
				if(i+1<rows.length){
					FILE_ID=FILE_ID + ",";
				}
			}
        	$.post( "fileAttachController.do?selectHisMatByIds",{
	            FILE_ID:FILE_ID,
                attachKey:"${attachKey}",
                busTableName:"${busTableName}"},
            function(responseText1, status, xhr) {
                var resultJson1 = $.parseJSON(responseText1);
                art.dialog.data("resultJsonInfo", resultJson1);
                AppUtil.closeLayer();
            });
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="HisMatToolbar">
				<form action="#" name="HisMatForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:68px;text-align:right;">材料名称</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.MATER_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">附件名称</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.FILE_NAME_LIKE" /></td>
								<td colspan="2"><input type="button" value="查询"
									class="eve-button"
									onclick="AppUtil.gridDoSearch('HisMatToolbar','HisMatGrid')" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('HisMatForm')" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="HisMatGrid" fitColumns="true" toolbar="#HisMatToolbar"
				method="post" idField="FILE_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="fileAttachController.do?hisMatDatagrid&sqrsfz=${sqrsfz}&sqjg_code=${sqjg_code}&attachKey=${attachKey}">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th>
	                    <th data-options="field:'MATER_NAME',align:'left'" width="120">材料名称</th>
						<th data-options="field:'FILE_NAME',align:'left',formatter:formatUrl" width="120" >附件名称</th>
	                    <th data-options="field:'CREATE_TIME',align:'left'" width="80">上传时间</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
