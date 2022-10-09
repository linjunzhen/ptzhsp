<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<style type="text/css" >
	.tab_text123 {
		width: 90px!important;
	}

</style>
<script type="text/javascript">


	function showExcelallExportWindow(){
        var exeId = $("#goInfoStatisToolbar input[ name='Q_T.EXE_ID_LIKE']").val();
        var creatorName = $("#goInfoStatisToolbar input[name='Q_T.CREATOR_NAME_LIKE']").val();
        var runStatus = $("#goInfoStatisToolbar input[name='Q_T.RUN_STATUS_=']").val();
        var isNeedSign = $("#goInfoStatisToolbar input[name='Q_T.ISNEEDSIGN_=']").val();
        var sssblx = $("#goInfoStatisToolbar input[name='Q_T.SSSBLX_=']").val();
        var createTimeGt = $("#goInfoStatisToolbar input[name='Q_T.CREATE_TIME_>=']").val();
        var createTimeLt = $("#goInfoStatisToolbar input[name='Q_T.CREATE_TIME_<=']").val();
        var  subject = $("#goInfoStatisToolbar input[name='Q_T.SUBJECT_LIKE']").val();
        var url= "statisticsController/exportCompanyInfosToExcel.do?Q_T.EXE_ID_LIKE="+
				exeId+"&Q_T.CREATOR_NAME_LIKE="+creatorName+"&Q_T.RUN_STATUS_EQ="+
				runStatus+"&Q_T.ISNEEDSIGN_EQ="+isNeedSign+"&Q_T.SSSBLX_EQ="+sssblx+"&Q_T.CREATE_TIME_GE="
				+createTimeGt+"&Q_T.CREATE_TIME_LE="+createTimeLt+"&Q_T.SUBJECT_LIKE="+subject+"&Q_T.RUN_STATUS_NEQ=0";
        window.location.href = encodeURI(url);
	}


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
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	
	function formatCurStepnames(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		var href = "";
		if(val=="开始"){
			href += "申请人";
		}else if(val==undefined||val==null||val==""){
		}else{
			href += val;
		}
	    return href;
	}

	$(document).ready(function() {
		var start1 = {
			elem : "#CompanyInfoT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#CompanyInfoT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);
	});

	//空数据，横向滚动
	$('#goInfoStatisGrid').datagrid({
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
		<div id="goInfoStatisToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="Expor_DepartServiceItem"
							   iconcls="eicon-file-excel-o" plain="true"
							   onclick="showExcelallExportWindow();">导出数据</a>
						</div>
					</div>
				</div>
			</div>

			<form action="#" name="ExecutionInfoForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.CREATOR_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">流程状态</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RUN_STATUS_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">是否全程网办</td>
							<td style="width:85px;"><input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_T.ISNEEDSIGN_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
							<td style="width:68px;text-align:right;">是否秒批</td>
							<td style="width:85px;"><input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_T.SSSBLX_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="CompanyInfoT.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="CompanyInfoT.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">包含归档数据</td>
							<td style="width:85px;">
								<eve:eveselect clazz="tab_text tab_text123" dataParams="YesOrNo"
											   dataInterface="dictionaryService.findDatasForSelect"
											 name="isHistory"  value="0">
								</eve:eveselect>
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('goInfoStatisToolbar','goInfoStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ExecutionInfoForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="goInfoStatisGrid" fitcolumns="true" toolbar="#goInfoStatisToolbar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="statistCommercialController.do?goInfoStatisData&Q_T.RUN_STATUS_NEQ=0">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="20%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="10%" formatter="formatCurStepnames">当前环节</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="10%" formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="11%">申请时间</th>
					<th data-options="field:'XKFILE_NUM',align:'left'" width="10%">统一社会信用代码</th>
					<th data-options="field:'COMPANY_NAME',align:'left'" width="15%">公司名称</th>
					<th data-options="field:'LEGAL_NAME',align:'left'" width="7%">负责人</th>
					<th data-options="field:'INVESTMENT',align:'left'" width="10%">注册资本(万元)</th>
					<th data-options="field:'BUSSINESS_SCOPE',align:'left'" width="25%">经营范围</th>
					<th data-options="field:'CONTACT_PHONE',align:'left'" width="10%">联系电话</th>
					<th data-options="field:'EFFECT_TIME',align:'left'" width="10%">成立日期</th>
					<th data-options="field:'CLOSE_TIME',align:'left'" width="10%">经营期限</th>
					<th data-options="field:'REGISTER_ADDR',align:'left'" width="15%">住所</th>

				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
