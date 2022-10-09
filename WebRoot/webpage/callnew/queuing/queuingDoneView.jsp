<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function formateStatus(value,row,index){
		if(value=='1'){
			return '已受理';
		}else if(value=='2'){
			return '过号';
		}else if(value=='3'){
			return '处理完毕（领照）';
		}else if(value=='4'){
			return '处理完毕（咨询）';
		}else if(value=='5'){
			return '处理完毕（其他）';
		}else if(value=='7'){
			return '取错事项';
		}
	}
	
	function zjformater(value,row,index){
		if(row.LINE_ZJLX=="SF"){
			return value;
		}else{
			return value+"<br>("+row.ZJLX+")";
		}
	}
	
	function evaluateformater(value,row,index){
		if(value){
			return "已评价";
		}else{
			return "未评价";
		}
	}
	  
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#QueuingDoneGrid');  
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
	$('#QueuingDoneGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
	$(document).ready(
			function() {
				var start1 = {
					elem : "#queuing.OPERATE_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.OPER_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.OPER_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.OPER_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#queuing.OPERATE_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.OPER_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.OPER_TIME_<=']")
								.val();
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
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="QueuingDoneToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
			</div>	
			<form action="#" name="QueuingDoneForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">窗口业务类</td>
							<td style="width:135px;padding-left:4px;">
								<input class="easyui-combobox"
								style="width:200px;" name="Q_t.BUSINESS_CODE_="
								data-options="url:'callSetController.do?loadBusiness&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'BUSINESS_CODE',textField:'BUSINESS_NAME',panelWidth: 200,panelHeight: '300' " />
								
							</td>
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float: left;" class="laydate-icon"
								id="queuing.OPERATE_TIME_BEGIN" name="Q_T.OPER_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float: left;" class="laydate-icon"
								id="queuing.OPERATE_TIME_END" name="Q_T.OPER_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">证件号码</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.LINE_CARDNO_=" /></td>
							
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">窗口号</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:200px;" name="Q_T.CUR_WIN_="
								data-options="
	url:'callSetController.do?loadWinNo&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'VALUE',textField:'TEXT',panelWidth: 200,panelHeight: '240' " />
							</td>
							<td style="width:68px;text-align:right;">排队号</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.LINE_NO_=" /></td>
							<td style="width:68px;text-align:right;">办理人员</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.OPERATOR_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">办理状态</td>
							<td style="width:135px;padding-left:4px;">
								<input class="easyui-combobox"
								style="width:128px;" name="Q_T.CALL_STATUS_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '',value: '请选择'},
									            {label: '1', value: '已受理'},{label: '2',value: '过号'},
									            {label: '3', value: '处理完毕（领照）'},{label: '4',value: '处理完毕（咨询）'},
									            {label: '5', value: '处理完毕（其他）'}],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">评价与否</td>
							<td style="width:135px;padding-left:4px;">
								<input class="easyui-combobox"
								style="width:200px;" name="Q_T.EVELUATE_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '',value: '请选择'},
									            {label: '1', value: '是'},{label: '0',value: '否'}],panelWidth: 200,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('QueuingDoneToolbar','QueuingDoneGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('QueuingDoneForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="QueuingDoneGrid" nowrap="false"
			toolbar="#QueuingDoneToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="newCallController.do?queuingDoneGrid">
			<thead>
				<tr>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'LINE_NO',align:'center'" width="10%">排队号</th>
					<th data-options="field:'LINE_NAME',align:'center'" width="10%">姓名</th>
					<th data-options="field:'LINE_ZJLX',hidden:true">证件类型</th>
					<!-- <th data-options="field:'ZJLX',align:'left'" width="15%" >证件类型</th> -->
					<th data-options="field:'LINE_CARDNO',align:'center',formatter:zjformater" width="18%" >证件号码</th>
					<th data-options="field:'CREATE_TIME',align:'center'" width="15%" >取号时间</th>
					<th data-options="field:'OPERATOR_NAME',align:'center'" width="10%" >办理人</th>
					<th data-options="field:'OPER_TIME',align:'center'" width="15%" >办理时间</th>
					<th data-options="field:'CALL_STATUS',align:'center',formatter:formateStatus" width="12%" >状态</th>
					<th data-options="field:'EVALUATE',align:'center',formatter:evaluateformater" width="10%" >评价</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>