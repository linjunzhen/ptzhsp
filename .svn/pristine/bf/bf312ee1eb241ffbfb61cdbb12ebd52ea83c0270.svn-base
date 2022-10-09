<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
	<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusTacheForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusTacheForm").serialize();
				var url = $("#BusTacheForm").attr("action");
				/**====可编辑表格数据开始======**/
				var insertRows = $('#BusTacheEditGrid').datagrid('getChanges','inserted');
					var updateRows = $('#BusTacheEditGrid').datagrid('getChanges','updated');
					var deleteRows = $('#BusTacheEditGrid').datagrid('getChanges','deleted');
					var changesRows = {
					inserted : [],
					updated : [],
					deleted : [],
				};
				var rows  = $('#BusTacheEditGrid').datagrid("getRows");
				for(var k=0;k<rows.length;k++){
					if(rows[k].editing){
						$('#BusTacheEditGrid').datagrid('endEdit', k);
					}
				}
				if (insertRows.length>0) {
					for (var i=0;i<insertRows.length;i++) {
						if(insertRows[i].editing==true){
							$.messager.alert('警告',"表格中数据未保存，请保存...");
							return;
						}
						if(insertRows[i].TACHE_NAME==''){
							$.messager.alert('警告',"环节名称不能为空，请输入...");
							return;						
						}
						if(insertRows[i].TACHE_NAME.length > 20){
							$.messager.alert('警告',insertRows[i].TACHE_NAME+"环节名称长度大于20个字符，请重新输入...");
							return;	
						}
						if(insertRows[i].TREE_SN==''){
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
						if(updateRows[k].TACHE_NAME==''){
							$.messager.alert('警告',"环节名称不能为空，请输入...");
							return;
						}
						if(updateRows[k].TACHE_NAME.length > 20){
							$.messager.alert('警告',updateRows[k].TACHE_NAME+"环节名称长度大于20个字符，请重新输入...");
							return;	
						}
						if(updateRows[k].TREE_SN==''){
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
							parent.$("#TacheGrid").datagrid("reload");
							parent.$("#SpecialGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_TACHE");
		/**
		* 初始化表格
		*/
		$('#BusTacheEditGrid').datagrid({  //初始化datagrid
		    url:'busSpecialController.do?tacheDatagrid',
		    idField:"TACHE_ID",
		    rownumbers: false,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#BusTacheEditbar",
		    queryParams: {
		    	"Q_T.BUS_CODE_EQ" : "${busCode}"
			},	
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'TACHE_ID',hidden:true},
		        {field:'TACHE_CODE',title:'环节编码',width:80,hidden:true},
		        {field:'TACHE_NAME',title:'环节名称',width:200,align:'left',editor:{type:'text',options:{maxlength:"20"}}},
		        {field:'TREE_SN',title:'排序',width:100,align:'left',editor:'numberbox'},
		        {field:'STATUS',title:'操作',width:100,align:'center',
					formatter:function(value,row,index){
						var d = '<a href="#" onclick="deleterow('+index+',\''+row.TACHE_ID+'\')">删除</a>';
						return d;
					}
				}
		    ]],
		    toolbar:[{ text:'新增',iconCls:'icon-note-add',handler:addrow}],
		    onBeforeEdit:function(index,row){
		        row.editing = true;
		        $('#BusTacheEditGrid').datagrid('refreshRow', index);
		        editcount++;
		    },
		    onAfterEdit:function(index,row){
		        row.editing = false;
		        $('#BusTacheEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onCancelEdit:function(index,row){
		        row.editing = false;
		        $('#BusTacheEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onClickRow:function(index,row){
				row.editing = true;
				$('#BusTacheEditGrid').datagrid('beginEdit', index);
				$("input[class='datagrid-editable-input']").attr("maxlength","20");
			}	
		});
		AppUtil.initAuthorityRes("BusTacheEditbar");
	});
	var editcount = 0;
	function addrow(){
		
		var length = $('#BusTacheEditGrid').datagrid("getRows").length+1
		$('#BusTacheEditGrid').datagrid('appendRow',{
			TACHE_ID: length,
			TACHE_CODE:'',
			TACHE_NAME:'',
			TREE_SN:length
		});
		var rows  = $('#BusTacheEditGrid').datagrid("getRows");
		for(var k=0;k<rows.length;k++){
			rows[k].editing = true;
			$('#BusTacheEditGrid').datagrid('beginEdit', k);
			$(".textbox-text").eq(k).val(k+1);
			$(".textbox-value").eq(k).val(k+1);
		}
		$("input[class='datagrid-editable-input']").attr("maxlength","20");
	}
	function editrow(index){
		$('#BusTacheEditGrid').datagrid('beginEdit', index);
	}
	function deleterow(index,val){
		var i = index;
		var rows  = $('#BusTacheEditGrid').datagrid("getRows");
		for(var k=0;k<rows.length;k++){
			if(rows[k].TACHE_ID == val){
				i = k;
			}
		}
		
		$.messager.confirm('确认','是否真的删除?',function(r){
			if (r){
				$('#BusTacheEditGrid').datagrid('deleteRow', i);
				rows = $('#BusTacheEditGrid').datagrid("getRows");
				for(var k=0;k<rows.length;k++){
					rows[k].editing = true;
					$('#BusTacheEditGrid').datagrid('beginEdit', k);
					$(".textbox-text").eq(k).val(k+1);
					$(".textbox-value").eq(k).val(k+1);
				}
			}
		});
	}
	function saverow(index){
		$('#BusTacheEditGrid').datagrid('endEdit', index);
	}
	function cancelrow(index){
		$('#BusTacheEditGrid').datagrid('cancelEdit', index);
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusTacheForm" method="post"
		action="busSpecialController.do?saveTache">
		<input type="hidden" name="BUS_CODE" value="${busCode}">
		<!--==========隐藏域部分开始 ===========-->
		<%--<input type="hidden" name="BUS_ID" value="${busSpecial.BUS_ID}">
		<input type="hidden" name="CREATE_TIME" value="${busSpecial.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busSpecial.CREATE_USER}">
		--%><!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务专项：</span>
					<input type="text" style="width:250px;float:left;" maxlength="128"
					class="eve-input" value="${busName}" readonly/><font
					class="dddl_platform_html_requiredFlag" >*</font>
				</td>					
			</tr>
		</table>
		<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">环节信息</td>
				</tr>
				<tr style="height:250px;">
					<td colspan="1">
					<!-- =========================表格开始==========================-->
					<div id="BusTacheEditbar"></div>
					<table id="BusTacheEditGrid"></table>
					<!-- =========================表格结束==========================-->
					</td>
				</tr>
			</table>
		<div class="eve_buttons">
			<input value="提交" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

