<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	  
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#QueuingForwardGrid');  
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
	$('#QueuingForwardGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
	$(document).ready(
			function() {
				var start1 = {
					elem : "#forward.OPERATE_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.FORWARD_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.FORWARD_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.FORWARD_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#forward.OPERATE_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.FORWARD_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.FORWARD_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.FORWARD_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
</script>
<div class="easyui-layout eui-jh-box eui-headborder" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="QueuingForwardToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
			</div>	
			<form action="#" name="QueuingForwardForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:128px;" class="laydate-icon"
								id="forward.OPERATE_TIME_BEGIN" name="Q_T.FORWARD_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:128px;" class="laydate-icon"
								id="forward.OPERATE_TIME_END" name="Q_T.FORWARD_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">排队号</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.LINE_NO_=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('QueuingForwardToolbar','QueuingForwardGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('QueuingForwardForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="QueuingForwardGrid" nowrap="false"
			toolbar="#QueuingForwardToolbar" method="post" idfield="LOG_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="newCallController.do?queuingForwardGrid">
			<thead>
				<tr>
					<th data-options="field:'LOG_ID',hidden:true" rowspan="2">LOG_ID</th>
					<th data-options="field:'RECORD_ID',hidden:true" rowspan="2">RECORD_ID</th>
					<th data-options="field:'LINE_NO',align:'center'" width="10%" rowspan="2">排队号</th>
					<th colspan="3">转发前</th>
					<th colspan="3">转发后</th>
					<th data-options="field:'FORWARD_TIME',align:'center'" width="14%" rowspan="2">操作时间</th>
				</tr>
				<tr>
					<th data-options="field:'FROM_USER',align:'center'" width="10%" >办理人员</th>
					<th data-options="field:'FROM_WIN',align:'center'" width="8%" >窗口号</th>
					<th data-options="field:'FWIN_BUSINESS_NAMES',align:'center'" width="20%" >窗口业务</th>
					<th data-options="field:'TO_USER',align:'center'" width="9%" >办理人员</th>
					<th data-options="field:'TO_WIN',align:'center'" width="8%" >窗口号</th>
					<th data-options="field:'TWIN_BUSINESS_NAMES',align:'center'" width="20%" >窗口业务</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>