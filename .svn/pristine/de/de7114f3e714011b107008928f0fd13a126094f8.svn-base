<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

    function DataRefresh(){
    	$.dialog.open("statisticsController.do?tzxmDataRefresh", {
    		title : "同步数据",
            width:"400px",
            lock: true,
            resize:false,
            height:"300px",
    	}, false);
    }

    function exportTzxmYqmxExcel(){
    	var beginDate = $("input[name='Q_t.END_TIME_>=']").val();
    	var endDate = $("input[name='Q_t.END_TIME_<=']").val();
    	window.location.href = encodeURI("statisticsController.do?exportTzxmYqbjmxExcel&Q_t.END_TIME_>="+beginDate+" 00:00:00&Q_t.END_TIME_<="+endDate+" 23:59:59&beginDate="+beginDate+"&endDate="+endDate);
    }
    
	function formatSubject(val,row){
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId="+exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	function formatOverdueDate(val,row){
		var li = "<li >";
		li+="<font style=\"float: right;margin: 0 20px;color: #ff4b4b\">";
		if(val==0){
			li+="";
		}else if(val==-1){
			li+="";
		}else if(val!=-2){
			li+=("超出"+val+"个工作日");
		}
		li+="</font>";
		li+="</li>";
		return li;
	}

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.tzxmbjmx_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_t.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_t.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_t.END_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogL.tzxmbjmx_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_t.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_t.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_t.END_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
			
	
	function resetSpsxmxForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SpsxmxForm');
	}
	
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#tzxmbjmxStatisGrid');  
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
	$('#tzxmbjmxStatisGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="tzxmbjmxStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							     <a href="#" class="easyui-linkbutton" iconcls="eicon-refresh" plain="true" 
						         onclick="DataRefresh();">数据同步</a> 
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
						         onclick="exportTzxmYqmxExcel();">导出数据</a>
								<!-- <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a> -->								 
							
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SpsxmxForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">办件名称</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_t.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.tzxmbjmx_TIME_BEGIN" name="Q_t.END_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.tzxmbjmx_TIME_END" name="Q_t.END_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('tzxmbjmxStatisToolBar','tzxmbjmxStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetSpsxmxForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="tzxmbjmxStatisGrid" fitcolumns="false" toolbar="#tzxmbjmxStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?tzxmbjmxData">
			<thead>
				<tr>
					<th data-options="field:'EXE_ID',align:'left'" width="12%" >申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="30%" formatter="formatSubject">办件名称</th>
<%--					<th data-options="field:'TASK_NODENAME',align:'left'" width="150" >审核环节</th>--%>
					<th data-options="field:'ITEM_NAME',align:'left'" width="25%" >审批服务项目名称</th>
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%" >受理时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%" >办理时间</th>
<%--					<th data-options="field:'DEAD_TIME',align:'left'" width="150" >办理期限</th>--%>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%" >承诺时限</th>
					<th data-options="field:'OVERDUE_WORKDAY',align:'left'" width="10%" formatter="formatOverdueDate">超期天数</th>
					<th data-options="field:'ASSIGNER_NAME',align:'left'" width="10%" >经办人</th>
					<th data-options="field:'CREATOR_PHONE',align:'left'" width="10%" >联系电话</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>