<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
 * 删除系统日志列表记录
 */
function deleteSysLogGridRecord() {
	AppUtil.deleteDataGridRecord(
			"sysLogController.do?multiDel", "SysLogGrid");
};
/**
 * 编辑系统日志列表记录
 */
function editSysLogGridRecord(){
	var entityId = AppUtil.getEditDataGridRecord("SysLogGrid");
	if(entityId){
		showSysLogWindow(entityId);
	}
}

/**
 * 显示系统日志信息对话框
 */
function showExcelExportWindow() {
	AppUtil.showExcelExportWin({
		dataGridId:"SysLogGrid",
		tplKey:"LogReportTpl",
		excelName:"系统日志数据",
		queryParams:{
			"L.FULLNAME":$("input[name='Q_L.FULLNAME_LIKE']").val(),
			"L.LOG_CONTENT":$("input[name='Q_L.LOG_CONTENT_LIKE']").val()
		}
	});
};
/**
 * 格式化操作类型
 */
function formatOperateType(val,row){
	if(row.OPERATE_TYPE=="1"){
		return "增加操作";
	}else if(row.OPERATE_TYPE=="2"){
		return "编辑操作";
	}else if(row.OPERATE_TYPE=="3"){
		return "删除操作";
	}else if(row.OPERATE_TYPE=="4"){
		return "登录操作";
	}else if(row.OPERATE_TYPE=="5"){
		return "上传操作";
	}else if(row.OPERATE_TYPE=="6"){
		return "角色管理";
	}
};

$(document).ready(function(){
	    var start1 = {
	    elem: "#SysLogL.OPERATE_TIME_BEGIN",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_L.OPERATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_L.OPERATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='Q_L.OPERATE_TIME_>=']").val("");
	    		}
	    	}
	    }
	};
	var end1 = {
	    elem: "#SysLogL.OPERATE_TIME_END",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_L.OPERATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_L.OPERATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='Q_L.OPERATE_TIME_<=']").val("");
	    		}
	    	}
	    }
	};
	laydate(start1);
	laydate(end1);

	//AppUtil.initAuthorityRes("SysLogToolbar");
});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="SysLogToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <div class="right-con"> 
    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
     <div class="e-toolbar-ct"> 
      <div class="e-toolbar-overflow"> 
         <div style="color:#005588;">
         <img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:8px; float:left;" />&nbsp;系统日志
         <!-- 
         <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true" 
         onclick="showExcelExportWindow();">导出数据</a>  -->
         </div>
      </div> 
     </div> 
    </div> 
   </div> 
   <form action="#" name="SysLogForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">日志内容</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.LOG_CONTENT_LIKE" /></td> 
       <td style="width:68px;text-align:right;">操作人姓名</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.FULLNAME_LIKE" /></td> 
       <td style="width:68px;text-align:right;">操作人帐号</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_L.USERNAME_LIKE" /></td> 
       <td colspan="2"></td> 
      </tr> 
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">开始日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="SysLogL.OPERATE_TIME_BEGIN" name="Q_L.OPERATE_TIME_&gt;=" /></td> 
       <td style="width:68px;text-align:right;">结束日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="SysLogL.OPERATE_TIME_END" name="Q_L.OPERATE_TIME_&lt;=" /></td> 
       <td style="width:68px;text-align:right;">操作类型</td> 
       <td style="width:135px;padding-left:4px;"> <input class="easyui-combobox" style="width:128px;" name="Q_L.OPERATE_TYPE_=" data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=LogOperateType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " /> </td> 
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('SysLogToolbar','SysLogGrid')" /> <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('SysLogForm')" /></td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true" id="SysLogGrid" nowrap="false" fitcolumns="false" toolbar="#SysLogToolbar" method="post" idfield="LOG_ID" checkonselect="false" selectoncheck="false" fit="true" border="false" url="sysLogController.do?datagrid"> 
   <thead> 
    <tr> 
     <th field="ck" checkbox="true"></th> 
     <th data-options="field:'LOG_ID',hidden:true">LOG_ID</th> 
     <th data-options="field:'BROWSER',align:'left'" width="10%">浏览器</th> 
     <th data-options="field:'LOG_CONTENT',align:'left'" width="30%">日志内容</th> 
     <th data-options="field:'OPERATE_TYPE',align:'left'" width="10%" formatter="formatOperateType">操作类型</th> 
     <th data-options="field:'OPERATE_TIME',align:'left'" width="15%">操作时间</th> 
     <th data-options="field:'FULLNAME',align:'left'" width="10%">操作人姓名</th> 
     <th data-options="field:'USERNAME',align:'left'" width="10%">操作人帐号</th> 
     <th data-options="field:'IP_ADDRESS',align:'left'" width="10%">IP地址</th> 
    </tr> 
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>