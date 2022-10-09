<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	var startTimeInput = {
           	elem: "#OldAgeCardNeedMeHandle.CREATE_TIME_BEGIN",
        	format: "YYYY-MM-DD hh:mm:ss",
        	istime: true,
        	choose: function(data){
   	        	var beginTime = data;
   	  			var endTime = $("#OldAgeCardNeedMeHandleToolbar").find("input[name='Q_E.CREATE_TIME_<=']").val();
   	  			if(beginTime != "" && endTime != ""){
   	  		    	if(beginTime > endTime){
   		  		    	alert("起始时间必须小于等于截止时间，请重新输入！");
   		  		    	$("#OldAgeCardNeedMeHandleToolbar").find("input[name='Q_E.CREATE_TIME_>=']").val("");
   	  		    	}
   	  			}
        	}
   		};
		var endTimeInput = {
        	elem: "#OldAgeCardNeedMeHandle.CREATE_TIME_END",
        	format: "YYYY-MM-DD hh:mm:ss",
        	istime: true,
        	choose: function(data){
            	var beginTime = $("#OldAgeCardNeedMeHandleToolbar").find("input[name='Q_E.CREATE_TIME_>=']").val();
      			var endTime = data;
      			if(beginTime != "" && endTime != ""){
      		    	if(beginTime > endTime){
      		    		alert("截止时间必须大于等于起始时间，请重新输入！");
      		    		$("#OldAgeCardNeedMeHandleToolbar").find("input[name='Q_E.CREATE_TIME_<=']").val("");
      		    	}
      			}
        	}
    	};
    	laydate(startTimeInput);
    	laydate(endTimeInput);
    	AppUtil.initAuthorityRes("OldAgeCardNeedMeHandleToolbar");
    });
    
    function formatOldAgeCardNeedMeHandleSubject(val, row){
    	//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//获取流程任务ID
		var taskId = row.TASK_ID;
		//获取流程任务状态
		var taskStatus = row.TASK_STATUS;
		var href = "<a href='";
		if(taskStatus != "1"){
			href += "executionController.do?goDetail&exeId="+exeId+"&taskId="+taskId;
		}else{
			href += "executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
    }
    
    function formatOldAgeCardNeedMeHandleSex(val, row){
    	if(val == '1'){
    		return '男';
    	}else if(val == '2'){
    		return '女';
    	}
    }
    
    function doOldAgeCardNewApply(){
    	window.open("oldAgeCardController.do?newApply", "_blank");
    }

	//空数据，横向滚动
	$('#OldAgeCardNeedMeHandleGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="OldAgeCardNeedMeHandleToolbar">
            <!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
					        <a href="#" class="easyui-linkbutton" iconCls="eicon-plus" reskey="NEW_OldAgeCard"  
					           plain="true" onclick="doOldAgeCardNewApply();">新证办理</a>
					        <a class="easyui-linkbutton" style="border-width:0;height:0;width:0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form name="OldAgeCardNeedMeHandleForm" action="#">
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
			        <tbody>
			            <tr style="height:28px;">
			                <td style="width:68px;text-align:right;">申报号</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_E.EXE_ID_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">申请人</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_E.SQRMC_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">身份证号</td>
			                <td>
			                    <input class="eve-input" type="text" name="Q_E.SQRSFZ_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			            </tr>
			            <tr style="height:28px;">
			                <td style="text-align:right;">申请日期</td>
							<td>
							    <input type="text" style="width:128px;float:left;" class="laydate-icon"
								       id="OldAgeCardNeedMeHandle.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" />
						    </td>
							<td style="text-align:right;">至</td>
							<td>
							    <input type="text" style="width:128px;float:left;" class="laydate-icon"
								       id="OldAgeCardNeedMeHandle.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" />
						    </td>
						    <td></td>
						    <td>
						        <input type="button" value="查询" class="eve-button"
								       onclick="AppUtil.gridDoSearch('OldAgeCardNeedMeHandleToolbar','OldAgeCardNeedMeHandleGrid')" />
								<input type="button" value="重置" class="eve-button"
									   onclick="AppUtil.gridSearchReset('OldAgeCardNeedMeHandleForm')" />
							</td>
			            </tr>
			        </tbody>
			    </table>
			</form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			   id="OldAgeCardNeedMeHandleGrid" fitcolumns="false" toolbar="#OldAgeCardNeedMeHandleToolbar"
			   method="post" idfield="EXE_ID" checkonselect="true" selectoncheck="true" fit="true" 
			   border="false" nowrap="false"
			   url="oldAgeCardController.do?needMeHandle">
	        <thead>
	            <tr>
	                <th field="ck" checkbox="true"></th>
	                <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
	                <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
	                <th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatOldAgeCardNeedMeHandleSubject">流程标题</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="10%">环节名称</th>
					<th data-options="field:'SQRMC',align:'left'" width="15%">申请人</th>
					<th data-options="field:'SQRXB',align:'left'" width="5%" formatter="formatOldAgeCardNeedMeHandleSex">性别</th>
					<th data-options="field:'SQRSFZ',align:'left'" width="15%">身份证号</th>
					<th data-options="field:'SQRSJH',align:'left'" width="15%">联系电话</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
	            </tr>
	        </thead>
	    </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>