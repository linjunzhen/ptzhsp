<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("factorInfoForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			//$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#factorInfoForm").serialize();
			var url = $("#factorInfoForm").attr("action");
			/**====可编辑表格数据开始======**/
			var data = $('#factorMngEditGrid').datagrid('getData');
			for(var t=0;t<data.rows.length;t++){
				$('#factorMngEditGrid').datagrid('endEdit', t);
			}
			var insertRows = $('#factorMngEditGrid').datagrid('getChanges','inserted');
				var updateRows = $('#factorMngEditGrid').datagrid('getChanges','updated');
				var deleteRows = $('#factorMngEditGrid').datagrid('getChanges','deleted');
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
					if(insertRows[i].SUPER_ELEMENTS==''){
						$.messager.alert('警告',"要素名称不能为空，请输入...");
						return;						
					}
					if(insertRows[i].SUPER_ELEMENTS.length>100){
						$.messager.alert('警告',"要素名称最大长度为100个字符，请重新调整后再进行提交.");
						return;
					}
					if(insertRows[i].RULE_DESC.length>500){
						$.messager.alert('警告',"要素描述最大长度为500个字符，请重新调整后再进行提交.");
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
					if(updateRows[k].SUPER_ELEMENTS==''){
						$.messager.alert('警告',"要素名称不能为空，请输入...");
						return;
					}
					if(updateRows[k].SUPER_ELEMENTS.length>100){
						$.messager.alert('警告',"要素名称最大长度为100个字符，请重新调整后再进行提交.");
						return;
					}
					if(updateRows[k].RULE_DESC.length>500){
						$.messager.alert('警告',"要素描述最大长度为500个字符，请重新调整后再进行提交.");
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
							content : "保存成功",
							icon : "succeed",
							time : 3,
							ok : true
						});
						parent.loadFactorList(resultJson.msg);
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
	}, "T_LCJC_BUS_RULECONFIG");
	
	var objectTypes = [
	    {value:'1',text:'时限监察'},
	    {value:'2',text:'内容监察'},
	    {value:'3',text:'流程监察'},
	    {value:'4',text:'收费监察'}
	];
	/**
	* 初始化表格
	*/
	$('#factorMngEditGrid').datagrid({  //初始化datagrid
	    url:'monitorNodeController.do?factInfodatagrid',
	    idField:"RULE_ID",
	    rownumbers: true,
	    fit:true,
	    border:false,
	    checkOnSelect:false,
	    selectOnCheck:false,
	    singleSelect:true,
	    fitColumns:true,
	    pagination:false,
	    toolbar: "#factorMngEditbar",
	    queryParams: {
	    	"processCode" : '${processCode}'
		},	
		columns:[[
	        //{field:'ck',checkbox:true},
	        {field:'RULE_ID',hidden:true},
	        {field:'SUPER_ELEMENTS',title:'要素名称',width:160,align:'left',editor:'text'},
	        {field:'ANALYSIS_TYPE',title:'监察类型',width:100,align:'left',
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
	        {field:'RULE_DESC',title:'要素描述',width:200,align:'left',editor:'text'},
	        {field:'STATUS',title:'操作',width:100,align:'center',
				formatter:function(value,row,index){
					return '<a href="#" onclick="deleterow(\''+row.RULE_ID+'\')">删除</a>';
				}
			}
	    ]],
	    toolbar:[{ text:'新增',iconCls:'icon-note-add',handler:addrow}],
	    onBeforeEdit:function(index,row){
	        row.editing = true;
	        $('#factorMngEditGrid').datagrid('refreshRow', index);
	        editcount++;
	    },
	    onAfterEdit:function(index,row){
	        row.editing = false;
	        $('#factorMngEditGrid').datagrid('refreshRow', index);
	        editcount--;
	    },
	    onCancelEdit:function(index,row){
	        row.editing = false;
	        $('#factorMngEditGrid').datagrid('refreshRow', index);
	        editcount--;
	    },
	    onClickRow:function (index, row) {
            $('#factorMngEditGrid').datagrid('beginEdit', index);
        }		
	});
	AppUtil.initAuthorityRes("factorMngEditbar");
});	

var editcount = 0;
function editrow(index){
	$('#factorMngEditGrid').datagrid('beginEdit', index);
}
function deleterow(serialId){
	$.messager.confirm('确认','您确定要删除掉该记录吗?',function(r){
		if (r){
			var $dataGrid = $("#factorMngEditGrid");
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
			$('#factorMngEditGrid').datagrid('deleteRow', rowindex);
		}
	});
}
function saverow(index){
	$('#factorMngEditGrid').datagrid('endEdit', index);
}
function cancelrow(index){
	$('#factorMngEditGrid').datagrid('cancelEdit', index);
}
function addrow(){
	var $dataGrid = $("#factorMngEditGrid");
	var length = $dataGrid.datagrid('getData').rows.length+1;
	$('#factorMngEditGrid').datagrid('appendRow',{
		RULE_ID:'t_'+length,
		SUPER_ELEMENTS:'',
		ANALYSIS_TYPE:'1',
		RULE_DESC:''
	});
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="factorInfoForm" method="post"
		action="monitorNodeController.do?saveOrUpdateBatch">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="PROCESS_CODE" value="${processCode}">
		<input type="hidden" name="TACHE_CODE" value="${tacheCode}">
		<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">要素信息</td>
				</tr>
				<tr style="height:350px;">
					<td colspan="1">
					<!-- =========================表格开始==========================-->
					<div id="factorMngEditbar"></div>
					<table id="factorMngEditGrid"></table>
					<!-- =========================表格结束==========================-->
					</td>
				</tr>
			</table>
			<div class="eve_buttons" style="position: relative;">
				<input value="提交" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
	</form>

</body>

