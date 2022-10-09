<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除信息列表记录
	 */
	function deletejurisdictionGridRecord() {
		AppUtil.deleteDataGridRecord("jurisdictionController.do?multiDel",
				"jurisdictionGrid");
	};
	/**
	 * 编辑信息列表记录
	 */
	function editjurisdictionGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("jurisdictionGrid");
		if (entityId) {
			showjurisdictionWindow(entityId);
		}
	}

	/**
	 * 显示信息信息对话框
	 */
	function showjurisdictionWindow(entityId) {
		$.dialog.open("jurisdictionController.do?info&entityId=" + entityId, {
			title : "编辑信息",
			width : "800px",
			height : "310px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("jurisdictionToolbar");
	});
	function reloadjurisdictionList(){
		$("#jurisdictionGrid").datagrid("reload");
	}

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="jurisdictionToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_JURISDICTION"
								iconcls="eicon-plus" plain="true"
								onclick="showjurisdictionWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_JURISDICTION"
								iconcls="eicon-pencil" plain="true"
								onclick="editjurisdictionGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_JURISDICTION"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletejurisdictionGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="jurisdictionForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:100px;text-align:right;">辖区分局名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.XQ_NAME_LIKE"/>
	    </td> 
        <td style="width:100px;text-align:right;">分局地址关键字：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.XQ_KEYWORD_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('jurisdictionToolbar','jurisdictionGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('jurisdictionForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="jurisdictionGrid" fitcolumns="true" toolbar="#jurisdictionToolbar"
			method="post" idfield="XQ_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="jurisdictionController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'XQ_ID',hidden:true">XQ_ID</th>	
					<th data-options="field:'XQ_NAME',align:'left'" width="40%">辖区分局名称</th>		
					<th data-options="field:'XQ_KEYWORD',align:'left'" width="40%">分局地址关键字</th>					
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
