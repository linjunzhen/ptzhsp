<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function syncBjxx(){
		var rows = $("#bjxxSyncGrid").datagrid("getChecked");
		if(rows.length<=0){
			parent.art.dialog({
				content : "请选择需要同步的记录",
				icon : "warning",
				time : 3,
				ok : true
			});
			return false;
		}
    	var exeIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				exeIds+=",";
			}
			exeIds+=rows[i].EXE_ID;
		}
		AppUtil.ajaxProgress({
			url : "swbDataController.do?bjxxSync",
			params : {exeIds : exeIds},
			callback : function(resultJson) {
				$("#bjxxSyncGrid").datagrid("reload");
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
			}
		});
		
	}
	  
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bjxxSyncGrid');  
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
	$('#bjxxSyncGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="bjxxSyncToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey=""
								iconcls="eicon-refresh" plain="true"
								onclick="syncBjxx();">同步</a> 
								
							<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"  striped="true"
			id="bjxxSyncGrid" nowrap="false"
			toolbar="#bjxxSyncToolbar" method="post" idfield="EXE_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="swbDataController.do?bjxxUnSyncData">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="40%">办件名称</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="30%" >服务事项名称</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%" >办理时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>