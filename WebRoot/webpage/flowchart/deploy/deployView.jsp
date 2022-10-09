<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
function deleteDeployGridRecord() {
	AppUtil.deleteDataGridRecord(
			"busDeployController.do?multiDel", "DeployGrid");
};
function editDeployGridRecord(){
	var entityId = AppUtil.getEditDataGridRecord("DeployGrid");
	if(entityId){
		$.dialog.open("busDeployController.do?info&entityId="+entityId, {
			title : "编辑部署信息",
			width : "630px",
			height : "470px",
			lock : true,
			resize : false,
			close: function () {
				$("#DeployGrid").datagrid("reload");
			}
		}, false);
	}
}

function addDeployGridRecord(){
	$.dialog.open("busDeployController.do?info", {
		title : "新建部署信息",
		width : "630px",
		height : "470px",
		lock : true,
		resize : false,
		close: function () {
			$("#DeployGrid").datagrid("reload");
		}
	}, false);
};

function formatDeployType(val,row){
	if(row.DEPLOY_TYPE=="0"){
		return "<font color='blue'><b>政务网</b></font>";
	}else if(row.DEPLOY_TYPE=="1"){
		return "<font color='green'><b>互联网</b></font>";
	}
};

function openimgview(val,row){
	return "<a target='_blank' href="+row.DEPLOY_FILEPIC+"><font color='blue'><b>查看</b></font></a>";
};
</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="DeploySearchToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
         <div style="color:#005588;">         
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-add" plain="true" 
         onclick="addDeployGridRecord();">新增</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-edit" plain="true" 
         onclick="editDeployGridRecord();">编辑</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-delete" plain="true" 
         onclick="deleteDeployGridRecord();">删除</a>
         </div>
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="DeploySearchForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">所在单位</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.BUSSYS_UNITNAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">系统名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.BUSSYS_PLATNAME_LIKE" /></td> 
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('DeploySearchToolbar','DeployGrid')" /> <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('DeploySearchForm')" /></td> 
      </tr>     
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="DeployGrid" fitcolumns="false" 
  		toolbar="#DeploySearchToolbar" method="post" 
  		idfield="ID" checkonselect="true" selectoncheck="true" nowrap="false"
  		fit="true" border="false" url="busDeployController.do?datagrid"> 
   <thead> 
    <tr> 
     <th field="ck" checkbox="true"></th> 
     <th data-options="field:'ID',hidden:true" width="80">ID</th> 
     <th data-options="field:'BUSSYS_UNITNAME',align:'left'" width="250">业务单位</th> 
     <th data-options="field:'DEPLOY_TYPE',align:'center'" formatter="formatDeployType" width="150">对接方式</th> 
     <th data-options="field:'BUSSYS_PLATNAME',align:'left'" width="250">系统名称</th> 
     <th data-options="field:'DEPLOY_FILEPIC',align:'center'" formatter="openimgview" width="150">部署图</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>