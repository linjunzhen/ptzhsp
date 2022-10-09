
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接日志列表记录
	 */
	function deleteDataAbutLogGridRecord() {
		AppUtil.deleteDataGridRecord("dataAbutLogController.do?multiDel",
				"DataAbutLogGrid");
	};
	/**
	 * 编辑对接日志列表记录
	 */
	function editDataAbutLogGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("DataAbutLogGrid");
		if (entityId) {
			showDataAbutLogWindow(entityId);
		}
	}

	function showDataAbutDetail() {
		var entityId = AppUtil.getEditDataGridRecord("DataAbutLogGrid");
		if (entityId) {
			$.dialog.open("dataAbutLogController.do?info&entityId=" + entityId, {
				title : "对接日志明细",
				width: "100%",
			    height: "100%",
			    left: "0%",
			    top: "0%",
			    fixed: true,
				lock : true,
				resize : false
			}, false);
		}
	};
	
	function formatAbutResult(val,row){
		if(val=="1"){
			return "<font color='green'><b>成功</b></font>";
		}else if(val=="-1"){
			return "<font color='red'><b>失败</b></font>";
		}
	};

	$(document).ready(function() {
		var start1 = {
			elem : "#DataAbutLogT.ABUT_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.ABUT_TIME_>=']").val();
				var endTime = $("input[name='Q_T.ABUT_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.ABUT_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#DataAbutLogT.ABUT_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.ABUT_TIME_>=']").val();
				var endTime = $("input[name='Q_T.ABUT_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.ABUT_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);

		AppUtil.initAuthorityRes("DataAbutLogToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DataAbutLogToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_DataAbutLog"
								iconcls="eicon-eye" plain="true"
								onclick="showDataAbutDetail();">查看</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="EDIT_DataAbutLog"
								iconcls="eicon-refresh" plain="true"
								onclick="editDataAbutLogGridRecord();">重新发起对接</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_DataAbutLog"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteDataAbutLogGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DataAbutLogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">配置编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ABUT_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">配置名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ABUT_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">对接结果</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.ABUT_RESULT_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=IsSuccess',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">对接日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="DataAbutLogT.ABUT_TIME_BEGIN" name="Q_T.ABUT_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">对接时间至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="DataAbutLogT.ABUT_TIME_END" name="Q_T.ABUT_TIME_&lt;=" /></td>
							<td style="width:68px;text-align:right;">业务标识值</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="64" style="width:128px;"
								name="Q_T.BUS_IDVALUE_=" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DataAbutLogToolbar','DataAbutLogGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('DataAbutLogForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DataAbutLogGrid" fitcolumns="true" toolbar="#DataAbutLogToolbar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="dataAbutLogController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'LOG_ID',hidden:true">LOG_ID</th>
					<th data-options="field:'ABUT_CODE',align:'left'" width="10%">对接配置编码</th>
					<th data-options="field:'BUS_IDVALUE',align:'left'" width="20%">业务标识值</th>
					<th data-options="field:'ABUT_NAME',align:'left'" width="25%">对接配置名称</th>
					<th data-options="field:'ABUT_TIME',align:'left'" width="15%">对接时间</th>
					<th data-options="field:'ABUT_RESULT',align:'left'" width="10%" formatter="formatAbutResult">对接结果</th>
					<th data-options="field:'ABUT_DESC',align:'left'" width="15%">对接动作描述</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
