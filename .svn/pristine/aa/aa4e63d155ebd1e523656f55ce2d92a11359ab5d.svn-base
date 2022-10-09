<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south {
	border-top-width: 0px !important;
}

.eve_buttons {
	position: relative !important;
}
</style>
<script type="text/javascript">

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true">
		<div data-options="region:'center',split:false">
			<div id="auditDataGridToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<!-- <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选标的物列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>  -->
			</div>
			<!-- =========================查询面板结束========================= -->

			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				pageSize="30" id="AuditDataGrid" fitColumns="true"
				toolbar="#auditDataGridToolbar" method="post" idField="OB_ID"
				checkOnSelect="true" emptyMsg="无数据" selectOnCheck="true" fit="true"
				border="false" nowrap="false"
				url="fjfdaTransController.do?auditDataGrid&fjfdaeveId=${fjfdaeveId}">
				<thead>
					<th data-options="field:'TASK_NODENAME',align:'left'"  width="100">环节名称</th>									
					<th data-options="field:'ASSIGNER_NAME',align:'left'"  width="150">审核人</th>
					<th data-options="field:'TASK_STATUS',align:'left'"  width="100">办理状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'"  width="150">提交时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="150">结束时间</th>
					<th data-options="field:'HANDLE_OPINION',align:'left'" width="200">审批意见</th>
                </thead>
			</table>
        </div>
    </div>

    

</body>


