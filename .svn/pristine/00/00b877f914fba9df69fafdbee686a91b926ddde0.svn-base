<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,json2,validationegine"></eve:resources>
<script type="text/javascript">
	function deleteBillItemGridRecord() {
		 AppUtil.deleteDataGridRecord("billRightController.do?multiDelItem", "billItemGrid");
	};

	function editBillItemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("billItemGrid");
		if (entityId) {
			showBillItemWindow(entityId);
		}
	}

	function showBillItemWindow(entityId) {
		var departId = '${billCatalog.DEPART_ID}';
		var catalogId = '${billCatalog.BILL_ID}';
		var isPublic = '${billCatalog.IS_PUBLIC}';
		var fta = '${billCatalog.FTA_FLAG}';
		if (entityId) {
			$.dialog.open("billRightController.do?billItemInfo&entityId="
					+ entityId+"&departId="+departId+"&catalogId="+catalogId+"&isPublic="+isPublic+"&fta="+fta, {
				title : "清单信息",
				width : "600px",
				lock : true,
				resize : false,
				height : "260px",
				close: function () {
	                AppUtil.gridDoSearch("billItemToolbar","billItemGrid");
	            }
			}, false);
		} else {
			$.dialog.open("billRightController.do?billItemInfo&departId="+departId+"&catalogId="+catalogId+"&isPublic="+isPublic+"&fta="+fta, {
				title : "清单信息",
				width : "600px",
				lock : true,
				resize : false,
				height : "260px",
				close: function () {
	                AppUtil.gridDoSearch("billItemToolbar","billItemGrid");
	            }
			}, false);
		}
	}
	function formatter(val,row){
		if(val=="0"){
			return "<font color='red'><b>不公开</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>公开</b></font>";
		}
	};
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true">
		<div region="center">
			<!-- =========================查询面板开始========================= -->
			<div id="billItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton" iconCls="icon-note-add"
									plain="true" onclick="showBillItemWindow();">新建</a> <a href="#"
									class="easyui-linkbutton" iconCls="icon-note-edit" plain="true"
									onclick="editBillItemGridRecord();">编辑</a> 
<%--									<a href="#"--%>
<%--									class="easyui-linkbutton" iconCls="icon-note-delete"--%>
<%--									plain="true" onclick="deleteBillItemGridRecord();">删除</a>--%>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="billRightForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:70px;text-align:right;">清单标题：</td>
								<td style="width:156px;"><input class="eve-input"
									type="text" maxlength="50" style="width:96%;"
									name="Q_T.BILL_NAME_LIKE" /></td>

								<td colspan="2"><input type="button" value="查询"
									class="eve-button"
									onclick="AppUtil.gridDoSearch('billItemToolbar','billItemGrid')" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('billRightForm')" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="billItemGrid" fitcolumns="true" toolbar="#billItemToolbar"
				method="post" idfield="BILL_ID" checkonselect="true"
				selectoncheck="true" fit="true" border="false" nowrap="false"
				singleSelect="false"
				url="billRightController.do?billItemData&billCatalogId=${billCatalog.BILL_ID}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'BILL_ID',hidden:true" width="80">BILL_ID</th>
						<th data-options="field:'BILL_NAME',align:'left'" width="150">清单标题</th>
						<th data-options="field:'FTA_FLAG',align:'left'" width="80">清单属性</th>
						<th data-options="field:'IS_PUBLIC',align:'left'" width="60" formatter="formatter">是否公开</th>
						<th data-options="field:'CREATE_TIME',align:'left'" width="80">创建时间</th>
					</tr>
				</thead>
			</table>
			<!-- =========================表格结束==========================-->
		</div>
	</div>

</body>