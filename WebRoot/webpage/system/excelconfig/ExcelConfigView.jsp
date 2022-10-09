<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">


/**
 * 删除Excel导出配置列表记录
 */
function deleteExcelConfigGridRecord() {
	AppUtil.deleteDataGridRecord(
			"excelConfigController.do?multiDel", "ExcelConfigGrid");
};
/**
 * 编辑Excel导出配置列表记录
 */
function editExcelConfigGridRecord(){
	var entityId = AppUtil.getEditDataGridRecord("ExcelConfigGrid");
	if(entityId){
		showExcelConfigWindow(entityId);
	}
}

/**
 * 显示Excel导出配置信息对话框
 */
function showExcelConfigWindow(entityId) {
	$.dialog.open("excelConfigController.do?info&entityId="+entityId, {
		title : "Excel导出配置信息",
        width:"600px",
        lock: true,
        resize:false,
        height:"500px",
	}, false);
};

$(document).ready(function(){
	
	AppUtil.initAuthorityRes("ExcelConfigToolbar");
	
});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="ExcelConfigToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
       <a href="#" class="easyui-linkbutton" reskey="ADD_ExcelConfig" iconcls="eicon-plus" plain="true" onclick="showExcelConfigWindow();">新建</a> 
       <a href="#" class="easyui-linkbutton" reskey="EDIT_ExcelConfig" iconcls="eicon-pencil" plain="true" onclick="editExcelConfigGridRecord();">编辑</a> 
       <a href="#" class="easyui-linkbutton" reskey="DEL_ExcelConfig" iconcls="eicon-trash-o" plain="true" onclick="deleteExcelConfigGridRecord();">删除</a> 
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="ExcelConfigForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">模版名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.TPL_NAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">模版KEY</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.TPL_KEY_LIKE" /></td> 
       <td colspan="4"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('ExcelConfigToolbar','ExcelConfigGrid')" /> <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('ExcelConfigForm')" /></td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true" id="ExcelConfigGrid" fitcolumns="false" toolbar="#ExcelConfigToolbar" method="post" idfield="CONFIG_ID" checkonselect="true" selectoncheck="true" fit="true" border="false" url="excelConfigController.do?datagrid"> 
   <thead> 
    <tr> 
     <th field="ck" checkbox="true"></th> 
     <th data-options="field:'CONFIG_ID',hidden:true">CONFIG_ID</th> 
     <th data-options="field:'TPL_NAME',align:'left'" width="25%">模版名称</th> 
     <th data-options="field:'TPL_KEY',align:'left'" width="20%">模版KEY</th> 
     <th data-options="field:'START_ROW',align:'left'" width="15%">写入数据的起始行</th> 
     <th data-options="field:'START_COL',align:'left'" width="36%">写入数据的起始列</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>