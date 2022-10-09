<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<script type="text/javascript">
	
	function checkTime(i){
	    if (i<10){
	    	i="0" + i;
	    }
	    return i;
    }
    
	function showAppointGridRecord(){
		var now=new Date();
		now.setMinutes (now.getMinutes () - 30);
        var year=now.getFullYear();
        var month=now.getMonth();
        var date=now.getDate();
        month=month+1;
        month=checkTime(month);
        date=checkTime(date);
        var day = year+"-"+month+"-"+date;
        
        var hour=now.getHours();
        var minu=now.getMinutes();
        hour=checkTime(hour);
        minu=checkTime(minu);
        var time = hour+":"+minu;
        alert(time);
        var rowsData = $("#AppointGrid").datagrid("getChecked");
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			if(rowsData[0].DATE_TIME>day){
				art.dialog({
					content: "非今天预约号，不能取号!",
					icon:"warning",
				    ok: true
				});
				return null;
			}
			if(rowsData[0].BEGIN_TIME<time){
				art.dialog({
					content: "预约号已过期!",
					icon:"warning",
				    ok: true
				});
				return null;
			}
			if(rowsData[0].IS_TAKE==1){
				art.dialog({
					content: "该预约已取号，不能重复取号!",
					icon:"warning",
				    ok: true
				});
				return null;
			}
			var entityId = rowsData[0].ID;
			$.dialog.open("appointmentController.do?appointmentTake&entityId="+entityId, {
				title : "预约取号",
				width : "450px",
				height : "250px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	function rowformater(value,row,index){
		if(value=='1'){
			return '是';
		}else if(value=='0'){
			return '否';
		}
	}
	
	function rowformaterstatus(value,row,index){
		if(value=='1'){
			return "<font color='blue'>正常</font>";
		}else if(value=='2'){
			return "<font color='red'>作废</font>";
		}
	}
	
	$(document).ready(function() {
		
		var appointTime = {
	    	elem: "#APPOINT.APPOINT_TIME",
		    format: "YYYY-MM-DD",
		    istime: false,
		    min:laydate.now()
		};
		laydate(appointTime);
		
		//AppUtil.initAuthorityRes("AppointToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="AppointToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-add" plain="true"
								onclick="showAppointGridRecord();">取号</a>
						</div>
					</div>
				</div>
			</div>			
			<form action="#" name="AppointForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">预约日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="APPOINT.APPOINT_TIME" name="Q_t.DATE_TIME_=" value="${currentDate }"/></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('AppointToolbar','AppointGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('AppointForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="AppointGrid" fitcolumns="true" nowrap="false"
			toolbar="#AppointToolbar" method="post" idfield="ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="appointmentController.do?appointmentGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true" width="80">ID</th>
					<th data-options="field:'NAME',align:'left'" width="10%">姓名</th>
					<th data-options="field:'CARD',align:'left'" width="15%">身份证号</th>
					<th data-options="field:'DATE_TIME',align:'left'" width="10%">预约日期</th>
					<th data-options="field:'BEGIN_TIME',align:'left'" width="10%">预约时段</th>
					<th data-options="field:'END_TIME',align:'left'" width="10%">预约时段</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%">预约部门</th>
					<th data-options="field:'IS_TAKE',align:'left',formatter:rowformater" width="5%">是否取号</th>
					<th data-options="field:'TAKE_TIME',align:'left'" width="15%">取号时间</th>
					<th data-options="field:'STATUS',align:'left',formatter:rowformaterstatus" width="5%">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
