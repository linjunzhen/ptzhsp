<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable {
	width: 100%;
	height: 100%
}

.gridtable .datagrid-htable {
	border-top: 1px solid #ccc;
	border-right: 1px solid #ccc
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

.gridtable .datagrid-body td {
	border-width: 0;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
.gridtable .datagrid-htable {
	border-left: 1px solid #ccc;
}
</style>
<script type="text/javascript">
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
				
				var start2 = {
					elem : "#SysLogLBjzt.CKBJLTJ_END_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.END_TIME_>=']").val("");
							}
						}
					}
				};
				var end2 = {
					elem : "#SysLogLBjzt.CKBJLTJ_END_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.END_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start2);
				laydate(end2);

			});
			
	$('#indPerStatisGrid').datagrid({ pagination: true,
		onLoadSuccess: function (data) {
			if (data.rows.length > 0) {
				//调用mergeCellsByField()合并单元格
				mergeCellsByField("indPerStatisGrid", "PARENT_NAME");
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
		var operatorId = row.OPERATOR_ID;
		if(val>0){
		    var href = "<a href=\"";
		    href += "javascript:showCountsWindow('"+operatorId+"')";
		    href += "\"><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
		    return href;
		}else{
		    return val;
		}
	}
	function showCountsWindow(operatorId) {
		var beginTime=document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN").value;  
		var endTime = document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_END").value;
		var beginTime2=document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_BEGIN").value;  
		var endTime2 = document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_END").value;
    	if(beginTime!='' && beginTime!=null && beginTime!=undefined){
    		beginTime = beginTime+" 00:00:00";
    	}
    	if(endTime!='' && endTime!=null && endTime!=undefined){
    		endTime = endTime+" 23:59:59";
    	}
    	if(beginTime2!='' && beginTime2!=null && beginTime2!=undefined){
    		beginTime2 = beginTime2+" 00:00:00";
    	}
    	if(endTime2!='' && endTime2!=null && endTime2!=undefined){
    		endTime2 = endTime2+" 23:59:59";
    	}
		$.dialog.open("statistCommercialController.do?countsDetail&operatorId="
				+operatorId+"&beginTime="+beginTime+"&endTime="+endTime
				+"&beginTime2="+beginTime2+"&endTime2="+endTime2, {
			title : "办件详情",
			width : "730px",
			height : "420px",
			operatorId:operatorId,
			lock : true,
			resize : false
		}, false);
	};
	
	function banjsForM(val,row){
		var operatorId = row.OPERATOR_ID;
		if(val>0){
		    var href = "<a href=\"";
		    href += "javascript:showBanJSWindow('"+operatorId+"')";
		    href += "\"><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	        return href;
		}else{
	        return val;	
		}
	}
	function showBanJSWindow(operatorId) {
		var beginTime=document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN").value;  
		var endTime = document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_END").value;
		var beginTime2=document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_BEGIN").value;  
		var endTime2 = document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_END").value;
    	if(beginTime!='' && beginTime!=null && beginTime!=undefined){
    		beginTime = beginTime+" 00:00:00";
    	}
    	if(endTime!='' && endTime!=null && endTime!=undefined){
    		endTime = endTime+" 23:59:59";
    	}
    	if(beginTime2!='' && beginTime2!=null && beginTime2!=undefined){
    		beginTime2 = beginTime2+" 00:00:00";
    	}
    	if(endTime2!='' && endTime2!=null && endTime2!=undefined){
    		endTime2 = endTime2+" 23:59:59";
    	}
		$.dialog.open("statistCommercialController.do?banJSDetail&operatorId="
				+operatorId+"&beginTime="+beginTime+"&endTime="+endTime
				+"&beginTime2="+beginTime2+"&endTime2="+endTime2, {
			title : "办结件详情",
			width : "700px",
			height : "410px",
			operatorId:operatorId,
			lock : true,
			resize : false
		}, false);
	};
	
	function zbsForM(val,row){
		if(val>0){
		    var operatorId = row.OPERATOR_ID;
		    var href = "<a href=\"";
		    href += "javascript:showZBSWindow('"+operatorId+"')";
		    href += "\"><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	        return href;
		}else{	
			return val
		}
	}
	function showZBSWindow(operatorId) {
		var beginTime=document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN").value;  
		var endTime = document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_END").value;
		var beginTime2=document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_BEGIN").value;  
		var endTime2 = document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_END").value;
    	if(beginTime!='' && beginTime!=null && beginTime!=undefined){
    		beginTime = beginTime+" 00:00:00";
    	}
    	if(endTime!='' && endTime!=null && endTime!=undefined){
    		endTime = endTime+" 23:59:59";
    	}
    	if(beginTime2!='' && beginTime2!=null && beginTime2!=undefined){
    		beginTime2 = beginTime2+" 00:00:00";
    	}
    	if(endTime2!='' && endTime2!=null && endTime2!=undefined){
    		endTime2 = endTime2+" 23:59:59";
    	}
		$.dialog.open("statistCommercialController.do?zBSDetail&operatorId="
				+operatorId+"&beginTime="+beginTime+"&endTime="+endTime
				+"&beginTime2="+beginTime2+"&endTime2="+endTime2, {
			title : "在办件详情",
			width : "700px",
			height : "410px",
			operatorId:operatorId,
			lock : true,
			resize : false
		}, false);
	};
	
	function tjsForM(val,row){
		if(val>0){
		    var operatorId = row.OPERATOR_ID;
		    var href = "<a href=\"";
		    href += "javascript:showTJSWindow('"+operatorId+"')";
		    href += "\"><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	        return href;
		}else{
		    return val;		
		}
	}
	function showTJSWindow(operatorId) {
		var beginTime=document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN").value;  
		var endTime = document.getElementById("SysLogLBjzt.CKBJLTJ_CREATE_TIME_END").value;
		var beginTime2=document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_BEGIN").value;  
		var endTime2 = document.getElementById("SysLogLBjzt.CKBJLTJ_END_TIME_END").value;
    	if(beginTime!='' && beginTime!=null && beginTime!=undefined){
    		beginTime = beginTime+" 00:00:00";
    	}
    	if(endTime!='' && endTime!=null && endTime!=undefined){
    		endTime = endTime+" 23:59:59";
    	}
    	if(beginTime2!='' && beginTime2!=null && beginTime2!=undefined){
    		beginTime2 = beginTime2+" 00:00:00";
    	}
    	if(endTime2!='' && endTime2!=null && endTime2!=undefined){
    		endTime2 = endTime2+" 23:59:59";
    	}
		$.dialog.open("statistCommercialController.do?tJSDetail&operatorId="
				+operatorId+"&beginTime="+beginTime+"&endTime="+endTime
				+"&beginTime2="+beginTime2+"&endTime2="+endTime2, {
			title : "退件详情",
			width : "700px",
			height : "410px",
			operatorId:operatorId,
			lock : true,
			resize : false
		}, false);
	};

</script>
<div class="easyui-layout eui-th-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="indPerStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="SysLogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:70px;text-align:right;">发起日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogLBjzt.CKBJLTJ_CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:30px;text-align:right;">到</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogLBjzt.CKBJLTJ_CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
								
							<td style="width:70px;text-align:right;">办结日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogLBjzt.CKBJLTJ_END_TIME_BEGIN" name="Q_T.END_TIME_>=" /></td>
							<td style="width:30px;text-align:right;">到</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogLBjzt.CKBJLTJ_END_TIME_END" name="Q_T.END_TIME_<=" /></td>
								
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('indPerStatisToolBar','indPerStatisGrid')" />
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
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="indPerStatisGrid" fitcolumns="true" toolbar="#indPerStatisToolBar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="statistCommercialController.do?indPerData">
			<thead>
				<tr>
					<th data-options="field:'FULLNAME'" width="29%" rowspan="2">姓名</th>
					<th colspan="4">办件统计</th>
				</tr>
				<tr>
					<th data-options="field:'COUNTS',align:'center'" width="17%" formatter="countsForM" >办件总数</th>
					<th data-options="field:'BANJS',align:'center'" width="17%" formatter="banjsForM" >办结数</th>
					<th data-options="field:'ZBS',align:'center'" width="17%" formatter="zbsForM" >在办数</th>		
					<th data-options="field:'TJS',align:'center'" width="17%" formatter="tjsForM" >退件数</th>
				</tr>
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>