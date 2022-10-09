<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine"></eve:resources>
<script type="text/javascript">
	
	
	/**
	 * 删除对接配置列表记录
	 */
	function deleteChildDepartGridRecord() {
		AppUtil.deleteDataGridRecord("callController.do?multiDelChildDepart",
				"ChildDepartGrid");
	};

	
	 function showSelectDeparts(){
    	var rows = $("#ChildDepartGrid").datagrid("getRows");
    	var departIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				departIds+=",";
			}
			departIds+=rows[i].DEPART_ID;
		}
    	parent.$.dialog.open("departmentController/childSelector.do?departIds="+departIds+"&allowCount=100&checkCascadeY=&"
   				+"checkCascadeN=&rootParentId=${departId}", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				saveSelectDept(selectDepInfo.departIds,selectDepInfo.departNames);
    				art.dialog.removeData("selectDepInfo");
    			}
    		}
    	}, false);
    }

	function saveSelectDept(departIds,departNames){
		AppUtil.ajaxProgress({
			url : "callController.do?saveChildDepart",
			params : {
				"departIds" : departIds,
				"departNames" : departNames,
				"parentId" : '${departId}'
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("ChildDepartToolbar", "ChildDepartGrid");
			}
		});
	}
	
	function changeChildDepartGridRecord(statu){
    	var rows = $("#ChildDepartGrid").datagrid("getChecked");
    	var entityIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].RECORD_ID;
		}
		AppUtil.ajaxProgress({
			url : "callController.do?isChildTake",
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
				AppUtil.gridDoSearch("ChildDepartToolbar", "ChildDepartGrid");
			}
		});
	}

	function rowformater(value,row,index){
		if(value=='1'){
			return '是';
		}else if(value=='0'){
			return '否';
		}
	}
	/* $(document).ready(function() {
		AppUtil.initAuthorityRes("ChildDepartToolbar");

	}); */
</script>
</head>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ChildDepartToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="icon-note-add" plain="true"
								onclick="showSelectDeparts();">选择</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteChildDepartGridRecord();">删除</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-ok" plain="true"
								onclick="changeChildDepartGridRecord('1');">可取号</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-no" plain="true"
								onclick="changeChildDepartGridRecord('0');">不可取号</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="ChildDepartGrid" nowrap="false"
			toolbar="#ChildDepartToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?childDepartDatagrid&parentId=${departId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'DEPART_ID',hidden:true" width="80">DEPART_ID</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="250">部门名称</th>
					<th data-options="field:'IS_TAKE',align:'left',formatter:rowformater" width="100" >是否可取号</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
