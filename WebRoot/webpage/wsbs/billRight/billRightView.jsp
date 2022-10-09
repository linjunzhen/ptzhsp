<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function deleteBillCatalogGridRecord() {
		defdelete("billRightController.do?multiDelCatalog",
				"billRightGrid");
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
			art.dialog.confirm("删除清单会删除对应的子项清单，您确定要删除该记录吗?", function() {
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
	
	function editBillCatalogGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("billRightGrid");
		if (entityId) {
			showBillCatalogWindow(entityId);
		}
	}
	
	function showBillCatalogWindow(entityId) {
		if (entityId) {
			$.dialog.open("billRightController.do?catalogInfo&entityId=" + entityId,
					{
						title : "清单信息",
						width : "600px",
						lock : true,
						resize : false,
						height : "260px",
			            close: function () {
			                AppUtil.gridDoSearch("billRightToolbar","billRightGrid");
			            }
					}, false);
		} else {
			$.dialog.open("billRightController.do?catalogInfo", {
				title : "清单信息",
				width : "600px",
				lock : true,
				resize : false,
				height : "260px",
	            close: function () {
	                AppUtil.gridDoSearch("billRightToolbar","billRightGrid");
	            }
			}, false);
		}
	}

	function showBillDeptWindow() {
		parent.$.dialog.open("tabletMenuController.do?deptInfo&allowCount=20",
				{
					title : "办事部门选择",
					width : "800px",
					lock : true,
					resize : false,
					height : "500px",
					close : function() {
						var selectBSDeptInfo = art.dialog
								.data("selectBSDeptInfo");
						if (selectBSDeptInfo && selectBSDeptInfo.departIds) {
							setOfficeSelectItems(selectBSDeptInfo.departIds,
									selectBSDeptInfo.departNames,
									selectBSDeptInfo.departCodes,
									selectBSDeptInfo.treeSns);
							art.dialog.removeData("selectBSDeptInfo");
						}
					}
				}, false);
	}
	function setOfficeSelectItems(itemIds, itemNames, codes, sns) {
		var rowsData = $("#billRightGrid").datagrid("getChecked");
		var row = rowsData[0];
		AppUtil.ajaxProgress({
			url : "tabletMenuController.do?saveWorkDept",
			params : {
				"departIds" : itemIds,
				"departNames" : itemNames,
				"departCodes" : codes,
				"treeSns" : sns
			},
			callback : function(resultJson) {
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
			}
		});
	}
	
	
	function BillItemManage() {
		var entityId = AppUtil.getEditDataGridRecord("billRightGrid");
		if (entityId) {
			$.dialog.open("billRightController.do?billItemManage&entityId="
					+ entityId, {
				title : "子项清单管理",
				width : "800px",
				lock : true,
				resize : false,
				height : "500px",
			}, false);
		}

	}
	
	
	function formatter(val,row){
		if(val=="0"){
			return "<font color='red'><b>不公开</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>公开</b></font>";
		}
	};
	
	function selectChildDepartName(){
		var departId = $("input[name='DEPART_ID']").val();
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='Q_D.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
                }
            }
        }, false);
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="billRightToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="EDIT_tabletdeptmng"
								iconCls="eicon-book" plain="true"
								onclick="showBillDeptWindow();">权责部门管理</a> <a href="#"
								class="easyui-linkbutton" iconCls="eicon-plus" plain="true"
								onclick="showBillCatalogWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" iconCls="eicon-pencil" plain="true"
								onclick="editBillCatalogGridRecord();">编辑</a> 
<%--								<a href="#"--%>
<%--								class="easyui-linkbutton" iconCls="icon-note-delete"--%>
<%--								plain="true" onclick="deleteBillCatalogGridRecord();">删除</a> --%>
								<a
								href="#" class="easyui-linkbutton" iconCls="eicon-edit"
								plain="true" onclick="BillItemManage();">子项清单管理</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="billRightForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:70px;text-align:right;">清单编码：</td>
							<td style="width:156px;"><input class="eve-input"
								type="text" maxlength="50" style="width:96%;"
								name="Q_T.ITEM_CATALOGCODE_LIKE" /></td>
							<td style="width:70px;text-align:right;">清单标题：</td>
							<td style="width:156px;"><input class="eve-input"
								type="text" maxlength="50" style="width:96%;"
								name="Q_T.BILL_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">清单性质</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.ITEM_KIND_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="selectChildDepartName();"
                                style="width:128px;" name="DEPART_NAME" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" value=""  />
                            </td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('billRightToolbar','billRightGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('billRightForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="billRightGrid" fitcolumns="true" toolbar="#billRightToolbar"
			method="post" idfield="BILL_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			singleSelect="false" url="billRightController.do?billCatalogData">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'BILL_ID',hidden:true" width="80">BILL_ID</th>
					<th data-options="field:'ITEM_CATALOGCODE',align:'left'" width="136">清单编码</th>
					<th data-options="field:'BILL_NAME',align:'left'" width="136">清单标题</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="100">所属部门</th>
					<th data-options="field:'ITEM_KIND',align:'left'" width="60">清单性质</th>
					<th data-options="field:'FTA_FLAG',align:'left'" width="60">清单属性</th>
					<th data-options="field:'IS_PUBLIC',align:'left'" width="60" formatter="formatter">是否公开</th>
					<th data-options="field:'ITEM_NUM',align:'left'" width="60">子项数量</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="80">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
