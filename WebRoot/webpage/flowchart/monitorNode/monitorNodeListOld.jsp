<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
* 编辑列表记录
*/
function editMonitorNodeGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("monitorNodeGrid");
	if (entityId) {
		showEditNodeWindow(entityId);
	}
}

/**
* 显示信息对话框
*/
function showEditNodeWindow(tacheCode,processId) {
	window.open("monitorNodeController.do?factorMng&tacheCode=" + tacheCode+"&processId="+processId,"showFactorWin");
}
function showViewNodeWindow(tacheCode,processId) {
	window.open("monitorNodeController.do?factorView&tacheCode=" + tacheCode+"&processId="+processId,"showViewFactorWin");
}
function editMonitorNode(tacheCode,processId) {
	//var entityId = AppUtil.getEditDataGridRecord("monitorNodeGrid");
	if (tacheCode) {
		showEditNodeWindow(tacheCode,processId);
	}
}
function viewMonitorNode(tacheCode,processId) {
	if (tacheCode) {
		showViewNodeWindow(tacheCode,processId);
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
function formatOperate(val,row){
	if(typeof(row.STATUS)!="undefined"){
		if((row.STATUS!="2"&&row.STATUS!="3")&&row.CHANGE_FLAG!="1"){
			return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editMonitorNode(\''+row.TACHE_CODE+'\',\''+row.PROCESS_ID+'\');">编辑</a>';
		//return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editMonitorNode(\''+row.TACHE_CODE+'\');">编辑</a>';
		}else{
			return '<a href="#" iconcls="icon-note-view" class="easyui-linkbutton" onclick="viewMonitorNode(\''+row.TACHE_CODE+'\',\''+row.PROCESS_ID+'\');">查看</a>';
		}	
	}else{
		if(row.TSTATUS=="1"||row.TSTATUS=="4"){
			return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editMonitorNode(\''+row.TACHE_CODE+'\',\''+row.PROCESS_ID+'\');">编辑</a>';
		}else{
			return '<a href="#" iconcls="icon-note-view" class="easyui-linkbutton" onclick="viewMonitorNode(\''+row.TACHE_CODE+'\',\''+row.PROCESS_ID+'\');">查看</a>';
		}
	}
}

function reloadNodeList(){
	$("#monitorNodeGrid").datagrid("reload");
}
</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="monitorNodeToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== 
   <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="EDIT_FACTOR"
								iconcls="icon-note-edit" plain="true"
								onclick="editMonitorNodeGridRecord();">编辑监察节点</a> 
						</div>
					</div>
				</div>
			</div> --> 
			
			 
   <form action="#" name="monitorNodeForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:65px;text-align:right;">业务专项名称</td> 
       <td style="width:136px;" align="left"> <input class="eve-input" type="text" maxlength="50" style="width:86%;" name="Q_U.BUS_NAME_LIKE" /></td> 
        <td style="width:50px;text-align:right;">监察点名称</td>  
	   <td style="width:116px;margin: 0px;"> <input class="eve-input" type="text" maxlength="50" style="width:86%;" name="Q_U.PROCESS_NAME_LIKE" /></td> 
      </tr> 
      <tr style="height:28px;">
      	<td style="width:50px;text-align:right;">要素名称</td>  
       <td style="width:116px;"> 
       		<input class="eve-input" type="text" maxlength="50" style="width:86%;" name="Q_U.SUPER_ELEMENTS_LIKE" />
	   </td> 
	   <td style="width:38px;text-align:right;">状态</td> 
       <td style="width:125px;">
		    <input class="easyui-combobox" style="width:100px;" name="Q_U.STATUS_EQ"  
					data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
					method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 100,panelHeight: 'auto' " /> 
	    </td>
	   <td style="width: 200px;"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('monitorNodeToolbar','monitorNodeGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('monitorNodeForm')" />
	    </td> 
		<td width="60px"></td>
      </tr>
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="false" pagination="true" id="monitorNodeGrid" fitcolumns="false"
   toolbar="#monitorNodeToolbar" method="post" checkonselect="false" selectoncheck="false" fit="true" border="false" 
   		url="monitorNodeController.do?datagrid"> 
   <thead> 
    <tr> 
    <!-- <th field="ck" checkbox="true"></th>  --> 
     <th data-options="field:'TTACHE_CODE',hidden:true" width="50">TTACHE_CODE</th> 
     <th data-options="field:'BUS_NAME',align:'left'" width="166" >业务专项名称</th> 
     <th data-options="field:'TACHE_NAME',align:'left'" width="126">业务环节</th> 
     <th data-options="field:'PROCESS_NAME',align:'left'" width="169">监察点名称</th> 
     <th data-options="field:'SUPER_ELEMENTS',align:'left'" width="160">要素名称</th> 
     <!--<th data-options="field:'RULE_DESC',align:'left'" width="160">要素描述</th> -->
     <th data-options="field:'UPDATE_TIME',align:'left'" width="136">更新时间</th>
     <th data-options="field:'STATUS',align:'left'" width="116" formatter="formatState">状态</th>  
     <th data-options="field:'UPDATE_USER',align:'left'" width="80" formatter="formatOperate">操作</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>