<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 删除流程实例列表记录
	 */
	function deleteTaxiLisenceGridRecord() {
		AppUtil.deleteDataGridRecord("executionController.do?multiDel",
				"TaxiLisenceGrid");
	};
	/**
	 * 编辑流程实例列表记录
	 */
	function editTaxiLisenceGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("TaxiLisenceGrid");
		if (entityId) {
			showExecutionWindow(entityId);
		}
	}

	function formatSubject(val,row){
		//获取流程状态
		// var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		// 追加赋值isFiled=1用于detail页面查询时区分是否未归档流程查询
		// 若isFiled=1，则查询归档表数据
		href += "executionController.do?goDetail&isFiled=0&exeId=" + exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>" + val + "</span></a>";
		return href;
	}

	function formatApplyStatus(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		if(runStatus == "4"){
			return "已退件";
		}else{
			return val;
		}
	}

	/**
	 * 导出execl
	 */
	function exportExcel() {
		var url = "TaxiLicenseController.do?exportExcel";
		window.location.href = url;
	}

	/**
	 * excel批量导入数据
	 */
	function showExcelImportWindows(){
		$.dialog.open("TaxiLicenseController.do?excelImportView", {
			title : "导入excel",
			width : "350px",
			height : "50px",
			lock : true,
			resize : false
		}, false);
	}




	/**
	 * 显示流程实例信息对话框
	 */
	function showExecutionWindow(entityId) {
		$.dialog.open("executionController.do?info&entityId=" + entityId, {
			title : "流程实例信息",
			width : "600px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	}





	$(document).ready(function() {
		var needHandlePtjStart = {
			elem : "#NeedMeHandlePtjT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_E.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_E.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_E.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var needHandlePtjEnd = {
			elem : "#NeedMeHandlePtjT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_E.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_E.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_E.CREATE_TIME_<=']").val("");
					}
				}
			}
		};

		laydate(needHandlePtjStart);
		laydate(needHandlePtjEnd);
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="TaxiLisenceToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
							   class="easyui-linkbutton l-btn l-btn-small l-btn-plain"  reskey="importRecord"
							   iconCls="eicon-file-excel-o" plain="true"
							   onclick="showExcelImportWindows();">导入</a>
							<a href="#" class="easyui-linkbutton l-btn l-btn-small l-btn-plain" reskey="exportRecord"
							   iconcls="eicon-file-excel-o" plain="true"
							   onclick="exportExcel();">导出数据</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="TaxiLisencePtjForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">申请状态</td>
							<td style="width:135px;">
								<eve:eveselect clazz="tab_text"
											   dataParams="ZGZ_APPLY_STATUS"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="全部" name="Q_ZGZ.ZGZ_APPLY_STATUS_="></eve:eveselect>

<%--								<input class="easyui-combobox" style="width:128px;" name="Q_E.RUN_STATUS_="
									data-options="url:'dictionaryController.do?load&defaultEmpty=true&typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />--%>
							</td>
							<td style="width:68px;text-align:right;">准驾车型</td>
							<td style="width:135px;">
								<eve:eveselect clazz="tab_text" dataParams="licenseType" dataInterface="dictionaryService.findDatasForSelect"
										   defaultEmptyText="选择准驾车型" name="Q_ZGZ.ZGZ_APPLY_ALLOWCARTYPE_="></eve:eveselect>
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandlePtjT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandlePtjT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>

							<td style="width:68px;text-align:right;">姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.SQRMC_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('TaxiLisenceToolbar','TaxiLisenceGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('TaxiLisencePtjForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="TaxiLisenceGrid" fitcolumns="true" toolbar="#TaxiLisenceToolbar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="TaxiLicenseController.do?passedTaxiLisenceList">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
<%--					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>--%>
					<th data-options="field:'CUR_STEPNAMES',hidden:true">当前环节名称</th>
					<th data-options="field:'RUN_STATUS',hidden:true">流程状态</th>
<%--					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="12%">当前环节名称</th>--%>
					<th data-options="field:'EXE_ID',align:'left'" width="12%" formatter="formatSubject">申报号</th>
					<th data-options="field:'ZGZ_APPLY_STATUS',align:'left'" width="12%" formatter="formatApplyStatus">申请状态</th>
					<th data-options="field:'ZGZ_APPLY_USERNAME',align:'left'" width="12%">姓名</th>
					<th data-options="field:'ZGZ_APPLY_ALLOWCARTYPE',align:'left'" width="8%">准驾车型</th>
					<th data-options="field:'ZGZ_APPLY_FIRSTGETJSZTIME',align:'left'" width="12%">初领驾照日期</th>
					<th data-options="field:'ZGZ_EXAM_SCORE',align:'left'" width="12%">全国科目</th>
					<th data-options="field:'ZGZ_AREAEXAM_SCORE',align:'left'" width="12%">区域科目</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="12%">申请时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
