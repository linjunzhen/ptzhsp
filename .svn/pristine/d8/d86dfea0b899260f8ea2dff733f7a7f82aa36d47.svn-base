<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

/***
 * 删除数据
 */
function deleteBasicColumnGridRecord() {
	AppUtil.deleteStatusDataGridRecord(
			"busColumnBasicController.do?multiDel", "BasicColumnGrid");
};

/**
 * 新增
 */
function addBasicColumnWin(){
	$.dialog.open("busColumnBasicController.do?info", {
		title : "基本信息字段配置新增",
		width : "680px",
		height : "480px",
		lock : true,
		resize : false,
		close: function () {
			$("#BasicColumnGrid").datagrid("reload");
		}
	}, false);
};

/***
 * 编辑
 */
function editBasicColumnWin(){
	//var entityId = AppUtil.getEditDataGridRecord("BasicColumnGrid");
	var entityId = AppUtil.getStatusDataGridRecord("BasicColumnGrid");
	if(entityId){
		$.dialog.open("busColumnBasicController.do?info&entityId="+entityId, {
			title : "基本信息字段配置编辑",
			width : "680px",
			height : "480px",
			lock : true,
			resize : false,
			close: function () {
				$("#BasicColumnGrid").datagrid("reload");
			}
		}, false);
	}	
};

/**
 * 确认
 */
function checkBasicColumnRecord(id){
	art.dialog.confirm("您是否要确认该基本信息数据提交业务梳理?", function() {
		AppUtil.ajaxProgress({
			url : "busColumnBasicController.do?submitColumn",
			params : {
				"id" : id
			},	
			callback:function(result){
				if(result.success){
					$("#BasicColumnGrid").datagrid("reload");
				}
			}
		});
	});	
};

//状态 0：暂存；1：确认；2：待审核；3：审核通过；4：审核不通过；5：关闭；
function formatBusColumnStatus(val,row){
	if (row.STATUS==0){
		return "<b><font color=red>暂存</font></b>";
	} else if(row.STATUS==1){
		return "<b><font color=blue>已确认</font></b>";
	}else if(row.STATUS==2){
		return "<b><font color=blue>待审核</font></b>";
	}else if(row.STATUS==3){
		return "<b><font color=blue>审核通过</font></b>";
	}else if(row.STATUS==4){
		return "<b><font color=blue>审核不通过</font></b>";
	}else if(row.STATUS==5){
		return "<b><font color=red>关闭</font></b>";
	}
};

//操作
function formatBusColumnOpt(val,row){
	if (row.STATUS==0){
		return '<a href="#" onclick="checkBasicColumnRecord(\''+row.ID+'\');"><font color="green"><b>确认</b></font></a>';
	}
}


</script>
<div class="easyui-layout" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="BasicColumnToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
         <div style="color:#005588;">         
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-add" plain="true" 
         onclick="addBasicColumnWin();">新增</a>
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-edit" plain="true" 
         onclick="editBasicColumnWin();">编辑</a>
         <!-- <a href="#" class="easyui-linkbutton" iconcls="icon-note-ok" plain="true" 
         onclick="checkBasicColumnRecord();">确认</a> -->
         <a href="#" class="easyui-linkbutton" iconcls="icon-note-delete" plain="true" 
         onclick="deleteBasicColumnGridRecord();">删除</a>
         </div>
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="BasicColumnSearchForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">单位名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_D.UNIT_NAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">系统名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_P.SYSTEM_NAME_LIKE" /></td>
       <td style="width:68px;text-align:right;">专项名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_S.BUS_NAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">状态</td> 
       <td style="width:135px;"> 
       	  <input class="easyui-combobox" style="width:128px;"
				 data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
				 method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 250,panelHeight: 'auto' "
			     name="Q_TT.STATUS_EQ" />
       </td>
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('BasicColumnToolbar','BasicColumnGrid')" /> <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('BasicColumnSearchForm')" /></td> 
      </tr>     
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="BasicColumnGrid" 
  	fitcolumns="true" toolbar="#BasicColumnToolbar" method="post" 
  	data-options="pageSize:15,pageList:[15,30,50,100]" nowrap="false"
  	idfield="ID" checkonselect="true"idfield="ID" 
  	selectoncheck="true" singleSelect="false" checkonselect="true"
  	fit="true" border="false" url="busColumnBasicController.do?datagrid"> 
   <thead> 
    <tr> 
     <th field="ck" checkbox="true"></th> 
     <th data-options="field:'ID',hidden:true" width="80">ID</th>
     <th data-options="field:'UNIT_NAME',align:'left'" width="80">单位名称</th>  
     <th data-options="field:'SYSTEM_NAME',align:'left'" width="100">系统名称</th> 
     <th data-options="field:'BUS_NAME',align:'left'" width="200">专项名称</th> 
     <th data-options="field:'STATUS',align:'center'" formatter="formatBusColumnStatus" width="50">状态</th>
     <th data-options="field:'PROCESS_CODE',align:'center'" formatter="formatBusColumnOpt" width="50">操作</th>
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>