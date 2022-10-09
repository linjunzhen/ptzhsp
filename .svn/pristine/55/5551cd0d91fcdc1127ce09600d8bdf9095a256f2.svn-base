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
	function formatDeptDesc(val,row){
		if(val){
			var varArray = val.split(",");
			var newVar = "";
			var isok = false;
			for(var i=0;i<varArray.length;i++){
				if(varArray[i]=="工商局"||varArray[i]=="质量技术监督局"||varArray[i]=="卫生监督处"){
					isok = true;
				}else{
					newVar +=","+varArray[i];
				}
			}
			if(isok){
				newVar +=",市场监督管理局行政许可处";
			}
			if(newVar!=null && newVar!=''){
				newVar = newVar.substring(1,newVar.length);
			}
			return newVar;
		}
	}
	function formatHandleDesc(val,row){
		if(val){
			val=val.replace(/\r\n/ig,"<br/>"); 
			if(row.TASK_NODENAME == "开始"){
				val=val.replace("已审核","已提交"); 			  
				return "<font color='black'><b>"+val+"</b></font>";
			}else if(row.TASK_NODENAME == "办结"){
				val=val.replace("已审核","已办结"); 			  
				return "<font color='black'><b>"+val+"</b></font>";
			}else if(row.TASK_NODENAME == "窗口办理"){
				val=val.replace("已审核","已受理"); 			  
				return "<font color='black'><b>"+val+"</b></font>";
			}else{			  
				return "<font color='black'><b>"+val+"</b></font>";
			}
		}
	}
  
  function formatIsOverTime(val,row){
	  
	  if(row.TASK_NODENAME == "窗口办理"||row.TASK_NODENAME == "开始"||row.TASK_NODENAME == "预审意见汇总"||row.TASK_NODENAME == "并审汇总"
		||row.TASK_NODENAME == "社保审批"||row.TASK_NODENAME == "地税审批"||row.TASK_NODENAME == "统计审批"||row.TASK_NODENAME == "质监审批"
		||row.TASK_NODENAME == "国税审批"||row.TASK_NODENAME == "商务审批"||row.TASK_NODENAME == "身份认证"
		||row.TASK_NODENAME == "社保预审"||row.TASK_NODENAME == "地税预审"||row.TASK_NODENAME == "统计预审"||row.TASK_NODENAME == "质监预审"
		||row.TASK_NODENAME == "审批"){
		  return "<b>无限制</b>";
	  } else if(row.IS_BGBAZX==-1&&(row.TASK_NODENAME == "工商审批"||row.TASK_NODENAME == "公安审批"||row.TASK_NODENAME == "国税审批"
		||row.TASK_NODENAME == "商务审批"||row.TASK_NODENAME == "办结")){
		  if(row.LEFT_WORKDAY>180){			  
			return "<font color='red'><b>已超时</b></font>";
		  }else{			  
				return "<font color='green'><b>未超时</b></font>";
		  }
	  }else if(row.IS_BGBAZX==1 && (row.TASK_NODENAME == "工商审批"||row.TASK_NODENAME == "办结")){
		 if(row.LEFT_WORKDAY>row.WORKDAYCOUNT){//变更三天超期，备案注销1天超期			  
			return "<font color='red'><b>已超时</b></font>";
		 }else{			  
			return "<font color='green'><b>未超时</b></font>";
		 }
	  }else if(val=="-1"){
		  return "<font color='green'><b>未超时</b></font>";
	  } else{
		  return "<font color='red'><b>已超时</b></font>";
	  }
  }

  function formatTaskStatus(val,row){
	  var shzt = "";
	  if(row.TASK_NODENAME == "窗口办理" && val=="1"){
		  shzt = "<font color='green'><b>待企业提交材料</b></font>";
	  }else if(val=="1"){
		  shzt = "<font color='green'><b>正在审核</b></font>";
	  }else if(row.TASK_NODENAME == "开始" && val=="2"){
		  shzt = "<font color='blue'><b>已提交</b></font>";
	  }else if(row.TASK_NODENAME == "办结" && val=="2"){
		  shzt = "<font color='blue'><b>已办结</b></font>";
	  }else if(row.TASK_NODENAME == "窗口办理" && val=="2"){
		  shzt = "<font color='blue'><b>已受理</b></font>";
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
					
					<th data-options="field:'LEFT_WORKDAY',hidden:true" width="80">LEFT_WORKDAY</th>
					<th data-options="field:'STEP_SEQ',align:'center'" width="100" formatter="formatStepSeq">步骤序号</th>
					<th data-options="field:'TASK_NODENAME',align:'left'"  width="100">环节名称</th>
					<th data-options="field:'TEAM_NAMES',align:'left'"  width="150">受理人</th>
					<th data-options="field:'ASSIGNER_DEPNAMES',align:'left'"  width="150"  formatter="formatDeptDesc">审批单位</th>
					<th data-options="field:'TASK_STATUS',align:'left'"  width="110" formatter="formatTaskStatus">办理状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'"  width="150">提交时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="150">结束时间</th>					
					<th data-options="field:'EXE_HANDLEDESC',align:'left'" width="250" formatter="formatHandleDesc">执行动作描述</th>
					<th data-options="field:'HANDLE_OPINION',align:'left'" width="200">审批意见</th>
					<%--<th data-options="field:'TASK_DEADTIME',align:'left'" width="100">办理截止期限</th>--%>					
					<th data-options="field:'WORK_HOURS',align:'left'" width="100">工时统计</th>
					<th data-options="field:'IS_OVERTIME',align:'left'" width="100" formatter="formatIsOverTime">是否超时</th>
<%--					<th data-options="field:'IS_HANG',align:'left'" width="150">结束时间</th>--%>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
