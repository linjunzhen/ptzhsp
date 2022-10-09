<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

/***
 * 删除数据
 */
function deleteEstimateGridRecord() {
	AppUtil.deleteDataGridRecord(
			"busEstimateController.do?multiDel", "EstimateGrid");
};

/**
 * 新增业务数据估算
 */
function addEstimateWin(){
	$.dialog.open("busEstimateController.do?info", {
		title : "业务数据量估算",
		width : "600px",
		height : "280px",
		lock : true,
		resize : false,
		close: function () {
			$("#EstimateGrid").datagrid("reload");
		}
	}, false);
};

/***
 * 编辑业务数据估算
 */
function editEstimateWin(){
	var entityId = AppUtil.getEditDataGridRecord("EstimateGrid");
	if(entityId){
		$.dialog.open("busEstimateController.do?info&entityId="+entityId, {
			title : "业务数据量估算",
			width : "600px",
			height : "280px",
			lock : true,
			resize : false,
			close: function () {
				$("#EstimateGrid").datagrid("reload");
			}
		}, false);
	}	
};


</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="EstimateToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
         <div style="color:#005588;">         
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-add" plain="true" 
         onclick="addEstimateWin();">新增</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-edit" plain="true" 
         onclick="editEstimateWin();">编辑</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-delete" plain="true" 
         onclick="deleteEstimateGridRecord();">删除</a>
         </div>
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="EstimateForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">业务单位</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.ESTIMATE_DEPTNAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">业务系统</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.ESTIMATE_PLATNAME_LIKE" /></td> 
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('EstimateToolbar','EstimateGrid')" /> <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('EstimateForm')" /></td> 
      </tr>     
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" 
  	id="EstimateGrid" fitcolumns="false" toolbar="#EstimateToolbar" 
  	method="post" idfield="ID" checkonselect="true" selectoncheck="true" nowrap="false"
  	fit="true" border="false" url="busEstimateController.do?datagrid"> 
   <thead> 
    <tr> 
     <th field="ck" checkbox="true"></th> 
     <th data-options="field:'ID',hidden:true" width="80">ID</th> 
     <th data-options="field:'ESTIMATE_DEPTNAME',align:'left'" width="200">业务单位</th> 
     <th data-options="field:'ESTIMATE_PLATNAME',align:'left'" width="200">业务系统</th> 
     <th data-options="field:'ESTIMATE_PERSONNUM',align:'left'" width="120">业务面向的个人总数</th> 
     <th data-options="field:'ESTIMATE_DEPTNUM',align:'left'" width="120">业务面向的单位总数</th> 
     <th data-options="field:'ESTIMATE_YEARNUM',align:'left'" width="120">年业务总数量</th> 
     <th data-options="field:'ESTIMATE_YEARDATASIZE',align:'left'" width="120">年业务数据量大小</th> 
     <th data-options="field:'ESTIMATE_UNSTRUCTSIZE',align:'left'" width="120">非结构化数据量大小</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>