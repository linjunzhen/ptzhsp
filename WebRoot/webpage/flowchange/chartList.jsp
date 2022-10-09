<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
*发起变更
**/

function changeBusSpeGrid(entityId) {
	$.dialog.open("flowChange.do?busSpeList", {
		title : "发起变更",
        width:"500px",
        lock: true,
        resize:false,
        height:"200px",
        close: function () {
			$("#flowChangeGrid").datagrid("reload");
		}
	}, false);
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
function showChangeBusSpeWindow(entityId,applyId) {
	//window.top.location.href="loginController.do?logout";
	window.open("flowChange.do?flowchange&entityId=" + entityId+"&applyId="+applyId,"showFlowChangeWin");
}
/**
* 查看对话框
*/
function changeFlowViewWindow(entityId,applyId) {
	window.open("flowChange.do?flowview&entityId=" + entityId+"&applyId="+applyId,"_blank");
}
/**
* 编辑列表记录
*/
function editChangeBusSpe(entityId,applyId) {
	if (entityId) {
		showChangeBusSpeWindow(entityId,applyId);
	}
}
/**
* 编辑列表记录
*/
function viewChangeBusSpe(entityId,applyId) {
	if (entityId) {
		changeFlowViewWindow(entityId,applyId);
	}
}
/**
 * 格式化
 */
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
/**
 * 格式化
 */
function formatOperate(val,row){
	if(row.TSTATUS=="0"||row.TSTATUS=="4"){
		return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editChangeBusSpe(\''+row.BUS_ID+'\',\''+row.APPLY_ID+'\');">编辑</a>';
	}else{
		return '<a href="#" class="easyui-linkbutton" onclick="viewChangeBusSpe(\''+row.BUS_ID+'\',\''+row.APPLY_ID+'\');">查看</a>';
	}
}
function reloadChangeBusSpList(){
	$("#flowChangeGrid").datagrid("reload");
}

$(document).ready(function(){

});
</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="flowChangeToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域==============
   <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="CHANGE_FLOW"
								iconcls="icon-note-edit" plain="true"
								onclick="changeBusSpeGrid();">发起变更</a>	
						</div>
					</div>
				</div>
			</div>
			 --> 
			 
   <form action="#" name="flowChangeForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:70px;text-align:right;padding-left: 0px;margin-left: 0px;">专项名称</td> 
       <td style="width:156px;"> <input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.BUS_NAME_LIKE" /></td> 
        <td style="width:70px;text-align:right;" align="left">专项编码</td>  
       <td style="width:126px;"> 
       		<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.BUS_CODE_LIKE" />
	   </td> 
	   <td style="width:38px;text-align:right;">状态</td> 
       <td style="width:105px;">
		    <input class="easyui-combobox" style="width:96%;" name="Q_T.STATUS_EQ"  
					data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
					method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 100,panelHeight: 'auto' " /> 
	    </td>
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('flowChangeToolbar','flowChangeGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('flowChangeForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="flowChangeGrid" fitcolumns="true"
   toolbar="#flowChangeToolbar" method="post" idfield="BUS_ID" checkonselect="false" 
   data-options="pageSize:15,pageList:[15,30,50,100]" nowrap="false" singleSelect="true"
   selectoncheck="false" fit="true" border="false" 
   		url="flowChange.do?datagrid"> 
   <thead> 
    <tr> 
     <th data-options="field:'BUS_ID',hidden:true" width="80">BUS_ID</th> 
     <th data-options="field:'BUS_CODE',align:'left'" width="156">业务专项编号</th> 
     <th data-options="field:'BUS_NAME',align:'left'" width="190" >业务专项名称</th> 
     <th data-options="field:'UNIT_CODE',align:'left'" width="96">业务单位编码</th> 
     <th data-options="field:'UPDATE_TIME',align:'left'" width="136">更新时间</th> 
     <th data-options="field:'TSTATUS',align:'left'" width="106" formatter="formatState">状态</th>
     <th data-options="field:'UPDATE_USER',align:'left'" width="100" formatter="formatOperate">操作</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>