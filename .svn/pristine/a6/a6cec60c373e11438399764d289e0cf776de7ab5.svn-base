<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,json2,artdialog"></eve:resources>
<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	/**
	 * 删除调查主题列表记录
	 */
	function deleteosQuestionGridRecord() {
		AppUtil.deleteDataGridRecord("osQuestionController.do?multiDel",
				"osQuestionGrid");
	};
	/**
	 * 编辑调查主题列表记录
	 */
	function editosQuestionGridRecord(topicId) {
		var entityId = AppUtil.getEditDataGridRecord("osQuestionGrid");
		if (entityId) {
			showosQuestionWindow(entityId,topicId);
		}
	}
	/**
	* 调查问题选项列表
	*/
	function listOsOptionGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("osQuestionGrid");
		if (entityId) {
			 $.dialog.open("osOptionController.do?view&questionId=" + entityId, {
				title : "问题选项列表",
				width : "1000px",
				height : "540px",
				lock : true,
				resize : false
			}, false); 
		}
	}
	
	/**
	 * 显示调查主题信息对话框
	 */
	function showosQuestionWindow(entityId,topicId) {
		$.dialog.open("osQuestionController.do?info&entityId=" + entityId+"&topicId="+topicId, {
			title : "编辑问题信息",
			width : "600px",
			height : "210px",
			lock : true,
			resize : false,
			close: function () {
				$("#osQuestionGrid").datagrid("reload");
			}
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("osQuestionToolbar");
	});
	function reloadosQuestionList(){
		$("#osQuestionGrid").datagrid("reload");
	}
	

function updateSn(){
	var rows = $("#osQuestionGrid").datagrid("getRows"); 
	var ids = [];
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].QUESTIONID);
	}
	if(ids.length>0){
		AppUtil.ajaxProgress({
			url:"osQuestionController.do?updateSn",
			params:{
				ids:ids
			},
			callback:function(resultJson){
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				$("#osQuestionGrid").datagrid("reload");
			}
		})
	}
}
function formatType(val,row){
	if(val=="select"){
		return "单选";
	}else if(val=="checkbox"){
		return "多选";
	}else if(val=="input"){
		return "文本框";
	}else if(val=="textarea"){
		return "文本域";
	}
};
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	    <div  id="CodeProjectFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TOPICID" value="${osTopic.TOPICID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">${osTopic.TITLE}</td>
			</tr>
				
		</table>
		
		<div class="easyui-layout" fit="true"  style="width: 100%;height:380px;">
			<div region="center">
				<!-- =========================查询面板开始========================= -->
				<div id="osQuestionToolbar">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#"
										class="easyui-linkbutton" reskey="ADD_OSTOPIC"
										iconcls="icon-note-add" plain="true"
										onclick="showosQuestionWindow('','${osTopic.TOPICID}');">新增</a> 
									<a href="#"
										class="easyui-linkbutton" reskey="EDIT_OSTOPIC"
										iconcls="icon-note-edit" plain="true"
										onclick="editosQuestionGridRecord('${osTopic.TOPICID}');">编辑</a> 
									<a href="#"
										class="easyui-linkbutton" reskey="DEL_OSTOPIC"
										iconcls="icon-note-delete" plain="true"
										onclick="deleteosQuestionGridRecord();">删除</a>
									<a href="#" class="easyui-linkbutton" resKey="SAVE_QUESTIONSN" iconCls="icon-tick" plain="true"
										onclick="updateSn();">保存排序</a>
										
									<a href="#"
										class="easyui-linkbutton" reskey="LIST_OSQUESTION"
										iconcls="icon-foldertable" plain="true"
										onclick="listOsOptionGridRecord();">选项列表</a>
								</div>
							</div>
						</div>
					</div>
				<form action="#" name="osQuestionForm"> 
					<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
					 <tbody>
					  <tr style="height:28px;"> 
						<td style="width:70px;text-align:right;">调查主题：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.TITLE_LIKE"/>
						</td> 
					   
					   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('osQuestionToolbar','osQuestionGrid')" />
						   <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('osQuestionForm')" />
						</td> 
					  </tr> 
					 </tbody>
					</table> 
				</form> 
				</div>
				<!-- =========================查询面板结束========================= -->
				<!-- =========================表格开始==========================-->
				<table class="easyui-datagrid" rownumbers="true" pagination="true"
					id="osQuestionGrid" fitcolumns="true" toolbar="#osQuestionToolbar"
					method="post" idfield="QUESTIONID" checkonselect="false" 
					selectoncheck="false" fit="true" border="false" 
					nowrap="false" singleSelect="false" 
					data-options="onLoadSuccess:function(){
						$(this).datagrid('enableDnd');
					}"
					url="osQuestionController.do?datagrid&TOPICID=${osTopic.TOPICID}" >
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'QUESTIONID',hidden:true" width="80">QUESTIONID</th>	
							<th data-options="field:'TITLE',align:'left'" width="280">问题标题</th>
							<th data-options="field:'TYPE',align:'left'" width="110" formatter="formatType">问题类型</th>
							<th data-options="field:'SEQNUM',align:'left'" width="110">排序编号</th>
						</tr>
					</thead>
				</table>
				<!-- =========================表格结束==========================-->
			</div>
		</div>
		</div>
		<div class="eve_buttons">
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
</body>
