<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%--抵押权注销登记 不动产抵押档案查询 --%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">


    function doSelectRows(){
    	var rows = $("#bdcdydacxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}	
		art.dialog.data("bdcdydacxInfo", rows);	
		AppUtil.closeLayer();
    	
    	
    }
	
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	
	function searchReset() {
		$("input[name='Q_T.NEGATIVELIST_NAME_LIKE']").val("");
	}
	
	//查询
	function search(){
	    var count=1;
		AppUtil.gridDoSearch('bdcdydacxToolbar','bdcdydacxGrid');
		$('#bdcdydacxGrid').datagrid({
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

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="bdcdydacxToolbar">
				<form action="#" name="bdcdydacxForm">
				<!--====================开始编写隐藏域============== -->
			 	
				<!--====================结束编写隐藏域============== -->
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:130px;text-align:right;">不动产单元号：</td>
						<td style="width:250px;"><input class="eve-input"
							type="text" maxlength="30" style="width:200px;"
							name="bdcdyh" /></td>
						<td style="width:130px;text-align:right;">不动产登记证明号：</td>
						<td style="width:250px;"><input class="eve-input"
							type="text" maxlength="20" style="width:200px;"
							name="bdcdjzmh" /></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="search();" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('bdcdydacxForm');" /></td>
					</tr>
				</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="bdcdydacxGrid" fitColumns="true" toolbar="#bdcdydacxToolbar"
				method="post"  checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcDyqzxdjController.do?datagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'YWH',hidden:true" width="80">业务号</th>
						<th data-options="field:'BDCQZH',hidden:true" width="80">不动产权证号</th>
						<th data-options="field:'QSZT',align:'left'" width="80">权属状态</th>
						<th data-options="field:'BDCDYH',align:'left'" width="140">不动产单元号</th>
						<th data-options="field:'BDCDJZMH',align:'left'" width="140">不动产登记证明号</th>
						<th data-options="field:'DYQR',align:'left'" width="200">抵押权人</th>
						<th data-options="field:'ZWR',align:'left'" width="50">债务人</th>
						<th data-options="field:'DYR',align:'left'" width="50">抵押人</th>
						<th data-options="field:'ZH',align:'left'" width="100">幢号</th>
						<th data-options="field:'HH',align:'left'" width="100">户号</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>
</body>

