
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    //受理
	function ysslWindow() {
	    var itemId = AppUtil.getEditDataGridRecord("SyysGrid");
	    if (itemId) {
	        var rowData = $("#SyysGrid").datagrid("getChecked")[0];
	        var taskId = rowData.TASK_ID;
	        var exeId = rowData.EXE_ID;
	        window.open("executionController.do?goHandle&taskId="+taskId+"&exeId="+exeId, "_blank");
	    }
	}
	//预审补件
	function ysWindow(){
		var itemId = AppUtil.getEditDataGridRecord("SyysGrid");
		if(itemId){
			var rowData = $("#SyysGrid").datagrid("getChecked")[0];
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
	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程流水号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId="+exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	function formatLeftDate(val,row){
		var li = "<li >";
		li+="<font style=\"float: left;color: #ff4b4b\">";
		if(val==0){
			li+="今天截止";
		}else if(val==-1){
			li+="已超期";
		}else if(val==3){
			li+="用户撤回";
		}else if(val!=-2){
			li+=("剩余"+val+"个工作日");
		}
		li+="</font>";
		li+="</li>";
		return li;
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
	function showSelectDeparts(){
		var departId = $("input[name='Q_T.DEPART_ID_=']").val();
		$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
			title : "组织机构选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					$("input[name='Q_D.DEPART_ID_=']").val(selectDepInfo.departIds);
					$("input[name='DEPT_NAME']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
	
	function resetSyysForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SyysForm');
	}

	//空数据，横向滚动
	$('#SyysGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="SyysToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
<%--            <div class="right-con">--%>
<%--                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">--%>
<%--                    <div class="e-toolbar-ct">--%>
<%--                        <div class="e-toolbar-overflow">--%>
<%--                        <a href="#" class="easyui-linkbutton" reskey="SL_Syys"--%>
<%--                                iconcls="icon-edit" plain="true"--%>
<%--                                onclick="ysslWindow();">预审</a>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
            <form action="#" name="SyysForm">
				<input type="hidden" id="DEPARTID" name="Q_D.DEPART_ID_=" value="">
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
							<td style="width:68px;text-align:right;">查询部门</td>							
							<td style="width:135px;">							
							<input type="text"  placeholder="请选择部门"
								style="width:128px;float:left;" class="eve-input" name="DEPT_NAME" onclick="showSelectDeparts();"/>
							</td>
                        <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('SyysToolbar','SyysGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="resetSyysForm()" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table rownumbers="true" pagination="true" striped="true"
            id="SyysGrid" fitcolumns="false" toolbar="#SyysToolbar"
            method="post" idfield="TASK_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="recipientController.do?needMeHandleAndQbysj">
            <thead>
                <tr>
                    <!-- <th field="ck" checkbox="true"></th> -->
                    <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
                    <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                   <th data-options="field:'DEPART_NAME',align:'left'" width="15%" >事项部门</th>
                    <th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">申报名称</th>
                    <th data-options="field:'CREATE_TIME',align:'left'" width="15%">提交日期</th>
                    <th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">预审人员</th>
                   <th data-options="field:'LEFT_WORKDAY',align:'left'" width="10%" formatter="formatLeftDate">时限说明</th>
<%--                    <th data-options="field:'SQRMC',align:'left'" width="100">申请人</th>--%>
                    <th data-options="field:'CREATOR_NAME',align:'left'" width="15%">注册用户</th>
                    <th data-options="field:'CREATOR_PHONE',align:'left'" width="10%">联系电话</th>
                    <th data-options="field:'DIC_NAME',align:'left'" width="10%">证件类型</th>
                    <th data-options="field:'ZJHM',align:'left'" width="15%">证件号码</th>
                </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>



