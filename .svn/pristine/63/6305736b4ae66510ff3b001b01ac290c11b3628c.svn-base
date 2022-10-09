<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
 	* 发起变更对话框
 	*/
	function showGoChangeWindow() {
		$.dialog.open("busSpecialChangeController.do?goChange", {
			title : "发起变更信息",
        	width:"600px",
        	lock: true,
        	resize:false,
        	height:"200px",
		}, false);
	}
	/**
	 * 删除专项列表记录
	 */
	function deleteSpecialChangeGridRecord() {
		AppUtil.deleteDataGridRecord("busSpecialController.do?specialMuitDel",
				"SpecialChangeGrid");
	};
	/**
	 * 编辑专项列表记录
	 */
	function editSpecialChangeGridRecord() {
		var entityId = AppUtil.getStatusDataGridRecord("SpecialChangeGrid");
		if (entityId) {
			showSpecialChangeWindow(entityId);
		}
	}
	/**
	 * 显示专项信息对话框
	 */
	function showSpecialChangeWindow(entityId) {
		$.dialog.open("busSpecialChangeController.do?specialInfo&entityId=" + entityId, {
    		title : "专项信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"400px"
    	}, false);
	}
	
	/**
	 * 删除环节列表记录
	 */
	function deleteTacheChangeGridRecord() {
		//var entityId = AppUtil.getStatusDataGridRecord("SpecialChangeGrid");
		//if (entityId) {
		AppUtil.deleteStatusDataGridRecord("busSpecialChangeController.do?tacheMuitDel",
				"TacheChangeGrid");
		//}
	};
	/**
	 * 编辑环节列表记录
	 */
	function editTacheChangeGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("SpecialChangeGrid");
		if (entityId) {
			showTacheChangeWindow(entityId);
		}
	}
	/**
	 * 显示环节信息对话框
	 */
	function showTacheChangeWindow(entityId) {
		
		var entityId = AppUtil.getStatusDataGridRecord("SpecialChangeGrid");
		if(!entityId){
			return;
		}
		var busCode = $("#tache_change_buscode").val();
		var applyId = $("#tache_change_applyid").val();
		if (busCode!="bus_code"){
			var url = "busSpecialChangeController.do?toTacheEdit&busCode="+busCode+"&applyId="+applyId;
			$.dialog.open(url, {
    			title : "环节信息",
            	width:"600px",
            	lock: true,
            	resize:false,
            	height:"400px",
    		}, false);
		}else{
			alert('请选择业务专项!');
		}
	}
	
	$(document).ready(function() {
		$('#SpecialChangeGrid').datagrid({  //初始化datagrid
		    url:'busSpecialChangeController.do?datagrid',
		    idField:"CHANGE_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    nowrap:false,
		    checkOnSelect:true,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:true,
		    nowrap:false,
		 	pageSize:15,
		 	pageList:[15,20,30],
		    toolbar: "#SpecialChangeToolbar",
		    queryParams: {
		    	//"Q_T.SITE_ID_=" : $('#topic_website_id').combobox('getValue')
			},	
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'CHANGE_ID',hidden:true},
		        {field:'APPLY_ID',hidden:true},
		        {field:'UNIT_NAME',title:'业务单位',width:100,align:'left'},
		        {field:'BUS_CODE',title:'专项编码',width:140,align:'left'},
		        {field:'BUS_NAME',title:'专项名称',width:200,align:'left'},
		        {field:'APPLY_NAME',title:'变更描述',width:150,align:'left'},
		        {field:'UPDATE_TIME',title:'更新时间',width:130,align:'left'},
		        {field:'STATUS',title:'审核状态',width:80,align:'left',formatter:function(value,row,index){
		        	if (row.STATUS==0){
		    			return "<b><font color=#AAAAAA>暂存</font></b>";
		    		} else if(row.STATUS==1){
		    			return "<b><font color=#3399ff>已确认</font></b>";
		    		}else if(row.STATUS==2){
		    			return "<b><font color=blue>待审核</font></b>";
		    		}else if(row.STATUS==3){
		    			return "<b><font color=green>审核通过</font></b>";
		    		}else if(row.STATUS==4){
		    			return "<b><font color=red>审核不通过</font></b>";
		    		}else if(row.STATUS==5){
		    			return "<b><font color=#444444>关闭</font></b>";
		    		}else{
		    			return "<b><font color=#3399ff>已确认</font></b>";
		    		}
		    	}}
		    ]],
		    onClickRow:function (index, row) {
                $("#tache_change_buscode").val(row.BUS_CODE);
                $("#tache_change_applyid").val(row.APPLY_ID);
                AppUtil.gridDoSearch('TacheChangeToolbar','TacheChangeGrid');
            }
		});	
		$('#TacheChangeGrid').datagrid({  //初始化datagrid
			url:'busSpecialChangeController.do?tacheDatagrid',
		    idField:"CHANGE_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:true,
		    selectOnCheck:true,
		    singleSelect:false,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#TacheChangeToolbar",
		    queryParams: {
		    	"Q_T.BUS_CODE_EQ" : $("#tache_change_buscode").val(),
		    	"Q_T.APPLY_ID_EQ" :	$("#tache_change_applyid").val(),
		    	"Q_T.IS_VALID_EQ" : '1'
			},	
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'CHANGE_ID',hidden:true},
		        {field:'STATUS',hidden:true},
		        {field:'TACHE_CODE',title:'环节编码',width:100},
		        {field:'TACHE_NAME',title:'环节名称',width:200,align:'left'}
		        ]]
		});
		AppUtil.initAuthorityRes("SpecialChangeToolbar");
		AppUtil.initAuthorityRes("TacheChangeToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" style="width:70%;">
		<!-- =========================查询面板开始========================= -->
		<div id="SpecialChangeToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								iconcls="icon-note-add" plain="true"
								onclick="showGoChangeWindow();">发起变更</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Special"
								iconcls="icon-note-edit" plain="true"
								onclick="editSpecialChangeGridRecord();">编辑专项</a> 
								<%--<a href="#"
								class="easyui-linkbutton" reskey="DEL_Special"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteSpecialChangeGridRecord();">删除专项</a>
						--%></div>
					</div>
				</div>
			</div>
			<form action="#" name="SpecialForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">专项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUS_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">状态</td>
							<td style="width:135px;">
							<input class="easyui-combobox" style="width:128px;"
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' "
								name="Q_T.STATUS_LIKE" /></td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SpecialChangeToolbar','SpecialChangeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SpecialForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  id="SpecialChangeGrid" ></table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'center',split:false">	
		<!-- =========================查询面板开始========================= -->
		<div id="TacheChangeToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" id="tache_change_buscode" value="bus_code" name="Q_T.BUS_CODE_EQ">
			<input type="hidden" id="tache_change_applyid" value="apply_id" name="Q_T.APPLY_ID_EQ">		
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Special"
								iconcls="icon-note-add" plain="true"
								onclick="showTacheChangeWindow();">编辑</a> 
								<%--<a href="#"
								class="easyui-linkbutton" reskey="EDIT_Special"
								iconcls="icon-note-edit" plain="true"
								onclick="editTacheChangeGridRecord();">编辑环节</a> 
								--%><a href="#"
								class="easyui-linkbutton" reskey="DEL_Special"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteTacheChangeGridRecord();">删除环节</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="TacheForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">环节名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.TACHE_NAME_LIKE" />
								
								<input class="eve-input"
								type="hidden" maxlength="20"
								name="Q_T.IS_VALID_EQ" value="1" />
								</td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('TacheChangeToolbar','TacheChangeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('TacheForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table id="TacheChangeGrid"></table>
		<!-- =========================表格结束==========================-->		
	</div>
</div>