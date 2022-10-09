<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    $(document).ready(function() {

        $("#FlowTypeGrid").datagrid({
            onClickRow : function(index, row) {
                doloadStage(row.TYPE_ID, row.TYPE_NAME);
            }
        });
        $("#FlowConfigFlowGrid").datagrid({
            onClickRow : function(index, row) {
                doloadLink(row.STAGE_ID, row.NAME);
            }
        });

    });




    function deleteFlowConfigGridRecord(id) {
        AppUtil.deleteDataGridRecord("flowTemplateController.do?stageMultiDel", id,function(){	
			$("#"+id).datagrid("reload");		
			$("#flowConfigItemGrid").datagrid("reload");
		});
    };
    function deleteflowConfigItemGridRecord(id) {
        AppUtil.deleteDataGridRecord("flowTemplateController.do?itemMultiDel", id);
    };
    
    function offShelvesflowConfigItemGridRecord(id) {
        offShelvesDataGridRecord("flowTemplateController.do?itemOffShelves", id);
    };
    
   function  offShelvesDataGridRecord(url,gridId,callback){
        var $dataGrid = $("#"+gridId);
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要被下架的记录!",
                icon:"warning",
                ok: true
            });
        }else{
            art.dialog.confirm("您确定要下架该记录吗?", function() {
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
                            $dataGrid.datagrid('reload');
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
    
    function goOffShelvesItemWindow() {
        if ($('#STAGE_ID').val() == '-1' || ''==$('#STAGE_ID').val()) {
            $.messager.alert('Warning', '请先选择集成流程');
        } else {
        var STAGE_ID = $('#STAGE_ID').val();
             var url = "flowTemplateController.do?goOffShelvesItemList&STAGE_ID=" + STAGE_ID;
        $.dialog.open(url, {
            title : "下架事项列表",
            width : "1000px",
            height : "500px",
            lock : true,
            resize : false
        }, false);
        }
    }    
    
    /**
     * 显示系统用户信息对话框
     */
    function showFlowConfigWindow(entityId, typeId) {
        var url = "flowTemplateController.do?stageInfo&entityId=" + entityId
                + "&TYPE_ID=" + typeId;
        $.dialog.open(url, {
            title : "下架事项列表",
            width : "680px",
            height : "300px",
            lock : true,
            resize : false
        }, false);
    };    
    
    
    
    
    function editFlowConfigFlowGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("FlowConfigFlowGrid");
        if (entityId) {
            showFlowConfigWindow(entityId);
        }
    }
    function editflowConfigItemGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("flowConfigItemGrid");
        if (entityId) {
            showflowConfigItemWindow(entityId);
        }
    }

    /**
     * 显示系统用户信息对话框
     */
    function showFlowConfigWindow(entityId, typeId) {
        var url = "flowTemplateController.do?stageInfo&entityId=" + entityId
                + "&TYPE_ID=" + typeId;
        $.dialog.open(url, {
            title : "流程阶段配置",
            width : "680px",
            height : "300px",
            lock : true,
            resize : false
        }, false);
    };

    /**
     * 显示系统用户信息对话框
     */
    function showflowConfigItemWindow(entityId, STAGE_ID) {
        var url = "flowTemplateController.do?itemInfo&entityId=" + entityId
                + "&STAGE_ID=" + STAGE_ID;
        $.dialog.open(url, {
            title : "阶段事项配置",
            width : "95%",
            height : "95%",
            lock : true,
            resize : false
        }, false);
    };

    function formatFlowName(val, row) {
            return val;
    }
    function formatType(val, row) {
        if (row.IS_KEY_ITEM == '1') {
            return "里程碑"
        } else {
            return "普通";
        }
    } 
	function formatItemName(val, row) {
        if(val){
			return val+"（"+row.ITEM_CODE+"）";
		} else {
			return "";
		}
    }


    function doloadStage(typeId, title) {
        $('#STAGE_ID').val("");
        $('#flow_typeId').val(typeId);
        $('#FlowConfigFlowGrid').datagrid(
                {
                    url : 'flowTemplateController.do?stageDatagrid&Q_T.TYPE_ID_EQ='
                            + typeId
                });
        $('#flowConfigItemGrid').datagrid('loadData', {
            total : 0,
            rows : []
        });
    }

    function doloadLink(STAGE_ID, title) {

        $('#STAGE_ID').val(STAGE_ID);
        $('#flowConfigItemGrid')
                .datagrid(
                        {
                            url : 'flowTemplateController.do?itemDatagrid&Q_T.IS_OFF_SHELVES_EQ=0&limit=350&Q_T.STAGE_ID_EQ='
                                    + STAGE_ID
                        });
    }


    function doAddFlow() {
        if ($('#flow_typeId').val() == '-1'||''==$('#flow_typeId').val()) {
            $.messager.alert('Warning', '请先选择流程类型');
        } else {
            showFlowConfigWindow('', $('#flow_typeId').val());
        }
    }
    function doAddLink() {
        if ($('#STAGE_ID').val() == '-1' || ''==$('#STAGE_ID').val()) {
            $.messager.alert('Warning', '请先选择集成流程');
        } else {
            showflowConfigItemWindow('', $('#STAGE_ID').val());
        }
    }
	
	
	function deleteFlowConfigTypeGridRecord() {
		AppUtil.deleteDataGridRecord("flowConfigTypeController.do?multiDel",
				"FlowTypeGrid");
	};

	function editFlowConfigTypeGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FlowTypeGrid");		
		if (entityId) {
			showFlowConfigTypeWindow(entityId);
		}
	}

	function showFlowConfigTypeWindow(entityId) {
		$.dialog.open("flowConfigTypeController.do?info&entityId=" + entityId, {
			title : "流程类型信息",
			width : "680px",
			lock : true,
			resize : false,
			height : "400px",
		}, false);
	};
