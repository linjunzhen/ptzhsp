<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function exportSwbReturnInfoStatisExcel(){
		var sendTimeFrom = $("#SwbReturnInfoStatisForm input[name='Q_T.SEND_TIME_>=']").val();
		var sendTimeTo = $("#SwbReturnInfoStatisForm input[name='Q_T.SEND_TIME_<=']").val();
		var recTimeFrom = $("#SwbReturnInfoStatisForm input[name='Q_T.REC_TIME_>=']").val();
		var recTimeTo = $("#SwbReturnInfoStatisForm input[name='Q_T.REC_TIME_<=']").val();
		var DEPART_NAME_LIKE = $("#SwbReturnInfoStatisForm input[name='Q_s.IMPL_DEPART_LIKE']").val();
		var DEPART_NAMEC_LIKE = $("#SwbReturnInfoStatisForm input[name='Q_s.ZBCS_LIKE']").val();
		var ITEM_NAME_LIKE = $("#SwbReturnInfoStatisForm input[name='Q_s.ITEM_NAME_LIKE']").val();
		var DATA_TYPE_LIKE = $("#SwbReturnInfoStatisForm input[name='Q_r.DATA_TYPE_LIKE']").val();
		var RESULT_LIKE = $("#SwbReturnInfoStatisForm input[name='Q_t.RESULT_LIKE']").val();
		if (sendTimeFrom || sendTimeTo || recTimeFrom || recTimeTo || DEPART_NAME_LIKE || DEPART_NAMEC_LIKE
				|| ITEM_NAME_LIKE || DATA_TYPE_LIKE || RESULT_LIKE) {
			if (recTimeFrom != '' && recTimeFrom != null && recTimeFrom != undefined) {
				recTimeFrom = recTimeFrom + " 00:00:00";
			}
			if (recTimeTo != '' && recTimeTo != null && recTimeTo != undefined) {
				recTimeTo = recTimeTo + " 23:59:59";
			}
			if (sendTimeFrom != '' && sendTimeFrom != null && sendTimeFrom != undefined) {
				sendTimeFrom = sendTimeFrom + " 00:00:00";
			}
			if (sendTimeTo != '' && sendTimeTo != null && sendTimeTo != undefined) {
				sendTimeTo = sendTimeTo + " 23:59:59";
			}

			window.location.href = encodeURI("swbDataController.do?exportSwbReturnInfoStatisExcel&Q_T.SEND_TIME_GE=" + sendTimeFrom
					+ "&Q_T.SEND_TIME_LE=" + sendTimeTo + "&Q_T.REC_TIME_GE=" + recTimeFrom +
					"&Q_T.REC_TIME_LE=" + recTimeTo + "&Q_t.RESULT_LIKE=" + RESULT_LIKE + "&Q_s.IMPL_DEPART_LIKE=" + DEPART_NAME_LIKE +
					"&Q_s.ZBCS_LIKE=" + DEPART_NAMEC_LIKE + "&Q_s.ITEM_NAME_LIKE=" + ITEM_NAME_LIKE + "&Q_r.DATA_TYPE_LIKE=" + DATA_TYPE_LIKE);
		} else {
			art.dialog({
				content: "请填写查询条件后再进行导出",
				icon:"warning",
				time:3,
				ok: true
			})
		}

	}
	 /**
	 * 导出execl
	 */
	function showExcelExportSpsmx() {
	    AppUtil.showExcelExportWin({
	        dataGridId:"SwbReturnInfoStatisStatisGrid",
	        tplKey:"SwbReturnInfoStatisb",
	        excelName:"审批事项明细表",
	        queryParams:{
	            "E.RESULT":$("input[name='Q_t.RESULT_LIKE']").val(),
	            "E.SEND_TIME":$("input[name='Q_T.SEND_TIME_>=']").val(),
	            "Y.SEND_TIME":$("input[name='Q_T.SEND_TIME_<=']").val(),
	            "E.REC_TIME":$("input[name='Q_T.REC_TIME_>=']").val(),
	            "Y.SEND_TIME":$("input[name='Q_T.REC_TIME_<=']").val()
	        }
	    });
	};
	
	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.SWBRETURNINFOSTATIS_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.SEND_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.SEND_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.SEND_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogL.SWBRETURNINFOSTATIS_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.SEND_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.SEND_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.SEND_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);
				var start2 = {
						elem : "#SysLogL.SWBRETURNINFOSTATISBJ_TIME_BEGIN",
						format : "YYYY-MM-DD",
						istime : false,
						choose : function(datas) {
							var beginTime = $("input[name='Q_T.REC_TIME_>=']")
									.val();
							var endTime = $("input[name='Q_T.REC_TIME_<=']")
									.val();
							if (beginTime != "" && endTime != "") {
								var start = new Date(beginTime.replace("-", "/")
										.replace("-", "/"));
								var end = new Date(endTime.replace("-", "/")
										.replace("-", "/"));
								if (start > end) {
									alert("开始时间必须小于等于结束时间,请重新输入!");
									$("input[name='Q_T.REC_TIME_>=']").val("");
								}
							}
						}
					};
					var end2 = {
						elem : "#SysLogL.SWBRETURNINFOSTATISBJ_TIME_END",
						format : "YYYY-MM-DD",
						istime : false,
						choose : function(datas) {
							var beginTime = $("input[name='Q_T.REC_TIME_>=']")
									.val();
							var endTime = $("input[name='Q_T.REC_TIME_<=']")
									.val();
							if (beginTime != "" && endTime != "") {
								var start = new Date(beginTime.replace("-", "/")
										.replace("-", "/"));
								var end = new Date(endTime.replace("-", "/")
										.replace("-", "/"));
								if (start > end) {
									alert("结束时间必须大于等于开始时间,请重新输入!");
									$("input[name='Q_T.REC_TIME_<=']").val("");
								}
							}
						}
					};
					laydate(start2);
					laydate(end2);
	
			});
			
	function resetSwbReturnInfoStatisForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SwbReturnInfoStatisForm');
	}

	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#SwbReturnInfoStatisStatisGrid');  
		var options = grid.datagrid('getPager').data("pagination").options;
		var maxnum = options.pageNumber*options.pageSize;
		var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ maxnum + '</span>').hide().appendTo(document.body);
	    $(currentObj).css('font', '12px, Microsoft YaHei');
	    var width = currentObj.width();
		var panel = grid.datagrid('getPanel');
	    if(width>25){
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width+5);
			grid.datagrid("resize");
		}else{			
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
			grid.datagrid("resize");
		}
	}
	$('#SwbReturnInfoStatisStatisGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="SwbReturnInfoStatisStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
						         onclick="exportSwbReturnInfoStatisExcel();">导出查询数据</a> 
								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SwbReturnInfoStatisForm" id="SwbReturnInfoStatisForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">部门：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_s.IMPL_DEPART_LIKE" />
							</td>
							<td style="width:80px;text-align:right;">处室：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_s.ZBCS_LIKE" />
							</td>
							<td style="width:80px;text-align:right;">发送时间从：</td>
							<td style="width:90px;"><input type="text"
								style="width:78px;float:left;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFOSTATIS_TIME_BEGIN" name="Q_T.SEND_TIME_>=" /></td>
							<td style="width:125px;">到<input type="text"
								style="width:78px;float:right;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFOSTATIS_TIME_END" name="Q_T.SEND_TIME_<=" /></td>
							<td style="width:80px;text-align:right;">接收时间从：</td>
							<td style="width:90px;"><input type="text"
								style="width:78px;float:left;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFOSTATISBJ_TIME_BEGIN" name="Q_T.REC_TIME_>=" /></td>
							<td style="width:125px;">到<input type="text"
								style="width:78px;float:right;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFOSTATISBJ_TIME_END" name="Q_T.REC_TIME_<=" /></td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">事项名称：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_s.ITEM_NAME_LIKE" />
							</td>
							<td style="width:80px;text-align:right;">处理结果：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_t.RESULT_LIKE" />
							</td>
							<td style="width:80px;text-align:right;">数据类型：</td>
							<td style="width:128px;">
								<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_r.DATA_TYPE_LIKE" />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SwbReturnInfoStatisStatisToolBar','SwbReturnInfoStatisStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetSwbReturnInfoStatisForm()" /></td>
							<td colspan="2"></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"  striped="true"
			id="SwbReturnInfoStatisStatisGrid" nowrap="false"
			toolbar="#SwbReturnInfoStatisStatisToolBar" method="post" idfield="UNID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="swbDataController.do?swbReturnInfoStatisData">
			<thead>
				<tr>
					<th data-options="field:'DEPART_NAME',align:'left'" width="10%" >部门</th>
					<th data-options="field:'DEPART_NAMEC',align:'left'" width="13%" >处室</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="10%" >事项编码</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="20%" >事项名称</th>
					<th data-options="field:'DATA_TYPE',align:'left'" width="7%" >数据类型</th>
					<th data-options="field:'RESULT',align:'left'" width="33%" >处理结果</th>
					<th data-options="field:'NUM',align:'left'" width="7%" >数量</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>