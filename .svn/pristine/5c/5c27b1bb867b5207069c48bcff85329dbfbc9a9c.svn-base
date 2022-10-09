<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	function deleteDocumentTemplateGridRecord() {
		AppUtil.deleteDataGridRecord("documentTemplateController.do?multiDel",
				"DocumentTemplateGrid");
	};

	function editDocumentTemplateGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("DocumentTemplateGrid");
		if (entityId) {
			showDocumentTemplateWindow(entityId);
		}
	}


	function showDocumentTemplateWindow(entityId) {
		$.dialog.open("documentTemplateController.do?info&entityId=" + entityId, {
			title : "模版配置信息",
			width : "700px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("DocumentTemplateToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DocumentTemplateToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_DocumentTemplate"
								iconcls="eicon-plus" plain="true"
								onclick="showDocumentTemplateWindow();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_DocumentTemplate"
								iconcls="eicon-pencil" plain="true"
								onclick="editDocumentTemplateGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_DocumentTemplate"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteDocumentTemplateGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DocumentTemplateForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:78px;text-align:right;">模板名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_DOCUMENT_NAME_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('DocumentTemplateToolbar','DocumentTemplateGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('DocumentTemplateForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DocumentTemplateGrid" fitcolumns="true" toolbar="#DocumentTemplateToolbar"
			method="post" idfield="DOCUMENT_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="documentTemplateController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'DOCUMENT_ID',hidden:true">Document_ID</th>
					<th data-options="field:'DOCUMENT_NAME',align:'left'" width="95%">模版名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
