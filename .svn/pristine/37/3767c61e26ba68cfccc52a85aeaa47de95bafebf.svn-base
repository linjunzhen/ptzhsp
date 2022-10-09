<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,json2,artdialog"></eve:resources>
<script type="text/javascript">
	/**
	 * 删除调查主题列表记录
	 */
	function deleteosTicketGridRecord() {
		AppUtil.deleteDataGridRecord("osTicketController.do?multiDel",
				"osTicketGrid");
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("osTicketToolbar");
	});
	function reloadosTicketList(){
		$("#osTicketGrid").datagrid("reload");
	}
	
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
	    <input type="hidden" name="OPTIONID" value="${osOption.OPTIONID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">${osOption.CONTENT}</td>
			</tr>
				
		</table>
		
		<div class="easyui-layout" fit="true"  style="width: 100%;height:400px;">
			<div region="center">
				<!-- =========================查询面板开始========================= -->
				<div id="osTicketToolbar">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#"
										class="easyui-linkbutton" reskey="DEL_OSTOPIC"
										iconcls="icon-note-delete" plain="true"
										onclick="deleteosTicketGridRecord();">删除</a>
								</div>
							</div>
						</div>
					</div>
				<form action="#" name="osTicketForm"> 
					<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
					 <tbody>
					  <tr style="height:28px;"> 
						<td style="width:70px;text-align:right;">投票内容：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.CONTENT_LIKE"/>
						</td> 
					   
					   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('osTicketToolbar','osTicketGrid')" />
						   <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('osTicketForm')" />
						</td> 
					  </tr> 
					 </tbody>
					</table> 
				</form> 
				</div>
				<!-- =========================查询面板结束========================= -->
				<!-- =========================表格开始==========================-->
				<table class="easyui-datagrid" rownumbers="true" pagination="true"
					id="osTicketGrid" fitcolumns="true" toolbar="#osTicketToolbar"
					method="post" idfield="TICKETID" checkonselect="false" 
					selectoncheck="false" fit="true" border="false" 
					nowrap="false" singleSelect="false" 
					url="osTicketController.do?datagrid&optionId=${osOption.OPTIONID}" >
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'TICKETID',hidden:true" width="80">TICKETID</th>	
							<th data-options="field:'CONTENT',align:'left'" width="200">投票内容</th>
							<th data-options="field:'TYPE',align:'left'" width="80">投票类型</th>
							<th data-options="field:'IP',align:'left'" width="80">投票IP</th>
							<th data-options="field:'SUBMITDATE',align:'left'" width="110">投票时间</th>
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
