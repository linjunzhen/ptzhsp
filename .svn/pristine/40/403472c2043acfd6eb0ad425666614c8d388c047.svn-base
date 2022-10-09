<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteCertificateTemplateGridRecord() {
		AppUtil.deleteDataGridRecord("certificateTemplateController.do?multiDel",
				"CertificateTemplateGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editCertificateTemplateGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("CertificateTemplateGrid");
		if (entityId) {
			showCertificateTemplateWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showCertificateTemplateWindow(entityId) {
		$.dialog.open("certificateTemplateController.do?info&entityId=" + entityId, {
			title : "权证模板配置信息",
			width : "700px",
			height : "300px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("CertificateTemplateToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="CertificateTemplateToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_CertificateTemplate"
								iconcls="eicon-plus" plain="true"
								onclick="showCertificateTemplateWindow();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_CertificateTemplate"
								iconcls="eicon-pencil" plain="true"
								onclick="editCertificateTemplateGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CertificateTemplate"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteCertificateTemplateGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="CertificateTemplateForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:78px;text-align:right;">权证模板名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_CERTIFICATE_NAME_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('CertificateTemplateToolbar','CertificateTemplateGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('CertificateTemplateForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="CertificateTemplateGrid" fitcolumns="true" toolbar="#CertificateTemplateToolbar"
			method="post" idfield="CERTIFICATE_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="certificateTemplateController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CERTIFICATE_ID',hidden:true">CERTIFICATE_ID</th>
					<th data-options="field:'CERTIFICATE_NAME',align:'left'" width="95%">权证模板名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
