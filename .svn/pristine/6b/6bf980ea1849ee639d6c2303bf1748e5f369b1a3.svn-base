<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	
	function noticeToPackage(){
		var entityId = AppUtil.getEditDataGridRecord("PackageServiceGrid");
		if (entityId) {
			var $dataGrid = $("#PackageServiceGrid");
			var rowsData = $dataGrid.datagrid('getChecked');
			if(rowsData[0].RUN_STATUS!=2&&rowsData[0].RUN_STATUS!=3){
				parent.art.dialog({
					content: "未正常办结业务，不能通知揽件",
					icon:"warning",
					time:3,
					ok: true
				});
				return;
			}
			if(rowsData[0].MAIL_STATUS!=0){
				parent.art.dialog({
					content: "只有未通知揽件的业务才可以通知揽件",
					icon:"warning",
					time:3,
					ok: true
				});
				return;
			}
			art.dialog.confirm("是否本业务已审核完成并出证，通知邮政服务进行揽件？", function() {
				AppUtil.ajaxProgress({
					url : "emsController.do?noticeToPackage",
					params : {entityId:entityId},
					callback : function(resultJson) {						
					    parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
						AppUtil.gridDoSearch("PackageServiceToolbar", "PackageServiceGrid");
					}
				});
			});
		}
	}
	
	function toPackage(){
		var entityId = AppUtil.getEditDataGridRecord("PackageServiceGrid");
		if (entityId) {
			var $dataGrid = $("#PackageServiceGrid");
			var rowsData = $dataGrid.datagrid('getChecked');
			if(rowsData[0].MAIL_STATUS!=1){
				parent.art.dialog({
					content: "只有待揽件的业务才可以揽件",
					icon:"warning",
					time:3,
					ok: true
				});
				return;
			}else{
				$.dialog.open("emsController.do?packageInfo&entityId=" + entityId, {
					title : "揽件信息",
					width : "450px",
					height : "350px",
					lock : true,
					resize : false
				}, false);
			}
		}
	}
	
	function tracking(){
		var entityId = AppUtil.getEditDataGridRecord("PackageServiceGrid");
		if (entityId) {
			var $dataGrid = $("#PackageServiceGrid");
			var rowsData = $dataGrid.datagrid('getChecked');
			if(rowsData[0].MAIL_STATUS!=2){
				parent.art.dialog({
					content: "未揽件业务无物流信息",
					icon:"warning",
					time:3,
					ok: true
				});
				return;
			}else{
				$.dialog.open("emsController.do?trackingInfo&entityId=" + entityId, {
					title : "物流跟踪信息",
					width : "700px",
					height : "500px",
					lock : true,
					resize : false
				}, false);
			}
		}
	}
	
	function cancelOrder(){
		var entityId = AppUtil.getEditDataGridRecord("PackageServiceGrid");
		if (entityId) {
			/* var $dataGrid = $("#PackageServiceGrid");
			var rowsData = $dataGrid.datagrid('getChecked');
			if(rowsData[0].MAIL_STATUS==2){
				parent.art.dialog({
					content: "只有未通知揽件的业务才可以通知揽件",
					icon:"warning",
					time:3,
					ok: true
				});
				return;
			} */
			art.dialog.confirm("是否确认取消订单？", function() {
				AppUtil.ajaxProgress({
					url : "emsController.do?cancelEms",
					params : {entityId:entityId},
					callback : function(resultJson) {
						if(resultJson.success){						
						    parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							AppUtil.gridDoSearch("PackageServiceToolbar", "PackageServiceGrid");
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			});
		}
	}
	
	
    /**
     * 导出execl
     */
    function showExcelExportWindow() {
        AppUtil.showExcelExportWin({
            dataGridId:"PackageServiceGrid",
            tplKey:"PackageServiceTpl",
            excelName:"揽件服务数据",
            queryParams:{
                "e.EXE_ID":($("#PackageServiceToolbar input[name='Q_e.EXE_ID_LIKE']").val()).trim(),
                "e.CREATOR_NAME":($("#PackageServiceToolbar input[name='Q_e.CREATOR_NAME_LIKE']").val()).trim(),
                "e.SQRMC":$("#PackageServiceToolbar input[name='Q_e.SQRMC_LIKE']").val(),
                "t.MAIL_STATUS":$("#PackageServiceToolbar input[name='Q_T.MAIL_STATUS_EQ']").val(),
                "e.CREATE_TIME":$("#PackageServiceToolbar input[name='Q_e.CREATE_TIME_>=']").val(),
                "y.CREATE_TIME":$("#PackageServiceToolbar input[name='Q_e.CREATE_TIME_<=']").val(),
                "e.SUBJECT":$("#PackageServiceToolbar input[name='Q_e.SUBJECT_LIKE']").val()
            }
        });
    };
	
	function rowformater(value,row,index){
		if(value=='1'){
			return '未揽件';
		}else if(value=='2'){
			return '已揽件';
		}else if(value=='3'){
			return '已撤单';
		}else if(value=='0'){
			return '未通知';
		}
	}
	
	$(document).ready(function() {
		var start1 = {
			elem : "#PackageServiceE.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_e.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_e.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_e.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#PackageServiceE.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_e.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_e.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_e.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);		
		
		//AppUtil.initAuthorityRes("PackageServiceToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="PackageServiceToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"  reskey="p_notice"
								iconcls="eicon-level-up" plain="true"
								onclick="noticeToPackage();">通知揽件</a>
							<a href="#" class="easyui-linkbutton"  reskey="p_package"
								iconcls="eicon-edit" plain="true"
								onclick="toPackage();">揽件</a>
							<a href="#" class="easyui-linkbutton"  reskey="p_traces"
								iconcls="eicon-th-list" plain="true"
								onclick="tracking();">物流跟踪</a>
							<!-- <a href="#" class="easyui-linkbutton"  reskey="p_cancelorder"
								iconcls="eicon-rotate-left" plain="true"
								onclick="cancelOrder();">撤单</a> -->
                        	<a href="#" class="easyui-linkbutton" reskey="Export_package"
                                iconcls="eicon-file-excel-o" plain="true" 
                                onclick="showExcelExportWindow();">导出数据</a> 
						</div>
					</div>
				</div>
			</div>			
			<form action="#" name="PackageServiceForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_e.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">发起人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_e.CREATOR_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_e.SQRMC_LIKE" /></td>
							<td style="width:68px;text-align:right;">物流状态</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.MAIL_STATUS_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '',value: '请选择'},
									            {label: '0', value: '未通知'},{label: '1',value: '待揽件'},{label: '2',value: '已揽件'}],panelWidth: 128,panelHeight: 'auto' " />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="PackageServiceE.CREATE_TIME_BEGIN" name="Q_e.CREATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="PackageServiceE.CREATE_TIME_END" name="Q_e.CREATE_TIME_&lt;=" /></td>
							<td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_e.SUBJECT_LIKE" />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('PackageServiceToolbar','PackageServiceGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('PackageServiceForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="PackageServiceGrid" fitcolumns="true" nowrap="false"
			toolbar="#PackageServiceToolbar" method="post" idfield="RECORD_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			url="emsController.do?packageServiceGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="8%" formatter="FlowUtil.formatRunStatus">任务状态</th>
					<th data-options="field:'EXE_ID',align:'left'" width="13%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="30%">流程标题</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="10%">发起人</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'MAIL_STATUS',align:'left'" width="10%" formatter="rowformater">物流状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
