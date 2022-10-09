<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
  	
	/**
	 * 删除测绘管理列表记录()
	 */
	function deleteBdcSurveyGridRecord() {
		AppUtil.deleteDataGridRecord("bdcSurveyController.do?multiDel",
				"BdcSurveyGrid");
	};

	/**
	 * 编辑测绘管理信息记录
	 */
	function editBdcSurveyGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BdcSurveyGrid");
		if (entityId) {
			showBdcSurveyWindow(entityId);
		}
	}
	
	/**
	 * 显示测绘管理信息对话框
	 */
	function showBdcSurveyWindow(entityId) {
		$.dialog.open("bdcSurveyController.do?info&entityId=" + entityId, {
			title : "测绘信息",
			width : "500px",
			lock : true,
			resize : false,
			height : "350px",
		}, false);
	};

	function expBdcSurveyGridRecord() {
		var located = $("input[name='Q_T.LOCATED_LIKE']").val();
		var surveyUserId = $("input[name='Q_T.SURVEY_USERID_EQ']").val();
		var surveyStatus = $("input[name='Q_T.SURVEY_STATUS_EQ']").val();
		var sqr = $("input[name='Q_T.SQR_LIKE']").val();
		var exeId = $("input[name='Q_T.EXE_ID_LIKE']").val();
		var url = "bdcSurveyController.do?exportBdcSurveyGridRecord&Q_T.LOCATED_LIKE=" + located;
		url += "&Q_T.SURVEY_USERID_EQ=" + surveyUserId + "&Q_T.SURVEY_STATUS_EQ=" + surveyStatus;
		url += "&Q_T.SQR_LIKE=" + sqr + "&Q_T.EXE_ID_LIKE=" + exeId;
		window.location.href = url;
	}
	
	//测绘状态
	function formatChzt(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>未测绘</b></font>";
		}else if(val=="2"){
			return "<font color='#ff4b4b'><b>测绘中</b></font>";
		}else if (val == "3") {
			return "<font color='#ff4b4b'><b>已测绘</b></font>";
		}
	}
	
	//是否默认
	function formatIsMr(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>是</b></font>";
		}else if(val=="0"){
			return "<font color='#ff4b4b'><b>否</b></font>";
		}
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("BdcSurveyToolbar");
	});
</script>
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
							<a href="#" class="easyui-linkbutton" reskey="ADD_BdcSurvey"
								iconcls="eicon-plus" plain="true"
								onclick="showBdcSurveyWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_BdcSurvey"
								iconcls="eicon-pencil" plain="true"
								onclick="editBdcSurveyGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_BdcSurvey"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteBdcSurveyGridRecord();">删除</a>
<%--                            <a href="#"--%>
<%--                               class="easyui-linkbutton l-btn l-btn-small l-btn-plain" reskey="IM_BdcSurvey"--%>
<%--                               iconcls="eicon-file-excel-o" plain="true"--%>
<%--                               onclick="impBdcSurveyGridRecord();">数据导入</a>--%>
                            <a href="#"
                               class="easyui-linkbutton l-btn l-btn-small l-btn-plain" reskey="EX_BdcSurvey"
                               iconcls="eicon-file-excel-o" plain="true"
                               onclick="expBdcSurveyGridRecord();">EXCEL导出</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BdcSurveyForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;">坐落：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="128" style="width:128px;"
								name="Q_T.LOCATED_LIKE" /></td>
							<td style="width:90px;text-align:right;">测绘公司：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
								style="width:135px;" name="Q_T.SURVEY_USERID_EQ"
								data-options="url:'bdcSurveyController.do?getDrawOrgList',editable:false,method: 'post',valueField:'VALUE',textField:'TEXT',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:90px;text-align:right;">测绘状态：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
								style="width:135px;" name="Q_T.SURVEY_STATUS_EQ"
								data-options="url:'dictionaryController.do?load&amp;typeCode=CHZT',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;">申请人：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="128" style="width:128px;"
								name="Q_T.SQR_LIKE" /></td>
							<td style="width:90px;text-align:right;">申报号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="128" style="width:128px;"
								name="Q_T.EXE_ID_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BdcSurveyToolbar','BdcSurveyGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BdcSurveyForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BdcSurveyGrid" fitcolumns="true" toolbar="#BdcSurveyToolbar"
			method="post" idfield="YW_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="bdcSurveyController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'YW_ID',hidden:true">主键ID</th>
					<th data-options="field:'SURVEY_USERID',hidden:true">测绘公司ID</th>
					<th data-options="field:'LOCATED',align:'left'" width="25%">坐落</th>
					<th data-options="field:'SURVEY_USERNAME',align:'left'" width="20%">测绘公司</th>
					<th data-options="field:'SURVEY_STATUS',align:'left'" width="5%" formatter="formatChzt">测绘状态</th>
					<th data-options="field:'IS_MR',align:'left'" width="5%" formatter="formatIsMr">是否默认</th>
					<th data-options="field:'SQR',align:'left'" width="10%">申请人</th>
					<th data-options="field:'EXE_ID',align:'left'" width="15%" >申报号</th>
					<th data-options="field:'CREATE_NAME',align:'left'" width="10%" >创建人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="10%" >创建日期</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>