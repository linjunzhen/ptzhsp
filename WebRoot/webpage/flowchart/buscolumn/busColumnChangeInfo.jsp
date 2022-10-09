<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("BasicColumnForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			//$("input[type='submit']").attr("disabled", "disabled");
			
			var rows  = $('#BusColumnBasicEditGrid').datagrid("getRows");
			if( rows.length <=0 ){
				$.messager.alert('警告',"请添加基本信息字段！");
				return;
			}
			
			var formData = $("#BasicColumnForm").serialize();
			var url = $("#BasicColumnForm").attr("action");
			/**====可编辑表格数据开始======**/
			var data = $('#BusColumnBasicEditGrid').datagrid('getData');
			for(var t=0;t<data.rows.length;t++){
				$('#BusColumnBasicEditGrid').datagrid('endEdit', t);
				//$('#BusColumnEditGrid').datagrid('refreshRow', t);
			}
			
			var insertRows = $('#BusColumnBasicEditGrid').datagrid('getChanges','inserted');
				var updateRows = $('#BusColumnBasicEditGrid').datagrid('getChanges','updated');
				var deleteRows = $('#BusColumnBasicEditGrid').datagrid('getChanges','deleted');
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
			
			/**====可编辑表格数据结束======**/
			AppUtil.ajaxProgress({
				url : url,
				params : {
					jsonDatas : jsonDatas,
					formDatas : formData
				},				
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
						parent.$("#BasicColumnChangeGrid").datagrid("reload");
						AppUtil.closeLayer();
					} else {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}
	}, "T_LCJC_BUS_COLUMN_CHANGE");
	
	var objectTypes = [
	    {value:'0',text:'默认'},
	    {value:'1',text:'时间类型'}
	];
	/**
	* 初始化表格
	*/
	$('#BusColumnBasicEditGrid').datagrid({  //初始化datagrid
	    url:'busColumnBasicController.do?columnchangedatagrid',
	    idField:"CHANGE_ID",
	    rownumbers: true,
	    fit:true,
	    border:false,
	    checkOnSelect:false,
	    selectOnCheck:false,
	    singleSelect:true,
	    fitColumns:true,
	    pagination:false,
	    toolbar: "#BusColumnBasicEditbar",
	    queryParams: {
	    	"processCode" : '${basicColumn.PROCESS_CODE}',
	    	"platCode":'${basicColumn.BUSSYS_PLATCODE}',
	    	"applyId":'${applyId}'
		},	
		columns:[[
	        //{field:'ck',checkbox:true},
	        {field:'CHANGE_ID',hidden:true},
	        {field:'COLUMN_NAME',title:'字段名称',width:250,align:'left',editor:'text'},
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
	        {field:'IS_SHOW',title:'是否显示',width:80,align:'center',
	        	formatter:function(value,row,index){
	        		return row.IS_SHOW == '0' ? '是' : '否';
				},
	        	editor:{
	        		type:'checkbox',
	                options:{
	                    on:'0',
	                    off:'1'
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
	        $('#BusColumnBasicEditGrid').datagrid('refreshRow', index);
	        editcount++;
	        $("input[class='datagrid-editable-input']").attr("maxlength","32");
	    },
	    onAfterEdit:function(index,row){
	        row.editing = false;
	        $('#BusColumnBasicEditGrid').datagrid('refreshRow', index);
	        editcount--;
	    },
	    onCancelEdit:function(index,row){
	        row.editing = false;
	        $('#BusColumnBasicEditGrid').datagrid('refreshRow', index);
	        editcount--;
	    },
	    onClickRow:function (index, row) {
            $('#BusColumnBasicEditGrid').datagrid('beginEdit', index);
            $("input[class='datagrid-editable-input']").attr("maxlength","32");
        }	
	});
	AppUtil.initAuthorityRes("BusColumnBasicEditbar");
});	

var editcount = 0;
function editrow(index){
	$('#BusColumnBasicEditGrid').datagrid('beginEdit', index);
}
function deleterow(serialId){
	$.messager.confirm('确认','您确定要删除掉该记录吗?',function(r){
		if (r){
			var $dataGrid = $("#BusColumnBasicEditGrid");
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
			$('#BusColumnBasicEditGrid').datagrid('deleteRow', rowindex);
		}
	});
}
function saverow(index){
	$('#BusColumnBasicEditGrid').datagrid('endEdit', index);
}
function cancelrow(index){
	$('#BusColumnBasicEditGrid').datagrid('cancelEdit', index);
}
function addrow(){
	var $dataGrid = $("#BusColumnBasicEditGrid");
	var length = $dataGrid.datagrid('getData').rows.length+1;
	$('#BusColumnBasicEditGrid').datagrid('appendRow',{
		CHANGE_ID:'t_'+length,
		COLUMN_NAME:'',
		FIELD_TYPE:'0',
		BUSSYS_SN:length,
		IS_SHOW:'0'
	});
}

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<div id="BasicColumnInfoTabDiv" class="easyui-layout" fit="true">
 <div data-options="region:'center'" fit="true">
	<form id="BasicColumnForm" method="post" action="busColumnBasicController.do?changeSaveOrUpdate">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="applyId" value="${applyId}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">字段基本信息</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务单位：</span>
					<input type="hidden" name="BUSSYS_UNITCODE" value="${basicColumn.BUSSYS_UNITCODE}"/>
					<span>${basicColumn.BUSSYS_UNITNAME}</span>
				</td>					
				</tr>
				<tr>
					<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务系统：</span>
					<input type="hidden" name="BUSSYS_PLATCODE" value="${basicColumn.BUSSYS_PLATCODE}"/>
					<span>${basicColumn.BUSSYS_PLATNAME}</span>
					</td>					
				</tr>
				<tr>
					<td colspan="2"><span style="width: 150px;float:left;text-align:right;">所属专项：</span>
					<input type="hidden" name="PROCESS_CODE" value="${basicColumn.PROCESS_CODE}"/>
					<span>${basicColumn.PROCESS_NAME}</span>
					</td>					
				</tr>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">字段详细信息</td>
				</tr>
				<tr style="height:260px;">
					<td colspan="1">
					<!-- =========================表格开始==========================-->
					<div id="BusColumnBasicEditbar"></div>
					<table id="BusColumnBasicEditGrid"></table>
					<!-- =========================表格结束==========================-->
					</td>
				</tr>
			</table>
			<div class="eve_buttons" style="position: relative;">
				<input value="提交变更" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</form>
 </div>
</div>
</body>