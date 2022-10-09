<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
	<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusTacheChangeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusTacheChangeForm").serialize();
				var url = $("#BusTacheChangeForm").attr("action");
				/**====可编辑表格数据开始======**/
				var insertRows = $('#BusTacheChangeEditGrid').datagrid('getChanges','inserted');
					var updateRows = $('#BusTacheChangeEditGrid').datagrid('getChanges','updated');
					var deleteRows = $('#BusTacheChangeEditGrid').datagrid('getChanges','deleted');
					var changesRows = {
					inserted : [],
					updated : [],
					deleted : [],
				};
				var rows  = $('#BusTacheChangeEditGrid').datagrid("getRows");
				for(var k=0;k<rows.length;k++){
				if(rows[k].editing){
						$('#BusTacheChangeEditGrid').datagrid('endEdit', k);
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
							parent.$("#TacheChangeGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_TACHE_CHANGE");
		/**
		* 初始化表格
		*/
		$('#BusTacheChangeEditGrid').datagrid({  //初始化datagrid
		    url:'busSpecialChangeController.do?tacheDatagrid',
		    idField:"CHANGE_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#BusTacheChangeEditbar",
		    queryParams: {
		    	"Q_T.BUS_CODE_EQ" : "${busCode}",
		    	"Q_T.APPLY_ID_EQ" : "${applyId}",
		    	"Q_T.IS_VALID_EQ" : '1'
			},	
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'CHANGE_ID',hidden:true},
		        {field:'APPLY_ID',hidden:true},
		        {field:'TACHE_NAME',title:'环节名称',width:200,align:'left',editor:{type:'text',options:{maxlength:"20"}}},
		        {field:'TREE_SN',title:'排序',width:100,align:'left',editor:'numberbox'},
		        {field:'STATUS',title:'操作',width:100,align:'center',
					formatter:function(value,row,index){
		        	var d = '<a href="#" onclick="deleterow('+index+',\''+row.CHANGE_ID+'\')">删除</a>';
					return d;
					}
				}
		    ]],
		    toolbar:[{ text:'新增',iconCls:'icon-note-add',handler:addrow}],
		    onBeforeEdit:function(index,row){
		        row.editing = true;
		        $('#BusTacheChangeEditGrid').datagrid('refreshRow', index);
		        editcount++;
		    },
		    onAfterEdit:function(index,row){
		        row.editing = false;
		        $('#BusTacheChangeEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onCancelEdit:function(index,row){
		        row.editing = false;
		        $('#BusTacheChangeEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onClickRow:function(index,row){
				row.editing = true;
				$('#BusTacheChangeEditGrid').datagrid('beginEdit', index);
				$("input[class='datagrid-editable-input']").attr("maxlength","20");
				
			}
		});
		AppUtil.initAuthorityRes("BusTacheChangeEditbar");
	});
	var editcount = 0;
	function addrow(){
		var length = $('#BusTacheChangeEditGrid').datagrid("getRows").length+1
		$('#BusTacheChangeEditGrid').datagrid('appendRow',{
			CHANGE_ID: length,
			TACHE_CODE:'',
			TACHE_NAME:'',
			TREE_SN:length
		});
		var rows  = $('#BusTacheChangeEditGrid').datagrid("getRows");
		for(var k=0;k<rows.length;k++){
			rows[k].editing = true;
			$('#BusTacheChangeEditGrid').datagrid('beginEdit', k);
			$(".textbox-text").eq(k).val(k+1);
			$(".textbox-value").eq(k).val(k+1);
		}
		$("input[class='datagrid-editable-input']").attr("maxlength","20");
	}
	function editrow(index){
		$('#BusTacheChangeEditGrid').datagrid('beginEdit', index);
	}
	function deleterow(index,val){
		var i = index;
		var rows  = $('#BusTacheChangeEditGrid').datagrid("getRows");
		for(var k=0;k<rows.length;k++){
			if(rows[k].CHANGE_ID == val){
				i = k;
			}
		}
		
		$.messager.confirm('确认','是否真的删除?',function(r){
			if (r){
				$('#BusTacheChangeEditGrid').datagrid('deleteRow', i);
				rows = $('#BusTacheChangeEditGrid').datagrid("getRows");
				for(var k=0;k<rows.length;k++){
					rows[k].editing = true;
					$('#BusTacheChangeEditGrid').datagrid('beginEdit', k);
					$(".textbox-text").eq(k).val(k+1);
					$(".textbox-value").eq(k).val(k+1);
				}
			}
		});
	}
	function saverow(index){
		$('#BusTacheChangeEditGrid').datagrid('endEdit', index);
	}
	function cancelrow(index){
		$('#BusTacheChangeEditGrid').datagrid('cancelEdit', index);
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusTacheChangeForm" method="post"
		action="busSpecialChangeController.do?saveTacheChange">
		<input type="hidden" name="BUS_CODE" value="${busCode}">
		<!--==========隐藏域部分开始 ===========-->
		<%--<input type="hidden" name="BUS_ID" value="${busSpecial.BUS_ID}">
		<input type="hidden" name="CREATE_TIME" value="${busSpecial.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busSpecial.CREATE_USER}">
		--%><!--==========隐藏域部分结束 ===========-->
		<input type="hidden" name="APPLY_ID" value="${applyId}">
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
					<div id="BusTacheChangeEditbar"></div>
					<table id="BusTacheChangeEditGrid"></table>
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

