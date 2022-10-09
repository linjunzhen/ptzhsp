<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!-- 单位基本信息查询弹窗界面 -->
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/sbqlc/qyzgbgdj/js/qyzgbgdj.js"></script>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	
	$(function (){
		//空数据，横向滚动
		$('#yhxxcxSelectorGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});	
	});
		
		
    //确定
	function doSelectRows(){
		var rows = $("#yhxxcxSelectorGrid").datagrid("getChecked");
    	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			art.dialog.data("selectInfo", {
				unitFile:rows[0].aab511,//单位档案号
    			unitInsurance:rows[0].aab999,//单位保险号
    			unitName:rows[0].aab004,//单位名称
    			unitNum:rows[0].aab001	//单位编号	
			});// 存储数据
			AppUtil.closeLayer();
		}
		
	}
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('yhxxcxSelectorToolbar','yhxxcxSelectorGrid');
		$('#yhxxcxSelectorGrid').datagrid({
    		onLoadSuccess:function(data){
   		        //确保数据初始化后只执行一次该方法
    			if((count=='1')&& data.total==0){
	    		  	 art.dialog({
						content : "无匹配数据返回，查询记录为空。",
						icon : "warning",
						ok : true
					 });
					count ++;	
	    		}  
    		}
    	}); 
	}
	
	
	//数据格式化
	function dataFormat(value,json){
		var data = JSON.parse(json);
		var rtn = "";
		$.each(data, function(idx, dic) {
			if(value==dic.DIC_CODE){
				rtn = dic.DIC_NAME;
				return false;
			}
		});
		return rtn;
	}
	//单位状态格式化
	var dwztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'YESNO'}
	});
	function formatDwzt(value){
		var json = dwztObj.responseText;
		return dataFormat(value,json);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="yhxxcxSelectorToolbar">
				<form action="#" name="yhxxcxSelectorForm" id="yhxxcxSelectorForm">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:100px;text-align:right;">银行名称：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="YHMC" />
							</td>
							<td>
								<input type="button" value="查询" class="eve-button"
								onclick="search();" />
								<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('yhxxcxSelectorForm');" />
							</td>
						</tr>						
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="yhxxcxSelectorGrid" fitColumns="true" toolbar="#yhxxcxSelectorToolbar"
				method="post"  checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="">
				<thead>
					<tr>
						<th field="ck" checkbox="true" ></th>						
						<th data-options="field:'aab002',align:'center'" width="15%">银行编号</th>
						<th data-options="field:'aab999',align:'center'" width="20%">银行名称</th>
						<th data-options="field:'tbrqi0',align:'center'" width="20%">网点编码</th>
						<th data-options="field:'aab004',align:'center'" width="20%">网点名称</th>
						<th data-options="field:'aab511',align:'center'" width="20%">金融行业机构编码</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false" >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="取消" type="button" onclick="AppUtil.closeLayer();" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />			
			</div>
		</div>
	</div>
</body>

