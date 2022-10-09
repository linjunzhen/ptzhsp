
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    //受理
	function ysslWindow() {
	    var itemId = AppUtil.getEditDataGridRecord("YsglGrid");
	    if (itemId) {
	        var rowData = $("#YsglGrid").datagrid("getChecked")[0];
	        var taskId = rowData.TASK_ID;
	        var exeId = rowData.EXE_ID;
	        toUrl("executionController.do?goHandle&taskId="+taskId+"&exeId="+exeId, "_blank");
	    }
	}
	function toUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		$("#YsglGrid").append(ssoForm);
		ssoForm.submit();		
	}
	//预审补件
	function ysWindow(){
		var itemId = AppUtil.getEditDataGridRecord("YsglGrid");
		if(itemId){
			var rowData = $("#YsglGrid").datagrid("getChecked")[0];
            var taskId = rowData.TASK_ID;
            var exeId = rowData.EXE_ID;
            var itemCode = rowData.ITEM_CODE;
            var sqfs = rowData.SQFS;
	        $.dialog.open("recipientController.do?bjxx&taskId="+taskId+"&exeId="+exeId+"&itemCode="+itemCode+"&sqfs="+sqfs, {
	            title : "预审补件信息",
	            width : "100%",
	            height : "100%",
	            lock : true,
	            resize : false
	        }, false);
		}
	}
	$(document).ready(function() {
        var start1 = {
            elem : "#ysCREATE_TIME_BEGIN",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#ysCREATE_TIME_BEGIN").val();
                var endTime = $("#ysCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("开始时间必须小于等于结束时间,请重新输入!");
                        $("#ysCREATE_TIME_BEGIN").val("");
                    }
                }
            }
        };
        var end1 = {
            elem : "#ysCREATE_TIME_END",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
                 var beginTime = $("#ysCREATE_TIME_BEGIN").val();
                var endTime = $("#ysCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("结束时间必须大于等于开始时间,请重新输入!");
                        $("#ysCREATE_TIME_END").val("");
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
        <div id="YsglToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                        <a href="#" class="easyui-linkbutton" reskey="SL_Ysgl"
                                iconcls="icon-edit" plain="true"
                                onclick="ysslWindow();">预审</a>
                         <!-- <a href="#" class="easyui-linkbutton" reskey="BJ_Ysgl"
                                iconcls="icon-page" plain="true"
                                onclick="ysWindow();">预审补件</a> -->
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="YsglForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">申报号</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.EXE_ID_LIKE" /></td>
                            <td style="width:68px;text-align:right;">申报名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.SUBJECT_LIKE" /></td>
                            <td style="width:68px;text-align:right;">注册用户</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.CREATOR_NAME_LIKE" /></td>
                                <td colspan="2"></td>
                        </tr>
                        <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">提交日期从</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="ysCREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
                            <td style="width:68px;text-align:right;">提交日期至</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="ysCREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('YsglToolbar','YsglGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('YsglForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
            id="YsglGrid" fitcolumns="true" toolbar="#YsglToolbar"
            method="post" idfield="TASK_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="recipientController.do?needMeHandleAndYsgl">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
                    <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                    <th data-options="field:'SUBJECT',align:'left'" width="25%" >申报名称</th>
                    <th data-options="field:'CREATE_TIME',align:'left'" width="15%">提交日期</th>
                    <th data-options="field:'CREATOR_NAME',align:'left'" width="14%">注册用户</th>
                    <th data-options="field:'CREATOR_PHONE',align:'left'" width="15%">联系电话</th>
                    <th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">预审人员</th>
                </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>



