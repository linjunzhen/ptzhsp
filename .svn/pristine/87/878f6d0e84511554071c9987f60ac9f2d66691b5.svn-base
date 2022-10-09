<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 提交第一阶段
	 */
	function submitFirstRecord(busCode, exeId) {
		AppUtil.ajaxProgress({
			url : "busAuditController.do?submitFirst",
			params : {
				"Q_T.BUS_CODE_EQ" : busCode
			},	
			callback:function(result){
				if(result.success){
					//$("#BusAuditGrid").datagrid("reload");
					$.dialog.open("busAuditController.do?busAuditInfo&busCode="+busCode+"&exeId="+exeId, {
			    		title : "提交信息",
			            width:"600px",
			            lock: true,
			            resize:false,
			            height:"400px",
			    	}, false);
			    	return;
				}
				$.messager.alert('警告',result.msg);
			}
		});
		
	};
	/**
	 * 提交第二阶段
	 */
	function submitSecondRecord(busCode,exeId) {
		
		AppUtil.ajaxProgress({
			url : "busAuditController.do?submitSecond",
			params : {
				"Q_T.BUS_CODE_EQ" : busCode
			},	
			callback:function(result){
				if(result.success){
					//$("#BusAuditGrid").datagrid("reload");
					$.dialog.open("busAuditController.do?busAuditInfo&busCode="+busCode+"&exeId="+exeId, {
			    		title : "提交信息",
			            width:"600px",
			            lock: true,
			            resize:false,
			            height:"400px",
			    	}, false);
			    	return;
				}
				alert(result.msg);
			}
		});	
	};
	
	$(document).ready(function() {
		$('#BusAuditGrid').datagrid({  //初始化datagrid
		    url:'busSpecialController.do?datagrid',
		    idField:"BUS_CODE",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:true,
		 	pageSize:15,
		 	 nowrap:false,
		 	pageList:[15,30,50],
		    toolbar: "#BusAuditToolbar",
		    queryParams: {
			},	
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'BUS_ID',hidden:true},
		        {field:'APPLY_ID',hidden:true},
		        {field:'UNIT_NAME',title:'业务单位',width:100,align:'left'},
		        {field:'BUS_CODE',title:'专项编码',width:100,align:'left'},
		        {field:'BUS_NAME',title:'专项名称',width:200,align:'left'},
		        {field:'UPDATE_TIME',title:'更新时间',width:115,align:'left'},
		        {field:'STATUS',title:'审核状态',width:105,align:'left',formatter:function(value,row,index){
			        var stage = "";
					if(row.VERSION == "1"){
						stage="第一阶段"
					}else{
						stage="第二阶段"
					}
		        	if (row.STATUS==0){
		    			return "<b><font color=#AAAAAA>"+stage+"暂存</font></b>";
		    		} else if(row.STATUS==1){
		    			return "<b><font color=#3399ff>"+stage+"已确认</font></b>";
		    		}else if(row.STATUS==2){
		    			return "<b><font color=blue>"+stage+"待审核</font></b>";
		    		}else if(row.STATUS==3){
		    			return "<b><font color=green>"+stage+"审核通过</font></b>";
		    		}else if(row.STATUS==4){
		    			return "<b><font color=red>"+stage+"审核不通过</font></b>";
		    		}else if(row.STATUS==5){
		    			return "<b><font color=#444444>关闭</font></b>";
		    		}else{
		    			return "<b><font color=#3399ff>"+stage+"已确认</font></b>";
		    		}
		    	}},
		    	{field:"VERSION",title:"操作",width:60,align:'left',formatter:function(val,row){
						if(val == "1" && row.STATUS!="3" && row.STATUS!="2"){
							return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="submitFirstRecord(\''+row.BUS_CODE+'\',\''+row.EXE_ID+'\');">提交审核</a>';
						}
						if(val == "1" && row.STATUS == "3"){
							return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="submitSecondRecord(\''+row.BUS_CODE+'\',\''+row.EXE_ID+'\');">提交审核</a>';
						}
						if(val == "2" && row.STATUS != "3" && row.STATUS!="2"){
							return '<a href="#" iconcls="icon-note-edit" class="easyui-linkbutton" onclick="submitSecondRecord(\''+row.BUS_CODE+'\',\''+row.EXE_ID+'\');">提交审核</a>';
						}
		    		}
			    }
		    ]],
		    onClickRow:function (index, row) {
                $("#audit_buscode").val(row.BUS_CODE);
                AppUtil.gridDoSearch('AuditStatusToolbar','AuditStatusGrid');
            }
		});	
		AppUtil.initAuthorityRes("BusAuditToolbar");
		$('#AuditStatusGrid').datagrid({  //初始化datagrid
		    url:'busAuditController.do?datagrid',
		    idField:"AUDIT_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#AuditStatusToolbar",
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'AUDIT_ID',hidden:true},
		        {field:'CONFIG_NAME',title:'核对项目',width:200,align:'left'},
		        {field:'STATUS',title:'状态',width:80,align:'left',formatter:function(value,row,index){
		    		if (row.STATUS==0){
		    			return "<img src='plug-in/easyui-1.4/themes/icons/cancel.png' />";
		    		} else if(row.STATUS==1){
		    			return "<img src='plug-in/easyui-1.4/themes/icons/ok.png' />";
		    		}else{
		    			return "<img src='plug-in/easyui-1.4/themes/icons/cancel.png' />";
		    		}
		    	}}
		    ]]
		});	
		AppUtil.initAuthorityRes("AuditStatusToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" style="width:70%;">
		<!-- =========================查询面板开始========================= -->
		<div id="BusAuditToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<%--<a href="#" class="easyui-linkbutton" reskey="SUBMIT1_BusAudit"
								iconcls="icon-note-edit" plain="true"
								onclick="submitFirstRecord();">提交第一阶段</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="SUBMIT2_BusAudit"
								iconcls="icon-note-edit" plain="true"
								onclick="submitSecondRecord();">提交第二阶段</a> 
						--%></div>
					</div>
				</div>
			</div>
			<form action="#" name="BusAuditForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">专项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUS_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">审核状态</td>
							<td style="width:135px;">
							<input class="easyui-combobox" style="width:128px;"
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' "
								name="Q_T.STATUS_LIKE" /></td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusAuditToolbar','BusAuditGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusAuditForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  id="BusAuditGrid" ></table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'center',split:false">	
		<!-- =========================查询面板开始========================= -->
		<div id="AuditStatusToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" id="audit_buscode" name="Q_T.BUS_CODE_EQ">	
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							&nbsp;
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="AuditStatusForm">
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  id="AuditStatusGrid" ></table>
		<!-- =========================表格结束==========================-->		
	</div>
</div>