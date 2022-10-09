<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
* 编辑列表记录
*/
function editBusSpeGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("busSpeGrid");
	if (entityId) {
		showBusSpeWindow(entityId);
	}
}

function reloadBusSpList(){
	$("#busSpeGrid").datagrid("reload");
}

/**
* 编辑列表记录
*/
function viewBusSpeGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("busSpeGrid");
	if (entityId) {
		showBusSpeViewWindow(entityId);
	}
}
/**
* 显示信息对话框
*/
function showBusSpeWindow(entityId) {
	//window.top.location.href="loginController.do?logout";
	window.open("flowChartController.do?flowdraw&entityId=" + entityId,"showFlowWin");
}
/**
* 查看对话框
*/
function showBusSpeViewWindow(entityId) {
	window.open("flowChartController.do?flowview&entityId=" + entityId,"_blank");
}
/**
* 编辑列表记录
*/
function editBusSpe(entityId) {
	if (entityId) {
		showBusSpeWindow(entityId);
	}
}
/**
* 编辑列表记录
*/
function viewBusSpe(entityId) {
	if (entityId) {
		showBusSpeViewWindow(entityId);
	}
}
/**
 * 格式化
 */
function formatOperate(val,row){
	if(row.TSTATUS=="0"){
		return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editBusSpe(\''+row.BUS_ID+'\');">编辑</a>';
	}else{
		return '<a href="#" class="easyui-linkbutton" onclick="viewBusSpe(\''+row.BUS_ID+'\');">查看</a>';
	}
}
function formatState(val,row){
	if(row.TSTATUS=="0"){
		return "暂存";
	}else if(row.TSTATUS=="1"){
		return "已确认";
	}else if(row.TSTATUS=="2"){
		return "待审核";
	}else if(row.TSTATUS=="3"){
		return "审核通过";
	}else if(row.TSTATUS=="4"){
		return "审核不通过";
	}
};

$(document).ready(function(){

});
</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="busSpeToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域==============  
   <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="EDIT_FLOW"
								iconcls="icon-note-edit" plain="true"
								onclick="editBusSpeGridRecord();">编辑流程图</a> 
							<a href="#" class="easyui-linkbutton" reskey="VIEW_FLOW"
								iconcls="icon-note-edit" plain="true"
								onclick="viewBusSpeGridRecord();">查看流程图</a>	
						</div>
					</div>
				</div>
			</div>
			-->
			 
   <form action="#" name="busSpeForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:70px;text-align:right;">专项名称</td> 
       <td style="width:156px;"> <input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.BUS_NAME_LIKE" /></td> 
        <td style="width:80px;text-align:right;">专项编码</td>  
       <td style="width:126px;"> 
       		<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.BUS_CODE_LIKE" />
	   </td> 
	   <td style="width:50px;text-align:right;">状态</td> 
       <td style="width:105px;">
		    <input class="easyui-combobox" style="width:99%;" name="Q_T.STATUS_EQ"  
					data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
					method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 100,panelHeight: 'auto' " /> 
	    </td>
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('busSpeToolbar','busSpeGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('busSpeForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="busSpeGrid" fitcolumns="true"
   toolbar="#busSpeToolbar" method="post" idfield="BUS_ID" checkonselect="false"
   data-options="pageSize:15,pageList:[15,30,50,100]" nowrap="false"
    selectoncheck="false" fit="true" border="false" singleSelect="true"
   		url="flowChartController.do?datagrid"> 
   <thead> 
    <tr> 
     <th data-options="field:'BUS_ID',hidden:true" width="80">BUS_ID</th> 
     <th data-options="field:'BUS_CODE',align:'left'" width="136">业务专项编号</th> 
     <th data-options="field:'BUS_NAME',align:'left'" width="230" >业务专项名称</th> 
     <th data-options="field:'UNIT_CODE',align:'left'" width="180">业务单位编码</th> 
      <th data-options="field:'UPDATE_TIME',align:'left'" width="146">更新时间</th>  
     <th data-options="field:'TSTATUS',align:'left'" width="136" formatter="formatState">状态</th> 
     <th data-options="field:'UPDATE_USER',align:'left'" width="126" formatter="formatOperate">操作</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>