<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	/**
	 * 删除流程实例列表记录
	 */
	function deleteNeedMeHandleGridRecord() {
		AppUtil.deleteDataGridRecord("executionController.do?multiDel",
				"NeedMeHandleGrid");
	};
	function restartFlowTaskExplain(){
		var $dataGrid = $("#NeedMeHandleGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var isAllowStart = true;
			var selectTaskIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				var TASK_STATUS = rowsData[i].TASK_STATUS;
				var TASK_NODENAME = rowsData[i].TASK_NODENAME;
				var TASK_ID  = rowsData[i].TASK_ID;
				if(TASK_STATUS!="-1"&&TASK_STATUS!="3"){
					isAllowStart = false;
					break;
				}
				if(i>0){
					selectTaskIds+=",";
				}
				selectTaskIds+=TASK_ID;
			}
			if(!isAllowStart){
				art.dialog({
					content: "只能选择被挂起或完成补件的流程任务进行重启!",
					icon:"warning",
				    ok: true
				});
			}else if(TASK_STATUS == -1 && TASK_NODENAME == "开始"){
				AppUtil.operateDataGridRecord("flowTaskController.do?reStart",{selectTaskIds:selectTaskIds},"NeedMeHandleGrid");
			}else{
				$.dialog.open("flowTaskController.do?restartFlowTaskExplain&selectTaskIds="+selectTaskIds, {
					title : "重启流程任务",
					width : "450px",
					height : "300px",
					lock : true,
					resize : false
				}, false);
			}
		}
	}
	function restartFlowTask(){
		var $dataGrid = $("#NeedMeHandleGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var isAllowStart = true;
			var selectTaskIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				var TASK_STATUS = rowsData[i].TASK_STATUS;
				var TASK_ID  = rowsData[i].TASK_ID;
				if(TASK_STATUS!="-1"){
					isAllowStart = false;
					break;
				}
				if(i>0){
					selectTaskIds+=",";
				}
				selectTaskIds+=TASK_ID;
			}
			if(!isAllowStart){
				art.dialog({
					content: "只能选择被挂起的流程任务进行重启!",
					icon:"warning",
				    ok: true
				});
			}else{
				AppUtil.operateDataGridRecord("flowTaskController.do?reStart",{selectTaskIds:selectTaskIds},"NeedMeHandleGrid");
			}
		}
	}
	/**
	 * 编辑流程实例列表记录
	 */
	function editNeedMeHandleGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("NeedMeHandleGrid");
		if (entityId) {
			showExecutionWindow(entityId);
		}
	}
	
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
	
	function formatPayStatus(val,row){
		//获取用户撤办状态
		var revokeStatus = row.REVOKE_STATUS;
		if(val=="0"){
			return "<font color='#0368ff'><b>待通知</b></font>";
		}else if(val=="2"){
			return "<font color='green'><b>已通知缴费</b></font>";
		}else if(val=="1"){
			return "<font color='black'><b>已缴费</b></font>";
		}else if(val=="3"){
			return "<font color='red'><b>缴费失败</b></font>";
		}else if(val=="4"){
			return "<font color='red'><b>不确定</b></font>";
		}
	}
	function formatPayStatusNew(val,row){
		//获取用户撤办状态
		var revokeStatus = row.REVOKE_STATUS;
		if(val=="3"){
			return "<font color='#0368ff'><b>已删除</b></font>";
		}else if(val=="0"){
			return "<font color='green'><b>已通知缴费（待缴）</b></font>";
		}else if(val=="1"){
			return "<font color='black'><b>缴费成功</b></font>";
		}else if(val=="2"){
			return "<font color='red'><b>缴费失败</b></font>";
		}else{
			return "<font color='red'><b></b></font>";
		}
	}
	function formatSignStatus(val, row) {
	
		if (val == "0") {
			return "<font color='#0368ff'><b>未通知</b></font>";
		} else if (val == "1") {
			return "<font color='green'><b>已通知</b></font>";
		} else if (val == "2") {
			return "<font color='black'><b>已签署</b></font>";
		} else if (val == "5") {
            return "<font color='red'><b>过期作废</b></font>";
        } else if (val == "7") {
			return "<font color='red'><b>拒签</b></font>";
		} else {
			return "<font color='red'><b></b></font>";
		}
	}
	
	function formatSqfs(val, row) {
		if (val == "1") {
			return "<font>网上申请</font>";
		} else if (val == "2") {
				return "<font>窗口收件</font>";
		} else if (val == "3") {
				return "<font>省网办发起</font>";
		} else if (val == "4") {
           	return "<font>省市联动办件收件</font>";
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
		$("#NeedMeHandleToolbar").append(ssoForm);
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
				AppUtil.gridDoSearch('NeedMeHandleToolbar','NeedMeHandleGrid');
			}
		}, false);
	}
	function formatLeftDay(val,row){
		if(row.TASK_NODENAME == "开始" && row.TASK_STATUS == "-1"){
			return "<font color='#ff4b4b'><b>补件中</b></font>";
		}else if(val=="-1"){
			return "<font color='#ff4b4b'><b>已超期</b></font>";
		}else if(row.IS_IMMEDIATE==1){
			return "<font color='#fa5800'><b>即办</b></font>";
		}else if(val=="0"){
			if(row.IS_IMMEDIATE==2){
				return "<font color='#fa5800'><b>[A]今天截止</b></font>";
			}else{
				return "<font color='#fa5800'><b>今天截止</b></font>";
			}
		}else if(val!="-2"){
			if(row.IS_IMMEDIATE==2){	
				return "<font color='#0368ff'><b>[A]剩余"+val+"个工作日</font>";
			}else{
				return "<font color='#0368ff'><b>剩余"+val+"个工作日</font>";
			}
		}else{
			return "无限制";
		}
	}

	/**
	 * 格式化评价结果
	 * @param val
	 * @param row
	 */
	function formatEvaluate(val,row){
    	var oEvaluate = {'3':'非常满意','2':'满意','1':'一般','0':'不满意','4':'不评价','5':'非常不满意','-1':'未评价'};
    	if(val==null||val==''){//评价结果（3：非常满意，2：满意，1：一般，0：不满意，4：不评价）
    		val = -1;
    	}
    	val = oEvaluate.hasOwnProperty(val)?val:-1;
    	return oEvaluate[val];
	}

	/**
	 * 评价
	 * @returns {null}
	 */
	function onEvaluate(){
		var rows = $("#NeedMeHandleGrid").datagrid("getChecked");
		if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
				ok: true
			});
			return null;
		}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
				ok: true
			});
			return null;
		}else{
			if(isCanEvaluate(rows[0])){
				$.dialog.open("newCallController.do?evaluatePc&EXE_ID=" + rows[0]['EXE_ID'], {
					title : "评价",
					width : "1200px",
					height : "800px",
					lock : true,
					resize : false,
					close: function(){
						AppUtil.gridDoSearch('NeedMeHandleToolbar','NeedMeHandleGrid');
					}
				}, false);
			}
		}
	}

	/**
	 * 判断是否可评价
	 * @param row
	 * @returns {boolean}
	 */
	function isCanEvaluate(row){
		if(row['RECORD_ID']!=null&&row['RECORD_ID']!=''){//判断是否已评价
			art.dialog({
				content: "办件已评价!",
				icon:"warning",
				ok: true
			});
			return false;
		}

		if(row['CREATE_TIME'].indexOf(row['NOW_DATE'])<0){//判断是否为当天的办件
			art.dialog({
				content: "只能评价当天的办件!",
				icon:"warning",
				ok: true
			});
			return false;
		}

		if(row['SQFS']!='2'){//判断是否是窗口收件
			art.dialog({
				content: "非窗口办件不能评价!",
				icon:"warning",
				ok: true
			});
			return false;
		}

		var loginUserInfo = AppUtil.getLoginUserInfo();
		if(row['CREATOR_ID']!=loginUserInfo['userId']){//判断是否是当前登录用户发起的办件
			art.dialog({
				content: "不能评价他人发起的办件!",
				icon:"warning",
				ok: true
			});
			return false;
		}

		return true;
	}

	/**
	 * 显示流程实例信息对话框
	 */
	function showExecutionWindow(entityId) {
		$.dialog.open("executionController.do?info&entityId=" + entityId, {
			title : "流程实例信息",
			width : "600px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	}

	$(document).ready(function() {
		var start1 = {
			elem : "#NeedMeHandleT.CREATE_TIME_BEGIN",
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
			elem : "#NeedMeHandleT.CREATE_TIME_END",
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
	
	function showBjxxGridRecord() {
		var rows = $("#NeedMeHandleGrid").datagrid("getChecked");
        if(rows.length==0){
            art.dialog({
                content: "请至少选择一条记录!",
                icon:"warning",
                ok: true
            });         
            return null;
        }else if(rows.length>1){
            art.dialog({
                content: "只能选择一条被操作的记录!",
                icon:"warning",
                ok: true
            });
            return null;
        }else{
        	//alert("BJXX_ID:"+rows[0].BJXX_ID+",BACKOPINION:"+rows[0].BACKOPINION+",APPLY_STATUS:"+rows[0].APPLY_STATUS)
        	thsm(rows[0].BJXX_ID,rows[0].BACKOPINION,rows[0].APPLY_STATUS);
        }
        
    }
	function thsm(bjid,backinfo,applyStatus){
        if(bjid!=''&&applyStatus=='7'){
            $.dialog.open("webSiteController/thbjxx.do?&BJID="+bjid, {
                title : "退回补件信息",
                width : "900px",
                height : "400px",
                lock : true,
                resize : false
            }, false);
        }else{
        	alert('该待办无补件要求');
        }
    }
    
    function chargeAdvice() {
		var rows = $("#NeedMeHandleGrid").datagrid("getChecked");
		if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
				ok: true
			});
			return null;
		}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
				ok: true
			});
			return null;
		} else if(rows[0].TASK_NODENAME=="开始"
				||rows[0].TASK_NODENAME=="受理"
					||rows[0].TASK_NODENAME=="网上预审"
						||rows[0].TASK_NODENAME=="待受理"){
			art.dialog({
				content: "请先受理!",
				icon:"warning",
				ok: true
			});
			return null;
		} else{
			var exeId = rows[0].EXE_ID;
			if (exeId) {
				$.dialog.open("executionController.do?payListView&exeId=" + exeId, {
					title : "收费清单",
					width : "900px",
					height : "500px",
					lock : true,
					resize : false
				}, false);
			}
		}
	}
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="NeedMeHandleToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;待我审批列表
								<a href="#"
                                class="easyui-linkbutton" reskey="SHOW_Bjxx"
                                iconcls="eicon-eye" plain="true"
                                onclick="showBjxxGridRecord();">查看补件要求</a>
