<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除信息列表记录
	 */
	function deletesingleSignOnGridRecord() {
		AppUtil.deleteDataGridRecord("singleSignOnController.do?multiDel",
				"singleSignOnGrid");
	};
	/**
	 * 编辑信息列表记录
	 */
	function editsingleSignOnGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("singleSignOnGrid");
		if (entityId) {
			showsingleSignOnWindow(entityId);
		}
	}

	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#singleSignOnToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	/**
	 * 显示信息信息对话框
	 */
	function showsingleSignOnWindow(entityId) {
		$.dialog.open("singleSignOnController.do?info&entityId=" + entityId, {
			title : "单点配置信息",
			width : "1000px",
			height : "360px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("singleSignOnToolbar");
	});
	function reloadsingleSignOnList(){
		$("#singleSignOnGrid").datagrid("reload");
	}

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="singleSignOnToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_singleSignOn"
								iconcls="eicon-plus" plain="true"
								onclick="showsingleSignOnWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_singleSignOn"
								iconcls="eicon-pencil" plain="true"
								onclick="editsingleSignOnGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_singleSignOn"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletesingleSignOnGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="singleSignOnForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:100px;text-align:right;">对接单位：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.SSO_DW_LIKE"/>
	    </td> 
        <td style="width:100px;text-align:right;">单位联系人：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.SSO_DWLXR_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('singleSignOnToolbar','singleSignOnGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('singleSignOnForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="singleSignOnGrid" fitcolumns="true" toolbar="#singleSignOnToolbar"
			method="post" idfield="SSO_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="singleSignOnController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SSO_ID',hidden:true">SSO_ID</th>	
					<th data-options="field:'SSO_DW',align:'left'" width="15%">对接单位</th>		
					<th data-options="field:'SSO_DWLXR',align:'left'" width="13%">单位联系人</th>	
					<th data-options="field:'SSO_DWLXRLXDH',align:'left'" width="12%">联系人电话</th>
					<th data-options="field:'SSO_USERNAME',align:'left'" width="10%">用户账号</th>				
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">创建时间</th>		
					<th data-options="field:'SSO_TOKEN',align:'left'" width="15%">TOKEN</th>	
					<th data-options="field:'SSO_TOKEN_TIME',align:'left'" width="15%">获取TOKEN时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
