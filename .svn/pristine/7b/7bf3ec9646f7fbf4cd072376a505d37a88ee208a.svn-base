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
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">	
	
	function encrypt(){  
    	var rowsData = $("#EncryptionGrid").datagrid("getChecked");
    	if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要加密的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var layload = layer.load('正在提交请求中…');
				var colName = $("#EncryptionGrid").datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				console.log(selectColNames);
				var configId = $("input[name='configId']").val();
				$.post("encryptionController.do?tableEncryption",{
					   selectColNames:selectColNames,
					   configId:configId
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							$("#EncryptionGrid").datagrid('clearSelections').datagrid('clearChecked');
							AppUtil.gridDoSearch('EncryptionToolbar','EncryptionGrid');
						}else{
							$("#EncryptionGrid").datagrid('clearSelections').datagrid('clearChecked');
							AppUtil.gridDoSearch('EncryptionToolbar','EncryptionGrid');
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
		}
	}
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="EncryptionToolbar">
				<form action="#" name="EncryptionForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
<!-- 							JBPM6_EXECUTION -->
								<c:if test="${config.CONFIG_ID=='2c90ef84723b357501723b369a370001'}">
									<td style="width:68px;text-align:right;">申报号</td>
									<td style="width:135px;"><input class="eve-input"
										type="text" maxlength="20" style="width:128px;"
										name="Q_EXE_ID_LIKE" /></td>
									<td colspan="2"><input type="button" value="查询"
										class="eve-button"
										onclick="AppUtil.gridDoSearch('EncryptionToolbar','EncryptionGrid')" />
										<input type="button" value="重置" class="eve-button"
										onclick="AppUtil.gridSearchReset('EncryptionForm')" /></td>
								</c:if>
							</tr>
						</tbody>
					</table>
				</form>
				<!--====================开始编写隐藏域============== -->
				<input name="configId" type="hidden" value="${config.CONFIG_ID }"/>
				<!--====================结束编写隐藏域============== -->
				
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true" pageList="[20,100,200,500,700,1000,2000]" pageSize="20"
				id="EncryptionGrid" fitColumns="true" toolbar="#EncryptionToolbar"
				method="post" idField="RECORD_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false" 
				url="encryptionController.do?needEncryptionData&configId=${config.CONFIG_ID}">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'RECORD_ID',align:'left'" >唯一标识</th>
	                    <c:forEach items="${columnlist }" var="column">
	                    	<th data-options="field:'${column.COLUMN_NAME}',align:'left'" width="15%">${column.COLUMN_COMMENT}</th>
	                    </c:forEach>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="加密" type="button" onclick="encrypt();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
