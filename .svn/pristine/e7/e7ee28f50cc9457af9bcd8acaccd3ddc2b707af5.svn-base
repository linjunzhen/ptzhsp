<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
		var chooseClassify = $("#chooseClassify").val();
		if(chooseClassify.length>0){
    		art.dialog.data("selectClassify", {
    			chooseClassify:chooseClassify
			});
		}
   		AppUtil.closeLayer();
    	
    }

	function addToChoose(){
		var classifyName = $("input[name='classifyName']").val();
		var choose = $("#chooseClassify").val();
		if(choose.length>0){		
			if(choose.indexOf(classifyName)>=0){
				return;
			}
			$("#chooseClassify").val(choose+","+classifyName);
		}else{
			$("#chooseClassify").val(classifyName);
		}
	}
	
	function dosearch(){
		$('#TZIndustry').combobox('clear');
	    var queryName = $("input[name='queryName']").val();
    	var url = "commercialController/loadTZIndustry.do?Q_t.indu_name_LIKE="+queryName;
    	$('#TZIndustry').combobox('reload',url);
	}
	
	function addToChooseScope(){
		var choose = $("#chooseScope").val();
		var classifyName = $("#TZIndustry").combobox('getText'); 
		if(choose.length>0){		
			if(choose.indexOf(classifyName)>=0){
				return;
			}
			$("#chooseScope").val(choose+","+classifyName);
		}else{
			$("#chooseScope").val(classifyName);
		}
	}
	
	function doSelectScope(){
		var chooseScope = $("#chooseScope").val();
		if(chooseScope.length>0){
    		art.dialog.data("selectClassify", {
    			chooseClassify:chooseScope
			});
		}
   		AppUtil.closeLayer();
    	
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-tabs eve-tabs" id="tabRegion" fit="true"
		style="width: 100%;height: 100%;">
		<div title="级联筛选">
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">一级分类：</span>
						<input name="firfl" class="easyui-combobox"
						style="width:232px; height:26px;"
						data-options="
			                url:'commercialController/loadIndustry.do?parentCode=0',
			                method:'post',
			                valueField:'INDU_CODE',
			                textField:'INDU_NAME',
			                panelHeight:'280',
			                editable:false,
			                onSelect:function(rec){
							   $('#secfl').combobox('clear');
							   $('#thrfl').combobox('clear');
							   $('#fourfl').combobox('clear');
							   if(rec.INDU_CODE){
							       var url = 'commercialController/loadIndustry.do?parentCode='+rec.INDU_CODE;
							       $('#secfl').combobox('reload',url);  
							   }
							   $('input[name=\'classifyName\']').val(rec.INDU_NAME);
							   $('input[name=\'classifyCode\']').val(rec.INDU_CODE);
							   $('#classifyRemark').val(rec.REMARK);
							}
	                " />
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">二级分类：</span> <input
						name="secfl" class="easyui-combobox"
						style="width:232px; height:26px;" id="secfl"
						data-options="
			                url:'commercialController/loadIndustry.do',
			                method:'post',
			                valueField:'INDU_CODE',
			                textField:'INDU_NAME',
			                panelHeight:'auto',
			                editable:false,
			                onSelect:function(rec){
							   $('#thrfl').combobox('clear');
							   $('#fourfl').combobox('clear');
							   if(rec.INDU_CODE){
							       var url = 'commercialController/loadIndustry.do?parentCode='+rec.INDU_CODE;
							       $('#thrfl').combobox('reload',url);  
							   }
							   $('input[name=\'classifyName\']').val(rec.INDU_NAME);
							   $('input[name=\'classifyCode\']').val(rec.INDU_CODE);
							   $('#classifyRemark').val(rec.REMARK);
							}
	                " />
					</td>
				</tr>

				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">三级分类：</span>
						<input name="thrfl" class="easyui-combobox"
						style="width:232px; height:26px;" id="thrfl"
						data-options="
			                url:'commercialController/loadIndustry.do',
			                method:'post',
			                valueField:'INDU_CODE',
			                textField:'INDU_NAME',
			                panelHeight:'auto',
			                editable:false,
			                onSelect:function(rec){
							   $('#fourfl').combobox('clear');
							   if(rec.INDU_CODE){
							       var url = 'commercialController/loadIndustry.do?parentCode='+rec.INDU_CODE;
							       $('#fourfl').combobox('reload',url);  
							   }
							   $('input[name=\'classifyName\']').val(rec.INDU_NAME);
							   $('input[name=\'classifyCode\']').val(rec.INDU_CODE);
							   $('#classifyRemark').val(rec.REMARK);
							}
	                " />
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">四级分类：</span>
						<input name="fourfl" class="easyui-combobox"
						style="width:232px; height:26px;" id="fourfl"
						data-options="
			                url:'commercialController/loadIndustry.do',
			                method:'post',
			                valueField:'INDU_CODE',
			                textField:'INDU_NAME',
			                panelHeight:'auto',
			                editable:false,
			                onSelect:function(rec){
							  
							   $('input[name=\'classifyName\']').val(rec.INDU_NAME);
							   $('input[name=\'classifyCode\']').val(rec.INDU_CODE);
							   $('#classifyRemark').val(rec.REMARK);
							}
	                " />
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">当前分类名称：</span> <input
						name="classifyName" type="text" class="eve-input"
						readonly="readonly" style="width: 230px;"></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">当前分类编码：</span> <input
						name="classifyCode" type="text" class="eve-input"
						readonly="readonly" style="width: 230px;"></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">当前分类说明：</span> <textarea
							name="classifyRemark" id="classifyRemark" class="eve-textarea"
							rows="5" readonly="readonly" style="width: 330px;"></textarea></td>
				</tr>
				<tr>
					<td style="text-align: center;"><input type="button"
						value="加入所选" onclick="addToChoose();"> <input
						type="button" value="清空所选" onclick="$('#chooseClassify').val('');">
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">所选信息：</span>
						<textarea rows="5" cols="10" style="width: 330px;"
							name="chooseClassify" id="chooseClassify" readonly="readonly">${selected}</textarea>
					</td>
				</tr>
			</table>



			<div class="eve_buttons">
				<input value="完成" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
		<div title="名称筛选">

			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">名称：</span>
						<input name="queryName" type="text" class="eve-input"> <input
						type="button" value="查询" onclick="dosearch();"></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">选择范围：</span>
						<input name="TZIndustry" class="easyui-combobox"
						style="width:232px; height:26px;" id="TZIndustry"
						data-options="
			                url:'commercialController/loadTZIndustry.do',
			                method:'post',
			                valueField:'INDU_CODE',
			                textField:'INDU_NAME',
			                panelHeight:'180',
			                editable:false
	                " />
						<input type="button" value="加入所选" onclick="addToChooseScope();">
						<input type="button" value="清空所选"
						onclick="$('#chooseScope').val('');"></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">已选范围：</span>
						<textarea rows="5" cols="10" style="width: 330px;"
							name="chooseScope" id="chooseScope" readonly="readonly">${selected }</textarea>
					</td>
				</tr>
			</table>
			<div class="eve_buttons">
				<input value="完成" type="button" onclick="doSelectScope();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
	</div>

</body>

