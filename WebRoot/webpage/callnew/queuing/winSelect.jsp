<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<script type="text/javascript">
	function wininfoformater(value,row,index){
		return row.BELONG_ROOM+"区，"+row.WIN_NO+"号窗，"+row.SCREEN_NO+"屏";
	}
	
	function rowformater(value,row,index){
		if(value=='1'){
			return '是';
		}else if(value=='0'){
			return '否';
		}
	}
	
	function operformater(value,row,index){
		if(value!=""&&value!=undefined&&value!='${sessionScope.curLoginUser.userId}'){
			return "——";
		}else{
			return "<a href='#' onclick='selectWin("+index+")' ><font style='color:blue;'>进入</font></a>";
		}
	}
	
	function statusformater(value,row,index){
		if(row.CUR_USERID!=""&&row.CUR_USERID!=undefined){
			return "工作中";
		}else{
			return "空闲";
		}
	}
	
	function selectWin(index){
		var row = $('#WinSelectGrid').datagrid('getData').rows[index];
		AppUtil.ajaxProgress({
			url : "newCallController.do?selectWinInfo",
			params : {
				recordId : row.RECORD_ID
			},
			callback : function(resultJson) {
				if(resultJson.success){
					art.dialog.data("selectWinInfo", {
						winNo : row.WIN_NO,
						departName : row.DEPART_NAME,
						business : row.WIN_BUSINESS_CODES,
						isContinue : resultJson.jsonString
					});// 存储数据
					AppUtil.closeLayer();
				}
			}
		});
	}
</script>
</head>
<div class="easyui-layout eui-dialog" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WinSelectToolbar">
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true"
			id="WinSelectGrid" nowrap="false"
			toolbar="#WinSelectToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="newCallController.do?winSelectDatagrid">
			<thead>
				<tr>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'WIN_NO',hidden:true">WIN_NO</th>
					<th data-options="field:'DEPART_NAME',hidden:true">DEPART_NAME</th>
					<th data-options="field:'WIN_BUSINESS_CODES',hidden:true">WIN_BUSINESS_CODES</th>
					<th data-options="field:'wininfo',align:'left',formatter:wininfoformater" width="20%">窗口屏信息</th>
					<th data-options="field:'WIN_BUSINESS_NAMES',align:'left'" width="37%">办理业务</th>
					<th data-options="field:'winStatus',align:'left',formatter:statusformater" width="15%">窗口状态</th>
					<th data-options="field:'CUR_USERNAME',align:'left'" width="15%">当前人员</th>
					<th data-options="field:'CUR_USERID',align:'left',formatter:operformater" width="10%">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
