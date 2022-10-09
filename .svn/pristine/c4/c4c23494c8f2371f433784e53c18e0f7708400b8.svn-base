<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
	.formatButton {
	    color: #fff !important;
	    background: #0c83d3;
	    cursor: pointer;
	    padding: 0 20px;
	    height: 25px;
	    border: 1px solid #0c83d3;
	    vertical-align: top;
	    border-radius: 3px;
	    display: inline-block;
	    line-height: 24px;
	    margin: 5px;
	}
	.formatButton:hover{
	    opacity: .8;
	}
</style>
<script type="text/javascript">

	$(document).ready(function() {
		AppUtil.initAuthorityRes("projectRecallToolbar");
	});
	/**
	 * 显示项目信息信息对话框
	 */
	function showCheckWindow(entityId) {
		$.dialog.open("projectDetailController.do?recallCheckSelector&entityId=" + entityId, {
			title : "撤件审核信息",
			width : "800px",
			height : "520px",
			lock : true,
			resize : false
		}, false);
	};
	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#projectRecallToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	function formatButton(value, row, index) {
        return '<a href="#" class="formatButton" onclick="showCheckWindow('+"'"+row.YW_ID+"'"+');">审核</a>';
    }
	function formatCheckStatus(status){
		if(status=='0'){
			return '撤件办理中';
		}else if(status=='1'){
			return '通过';
		}else{
			return '不通过';
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="projectRecallToolbar">
			<form action="#" name="projectDetailForm"> 
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
				    <tbody>
					    <tr style="height:28px;"> 
					        <td style="width:70px;text-align:right;">项目代码：</td> 
					        <td style="width:156px;"> 
								<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_CODE_LIKE"/>
						    </td> 
						   	<td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('projectRecallToolbar','projectRecallGrid')" />
						       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('projectDetailForm')" />
						    </td>
					    </tr>
				    </tbody>
				</table> 
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="projectRecallGrid" fitcolumns="true" toolbar="#projectRecallToolbar"
			method="post" idfield="ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectDetailController.do?projectRecallData&Q_T.CREATOR_ID_EQ=">
			<thead>
				<tr>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'PROJECT_CODE',align:'left'" width="13%">项目代码</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="29%">项目名称</th>
					<th data-options="field:'SUBJECT',align:'left'" width="37%">流程标题</th>
					<th data-options="field:'CHECK_STATUS',align:'left'" width="8%" formatter="formatCheckStatus">撤件状态</th>
					<th data-options="field:'operation',align:'left'" width="10%" formatter="formatButton">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
