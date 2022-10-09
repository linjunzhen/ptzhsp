<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	function setConfigUsers(index) {
		var row = $('#flowUserGrid').datagrid('getData').rows[index];
		if (row) {
			var userIds = row.USER_ID;
	        $.dialog.open("sysUserController.do?selector&allowCount=0&userIds="
					+ userIds, {
				title : "人员选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close: function () {
	    			var selectUserInfo = art.dialog.data("selectUserInfo");
	    			if(selectUserInfo&&selectUserInfo.userIds){
	    				saveConfigAuditUsers(selectUserInfo.userIds,index);
	    				art.dialog.removeData("selectUserInfo");
	    			}
	    		}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可设置记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}  
	function saveConfigAuditUsers(userIds, index) {
		var row = $('#flowUserGrid').datagrid('getData').rows[index];
    	if(row){
    		AppUtil.ajaxProgress({
    			url : "serviceItemController.do?bindUsers",
    			params : {
    				"itemId" : row.ITEM_ID,
    				"userIds" : userIds
    			},
    			callback : function(resultJson) {
    				
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
					AppUtil.gridDoSearch("flowUserToolbar", "flowUserGrid");
    			}
    		});
    	}else{
			parent.art.dialog({
				content : "当前无可设置记录",
				icon : "warning",
				time : 3,
				ok : true
			});
    	}
	}
	function saveConfigUsers(userIds, userNames, userAccounts, index) {
		var row = $('#flowUserGrid').datagrid('getData').rows[index];
		if (row) {
			AppUtil.ajaxProgress({
				url : "flowConfigUserController.do?saveOrUpdate",
				params : {
					"STAGE_ID" : row.STAGE_ID,
					"ITEM_ID" : row.ITEM_ID,
					"DEF_ID" : row.BDLCDYID,
					"USER_ID" : userIds,
					"USER_NAME" : userNames,
					"USER_ACCOUNT" : userAccounts,
					"NODE_NAME":"预审"
				},
				callback : function(resultJson) {
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
					AppUtil.gridDoSearch("flowUserToolbar", "flowUserGrid");
				}
			});
		}else{
			parent.art.dialog({
				content : "当前无可设置记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	
	function formatItemUser(val,row,index){
		var html = '<a href="#" onclick="setConfigUsers('+index+')"><font style="color:blue;">【设置】</font></a>';			
		return html;
	}
	
    
    /**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN" && !treeNode.isParent ) {
			$("#flowUserToolbar input[name='Q_T.STAGE_ID_EQ']").val(treeNode.id);
			$("#titleFont").html(treeNode.pname+"-"+treeNode.name);
			AppUtil.gridDoSearch("flowUserToolbar","flowUserGrid");
		}
	}

	$(document).ready(function() {
		var flowConfigTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onDepartmentTreeClick,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
				}
			},
			async : {
				enable : true,
				url : "flowConfigUserController.do?tree",
				otherParam : {
					
				}
			}
		};
		$.fn.zTree.init($("#flowConfigTree"), flowConfigTreeSetting);

	});
	function showExcelExportUserInfo(){
        AppUtil.showExcelExportWin({
            dataGridId:"flowUserGrid",
            tplKey:"UserInfoReportTpl",
            excelName:"用户数据",
            queryParams:{
                "T.USERNAME":$("input[name='Q_T.USERNAME_LIKE']").val(),
                "T.FULLNAME":$("input[name='Q_T.FULLNAME_LIKE']").val(),
                "T.STATUS":$("input[name='Q_T.STATUS_=']").val()
            }
        });
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">

    <div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							&nbsp;类型树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="flowConfigTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<!-- =========================查询面板开始========================= -->
		<div id="flowUserToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
								&nbsp;<font id="titleFont">全部事项</font>
							</div>
						</div>
					</div>
				</div>
			</div>
		    <!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_T.STAGE_ID_EQ" />
			<input type="hidden" name="STAGE_ID"  />
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="flowUserForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_F.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_F.ITEM_CODE_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('flowUserToolbar','flowUserGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('flowUserForm');" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="flowUserGrid" fitcolumns="true" toolbar="#flowUserToolbar"
			method="post" idfield="ID" checkonselect="true" nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="flowTemplateController.do?itemDatagrid">
			<thead>
				<tr>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="40%">事项名称</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="15%">事项编码</th>
					<th data-options="field:'USER_NAME',align:'left'" width="28%">预审分发审批人员</th>
					<th data-options="field:'USER_ID',align:'left'" width="15%" formatter="formatItemUser">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>