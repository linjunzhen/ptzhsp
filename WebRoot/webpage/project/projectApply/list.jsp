<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除项目信息列表记录
	 */
	function deleteprojectApplyGridRecord() {
		AppUtil.deleteDataGridRecord("projectApplyController.do?multiDel",
				"projectApplyGrid");
	};
	/**
	 * 编辑项目信息列表记录
	 */
	function editprojectApplyGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("projectApplyGrid");
		if (entityId) {
			showprojectApplyWindow(entityId);
		}
	}

	/**
	 * 注销项目信息列表记录
	 */
	function cancelprojectApplyGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("projectApplyGrid");
		if (entityId) {
			var projectCode = AppUtil.getEditDataGridRecordValue("projectApplyGrid", "PROJECT_CODE");
			$.post("projectApplyController.do?isCancelOk", {
				projectCode : projectCode
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {
					art.dialog.confirm("您确定要注销该记录吗?", function() {
						var layload = art.dialog({
							content : '正在提交请求中…'
						}).lock();
						$.post("projectApplyController.do?cancelProject", {
							entityId : entityId
						}, function(responseText, status, xhr) {
							layload.close();
							var resultJson = $.parseJSON(responseText);
							if (resultJson.success == true) {
								art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									time : 3,
									ok : true
								});
								$("#projectApplyGrid").datagrid("reload");
								$("#projectApplyGrid").datagrid('clearSelections').datagrid('clearChecked');
							} else {
								art.dialog({
									content : resultJson.msg,
									icon : "error",
									ok : true
								});
							}
						}).error(function(xhr, errorText, errorType) {
							layload.close();
							art.dialog({
								content : '程序内部出错，请联系管理员!',
								icon : "error",
								ok : true
							});
						});
						;
					});
				} else {
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			}).error(function(xhr, errorText, errorType) {
				layload.close();
				art.dialog({
					content : '程序内部出错，请联系管理员!',
					icon : "error",
					ok : true
				});
			});
			;
		}
	}

	/**
	* 项目事项列表
	*/
	function listItemGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("projectApplyGrid");
		if (entityId) {
			var $dataGrid = $("#projectApplyGrid");
			var rowsData = $dataGrid.datagrid("getChecked");
			var FLOW_CATE_ID = rowsData[0].FLOW_CATE_ID;
			var PROJECT_CODE = rowsData[0].PROJECT_CODE;
			 $.dialog.open("projectApplyController/itemSelector.do?PROJECT_CODE="+PROJECT_CODE+"&FLOW_CATE_ID=" + FLOW_CATE_ID, {
				title : "事项列表",
				width : "1200px",
				height : "610px",
				lock : true,
				resize : false,
				close : function() {
					var selectProjectItemInfo = art.dialog.data("selectProjectItemInfo");
					if (selectProjectItemInfo) {
						var defKey = selectProjectItemInfo.defKeys;
						var itemCode = selectProjectItemInfo.itemCodes;													
						art.dialog.removeData("selectProjectItemInfo");	
						toUrl("executionController.do?goStart&defKey="
								+ defKey + "&itemCode=" + itemCode,PROJECT_CODE);
					}
				}
			}, false); 
		}
	}
	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#projectApplyToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	/**
	 * 显示项目信息信息对话框
	 */
	function showprojectApplyWindow(entityId) {
		$.dialog.open("projectApplyController.do?info&entityId=" + entityId, {
			title : "编辑项目信息",
			width : "1200px",
			height : "610px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("projectApplyToolbar");
	});
function reloadprojectApplyList(){
	$("#projectApplyGrid").datagrid("reload");
}
/**
 * 格式化
 */
function formatState(val,row){
	if(val=="0"){
		return "<font color='red'>注销</font>";
	}else if(val=="1"){
		return "<font color='green'>启用</font>";
	}else{
		return "<font color='blue'></font>";
	}
};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="projectApplyToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_PROJECTAPPLY"
								iconcls="eicon-plus" plain="true"
								onclick="showprojectApplyWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_PROJECTAPPLY"
								iconcls="eicon-pencil" plain="true"
								onclick="editprojectApplyGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_PROJECTAPPLY"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteprojectApplyGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" reskey="LIST_PROJECTAPPLY"
								iconcls="eicon-list" plain="true"
								onclick="listItemGridRecord();">事项列表</a>
							<a href="#"
                                class="easyui-linkbutton" reskey="CANCEL_PROJECTAPPLY"
                                iconcls="eicon-remove" plain="true"
                                onclick="cancelprojectApplyGridRecord();">注销</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="projectApplyForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">项目代码：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_CODE_LIKE"/>
	    </td> 
        <td style="width:70px;text-align:right;">项目名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_NAME_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('projectApplyToolbar','projectApplyGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('projectApplyForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="projectApplyGrid" fitcolumns="true" toolbar="#projectApplyToolbar"
			method="post" idfield="ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectApplyController.do?datagrid&Q_T.CREATOR_ID_EQ=">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true">ID</th>	
					<th data-options="field:'PROJECT_CODE',align:'left'" width="20%">项目代码</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="30%">项目名称</th>		
					<!--<th data-options="field:'FLOW_CATE_NAME',align:'left'" width="150">项目分类</th>-->
					<th data-options="field:'APPLY_DATE',align:'left'" width="15%">申报时间</th>				
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">创建时间</th>
					<th data-options="field:'PRO_STATUS',align:'left'" width="15%" formatter="formatState">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
