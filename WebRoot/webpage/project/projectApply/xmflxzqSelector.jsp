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
			var checkCascadeY = $("input[name='checkCascadeY']").val();
			var checkCascadeN = $("input[name='checkCascadeN']").val();
			var allowCount = $("input[name='allowCount']").val();
			var noAuth = $("input[name='noAuth']").val();		
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
					selectedMulti : false
				},
				callback : {
					onAsyncSuccess : function(event, treeId, treeNode, msg) {
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						treeObj.expandAll(true);
						var nodes = treeObj.getNodes();						
						for(var i in nodes) {
							if(nodes[i].isParent){
								nodes[i].nocheck = true;
								treeObj.updateNode(nodes[i]); 
								zTreeNoCheck(event, treeId, nodes[i]);
							}
						}
					}
				},
				async : {
					enable : true,
					url : "baseController.do?tree",
					otherParam : {
						"tableName" : "T_FLOW_CONFIG_CATEGORY",
						"idAndNameCol" : "ID,NAME",
						"targetCols" : "PARENT_ID",
						"rootParentId" : "0",
						"isShowRoot" : "true",
						"rootName" : "类别树"
					}
				}
			};
			$.fn.zTree.init($("#XmflSelectTree"), setting);
		});
		function zTreeNoCheck(event, treeId,treeNode){
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var cNodes = treeNode.children;			
			for(var i in cNodes) {
				if(cNodes[i].isParent){
					cNodes[i].nocheck = true;
					treeObj.updateNode(cNodes[i]);
					zTreeNoCheck(event, treeId, cNodes[i]);
				}
			}
		}
		
		//标记点击树还是列表 [0:树   1：列表]
		var isclickRow = 0;
		function selectXmflTree() {
			if (isclickRow == 1) {
				AppUtil.closeLayer();
			}
			var allowCount = $("input[name='allowCount']").val();
			var treeObj = $.fn.zTree.getZTreeObj("XmflSelectTree");
			var nodes = treeObj.getCheckedNodes(true);
			var checkIds = "";
			var checkNames = "";
			var checkRecords = [];
			if (nodes != null && nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					if (!nodes[i].isParent) {
						checkIds += nodes[i].id + ",";
						checkNames += nodes[i].name + ",";
						checkRecords.push(nodes[i].id);
					} else {
						
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
					art.dialog.data("selectXmflInfo", {
						ids : checkIds,
						names : checkNames
					});// 存储数据
					AppUtil.closeLayer();
				}
			} else {
				alert("请选择" + allowCount + "条记录!");
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
			</div>
			<div id="treeDiv">
				<ul id="XmflSelectTree" class="ztree"></ul>
			</div>

		</div>
		
	    <div data-options="region:'south',split:true,border:false"  >
				<div class="eve_buttons">
					<input value="确定" type="button" onclick="selectXmflTree();"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
						value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</div>
		</div>
	</div>
</body>