</script>

<div class="eui-col-box clearfix">
	<div class="eui-col3-box" style="width: 25%;">
		<div class='easyui-panel' style="width: 98%; height: 99%;"
			title='流程类型'>
			<div id="FlowConfigTypeToolbar">
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton" reskey="ADD_FlowType"
									iconcls="eicon-plus" plain="true"
									onclick="showFlowConfigTypeWindow();">新建</a> <a href="#"
									class="easyui-linkbutton" reskey="EDIT_FlowType"
									iconcls="eicon-pencil" plain="true"
									onclick="editFlowConfigTypeGridRecord();">编辑</a> <a href="#"
									class="easyui-linkbutton" reskey="DEL_FlowType"
									iconcls="eicon-trash-o" plain="true"
									onclick="deleteFlowConfigTypeGridRecord();">删除</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================查询面板结束========================= -->
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="FlowTypeGrid" fitcolumns="true" nowrap='false'
				toolbar="#FlowConfigTypeToolbar" method="post" idfield="TYPE_ID"
				singleSelect="true" checkonselect="false" selectoncheck="false"
				fit="true" border="false" url="flowConfigTypeController.do?datagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'TYPE_ID',hidden:true" width="80">TYPE_ID</th>
						<th data-options="field:'TYPE_NAME',align:'left'" width="150">审批流程名称</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="eui-col3-box" style="width: 35%;">
		<div id='flowDiv' class='easyui-panel'
			style="width: 98%; height: 99%;" title='流程阶段'>
			<div id="FlowConfigFlowToolbar">
				<!--====================开始编写隐藏域============== -->
				<input id='flow_typeId' type="hidden" name="Q_T.TYPE_ID_EQ"
					value='-1' />
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton" reskey="Add_FlowConfig"
									iconcls="eicon-plus" plain="true" onclick="doAddFlow();">新建</a>
								<a href="#" class="easyui-linkbutton" reskey="EDIT_SysUser"
									iconcls="eicon-pencil" plain="true"
									onclick="editFlowConfigFlowGridRecord();">编辑</a> <a href="#"
									class="easyui-linkbutton" reskey="DEL_SysUser"
									iconcls="eicon-trash-o" plain="true"
									onclick="deleteFlowConfigGridRecord('FlowConfigFlowGrid');">删除</a>
								<!--<a href="#" class="easyui-linkbutton" reskey="DEL_SysUser"
									iconcls="icon-note-add" plain="true"
									onclick="doAddFlowTask();">生成默认流程</a>-->
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================查询面板结束========================= -->
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="FlowConfigFlowGrid" fitcolumns="true" nowrap='false'
				toolbar="#FlowConfigFlowToolbar" method="post" idfield="STAGE_ID"
				singleSelect="true" checkonselect="false" selectoncheck="false"
				fit="true" border="false" url="">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'STAGE_ID',hidden:true" width="80">STAGE_ID</th>
						<th data-options="field:'NAME',align:'left'" width="150">流程阶段名</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="150"
							formatter="formatItemName">绑定事项</th>
						<th data-options="field:'TREE_SN',align:'left'" width="40">排序</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div class="eui-col3-box" style="width: 40%;">
		<div id='linkDiv' class='easyui-panel'
			style="width: 98%; height: 99%;" title='阶段事项'>
			<div id="flowConfigItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<input id='STAGE_ID' type="hidden" name="Q_T.STAGE_ID_EQ" value='-1' />
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton" reskey="Add_FlowConfig"
									iconcls="eicon-plus" plain="true" onclick="doAddLink();">新建</a>
								<a href="#" class="easyui-linkbutton" reskey="EDIT_SysUser"
									iconcls="eicon-pencil" plain="true"
									onclick="editflowConfigItemGridRecord();">编辑</a> <a href="#"
									class="easyui-linkbutton" reskey="DEL_SysUser"
									iconcls="eicon-trash-o" plain="true"
									onclick="deleteflowConfigItemGridRecord('flowConfigItemGrid');">删除</a>
								<a href="#" class="easyui-linkbutton" reskey="OFF_Shelves"
									iconcls="eicon-remove" plain="true"
									onclick="offShelvesflowConfigItemGridRecord('flowConfigItemGrid');">下架</a>
								<a href="#" class="easyui-linkbutton" reskey="OFF_ShelvesList"
									iconcls="eicon-list" plain="true"
									onclick="goOffShelvesItemWindow();">下架事项列表</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================查询面板结束========================= -->
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="flowConfigItemGrid" fitcolumns="true" nowrap='false'
				toolbar="#flowConfigItemToolbar" method="post" idfield="ID"
				singleSelect="true" checkonselect="false" selectoncheck="false"
				fit="true" border="false" url="">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ID',hidden:true" width="80">ID</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="250"
							formatter="formatFlowName">事项名</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="80">事项编码</th>
						<th data-options="field:'IS_KEY_ITEM',align:'left'" width="60"
							formatter="formatType">事项类型</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
