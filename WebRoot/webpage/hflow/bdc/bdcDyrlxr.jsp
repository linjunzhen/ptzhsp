<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BdcSurveyToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-plus" plain="true"
								onclick="showBdcSurveyWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" 
								iconcls="eicon-pencil" plain="true"
								onclick="editBdcSurveyGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" 
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteBdcSurveyGridRecord();">删除</a>
                            <a href="#"
                               class="easyui-linkbutton l-btn l-btn-small l-btn-plain" 
                               iconcls="eicon-file-excel-o" plain="true"
                               onclick="expBdcSurveyGridRecord();">导出</a>
						</div>
					</div>
				</div>
			</div>
			
			<form action="#" name="ExecutionForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">抵押权人名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.DYQRMC_LIKE" /></td>
							<td style="width:68px;text-align:right;">统一社会信用代码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.TYSHXYDM_LIKE" /></td>
						    <td style="width:68px;text-align:right;">联系人</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.LXR_NAME_LIKE" />
							</td>
						    <td style="width:68px;text-align:right;">联系电话</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.LXR_PHONE_LIKE" />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">创建日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="bdcDyrlxrController.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">创建日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="bdcDyrlxrController.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BdcSurveyToolbar','BdcDyrlxrGrid')" />
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
			id="BdcDyrlxrGrid" fitcolumns="true" toolbar="#BdcSurveyToolbar"
			method="post" idfield="YW_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="bdcDyrlxrController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'YW_ID',hidden:true">主键ID</th>
					<th data-options="field:'DYQRMC',align:'left'" width="15%">抵押权人名称</th>
					<th data-options="field:'TYSHXYDM',align:'left'" width="15%" >统一社会信用代码</th>
					<th data-options="field:'LXR_NAME',align:'left'" width="15%" >联系人</th>
					<th data-options="field:'LXR_PHONE',align:'left'" width="15%" >联系电话</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%" >创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>

<script type="text/javascript" >
	/**
	 * 删除抵押权人联系人列表记录()
	 */
	function deleteBdcSurveyGridRecord() {
		AppUtil.deleteDataGridRecord("bdcDyrlxrController.do?multiDel",
				"BdcDyrlxrGrid");
	};
	
	/**
	 * 编辑测绘管理信息记录
	 */
	function editBdcSurveyGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BdcDyrlxrGrid");
		if (entityId) {
			showBdcSurveyWindow(entityId);
		}
	}
	
	/**
	 * 显示测绘管理信息对话框
	 */
	function showBdcSurveyWindow(entityId) {
		$.dialog.open("bdcDyrlxrController.do?info&entityId=" + entityId, {
			title : "抵押人联系人",
			width : "500px",
			lock : true,
			resize : false,
			height : "350px",
		}, false);
	};
	
	function expBdcSurveyGridRecord() {
		var url = "bdcDyrlxrController.do?exportBdcDyrlxrGridRecord";
		window.location.href = url;
	}
	
	
	$(document).ready(function() {
    	var startTimeInput = {
           	elem: "#bdcDyrlxrController.CREATE_TIME_BEGIN",
        	format: "YYYY-MM-DD hh:mm:ss",
        	istime: true,
        	choose: function(data){
   	        	var beginTime = data;
   	  			var endTime = $("#BdcSurveyToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val();
   	  			if(beginTime != "" && endTime != ""){
   	  		    	if(beginTime > endTime){
   		  		    	alert("起始时间必须小于等于截止时间，请重新输入！");
   		  		    	$("#BdcSurveyToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val("");
   	  		    	}
   	  			}
        	}
   		};
		var endTimeInput = {
        	elem: "#bdcDyrlxrController.CREATE_TIME_END",
        	format: "YYYY-MM-DD hh:mm:ss",
        	istime: true,
        	choose: function(data){
            	var beginTime = $("#BdcSurveyToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val();
      			var endTime = data;
      			if(beginTime != "" && endTime != ""){
      		    	if(beginTime > endTime){
      		    		alert("截止时间必须大于等于起始时间，请重新输入！");
      		    		$("#BdcSurveyToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val("");
      		    	}
      			}
        	}
    	};
    	laydate(startTimeInput);
    	laydate(endTimeInput);
	
		AppUtil.initAuthorityRes("BdcSurveyToolbar");
	});

</script>