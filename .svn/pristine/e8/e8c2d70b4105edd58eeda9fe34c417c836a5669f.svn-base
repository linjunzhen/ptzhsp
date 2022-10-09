<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

    function exportSpsxmxExcel(){
    	var beginDate = $("input[name='Q_e.END_TIME_>=']").val();
    	var endDate = $("input[name='Q_e.END_TIME_<=']").val();
		var DEPTNAME = $("input[name='DEPT_NAME']").val();
		var DEPART_ID = $("input[name='Q_T.DEPART_ID_=']").val();
		var SUBJECT = $("input[name='Q_e.SUBJECT_LIKE']").val();
		var RUN_STATUS = $("input[name='Q_e.RUN_STATUS_=']").val();
		var url = "statisticsController.do?exportYqbjmxExcel&beginDate="+beginDate+"&endDate="+endDate+"&DEPTNAME="+DEPTNAME+"&Q_T.DEPART_ID_EQ="+DEPART_ID+"&Q_e.SUBJECT_LIKE="+SUBJECT+"&Q_e.RUN_STATUS_EQ="+RUN_STATUS;
		if(null!=beginDate&&''!=beginDate){
			url+="&Q_e.END_TIME_GE="+beginDate+" 00:00:00";
		}
		if(null!=endDate&&''!=endDate){
			url+="&Q_e.END_TIME_LE="+endDate+" 23:59:59";
		}
    	window.location.href = encodeURI(url);
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
		li+="<font style=\"color: #ff4b4b\">";
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
					elem : "#SysLogL.YQBJMX_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_e.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_e.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_e.END_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogL.YQBJMX_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_e.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_e.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_e.END_TIME_<=']").val("");
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
	function resetSpsxmxForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SpsxmxForm');
	}
	
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#yqbjmxStatisGrid');  
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
	$('#yqbjmxStatisGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
	
	//空数据，横向滚动
	$('#yqbjmxStatisGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="yqbjmxStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
						         onclick="exportSpsxmxExcel();">导出数据</a>
								<!-- <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a> -->								 
							
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SpsxmxForm">
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
								
							<td style="width:68px;text-align:right;">办件名称</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_e.SUBJECT_LIKE" />
							</td>
							<td style="width:98px;text-align:right;">办结日期从：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.YQBJMX_TIME_BEGIN" name="Q_e.END_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">至：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.YQBJMX_TIME_END" name="Q_e.END_TIME_<=" /></td>
							                         <td style="width:68px;text-align:right;">流程状态</td>
                            <td style="width:135px;"><input class="easyui-combobox"
                                style="width:128px;" name="Q_e.RUN_STATUS_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>	
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('yqbjmxStatisToolBar','yqbjmxStatisGrid')" />
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
			id="yqbjmxStatisGrid" fitcolumns="false" toolbar="#yqbjmxStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?yqbjmxData">
			<thead>
				<tr>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >部门</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%" >申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="30%"  formatter="formatSubject">办件名称</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="25%" >审批服务项目名称</th>
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%" >受理时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%" >办结时间</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%" >承诺时限</th>
					<th data-options="field:'OVERDUE_WORKDAY',align:'left'" width="10%" formatter="formatOverdueDate">超期天数</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="10%" >经办人</th>
					<th data-options="field:'CREATOR_PHONE',align:'left'" width="10%" >联系电话</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>