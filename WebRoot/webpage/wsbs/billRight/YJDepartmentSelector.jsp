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
						if (curUserResKeys != "__ALL"&&noAuth!="true") {
							var loginUserInfoJson = $(
									"input[name='loginUserInfoJson']").val();
							var loginUser = JSON2.parse(loginUserInfoJson);
							//获取授权的区划代码数组
							var authDepCodeArray = loginUser.authDepCodes
									.split(",");
							var nodes = treeObj.transformToArray(treeObj
									.getNodes());
							for (var i = 0; i < nodes.length; i++) {
								var child = nodes[i];
								if (!AppUtil.isContain(authDepCodeArray,
										child.DEPART_CODE)) {
									child.nocheck = true;
									treeObj.updateNode(child);
								}
							}
						}
					}
				},
				async : {
					enable : true,
					url : "departmentController.do?parentTree",
					otherParam : {
						"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
						"idAndNameCol" : "DEPART_ID,DEPART_NAME,DEPART_CODE",
						"targetCols" : "PARENT_ID,PATH",
						"rootParentId" : "0",
						"Q_STATUS_!=" : 0,
						"needCheckIds" : needCheckIds,
						"isShowRoot" : "false",
						"rootName" : "部门树"
					}
				}
			};
			$.fn.zTree.init($("#DepartmentSelectTree"), setting);
		});

		//标记点击树还是列表 [0:树   1：列表]
		var isclickRow = 0;
		function selectDepartmentTree() {
			if (isclickRow == 1) {
				AppUtil.closeLayer();
			}
			var allowCount = $("input[name='allowCount']").val();
			var treeObj = $.fn.zTree.getZTreeObj("DepartmentSelectTree");
			var nodes = treeObj.getCheckedNodes(true);
			var checkIds = "";
			var checkNames = "";
			var checkRecords = [];
			if (nodes != null && nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					if (nodes[i].id != "0") {
						checkIds += nodes[i].id + ",";
						checkNames += nodes[i].name + ",";
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
					art.dialog.data("selectDepInfo", {
						departIds : checkIds,
						departNames : checkNames
					});// 存储数据
					AppUtil.closeLayer();
				}
			} else {
				alert("请至少选择一条记录!")
			}
		}
		function gridDoSearch() {
			var departName = $("input[name='Q_T.DEPART_NAME_LIKE']").val();
			if (departName != '') {
				$("#datagridDiv").css("display", "");
				$("#treeDiv").css("display", "none");
				AppUtil.gridDoSearch('DepartToolbar', 'DepartGrid');
			} else {
				$("#datagridDiv").css("display", "none");
				$("#treeDiv").css("display", "");
			}
		}

		function searchReset() {
			$("#datagridDiv").css("display", "none");
			$("#treeDiv").css("display", "");
			AppUtil.gridSearchReset('DepartForm');
		}

		function clickRow(rowIndex, rowData) {
			isclickRow = 1;
			art.dialog.data("selectDepInfo", {
				departIds : rowData.DEPART_ID,
				departNames : rowData.DEPART_NAME
			});
		}

		function doubleClick(rowIndex, rowData) {
			art.dialog.data("selectDepInfo", {
				departIds : rowData.DEPART_ID,
				departNames : rowData.DEPART_NAME
			});
			AppUtil.closeLayer();
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
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="DepartForm">
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
				<ul id="DepartmentSelectTree" class="ztree"></ul>
			</div>

		</div>
	    <div data-options="region:'south',split:true,border:false"  >
				<div class="eve_buttons">
					<input value="确定" type="button" onclick="selectDepartmentTree();"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
						value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</div>
		</div>
	</div>
</body>