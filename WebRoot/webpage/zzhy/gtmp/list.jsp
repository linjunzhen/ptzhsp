<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	/**
	 * 删除信息列表记录
	 */
	function deletegtmpGridRecord() {
		AppUtil.deleteDataGridRecord("gtmpController.do?multiDel",
				"gtmpGrid");
	};
	/**
	 * 编辑信息列表记录
	 */
	function editgtmpGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("gtmpGrid");
		if (entityId) {
			showgtmpWindow(entityId);
		}
	}

	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#gtmpToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	/**
	 * 显示信息信息对话框
	 */
	function showgtmpWindow(entityId) {
		$.dialog.open("gtmpController.do?info&entityId=" + entityId, {
			title : "平台信息",
			width : "1200px",
			height : "610px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("gtmpToolbar");
	});
	function reloadgtmpList(){
		$("#gtmpGrid").datagrid("reload");
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
	function formatIsTest(val,row){
		if(val=="1"){
			return "<font color='red'>是(停止推送)</font>";
		}else{			
			return "否(正常推送)";
		}
	}
	function formatIsInter(val,row){
		if(val=="1"){
			return "<font color='#0368ff'>是(已推送)</font>";
		}else{			
			return "否(未推送)";
		}
	}
		
	$(document).ready(function() {
		var start1 = {
			elem : "#GTMP.CREATE_TIME_BEGIN",
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
			elem : "#GTMP.CREATE_TIME_END",
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
	
function gtmpTestStatus(status){
	var $dataGrid = $("#gtmpGrid");
	var rowsData = $dataGrid.datagrid("getChecked");
	var colName = $dataGrid.datagrid("options").idField; 
	var selectColNames = "";
	for ( var i = 0; i < rowsData.length; i++) {
		if(i>0){
			selectColNames+=",";
		}
		selectColNames+=eval('rowsData[i].'+colName);
	}
	if(selectColNames==""){
		art.dialog({
			content: "请选择需要被操作的记录!",
			icon:"warning",
			ok: true
		});
	}else{
		art.dialog.confirm("您确定要更改推送状态吗?", function() {			
			var layload = layer.load('正在提交请求中…');
			$.post("individualController.do?gtmpTestStatus",{
			   selectColNames:selectColNames,
			   status:status
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					AppUtil.gridDoSearch('gtmpToolbar','gtmpGrid')
				}else{
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			});
		});
	}
}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="gtmpToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="GTMPYXTS"
								iconcls="eicon-send-o" plain="true"
								onclick="gtmpTestStatus(0);">允许推送</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="GTMPTZTS"
								iconcls="eicon-trash-o" plain="true"
								onclick="gtmpTestStatus(1);">停止推送</a> 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="gtmpForm"> 
				<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
					<tbody>
						<tr style="height:28px;"> 
							<td style="width:100px;text-align:right;">申报号：</td> 
							<td style="width:156px;"> 
								<input class="eve-input" style="width:128px;float:left;" type="text" maxlength="50" style="width:96%;" name="Q_T.EXE_ID_LIKE"/>
							</td> 
							<td style="width:100px;text-align:right;">平台名称：</td> 
							<td style="width:156px;"> 
								<input class="eve-input" style="width:128px;float:left;" type="text" maxlength="50" style="width:96%;" name="Q_I.PT_NAME_LIKE"/>
							</td> 
							<td style="width:100px;text-align:right;">经营者名称：</td> 
							<td style="width:156px;"> 
								<input class="eve-input" style="width:128px;float:left;" type="text" maxlength="50" style="width:96%;" name="Q_I.DEALER_NAME_LIKE"/>
							</td> 
							<td style="width:68px;text-align:right;">流程状态</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RUN_STATUS_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">当前环节：</td>
							<td style="width:135px;">
								<input class="eve-input"  style="width:128px;float:left;"  type="text" maxlength="20" style="width:128px;"
															name="Q_T.CUR_STEPNAMES_LIKE" />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从：</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="GTMP.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">申请日期至：</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="GTMP.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td>
							<td style="width:68px;text-align:right;">是否测试阶段：</td>
							<td style="width:135px;">
								<input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_I.IS_TEST_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">是否推送：</td>
							<td style="width:135px;">
								<input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_I.PUSH_STATUS_INTER_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('gtmpToolbar','gtmpGrid')" />
							   <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('gtmpForm')" />
							</td> 
						</tr> 
					</tbody>
				</table> 
		   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="gtmpGrid" fitcolumns="true" toolbar="#gtmpToolbar"
			method="post" idfield="EXE_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="individualController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'RUN_STATUS',align:'center'" width="10%" formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'IS_TEST',align:'center'" width="15%" formatter="formatIsTest">是否测试阶段</th>
					<th data-options="field:'CUR_STEPNAMES',align:'center'" width="15%" formatter="formatCurStepnames">当前环节</th>
					<th data-options="field:'CUR_STEPAUDITNAMES',align:'center'" width="10%">当前审核人</th>
					<th data-options="field:'PT_NAME',align:'center'" width="10%">平台名称</th>
					<th data-options="field:'DEALER_NAME',align:'center'" width="10%">经营者名称</th>
					<th data-options="field:'CREATE_TIME',align:'center'" width="15%">申请时间</th>
					<th data-options="field:'END_TIME',align:'center'" width="15%">办结时间</th>
					<th data-options="field:'PUSH_STATUS_INTER',align:'center'" width="15%" formatter="formatIsInter">是否推送</th>
					<th data-options="field:'PUSH_INTER_TIME',align:'center'" width="15%">推送时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
