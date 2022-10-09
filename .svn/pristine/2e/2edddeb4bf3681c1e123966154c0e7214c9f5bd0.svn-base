<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusEsuperChangeSeeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusEsuperChangeSeeForm").serialize();
				var url = $("#BusEsuperChangeSeeForm").attr("action");
    			AppUtil.ajaxProgress({
					url : url,
					params : formData,
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
		$('#BusEsuperChangeSeeGrid').datagrid({  //初始化datagrid
		    url:'busEsuperChangeController.do?columnDatagrid',
		    idField:"SERIAL_ID",
		    rownumbers: true,
		    fit:true,
		    border:false,
		    checkOnSelect:false,
		    selectOnCheck:false,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:false,
		    toolbar: "#BusEsuperChangeSeebar",
		    queryParams: {
		    	"Q_T.PROCESS_CODE_EQ" : '${busProcess.PROCESS_CODE}',
		    	"Q_T.APPLY_ID_EQ" : '${busProcess.APPLY_ID}'
			},	
			columns:[[
		        //{field:'ck',checkbox:true},
		        {field:'SERIAL_ID',hidden:true},
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
		        {field:'BUSSYS_SN',title:'排序',width:100,align:'left',editor:'numberbox'}
		    ]]
		});
		AppUtil.initAuthorityRes("BusEsuperChangeSeebar");
	});
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusEsuperChangeSeeForm" method="post"
		action="busEsuperChangeController.do?saveEditColumn">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="PROCESS_ID" value="${busProcess.PROCESS_ID}">
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
		<div id="BusEsuperChangeSeebar">
			<input type="hidden" name="Q_T.PROCESS_CODE_EQ" value="${busProcess.PROCESS_CODE}">
		</div>
		<table id="BusEsuperChangeSeeGrid"></table>
		<!-- =========================表格结束==========================-->
		<div class="eve_buttons">
			<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

