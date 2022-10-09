<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	
	function formatTaskStatus(val,row){
		//获取用户撤办状态
		var revokeStatus = row.REVOKE_STATUS;
		if(revokeStatus==0){
			 return "<font color='#ff4b4b'><b>用户申请撤办</b></font>";
		}
		if(val=="1"){
			return "<font color='#0368ff'><b>正在审核</b></font>";
		}else if(val=="2"){
			return "<font color='blue'><b>已审核</b></font>";
		}else if(val=="3"){
			return "<font color='#ff4b4b'><b>退回</b></font>";
		}else if(row.TASK_NODENAME == "开始" && val == "-1" ){
			return "<font color='#8c97cb'><b>退回补件</b></font>";
		}else if(val=="4"){
			return "<font color='#8c97cb'><b>转发</b></font>";
		}else if(val=="5"){
			return "<font color='#8c97cb'><b>委托</b></font>";
		}else if(val=="6"){
			return "<font color='black'><b>结束流程</b></font>";
		}else if(val=="-1"){
			return "<font color='#8c97cb'><b>挂起</b></font>";
		}
	}
	
	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//获取流程任务ID
		var taskId = row.TASK_ID;
		//获取流程任务状态
		var taskStatus = row.TASK_STATUS;
		var href = "<a href='";
		if(taskStatus!="1"){
			href += "executionController.do?goDetail&exeId="+exeId+"&taskId="+taskId+"'  target='_blank'";
		}else{
			href += "javascript:void(0)' onclick=\"isRevoke('"+exeId+"','"+taskId+"');\"";
		}
		href += "><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	function isRevoke(exeId,taskId){
		$.post("userInfoController/isRevoke.do",{
			exeId:exeId
		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success){
				sqcb(exeId,taskId)
			}else{
				toPostUrl("executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId);
			}
		});
	}
	function toPostUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		$("#YctbNeedMeHandleJBToolbar").append(ssoForm);
		ssoForm.submit();		
	}
	function sqcb(exeId,taskId){
		$.dialog.open("executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId, {
			title : "撤办审核",
			width : "600px",
			height : "240px",
			lock : true,
			resize : false,
			close: function(){
				AppUtil.gridDoSearch('YctbNeedMeHandleJBToolbar','YctbNeedMeHandleJBGrid');
			}
		}, false);
	}
	function formatLeftDay(val,row){
		if(row.TASK_NODENAME == "开始" && row.TASK_STATUS == "-1"){
			return "<font color='#ff4b4b'><b>补件中</b></font>";
		}else if(row.LEFTMINUTE>10){
			return "<font color='#0368ff'><b>剩余"+row.LEFTMINUTE+"分钟</font>";
		}else if(row.LEFTMINUTE>5){
			return "<font color='#f3d754'><b>剩余"+row.LEFTMINUTE+"分钟</font>";
		}else if(row.LEFTMINUTE>3){
			return "<font color='#fa5800'><b>剩余"+row.LEFTMINUTE+"分钟</font>";
		}else if(row.LEFTMINUTE>0){
			return "<font color='#ff4b4b'><b>剩余"+row.LEFTMINUTE+"分钟</font>";
		}else if(row.LEFTMINUTE==0){
			return "<font color='#ff4b4b'><b>剩余不满1分钟</font>";
		}else{
			return "<font color='#ff4b4b'><b>超期"+Math.abs(row.LEFTMINUTE)+"分钟</font>";
		}
	}


	$(document).ready(function() {
		var start1 = {
			elem : "#YctbNeedMeHandleJBT.CREATE_TIME_BEGIN",
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
		var end1 = {
			elem : "#YctbNeedMeHandleJBT.CREATE_TIME_END",
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
		laydate(start1);
		laydate(end1);
	});
	
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="YctbNeedMeHandleJBToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;一窗通办即办预警列表
							</div>
							 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="YctbNeedMeHandleJBForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">发起人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.CREATOR_NAME_LIKE" /></td>
						    <td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_E.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">证件号码</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_E.SQRSFZ_LIKE" />
							</td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="YctbNeedMeHandleJBT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="YctbNeedMeHandleJBT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.SQRMC_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('YctbNeedMeHandleJBToolbar','YctbNeedMeHandleJBGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('YctbNeedMeHandleJBForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="YctbNeedMeHandleJBGrid" fitcolumns="false" toolbar="#YctbNeedMeHandleJBToolbar"
			method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="flowTaskController.do?needMeHandle&isHaveHandup=true&Q_E.ACCEPTWAY_EQ=1&Q_S.SXLX_EQ=1">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'TASK_ID',hidden:true" >TASK_ID</th>
					<th data-options="field:'LEFTMINUTE',hidden:true" >LEFTMINUTE</th>
					<th data-options="field:'TASK_STATUS',align:'left'" width="12%" formatter="formatTaskStatus">任务状态</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'ZBCS',align:'left'" width="10%">业务类别</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="10%">环节名称</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="10%">发起人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="8%">经办人</th>
					<th data-options="field:'LEFT_WORKDAY',align:'left'" width="10%" formatter="formatLeftDay">办理时限</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
