<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deletecommentGridRecord() {
		AppUtil.deleteDataGridRecord("commentController.do?multiDel",
				"commentGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editcommentGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("commentGrid");
		if (entityId) {
			showcommentWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showcommentWindow(entityId) {
		$.dialog.open("commentController.do?info&entityId=" + entityId, {
			title : "查看评议信息",
			width : "700px",
			height : "550px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("commentToolbar");
	});
function reloadcommentList(){
	$("#commentGrid").datagrid("reload");
}
/**
 * 格式化
 */
function formatState(val,row){
	if(val=="0"){
		return "<font color='red'>否</font>";
	}else{
		return "<font color='green'>是</font>";
	}
};
/**
*满意度格式化
*/
function formatSatisfaction(val,row){
	if(val=="0"){
		return "<font color='#ff4b4b'>不满意</font>";
	}else if(val=="1"){
		return "<font color='#0368ff'>基本满意</font>";
	}else if(val=="2"){
		return "<font color='green'>满意</font>";
	}else{
		return "<font color='#8c97cb'>不了解</font>";
	}
};
function showSelectDeparts(){
	var departId = $("input[name='COMMENT_DEPTID']").val();
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
				//$("input[name='COMMENT_DEPTID']").val(selectDepInfo.departIds);
				$("input[name='Q_U.COMMENT_DEPT_LIKE']").val(selectDepInfo.departNames);
				art.dialog.removeData("selectDepInfo");
			}
		}
	}, false);
}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="commentToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-eye" plain="true"
								onclick="editcommentGridRecord();">查看</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_comment"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletecommentGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="commentForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">评议单位：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.COMMENT_DEPT_LIKE" onclick="showSelectDeparts();"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('commentToolbar','commentGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('commentForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="commentGrid" fitcolumns="true" toolbar="#commentToolbar"
			method="post" idfield="COMMENT_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="true"
			url="commentController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'COMMENT_ID',hidden:true">COMMENT_ID</th>	
					<th data-options="field:'COMMENT_DEPT',align:'left'" width="15%">评议单位</th>
					<th data-options="field:'COMMENT_NAME',align:'left'" width="7%">评议人</th>						
					<th data-options="field:'COMMENT_FZZC',align:'left'" width="32%" formatter="formatSatisfaction">贯彻落实中央和市委市政府,重大决策部署和方针政策</th>					
					<th data-options="field:'COMMENT_BSXL',align:'left'" width="7%" formatter="formatSatisfaction">办事效率</th>
					<th data-options="field:'COMMENT_LJQZ',align:'left'" width="7%" formatter="formatSatisfaction">廉洁勤政</th>
					<th data-options="field:'COMMENT_YFXZ',align:'left'" width="7%" formatter="formatSatisfaction">依法行政</th>
					<th data-options="field:'COMMENT_FWZL',align:'left'" width="7%" formatter="formatSatisfaction">服务质量</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="14%">评议时间</th>
					<!--<th data-options="field:'REPLY_STATUS',align:'left'" width="80" formatter="formatState">回复状态</th>
					<th data-options="field:'REPLY_TIME',align:'left'" width="110">回复时间</th>   -->
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
