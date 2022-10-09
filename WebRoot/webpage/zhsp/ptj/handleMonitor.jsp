<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	/**
	 * 删除流程实例列表记录
	 */
	function deleteHandleMonitorPtjRecord() {
		AppUtil.deleteDataGridRecord("executionController.do?multiDel",
				"handleMonitorPtjGrid");
	};
	
	/**
	* 终结流程实例
	*/
	function endHandleMonitorPtjRecord(){
		AppUtil.deleteDataGridRecord("executionController.do?multiEnd",
		"handleMonitorPtjGrid");
	};
	/**
	* 更改执行路径
	*/
	function changeFlowBranch(){
		var entityId = AppUtil.getEditDataGridRecord("handleMonitorPtjGrid");
		if (entityId) {
			$.dialog.open("flowTaskController.do?goChange&exeId=" + entityId, {
				title : "更改执行路径",
				width : "800px",
				height : "200px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	/**
	 * 流程任务管理
	 */
	function managerFlowTask() {
		var entityId = AppUtil.getEditDataGridRecord("handleMonitorPtjGrid");
		if (entityId) {
			$.dialog.open("flowTaskController.do?goManager&exeId=" + entityId, {
				title : "实例任务明细",
				width : "550px",
				height : "450px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		if(runStatus=="0"){
			href += "executionController.do?goStart&exeId="+exeId;
		}else{
			href += "executionController.do?goDetail&exeId="+exeId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}

	$(document).ready(function() {
		var handleMonitorPtjStart = {
			elem : "#handleMonitorPtjT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var handleMonitorPtjEnd = {
			elem : "#handleMonitorPtjT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(handleMonitorPtjStart);
		laydate(handleMonitorPtjEnd);
	});
	
	function showSelectDeparts(){
    	var departId = $("input[name='DEPART_ID']").val();
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				$("input[name='Q_tsd.DEPART_ID_=']").val(selectDepInfo.departIds);
        			$("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
    			}
    		}
    	}, false);
    }
	
	/*判断是否超期*/
	function formattersfcq(val,row){
		if(val==1){
			return  "<font color='#0368ff'>未超期</font> ";	
		}else if(val==0){
			return  "<font color='#ff4b4b'>超期</font> ";	
		}
	}
	
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#handleMonitorPtjGrid');  
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
	$('#handleMonitorPtjGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
	
	//空数据，横向滚动
	$('#handleMonitorPtjGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="handleMonitorPtjToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<!-- <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						   <a href="#"
								class="easyui-linkbutton" 
								iconCls="icon-disabled" plain="true"
								onclick="endHandleMonitorPtjRecord();">终结流程实例</a> 
						   <a href="#"
								class="easyui-linkbutton" 
								iconCls="icon-script" plain="true"
								onclick="managerFlowTask();">流程任务管理</a> 
							<a href="#"
								class="easyui-linkbutton" 
								iconCls="icon-arrow-branch" plain="true"
								onclick="changeFlowBranch();">更改执行路径</a> 
						   <a href="#"
								class="easyui-linkbutton" 
								iconCls="icon-note-delete" plain="true"
								onclick="deleteHandleMonitorPtjRecord();">删除流程实例</a>
						</div>
					</div>
				</div>
			</div> -->
			<form action="#" name="HandleMonitorPtjForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SQRMC_LIKE" /></td>
							<td style="width:68px;text-align:right;">经办人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.JBR_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">流程状态</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RUN_STATUS_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
															
						    <!-- <td style="width:68px;text-align:right;">备注信息</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_TBP.REMARK_LIKE" />
							</td> -->
							<td style="width:68px;text-align:right;">办件部门</td>
							<td style="width:135px;">
			    				<input type="hidden" name="Q_tsd.DEPART_ID_=">
								<input class="eve-input" type="text" readonly="readonly" maxlength="20" style="width:128px;" onclick="showSelectDeparts();" name="DEPART_NAME" />
							</td> 
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="handleMonitorPtjT.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="handleMonitorPtjT.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td>
							<td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">证件号码</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SQRSFZ_LIKE" />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('handleMonitorPtjToolbar','handleMonitorPtjGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="$('#handleMonitorPtjToolbar input[name=\'Q_tsd.DEPART_ID_=\']').val('');AppUtil.gridSearchReset('HandleMonitorPtjForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="handleMonitorPtjGrid" fitcolumns="true" toolbar="#handleMonitorPtjToolbar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="serviceHandleController.do?handleMonitor&Q_T.RUN_STATUS_NEQ=0&Q_TWS.SXLX_EQ=2">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="8%">经办人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="10%" formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="10%">当前环节</th>
					<th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">当前环节审核人</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
					<th data-options="field:'RES_TIME',align:'left'" width="10%">剩余时间</th>
					<th data-options="field:'SFCQ',align:'left'" width="10%" formatter="formattersfcq">是否超期</th>
					<th data-options="field:'WORK_HOURS',align:'left'" width="10%">运行时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
