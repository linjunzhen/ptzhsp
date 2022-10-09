<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteReadTemplateGridRecord() {
		AppUtil.deleteDataGridRecord("readTemplateController.do?multiDel",
				"ReadTemplateGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editReadTemplateGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ReadTemplateGrid");
		if (entityId) {
			showReadTemplateWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showReadTemplateWindow(entityId) {
		$.dialog.open("readTemplateController.do?info&entityId=" + entityId, {
			title : "阅办模板配置信息",
			width : "700px",
			height : "300px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("ReadTemplateToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ReadTemplateToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_ReadTemplate"
								iconcls="eicon-plus" plain="true"
								onclick="showReadTemplateWindow();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_ReadTemplate"
								iconcls="eicon-pencil" plain="true"
								onclick="editReadTemplateGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_ReadTemplate"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteReadTemplateGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ReadTemplateForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:78px;text-align:right;">阅办模板名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_READ_NAME_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('ReadTemplateToolbar','ReadTemplateGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('ReadTemplateForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ReadTemplateGrid" fitcolumns="true" toolbar="#ReadTemplateToolbar"
			method="post" idfield="READ_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="readTemplateController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'READ_ID',hidden:true">READ_ID</th>
					<th data-options="field:'READ_NAME',align:'left'" width="95%">阅办事项名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
