<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除我要纠错列表记录
	 */
	function deleteWyjcGridRecord() {
		AppUtil.deleteDataGridRecord("wyjcController.do?multiDel",
				"WyjcGrid");
	};
	/**
	 * 编辑我要纠错列表记录
	 */
	function editWyjcGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("WyjcGrid");
		if (entityId) {
			showBusWyjcWindow(entityId);
		}
	}

	/**
	 * 显示我要纠错信息对话框
	 */
	function showBusWyjcWindow(entityId) {
		$.dialog.open("wyjcController.do?info&entityId=" + entityId, {
			title : "处理纠错信息",
			width : "800px",
			height : "550px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("WyjcToolbar");
	});
function reloadWyjcList(){
	$("#WyjcGrid").datagrid("reload");
}
/**
 * 格式化
 */
function formatState(val,row){
	if(val=="0"){
		return "<font color='red'>否</font>";
	}else{
		return "<font color='green'>是</font>";
	}
};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WyjcToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-edit" plain="true"
								onclick="editWyjcGridRecord();">处理</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CONSULT"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteWyjcGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="WyjcForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;">
        <td style="width:70px;text-align:right;">标题：</td> 
        <td style="width:126px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:95%;" name="Q_T.ERROR_TITLE_LIKE" />
	    </td> 
	    <td style="width:70px;text-align:right;">错误类型：</td> 
	    <td style="width:126px;"> 			
			<select defaultemptytext="请选择错误类型" class="eve-input" name="Q_T.ERROR_TYPE_=">
				<option value="">请选择错误类型</option>
				<option value="网页错误">网页错误</option>
				<option value="其他">其他</option>
			</select>	
	    </td> 
	    <td style="width:70px;text-align:right;">处理状态：</td> 
	    <td style="width:126px;"> 
			<select defaultemptytext="请选择处理状态" class="eve-input" name="Q_T.REPLY_FLAG_=">
				<option value="">请选择处理状态</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>		
	    </td>
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('WyjcToolbar','WyjcGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('WyjcForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="WyjcGrid" fitcolumns="true" toolbar="#WyjcToolbar"
			method="post" idfield="ERROR_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="wyjcController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ERROR_ID',hidden:true">ERROR_ID</th>
					<th data-options="field:'ERROR_TITLE',align:'left'" width="35%">标题</th>
					<th data-options="field:'ERROR_TYPE',align:'left'" width="10%" >错误类型</th>		
					<th data-options="field:'USER_NAME',align:'left'" width="8%">姓名</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">提交时间</th>   
					<th data-options="field:'REPLY_FLAG',align:'left'" width="8%" formatter="formatState">处理状态</th>
					<th data-options="field:'REPLY_TIME',align:'left'" width="20%">处理时间</th>   
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
