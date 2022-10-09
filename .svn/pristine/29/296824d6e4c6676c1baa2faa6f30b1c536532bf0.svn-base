<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>

<script type="text/javascript">

    function exportCjybExcel(){
    	var beginDate = $("#CjybForm input[name='Q_T.CREATE_TIME_>=']").val();
    	var endDate = $("#CjybForm input[name='Q_T.CREATE_TIME_<=']").val();
		var SUBJECT = $("#CjybForm input[name='Q_T.SUBJECT_LIKE']").val();
		var runStatus = $("#CjybForm input[name='Q_T.RUN_STATUS_=']").val();
		var url =  "statisticsController.do?exportCjybExcel&beginDate="+beginDate+"&endDate="+endDate+"&Q_T.SUBJECT_LIKE="+SUBJECT+"&Q_T.RUN_STATUS_EQ="+runStatus;
		if(null!=beginDate&&''!=beginDate){
			url+="&Q_T.CREATE_TIME_GE="+beginDate+" 00:00:00";
		}
		if(null!=endDate&&''!=endDate){
			url+="&Q_T.CREATE_TIME_LE="+endDate+" 23:59:59";
		}
		var encodeUrl = encodeURI(url);
    	window.location.href = encodeUrl;
		
    }

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.CYJB_TIME_BEGIN",
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
					elem : "#SysLogL.CYJB_TIME_END",
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

			});
			

	function resetCjybForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('CjybForm');
	}
		
	function formatCyjbSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//当前环节
		var curTache=row.CUR_STEPNAMES;
		//获取流程申报号
		var defkey= row.DEF_KEY;
		/*获取创建时间*/
		var createTime = row.CREATE_TIME;
		var timeStamp = new Date(createTime).getTime();
		/*获取一年前的时间戳*/
		var timeStampS = new Date().getTime() - 365*24*3600*1000;
		var dicstate= row.DIC_STATE;
		var href = "<a href='";
		if(runStatus=="0"){
			if (timeStamp < timeStampS) {
				href += "executionController.do?goStart&exeId="+exeId + "&isFiled=1";
			} else {
				href += "executionController.do?goStart&exeId=" + exeId;
			}
		}else if(runStatus=="1"&&curTache=="并联审查"&&dicstate=="1"){
			if (timeStamp < timeStampS) {
				href += "executionController.do?goDetailGover&exeId="+exeId + "&isFiled=1";
			} else {
				href += "executionController.do?goDetailGover&exeId=" + exeId;
			}
		}else{
			if (timeStamp < timeStampS) {
				href += "executionController.do?goDetail&exeId="+exeId + "&isFiled=1";
			} else {
				href += "executionController.do?goDetail&exeId=" + exeId;
			}
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="cjybStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
					         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
					         onclick="exportCjybExcel();">导出数据</a> 
							 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="CjybForm" id="CjybForm">
				<input type="hidden" id="DEPARTID" name="Q_T.DEPART_ID_=" value="">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">流程标题：</td>							
							<td style="width:135px;">							
							<input type="text" 
								style="width:128px;float:left;" class="eve-input" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.CYJB_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.CYJB_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">流程状态：</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RUN_STATUS_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />

							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('cjybStatisToolBar','cjybStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetCjybForm()" /></td>
							</td>	
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="cjybStatisGrid" fitcolumns="true" toolbar="#cjybStatisToolBar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?cyjbData">
			<thead>
				<tr>
					<th data-options="field:'SUBJECT',align:'left'" width="37%"  formatter="formatCyjbSubject">流程标题</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="11%" formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%" >申请时间</th>
					<th data-options="field:'CYJB_SPJE',align:'left'" width="11%" >审批金额</th>
					<th data-options="field:'CYJB_SPDEPT_NAME',align:'left'" width="18%" >审核部门</th>
					<th data-options="field:'CYJB_CZSJ',align:'left'" width="8%" >出账时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>