<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
*发起变更
**/

function changeMonitorNodeGrid(entityId) {
	$.dialog.open("processChange.do?changeSelect", {
		title : "发起变更",
        width:"500px",
        lock: true,
        resize:false,
        height:"200px",
        close: function () {
			$("#processChangeGrid").datagrid("reload");
		}
	}, false);
}

function viewProcessChange(busCode,applyId) {
	if (busCode) {
		showViewChangeWindow(busCode,applyId);
	}
}
function showViewChangeWindow(busCode,applyId) {
	window.open("processChange.do?factorView&busCode=" + busCode+"&applyId="+applyId,"showViewChangeFactorWin");
}
/**
* 显示信息对话框
*/
function showEditProcessChange(busCode,applyId) {
	window.open("processChange.do?factorMng&busCode=" + busCode+"&applyId="+applyId,"showChangeFactorWin");
}
function editProcessChange(busCode,applyId) {
	//var entityId = AppUtil.getEditDataGridRecord("monitorNodeGrid");
	if (busCode) {
		showEditProcessChange(busCode,applyId);
	}
}
/**
 * 格式化
 */
function formatState(val,row){
	if(row.STATUS=="0"){
		return "暂存";
	}else if(row.STATUS=="1"){
		return "已确认";
	}else if(row.STATUS=="2"){
		return "待审核";
	}else if(row.STATUS=="3"){
		return "审核通过";
	}else if(row.STATUS=="4"){
		return "审核不通过";
	}
};
/**
 * 格式化
 */
function formatProcessOper(val,row){
	if(typeof(row.STATUS)!="undefined"){
		if(row.STATUS!="2"&&row.STATUS!="3"){
			return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editProcessChange(\''+row.BUS_CODE+'\',\''+row.APPLY_ID+'\');">编辑</a>';
		}else{
			return '<a href="#" iconcls="icon-note-view" class="easyui-linkbutton" onclick="viewProcessChange(\''+row.BUS_CODE+'\',\''+row.APPLY_ID+'\');">查看</a>';
		}	
	}else{
		if(row.TACHE_STATUS=="1"||row.TACHE_STATUS=="4"){
			return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editProcessChange(\''+row.BUS_CODE+'\',\''+row.APPLY_ID+'\');">编辑</a>';
		}else{
			return '<a href="#" iconcls="icon-note-view" class="easyui-linkbutton" onclick="viewProcessChange(\''+row.BUS_CODE+'\',\''+row.APPLY_ID+'\');">查看</a>';
		}
	}
}

$(document).ready(function(){

});
function reloadNodeList(){
	$("#processChangeGrid").datagrid("reload");
}
</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="processChangeToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域==============  
   <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="EDIT_Process"
								iconcls="icon-note-edit" plain="true"
								onclick="changeMonitorNodeGrid();">发起变更</a> 
						</div>
					</div>
				</div>
			</div>
			-->
			 
   <form action="#" name="processChangeForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;">
      	<td style="width:70px;text-align:right;">专项名称</td> 
       <td style="width:156px;"> <input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.BUS_NAME_LIKE" /></td> 
	   <td style="width:70px;text-align:right;">状态</td>
	   <td style="width:125px;">
		    <input class="easyui-combobox" style="width:96%;" name="Q_U.STATUS_EQ"  
					data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
					method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 100,panelHeight: 'auto' " /> 
	    </td>
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('processChangeToolbar','processChangeGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('processChangeForm')" />
	    </td> 
      </tr>
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="processChangeGrid" fitcolumns="true"
   toolbar="#processChangeToolbar" method="post" checkonselect="false" selectoncheck="false" fit="true" border="false" 
   data-options="pageSize:15,pageList:[15,30,50,100]" nowrap="false" singleSelect="true"
   		url="processChange.do?datagrid"> 
   <thead> 
    <tr> 
     <!-- <th field="ck" checkbox="true"></th>  -->  
     <th data-options="field:'BUS_CODE',align:'left'" width="136" >业务专项编码</th> 
     <th data-options="field:'BUS_NAME',align:'left'" width="156">业务专项名称</th> 
     <th data-options="field:'UNIT_CODE',align:'left'" width="130">业务单位编码</th> 
     <th data-options="field:'N_PROCESS',align:'left'" width="100">监察点数量</th> 
     <th data-options="field:'N_ELEMENTS',align:'left'" width="100">监察要素数量</th>
     <th data-options="field:'UPDATE_TIME',align:'left'" width="146">更新时间</th>
     <th data-options="field:'STATUS',align:'left'" width="100" formatter="formatState">状态</th>  
     <th data-options="field:'UPDATE_USER',align:'left'" width="80" formatter="formatProcessOper">操作</th>  
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>