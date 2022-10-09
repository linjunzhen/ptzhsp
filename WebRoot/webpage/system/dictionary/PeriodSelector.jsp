<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,ztree,artdialog"></eve:resources>
<script type="text/javascript" src="plug-in/ztree-3.5/js/jquery.ztree.exhide-3.5.min.js"></script>  
<script type="text/javascript">
/**
 * 提交时执行函数
 */
function selectPeriod(){
	var $dataGrid = $("#PeriodGrid");
	var rowsData = $dataGrid.datagrid('getChecked');
	if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
		art.dialog({
			content: "请选择一条记录!",
			icon:"warning",
		    ok: true
		});

	}else{
		var callbackFn = $("input[name='callbackFn']").val();
	    parent.frames[0].eval(callbackFn)(rowsData[0].DIC_ID,rowsData[0].DIC_NAME);
	    AppUtil.closeLayer();
	}
}

$(function() {
	$('#period_id').combobox({  //初始化站点模板组下拉框
		url:'dictionaryController.do?loadPeriod&typeCode=InfoCenterTerm',
		valueField:'TYPE_ID',
		textField:'TYPE_NAME',
		editable:false,
		method: 'post',
		panelWidth: 180,
		panelHeight: 'auto',
		onSelect: function(rec){  //选择站点模板组触发事件
			$('#PeriodGrid').treegrid('clearSelections');
			$('#PeriodGrid').datagrid('reload',{
				"Q_T.TYPE_ID_=" : $('#period_id').combobox('getValue')
	    	});
		},
		onLoadSuccess: function(param){		
			$('#PeriodGrid').datagrid({  //初始化datagrid
				    url:'dictionaryController.do?datagrid',
				    idField:"DIC_ID",
				    rownumbers: true,
				    fit:true,
				    border:false,
				    singleSelect:true,
				    fitColumns:true,
				    //pagination:true,
				    queryParams: {
				    	"Q_T.TYPE_ID_=" : $('#period_id').combobox('getValue')
					},	
				    columns:[[
				        {field:'DIC_ID',hidden:true},
				        {field:'DIC_NAME',title:'会议名称',width:150,align:'left'}
				    ]]
				});			
		}
	});    	
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">	
	<input type="hidden" value="${callbackFn}" name="callbackFn">
	<div data-options="region:'west',split:false"
		style="width:320px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div >
							请选择届次：
							<input name="period_id" id="period_id" style="width:180px"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div style="height:340px;overflow-x:hidden;overflow-y:auto;">
			<table  id="PeriodGrid" ></table>
		</div>
	</div>
	<div class="eve_buttons">
		<input value="确定" type="button" onclick="selectPeriod();"
			class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
			value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
			onclick="AppUtil.closeLayer();" />
	</div>
</body>