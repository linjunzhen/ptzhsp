<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function exportSwbReturnInfoExcel(){
		var sendTimeFrom = $("#SwbReturnInfoForm input[name='Q_T.SEND_TIME_>=']").val();
		var sendTimeTo = $("#SwbReturnInfoForm input[name='Q_T.SEND_TIME_<=']").val();
		var recTimeFrom = $("#SwbReturnInfoForm input[name='Q_T.REC_TIME_>=']").val();
		var recTimeTo = $("#SwbReturnInfoForm input[name='Q_T.REC_TIME_<=']").val();
		var RESULT_LIKE = $("#SwbReturnInfoForm input[name='Q_t.RESULT_LIKE']").val();
		var RUN_STATUS = $("#SwbReturnInfoForm input[name='Q_T.RUN_STATUS_=']").val();
		var exeId = $("#SwbReturnInfoForm input[name='Q_t.SN_LIKE']").val();
		var dataType = $("#SwbReturnInfoForm input[name='Q_r.DATA_TYPE_EQ']").val();
		if (sendTimeFrom || sendTimeTo || recTimeFrom ||recTimeTo ||RESULT_LIKE || RUN_STATUS || exeId || dataType) {
			if(recTimeFrom!='' && recTimeFrom!=null && recTimeFrom!=undefined){
				recTimeFrom = recTimeFrom+" 00:00:00";
			}
			if(recTimeTo!='' && recTimeTo!=null && recTimeTo!=undefined){
				recTimeTo = recTimeTo+" 23:59:59";
			}
			if(sendTimeFrom!='' && sendTimeFrom!=null && sendTimeFrom!=undefined){
				sendTimeFrom = sendTimeFrom+" 00:00:00";
			}
			if(sendTimeTo!='' && sendTimeTo!=null && sendTimeTo!=undefined){
				sendTimeTo = sendTimeTo+" 23:59:59";
			}
			window.location.href = encodeURI("swbDataController.do?exportSwbReturnInfoExcel&Q_T.SEND_TIME_GE="+sendTimeFrom
					+"&Q_T.SEND_TIME_LE="+sendTimeTo+"&Q_T.REC_TIME_GE="+recTimeFrom+
					"&Q_T.REC_TIME_LE="+recTimeTo+"&Q_t.RESULT_LIKE="+RESULT_LIKE+"&Q_t.SN_LIKE="+exeId+"&Q_r.DATA_TYPE_EQ="+dataType);
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
	        dataGridId:"swbReturnInfoStatisGrid",
	        tplKey:"swbReturnInfob",
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
					elem : "#SysLogL.SWBRETURNINFO_TIME_BEGIN",
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
					elem : "#SysLogL.SWBRETURNINFO_TIME_END",
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
						elem : "#SysLogL.SWBRETURNINFOBJ_TIME_BEGIN",
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
						elem : "#SysLogL.SWBRETURNINFOBJ_TIME_END",
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
			
	function resetSwbReturnInfoForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SwbReturnInfoForm');
	}

	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#swbReturnInfoStatisGrid');  
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
	$('#swbReturnInfoStatisGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="swbReturnInfoStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
						         onclick="exportSwbReturnInfoExcel();">导出查询数据</a> 
								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SwbReturnInfoForm" id="SwbReturnInfoForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">申报号：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_t.SN_LIKE" />
							</td>
							<td style="width:80px;text-align:right;">处理结果：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_t.RESULT_LIKE" />
							</td>
							<td style="width:80px;text-align:right;">数据类型：</td>
							<td style="width:128px;">
								<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_r.DATA_TYPE_EQ" />
							</td>
							<td style="width:80px;text-align:right;">发送时间从：</td>
							<td style="width:90px;"><input type="text"
								style="width:78px;float:left;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFO_TIME_BEGIN" name="Q_T.SEND_TIME_>=" /></td>
							<td style="width:125px;">到<input type="text"
								style="width:78px;float:right;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFO_TIME_END" name="Q_T.SEND_TIME_<=" /></td>								
							<td colspan="2"></td>
							<td style="width:80px;text-align:right;">接收时间从：</td>
							<td style="width:90px;"><input type="text"
								style="width:78px;float:left;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFOBJ_TIME_BEGIN" name="Q_T.REC_TIME_>=" /></td>
							<td style="width:125px;">到<input type="text"
								style="width:78px;float:right;" class="laydate-icon"
								id="SysLogL.SWBRETURNINFOBJ_TIME_END" name="Q_T.REC_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('swbReturnInfoStatisToolBar','swbReturnInfoStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetSwbReturnInfoForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"  striped="true"
			id="swbReturnInfoStatisGrid" nowrap="false"
			toolbar="#swbReturnInfoStatisToolBar" method="post" idfield="UNID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="swbDataController.do?swbReturnInfoData">
			<thead>
				<tr>
					<th data-options="field:'UNID',align:'left'" width="15%">ID</th>
					<th data-options="field:'REC_TIME',align:'left'" width="15%" >接收时间</th>
					<th data-options="field:'CASEUNID',align:'left'" width="15%" >xml唯一id</th>
					<th data-options="field:'SN',align:'left'" width="12%" >申报号</th>
					<th data-options="field:'SEND_TIME',align:'left'" width="15%" >发送时间</th>
					<th data-options="field:'RESULT',align:'left'" width="30%" >处理结果</th>
					<th data-options="field:'DATA_TYPE',align:'left'" width="7%" >数据类型</th>
					<th data-options="field:'OPER_TIME',align:'left'" width="15%" >本地报送时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>