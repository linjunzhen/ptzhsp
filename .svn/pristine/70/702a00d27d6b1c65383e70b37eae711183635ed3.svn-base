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
							<a href="#"
								class="easyui-linkbutton" 
								iconcls="eicon-pencil" plain="true"
								onclick="editBdcSurveyGridRecord();">编辑</a>
							<a href="#"
								class="easyui-linkbutton" 
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteBdcSurveyGridRecord();">删除</a>
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
								name="Q_T.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">缴费状态</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ORDER_STATUS_LIKE" /></td>
							<td style="width:68px;text-align:right;">创建日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="bdcddjfxxController.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">创建日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="bdcddjfxxController.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
							<td ><input type="button" value="查询"
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
			url="bdcDdjfxxController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'YW_ID',hidden:true">主键ID</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%" >创建时间</th>
					<th data-options="field:'EXE_ID',align:'left'" width="15%">申报号</th>
					<th data-options="field:'ORDER_TOTALPRICE',align:'left'" width="10%">订单总金额</th>
					<th data-options="field:'ORDER_STATUS',align:'left'" width="15%" formatter="formatOrderState">缴费状态</th>
					<th data-options="field:'DZFP_FILEIDS',align:'left'" width="20%" formatter="formatDzfp">完税电子发票</th>
					<th data-options="field:'JSD_FILEIDS',align:'left'" width="20%" formatter="formatJsd">完税缴税单</th>
					
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>

<script type="text/javascript" >
    function formatOrderState(val,row){
       	switch (val) {
		case "1":
			return "<font ><b>已通知待缴费</b></font>";
		case "2":
			return "<font ><b>已缴费</b></font>";
		case "3":
			return "<font ><b>已取消</b></font>";
		default:
			break;
		}
    }
    
	
	/**
	 * 删除记录()
	 */
	function deleteBdcSurveyGridRecord() {
		AppUtil.deleteDataGridRecord("bdcDdjfxxController.do?multiDel",
				"BdcDyrlxrGrid");
	};
	
	/**
	 * 编辑记录
	 */
	function editBdcSurveyGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BdcDyrlxrGrid");
		if (entityId) {
     	 	$.ajax({
		         type: "POST",
			     url: "bdcDdjfxxController.do?checkUpdateLimit&entityId="+ entityId,
			     async: false, //采用同步方式进行数据判断
			     success: function (resultJsonString) {
			     	var resultJson = $.parseJSON(resultJsonString);		
			     	if(resultJson.success){
			     		showBdcSurveyWindow(entityId)
			     	}else{
			     		alert(resultJson.msg)
			     	}
			     }
			 });
		}
	}
	
	/**
	 * 显示信息对话框
	 */
	function showBdcSurveyWindow(entityId) {
		$.dialog.open("bdcDdjfxxController.do?create&entityId=" + entityId, {
			title : "",
			width : "800px",
			lock : true,
			resize : false,
			height : "450px",
		}, false);
	};
	
	/**
	 * 完税电子发票附件下载
	 */
    function formatDzfp(val,row){
	      var newhtml = "";
	      if(row.DZFP_FILEIDS!=null && row.DZFP_FILEIDS!=undefined && row.DZFP_FILEIDS!=""){
	     	 $.ajax({
		         type: "POST",
			     url: "executionController.do?getResultFiles&fileIds="+row.DZFP_FILEIDS,
			     async: false, //采用同步方式进行数据判断
			     success: function (resultJson) {
			    	   if(resultJson!="websessiontimeout"){
		                 var list=resultJson.rows;
		                 for(var i=0; i<list.length; i++){
		                     newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
		                     newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
		                     newhtml+=list[i].FILE_NAME+'</a>';
		                     newhtml+='</p>';
		                 }
		             }
			     }
			 });
	     }
	     return newhtml;
    }
	
	 //完税缴税单附件下载
    function formatJsd(val,row){
	      var newhtml = "";
	      if(row.JSD_FILEIDS!=null && row.JSD_FILEIDS!=undefined && row.JSD_FILEIDS!=""){
	     	 $.ajax({
		         type: "POST",
			     url: "executionController.do?getResultFiles&fileIds="+row.JSD_FILEIDS,
			     async: false, //采用同步方式进行数据判断
			     success: function (resultJson) {
			    	   if(resultJson!="websessiontimeout"){
		                 var list=resultJson.rows;
		                 for(var i=0; i<list.length; i++){
		                     newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
		                     newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
		                     newhtml+=list[i].FILE_NAME+'</a>';
		                     newhtml+='</p>';
		                 }
		             }
			     }
			 });
	     }
	     return newhtml;
    }
	$(document).ready(function() {
    	var startTimeInput = {
           	elem: "#bdcddjfxxController.CREATE_TIME_BEGIN",
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
        	elem: "#bdcddjfxxController.CREATE_TIME_END",
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