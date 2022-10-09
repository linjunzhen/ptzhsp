<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteTabletGridRecord() {
		defdelete("tabletMenuController.do?multiDel",
				"tabletMenuGrid");
	};
	function defdelete(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			art.dialog.confirm("删除清单会删除对应的服务事项，您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $dataGrid.datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				$.post(url,{
					   selectColNames:selectColNames
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
							if(callback!=null){
								callback.call(this,resultJson);
							}else{
								$dataGrid.datagrid('reload');
								$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
							}
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
		}
	}
	/**
	 * 编辑服务事项列表记录
	 */
	function editTabletGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("tabletMenuGrid");
		if (entityId) {
			showTabletWindow(entityId);
		}
	}
	
	function editTabletServiceGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("tabletMenuGrid");
		if (entityId) {
			selectCatalog(entityId);
		}
	}
    
    /**
	 * 显示办事部门信息对话框
	 */
	function showDeptMngWindow(entityId) {
	    parent.$.dialog.open("tabletMenuController.do?deptInfo&allowCount=20", {
            title : "办事部门选择",
            width : "800px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectBSDeptInfo = art.dialog.data("selectBSDeptInfo");
                if(selectBSDeptInfo&&selectBSDeptInfo.departIds){
	    				setOfficeSelectItems(selectBSDeptInfo.departIds,selectBSDeptInfo.departNames,
	    					selectBSDeptInfo.departCodes,selectBSDeptInfo.treeSns);
	    				art.dialog.removeData("selectBSDeptInfo");
	    		}
            }
        }, false);
	}
    
    function setSelectItems(itemIds, itemNames,codes) {
		var rowsData = $("#tabletMenuGrid").datagrid("getChecked");
		var row = rowsData[0];
		AppUtil.ajaxProgress({
			url : "tabletMenuController.do?saveMenuItems",
			params : {
				"tabletId" : row.TABLET_ID,
				"itemIds" : itemIds,
				"itemNames":itemNames,
				"itemCodes":codes
			},
			callback : function(resultJson) {
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				//AppUtil.gridDoSearch("Toolbar", "SysRoleGrid");
			}
		});
	}

	/**
	 * 显示目录信息对话框
	 */
	function showTabletWindow(entityId) {
		if(entityId){
		    $.dialog.open("tabletMenuController.do?info&entityId=" + entityId, {
		        title : "清单信息",
		        width:"600px",
		        lock: true,
		        resize:false,
		        height:"260px",
		    }, false);
		}else{
	            $.dialog.open("tabletMenuController.do?info", {
	                title : "清单信息",
	                width:"600px",
	                lock: true,
	                resize:false,
	                height:"260px",
	            }, false);
	    }
	};
	
	
	function setOfficeSelectItems(itemIds, itemNames,codes,sns) {
		var rowsData = $("#tabletMenuGrid").datagrid("getChecked");
		var row = rowsData[0];
		AppUtil.ajaxProgress({
			url : "tabletMenuController.do?saveWorkDept",
			params : {
				"departIds" : itemIds,
				"departNames":itemNames,
				"departCodes":codes,
				"treeSns":sns
			},
			callback : function(resultJson) {
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				//AppUtil.gridDoSearch("Toolbar", "SysRoleGrid");
			}
		});
	}
	$(document).ready(function() {
		//AppUtil.initAuthorityRes("tabletMenuToolbar");
	});

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
function formatType(val,row){
	if(val=="0"){
		return "办事咨询";
	}else{
		return "其他咨询";
	}
};
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="tabletMenuToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="EDIT_tabletdeptmng" iconCls="icon-note-edit"
                                plain="true" onclick="showDeptMngWindow();">办事部门管理</a>
								<a href="#" class="easyui-linkbutton"  iconCls="icon-note-add"
                                plain="true" onclick="showTabletWindow();">新建</a>
                            <a href="#" class="easyui-linkbutton"  iconCls="icon-note-edit"
                                plain="true" onclick="editTabletGridRecord();">编辑</a>
                            <a href="#" class="easyui-linkbutton"  iconCls="icon-note-delete" plain="true"
                                onclick="deleteTabletGridRecord();">删除</a><!-- 
                                <a href="#" class="easyui-linkbutton" resKey="EDIT_tabletServiceItem" iconCls="icon-note-edit"
                                plain="true" onclick="editTabletServiceGridRecord();">编辑清单服务事项</a>   -->
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="tabletMenuForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">清单标题：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.TABLET_NAME_LIKE" />
	    </td> 
	   <td style="width:68px;text-align:right;">清单性质</td>
       <td style="width:135px;"><input class="easyui-combobox" style="width:128px;" name="Q_T.ITEM_KIND_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
       </td>
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('tabletMenuToolbar','tabletMenuGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('tabletMenuForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="tabletMenuGrid" fitcolumns="true" toolbar="#tabletMenuToolbar"
			method="post" idfield="TABLET_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="tabletMenuController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TABLET_ID',hidden:true" width="80">TABLET_ID</th>
					<th data-options="field:'TABLET_NAME',align:'left'" width="136">清单标题</th>	
					<th data-options="field:'DEPART_NAME',align:'left'" width="96">办事部门</th>
					<th data-options="field:'ITEM_KIND',align:'left'" width="80">清单性质</th>
				<!-- 	<th data-options="field:'REPLY_FLAG',align:'left'" width="60" formatter="formatState">回复状态</th>  -->
					<th data-options="field:'CREATE_TIME',align:'left'" width="80">创建时间</th>   
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
