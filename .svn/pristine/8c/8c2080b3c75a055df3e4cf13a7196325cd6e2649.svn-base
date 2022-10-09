<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusEsuperChangeEditForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				//var formData = $("#BusEsuperChangeEditForm").serialize();
				var url = $("#BusEsuperChangeEditForm").attr("action");
				
				var data = $('#BusEsuperChangeEditGrid').datagrid('getData');
				for(var t=0;t<data.rows.length;t++){
					$('#BusEsuperChangeEditGrid').datagrid('endEdit', t);
					//$('#BusEsuperChangeEditGrid').datagrid('refreshRow', t);
				}
				
				var insertRows = $('#BusEsuperChangeEditGrid').datagrid('getChanges','inserted');
   				var updateRows = $('#BusEsuperChangeEditGrid').datagrid('getChanges','updated');
   				var deleteRows = $('#BusEsuperChangeEditGrid').datagrid('getChanges','deleted');
   				var changesRows = {
    				inserted : [],
    				updated : [],
    				deleted : [],
    			};
    			if (insertRows.length>0) {
					for (var i=0;i<insertRows.length;i++) {
						if(insertRows[i].editing==true){
							$.messager.alert('警告',"表格中数据未保存，请保存...");
							return;
						}
						if(insertRows[i].COLUMN_NAME==''){
							$.messager.alert('警告',"监察字段不能为空，请输入...");
							return;						
						}
						if(insertRows[i].COLUMN_NAME.length>32){
							$.messager.alert('警告',"第"+i+"个监察字段长度不能大于32，请重输");
							return;						
						}
						if(insertRows[i].BUSSYS_SN==''){
							$.messager.alert('警告',"排序不能为空，请输入...");
							return;
						}
						changesRows.inserted.push(insertRows[i]);
					}
				}
				if (updateRows.length>0) {
					for (var k=0;k<updateRows.length;k++) {
						if(updateRows[k].editing==true){
							$.messager.alert('警告',"表格中数据未保存，请保存...");
							return;
						}
						if(updateRows[k].COLUMN_NAME==''){
							$.messager.alert('警告',"监察字段不能为空，请输入...");
							return;
						}
						if(updateRows[k].COLUMN_NAME.length>32){
							$.messager.alert('警告',"第"+i+"个监察字段长度不能大于32，请重输");
							return;						
						}
						if(updateRows[k].BUSSYS_SN==''){
							$.messager.alert('警告',"排序不能为空，请输入...");
							return;
						}
						changesRows.updated.push(updateRows[k]);
					}
				}
				if (deleteRows.length>0) {
					for (var j=0;j<deleteRows.length;j++) {
						changesRows.deleted.push(deleteRows[j]);
					}
				}
				var jsonDatas = JSON.stringify(changesRows);
				
    			AppUtil.ajaxProgress({
					url : url,
					params : {
						jsonDatas : jsonDatas,
						processCode : '${busProcess.PROCESS_CODE}',
						applyId:'${busProcess.APPLY_ID}'
					},
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.$("#BusEsuperChangeGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_LCJC_BUS_PROCESS");
		
		var objectTypes = [
		    {value:'0',text:'默认'},
		    {value:'1',text:'时间类型'}
		];
		//
		$('#BusEsuperChangeEditGrid').datagrid({  //初始化datagrid
		    url:'busEsuperChangeController.do?columnDatagrid',
		    idField:"CHANGE_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#BusEsuperChangeEditbar",
		    queryParams: {
		    	"Q_T.PROCESS_CODE_EQ" : '${busProcess.PROCESS_CODE}',
		    	"Q_T.APPLY_ID_EQ" : '${busProcess.APPLY_ID}'
			},	
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'CHANGE_ID',hidden:true},
		        {field:'COLUMN_NAME',title:'监察字段',width:250,align:'left',editor:'text'},
		        {field:'FIELD_TYPE',title:'字段类型',width:150,align:'left',
		        	formatter:function(value){
						for(var i=0; i<objectTypes.length; i++){
							if (objectTypes[i].value == value) return objectTypes[i].text;
						}
						return value;
					},
		        	editor:{
		        		type:'combobox',
						options:{
							valueField:'value',
							textField:'text',
							data:objectTypes,
							required:true,
							panelHeight:'auto'
						}
		        	}
		        },
		        {field:'BUSSYS_SN',title:'排序',width:100,align:'left',editor:'numberbox'},
		        {field:'STATUS',title:'操作',width:100,align:'center',
					formatter:function(value,row,index){
						return '<a href="#" onclick="deleterow(\''+row.CHANGE_ID+'\')">删除</a>';	
					}
				}
		    ]],
		    toolbar:[{ text:'新增',iconCls:'icon-note-add',handler:addrow}],
		    onBeforeEdit:function(index,row){
		        row.editing = true;
		        $('#BusEsuperChangeEditGrid').datagrid('refreshRow', index);
		        editcount++;
		    },
		    onAfterEdit:function(index,row){
		        row.editing = false;
		        $('#BusEsuperChangeEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onCancelEdit:function(index,row){
		        row.editing = false;
		        $('#BusEsuperChangeEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onClickRow:function (index, row) {
                $('#BusEsuperChangeEditGrid').datagrid('beginEdit', index);
            }	
		});
		AppUtil.initAuthorityRes("BusEsuperChangeEditbar");
	});
	var editcount = 0;
	function editrow(index){
		$('#BusEsuperChangeEditGrid').datagrid('beginEdit', index);
	}
	function deleterow(serialId){
		$.messager.confirm('确认','您确定要删除掉该记录吗?',function(r){
			if (r){
				var $dataGrid = $("#BusEsuperChangeEditGrid");
				var data = $dataGrid.datagrid('getData');
				var rows = data.rows;
				var colName = $dataGrid.datagrid('options').idField;  		
				var rowindex;  
				for( var j=0; j< rows.length; j++){
					if(eval('rows[j].'+colName) == serialId){
						rowindex = j;
						break;  
					}
				}
				$('#BusEsuperChangeEditGrid').datagrid('deleteRow', rowindex);
			}
		});
	}
	function saverow(index){
		$('#BusEsuperChangeEditGrid').datagrid('endEdit', index);
	}
	function cancelrow(index){
		$('#BusEsuperChangeEditGrid').datagrid('cancelEdit', index);
	}
	function addrow(){
		var $dataGrid = $("#BusEsuperChangeEditGrid");
		var length = $dataGrid.datagrid('getData').rows.length+1;
		$('#BusEsuperChangeEditGrid').datagrid('appendRow',{
			CHANGE_ID:'t_'+length,
			COLUMN_NAME:'',
			FIELD_TYPE:'0',
			BUSSYS_SN:length
		});
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusEsuperChangeEditForm" method="post"
		action="busEsuperChangeController.do?saveEditColumn">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="CHANGE_ID" value="${busProcess.CHANGE_ID}">
		<input type="hidden" name="APPLY_ID" value="${busProcess.APPLY_ID}">
		<input type="hidden" name="PROCESS_CODE" value="${busProcess.PROCESS_CODE}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td style="width: 100px;"><span style="width: 100px;float:left;text-align:right;">业务专项：</span>
				</td>
				<td>	
					&nbsp;${busProcess.BUS_NAME }
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"><span style="width: 100px;float:left;text-align:right;">业务环节：</span>
				</td>
				<td>	
					&nbsp;${busProcess.TACHE_NAME }
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"><span style="width: 100px;float:left;text-align:right;">监察节点：</span>
				</td>
				<td>	
					&nbsp;${busProcess.PROCESS_NAME }
				</td>
			</tr>
		</table>
		<!-- =========================表格开始==========================-->
		<div id="BusEsuperChangeEditbar">
			<input type="hidden" name="Q_T.PROCESS_CODE_EQ" value="${busProcess.PROCESS_CODE}">
		</div>
		<table id="BusEsuperChangeEditGrid"></table>
		<!-- =========================表格结束==========================-->
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

