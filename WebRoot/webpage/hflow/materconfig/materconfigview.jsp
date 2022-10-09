<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 删除流程表单列表记录
 */
function deleteNodeAuditerDataGridRecord() {
	AppUtil.deleteDataGridRecord("nodeAuditerController.do?multiDel",
			"materConfigGrid");
};
/**
 * 编辑流程表单列表记录
 */
function editNodeAuditerGridRecord() {
	var CONFIG_ID = AppUtil.getEditDataGridRecord("materConfigGrid");
	if (CONFIG_ID) {
		showNodeAuditerWindow(CONFIG_ID);
	}
}

/**
 * delDocTemplate
 */
function delDocTemplate(){
	AppUtil.deleteDataGridRecord("materConfigController.do?multiDel",
	"materConfigGrid");
}

function updateDicSn(){
	var rows = $("#materConfigGrid").datagrid("getRows"); 
	var configIds = [];
	for(var i=0;i<rows.length;i++){
		configIds.push(rows[i].CONFIG_ID);
	}
	if(configIds.length>0){
		AppUtil.ajaxProgress({
			url:"materConfigController.do?updateSn",
			params:{
				configIds:configIds
			},
			callback:function(resultJson){
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				$("#materConfigGrid").datagrid("reload");
			}
		})
	}
	
}

/**
 * 显示流程表单信息对话框
 */
function bindDocTemplate(entityId) {
	var defId = $("input[name='defId']").val();
	var nodeName = $("input[name='nodeName']").val();
	parent.$.dialog.open("documentTemplateController.do?selector", {
        title : "审批模版选择器",
        width : "800px",
        lock : true,
        resize : false,
        height : "500px",
        close: function () {
            var selectDocumentInfo = art.dialog.data("selectDocumentInfo");
            if(selectDocumentInfo&&selectDocumentInfo.documentIds){
                AppUtil.ajaxProgress({
                    url : "materConfigController.do?bindTemplate",
                    params : {
                        "defId" : defId,
                        "nodeName" : nodeName,
                        "tplIds" : selectDocumentInfo.documentIds
                    },
                    callback : function(resultJson) {
                        if (resultJson.success) {
                            parent.art.dialog({
                                content: resultJson.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                            art.dialog.removeData("selectDocumentInfo");
                            $("#materConfigGrid").datagrid("reload");
                        } else {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "error",
                                ok : true
                            });
                        }
                    }
                });
            }
        }
    }, false);

};

$(function(){
	var nodeName = $("input[name='NODE_NAME']").val();
	$("#materConfigGrid").datagrid({
		url:"materConfigController.do?datagrid",
		queryParams: {
			"Q_T.NODE_NAME_EQ": nodeName,
			"Q_T.DEF_ID_EQ": '${nodeData.defId}',
			"Q_T.DEF_VERSION_EQ": '${nodeData.flowVersion}'
		}
	});
});


function formatIsBackfill(val,row){
    if(val=="1"){
        return "<font color='green'><b>是</b></font>";
    }else if(val=="-1"){
        return "<font color='red'><b>否</b></font>";
    }
}

function needBackfill(){
	updateIsBackfill("materConfigController.do?updateIsBackfill&isBackfill=1","materConfigGrid");
}

function noBackfill(){
	updateIsBackfill("materConfigController.do?updateIsBackfill&isBackfill=-1","materConfigGrid");
}
//更新材料是否回填
function updateIsBackfill(url,gridId){
    var $dataGrid = $("#"+gridId);
    var rowsData = $dataGrid.datagrid('getChecked');
    if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
        art.dialog({
            content: "请选择需要修改的记录!",
            icon:"warning",
            ok: true
        });
    }else{
        art.dialog.confirm("您确定要修改该记录吗?", function() {
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
                            $dataGrid.datagrid('reload');
                            $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
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

</script>

<div class="easyui-layout" fit="true">
	<div region="center">
	    <input type="hidden" name="defId" value="${nodeData.defId}">
	    <input type="hidden" name="nodeName" value="${nodeData.nodeName}">
		<!-- =========================查询面板开始=========================-->
		<div id="materConfigToolBar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_FlowDef"
								iconCls="icon-note-add" plain="true"
								onclick="bindDocTemplate();">绑定模版</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="DEL_FlowDef"
								iconCls="icon-note-delete" plain="true"
								onclick="delDocTemplate();">删除模版</a>
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_FlowDef"
								iconCls="icon-save" plain="true"
								onclick="updateDicSn();">保存排序</a> 
							<a href="#"
                                 class="easyui-linkbutton" resKey="EDIT_FlowDef"
                                 iconCls="icon-ok" plain="true"
                                 onclick="needBackfill();">设置回填</a>  
                            <a href="#"
                                  class="easyui-linkbutton" resKey="EDIT_FlowDef"
                                  iconCls="icon-no" plain="true"
                                  onclick="noBackfill();">设置不回填</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="materConfigGrid" fitcolumns="true" toolbar="#materConfigToolBar"
			method="post" idfield="CONFIG_ID" checkonselect="false"
			data-options="onLoadSuccess:function(){
			     $(this).datagrid('enableDnd');
			}"
			selectoncheck="false" fit="true" border="false">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CONFIG_ID',hidden:true" width="80">CONFIG_ID</th>
					<th data-options="field:'DOCUMENT_NAME',align:'left'" width="100">模版名称</th>
					<th data-options="field:'DIC_NAME',align:'left'" width="40" >模版类型</th>
					<th data-options="field:'CONFIG_SN',align:'left'" width="40" >排序值</th>
					<th data-options="field:'IS_BACKFILL',align:'left'" width="40" formatter="formatIsBackfill">是否回填</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
