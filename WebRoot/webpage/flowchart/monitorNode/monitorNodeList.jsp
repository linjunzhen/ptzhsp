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
function showEditNodeWindow(busCode) {
	window.open("monitorNodeController.do?factorMng&busCode="+busCode,"showFactorWin");
}
function showViewNodeWindow(busCode) {
	window.open("monitorNodeController.do?factorView&busCode="+busCode,"showViewFactorWin");
}
function editMonitorNode(busCode) {
	//var entityId = AppUtil.getEditDataGridRecord("monitorNodeGrid");
	if (busCode) {
		showEditNodeWindow(busCode);
	}
}
function viewMonitorNode(busCode) {
	if (busCode) {
		showViewNodeWindow(busCode);
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
		if((row.STATUS!="2"&&row.STATUS!="3")){
			return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editMonitorNode(\''+row.BUS_CODE+'\');">编辑</a>';
		//return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editMonitorNode(\''+row.TACHE_CODE+'\');">编辑</a>';
		}else{
			return '<a href="#" iconcls="icon-note-view" class="easyui-linkbutton" onclick="viewMonitorNode(\''+row.BUS_CODE+'\');">查看</a>';
		}	
	}else{
		if(row.TACHE_STATUS=="1"||row.TACHE_STATUS=="4"){
			return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="editMonitorNode(\''+row.BUS_CODE+'\');">编辑</a>';
		}else{
			return '<a href="#" iconcls="icon-note-view" class="easyui-linkbutton" onclick="viewMonitorNode(\''+row.BUS_CODE+'\');">查看</a>';
		}
	}
}
function formatOperateOld(val,row){
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
	   <td style="width:65px;text-align:right;">专项名称</td> 
       <td style="width:156px;" align="left"> <input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.BUS_NAME_LIKE" /></td>
	   <td style="width:38px;text-align:right;">状态</td> 
       <td style="width:125px;">
		    <input class="easyui-combobox" style="width:96%;" name="Q_U.STATUS_EQ"  
					data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
					method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 100,panelHeight: 'auto' " /> 
	    </td>
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('monitorNodeToolbar','monitorNodeGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('monitorNodeForm')" />
	    </td> 
      </tr>
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="monitorNodeGrid" fitcolumns="true"
   toolbar="#monitorNodeToolbar" method="post" checkonselect="false" selectoncheck="false" fit="true" border="false"
   data-options="pageSize:15,pageList:[15,30,50,100]" nowrap="false" singleSelect="true"
   		url="monitorNodeController.do?datagrid"> 
   <thead> 
    <tr> 
    <!-- <th field="ck" checkbox="true"></th>  --> 
	 <th data-options="field:'BUS_CODE',align:'left'" width="136" >业务专项编码</th>
     <th data-options="field:'BUS_NAME',align:'left'" width="166" >业务专项名称</th> 
     <th data-options="field:'UNIT_CODE',align:'left'" width="139">业务单位编码</th> 
     <th data-options="field:'N_PROCESS',align:'left'" width="106" >监察点数量</th> 
     <th data-options="field:'N_ELEMENTS',align:'left'" width="106">监察要素数量</th> 
     <th data-options="field:'UPDATE_TIME',align:'left'" width="156">更新时间</th>
    <th data-options="field:'STATUS',align:'left'" width="116" formatter="formatState">状态</th>   
     <th data-options="field:'UPDATE_USER',align:'left'" width="80" formatter="formatOperate">操作</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>