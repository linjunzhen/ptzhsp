<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable {
	width: 100%;
	height: 100%
}

.gridtable .datagrid-htable {
	border-top: 1px solid #ccc;
	border-left: 1px solid #ccc
}

.gridtable .datagrid-btable {
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc
}

.gridtable .datagrid-header-row td {
	border-width: 0;
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

.gridtable .datagrid-header-row td:last-child {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}

.gridtable .datagrid-body td {
	border-width: 0;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
</style>
<script type="text/javascript">

    function exportBjpyExcel(){
    	var beginDate = $("input[name='Q_t.CREATE_TIME_>=']").val();
    	var endDate = $("input[name='Q_t.CREATE_TIME_<=']").val();
    	window.location.href = "statisticsNewController.do?exportSlBjpytjExcel&Q_T.CREATE_TIME_GE="+beginDate+" 00:00:00&Q_T.CREATE_TIME_LE="+endDate+" 23:59:59&beginDate="+beginDate+"&endDate="+endDate;
    }

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.SLBJPY_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_t.CREATE_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_t.CREATE_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_t.CREATE_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogL.SLBJPY_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_t.CREATE_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_t.CREATE_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_t.CREATE_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
	$('#SLBJPYStatisticsGrid').datagrid({ pagination: false,
		onLoadSuccess: function (data) {
			if (data.rows.length > 0) {
				//调用mergeCellsByField()合并单元格
				mergeCellsByField("SLBJPYStatisticsGrid", "PARENT_NAME,OPERATOR");
			}
		}
	});		
	/**
	* EasyUI DataGrid根据字段动态合并单元格
	* 参数 tableID 要合并table的id
	* 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
	*/
	function mergeCellsByField(tableID, colList) {
		var ColArray = colList.split(",");
		var tTable = $("#" + tableID);
		var TableRowCnts = tTable.datagrid("getRows").length;
		var tmpA;
		var tmpB;
		var PerTxt = "";
		var CurTxt = "";
		var alertStr = "";
		for (j = ColArray.length - 1; j >= 0; j--) {
			PerTxt = "";
			tmpA = 1;
			tmpB = 0;

			for (i = 0; i <= TableRowCnts; i++) {
				if (i == TableRowCnts) {
					CurTxt = "";
				}
				else {
					CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
				}
				if (PerTxt == CurTxt) {
					tmpA += 1;
				}
				else {
					tmpB += tmpA;
					
					tTable.datagrid("mergeCells", {
						index: i - tmpA,
						field: ColArray[j],　　//合并字段
						rowspan: tmpA,
						colspan: null
					});
					tTable.datagrid("mergeCells", { //根据ColArray[j]进行合并
						index: i - tmpA,
						field: "Ideparture",
						rowspan: tmpA,
						colspan: null
					});
				   
					tmpA = 1;
				}
				PerTxt = CurTxt;
			}
		}
	}		
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="SLBJPYStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
					         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
					         onclick="exportBjpyExcel();">导出数据</a> 
							 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SLBJPYForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.SLBJPY_TIME_BEGIN" name="Q_t.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.SLBJPY_TIME_END" name="Q_t.CREATE_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SLBJPYStatisToolBar','SLBJPYStatisticsGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SLBJPYForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="SLBJPYStatisticsGrid" fitcolumns="false" toolbar="#SLBJPYStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsNewController.do?slbjpyData">
			<thead>
				<tr>
					<th data-options="field:'PARENT_NAME',align:'left'" width="15%" rowspan="2">上级部门</th>
					<!-- <th data-options="field:'CUR_WIN',align:'left'" width="100" >窗口号</th> -->
					<th data-options="field:'OPERATOR',align:'left'" width="10%" rowspan="2">用户</th>
					<th colspan="4">办件量（件）</th>
					<th colspan="3">评议量（件）</th>
					<th colspan="3">非常满意数</th>
					<th colspan="3">满意数</th>
					<th colspan="3">一般数</th>
					<th colspan="3">不满意数</th>
				</tr>
				<tr>					
					<th data-options="field:'COUNTSTOTAL',align:'center'" width="10%" >办件量总计</th>
					<th data-options="field:'COUNTS_2',align:'center'" width="10%" >承诺</th>
					<th data-options="field:'COUNTS_1',align:'center'" width="10%" >即办</th>
					<th data-options="field:'COUNTS_3',align:'center'" width="10%" >特殊</th>
					<th data-options="field:'SUMS_2',align:'center'" width="10%" >承诺</th>
					<th data-options="field:'SUMS_1',align:'center'" width="10%" >即办</th>
					<th data-options="field:'SUMS_3',align:'center'" width="10%" >特殊</th>
					<th data-options="field:'EVALUATE_3_2',align:'center'" width="10%" >承诺</th>
					<th data-options="field:'EVALUATE_3_1',align:'center'" width="10%" >即办</th>
					<th data-options="field:'EVALUATE_3_3',align:'center'" width="10%" >特殊</th>
					<th data-options="field:'EVALUATE_2_2',align:'center'" width="10%" >承诺</th>
					<th data-options="field:'EVALUATE_2_1',align:'center'" width="10%" >即办</th>
					<th data-options="field:'EVALUATE_2_3',align:'center'" width="10%" >特殊</th>
					<th data-options="field:'EVALUATE_1_2',align:'center'" width="10%" >承诺</th>
					<th data-options="field:'EVALUATE_1_1',align:'center'" width="10%" >即办</th>
					<th data-options="field:'EVALUATE_1_3',align:'center'" width="10%" >特殊</th>
					<th data-options="field:'EVALUATE_0_2',align:'center'" width="10%" >承诺</th>
					<th data-options="field:'EVALUATE_0_1',align:'center'" width="10%" >即办</th>
					<th data-options="field:'EVALUATE_0_3',align:'center'" width="10%" >特殊</th>
				</tr>
				
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>