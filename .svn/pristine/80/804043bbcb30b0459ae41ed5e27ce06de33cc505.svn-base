
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    //受理
	function slWindow() {
	    var itemId = AppUtil.getEditDataGridRecord("WinSign");
	    if (itemId) {
	        var rowData = $("#WinSign").datagrid("getChecked")[0];
	        var taskId = rowData.TASK_ID;
	        var exeId = rowData.EXE_ID;
	        window.open("executionController.do?goHandle&taskId="+taskId+"&exeId="+exeId, "_blank");
	    }
	}
	//收件补件
	function bjWindow(){
		var itemId = AppUtil.getEditDataGridRecord("WinSign");
		if(itemId){
			var rowData = $("#WinSign").datagrid("getChecked")[0];
            var taskId = rowData.TASK_ID;
            var exeId = rowData.EXE_ID;
            var itemCode = rowData.ITEM_CODE;
            var sqfs = rowData.SQFS;
	        $.dialog.open("recipientController.do?bjxx&taskId="+taskId+"&exeId="+exeId+"&itemCode="+itemCode+"&sqfs="+sqfs , {
	            title : "收件补件信息",
	            width : "100%",
	            height : "100%",
	            lock : true,
	            resize : false
	        }, false);
		}
	}
	$(document).ready(function() {
        var start1 = {
            elem : "#sjCREATE_TIME_BEGIN",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#sjCREATE_TIME_BEGIN").val();
                var endTime = $("#sjCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("开始时间必须小于等于结束时间,请重新输入!");
                        $("#sjCREATE_TIME_BEGIN").val("");
                    }
                }
            }
        };
        var end1 = {
            elem : "#sjCREATE_TIME_END",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
                 var beginTime = $("#sjCREATE_TIME_BEGIN").val();
                var endTime = $("#sjCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("结束时间必须大于等于开始时间,请重新输入!");
                        $("#sjCREATE_TIME_END").val("");
                    }
                }
            }
        };
        laydate(start1);
        laydate(end1);
    });

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="WinSignToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <form action="#" name="WinSignForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">窗口号</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="2" style="width:128px;"
                                name="Q_T.WIN_NO_LIKE" /></td>
                            <td style="width:68px;text-align:right;">签到人</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="10" style="width:128px;"
                                name="Q_T.LOGIN_USER_NAME_LIKE" /></td>
                            <td style="width:68px;text-align:right;">签到时间从</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="sjCREATE_TIME_BEGIN" name="Q_T.LOGIN_TIME_>=" /></td>
                            <td style="width:68px;text-align:right;">申请日期至</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="sjCREATE_TIME_END" name="Q_T.LOGIN_TIME_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('WinSignToolbar','WinSign')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('WinSignForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
            id="WinSign" fitcolumns="true" toolbar="#WinSignToolbar"
            method="post" idfield="SIGN_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="winSignController.do?datagrid">
            <thead>
                <tr>
                    <!-- <th field="ck" checkbox="true"></th> -->
                    <th data-options="field:'SIGN_ID',hidden:true">SIGN_ID</th>
                    <th data-options="field:'WIN_NO',align:'left'" width="10%">窗口号</th>
                    <th data-options="field:'LOGIN_USER_NAME',align:'left'" width="15%" >签到人员</th>
                    <th data-options="field:'LOGIN_TIME',align:'left'" width="20%">签到时间</th>
                    <th data-options="field:'WIN_IP',align:'left'" width="54%">窗口IP地址</th>
                </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>



