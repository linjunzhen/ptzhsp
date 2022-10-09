
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	/**
	 * 删除目录列表记录
	 */
	function deleteCatalogGridRecord() {
	    AppUtil.deleteDataGridRecord("catalogController.do?multiDel",
	            "catalogGrid");
	};
	/**
	 * 编辑目录列表记录
	 */
	function editCatalogGridRecord() {
	    var entityId = AppUtil.getEditDataGridRecord("catalogGrid");
	    if (entityId) {
	        showCatalogWindow(entityId);
	    }
	}
	
	/**
	 * 显示目录信息对话框
	 */
	function showCatalogWindow(entityId) {
		if(entityId){
		    $.dialog.open("catalogController.do?info&entityId=" + entityId, {
		        title : "事项目录信息",
		        width:"600px",
		        lock: true,
		        resize:false,
		        height:"300px",
		    }, false);
		}else{
	        var departId = $("#catalogToolbar input[name='DEPART_ID']").val();
	        if(departId&&departId!="0"){
	            var treeObj = $.fn.zTree.getZTreeObj("caDepartmentTree");
	            var treeNode = treeObj.getNodesByParam("id",departId, null)[0];
	            $.dialog.open("catalogController.do?info&DEPART_ID="+treeNode.id+"&DEPART_NAME="+treeNode.name, {
	                title : "目录信息",
	                width:"600px",
	                lock: true,
	                resize:false,
	                height:"300px",
	            }, false);
	        }else{
	            art.dialog({
	                content: "请先选择组织!",
	                icon:"warning",
	                ok: true
	            });
	        }
	    }
	};
	/**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#catalogToolbar input[name='DEPART_ID']").val(treeNode.id);
			if(treeNode.TREE_LEVEL=='4'){
				$("#catalogToolbar input[name='Q_SC.CHILD_DEPART_ID_=']").val(treeNode.id);
				$("#catalogToolbar input[name='Q_D.PATH_LIKE']").val('');
			}else{
				$("#catalogToolbar input[name='Q_SC.CHILD_DEPART_ID_=']").val('');
				$("#catalogToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			}
			AppUtil.gridDoSearch("catalogToolbar", "catalogGrid");
		}
	}

	$(document).ready(function() {
		var caDepartmentTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onDepartmentTreeClick
			},
			async : {
				enable : true,
				url : "departmentController.do?tree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME",
					"targetCols" : "PARENT_ID,PATH,TREE_LEVEL",
					"rootParentId" : "0",
					"Q_STATUS_!=":0,
					"isShowRoot" : "true",
					"rootName" : "组织机构树"
				}
			}
		};
		$.fn.zTree.init($("#caDepartmentTree"), caDepartmentTreeSetting);
		//AppUtil.initAuthorityRes("TreeSysUserToolbar");
	});
	//保存排序
	function updateCSn(){
	        var rows = $("#catalogGrid").datagrid("getRows"); 
	        var catalogIds = [];
	        for(var i=0;i<rows.length;i++){
	        	catalogIds.push(rows[i].CATALOG_ID);
	        }
	        if(catalogIds.length>0){
	            AppUtil.ajaxProgress({
	                url:"catalogController.do?updateSn",
	                params:{
	                	catalogIds:catalogIds
	                },
	                callback:function(resultJson){
	                      parent.art.dialog({
	                            content: resultJson.msg,
	                            icon:"succeed",
	                            time:3,
	                            ok: true
	                        });
	                    $("#catalogGrid").datagrid("reload");
	                }
	            })
	        }
	    
	}
	
	$(document).ready(function() {
        AppUtil.initAuthorityRes("catalogToolbar");
    });
	
	function zyCatalogDepartment(){
		var rowsData = $("#catalogGrid").datagrid("getChecked"); 
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
        	var departId = rowsData[0].DEPART_ID;
			$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
	                +"checkCascadeN=", {
	            title : "组织机构选择器",
	            width:"600px",
	            lock: true,
	            resize:false,
	            height:"500px",
	            close: function () {
	                var selectDepInfo = art.dialog.data("selectDepInfo");
	                if(selectDepInfo){
	                	AppUtil.ajaxProgress({
                            url:"serviceItemController.do?updateSSBMBM",
                            params:{
                                "departId":selectDepInfo.departIds,
                                "catalogId":rowsData[0].CATALOG_ID,
                                "catalogCode":rowsData[0].CATALOG_CODE
                            },
                            callback:function(resultJson){
                                art.dialog({
                                    content: resultJson.msg,
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                                AppUtil.gridDoSearch("catalogToolbar", "catalogGrid");
                            }
                        });
                        art.dialog.removeData("selectDepInfo");
	                }
	            }
	        }, false);
        }
	}
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;组织机构树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="caDepartmentTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="catalogToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_D.PATH_LIKE" />
			<input type="hidden" name="DEPART_ID"  />
			<input type="hidden" name="Q_SC.CHILD_DEPART_ID_=" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							 <a href="#" class="easyui-linkbutton" resKey="ADD_Catalog" iconCls="eicon-plus"
                                plain="true" onclick="showCatalogWindow();">新建</a>
                            <a href="#" class="easyui-linkbutton" resKey="EDIT_Catalog" iconCls="eicon-pencil"
                                plain="true" onclick="editCatalogGridRecord();">编辑</a>
                            <a href="#" class="easyui-linkbutton" resKey="DEL_Catalog" iconCls="eicon-trash-o" plain="true"
                                onclick="deleteCatalogGridRecord();">删除</a>
                            <a href="#" class="easyui-linkbutton" resKey="SAVECSN_Catalog" iconCls="eicon-check" plain="true"
                                onclick="updateCSn();">保存排序</a>
                            <a href="#" class="easyui-linkbutton" resKey="ZY_Catalog" iconCls="eicon-fast-forward" plain="true"
                                onclick="zyCatalogDepartment();">转移目录</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="catalogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
					    <td style="width:68px;text-align:right;">目录编码</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="30" style="width:128px;"
                                name="Q_SC.CATALOG_CODE_LIKE" /></td>
                            <td style="width:68px;text-align:right;">目录名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_SC.CATALOG_NAME_LIKE" /></td>
					    <td style="width:68px;text-align:right;">目录性质</td>
                         <td style="width:135px;"><input class="easyui-combobox"
                                style="width:128px;" name="Q_SC.SXXZ_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
						<td colspan="4"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('catalogToolbar','catalogGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('catalogForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="catalogGrid" fitColumns="true" toolbar="#catalogToolbar"
			method="post" idField="CATALOG_ID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true" fit="true" border="false"
			data-options="onLoadSuccess:function(){
                      $(this).datagrid('enableDnd');
            }"
			url="catalogController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'CATALOG_ID',hidden:true">CATALOG_ID</th>
					<th data-options="field:'CATALOG_NAME',align:'left'" width="30%">目录名称</th>
					<th data-options="field:'CATALOG_CODE',align:'left'" width="15%">目录编码</th>
					<th data-options="field:'SXXZ',align:'left'" width="10%">目录性质</th>
					<th data-options="field:'C_SN',align:'left'" width="8%">排序值</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%">所属部门</th>
					<th data-options="field:'CHILD_DEPART',align:'left'" width="16%">所属子部门</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



