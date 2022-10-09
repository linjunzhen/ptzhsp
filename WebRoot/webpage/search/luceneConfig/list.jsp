<%@page import="net.evecom.core.util.FileUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Properties properties = FileUtil.readProperties("project.properties");
	String luceneWebPath = properties.getProperty("lucenePath");
 %>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteluceneConfigGridRecord() {
		AppUtil.deleteDataGridRecord("luceneConfigController.do?multiDel",
				"luceneConfigGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editluceneConfigGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("luceneConfigGrid");
		if (entityId) {
			showluceneConfigWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showluceneConfigWindow(entityId) {
		$.dialog.open("luceneConfigController.do?info&entityId=" + entityId, {
			title : "编辑配置信息",
			width : "680px",
			height : "550px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("luceneConfigToolbar");
	});
function reloadluceneConfigList(){
	$("#luceneConfigGrid").datagrid("reload");
}
function addIndexes(){
	var luceneName = AppUtil.getEditDataGridRecordValue("luceneConfigGrid","CONFIG_LUCENENAME");
	
	var luceneTitle = AppUtil.getEditDataGridRecordValue("luceneConfigGrid","CONFIG_TITLE");
	
	art.dialog.confirm("您确定要对"+luceneTitle+"生成索引吗?", function() {			
		window.open("<%=luceneWebPath%>/LuceneRebuild?luceneName="+luceneName);
	})
	return false;
}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="luceneConfigToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_OSTOPIC"
								iconcls="eicon-plus" plain="true"
								onclick="showluceneConfigWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-pencil" plain="true"
								onclick="editluceneConfigGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_luceneConfig"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteluceneConfigGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_INDEXES"
								iconcls="icon-foldertable" plain="true"
								onclick="addIndexes();">生成索引</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="luceneConfigForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">配置名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.CONFIG_TITLE_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('luceneConfigToolbar','luceneConfigGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('luceneConfigForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="luceneConfigGrid" fitcolumns="true" toolbar="#luceneConfigToolbar"
			method="post" idfield="CONFIG_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="luceneConfigController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CONFIG_ID',hidden:true">CONFIG_ID</th>	
					<th data-options="field:'CONFIG_TITLE',align:'left'" width="25%">配置名称</th>
					<th data-options="field:'CONFIG_LUCENENAME',align:'left'" width="15%">索引名称</th>						
					<th data-options="field:'CONFIG_INDEXDIRECTORY',align:'left'" width="20%">索引路径</th>
					<th data-options="field:'CONFIG_LISTCOUNT',align:'left'" width="10%">页显示数</th>
					<th data-options="field:'CONFIG_DBTYPE',align:'left'" width="10%">数据库类型</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="16%">配置时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
