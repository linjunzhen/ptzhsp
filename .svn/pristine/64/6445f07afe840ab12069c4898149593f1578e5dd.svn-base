<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deleteWinScreenGridRecord() {
		AppUtil.deleteDataGridRecord("callSetController.do?multiDelWinScreen",
				"WinScreenGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editWinScreenGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("WinScreenGrid");
		if (entityId) {
			showWinScreenGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showWinScreenGridRecord(entityId) {
		$.dialog.open("callSetController.do?winScreenInfo&entityId=" + entityId, {
			title : "窗口屏绑定信息",
			width : "450px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
	};	
	
	function changeScreenGridRecord(statu){
    	var rows = $("#WinScreenGrid").datagrid("getChecked");
    	var entityIds = "";
    	if(rows.length==0){
       	 	parent.art.dialog({
					content: "请至少选择一条记录",
					icon:"succeed",
					time:3,
					ok: true
				});
			return;
		}
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].RECORD_ID;
		}
		AppUtil.ajaxProgress({
			url : "callSetController.do?winScreenUse",
			params : {
				"entityIds" : entityIds,
				"statu" : statu
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("WinScreenToolbar", "WinScreenGrid");
			}
		});
	}
	
	function rowformater(value,row,index){		
		if(value=='1'){
			return "<font style='color:#0368ff;'>是</font>";
		}else if(value=='0'){
			return "<font style='color:#ff4b4b;'>否</font>";
		}
	}
	/* $(document).ready(function() {
		AppUtil.initAuthorityRes("WinScreenToolbar");

	}); */
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WinScreenToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="eicon-plus" plain="true"
								onclick="showWinScreenGridRecord();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Win"
								iconcls="eicon-pencil" plain="true"
								onclick="editWinScreenGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteWinScreenGridRecord();">删除</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-check" plain="true"
								onclick="changeScreenGridRecord('1');">启用</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-ban" plain="true"
								onclick="changeScreenGridRecord('0');">禁用</a>
						</div>
					</div>
				</div>
			</div>	
			<form action="#" name="WinScreenForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">所属大厅</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_t.BELONG_ROOM_="
								data-options="
	url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=roomNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">窗口编号</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_t.WIN_NO_="
								data-options="
	url:'callSetController.do?loadWinNo&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'VALUE',textField:'TEXT',panelWidth: 128,panelHeight: '120' " />
							</td>
							<td style="width:68px;text-align:right;">屏编号</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_t.SCREEN_NO_="
								data-options="
	url:'callSetController.do?loadScreenNo&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'VALUE',textField:'TEXT',panelWidth: 128,panelHeight: '120' " />
							</td>
							<td style="width:68px;text-align:right;">启用状态</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_t.USE_STATUS_="
								data-options="
	url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('WinScreenToolbar','WinScreenGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('WinScreenForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="WinScreenGrid" fitcolumns="false" nowrap="false"
			toolbar="#WinScreenToolbar" method="post" idfield="RECORD_ID"
			checkonselect="true" selectoncheck="false" fit="true" border="false"
			url="callSetController.do?winScreenDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'BELONG_ROOM',align:'left'" width="15%">所属大厅</th>
					<th data-options="field:'WIN_NO',align:'left'" width="15%">窗口编号</th>
					<th data-options="field:'SCREEN_NO',align:'left'" width="15%" >屏编号</th>
					<th data-options="field:'DEPARTINFO',align:'left'" width="20%" >显示单位名称</th>
					<th data-options="field:'WORD_NUM',align:'left'" width="15%" >显示字符数</th>
					<th data-options="field:'USE_STATUS',align:'left',formatter:rowformater" width="15%" >启用状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
