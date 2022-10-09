<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusSpecialForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusSpecialForm").serialize();
				var url = $("#BusSpecialForm").attr("action");
				/**====可编辑表格数据开始======**/
				var insertRows = $('#BusSpecialEditGrid').datagrid('getChanges','inserted');
					var updateRows = $('#BusSpecialEditGrid').datagrid('getChanges','updated');
					var deleteRows = $('#BusSpecialEditGrid').datagrid('getChanges','deleted');
					var changesRows = {
					inserted : [],
					updated : [],
					deleted : [],
				};
				var rows  = $('#BusSpecialEditGrid').datagrid("getRows");
				for(var k=0;k<rows.length;k++){
					if(rows[k].editing){
						$('#BusSpecialEditGrid').datagrid('endEdit', k);
					}
				}
				if(rows.length <= 0){
					$.messager.alert('警告',"尚未添加专项不能保存。");
					return;
				}
				if (insertRows.length>0) {
					for (var i=0;i<insertRows.length;i++) {
						if(insertRows[i].editing==true){
							$.messager.alert('警告',"表格中数据未保存，请保存...");
							return;
						}
						if(insertRows[i].BUS_NAME==''){
							$.messager.alert('警告',"专项名称不能为空，请输入...");
							return;						
						}
						if(insertRows[i].BUS_NAME.length>60){
							$.messager.alert('警告',"第"+(i+1)+"个专项名称长度不能大于60，请重输");
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
						if(updateRows[k].BUS_NAME==''){
							$.messager.alert('警告',"专项名称不能为空，请输入...");
							return;
						}
						if(updateRows[k].BUS_NAME.length>60){
							$.messager.alert('警告',"第"+(i+1)+"个专项名称长度不能大于60，请重输");
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
				if($("[name='UNIT_CODE']").val()==""){
					$.messager.alert('警告',"请选择业务单位...");
					return;
				}
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
		}, "T_LCJC_BUS_Special");
		/**
		* 初始化表格
		*/
		$('#BusSpecialEditGrid').datagrid({  //初始化datagrid
		    url:'busSpecialController.do?datagrid&Q_T.BUS_NAME_LIKE="BusSpecialEditGrid"',
		    idField:"BUS_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#BusSpecialEditbar",
		    queryParams: {
		    	//"processCode" : '${basicColumn.PROCESS_CODE}'
			},	
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'BUS_ID',hidden:true},
		        {field:'BUS_NAME',title:'专项名称',width:250,align:'left',editor:'text'},
		        {field:'STATUS',title:'操作',width:100,align:'center',
					formatter:function(value,row,index){
						
						var d = '<a href="#" onclick="deleterow('+index+',\''+row.BUS_ID+'\')">删除</a>';
						return d;
						
					}
				}
		    ]],
		    toolbar:[{ text:'新增',iconCls:'icon-note-add',handler:addrow}],
		    onBeforeEdit:function(index,row){
		        row.editing = true;
		        $('#BusSpecialEditGrid').datagrid('refreshRow', index);
		        editcount++;
		    },
		    onAfterEdit:function(index,row){
		        row.editing = false;
		        $('#BusSpecialEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onCancelEdit:function(index,row){
		        row.editing = false;
		        $('#BusSpecialEditGrid').datagrid('refreshRow', index);
		        editcount--;
		    },
		    onClickRow:function(index,row){
				row.editing = true;
				$('#BusSpecialEditGrid').datagrid('beginEdit', index);
				$("input[class='datagrid-editable-input']").attr("maxlength","60");
				//$("input[class='datagrid-editable-input']").on("input",function(e){console.log(e)});
			}
		});
		AppUtil.initAuthorityRes("BusSpecialEditbar");
	});
	var editcount = 0;
	function addrow(){
		
		$('#BusSpecialEditGrid').datagrid('appendRow',{
			BUS_NAME:'',
			BUS_CODE:''
		});
	}
	function editrow(index){
		$('#BusSpecialEditGrid').datagrid('beginEdit', index);
	}
	function deleterow(index,val){
		var i = index;
		var rows  = $('#BusSpecialEditGrid').datagrid("getRows");
		for(var k=0;k<rows.length;k++){
			if(rows[k].BUS_ID == val){
				i = k;
			}
		}
		
		$.messager.confirm('确认','是否真的删除?',function(r){
			if (r){
				$('#BusSpecialEditGrid').datagrid('deleteRow', i);
			}
		});
	}
	function saverow(index){
		$('#BusSpecialEditGrid').datagrid('endEdit', index);
	}
	function cancelrow(index){
		$('#BusSpecialEditGrid').datagrid('cancelEdit', index);
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusSpecialForm" method="post"
		action="busSpecialController.do?saveAddSpecial">
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
				<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务单位：</span>
					<c:if test="${busSpecial.BUS_ID!=null}">
					<input class="easyui-combobox validate[required]" style="width:250px;" id="UNIT_CODE" name="UNIT_CODE" value="${busSpecial.UNIT_CODE}"
						data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 250,panelHeight: '250' " disabled/>
					</c:if><c:if test="${busSpecial.BUS_ID==null}">
					<input class="easyui-combobox validate[required]" style="width:250px;"  id="UNIT_CODE" name="UNIT_CODE" value="${busSpecial.UNIT_CODE}"
						data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 250,panelHeight: '250' " />
					<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>					
			</tr>
			<%--<tr>
				<td><span style="width: 150px;float:left;text-align:right;">专项编码：</span>
					<c:if test="${busSpecial.BUS_ID!=null}">
					   ${busSpecial.BUS_CODE}
					</c:if> <c:if test="${busSpecial.BUS_ID==null}">
						<input type="text" style="width:250px;float:left;" maxlength="18"
							class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
							name="BUS_CODE" id="BUS_CODE" value="${busSpecial.BUS_CODE}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">专项名称：</span>
					<input type="text" style="width:250px;float:left;" maxlength="128"
					class="eve-input validate[required]" name="BUS_NAME" value="${busSpecial.BUS_NAME}"/><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">排序：</span>
					<input type="text" style="width:250px;float:left;" maxlength="2"
					class="easyui-numberbox validate[required]"
					value="${busSpecial.TREE_SN}" precision="0" name="TREE_SN" /><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
		--%></table>
		<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">专项信息</td>
				</tr>
				<tr style="height:250px;">
					<td colspan="1">
					<!-- =========================表格开始==========================-->
					<div id="BusSpecialEditbar"></div>
					<table id="BusSpecialEditGrid"></table>
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

