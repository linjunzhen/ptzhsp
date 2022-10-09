<script type="text/javascript">

${ComboTreeFnAndSetContent}
/**
 * 删除${MainChineseName}列表记录
 */
function delete${MainClassName}GridRecord() {
	AppUtil.deleteDataGridRecord(
			"${MainClassName?uncap_first}Controller.do?multiDel", "${MainClassName}Grid");
};
/**
 * 编辑${MainChineseName}列表记录
 */
function edit${MainClassName}GridRecord(){
	var entityId = AppUtil.getEditDataGridRecord("${MainClassName}Grid");
	if(entityId){
		show${MainClassName}Window(entityId);
	}
}

/**
 * 显示${MainChineseName}信息对话框
 */
function show${MainClassName}Window(entityId) { 
	$.dialog.open("${MainClassName?uncap_first}Controller.do?info&entityId="+entityId, {
		title : "${MainChineseName}信息",
        width:"600px",
        height:"400px",
        lock: true,
        resize:false
	}, false);
};

$(document).ready(function(){
	${bindDateControlContent}
	AppUtil.initAuthorityRes("${MainClassName}Toolbar");
	${InitComboTreeContent}
});
</script>

<!-- =========================查询面板开始========================= -->
<div id="${MainClassName}Toolbar">
<!--====================开始编写隐藏域============== -->
<!--====================结束编写隐藏域============== --> 
	<div class="right-con">
		<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
			<div class="e-toolbar-ct">
				<div class="e-toolbar-overflow">
					<a href="#" class="easyui-linkbutton" resKey="ADD_${MainClassName}" iconCls="icon-note-add"
						plain="true" onclick="show${MainClassName}Window();">新建${MainChineseName}</a>
					<a href="#" class="easyui-linkbutton" resKey="EDIT_${MainClassName}" iconCls="icon-note-edit"
						plain="true" onclick="edit${MainClassName}GridRecord();">编辑${MainChineseName}</a>
					<a href="#" class="easyui-linkbutton" resKey="DEL_${MainClassName}" iconCls="icon-note-delete" 
					   plain="true" onclick="delete${MainClassName}GridRecord();">删除${MainChineseName}</a>
				</div>
			</div>
		</div>
	</div>
	<form action="#" name="${MainClassName}Form" >
	<table class="dddl-contentBorder dddl_table" 
		style="background-repeat:repeat;width:100%;border-collapse:collapse;">
		 ${QueryConfigContent}
	</table>
	</form>
</div>
<!-- =========================查询面板结束========================= -->
      
<!-- =========================表格开始==========================-->
<table class="easyui-datagrid" rownumbers="true" pagination="true"
	id="${MainClassName}Grid" fitColumns="false" toolbar="#${MainClassName}Toolbar"
	method="post" idField="${MainPrimaryKey}" checkOnSelect="false"
	selectOnCheck="false" fit="true" border="false"
	url="${MainClassName?uncap_first}Controller.do?datagrid">
	<thead>
		<tr>
			<th field="ck" checkbox="true"></th>
			
			<#list gridColumns as gridColumn>
			   <#if gridColumn.columnName==MainPrimaryKey >
			   <th data-options="field:'${MainPrimaryKey}',hidden:true" width="80">${MainPrimaryKey}</th>
			   <#else>
			   <th data-options="field:'${gridColumn.columnName}',align:'left'" width="100">${gridColumn.comments}</th>
			   </#if>
	        </#list>
		</tr>
	</thead>
</table>
<!-- =========================表格结束==========================-->
${comboTreeDiv}

