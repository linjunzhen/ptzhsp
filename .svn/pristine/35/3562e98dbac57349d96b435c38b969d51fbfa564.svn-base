<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style>
<script type="text/javascript">
  function formatHandleDesc(val,row){
	  if(val){
		  val=val.replace(/\r\n/ig,"<br/>"); 
		  return "<font color='black'><b>"+val+"</b></font>";
	  }
  }
  
  function formatIsOverTime(val,row){
	  //alert(row.TASK_NODENAME);
	  if( row.TASK_NODENAME == "汇总 意见"
		||row.TASK_NODENAME == "《一阶段施工图》联合审查申请"
		||row.TASK_NODENAME == "预 审"
		||row.TASK_NODENAME == "汇总意见"
		||row.TASK_NODENAME == "填写公示材料"
		||row.TASK_NODENAME == "区项目投资处领导审核"
		||row.TASK_NODENAME == "区审批局领导签发"
		||row.TASK_NODENAME == "网站公示"
		||row.TASK_NODENAME == "填写公示结果"
		||row.TASK_NODENAME == "拟批复《施工图联合审查意见书》"
		||row.TASK_NODENAME == "区项目投资处领导审批"
		||row.TASK_NODENAME == "区审批局领导审签"
		||row.TASK_NODENAME == "区管委会领导签发"
		||row.TASK_NODENAME == "联合审查申请"
		||row.TASK_NODENAME == "综合（专家）评审意见"
		||row.TASK_NODENAME == "《一阶段施工图》（修编版）审查申请"){
		  return "<b>无限制</b>";
	  } else if(val=="-1"){
		  return "<font color='green'><b>未超时</b></font>";
	  } else{
		  return "<font color='red'><b>已超时</b></font>";
	  }
  }

  function formatTaskStatus(val,row){
	  var shzt = "";
	  if(val=="1"){
		  shzt = "<font color='green'><b>正在审核</b></font>";
	  }else if(val=="2"){
		  shzt = "<font color='blue'><b>已审核</b></font>";
	  }else if(val=="3"){
		  shzt = "<font color='red'><b>退回</b></font>";
	  }else if(row.TASK_NODENAME == "开始" && val == "-1" ){
		  shzt = "<font color='purple'><b>退回补件</b></font>";
	  }else if(val=="4"){
		  shzt = "<font color='purple'><b>转发</b></font>";
	  }else if(val=="5"){
		  shzt = "<font color='purple'><b>委托</b></font>";
	  }else if(val=="6"){
		  shzt = "<font color='black'><b>结束流程</b></font>";
	  }else if(val=="-1"){
		  shzt = "<font color='purple'><b>挂起</b></font>";
	  }else if(val=="7"){
		  return "<font color='purple'><b>申请人撤回</b></font>";
	  }
	  if(1 == row.IS_HANG && row.TASK_NODENAME != "开始"){
		  shzt = shzt + "<a "+
	      "class='easyui-linkbutton' reskey='SHOW_HangExplain'"+
	      "iconcls='l-btn-icon icon-eye ' plain='true'"+
	      "onclick='showHangExplain(\""+row.TASK_ID+"\");'><img src=\"<%=basePath%>plug-in/easyui-1.4/themes/icons/eye.png\" ></a>";
	  }
	  return shzt;
  }
	
  function showHangExplain(TASK_ID){
		$.dialog.open("flowTaskController.do?flowTaskExplain&selectTaskIds="+TASK_ID, {
			title : "挂起说明",
			width : "720px",
			height : "300px",
			lock : true,
			resize : false
		}, false);
  }
  function formatStepSeq(val,row){
	  return "<b>"+val+"</b>";
  }
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="false" pagination="false"
			id="FlowTaskGrid" fitcolumns="true" nowrap="false" 
			method="post" idfield="TASK_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="flowTaskController.do?datagrid&isFiled=${isFiled}&Q_T.EXE_ID_EQ=${exeId}&Q_T.STEP_SEQ_NEQ=0&start=0&limit=100">
			<thead>
				<tr>
					<th data-options="field:'STEP_SEQ',align:'center'" width="100" formatter="formatStepSeq">步骤序号</th>
					<th data-options="field:'TASK_NODENAME',align:'left'"  width="100">环节名称</th>
					<th data-options="field:'TEAM_NAMES',align:'left'"  width="150">审核人</th>
					<th data-options="field:'ASSIGNER_DEPNAMES',align:'left'"  width="150">审批单位</th>
					<th data-options="field:'TASK_STATUS',align:'left'"  width="100" formatter="formatTaskStatus">办理状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'"  width="150">提交时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="150">结束时间</th>
					
					<th data-options="field:'WORK_HOURS',align:'left'" width="100">工时统计</th>
					
					<th data-options="field:'EXE_HANDLEDESC',align:'left'" width="250" formatter="formatHandleDesc">执行动作描述</th>
					<th data-options="field:'HANDLE_OPINION',align:'left'" width="200">审批意见</th>
					<th data-options="field:'TASK_DEADTIME',align:'left'" width="100">办理截止期限</th>
					<th data-options="field:'WORKDAY_LIMIT',align:'left'" width="100">办理截止期限</th>
					<th data-options="field:'IS_OVERTIME',align:'left'" width="100" formatter="formatIsOverTime">是否超时</th>
<%--					<th data-options="field:'IS_HANG',align:'left'" width="150">结束时间</th>--%>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
