<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var startTimeInput = {
	    	elem: "#OldAgeCardHandledByMe.CREATE_TIME_BEGIN",
	     	format: "YYYY-MM-DD hh:mm:ss",
	     	istime: true,
	     	choose: function(data){
	         	var beginTime = data;
	  		    var endTime = $("#OldAgeCardHandledByMeToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val();
	  		    if(beginTime != "" && endTime != ""){
	  		    	if(beginTime > endTime){
		  		    	alert("起始时间必须小于等于截止时间，请重新输入！");
		  		    	$("#OldAgeCardHandledByMeToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val("");
	  		    	}
	  		   	}
	     	}
		};
	  	var endTimeInput = {
	     	elem: "#OldAgeCardHandledByMe.CREATE_TIME_END",
	     	format: "YYYY-MM-DD hh:mm:ss",
	     	istime: true,
	     	choose: function(data){
	     		var beginTime = $("#OldAgeCardHandledByMeToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val();
			    	var endTime = data;
			    	if(beginTime != "" && endTime != ""){
			    		if(beginTime > endTime){
			    			alert("截止时间必须大于等于起始时间，请重新输入！");
			    			$("#OldAgeCardHandledByMeToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val("");
			    		}
			    	}
	     	}
		};
	  	laydate(startTimeInput);
	    laydate(endTimeInput);
	    AppUtil.initAuthorityRes("OldAgeCardHandledByMeToolbar");
	});
    
    function formatOldAgeCardHandledByMeSubject(val, row){
    	//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//当前环节
		var curTache = row.CUR_STEPNAMES;
		var href = "<a href='";
		if(runStatus == "0"){
			href += "executionController.do?goStart&exeId="+exeId;
		}else{
			href += "executionController.do?goDetail&exeId="+exeId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
    }
    
    function revokeOldAgeCardHandledByMe(){
    	var exeId = AppUtil.getEditDataGridRecord("OldAgeCardHandledByMeGrid");
    	if(exeId){
    		art.dialog.confirm("您确定要撤回该流程吗?", function(){
    			var layload = layer.load("正在提交请求中…");
				$.post("executionController.do?revokeFlow", {
					    exeId: exeId
				    }, function(responseText, status, xhr){
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon: "succeed",
								time: 3,
							    ok: true
							});
							$("#OldAgeCardHandledByMeGrid").datagrid('reload');
							$("#OldAgeCardNeedMeHandleGrid").datagrid('reload');
						}else{
							art.dialog({
								content: resultJson.msg,
								icon: "error",
							    ok: true
							});
						}
				});
    		});
    	}
    }

	//空数据，横向滚动
	$('#OldAgeCardHandledByMeGrid').datagrid({
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
        <div id="OldAgeCardHandledByMeToolbar">
            <!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index:1111;top:0px;left:0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <a href="#" class="easyui-linkbutton" iconCls="eicon-step-backward" 
						       plain="true" onclick="revokeOldAgeCardHandledByMe();">撤回流程</a>
						    <a class="easyui-linkbutton" style="border-width:0;height:0;width:0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form name="OldAgeCardHandledByMeForm" action="#">
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
			        <tbody>
			            <tr style="height:28px;">
			                <td style="width:68px;text-align:right;">申报号</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_T.EXE_ID_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">流程状态</td>
			                <td style="width:140px;padding-left:4px;">
			                    <input class="easyui-combobox" name="Q_T.RUN_STATUS_=" style="width:148px;" 
								       data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:148,panelHeight:'auto'"/>
			                </td>
			                <td style="width:68px;text-align:right;">申请人</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_T.SQRMC_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td colspan="2"></td>
			            </tr>
			            <tr style="height:28px;">
			                <td style="width:68px;text-align:right;">身份证号</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_T.SQRSFZ_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">申请日期</td>
							<td style="width:140px;">
							    <input type="text" style="width:128px;float:left;" class="laydate-icon"
								       id="OldAgeCardHandledByMe.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" />
						    </td>
							<td style="width:68px;text-align:right;">至</td>
							<td style="width:140px;">
							    <input type="text" style="width:128px;float:left;" class="laydate-icon"
								       id="OldAgeCardHandledByMe.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" />
						    </td>
						    <td colspan="2">
						        <input type="button" value="查询" class="eve-button"
								       onclick="AppUtil.gridDoSearch('OldAgeCardHandledByMeToolbar','OldAgeCardHandledByMeGrid')" />
								<input type="button" value="重置" class="eve-button"
									   onclick="AppUtil.gridSearchReset('OldAgeCardHandledByMeForm')" />
							</td>
			            </tr>
			        </tbody>
			    </table>
			</form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			   id="OldAgeCardHandledByMeGrid" fitcolumns="true" toolbar="#OldAgeCardHandledByMeToolbar"
			   method="post" idfield="EXE_ID" checkonselect="true" 
			   selectoncheck="true" fit="true" border="false" nowrap="false"
			   url="oldAgeCardController.do?handledByMe">
			<thead>
			    <tr>
			        <th field="ck" checkbox="true"></th>
			        <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
			        <th data-options="field:'SUBJECT',align:'left'" width="15%" formatter="formatOldAgeCardHandledByMeSubject">流程标题</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'SQRSFZ',align:'left'" width="15%">身份证号</th>
					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="10%">当前环节</th>
					<th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">当前环节审核人</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="10%" formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
			    </tr>
			</thead>
		</table>
        <!-- =========================表格结束==========================-->
    </div>
</div>