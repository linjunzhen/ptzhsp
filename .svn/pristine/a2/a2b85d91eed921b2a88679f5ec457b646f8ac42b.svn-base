<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
function deleteContactGridRecord() {
	AppUtil.deleteDataGridRecord(
			"busContactController.do?multiDel", "ContactGrid");
};
function editContactGridRecord(){
	var entityId = AppUtil.getEditDataGridRecord("ContactGrid");
	if(entityId){
		$.dialog.open("busContactController.do?info&entityId="+entityId, {
			title : "联系人信息",
			width : "600px",
			height : "280px",
			lock : true,
			resize : false,
			close: function () {
				$("#ContactGrid").datagrid("reload");
			}
		}, false);
	}
}

function addContactGridRecord(){
	$.dialog.open("busContactController.do?info", {
		title : "联系人信息",
		width : "600px",
		height : "280px",
		lock : true,
		resize : false,
		close: function () {
			$("#ContactGrid").datagrid("reload");
		}
	}, false);
};

function formatContactType(val,row){
	//0：业务处室，1:信息科室，2：系统开发商或负责人，3：经办，4：对接技术人员
	if(row.CONTACT_TYPE=="0"){
		return "<font color='blue'><b>业务处室</b></font>";
	}else if(row.CONTACT_TYPE=="1"){
		return "<font color='blue'><b>信息科室</b></font>";
	}else if(row.CONTACT_TYPE=="2"){
		return "<font color='blue'><b>系统开发商或负责人</b></font>";
	}else if(row.CONTACT_TYPE=="3"){
		return "<font color='blue'><b>经办</b></font>";
	}else if(row.CONTACT_TYPE=="4"){
		return "<font color='blue'><b>对接技术人员</b></font>";
	}
};

</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="ContractSearchToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
         <div style="color:#005588;">         
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-add" plain="true" 
         onclick="addContactGridRecord();">新增</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-edit" plain="true" 
         onclick="editContactGridRecord();">编辑</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-delete" plain="true" 
         onclick="deleteContactGridRecord();">删除</a>
         </div>
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="ContactSearchForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">人员类型</td> 
       <td style="width:135px;"><input style="width:150px;height:25px;float:left;" class="easyui-combobox" name="Q_L.CONTACT_TYPE_EQ"
			data-options="url:'dictionaryController.do?load&typeCode=ContactType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto'" />
		</td> 
       <td style="width:68px;text-align:right;">业务单位</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.CONTACT_DEPTNAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">业务系统</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.CONTACT_PLATNAME_LIKE" /></td> 
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('ContractSearchToolbar','ContactGrid')" /> <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('ContactSearchForm')" /></td> 
      </tr>     
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="ContactGrid" fitcolumns="false" 
  		toolbar="#ContractSearchToolbar" method="post" 
  		idfield="ID" checkonselect="true" selectoncheck="true" nowrap="false"
  		fit="true" border="false" url="busContactController.do?datagrid"> 
   <thead> 
    <tr>
		<th field="ck" checkbox="true"></th>
		<th data-options="field:'ID',hidden:true" width="80">ID</th>
		<th data-options="field:'CONTACT_DEPTNAME',align:'left'" width="200">业务单位</th>
		<th data-options="field:'CONTACT_PLATNAME',align:'left'" width="180">业务系统</th>
		<th data-options="field:'CONTACT_NAME',align:'left'" width="120">姓名</th>
		<th data-options="field:'CONTACT_TYPE',align:'center'" width="120" formatter="formatContactType">人员类型</th>
		<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
		<th data-options="field:'CONTACT_MOBILE',align:'left'" width="150">手机号码</th>
		<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>						
	</tr>
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>