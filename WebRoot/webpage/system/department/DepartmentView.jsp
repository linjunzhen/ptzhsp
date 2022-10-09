
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

    function showSelectSysUser(){
    	var departId = $("#TreeSysUserToolbar input[name='DEPART_ID']").val();
    	if(departId&&departId!="0"){
    		AppUtil.ajaxProgress({
	    		url:"sysUserController.do?userIdAndName",
	    	    params:{
	    	    	"depId":departId
	    	    },
	    	    callback:function(resultJson){
	    	    	var userIds = "";
	    	    	var userNames = "";
	    	    	if(resultJson.USER_IDS){
	    	    		userIds = resultJson.USER_IDS;
	    	    		userNames = resultJson.USER_NAMES;
	    	    	}
	    	    	$.dialog.open(encodeURI("sysUserController.do?selector&allowCount=0&userIds="+userIds+"&userNames="+userNames), {
	            		title : "人员选择器",
	                    width:"1000px",
	                    lock: true,
	                    resize:false,
	                    height:"500px",
	                    close: function () {
	            			var selectUserInfo = art.dialog.data("selectUserInfo");
	            			if(selectUserInfo){
	            				var departId = $("input[name='DEPART_ID']").val();
	            		    	AppUtil.ajaxProgress({
	            		    		url:"departmentController.do?addUsers",
	            		    	    params:{
	            		    	    	"departId":departId,
	            		    	    	"userIds":selectUserInfo.userIds
	            		    	    },
	            		    	    callback:function(resultJson){
	            		    	    	art.dialog({
	            							content: resultJson.msg,
	            							icon:"succeed",
	            							time:3,
	            						    ok: true
	            						});
	            		    	    	AppUtil.gridDoSearch("TreeSysUserToolbar","TreeSysUserGrid");
	            		    	    }
	            		    	});
	            		    	art.dialog.removeData("selectUserInfo");
	            			}
	            		}
	            	}, false);
	    	    }
	    	});
    		
    	}else{
    		art.dialog({
				content: "请先选择组织机构!",
				icon:"warning",
			    ok: true
			});
    	}
    	 
    }
    
    function removeSelectSysUser(){
		var rowsData = $("#TreeSysUserGrid").datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != "undefined" && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被移除的用户!",
				icon:"warning",
			    ok: true
			});
		}else{
			var departId = $("input[name='DEPART_ID']").val();
			var userIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					userIds+=",";
				}
				userIds+=rowsData[i].USER_ID;
			}
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
	                        url:"departmentController.do?removeUsers",
	                        params:{
	                            "userIds":userIds,
	                            "departId":selectDepInfo.departIds
	                        },
	                        callback:function(resultJson){
	                            art.dialog({
	                                content: resultJson.msg,
	                                icon:"succeed",
	                                time:3,
	                                ok: true
	                            });
	                            AppUtil.gridDoSearch("TreeSysUserToolbar","TreeSysUserGrid");
	                        }
	                    });
	                }
	                
	            }
	        }, false);
		}
    	
    	
    }
	/**
	 * 添加树形hover工具按钮
	 */
	function addDepartmentTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子机构' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showDepartmentWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showDepartmentWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "departmentTree",
				url : "departmentController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeDepartmentTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onDepartmentTreeDrop(event, treeId, treeNodes, targetNode,
			moveType, isCopy) {
		if(moveType!=null){
			AppUtil.ajaxProgress({
				url : "baseController.do?updateTreeSn",
				params : {
					"dragTreeNodeId" : treeNodes[0].id,
					"dragTreeNodeNewLevel" : treeNodes[0].level,
					"moveType":moveType,
					"targetNodeId" : targetNode.id,
					"targetNodeLevel" : targetNode.level,
					"tableName" : "T_MSJW_SYSTEM_DEPARTMENT"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						/* $.fn.zTree.getZTreeObj("departmentTree").reAsyncChildNodes(
								null, "refresh"); */
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						/* $.fn.zTree.getZTreeObj("departmentTree").reAsyncChildNodes(
								null, "refresh"); */
					}
				}
			});
		}
		
	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#TreeSysUserToolbar input[name='DEPART_ID']").val(treeNode.id);
			$("#TreeSysUserToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			AppUtil.gridDoSearch("TreeSysUserToolbar", "TreeSysUserGrid");
		}
	}

	/**
	 * 弹出组织机构信息窗口
	 */
	function showDepartmentWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = encodeURI("departmentController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name+"&time="+new Date());
		} else {
			var parentNode = treeNode.getParentNode();
			url = encodeURI("departmentController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id+"&time="+new Date());
		}
		$.dialog.open(url, {
    		title : "组织机构信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"280px",
    	}, false);
	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		AppUtil.gridSearchReset("TreeSysUserForm");
	}

	/**
	 * 删除系统用户列表记录
	 */
	function deleteSysUserDataGridRecord() {
		AppUtil.deleteDataGridRecord("sysUserController.do?multiDel",
				"sysUserGrid");
	};
	/**
	 * 编辑系统用户列表记录
	 */
	function editSysUserGridRecord() {
		var USER_ID = AppUtil.getEditDataGridRecord("sysUserGrid");
		if (USER_ID) {
			showSysUserWindow(USER_ID);
		}
	}
	
	function formatStatus(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>激活</b></font>";
		}else{
			return "<font color='#ff4b4b'><b>禁用</b></font>";
		}
	}

	$(document).ready(function() {
		var curUserResKeys = $("input[name='curUserResKeys']").val();
		var departmentTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addDepartmentTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeDepartmentTreeHoverDom
			},
			callback : {
				onClick : onDepartmentTreeClick,
				onDrop : onDepartmentTreeDrop,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
					if (curUserResKeys != "__ALL") {
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
								if(child.DEPART_CODE){
									treeObj.hideNode(child);
								}
							}
						}
					}
				}
			},
			async : {
				enable : true,
				url : "departmentController.do?tree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"Q_STATUS_!=":0,
					"isShowRoot" : "true",
					"rootName" : "组织机构树"
				}
			}
		};
		$.fn.zTree.init($("#departmentTree"), departmentTreeSetting);
		//AppUtil.initAuthorityRes("TreeSysUserToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
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
		<ul id="departmentTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="TreeSysUserToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_D.PATH_LIKE" />
			<input type="hidden" name="DEPART_ID"  />
			<input type="hidden" value="${sessionScope.curLoginUser.loginUserInfoJson}" name="loginUserInfoJson"> 
			<input type="hidden" value="${sessionScope.curLoginUser.resKeys}" name="curUserResKeys">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_SysUser"
								iconCls="icon-adduser" plain="true"
								onclick="showSelectSysUser();">加入用户</a> <a href="#"
								class="easyui-linkbutton" resKey="EDIT_SysUser"
								iconCls="icon-removeuser" plain="true"
								onclick="removeSelectSysUser();">转移用户</a> 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="TreeSysUserForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">帐号</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.USERNAME_LIKE" /></td>
						<td style="width:68px;text-align:right;">用户姓名</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.FULLNAME_LIKE" /></td>
						<td colspan="4"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('TreeSysUserToolbar','TreeSysUserGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('TreeSysUserForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="TreeSysUserGrid" fitColumns="true" toolbar="#TreeSysUserToolbar"
			method="post" idField="USER_ID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true" fit="true" border="false"
			url="sysUserController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'USER_ID',hidden:true">USER_ID</th>
					<th data-options="field:'USERNAME',align:'left'" width="15%">帐号</th>
					<th data-options="field:'FULLNAME',align:'left'" width="15%">用户姓名</th>
					<th data-options="field:'MOBILE',align:'left'" width="12%">手机号码</th>
					<th data-options="field:'STATUS',align:'left'" width="8%" formatter="formatStatus">状态</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="20%">机构名称</th>
					<th data-options="field:'ROLE_NAMES',align:'left'" width="24%">授权角色</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



