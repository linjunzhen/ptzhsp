<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
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
		AppUtil.initAuthorityRes("projectFullTestToolbar");
	});
	/**
	 * 显示项目信息信息对话框
	 */
	function downloadFile(fullTestFileId) {
		if(fullTestFileId){
			AppUtil.downLoadFile(fullTestFileId);
		}else{
			art.dialog({
				content : "获取文件信息错误，下载失败！",
				icon : "error",
				ok : true
			});
		}
	};
	function formatButton(value, row, index) {
        return '<a href="#" class="formatButton" onclick="downloadFile('+"'"+row.FULLTESTFILEID+"'"+');">附件下载</a>';
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="projectFullTestToolbar">
			<form action="#" name="projectFullTestForm"> 
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
						   
						   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('projectFullTestToolbar','projectFullTestGrid')" />
						       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('projectFullTestForm')" />
						    </td> 
					    </tr>
				    </tbody>
				</table> 
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="projectFullTestGrid" fitcolumns="true" toolbar="#projectFullTestToolbar"
			method="post" idfield="FULLTEST_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectFullTestController.do?fullTestListData">
			<thead>
				<tr>
					<th data-options="field:'FULLTEST_ID',hidden:true">FULLTEST_ID</th>
					<th data-options="field:'FULLTESTFILEID',hidden:true">FULLTESTFILEID</th>
					<th data-options="field:'PROJECT_CODE',align:'left'" width="15%">项目代码</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="20%">项目名称</th>
					<th data-options="field:'CHBGLX_NAME',align:'left'" width="15%">报告类型</th>
					<th data-options="field:'REPORT_NAME',align:'left'" width="20%">报告名称</th>
					<th data-options="field:'REPORT_TIME',align:'left'" width="15%">报告时间</th>
					<th data-options="field:'operation',align:'left'" width="15%" formatter="formatButton">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
