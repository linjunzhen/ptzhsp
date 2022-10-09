<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
	<eve:resources
		loadres="jquery,easyui,apputil,artdialog,json2,layer,ztree"></eve:resources>
<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
	<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
	<script type="text/javascript">
		$(function() {
			var curUserResKeys = $("input[name='curUserResKeys']").val();
			var needCheckIds = $("input[name='needCheckIds']").val();			
			var checkCascadeY = $("input[name='checkCascadeY']").val();
			var checkCascadeN = $("input[name='checkCascadeN']").val();
			var allowCount = $("input[name='allowCount']").val();
			var noAuth = $("input[name='noAuth']").val();
			var ISTZINDUSTRY = $("input[name='ISTZINDUSTRY']").val();			
			var checkTypeObj = {
				"Y" : checkCascadeY,
				"N" : checkCascadeN
			};
			var check = {
				enable : true,
				chkboxType : checkTypeObj
			};
			if (allowCount == 1) {
				check.chkStyle = "radio";
				check.radioType = "all";
			}
			var setting = {
				check : check,
				edit : {
					enable : false,
					showRemoveBtn : false,
					showRenameBtn : false
				},
				view : {
					selectedMulti : false,
					fontCss :getFontCss
				},
				callback : {
					onAsyncSuccess : function(event, treeId, treeNode, msg) {
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						//treeObj.expandAll(true);
						var cname =  $.trim($("input[name='Q_INDU_NAME_LIKE']").val());
						changeColor('JyfwSelectTree','name',cname);
						var nodes = treeObj.getCheckedNodes(true);						
						for(var i in nodes) {
							zTreeOnCheck(event, treeId, nodes[i]);
						}
					},
					onCheck: zTreeOnCheck
				},
				async : {
					enable : true,
					url : "domesticControllerController/tree.do",
					otherParam : {
						"tableName" : "T_WSBS_INDUSTRY",
						"idAndNameCol" : "INDU_ID,INDU_NAME,INDU_CODE",
						"targetCols" : "PARENT_ID,PATH,FINDU_CODE",
						"rootParentId" : "4028819d51cc6f280151cde6a3f00027",
						"needCheckIds" : needCheckIds,
						"isShowRoot" : "true",
						"rootName" : "类别树"
					}
				}
			};
			$.fn.zTree.init($("#JyfwSelectTree"), setting);
			
			
			$("#SelectedINDUGrid").datagrid({
				onDblClickRow: function(index, row){
					$("#SelectedINDUGrid").datagrid("deleteRow",index);
				}
			});
		});
		function zTreeOnCheck(event, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var cNodes = treeNode.children;
			if(treeNode.checked){				
				var rows = $("#SelectedINDUGrid").datagrid('getData').rows;  var length = rows.length;  
				var rowindex=-1;  				
				for (var i = 0; i < length; i++) {  
					if (rows[i]['INDU_ID'] == treeNode.id) {  
						rowindex = i;  
						break;  
					}  
				}
				if(rowindex==-1){
					$('#SelectedINDUGrid').datagrid("insertRow", 
					{ row: {INDU_ID:treeNode.id, INDU_CODE:treeNode.INDU_CODE,INDU_NAME:treeNode.name, FINDU_CODE:treeNode.FINDU_CODE }});
					$('#SelectedINDUGrid').datagrid('enableDnd');
				}
				for(var i in cNodes) {
					treeObj.checkNode(cNodes[i], false,true, true);
					treeObj.setChkDisabled(cNodes[i], true,false,true);
				}
			} else {
				var rows = $("#SelectedINDUGrid").datagrid('getData').rows;  
                var length = rows.length;  
				var rowindex=-1;  				
				for (var i = 0; i < length; i++) {  
					if (rows[i]['INDU_ID'] == treeNode.id) {  
						rowindex = i;  
						break;  
					}  
				}
				if(rowindex>-1){
					$('#SelectedINDUGrid').datagrid('deleteRow',rowindex);					
				}
				for(var i in cNodes) {
					treeObj.checkNode(cNodes[i], false,true, true);
					treeObj.setChkDisabled(cNodes[i], false,false,true);
				}
			}			 
		};
		//标记点击树还是列表 [0:树   1：列表]
		var isclickRow = 0;
		function selectJyfwTree() {
			if (isclickRow == 1) {
				AppUtil.closeLayer();
			}
			var allowCount = $("input[name='allowCount']").val();
			var treeObj = $.fn.zTree.getZTreeObj("JyfwSelectTree");
			var nodes = treeObj.getCheckedNodes(true);
			var checkIds = "";
			var checkNames = "";
			var checkCodes = "";
			var firstCodes = "";
			var checkRecords = [];
			if (nodes != null && nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					if (nodes[i].id != "0") {
						checkIds += nodes[i].id + ",";
						checkNames += nodes[i].name + ",";
						checkCodes += nodes[i].INDU_CODE + ",";
						firstCodes += nodes[i].FINDU_CODE + ",";
						checkRecords.push(nodes[i].id);
					}
				}
				if (checkRecords.length >= 1) {
					if (allowCount != 0) {
						if (checkRecords.length > allowCount) {
							alert("最多只能选择" + allowCount + "条记录!");
							return;
						}
					}
					checkIds = checkIds.substring(0, checkIds.length - 1);
					checkNames = checkNames.substring(0, checkNames.length - 1);
					checkCodes = checkCodes.substring(0, checkCodes.length - 1);
					firstCodes = firstCodes.substring(0, firstCodes.length - 1);
					art.dialog.data("selectInduInfo", {
						induIds : checkIds,
						induNames : checkNames,
						induCodes : checkCodes,
						firstCodes : firstCodes
					});// 存储数据
					AppUtil.closeLayer();
				}
			} else {
				
				AppUtil.closeLayer();
			}
		}
		
		
		function getFontCss(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		}
		var nodeList = [];
		var treeId;
		//更改查询节点的颜色
		function changeColor(id,key,value){  
			treeId = id;  
			updateNodes(false);  
			if(value != ""){  
				var treeObj = $.fn.zTree.getZTreeObj(treeId);  
				nodeList = treeObj.getNodesByParamFuzzy(key, value);  
				if(nodeList && nodeList.length>0){  
					updateNodes(true);  
				}  
			}  
		}  
		//更改查询节点与其父节点的显示状态
		function updateNodes(highlight) {  
			var treeObj = $.fn.zTree.getZTreeObj(treeId);  
			for( var i=0; i<nodeList.length;  i++) {  
				nodeList[i].highlight = highlight;  
				if(nodeList[i].id!=0){
					nodeList[i].isshow = true;
					getParentInduNode(nodeList[i])
				}
				treeObj.updateNode(nodeList[i]);  				
			}  
			removeInduNode()
		}  
		//设置父节点为显示状态
		function getParentInduNode(node){
			var treeObj = $.fn.zTree.getZTreeObj(treeId);  
			if(node.id!=0){
				var pnode = node.getParentNode();
				if(pnode){
					pnode.isshow = true;
					getParentInduNode(pnode);
					treeObj.updateNode(pnode); 					
				}				
			}
		}
		//删除不显示的节点
		function removeInduNode(){
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodes = treeObj.getNodes();
			var nodes_array = treeObj.transformToArray(nodes);			
			if(nodes.length>0&&nodes[0].isshow){
				for(var i=0;i<nodes_array.length;i++){
					if(!nodes_array[i].isshow){
						treeObj.removeNode(nodes_array[i]);
					}
				}				
			}
		}
		//查询
		function gridDoSearch() {
			reloadTree();
		}
		//重置
		function searchReset() {
			$("input[name='Q_INDU_NAME_LIKE']").val('');
			reloadTree();
		}
		function reloadTree(){		
		
			var needCheckIds = "";
			var rows = $("#SelectedINDUGrid").datagrid("getRows"); //这段代码是获取当前页的所有行。
			for(var i=0;i<rows.length;i++){
				if(i>0){
					needCheckIds+=",";
				}
				needCheckIds += rows[i].INDU_ID;
			}		
			var ISTZINDUSTRY = $("input[name='ISTZINDUSTRY']").val();	
			var zTree = $.fn.zTree.getZTreeObj("JyfwSelectTree");
			zTree.setting.async.url = "domesticControllerController/tree.do";
			zTree.setting.async.otherParam = {
				"tableName" : "T_WSBS_INDUSTRY",
				"idAndNameCol" : "INDU_ID,INDU_NAME,INDU_CODE",
				"targetCols" : "PARENT_ID,PATH,FINDU_CODE",
				"rootParentId" : "4028819d51cc6f280151cde6a3f00027",
				"needCheckIds" : needCheckIds,
				"isShowRoot" : "true",
				"rootName" : "类别树"
			}
			zTree.reAsyncChildNodes(null, "refresh");
		}
		function clickRow(rowIndex, rowData) {
			isclickRow = 1;
			art.dialog.data("selectInduInfo", {
				induIds : rowData.INDU_ID,
				induNames : rowData.INDU_NAME,
				induCodes : rowData.INDU_CODE,
				firstCodes : rowData.FINDU_CODE
			});
		}

		function doubleClick(rowIndex, rowData) {
			art.dialog.data("selectInduInfo", {
				induIds : rowData.INDU_ID,
				induNames : rowData.INDU_NAME,
				induCodes : rowData.INDU_CODE,
				firstCodes : rowData.FINDU_CODE
			});
			AppUtil.closeLayer();
		}
		function doSelectRows(){
			var rows = $("#SelectedINDUGrid").datagrid("getRows");
			
			var allowCount = $("input[name='allowCount']").val();
			var checkIds = "";
			var checkNames = "";
			var checkCodes = "";
			var firstCodes = "";
			var checkRecords = [];
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					checkIds+=",";
					checkNames+=",";
					checkCodes+=",";
				}
				checkIds+=rows[i].INDU_ID;
				checkNames+=rows[i].INDU_NAME;
				checkCodes+=rows[i].INDU_CODE;
				if(firstCodes.indexOf(rows[i].FINDU_CODE)=='-1'){
					if(i>0)firstCodes+=",";
					firstCodes+=rows[i].FINDU_CODE;
				}
				checkRecords.push(rows[i].INDU_ID);
			}
			if (checkRecords.length >= 1) {
				if (allowCount != 0) {
					if (checkRecords.length > allowCount) {
						alert("最多只能选择" + allowCount + "条记录!");
						return;
					}
				}
				art.dialog.data("selectInduInfo", {
					induIds : checkIds,
					induNames : checkNames,
					induCodes : checkCodes,
					firstCodes : firstCodes
				});// 存储数据
				AppUtil.closeLayer();
			}else{				
				AppUtil.closeLayer();
			}
			
			
		}
	</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true"
		style="margin:0px;background-color: #f7f7f7;">
		<div region="center">
			<!-- =========================查询面板开始========================= -->
			<div id="DepartToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" value="${noAuth}" name="noAuth">
				<input type="hidden" value="${checkCascadeY}" name="checkCascadeY">
				<input type="hidden" value="${checkCascadeN}" name="checkCascadeN">
				<input type="hidden"
					value="${sessionScope.curLoginUser.loginUserInfoJson}"
					name="loginUserInfoJson"> <input type="hidden"
					value="${sessionScope.curLoginUser.resKeys}" name="curUserResKeys">
				<input type="hidden" value="${needCheckIds}" name="needCheckIds">
				<input type="hidden" value="${allowCount}" name="allowCount">
				<input type="hidden" value="${callbackFn}" name="callbackFn">
				<input type="hidden" value="${ISTZINDUSTRY}" name="ISTZINDUSTRY">
				<!--====================结束编写隐藏域============== -->	
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:center;">类别名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_INDU_NAME_LIKE" /></td>
							<td><input type="button" value="查询"
								class="eve-button" onclick="gridDoSearch()" /> <input
								type="button" value="重置" class="eve-button"
								onclick="searchReset()" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="treeDiv">
				<ul id="JyfwSelectTree" class="ztree"></ul>
			</div>

		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">		
						
			<div id="SelectedINDUToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con" style="height: 29px;">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选经营范围列表（鼠标左键可双击删除或单击拖动）
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedINDUGrid" 
			    fitColumns="true" toolbar="#SelectedINDUToolbar" nowrap="false"
				method="post" idField="INDU_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'INDU_ID',hidden:true" width="80">INDU_ID</th>
						<th data-options="field:'INDU_CODE',hidden:true" width="80">INDU_CODE</th>
						<th data-options="field:'INDU_NAME',align:'left'" width="200">经营范围名称</th>
					</tr>
				</thead>
			</table>
		</div>
	    <div data-options="region:'south',split:true,border:false"  >
				<div class="eve_buttons">
					<input value="确定" type="button" onclick="doSelectRows();"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
						value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</div>
		</div>
	</div>
</body>