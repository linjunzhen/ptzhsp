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
			var parentId = $("input[name='parentId']").val();
			
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
					enable : true,
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
					},
					onClick:function (e, treeId, treeNode, clickFlag) { 
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						treeObj.checkNode(treeNode, !treeNode.checked, true); 
					}
				},
				async : {
					enable : true,
					url : "baseController.do?tree",
					otherParam : {
						"tableName" : "T_FJFDA_BUSTYPE",
						"idAndNameCol" : "TYPE_ID,TYPE_NAME,TYPE_CODE",
						"targetCols" : "PARENT_ID,PATH",
						"rootParentId" : parentId,
						"needCheckIds" : needCheckIds,
						"isShowRoot" : "false",
						"rootName" : "明细树"
					}
				}
			};
			$.fn.zTree.init($("#BusTypeSelectTree"), setting);
		});

		//标记点击树还是列表 [0:树   1：列表]
		var isclickRow = 0;
		function selectBusTypeTree() {
			if (isclickRow == 1) {
				AppUtil.closeLayer();
			}
			var allowCount = $("input[name='allowCount']").val();
			var treeObj = $.fn.zTree.getZTreeObj("BusTypeSelectTree");
			var nodes = treeObj.getCheckedNodes(true);
			var checkIds = "";
			var checkNames = "";
			var checkRecords = [];
			var namesArray = [];
			var idsArray = [];
			var num = 1;
			var isparent = false;
			if (nodes != null && nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					if(nodes[i].isParent){	
						if(checkNames!=null&&checkNames!=""&&isparent){
							checkNames =checkNames.substring(0, checkNames.length - 1)+"],";//\r\n
						}					
						if (nodes[i].id != "0") {
							checkIds += nodes[i].id + ",";
							checkNames += nodes[i].name + "[ ";//num+","+
							checkRecords.push(nodes[i].id);
						}
						isparent = nodes[i].isParent;
						num++;
					}else{
						if (nodes[i].id != "0") {
							checkIds += nodes[i].id + ",";
							checkNames += nodes[i].name + ",";
							checkRecords.push(nodes[i].id);
							namesArray.push(nodes[i].name);
							idsArray.push(nodes[i].id)
						}
						if(i==(nodes.length-1)){
							if(checkNames!=null&&checkNames!=""){
								if(isparent){
									checkNames =checkNames.substring(0, checkNames.length - 1)+"]";									
								}else{									
									checkNames =checkNames.substring(0, checkNames.length - 1);
								}
							}	
						}
					}
				}
				if (checkRecords.length >= 1) {
					if (allowCount != 0) {
						if (checkRecords.length > allowCount) {
							art.dialog({
					            content: "最多只能选择" + allowCount + "条记录!",
					            icon:"warning",
					            ok: true
					        });
							return;
						}
					}
					checkIds = checkIds.substring(0, checkIds.length - 1);
					//checkNames = checkNames.substring(0, checkNames.length - 1);
					art.dialog.data("selectBusTypeInfo", {
						typeIds : checkIds,
						typeNames : checkNames,
						namesArray: namesArray,
						idsArray:idsArray
					});// 存储数据
					AppUtil.closeLayer();
				}
			} else {
				art.dialog({
		            content: "请至少选择一条记录!",
		            icon:"warning",
		            ok: true
		        });
			}
		}
		function gridDoSearch() {
			var departName = $("input[name='Q_T.DEPART_NAME_LIKE']").val();
			if (departName != '') {
				$("#datagridDiv").css("display", "");
				$("#treeDiv").css("display", "none");
				AppUtil.gridDoSearch('BusTypeToolbar', 'BusTypeGrid');
			} else {
				$("#datagridDiv").css("display", "none");
				$("#treeDiv").css("display", "");
			}
		}

		function searchReset() {
			$("#datagridDiv").css("display", "none");
			$("#treeDiv").css("display", "");
			AppUtil.gridSearchReset('BusTypeForm');
		}

		function clickRow(rowIndex, rowData) {
			isclickRow = 1;
			art.dialog.data("selectBusTypeInfo", {
				typeIds : rowData.TYPE_ID,
				typeNames : rowData.TYPE_NAME
			});
		}

		function doubleClick(rowIndex, rowData) {
			art.dialog.data("selectBusTypeInfo", {
				typeIds : rowData.TYPE_ID,
				typeNames : rowData.TYPE_NAME
			});
			AppUtil.closeLayer();
		}
	</script>
</head>

<body style="margin:0px;background-color: #fff;">
	<div class="easyui-layout" fit="true"
		style="margin:0px;background-color: #fff;">
		<div region="center">
			<!-- =========================查询面板开始========================= -->
			<div id="BusTypeToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" value="${noAuth}" name="noAuth">
				<input type="hidden" value="${checkCascadeY}" name="checkCascadeY">
				<input type="hidden" value="${checkCascadeN}" name="checkCascadeN">
				<%-- 
				<input type="hidden"
					value="${session111Scope.curLoginUser.loginUserInfoJson}"
					name="loginUserInfoJson"> <input type="hidden"
					value="${session111Scope.curLoginUser.resKeys}" name="curUserResKeys">
					--%>
				<input type="hidden" value="${needCheckIds}" name="needCheckIds">
				<input type="hidden" value="${allowCount}" name="allowCount">
				<input type="hidden" value="${callbackFn}" name="callbackFn">
				<input type="hidden" value="${parentId}" name="parentId">
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="BusTypeForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;display: none;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:68px;text-align:center;">机构名称</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.DEPART_NAME_LIKE" /></td>
								<td colspan="6"><input type="button" value="查询"
									class="eve-button" onclick="gridDoSearch()" /> <input
									type="button" value="重置" class="eve-button"
									onclick="searchReset()" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div id="treeDiv">
				<ul id="BusTypeSelectTree" class="ztree"></ul>
			</div>

		</div>
	    <div data-options="region:'south',split:true,border:false"  >
				<div class="eve_buttons">
					<input value="确定" type="button" onclick="selectBusTypeTree();"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
						value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</div>
		</div>
	</div>
</body>