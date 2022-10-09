<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	  
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#PrintRecordGrid');  
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
	$('#PrintRecordGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
	$(document).ready(
			function() {
				var start1 = {
					elem : "#printRecord.OPERATE_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.PRINT_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.PRINT_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.PRINT_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#printRecord.OPERATE_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.PRINT_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.PRINT_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.PRINT_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
			
	function printGridRecord(){
        var $dataGrid = $("#PrintRecordGrid");
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择您要操作的记录!",
                icon:"warning",
                ok: true
            });
            return;
        }else if(rowsData.length > 1){
       	 	art.dialog({
                content: "只能选择一条记录!",
                icon:"warning",
                ok: true
            });
            return;
        }else{
	        var dateStr = "";
			var OPER_REMARK_SQR=rowsData[0].OPER_REMARK_SQR;
			var TemplatePath = '20170321110841.doc';
			var TemplateName = '窗口一次性告知单';
			var itemCode = rowsData[0].ITEM_CODE;
			dateStr +="&OPER_REMARK_SQR="+OPER_REMARK_SQR;
			dateStr +="&TemplatePath="+TemplatePath;
			dateStr +="&TemplateName="+TemplateName;
			dateStr +="&itemCode="+itemCode;
			$.dialog.open(encodeURI("printAttachController.do?printBack"+dateStr), {
	                title : "打印模版",
	                width: "100%",
	                height: "100%",
	                left: "0%",
	                top: "0%",
	                fixed: true,
	                lock : true,
	                resize : false
	        }, false);
        }
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="printRecordToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-print" plain="true"
								onclick="printGridRecord();">打印</a>
						</div>
					</div>
				</div>
			</div>	
			<form action="#" name="printRecordForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:128px;" class="laydate-icon"
								id="printRecord.OPERATE_TIME_BEGIN" name="Q_T.PRINT_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:128px;" class="laydate-icon"
								id="printRecord.OPERATE_TIME_END" name="Q_T.PRINT_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">排队号</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.LINE_NO_=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('printRecordToolbar','PrintRecordGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('printRecordForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="PrintRecordGrid" nowrap="false"
			toolbar="#printRecordToolbar" method="post" idfield="PRINT_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="newCallController.do?printRecordGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'PRINT_ID',hidden:true">PRINT_ID</th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'ITEM_CODE',hidden:true">ITEM_CODE</th>
					<th data-options="field:'OPER_REMARK_SQR',hidden:true">OPER_REMARK_SQR</th>
					<th data-options="field:'LINE_NO',align:'center'" width="15%">排队号</th>
					<th data-options="field:'FULLNAME',align:'center'" width="15%">办理人员</th>
					<th data-options="field:'WIN_NO',align:'center'" width="10%">窗口号</th>
					<th data-options="field:'BUSINESS_NAME',align:'center'" width="25%">办理业务</th>
					<th data-options="field:'PRINT_TIME',align:'center'" width="32%">打印时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>