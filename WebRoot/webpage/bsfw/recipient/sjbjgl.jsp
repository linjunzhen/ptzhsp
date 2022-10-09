
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    //受理
	function bjslWindow() {
	    var itemId = AppUtil.getEditDataGridRecord("SjbjglGrid");
	    if (itemId) {
	        var rowData = $("#SjbjglGrid").datagrid("getChecked")[0];
	        var taskId = rowData.TASK_ID;
	        var exeId = rowData.EXE_ID;
	        toUrl("executionController.do?goHandle&taskId="+taskId+"&exeId="+exeId);
	    }
	}
	function toUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		$("#SjbjglToolbar").append(ssoForm);
		ssoForm.submit();		
	}
    
	$(document).ready(function() {
        var start1 = {
            elem : "#bjCREATE_TIME_BEGIN",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#bjCREATE_TIME_BEGIN").val();
                var endTime = $("#bjCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("开始时间必须小于等于结束时间,请重新输入!");
                        $("#bjCREATE_TIME_BEGIN").val("");
                    }
                }
            }
        };
        var end1 = {
            elem : "#bjCREATE_TIME_END",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#bjCREATE_TIME_BEGIN").val();
                var endTime = $("#bjCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("结束时间必须大于等于开始时间,请重新输入!");
                        $("#bjCREATE_TIME_END").val("");
                    }
                }
            }
        };
        laydate(start1);
        laydate(end1);
    });

	//空数据，横向滚动
	$('#SjbjglGrid').datagrid({
		onLoadSuccess: function(){
			var dc = $(this).data('datagrid').dc;
	        var header2Row = dc.header2.find('tr.datagrid-header-row');
	        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		}
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="SjbjglToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                        <a href="#" class="easyui-linkbutton" reskey="SL_sjbjgl"
                                iconcls="icon-edit" plain="true"
                                onclick="bjslWindow();">受理</a>
						
						<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SjbjglForm">
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
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="bjCREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
                            <td style="width:68px;text-align:right;">申请日期至</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="bjCREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
                         <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('SjbjglToolbar','SjbjglGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('SjbjglForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
            id="SjbjglGrid" fitcolumns="true" toolbar="#SjbjglToolbar"
            method="post" idfield="TASK_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="recipientController.do?needMeHandleAndSjbj">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
                    <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                    <th data-options="field:'ITEM_NAME',align:'left'" width="25%" >服务项目名称</th>
                    <th data-options="field:'SQRMC',align:'left'" width="15%">申请人</th>
                    <th data-options="field:'SQRSJH',align:'left'" width="13%">申请人电话</th>
                    <th data-options="field:'CREATOR_NAME',align:'left'" width="10%">收件人</th>
                    <th data-options="field:'CREATE_TIME',align:'left'" width="21%">收件时间</th>
                </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>



