<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,json2,artdialog"></eve:resources>
<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	/**
	 * 删除调查主题列表记录
	 */
	function deleteosOptionGridRecord() {
		AppUtil.deleteDataGridRecord("osOptionController.do?multiDel",
				"osOptionGrid");
	};
	/**
	 * 编辑调查主题列表记录
	 */
	function editosOptionGridRecord(questionId) {
		var entityId = AppUtil.getEditDataGridRecord("osOptionGrid");
		if (entityId) {
			showosOptionWindow(entityId,questionId);
		}
	}
	
	/**
	* 选项投票记录列表
	*/
	function listOsTicketGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("osOptionGrid");
		if (entityId) {
			 $.dialog.open("osTicketController.do?view&optionId=" + entityId, {
				title : "选项投票记录列表",
				width : "800px",
				height : "540px",
				lock : true,
				resize : false
			}, false); 
		}
	}
	/**
	 * 编辑选项信息
	 */
	function showosOptionWindow(entityId,questionId) {
		$.dialog.open("osOptionController.do?info&entityId=" + entityId+"&questionId="+questionId, {
			title : "编辑选项信息",
			width : "600px",
			height : "260px",
			lock : true,
			resize : false,
			close: function () {
				$("#osOptionGrid").datagrid("reload");
			}
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("osOptionToolbar");
	});
	function reloadosOptionList(){
		$("#osOptionGrid").datagrid("reload");
	}
	

function updateSn(){
	var rows = $("#osOptionGrid").datagrid("getRows"); 
	var ids = [];
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].OPTIONID);
	}
	if(ids.length>0){
		AppUtil.ajaxProgress({
			url:"osOptionController.do?updateSn",
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
				$("#osOptionGrid").datagrid("reload");
			}
		})
	}
};
/**
 * 格式化
 */
function formatType(val,row){
	if(val=="0"){
		return "取真实数据";
	}else if(val=="1"){
		return "取附加票数";
	}else{
		return "取总和";
	}
};
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	    <div  id="CodeProjectFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="QUESTIONID" value="${osQuestion.QUESTIONID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">${osQuestion.TITLE}</td>
			</tr>
				
		</table>
		
		<div class="easyui-layout" fit="true"  style="width: 100%;height:380px;">
			<div region="center">
				<!-- =========================查询面板开始========================= -->
				<div id="osOptionToolbar">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#"
										class="easyui-linkbutton" reskey="ADD_OSTOPIC"
										iconcls="icon-note-add" plain="true"
										onclick="showosOptionWindow('','${osQuestion.QUESTIONID}');">新增</a> 
									<a href="#"
										class="easyui-linkbutton" reskey="EDIT_OSTOPIC"
										iconcls="icon-note-edit" plain="true"
										onclick="editosOptionGridRecord('${osQuestion.QUESTIONID}');">编辑</a> 
									<a href="#"
										class="easyui-linkbutton" reskey="DEL_OSTOPIC"
										iconcls="icon-note-delete" plain="true"
										onclick="deleteosOptionGridRecord();">删除</a>
									<a href="#" class="easyui-linkbutton" resKey="SAVE_QUESTIONSN" iconCls="icon-tick" plain="true"
										onclick="updateSn();">保存排序</a>
										
									<a href="#"
										class="easyui-linkbutton" reskey="LIST_OSQUESTION"
										iconcls="icon-foldertable" plain="true"
										onclick="listOsTicketGridRecord();">投票记录</a>
								</div>
							</div>
						</div>
					</div>
				<form action="#" name="osOptionForm"> 
					<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
					 <tbody>
					  <tr style="height:28px;"> 
						<td style="width:70px;text-align:right;">选项内容：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.CONTENT_LIKE"/>
						</td> 
					   
					   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('osOptionToolbar','osOptionGrid')" />
						   <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('osOptionForm')" />
						</td> 
					  </tr> 
					 </tbody>
					</table> 
				</form> 
				</div>
				<!-- =========================查询面板结束========================= -->
				<!-- =========================表格开始==========================-->
				<table class="easyui-datagrid" rownumbers="true" pagination="true"
					id="osOptionGrid" fitcolumns="true" toolbar="#osOptionToolbar"
					method="post" idfield="OPTIONID" checkonselect="false" 
					selectoncheck="false" fit="true" border="false" 
					nowrap="false" singleSelect="false" 
					data-options="onLoadSuccess:function(){
						$(this).datagrid('enableDnd');
					}"
					url="osOptionController.do?datagrid&questionId=${osQuestion.QUESTIONID}" >
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'OPTIONID',hidden:true" width="80">OPTIONID</th>	
							<th data-options="field:'TITLE',align:'left'" width="80">选项标题</th>
							<th data-options="field:'CONTENT',align:'left'" width="280">选项内容</th>
							<th data-options="field:'COUNTTYPE',align:'left'" width="100"  formatter="formatType">统计方式</th>
							<th data-options="field:'TICKETS',align:'left'" width="80">真实票数</th>
							<th data-options="field:'APPENDTICKETS',align:'left'" width="80">附加票数</th>
							<th data-options="field:'SEQNUM',align:'left'" width="80">排序编号</th>
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
