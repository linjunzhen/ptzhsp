<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable{width:100%;height:100%}
.gridtable .datagrid-htable{border-top:1px solid #8DB2E3;border-right:1px solid #8DB2E3}
.gridtable .datagrid-btable{border-right:1px solid #8DB2E3;border-bottom:1px solid #8DB2E3}
.gridtable .datagrid-header-row td{border-width:0;border-left:1px solid #8DB2E3;border-bottom: 1px solid #8DB2E3;}
.gridtable .datagrid-body td{border-width:0;border-left:1px solid #8DB2E3;border-top: 1px solid #8DB2E3} 
</style>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>				
<script type="text/javascript">
    
    function exportExcel(){
    	var dept = $("input[name='Q_T.RECEIVEDEPT_LIKE']").val();
    	var type = $("input[name='Q_T.SERVICETYPE_=']").val();
    	var begin = $("#apasCREATE_TIME_BEGIN").val();
    	var end= $("#apasCREATE_TIME_END").val();
    	window.location.href = "apasInfoController.do?exportExcel&Q_T.RECEIVEDEPT_LIKE="+dept+"&Q_T.SERVICETYPE_EQ="+type
    					+"&Q_T.CREATETIME_BEGIN="+begin+"&Q_T.CREATETIME_END="+end;
    }
   $(document).ready(function(){
   	  var start1 = {
            elem : "#apasCREATE_TIME_BEGIN",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#apasCREATE_TIME_BEGIN").val();
                var endTime = $("#apasCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("开始时间必须小于等于结束时间,请重新输入!");
                        $("#apasCREATE_TIME_BEGIN").val("");
                    }
                }
            }
        };
        var end1 = {
            elem : "#apasCREATE_TIME_END",
            format : "YYYY-MM-DD",
            istime : false,
            choose : function(datas) {
            	var beginTime = $("#apasCREATE_TIME_BEGIN").val();
                var endTime = $("#apasCREATE_TIME_END").val();
                if (beginTime != "" && endTime != "") {
                    var start = new Date(beginTime);
                    var end = new Date(endTime);
                    if (start > end) {
                        alert("结束时间必须大于等于开始时间,请重新输入!");
                        $("#apasCREATE_TIME_END").val("");
                    }
                }
            }
        };
        laydate(start1);
        laydate(end1);
        
	  AppUtil.initAuthorityRes("apasInfoToolBar");
  }); 
	
	//空数据，横向滚动
	$('#apasInfoGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="apasInfoToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
						         onclick="exportExcel();" resKey="export_apasinfo">导出数据</a> 
								 
								<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SysLogForm">
				<input type="hidden" name="DEPART_ID" value="${sysUser.depId}">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">部门</td>
							<td style="width:185px;">
								<input class="eve-input"
								type="text" maxlength="20" style="width:178px;"
								name="Q_T.RECEIVEDEPT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SERVICETYPE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=OldServiceType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">办件名称</td>
							<td style="width:185px;">
								<input class="eve-input"
								type="text" maxlength="20" style="width:178px;"
								name="Q_T.PROJECTNAME_LIKE" />
							</td>
							<td colspan="2"></td>
						</tr>
						<tr　style="height: 28px;">
							<td style="width:68px;text-align:right;">收件时间从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="apasCREATE_TIME_BEGIN" name="Q_T.CREATETIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">收件时间至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="apasCREATE_TIME_END" name="Q_T.CREATETIME_&lt;=" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('apasInfoToolBar','apasInfoGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="apasInfoGrid" fitcolumns="true" toolbar="#apasInfoToolBar"
			method="post" idfield="UNID_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="apasInfoController.do?apasInfoData">
			<thead>
				<tr>
					<th data-options="field:'RECEIVEDEPT',align:'left'" width="9%" >部门</th>
					<th data-options="field:'SERVICENAME',align:'left'" width="20%" >事项名称</th>
					<th data-options="field:'PROJECTNAME',align:'left'" width="20%" >办件名称</th>
					<th data-options="field:'SERVICETYPE',align:'left'" width="7%" >办件类型</th>
					<th data-options="field:'APPLYNAME',align:'left'" width="10%" >申请人</th>
					<th data-options="field:'CONTACTMAN',align:'left'" width="7%" >联系人</th>
					<th data-options="field:'MOBILEPHONE',align:'left'" width="9%" >联系电话</th>
					<th data-options="field:'RECEIVEUSER',align:'left'" width="7%" >收件人</th>
					<th data-options="field:'CREATETIME',align:'left'" width="15%" >收件时间</th>
					<th data-options="field:'HANDLESTATE',align:'left'" width="7%" >办件状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>