<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/yrdwbg/js/yrdwbg.js"></script>
<script type="text/javascript">
	$(function (){
			//空数据，横向滚动
			$('#selectZxUnitGrid').datagrid({
				onLoadSuccess: function(data){
					if(data.total==0){
						var dc = $(this).data('datagrid').dc;
				        var header2Row = dc.header2.find('tr.datagrid-header-row');
				        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
			        }
				}
			});
	});
	
	//确定操作
	function doSelectRows(){
		var rows = $("#selectZxUnitGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if(rows.length>allowCount){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}else if(rows.length < allowCount){
			alert("至少选择一条记录！");
			return;
		}	
		art.dialog.data("zxInfos", rows);	
		AppUtil.closeLayer();
	}
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('selectZxUnitToolbar','selectZxUnitGrid')
		$('#selectZxUnitGrid').datagrid({
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
		<div data-options="region:'center',split:false">
			<div id="selectZxUnitToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="selectZxUnitForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">单位保险号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:135px;"
									name="DWBXH" /></td>
								<td style="width:120px;text-align:right;">单位名称：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:128px;"
									name="DWMC" /></td>
								<td colspan="2"><input type="button" value="查询"
									class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('selectZxUnitForm');" /></td>
							</tr>		
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectZxUnitGrid" fitColumns="false" toolbar="#selectZxUnitToolbar"
				method="post" idField="ZXID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybYrdwzxController.do?zxInfoDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ZXID',hidden:true">单位ID</th>
						<th data-options="field:'ZX_DWBXH',align:'left'" width="10%">单位保险号</th>
						<th data-options="field:'ZX_DWMC',align:'left'" width="10%">单位名称</th>
						<th data-options="field:'ZX_HDBJ',align:'left'" width="10%">核对标记</th>
						<th data-options="field:'ZX_DWRS',align:'left'" width="10%">单位人数</th>
						<th data-options="field:'ZX_SH',align:'left'" width="10%">税号</th>
						<th data-options="field:'ZX_JGBM',align:'left'" width="10%">组织机构编码</th>
						<th data-options="field:'ZX_DABH',align:'left'" width="10%">档案编号</th>
						<th data-options="field:'ZX_DSBDJG',align:'left'" width="10%">地税比对结果</th>
					</tr>
				</thead>
			</table>	
		</div>		
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons" style="text-align: right;">
				<input value="确认" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>	
</body>

