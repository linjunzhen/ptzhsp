<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除信息列表记录
	 */
	function deletegtptxxglGridRecord() {
		AppUtil.deleteDataGridRecord("gtptxxglController.do?multiDel",
				"gtptxxglGrid");
	};
	/**
	 * 编辑信息列表记录
	 */
	function editgtptxxglGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("gtptxxglGrid");
		if (entityId) {
			showgtptxxglWindow(entityId);
		}
	}

	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#gtptxxglToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	/**
	 * 显示信息信息对话框
	 */
	function showgtptxxglWindow(entityId) {
		$.dialog.open("gtptxxglController.do?info&entityId=" + entityId, {
			title : "平台信息",
			width : "1200px",
			height : "610px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("gtptxxglToolbar");
	});
	function reloadgtptxxglList(){
		$("#gtptxxglGrid").datagrid("reload");
	}

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="gtptxxglToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_GTPTXXGL"
								iconcls="eicon-plus" plain="true"
								onclick="showgtptxxglWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_GTPTXXGL"
								iconcls="eicon-pencil" plain="true"
								onclick="editgtptxxglGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_GTPTXXGL"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletegtptxxglGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="gtptxxglForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:100px;text-align:right;">平台名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PT_NAME_LIKE"/>
	    </td> 
        <td style="width:100px;text-align:right;">联络员名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.LIAISON_NAME_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('gtptxxglToolbar','gtptxxglGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('gtptxxglForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="gtptxxglGrid" fitcolumns="true" toolbar="#gtptxxglToolbar"
			method="post" idfield="PT_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="gtptxxglController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'PT_ID',hidden:true">PT_ID</th>	
					<th data-options="field:'PT_NAME',align:'left'" width="30%">平台名称</th>		
					<th data-options="field:'PT_HY',align:'left'" width="20%">行业</th>	
					<th data-options="field:'LIAISON_NAME',align:'left'" width="15%">联络员姓名</th>
					<th data-options="field:'LIAISON_MOBILE',align:'left'" width="15%">手机号码</th>				
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
