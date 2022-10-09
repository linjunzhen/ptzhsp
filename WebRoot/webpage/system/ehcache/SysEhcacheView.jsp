<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">


	/**
	 * 删除缓存管理列表记录
	 */
	function deleteSysEhcacheGridRecord() {
	    AppUtil.deleteDataGridRecord(
	            "sysEhcacheController.do?multiDel", "SysEhcacheGrid");
	};
	/**
	 * 编辑缓存管理列表记录
	 */
	function editSysEhcacheGridRecord(){
	    var entityId = AppUtil.getEditDataGridRecord("SysEhcacheGrid");
	    if(entityId){
	        showSysEhcacheWindow(entityId);
	    }
	}
	
	/**
	 * 显示缓存管理信息对话框
	 */
	function showSysEhcacheWindow(entityId) { 
	    $.dialog.open("sysEhcacheController.do?info&entityId="+entityId, {
	        title : "缓存管理信息",
	        width:"600px",
	        height:"400px",
	        lock: true,
	        resize:false
	    }, false);
	};
	
	$(document).ready(function(){
	    
	    AppUtil.initAuthorityRes("SysEhcacheToolbar");
	    
	});
	function formatStatus(val,row){
        if(val=="1"){
            return "<font color='#0368ff'><b>增加</b></font>";
        }else{
            return "<font color='#ff4b4b'><b>删除</b></font>";
        }
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="SysEhcacheToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
       <a href="#" class="easyui-linkbutton" reskey="ADD_SysEhcache" iconcls="eicon-plus" plain="true" onclick="showSysEhcacheWindow();">新建</a> 
       <a href="#" class="easyui-linkbutton" reskey="EDIT_SysEhcache" iconcls="eicon-pencil" plain="true" onclick="editSysEhcacheGridRecord();">编辑</a> 
       <a href="#" class="easyui-linkbutton" reskey="DEL_SysEhcache" iconcls="eicon-trash-o" plain="true" onclick="deleteSysEhcacheGridRecord();">删除</a> 
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="SysEhcacheForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">缓存名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.EHCACHE_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">缓存状态</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.EHCACHE_STATUE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=EhcacheStatue',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SysEhcacheToolbar','SysEhcacheGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysEhcacheForm')" /></td>
						</tr>
					</tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" id="SysEhcacheGrid" striped="true"
   fitcolumns="true" toolbar="#SysEhcacheToolbar" method="post" idfield="EHCACHE_ID"
    checkonselect="true" selectoncheck="true" fit="true" border="false" 
    url="sysEhcacheController.do?datagrid"> 
   <thead> 
    <tr> 
     <th field="ck" checkbox="true"></th> 
     <th data-options="field:'EHCACHE_ID',hidden:true">EHCACHE_ID</th> 
     <th data-options="field:'EHCACHE_NAME',align:'left'" width="25%" >缓存名称</th> 
     <th data-options="field:'EHCACHE_CLASS_NAME',align:'left'" width="60%">缓存类名</th> 
     <th data-options="field:'EHCACHE_STATUE',align:'left'" width="10%" formatter="formatStatus">缓存状态 </th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>
    