function loadFactorList(processCode){
	$("#buscolumn_addprocess").val(processCode);
	$('#BusColumnAddGrid').datagrid({  //初始化datagrid
	    url:'busColumnEsuperController.do?columnDatagrid',
	    idField:"SERIAL_ID",
	    rownumbers: true,
	    fit:true,
	    border:false,
	    checkOnSelect:false,
	    selectOnCheck:false,
	    singleSelect:true,
	    fitColumns:true,
	    pagination:false,
	    toolbar: "#BusColumnAddbar",
	    queryParams: {
	    	"Q_T.PROCESS_CODE_EQ" : processCode
		},	
		columns:[[
	        //{field:'ck',checkbox:true},
	        {field:'SERIAL_ID',hidden:true},
	        {field:'COLUMN_NAME',title:'监察字段',width:250,align:'left',editor:'text'},
	        {field:'BUSSYS_SN',title:'排序',width:100,align:'left',editor:'numberbox'},
	        {field:'STATUS',title:'操作',width:100,align:'center',
				formatter:function(value,row,index){
					if (row.editing){
						var s = '<a href="#" onclick="saverow('+index+')">保存</a> ';
						var c = '<a href="#" onclick="cancelrow('+index+')">取消</a>';
						return s+c;
					} else {
						var e = '<a href="#" onclick="editrow('+index+')">编辑</a> ';
						var d = '<a href="#" onclick="deleterow('+index+')">删除</a>';
						return e+d;
					}
				}
			}
	    ]],
	    toolbar:[{ text:'新增',iconCls:'icon-add',handler:addrow},
	    		{ text:'保存',iconCls:'icon-save',handler:saveData}],
	    onBeforeEdit:function(index,row){
	        row.editing = true;
	        $('#BusColumnAddGrid').datagrid('refreshRow', index);
	        editcount++;
	    },
	    onAfterEdit:function(index,row){
	        row.editing = false;
	        $('#BusColumnAddGrid').datagrid('refreshRow', index);
	        editcount--;
	    },
	    onCancelEdit:function(index,row){
	        row.editing = false;
	        $('#BusColumnAddGrid').datagrid('refreshRow', index);
	        editcount--;
	    }	
	});
}
var editcount = 0;
function editrow(index){
	$('#BusColumnAddGrid').datagrid('beginEdit', index);
}
function deleterow(index){
	$.messager.confirm('确认','是否真的删除?',function(r){
		if (r){
			$('#BusColumnAddGrid').datagrid('deleteRow', index);
		}
	});
}
function saverow(index){
	$('#BusColumnAddGrid').datagrid('endEdit', index);
}
function cancelrow(index){
	$('#BusColumnAddGrid').datagrid('cancelEdit', index);
}
function addrow(){
	if (editcount > 0){
		$.messager.alert('警告','当前还有'+editcount+'记录正在编辑，不能增加记录。');
		return;
	}
	$('#BusColumnAddGrid').datagrid('appendRow',{
		COLUMN_NAME:'',
		BUSSYS_SN:''
	});
}
function saveData(){
	var url = $("#BusColumnAddForm").attr("action");
				
	var insertRows = $('#BusColumnAddGrid').datagrid('getChanges','inserted');
	var updateRows = $('#BusColumnAddGrid').datagrid('getChanges','updated');
	var deleteRows = $('#BusColumnAddGrid').datagrid('getChanges','deleted');
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
	var processValue = $('#buscolumn_addprocess').val();
	if(processValue){
		AppUtil.ajaxProgress({
			url : url,
			params : {
				jsonDatas : jsonDatas,
				processCode : processValue
			},
			callback : function(resultJson) {
				if (resultJson.success) {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
				} else {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
			}
		});
	}else{
		$.messager.alert('警告',"请选择监察节点!");
		return;
	}	
}