<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,laydate,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    //确定按钮
    function doSelectRows(){
    	var rows = $("#bdcWaitAcceptGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		if(rows.length>0){
			parent.toUrl(encodeURI(encodeURI("executionController.do?goHandle&taskId="+rows[0].TASK_ID+"&exeId="+rows[0].EXE_ID+"&lineId=${record.RECORD_ID}")),"");
		    AppUtil.closeLayer(); 
		}else{
			parent.art.dialog({
				content: "请先选择办件",
				icon:"error",
				ok: true
			});
		}
		
    }
   
    //去除字符串的空串(前后空格)
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bdcWaitAcceptGrid');  
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
	$('#bdcWaitAcceptGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	
	
	
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
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="bdcWaitAcceptToolbar">
				<form action="#" name="bdcWaitAcceptForm">
					<!--====================开始编写隐藏域============== -->
					
					<input type="hidden" name="RECORD_ID" value="${record.RECORD_ID}">
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
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
								onclick="AppUtil.gridDoSearch('bdcWaitAcceptToolbar','bdcWaitAcceptGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('bdcWaitAcceptForm')" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="bdcWaitAcceptGrid" fitColumns="true" toolbar="#bdcWaitAcceptToolbar"
				method="post" idField="FWMMHTH" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="flowTaskController.do?needMeHandle&isHaveHandup=true&Q_T.TASK_NODENAME_EQ=%e5%be%85%e5%8f%97%e7%90%86">
				<thead>
					<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
					<th data-options="field:'TASK_STATUS',align:'left'" width="6%" formatter="formatTaskStatus">任务状态</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%">流程标题</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="9%">环节名称</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="9%">发起人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="11%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="11%">经办人</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

</body>
</html>