<%--                                <a href="#"--%>
<%--                                class="easyui-linkbutton" --%>
<%--                                iconcls="icon-tick" plain="true"--%>
<%--                                onclick="restartFlowTask();">重启流程任务</a>--%>
                                <a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-play" plain="true"
                                onclick="restartFlowTaskExplain();">重启流程任务</a>
								<%--<a href="#"
								   class="easyui-linkbutton"
								   iconcls="eicon-sticky-note-o" plain="true"
								   onclick="chargeAdvice();">缴费通知</a>--%>
								<a href="#"
								   class="easyui-linkbutton"
								   iconcls="eicon-pencil" plain="true"
								   onclick="onEvaluate();">评价</a>
							</div>
							 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ExecutionForm">
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
								id="NeedMeHandleT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandleT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.SQRMC_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('NeedMeHandleToolbar','NeedMeHandleGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ExecutionForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="NeedMeHandleGrid" fitcolumns="false" toolbar="#NeedMeHandleToolbar"
			method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="flowTaskController.do?needMeHandle&isHaveHandup=true">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
					<th data-options="field:'TASK_STATUS',align:'left'" width="5%" formatter="formatTaskStatus">任务状态</th>
<!-- 					<th data-options="field:'PAY_STATUS',align:'left'" width="5%" formatter="formatPayStatus">缴费状态</th> -->
					<th data-options="field:'PAY_STATUSNEW',align:'left'" width="5%" formatter="formatPayStatusNew">缴费状态</th>
					<th data-options="field:'SIGN_STATUS',align:'left'" width="5%" formatter="formatSignStatus">签署状态</th>
					<th data-options="field:'EXE_ID',align:'left'" width="8%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="7%">环节名称</th>
					<th data-options="field:'SQFS',align:'left'" width="7%" formatter="formatSqfs">申请方式</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="8%">发起人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="8%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="6%">经办人</th>
					<th data-options="field:'LEFT_WORKDAY',align:'left'" width="9%" formatter="formatLeftDay">办理时限</th>
					<th data-options="field:'EVALUATE',align:'left'" width="9%" formatter="formatEvaluate">评价结果</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
