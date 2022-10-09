<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	  
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#lineMsgRecordGrid');  
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
	$('#lineMsgRecordGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
	
	$(document).ready(function() {
		var createTime = {
	    	elem: "#CREATE_TIME",
		    format: "YYYY-MM-DD",
		    istime: false,
		    //min:laydate.now()
		};
		laydate(createTime);
	});
	
	
	function formaterStatus(value,row,index){
		if(value=='1'){
			return "<font color='#0368ff'>成功</font>";
		}else if(value=='0'){
			return "<font color='#ff4b4b'>失败</font>";
		}
	}
	
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="lineMsgRecordToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
		<!-- 	<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-print" plain="true"
								onclick="printGridRecord();">打印</a>
						</div>
					</div>
				</div>
			</div>	 -->
			<form action="#" name="lineMsgRecordForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">排队号：</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:128px;float:left;" name="Q_T.LINE_NO_LIKE" /></td>
							<td style="width:68px;text-align:right;">创建日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:135px;float:left;" class="laydate-icon"
								id="CREATE_TIME" name="Q_t.CREATE_TIME_>=" /></td>
							<td colspan="2"><input type="button" value="查询"	
								class="eve-button"
								onclick="AppUtil.gridDoSearch('lineMsgRecordToolbar','lineMsgRecordGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('lineMsgRecordForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="lineMsgRecordGrid" nowrap="false"
			toolbar="#lineMsgRecordToolbar" method="post" idfield="YW_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="newCallController.do?lineMsgRecordGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'YW_ID',hidden:true">业务ID</th>
					<th data-options="field:'QUEUE_RECORDID',hidden:true">排队叫号记录ID</th>
					<th data-options="field:'LINE_NO',align:'center'" width="15%">排队号</th>
					<th data-options="field:'LINE_NAME',align:'center'" width="15%">用户名称</th>
					<th data-options="field:'LINE_MOBILE',align:'center'" width="15%">发送短信手机号</th>
					<th data-options="field:'MSG_STATUS',align:'center',formatter:formaterStatus" width="10%">发送状态</th>
					<th data-options="field:'CREATOR_NAME',align:'center'" width="25%">操作人</th>
					<th data-options="field:'CREATE_TIME',align:'center'" width="20%">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>