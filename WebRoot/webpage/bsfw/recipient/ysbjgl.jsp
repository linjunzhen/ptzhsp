
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		if(runStatus=="0"){
			href += "executionController.do?goStart&exeId="+exeId;
		}else{
			href += "executionController.do?goDetail&exeId="+exeId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	}
    //受理
	function ysbjslWindow() {
	    var itemId = AppUtil.getEditDataGridRecord("YsbjglGrid");
	    if (itemId) {
	        var rowData = $("#YsbjglGrid").datagrid("getChecked")[0];
	        var taskId = rowData.TASK_ID;
	        var exeId = rowData.EXE_ID;
	        window.open("executionController.do?goHandle&taskId="+taskId+"&exeId="+exeId, "_blank");
	    }
	}
    
	$(document).ready(function() {
        var start1 = {
            elem : "#ysbjCREATE_TIME_BEGIN",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#ysbjCREATE_TIME_BEGIN").val();
                var endTime = $("#ysbjCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("开始时间必须小于等于结束时间,请重新输入!");
                        $("#ysbjCREATE_TIME_BEGIN").val("");
                    }
                }
            }
        };
        var end1 = {
            elem : "#ysbjCREATE_TIME_END",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#ysbjCREATE_TIME_BEGIN").val();
                var endTime = $("#ysbjCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("结束时间必须大于等于开始时间,请重新输入!");
                        $("#ysbjCREATE_TIME_END").val("");
                    }
                }
            }
        };
        laydate(start1);
        laydate(end1);
    });

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="YsbjglToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <!-- <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                        <a href="#" class="easyui-linkbutton" reskey="SL_Ysbjgl"
                                iconcls="icon-edit" plain="true"
                                onclick="ysbjslWindow();">受理</a>
						<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
                        </div>
                    </div>
                </div>
            </div> -->
            <form action="#" name="YsbjglForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">申报名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.ITEM_NAME_LIKE" /></td>
                            <td style="width:68px;text-align:right;">申报号</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.EXE_ID_LIKE" /></td>
                            <td style="width:68px;text-align:right;">申请人</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.SQRMC_LIKE" /></td>
                                <td colspan="2"></td>
                        </tr>
                        <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申请日期从</td>
                            <td style="width:135px;"><input type="text"
                                style="width:128px;float:left;" class="laydate-icon"
                                id="ysbjCREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
                            <td style="width:68px;text-align:right;">申请日期至</td>
                            <td style="width:135px;"><input type="text"
                                style="width:128px;float:left;" class="laydate-icon"
                                id="ysbjCREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
                         <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('YsbjglToolbar','YsbjglGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('YsbjglForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true"
            id="YsbjglGrid" fitcolumns="true" toolbar="#YsbjglToolbar"
            method="post" idfield="TASK_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="recipientController.do?needMeHandleAndYsbj">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'TASK_ID',hidden:true" width="80">TASK_ID</th>
                    <th data-options="field:'EXE_ID',align:'left'" width="90">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="250" formatter="formatSubject">申报名称</th>
                    <th data-options="field:'ITEM_NAME',align:'left'" width="200" >服务项目名称</th>
                    <th data-options="field:'SQRMC',align:'left'" width="120">申请人</th>
                    <th data-options="field:'SQRSJH',align:'left'" width="80">申请人电话</th>
                </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>



