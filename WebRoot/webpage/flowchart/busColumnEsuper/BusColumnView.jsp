<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/***
	 * 删除数据
	 */
	function removeBusColumnWindow(){
		AppUtil.deleteColumnStatusDataGridRecord(
			"busColumnEsuperController.do?muitDel", "BusEsuperColumnGrid");
	}
	/**
	 * 显示监察字段对话框
	 */
	function showBusColumnWindow(entityId) {
		$.dialog.open("busColumnEsuperController.do?info&entityId=" + entityId, {
    		title : "监察字段信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"480px",
    	}, false);
	}
	/**
	 * 编辑监察字段列表记录
	 */
	function editBusColumnWindow(){
		var entityId = AppUtil.getStatusDataGridRecord("BusEsuperColumnGrid");
		if (entityId) {
			showBusColumnWindow(entityId);
		}
	}
	/**
	 * 查看监察字段列表记录
	 */
	function seeBusColumnWindow(){
		var entityId = AppUtil.getEditDataGridRecord("BusEsuperColumnGrid");
		if (entityId) {
			$.dialog.open("busColumnEsuperController.do?seeInfo&entityId=" + entityId, {
	    		title : "监察字段信息",
	            width:"600px",
	            lock: true,
	            resize:false,
	            height:"450px",
	    	}, false);
    	}
	}
	
	$(document).ready(function() {
		$('#BusEsuperColumnGrid').datagrid({  //初始化datagrid
		    url:'busColumnEsuperController.do?datagrid',
		    idField:"PROCESS_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:true,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:true,
		    nowrap:false,
		 	pageSize:15,
		 	pageList:[15,20,30],
		    toolbar: "#BusEsColumnToolbar",
		    queryParams: {
		    	//"Q_T.SITE_ID_=" : $('#topic_website_id').combobox('getValue')
			},	
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'PROCESS_ID',hidden:true},
		        {field:'BUS_NAME',title:'业务专项',width:150,align:'left'},
		        {field:'TACHE_NAME',title:'业务环节',width:150,align:'left'},
		        {field:'PROCESS_NAME',title:'监察点名称',width:200,align:'left'},
		        {field:'UPDATE_TIME',title:'更新时间',width:150,align:'left'},
		        {field:'STATUS',title:'审核状态',width:150,align:'left',formatter:function(value,row,index){
		    		if (row.STATUS==0){
		    			return "<b><font color=red>暂存</font></b>";
		    		} else if(row.STATUS==1){
		    			return "<b><font color=blue>已确认</font></b>";
		    		}else if(row.STATUS==2){
		    			return "<b><font color=blue>待审核</font></b>";
		    		}else if(row.STATUS==3){
		    			return "<b><font color=blue>审核通过</font></b>";
		    		}else if(row.STATUS==4){
		    			return "<b><font color=red>审核不通过</font></b>";
		    		}else if(row.STATUS==5){
		    			return "<b><font color=red>关闭</font></b>";
		    		}else{
		    			return "<b><font color=red>监察字段未配置</font></b>";
		    		}
		    	}}
		    ]],
		    onDblClickRow:function (index, row) {//onClickRow
		    	//$('#BusEsuperColumnGrid').datagrid('selectRow',index);
		    	var rowsData = $("#BusEsuperColumnGrid").datagrid("getChecked");
		    	if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
		    		return;
		    	}
		    	if(row.STATUS==3 || row.STATUS==2){
		    		seeBusColumnWindow();
		    	}else{
		    		editBusColumnWindow();
		    	}
            }	
		});
		AppUtil.initAuthorityRes("BusEsColumnToolbar");
	});
	
	
	
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BusEsColumnToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<!-- <a href="#" class="easyui-linkbutton" reskey="ADD_BusColumn"
								iconcls="icon-note-add" plain="true"
								onclick="showBusColumnWindow();">新建</a>  -->
								
							<!-- reskey="EDIT_BusColumn" reskey="REMOVE_BusColumn" reskey="SEE_BusColumn"-->
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-edit" plain="true"
								onclick="editBusColumnWindow();">编辑</a>
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-delete" plain="true"
								onclick="removeBusColumnWindow();">删除</a>
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-edit" plain="true"
								onclick="seeBusColumnWindow();">查看</a> 								
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BusEsuperColumnForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">业务专项：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_C.BUS_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">业务环节：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_B.TACHE_NAME_LIKE" /></td>
								
							<td style="width:80px;text-align:right;">监察点名称：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_A.PROCESS_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">审核状态：</td> 
					        <td style="width:135px;"> 
					       	  <input class="easyui-combobox" style="width:128px;"
									 data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
									 method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' "
								     name="Q_D.STATUS_EQ" />
					        </td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusEsColumnToolbar','BusEsuperColumnGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusEsuperColumnForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  id="BusEsuperColumnGrid" ></table>
		<!-- =========================表格结束==========================-->
	</div>
</div>