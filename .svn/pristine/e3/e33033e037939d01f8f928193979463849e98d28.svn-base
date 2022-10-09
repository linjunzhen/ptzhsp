<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/yrdwbg/js/yrdwbg.js"></script>
<script type="text/javascript">
	$(function (){
			//空数据，横向滚动
			$('#selectUnitInfosGrid').datagrid({
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
		var rows = $("#selectUnitInfosGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if(rows.length>allowCount){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}else if(rows.length < allowCount){
			alert("至少选择一条记录！");
			return;
		}	
		art.dialog.data("dwInfos", rows);	
		AppUtil.closeLayer();
	}
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('selectUnitInfosToolbar','selectUnitInfosGrid')
		$('#selectUnitInfosGrid').datagrid({
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
			<div id="selectUnitInfosToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="selectUnitInfosForm">
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
								<td style="width:120px;text-align:right;">单位档案号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:128px;"
									name="DWDAH" />
								</td>
							</tr>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">税号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:128px;"
									name="SH" />									
								</td>
								<td style="width:120px;text-align:right;">核对标记：</td>
								<td style="width:135px;">
									<input class="easyui-combobox"
		                                style="width:128px;" name="HDBJ"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',
		                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
		                                panelWidth: 128,panelHeight: 'auto' " />
								</td>
								<td colspan="2"><input type="button" value="查询"
									class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('selectUnitInfosForm');" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectUnitInfosGrid" fitColumns="false" toolbar="#selectUnitInfosToolbar"
				method="post" idField="YWH" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybYrdwbgController.do?unitInfosDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'YWH',hidden:true">单位ID</th>
						<th data-options="field:'SBBM',align:'left'" width="10%">社保登记证号码</th>
						<th data-options="field:'DWBXH',align:'left'" width="10%">单位保险号</th>
						<th data-options="field:'CBRQ',align:'left'" width="10%">参保日期</th>
						<th data-options="field:'DWMC',align:'left'" width="10%">单位名称</th>
						<th data-options="field:'DWDAH',align:'left'" width="10%">单位档案号</th>
						<th data-options="field:'DWLX',align:'left'" width="10%">单位类型</th>
						<th data-options="field:'DWBH',align:'left'" width="10%">单位编号</th>
						<th data-options="field:'SH',align:'left'" width="10%">税号</th>
						<th data-options="field:'LSGX',align:'left'" width="10%">隶属关系</th>
						<th data-options="field:'DWLB',align:'left'" width="10%">单位类别</th>
						<th data-options="field:'SHBZ',align:'left'" width="10%">审核标志</th>
						<th data-options="field:'HDBZ',align:'left'" width="10%">核对标记</th>
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

