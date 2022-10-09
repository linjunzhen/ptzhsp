<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable{width:100%;height:100%}
.gridtable .datagrid-htable{border-top:1px solid #8DB2E3;border-right:1px solid #8DB2E3}
.gridtable .datagrid-btable{border-right:1px solid #8DB2E3;border-bottom:1px solid #8DB2E3}
.gridtable .datagrid-header-row td{border-width:0;border-left:1px solid #8DB2E3;border-bottom: 1px solid #8DB2E3;}
.gridtable .datagrid-body td{border-width:0;border-left:1px solid #8DB2E3;border-top: 1px solid #8DB2E3} 
</style>
<script type="text/javascript">

    
    function exportGlcExcel(){
    	var beginDate = $("input[name='Q_T.CREATE_TIME_>=']").val();
    	var endDate = $("input[name='Q_T.CREATE_TIME_<=']").val();
    	window.location.href = "statistCommercialController.do?exportGlcExcel&Q_T.CREATE_TIME_GE="+beginDate+" 00:00:00&Q_T.CREATE_TIME_LE="+endDate+" 23:59:59&beginDate="+beginDate+"&endDate="+endDate;
    }

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.CREATE_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.CREATE_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.CREATE_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogLBjzt.CKBJLTJ_CREATE_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.CREATE_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.CREATE_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.CREATE_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
			
	$('#glcStatisGrid').datagrid({ pagination: false,
		onLoadSuccess: function (data) {
			if (data.rows.length > 0) {
				//调用mergeCellsByField()合并单元格
				mergeCellsByField("glcStatisGrid", "PARENT_NAME");
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
	function countsForM(val,row){
		var name = row.JBR_NAME;
		var href = "<a href='";
		href += "statistCommercialController.do?countsDetail&name="+name;
		href += "' target='_blank'><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	}
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="glcStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
						         <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true" 
						         onclick="exportGlcExcel();">导出数据</a> 
								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SysLogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogLBjzt.CKBJLTJ_CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('glcStatisToolBar','glcStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="glcStatisGrid" fitcolumns="false" toolbar="#glcStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="true"
			url="statistCommercialController.do?glcData">
			<thead>
				<tr>
					<th data-options="field:'JBR_NAME',width:150" rowspan="2">姓名</th>
					<th colspan="5">办件统计</th>
				</tr>
				<tr>
					<th data-options="field:'COUNTS',align:'center'" width="70" formatter="countsForM" >办件总数</th>
					<th data-options="field:'BANJS',align:'center'" width="70">办结数</th>
					<th data-options="field:'ZBS',align:'center'" width="70" >在办数</th>					
					<th data-options="field:'BUJS',align:'center'" width="70" >补件数</th>
					<th data-options="field:'TJS',align:'center'" width="70" >退件数</th>
				</tr>
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>