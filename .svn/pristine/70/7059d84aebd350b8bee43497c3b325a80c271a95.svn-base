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
		AppUtil.initAuthorityRes("projectDetailToolbar");
	});
	/**
	 * 显示项目信息信息对话框
	 */
	function showProjectDetailWindow(entityId) {
		$.dialog.open("projectDetailController.do?info&entityId=" + entityId, {
			title : "编辑项目信息",
			width : "1200px",
			height : "610px",
			lock : true,
			resize : false
		}, false);
	};
	/**
	 * 项目事项列表
	 */
	function listDetailItemGridRecord(entityId, flowCateId, projectCode){
		if (entityId) {
			var $dataGrid = $("#projectDetailGrid");
			var rowsData = $dataGrid.datagrid("getChecked");
			 $.dialog.open("projectDetailController/ItemDetailSelector.do?PROJECT_CODE="+projectCode+"&FLOW_CATE_ID=" + flowCateId, {
				title : "事项列表",
				width : "1200px",
				height : "700px",
				lock : true,
				resize : false,
				close : function() {
					var selectProjectItemInfo = art.dialog.data("selectProjectItemInfo");
					if (selectProjectItemInfo) {
						var defKey = selectProjectItemInfo.defKeys;
						var itemCode = selectProjectItemInfo.itemCodes;													
						art.dialog.removeData("selectProjectItemInfo");	
						toUrl("executionController.do?goStart&defKey="
								+ defKey + "&itemCode=" + itemCode,PROJECT_CODE);
					}
				}
			}, false); 
		}
	}
	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#projectDetailToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	function formatButton(value, row, index) {
        return '<a href="#" class="formatButton" onclick="showProjectDetailWindow('+"'"+row.ID+"'"+');">项目信息</a>'+
        	'&nbsp;<a href="#" class="formatButton" onclick="listDetailItemGridRecord('+"'"+row.ID+"'"+','+"'"+row.FLOW_CATE_ID+
        			"'"+','+"'"+row.PROJECT_CODE+"'"+');">项目事项</a>';
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="projectDetailToolbar">
			<form action="#" name="projectDetailForm"> 
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
				    <tbody>
					    <tr style="height:28px;"> 
					        <td style="width:70px;text-align:right;">项目代码：</td> 
					        <td style="width:156px;"> 
								<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_CODE_LIKE"/>
						    </td> 
					        <td style="width:70px;text-align:right;">项目名称：</td> 
					        <td style="width:156px;"> 
								<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_NAME_LIKE"/>
						    </td> 
						   
						   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('projectDetailToolbar','projectDetailGrid')" />
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
			id="projectDetailGrid" fitcolumns="true" toolbar="#projectDetailToolbar"
			method="post" idfield="ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectDetailController.do?projectListData&Q_T.CREATOR_ID_EQ=">
			<thead>
				<tr>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'PROJECT_CODE',align:'left'" width="20%">项目代码</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="30%">项目名称</th>
					<th data-options="field:'stageName',align:'left'" width="20%">进展情况</th>
					<th data-options="field:'operation',align:'left'" width="27%" formatter="formatButton">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
