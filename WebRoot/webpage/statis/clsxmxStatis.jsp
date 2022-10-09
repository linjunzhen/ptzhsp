<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.CLSXMX_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.OPER_TIME_>=']").val();
						var endTime = $("input[name='Q_T.OPER_TIME_<=']").val();
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
				var end1 = {
					elem : "#SysLogL.CLSXMX_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.OPER_TIME_>=']").val();
						var endTime = $("input[name='Q_T.OPER_TIME_<=']").val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.OPER_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
			
	function showSelectDeparts(){
		var departId = $("input[name='Q_T.DEPART_ID_=']").val();
		$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
			title : "组织机构选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					$("input[name='Q_T.DEPART_ID_=']").val(selectDepInfo.departIds);
					$("input[name='DEPT_NAME']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
	function resetClsxmxForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('ClsxmxForm');
	}

	function rowformater(value,row,index){
		if(value=='3'){
			return '领照';
		}else if(value=='4'){
			return '咨询';
		}else if(value=='5'){
			return '其他';
		}
	}
	
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#clsxmxStatisGrid');  
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
	$('#clsxmxStatisGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="clsxmxStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<!-- <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
						         <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true" 
						         onclick="exportSpsxmxExcel();">导出数据</a> 
							</div>
						</div>
					</div>
				</div>
			</div> -->
			<form action="#" name="ClsxmxForm">
				<input type="hidden" id="DEPARTID" name="Q_T.DEPART_ID_=" value="">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">查询部门：</td>							
							<td style="width:135px;">							
							<input type="text"  placeholder="请选择部门"
								style="width:128px;float:left;" class="eve-input" name="DEPT_NAME" onclick="showSelectDeparts();"/>
							</td>
							<td style="width:68px;text-align:right;">排队号：</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.LINE_NO_=" /></td>
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.CLSXMX_TIME_BEGIN" name="Q_T.OPER_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.CLSXMX_TIME_END" name="Q_T.OPER_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('clsxmxStatisToolBar','clsxmxStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetClsxmxForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="clsxmxStatisGrid" fitcolumns="false" toolbar="#clsxmxStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?clsxmxData">
			<thead>
				<tr>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >部门</th>
					<th data-options="field:'CUR_WIN',align:'left'" width="10%" >窗口号</th>
					<th data-options="field:'LINE_NO',align:'left'" width="10%" >排队号</th>
					<th data-options="field:'LINE_NAME',align:'left'" width="10%" >联系人</th>
					<th data-options="field:'OPER_REMARK_SQR',align:'left'" width="15%" >申请姓名／单位</th>
					<th data-options="field:'CALL_STATUS',align:'left',formatter:rowformater" width="15%" >处理类型</th>
					<th data-options="field:'OPER_TIME',align:'left'" width="15%" >受理时间</th>
					<th data-options="field:'OPERATOR',align:'left'" width="10%" >经办人</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>