<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deleteWinUserGridRecord() {
		AppUtil.deleteDataGridRecord("callSetController.do?multiDelWinUser",
				"WinUserGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editWinUserGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("WinUserGrid");
		if (entityId) {
			showWinUserGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showWinUserGridRecord(entityId) {
		$.dialog.open("callSetController.do?winUserInfo&entityId=" + entityId, {
			title : "叫号窗口信息",
			width : "800px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	};

	function isContinueGridRecord(statu){
    	var rows = $("#WinUserGrid").datagrid("getChecked");
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
			url : "callSetController.do?isContinue",
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
				AppUtil.gridDoSearch("WinUserToolbar", "WinUserGrid");
			}
		});
	}
	
	function isUseGridRecord(statu){
    	var rows = $("#WinUserGrid").datagrid("getChecked");
        if(rows.length==0){
        	 parent.art.dialog({
					content: "请至少选择一条记录",
					icon:"succeed",
					time:3,
					ok: true
				});
			return;
		}
    	var entityIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].RECORD_ID;
		}
		AppUtil.ajaxProgress({
			url : "callSetController.do?isUse",
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
				AppUtil.gridDoSearch("WinUserToolbar", "WinUserGrid");
			}
		});
	}
	
	function limitAB(statu){
		AppUtil.ajaxProgress({
			url : "callSetController.do?limitAB",
			params : {
				"statu" : statu
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("WinUserToolbar", "WinUserGrid");
			}
		});
	}

	function removeWinOccupation() {
		var rows = $("#WinUserGrid").datagrid("getChecked");
		if(rows.length==0){
			parent.art.dialog({
				content: "请至少选择一条记录",
				icon:"succeed",
				time:3,
				ok: true
			});
			return;
		}
		var recordIds = "";
		for(var i = 0;i<rows.length;i++){
			if(rows[i].CUR_USERNAME){
				recordIds+=rows[i].RECORD_ID + ",";
			}
		}

		$.post("callSetController.do?removeWinOccupation",{recordIds:recordIds},function (data) {
			if (data) {
				var json = JSON.parse(data);
				if (json.success) {
					parent.art.dialog({
						content: json.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					AppUtil.gridDoSearch("WinUserToolbar", "WinUserGrid");
				}
			}
		})

	}

	function rowformater(value,row,index){
		if(value=='1'){
			return "<font style='color:#0368ff;'>是</font>";
		}else if(value=='0'){
			return "<font style='color:#ff4b4b;'>否</font>";
		}
	}
	
	function wininfoformater(value,row,index){
		var winInfo = row.BELONG_ROOM+"区，"+row.WIN_NO+"号窗，"+row.SCREEN_NO+"屏";
		if(row.IS_YCTB=='1'){
			winInfo += "<font color='red'>["+row.GROUP_NAME+"]</font>";
		}
		return winInfo;
	}
	
	function winStatusformater(value,row,index){
		if(row.CUR_USERNAME!=""&&row.CUR_USERNAME!=undefined){
			return "工作中";
		}else{
			return "空闲";
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WinUserToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="eicon-plus" plain="true"
								onclick="showWinUserGridRecord();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Win"
								iconcls="eicon-pencil" plain="true"
								onclick="editWinUserGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteWinUserGridRecord();">删除</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-check" plain="true"
								onclick="isContinueGridRecord('1');">可继续受理</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-close" plain="true"
								onclick="isContinueGridRecord('0');">不可继续受理</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-check" plain="true"
								onclick="isUseGridRecord('1');">启用</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-ban" plain="true"
								onclick="isUseGridRecord('0');">禁用</a>
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-check" plain="true"
								onclick="limitAB('1');">开启2.4.6.8.10.12AB类业务</a>
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-ban" plain="true"
								onclick="limitAB('0');">限制2.4.6.8.10.12AB类业务</a>
							<a href="#"
							   class="easyui-linkbutton" reskey="DEL_Win"
							   iconcls="eicon-check" plain="true"
							   onclick="removeWinOccupation();">解除窗口占用</a>
						</div>
					</div>
				</div>
			</div>	
			<form action="#" name="WinUserForm">
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
							<td style="width:68px;text-align:right;">办理人员</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:150px;float:left;" name="Q_t.OPER_USERNAMES_LIKE" /></td>
							<td style="width:68px;text-align:right;">窗口状态</td>
							<td style="width:135px;padding-left:4px;">
								<input class="easyui-combobox"
								style="width:128px;" name="Q_T.WORK_STATUS_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '',value: '请选择'},
									            {label: '1', value: '工作中'},{label: '0',value: '空闲'}],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('WinUserToolbar','WinUserGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('WinUserForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="WinUserGrid" fitcolumns="true" nowrap="false"
			toolbar="#WinUserToolbar" method="post" idfield="RECORD_ID"
			checkonselect="true" selectoncheck="false" fit="true" border="false"
			url="callSetController.do?winUserDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'wininfo',align:'left',formatter:wininfoformater" width="15%">窗口屏信息</th>
					
					<th data-options="field:'OPER_USERNAMES',align:'left'" width="17%">办理人员</th>
					<th data-options="field:'WIN_BUSINESS_NAMES',align:'left'" width="30%">办理业务</th>
					<th data-options="field:'IS_CONTINUE',align:'left',formatter:rowformater" width="10%">是否多次受理</th>
					<th data-options="field:'IS_USE',align:'left',formatter:rowformater" width="7%">启用状态</th>
					<th data-options="field:'winstatus',align:'left',formatter:winStatusformater" width="7%">窗口状态</th>
					<th data-options="field:'CUR_USERNAME',align:'left'" width="10%">当前办理人员</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
