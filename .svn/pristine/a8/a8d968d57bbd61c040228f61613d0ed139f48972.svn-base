<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 删除专项列表记录
	 */
	function deleteSpecialGridRecord() {
			AppUtil.deleteStatusDataGridRecord("busSpecialController.do?specialMuitDel",
				"SpecialGrid");
	};
	/**
	 * 编辑专项列表记录
	 */
	function editSpecialGridRecord() {
		var entityId = AppUtil.getStatusDataGridRecord("SpecialGrid",0);
		if (entityId) {
			$.dialog.open("busSpecialController.do?specialInfo&entityId="+entityId, {
	    		title : "专项信息",
	            width:"500px",
	            lock: true,
	            resize:false,
	            height:"160px",
	    	}, false);
		}
	}
	/**
	 * 导出专项列表记录
	 */
	function expSpecialGridRecord() {
		var rowsData = $("#SpecialGrid").datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			//if(rowsData[0].STATUS=="3" && rowsData[0].VERSION == "2"){
				window.open("busSpecialController.do?expSpecial&busCode="+rowsData[0].BUS_CODE,rowsData[0].BUS_NAME);
			//}else{
				//art.dialog({
					//content: "该记录未通过第二次阶段审核!",
					//icon:"warning",
				    //ok: true
				//});
				//return null;
			//}
		}
	}
	
	/**
	 * 删除环节列表记录
	 */
	function deleteTacheGridRecord() {
		//var entityId = AppUtil.getStatusDataGridRecord("SpecialGrid");
		//if(entityId){
			AppUtil.deleteStatusDataGridRecord("busSpecialController.do?tacheMuitDel",
				"TacheGrid");
		//}
	};
	/**
	 * 编辑环节列表记录
	 */
	function editTacheGridRecord() {
		var entityId = AppUtil.getStatusDataGridRecord("SpecialGrid");
		if (entityId) {
			showTacheWindow(entityId);
		}
	}
	/**
	 * 显示环节信息对话框
	 */
	function showTacheWindow(entityId) {
		/**if(entityId){
			var url = "busSpecialController.do?tacheInfo&entityId="+entityId;
			$.dialog.open(url, {
	    		title : "环节信息",
	            width:"600px",
	            lock: true,
	            resize:false,
	            height:"400px",
	    	}, false);
		}else{
			var busCode = $("#tache_buscode").val();
			if (busCode){
				var url = "busSpecialController.do?tacheInfo&entityId="+entityId+"&busCode="+busCode;
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
		}*/
		var entityId = AppUtil.getStatusDataGridRecord("SpecialGrid");
		if(!entityId){
			return;
		}
		var busCode = $("#tache_buscode").val();
		if (busCode!="bus_code"){
			var url = "busSpecialController.do?toTacheEdit&busCode="+busCode;
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
	/**
	 * 显示专项信息对话框
	 */
	function showSpecialWindow(entityId) {
		$.dialog.open("busSpecialController.do?specialAdd", {
    		title : "专项信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"400px"
    	}, false);
	}
	
	function copySpecial(){
		var $dataGrid = $("#SpecialGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		} 
		if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}
		var busCode = eval("rowsData[0].BUS_CODE");
		AppUtil.ajaxProgress({
			url : "busSpecialController.do?copySpecial",
			params : {
				"Q_T.BUS_CODE_EQ" : busCode
			},				
			callback : function(resultJson) {
				if (resultJson.success) {
					art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
					$("#SpecialGrid").datagrid("reload");
				} else {
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			}
		});
	}
	$(document).ready(function() {
		$('#SpecialGrid').datagrid({  //初始化datagrid
		    url:'busSpecialController.do?datagrid',
		    idField:"BUS_CODE",
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
		    toolbar: "#SpecialToolbar",
		    queryParams: {
		    	//"Q_T.SITE_ID_=" : $('#topic_website_id').combobox('getValue')
			},	
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'BUS_ID',hidden:true},
		        {field:'VERSION',hidden:true},
		        {field:'UNIT_NAME',title:'业务单位 ',width:100,align:'left'},
		        {field:'BUS_CODE',title:'专项编码',width:100,align:'left'},
		        {field:'BUS_NAME',title:'专项名称',width:200,align:'left'},
		        {field:'UPDATE_TIME',title:'更新时间',width:110,align:'left'},
		        {field:'STATUS',title:'审核状态',width:100,align:'left',formatter:function(value,row,index){
		        	 var stage = "";
						if(row.VERSION == "1"){
							stage="第一阶段"
						}else{
							stage="第二阶段"
						}
			        	if (row.STATUS==0){
			    			return "<b><font color=#AAAAAA>"+stage+"暂存</font></b>";
			    		} else if(row.STATUS==1){
			    			return "<b><font color=#3399ff>"+stage+"已确认</font></b>";
			    		}else if(row.STATUS==2){
			    			return "<b><font color=blue>"+stage+"待审核</font></b>";
			    		}else if(row.STATUS==3){
			    			return "<b><font color=green>"+stage+"审核通过</font></b>";
			    		}else if(row.STATUS==4){
			    			return "<b><font color=red>"+stage+"审核不通过</font></b>";
			    		}else if(row.STATUS==5){
			    			return "<b><font color=#444444>关闭</font></b>";
			    		}else{
			    			return "<b><font color=#3399ff>"+stage+"已确认</font></b>";
			    		}
		    	}}
		    ]],
		    onClickRow:function (index, row) {
                $("#tache_buscode").val(row.BUS_CODE);
                AppUtil.gridDoSearch('TacheToolbar','TacheGrid');
            }
		});	
		$('#TacheGrid').datagrid({  //初始化datagrid
			url:'busSpecialController.do?tacheDatagrid',
		    idField:"TACHE_CODE",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:true,
		    selectOnCheck:true,
		    singleSelect:false,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#TacheToolbar",
		    queryParams: {
		    	"Q_T.BUS_CODE_EQ" : $("#tache_buscode").val()
			},	
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'STATUS',hidden:true},
		        {field:'TACHE_CODE',title:'环节编码',width:100},
		        {field:'TACHE_NAME',title:'环节名称',width:200,align:'left'}
		        ]]
		});
		AppUtil.initAuthorityRes("SpecialToolbar");
		AppUtil.initAuthorityRes("TacheToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" style="width:70%;">
		<!-- =========================查询面板开始========================= -->
		<div id="SpecialToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<span>业务专项：</span>
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-add" plain="true"
								onclick="showSpecialWindow();">新建专项</a> 
								<a href="#"
								class="easyui-linkbutton" 
								iconcls="icon-note-edit" plain="true"
								onclick="editSpecialGridRecord();">编辑专项</a> 
								<a href="#"
								class="easyui-linkbutton" 
								iconcls="icon-note-delete" plain="true"
								onclick="deleteSpecialGridRecord();">删除专项</a>
								<a href="#"
								class="easyui-linkbutton" 
								iconcls="icon-save" plain="true"
								onclick="copySpecial();">生成副本</a>
								<!-- 
								<a href="#"
								class="easyui-linkbutton" 
								iconcls="icon-excel" plain="true"
								onclick="expSpecialGridRecord();">导出专项</a>  -->
						</div>
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
							<td style="width:68px;text-align:right;">审核状态</td>
							<td style="width:135px;">
							<input class="easyui-combobox" style="width:128px;"
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' "
								name="Q_T.STATUS_LIKE" />
								</td>
							<td colspan="4">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SpecialToolbar','SpecialGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SpecialForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  id="SpecialGrid" ></table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'center',split:false">	
		<!-- =========================查询面板开始========================= -->
		<div id="TacheToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" id="tache_buscode" value="bus_code" name="Q_T.BUS_CODE_EQ">	
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<span>环节名称：</span>
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-add" plain="true"
								onclick="showTacheWindow();">编辑</a> 
								<%--<a href="#"
								class="easyui-linkbutton" reskey="EDIT_Special"
								iconcls="icon-note-edit" plain="true"
								onclick="editTacheGridRecord();">编辑环节</a> 
								--%><a href="#"
								class="easyui-linkbutton" 
								iconcls="icon-note-delete" plain="true"
								onclick="deleteTacheGridRecord();">删除环节</a>
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
								name="Q_T.TACHE_NAME_LIKE" /></td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('TacheToolbar','TacheGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('TacheForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		
		<table id="TacheGrid"></table>
		<!-- =========================表格结束==========================-->		
	</div>
</div>