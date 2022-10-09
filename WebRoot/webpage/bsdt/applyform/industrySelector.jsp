<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	
	function dosearch(){
		$('#TZIndustry').combobox('clear');
	    var queryName = $("input[name='queryName']").val();
    	var url = "commercialController/loadTZIndustry.do?Q_t.indu_name_LIKE="+queryName;
    	$('#TZIndustry').combobox('reload',url);
	}
	
	function addToChooseIndustry(){
		var choose = $("#chooseIndustry").val();
		var industryCodes = $("input[name='industryCodes']").val();
		var industryName = $("#TZIndustry").combobox('getText'); 
		var industryCode = $("input[name='industryCode']").val();
		var industryInfo = industryName+"("+industryCode+")";
		if(choose.length>0){		
			if(choose.indexOf(industryInfo)>=0){
				return;
			}
			$("#chooseIndustry").val(choose+","+industryInfo);
			$("input[name='industryCodes']").val(industryCodes+","+industryCode);
		}else{
			$("#chooseIndustry").val(industryInfo);
			$("input[name='industryCodes']").val(industryCode);
		}
	}
	
	function doSelectIndustry(){
		var chooseIndustry = $("#chooseIndustry").val();
		var industryCodes = $("input[name='industryCodes']").val();
		if(chooseIndustry.length>0){
    		art.dialog.data("selectIndustry", {
    			chooseIndustry:chooseIndustry,
    			industryCodes:industryCodes
			});
		}
   		AppUtil.closeLayer();
    	
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div data-options="region:'center'" fit="true" style="width: 100%;height: 100%;">
		
		<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">名称：</span>
					<input name="queryName" type="text" class="eve-input">
					<input type="button" value="查询" onclick="dosearch();">
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">选择行业：</span>
					<input name="TZIndustry" class="easyui-combobox"  style="width:232px; height:26px;" id="TZIndustry"
						data-options="
			                url:'commercialController/loadTZIndustry.do',
			                method:'post',
			                valueField:'INDU_CODE',
			                textField:'INDU_NAME',
			                panelHeight:'180',
			                editable:false,
			                onSelect:function(rec){
							  
							   $('input[name=\'industryName\']').val(rec.INDU_NAME);
							   $('input[name=\'industryCode\']').val(rec.INDU_CODE);
							}
	                " />
	             </td>
			</tr>
			<tr>
				<td colspan="2"><span
					style="width: 100px;float:left;text-align:right;">当前行业名称：</span>
					<input name="industryName" type="text" class="eve-input" readonly="readonly" style="width: 230px;">
				</td>
			</tr>
			<tr>
				<td colspan="2"><span
					style="width: 100px;float:left;text-align:right;">当前行业编码：</span>
					<input name="industryCode" type="text" class="eve-input" readonly="readonly" style="width: 230px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
	                <input type="button" value="加入所选" onclick="addToChooseIndustry();">
					<input type="button" value="清空所选" onclick="$('#chooseIndustry').val('');">
				</td>				
			</tr>
			<tr>
				<td><span
					style="width: 100px;float:left;text-align:right;">已选范围：</span>
					<input type="hidden" name="industryCodes" value="${industryCode}" />
					<textarea rows="5" cols="10" style="width: 330px;" name="chooseIndustry" id="chooseIndustry" readonly="readonly">${selected }</textarea>
				</td>
			</tr>
		</table>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="完成" type="button" onclick="doSelectIndustry();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
</div>
	
</body>

