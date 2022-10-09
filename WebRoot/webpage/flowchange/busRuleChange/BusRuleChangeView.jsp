<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/***
	 * 删除数据
	 */
	function removeRuleChangeWindow(){
		AppUtil.deleteStatusDataGridRecord(
			"busRuleChangeController.do?muitDel", "BusRuleChangeGrid");
	}
	
	/**
	 * 发起变更对话框
	 */
	function showGoChangeWindow() {
		$.dialog.open("busRuleChangeController.do?goChange", {
    		title : "发起变更信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"400px",
    	}, false);
	}
	
	/**
	 * 显示监察规则对话框
	 */
	function showRuleChangeWindow(entityId) {
		$.dialog.open("busRuleChangeController.do?info&entityId=" + entityId, {
    		title : "监察规则信息",
            width:"720px",
            lock: true,
            resize:false,
            height:"550px",
    	}, false);
	}
	/**
	 * 编辑监察规则列表记录
	 */
	function editRuleChangeWindow(){
		var entityId = AppUtil.getStatusDataGridRecord("BusRuleChangeGrid");
		if (entityId) {
			showRuleChangeWindow(entityId);
		}
	}
	/**
	 * 查看监察规则列表记录
	 */
	function seeRuleChangeWindow(){
		var entityId = AppUtil.getEditDataGridRecord("BusRuleChangeGrid");
		if (entityId) {
			$.dialog.open("busRuleChangeController.do?seeInfo&entityId=" + entityId, {
	    		title : "监察规则信息",
	            width:"720px",
	            lock: true,
	            resize:false,
	            height:"500px",
	    	}, false);
    	}
	}
	
	$(document).ready(function() {
		$('#BusRuleChangeGrid').datagrid({  //初始化datagrid
		    url:'busRuleChangeController.do?datagrid',
		    idField:"CHANGE_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:true,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:true,
		    nowrap:false,
		 	pageSize:15,
		 	pageList:[15,20,30],
		    toolbar: "#BusRuleChangeToolbar",
		    queryParams: {
		    	//"Q_T.SITE_ID_=" : $('#topic_website_id').combobox('getValue')
			},	
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'CHANGE_ID',hidden:true},
		        //{field:'APPLY_ID',title:'申报号',width:150,align:'left'},
		        {field:'BUS_NAME',title:'业务专项',width:150,align:'left'},
		        {field:'TACHE_NAME',title:'业务环节',width:150,align:'left'},
		        {field:'PROCESS_NAME',title:'监察点名称',width:150,align:'left'},
		        {field:'SUPER_ELEMENTS',title:'监察要素',width:100,align:'left'},
		        {field:'ANALYSIS_TYPE',title:'监察类型',width:100,align:'left',formatter:function(value,row,index){
		    		if (row.ANALYSIS_TYPE==1){
		    			return "<b><font >时限监察</font></b>";
		    		} else if(row.ANALYSIS_TYPE==2){
		    			return "<b><font >内容监察</font></b>";
		    		}else if(row.ANALYSIS_TYPE==3){
		    			return "<b><font >流程监察</font></b>";
		    		}else if(row.ANALYSIS_TYPE==4){
		    			return "<b><font >收费监察</font></b>";
		    		}else{
		    			return "<b><font ></font></b>";
		    		}
		    	}},
		        {field:'IS_ARTIFICIAL',title:'监察方式',width:80,align:'left',formatter:function(value,row,index){
		    		if (row.IS_ARTIFICIAL==0){
		    			return "<b><font color=green>自动监察</font></b>";
		    		} else if(row.IS_ARTIFICIAL==1){
		    			return "<b><font color=blue>人工监察</font></b>";
		    		}else{
		    			return "<b><font ></font></b>";
		    		}
		    	}},
		        {field:'JUDGE_DESC',title:'规则表达式',width:150,align:'left'},
		        {field:'RULE_DESC',title:'规则描述',width:200,align:'left'},
		        {field:'UPDATE_TIME',title:'更新时间',width:100,align:'left'},
		        {field:'STATUS',title:'审核状态',width:80,align:'left',formatter:function(value,row,index){
		    		if (row.STATUS==0){
		    			return "<b><font color=red>暂存</font></b>";
		    		} else if(row.STATUS==1){
		    			return "<b><font color=blue>已确认</font></b>";
		    		}else if(row.STATUS==2){
		    			return "<b><font color=blue>待审核</font></b>";
		    		}else if(row.STATUS==3){
		    			return "<b><font color=blue>审核通过</font></b>";
		    		}else if(row.STATUS==4){
		    			return "<b><font color=red>审核不通过</font></b>";
		    		}else if(row.STATUS==5){
		    			return "<b><font color=red>关闭</font></b>";
		    		}else{
		    			return "<b></b>";
		    		}
		    	}}
		    ]],
		    onDblClickRow:function (index, row) {//onClickRow
		    	//$('#BusRuleChangeGrid').datagrid('selectRow',index);
		    	var rowsData = $("#BusRuleChangeGrid").datagrid("getChecked");
		    	if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
		    		return;
		    	}
		    	if(row.STATUS==3 || row.STATUS==2){
		    		seeRuleChangeWindow();
		    	}else{
		    		editRuleChangeWindow();
		    	}
            }		
		});
		AppUtil.initAuthorityRes("BusRuleChangeToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BusRuleChangeToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<!-- <a href="#" class="easyui-linkbutton" reskey="GO_BusRuleChange"
								iconcls="icon-note-edit" plain="true"
								onclick="showGoChangeWindow();">发起变更</a> 	
							<a href="#" class="easyui-linkbutton" reskey="ADD_BusRuleChange"
								iconcls="icon-note-add" plain="true"
								onclick="showRuleChangeWindow();">新建</a> 	 -->
							<!--reskey="EDIT_BusRuleChange"reskey="REMOVE_BusRuleChange"reskey="SEE_BusRuleChange"  -->
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-edit" plain="true"
								onclick="editRuleChangeWindow();">编辑</a>
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-delete" plain="true"
								onclick="removeRuleChangeWindow();">删除</a>
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-edit" plain="true"
								onclick="seeRuleChangeWindow();">查看</a> 								
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BusRuleChangeForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">业务专项：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_C.BUS_NAME_LIKE" /></td>
							<td style="width:80px;text-align:right;">业务环节：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_B.TACHE_NAME_LIKE" /></td>
							<td style="width:80px;text-align:right;">监察点名称：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_A.PROCESS_NAME_LIKE" /></td>
							<td style="width:80px;text-align:right;">监察要素：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SUPER_ELEMENTS_LIKE" /></td>
							<td colspan="2">&nbsp;
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">监察类型：</td>
							<td style="width:135px;">
								<input class="easyui-combobox" style="width:130px;" name="Q_T.ANALYSIS_TYPE_EQ"  
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperType',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 130,panelHeight: 'auto' " /> 
							</td>
							<td style="width:80px;text-align:right;">监察方式：</td>
							<td style="width:135px;">
								<input class="easyui-combobox" style="width:130px;" name="Q_T.IS_ARTIFICIAL_EQ" 
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 130,panelHeight: 'auto' " /> 
							</td>
							<td style="width:68px;text-align:right;">审核状态：</td> 
					        <td style="width:135px;"> 
					       	  <input class="easyui-combobox" style="width:130px;"
									 data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=FlowStatus',editable:false,
									 method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 130,panelHeight: 'auto' "
								     name="Q_T.STATUS_EQ" />
					        </td>
							<td colspan="2">&nbsp;&nbsp;
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusRuleChangeToolbar','BusRuleChangeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusRuleChangeForm')" /></td>
							<td colspan="2">&nbsp;
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  id="BusRuleChangeGrid" ></table>
		<!-- =========================表格结束==========================-->
	</div>
</div>