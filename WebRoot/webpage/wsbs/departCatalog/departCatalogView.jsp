
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	function deleteDepartCatalogGrid(){
		AppUtil.deleteDataGridRecord("departCatalogController.do?multiDelCatalog", "departCatalogGrid");
	}
	/**
	 * 编辑目录列表记录
	 */
	function editDepartdepartCatalogGridRecord() {
	    var entityId = AppUtil.getEditDataGridRecord("departCatalogGrid");
	    if (entityId) {
	        showDepartCatalogWindow(entityId);
	    }
	}
	/**
	 * 显示目录信息对话框
	 */
	function showDepartCatalogWindow(entityId) {
		if(entityId){
		    $.dialog.open("departCatalogController.do?catalogInfo&entityId=" + entityId, {
		        title : "目录信息",
		        width:"600px",
		        lock: true,
		        resize:false,
		        height:"320px",
		    }, false);
		}else{
            $.dialog.open("departCatalogController.do?catalogInfo",{//&DEPART_ID="+treeNode.id+"&DEPART_NAME="+treeNode.name, {
                title : "目录信息",
                width:"600px",
                lock: true,
                resize:false,
                height:"320px",
            }, false);
	    }
	};
	/**
     * 导出execl
     */
    function showExcelExportSelect() {
        AppUtil.showExcelExportWin({
            dataGridId:"departCatalogGrid",
            tplKey:"ServiceCataReportTpl",
            excelName:"目录导出",
            queryParams:{
                "SC.CATALOG_CODE":$("input[name='Q_SC.CATALOG_CODE_LIKE']").val(),
                "SC.CATALOG_NAME":$("input[name='Q_SC.CATALOG_NAME_LIKE']").val(),
                "D.PATH":$("input[name='Q_D.PATH_LIKE']").val(),
                "SC.CHILD_DEPART_ID":$("input[name='Q_SC.CHILD_DEPART_ID_=']").val(),
                "SC.SXXZ":$("input[name='Q_SC.SXXZ_=']").val()
            }
        });
    };
	/**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#departCatalogToolbar input[name='DEPART_ID']").val(treeNode.id);
			if(treeNode.TREE_LEVEL=='4'){
				$("#departCatalogToolbar input[name='Q_SC.CHILD_DEPART_ID_=']").val(treeNode.id);
				$("#departCatalogToolbar input[name='Q_D.PATH_LIKE']").val('');
			}else{
				$("#departCatalogToolbar input[name='Q_SC.CHILD_DEPART_ID_=']").val('');
				$("#departCatalogToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			}
			AppUtil.gridDoSearch("departCatalogToolbar", "departCatalogGrid");
		}
	}

	$(document).ready(function() {
		var catalogDepartmentTreeSetting = {
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
				url : "departmentController.do?treeAuth",
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
		$.fn.zTree.init($("#catalogDepartmentTree"), catalogDepartmentTreeSetting);
		//AppUtil.initAuthorityRes("TreeSysUserToolbar");
	});
	//保存排序
	function updateCatalogSn(){
	        var rows = $("#departCatalogGrid").datagrid("getRows"); 
	        var catalogIds = [];
	        for(var i=0;i<rows.length;i++){
	        	catalogIds.push(rows[i].CATALOG_ID);
	        }
	        if(catalogIds.length>0){
	            AppUtil.ajaxProgress({
	                url:"departCatalogController.do?updateSn",
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
	                    $("#departCatalogGrid").datagrid("reload");
	                }
	            })
	        }
	    
	}
	
	function moveDepartCatalogGrid(){
		var $dataGrid = $("#departCatalogGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要划转的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var catalogIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					catalogIds+=",";
				}
				catalogIds+=rowsData[i].CATALOG_ID;
			}
	        parent.$.dialog.open("departmentController/parentSelector.do?allowCount=1&checkCascadeY=&"
	                +"checkCascadeN=", {
	            title : "所属部门选择器",
	            width:"600px",
	            lock: true,
	            resize:false,
	            height:"500px",
	            close: function () {
	                var selectDepInfo = art.dialog.data("selectDepInfo");
	                if(selectDepInfo){
	                    var departId = selectDepInfo.departIds;
	                    var departName = selectDepInfo.departNames;
						art.dialog.removeData("selectDepInfo");
						
						$.post("departCatalogController.do?move", {
							departId:departId,
							catalogIds:catalogIds
						}, function(responseText, status, xhr) {
							var resultJson = $.parseJSON(responseText);
							if (resultJson.success == true) {					
								art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									ok : true
								});
								$dataGrid.datagrid('reload');
							} else {
								art.dialog({
									content : resultJson.msg,
									icon : "error",
									ok : true
								});
							}
						});
	                }
	            }
	        }, false);
        }
	}

	/*
	* 查看事项
	* */
	function checkServiceItem() {
		var rows = $("#departCatalogGrid").datagrid("getChecked");
		var entityId = rows[0].CATALOG_ID;
		if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
				ok: true
			});
			return null;
		}else if (rows.length > 1) {
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon: "warning",
				ok: true
			});
			return null;
		} else {
			$.dialog.open("departCatalogController.do?checkServiceItemView&entityId=" + entityId, {
				title : "查看目录事项",
				width : "700px",
				height : "300px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	function isUseGridRecord(statu){
    	var rows = $("#departCatalogGrid").datagrid("getChecked");
        if(rows.length!=0){
        	parent.art.dialog({
				content: "请选择一条记录",
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
			entityIds+=rows[i].CATALOG_ID;
		}
		AppUtil.ajaxProgress({
			url : "departCatalogController.do?isUse",
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
				AppUtil.gridDoSearch("departCatalogToolbar", "departCatalogGrid");
			}
		});
	}	

	function rowformater(value,row,index){
		if(value=='1'){
			return "<font style='color:blue;'>启用</font>";
		}else if(value=='0'){
			return "<font style='color:red;'>禁用</font>";
		}
	}
	
	$(document).ready(function() {
        AppUtil.initAuthorityRes("departCatalogToolbar");
    });
	
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
	            clone.css({  
	                "position" : "absolute",  
	                left : -1000  
	            }).appendTo("body");  
	            var width = clone.width("auto").width();  
	            if (width > 25) {  
	                //多加5个像素,保持一点边距  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
	                $(this).datagrid("resize");  
	                //一些清理工作  
	                clone.remove();  
	                clone = null;  
	            } else {  
	                //还原成默认状态  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
	            }  
	        });  
	    }  
	});
	$("#departCatalogGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
	
	//空数据，横向滚动
	$('#departCatalogGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
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
		<ul id="catalogDepartmentTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="departCatalogToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_D.PATH_LIKE" /> 
			<input type="hidden" name="DEPART_ID" /> 
			<input type="hidden" name="Q_SC.CHILD_DEPART_ID_=" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_departCatalog"
								iconCls="eicon-plus" plain="true"
								onclick="showDepartCatalogWindow();">新建</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_departCatalog"
								iconCls="eicon-pencil" plain="true"
								onclick="editDepartdepartCatalogGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_departCatalog"
								iconCls="eicon-trash-o" plain="true" 
								onclick="deleteDepartCatalogGrid();">删除</a>
							<a href="#" class="easyui-linkbutton" resKey="MOVE_departCatalog"
								iconCls="eicon-fast-forward" plain="true" 
								onclick="moveDepartCatalogGrid();">划转</a>
							<a href="#" class="easyui-linkbutton" reskey="USE_departCatalog"
								iconcls="eicon-check" plain="true"
								onclick="isUseGridRecord('1');">启用</a>
							<a href="#" class="easyui-linkbutton" reskey="UNUSE_departCatalog"
								iconcls="eicon-ban" plain="true"
								onclick="isUseGridRecord('0');">禁用</a>
							<a href="#" class="easyui-linkbutton" reskey="Expor_DepartServiceItem"
                                iconcls="eicon-file-excel-o" plain="true" 
                                    onclick="showExcelExportSelect();">导出数据</a>
							<a href="#" class="easyui-linkbutton"
							   iconcls="eicon-list" plain="true"
							   onclick="checkServiceItem();">查看事项</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="catalogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">目录编码</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="30" style="width:128px;" name="Q_SC.CATALOG_CODE_LIKE" /></td>
						<td style="width:68px;text-align:right;">目录名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_SC.CATALOG_NAME_LIKE" /></td>
						<td style="width:68px;text-align:right;">目录性质</td>
						<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
							style="width:128px;" name="Q_SC.SXXZ_="
							data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
						</td>
						<td colspan="4"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('departCatalogToolbar','departCatalogGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('catalogForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="departCatalogGrid" fitColumns="true"
			toolbar="#departCatalogToolbar" method="post" idField="CATALOG_ID"
			checkOnSelect="true" selectOnCheck="true" fit="true" border="false" nowrap="false"
			data-options="onLoadSuccess:function(){
                      $(this).datagrid('enableDnd');
            }"
			url="departCatalogController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'CATALOG_ID',hidden:true">CATALOG_ID</th>
					<th data-options="field:'CATALOG_NAME',align:'left'" width="30%">目录名称</th>
					<th data-options="field:'CATALOG_CODE',align:'left'" width="15%">目录编码</th>
					<th data-options="field:'SXXZ',align:'left'" width="8%">目录性质</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="16%">创建时间</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="20%">所属部门</th>
					<th data-options="field:'CHILD_DEPART',align:'left'" width="15%">所属子部门</th>
					<th data-options="field:'IS_USE',align:'left',formatter:rowformater" width="8%">状态</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



