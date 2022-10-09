<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
	function formatTitle(val,row){
		return "<span style='font-size:10pt;'><strong>"+val+"</strong></span>";
	}
	function formatCode(val,row){
		if(val==null||val==undefined)val="无  ";
		var REG_NUM="无 ";
		var CREDIT_CODE="无 ";
		if(row.REG_NUM!=null&&row.REG_NUM!=undefined)REG_NUM=row.REG_NUM;
		if(row.CREDIT_CODE!=null&&row.CREDIT_CODE!=undefined)CREDIT_CODE=row.CREDIT_CODE;
		if(val==null||val==undefined)val="";
		interHtml="【机构代码】："+val;
		interHtml+="<br>";
		interHtml+="【工商注册号】： "+REG_NUM;
		interHtml+="<br>";
		interHtml+="【统一社会信息代码】： "+CREDIT_CODE;
		return interHtml;
	}

	$(function() {
		$('#CreditQueryGrid').datagrid({  //初始化datagrid
		    url:'creditController.do?datagrid',
		    idField:"CREDIT_ID",
		    rownumbers: false,
		    fit:true,
		    border:false,
		    checkOnSelect:true,
		    selectOnCheck:true,
		    singleSelect:true,
		    fitColumns:true,
		    pagination:true,
		 	pageSize:15,
		 	pageList:[15,20,30],
		    queryParams: {
		     	"CORP_NAME" : "${names}",
		    	"CORP_CODE" : "${codes}"
			},
			onDblClickRow: function(index, row){
				var regNum="";
				if(row.CORP_CODE!=null){
					regNum=row.CORP_CODE;
				}else if(row.CREDIT_CODE!=null){
					regNum=row.CREDIT_CODE;
				}else if(row.REG_NUM!=null){
					regNum=row.REG_NUM;
				}
			 	art.dialog.data("creditInfo",{
					regNum:regNum,
					corpName:row.CORP_NAME
				});
				AppUtil.closeLayer();
			},
			columns:[[
		        {field:'ck',checkbox:true},
		        {field:'CORP_NAME',title:'监察要素',width:80,align:'left',formatter:formatTitle},
		        {field:'CORP_CODE',title:'监察要素',width:120,align:'left',formatter:formatCode}
		    ]]
		});	
	});
	function closePage(){
		art.dialog.data("creditInfo",null);
		AppUtil.closeLayer();
	}
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= 
		<div id="FormFieldToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FormField"
								iconcls="icon-reload" plain="true"
								onclick="refreshFormField();">刷新表单字段</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		-->
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		
		<table id="CreditQueryGrid"></table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>
