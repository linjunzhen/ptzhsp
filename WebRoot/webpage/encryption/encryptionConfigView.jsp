<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addEncryptionConfigTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_"+treeNode.id).length>0) return;
		if ($("#editItemHref_"+treeNode.id).length>0) return;
		if ($("#delItemHref_"+treeNode.id).length>0) return;
		var operateStr = "";
		if(treeNode.id=="0"){
			operateStr += "<a id='addItemHref_" +treeNode.id+ "' title='创建' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		}
		if(treeNode.id!="0"){
			operateStr+="<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr+="<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showEncryptionInfoConfigWindow(treeNode,true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showEncryptionInfoConfigWindow(treeNode,false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId:"EncryptionConfigTree",
				url:"encryptionController.do?multiDel",
				noAllowDeleteIfHasChild:true,
				treeNode:treeNode
			});
			$("#EncryptionConfigGrid").datagrid("reload");
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeEncryptionConfigTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_"+treeNode.id).unbind().remove();
		$("#editItemHref_"+treeNode.id).unbind().remove();
		$("#delItemHref_"+treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onEncryptionConfigTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
		if(moveType!=null){
			AppUtil.ajaxProgress({
			   url:"baseController.do?updateTreeSn",
			   params:{
				   "dragTreeNodeId":treeNodes[0].id,
				   "dragTreeNodeNewLevel":treeNodes[0].level,
				   "targetNodeId":targetNode.id,
				   "moveType":moveType,
				   "targetNodeLevel":targetNode.level,
				   "tableName":"ENCRYPTION_CONFIG_TABLE"
			   },
			   callback:function(resultJson){
					if(resultJson.success){
						/* $.fn.zTree.getZTreeObj("EncryptionConfigTree").reAsyncChildNodes(null, "refresh"); */
		    	   	    alert("成功完成拖拽排序!");
					}else{
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("EncryptionConfigTree").reAsyncChildNodes(null, "refresh");
					}
			   	    
			   }
			});
		}
		
	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onEncryptionConfigTreeClick(event, treeId, treeNode, clickFlag) {
		if(event.target.tagName=="SPAN"){
			if(treeNode.id=="0"){
				$("#EncryptionConfigToolbar input[name='CONFIG_ID']").val("");
				$("#EncryptionConfigToolbar input[name='Q_T.CONFIG_ID_=']").val("");
			}else{
				$("#EncryptionConfigToolbar input[name='CONFIG_ID']").val(treeNode.id);
				$("#EncryptionConfigToolbar input[name='Q_T.CONFIG_ID_=']").val(treeNode.id);
				$("#tableNamediv").html("_"+treeNode.name);
				$("input[name='configName']").val(treeNode.name);
			}
			
			AppUtil.gridDoSearch("EncryptionConfigToolbar","EncryptionConfigGrid");
		}
	}
	
	/**
	 * 弹出字典类别窗口
	 */
	function showEncryptionInfoConfigWindow(treeNode,isAdd) {
		var url = "";
		if(isAdd){
			url = "encryptionController.do?info&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
		}else{
			var parentNode = treeNode.getParentNode();
			url = "encryptionController.do?info&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
		}
		$.dialog.open(url, {
			title : "加密表信息",
	        width:"700px",
	        lock: true,
	        resize:false,
	        height:"280px",
		}, false);
	};
	/**
	 * 重置查询条件
	 */
	function resetSearch(){
		AppUtil.gridSearchReset("EncryptionConfigForm");
	}
	
	/**
	 * 删除数据字典列表记录
	 */
	function deleteDictionaryDataGridRecord() {
		AppUtil.deleteDataGridRecord(
				"encryptionController.do?multiDel", "EncryptionConfigGrid");
	};
	
	/**
	 * 编辑数据字典列表记录
	 */
	function editEncryptionConfigGridRecord(){
		var DIC_ID = AppUtil.getEditDataGridRecord("EncryptionConfigGrid");
		if(DIC_ID){
			showEncryptionConfigWindow(DIC_ID);
		}
	}
	
	/**
	 * 显示字典信息对话框
	 */
	function showEncryptionConfigWindow(entityId) {
		if(entityId){
			$.dialog.open("encryptionController.do?columnInfo&entityId="+entityId, {
				title : "字段配置信息",
		        width:"650px",
		        lock: true,
		        resize:false,
		        height:"250px",
			}, false);
		}else{
			var typeId = $("#EncryptionConfigToolbar input[name='CONFIG_ID']").val();
			if(typeId&&typeId!="0"){
				var treeObj = $.fn.zTree.getZTreeObj("EncryptionConfigTree");
				var treeNode = treeObj.getNodesByParam("id",typeId, null)[0];
				$.dialog.open("encryptionController.do?columnInfo&CONFIG_ID="+treeNode.id+"&CONFIG_NAME="+treeNode.name, {
					title : "字段配置信息",
			        width:"650px",
			        lock: true,
			        resize:false,
			        height:"250px",
				}, false);
			}else{
				art.dialog({
					content: "请先选择配置表!",
					icon:"warning",
				    ok: true
				});
			}
		}
		
	};
	
	function deleteEncryptionConfigGridRecord(){
		var rowsData = $("#EncryptionConfigGrid").datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			art.dialog.confirm("您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $("#EncryptionConfigGrid").datagrid('options').idField;  
				var selectColNames = "";
				var flag = true;
				for ( var i = 0; i < rowsData.length; i++) {
					if(rowsData[i].IS_ENCRYPTED=='1'){
						flag = false;
					}
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				if(flag){
					$.post("encryptionController.do?multiDelColumn",{
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
								$("#EncryptionConfigGrid").datagrid('reload');
								$("#EncryptionConfigGrid").datagrid('clearSelections').datagrid('clearChecked');
							}else{
								$("#EncryptionConfigGrid").datagrid('reload');
								art.dialog({
									content: resultJson.msg,
									icon:"error",
								    ok: true
								});
							}
					});
				}else{
					layer.close(layload);
					art.dialog({
						content: "选择的字段已完成过数据加密，不可删除！",
						icon:"error",
					    ok: true
					});
				}
			});
		}
	}
	
	function tableEncryption(){
		var configId = $("#EncryptionConfigToolbar input[name='CONFIG_ID']").val();
		var configName = $("input[name='configName']").val();
		
		if(configId&&configId!="0"){
			$.dialog.open("encryptionController.do?toTableEncryption&configId="+configId, {
					title : "表历史数据加密",
			        width:"1200px",
			        lock: true,
			        resize:false,
			        height:"600px",
				}, false);
		}else{
			art.dialog({
				content: "请先选择要进行加密的配置表!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
	$(document).ready(function(){
		var EncryptionConfigTreeSetting = {
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			view : {
				addHoverDom: addEncryptionConfigTreeHoverDom,
				selectedMulti: false,
				removeHoverDom : removeEncryptionConfigTreeHoverDom
			},
			callback : {
				onClick : onEncryptionConfigTreeClick,
				onDrop: onEncryptionConfigTreeDrop
			},
			async : {
				enable : true,
				url : "encryptionController/tree.do",
				otherParam : {
					"tableName" : "ENCRYPTION_CONFIG_TABLE",
					"idAndNameCol" : "CONFIG_ID,CONFIG_NAME",
					"targetCols" : "TREE_PATH,BUSTABLE_NAME,PARENT_ID",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName":"加密表"
				}
			}
		};
		$.fn.zTree.init($("#EncryptionConfigTree"), EncryptionConfigTreeSetting);
		//AppUtil.initAuthorityRes("EncryptionConfigToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:350px;" >
		<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						  <div style="color:#005588;"><img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:7px; float:left;" />&nbsp;加密表配置树</div>
						</div>
					</div>
				</div>
		</div>
		<ul id="EncryptionConfigTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="EncryptionConfigToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;加密表<a
									id="tableNamediv"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		    <%--====================开始编写隐藏域============== --%>
		     <input type="hidden" name="Q_T.CONFIG_ID_=" value="" />
		     <input type="hidden" name="CONFIG_ID"  />
		     <input type="hidden" name="configName"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_EncryptionConfig" iconCls="eicon-plus"
								plain="true" onclick="showEncryptionConfigWindow();">新建</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_EncryptionConfig" iconCls="eicon-pencil"
								plain="true" onclick="editEncryptionConfigGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_EncryptionConfig" iconCls="eicon-trash-o" plain="true"
								onclick="deleteEncryptionConfigGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" resKey="Run_Encryption" iconCls="eicon-compress" plain="true"
								onclick="tableEncryption();">表加密</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="EncryptionConfigForm">
			<table class="dddl-contentBorder dddl_table"
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				<tr>
					<td style="width:68px;text-align:right;">字段名称</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.COLUMN_NAME_=" style="width:128px;"></td>
					<td style="width:68px;text-align:right;">字段注释</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.COLUMN_COMMENT_LIKE" style="width:128px;"></td>
					<td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('EncryptionConfigToolbar','EncryptionConfigGrid')">
						<input type="button" value="重置" class="eve-button" onclick="resetSearch();"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="EncryptionConfigGrid" fitColumns="true" toolbar="#EncryptionConfigToolbar"
			method="post" idField="COLUMN_ID" checkOnSelect="true"
			selectOnCheck="true" fit="true" border="false"
			url="encryptionController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'COLUMN_ID',hidden:true">COLUMN_ID</th>
					<th data-options="field:'COLUMN_NAME',align:'left'" width="30%">字段名</th>
					<th data-options="field:'COLUMN_COMMENT',align:'left'" width="30%">字段注释</th>
					<th data-options="field:'CONFIG_NAME',align:'left'" width="34%" >所属表</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




