<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/cxjmcbxb/js/cxjmcbxb.js"></script>
<script type="text/javascript">
     $(function(){
     	//空数据，横向滚动
		$('#selectDdPersonsGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});	
      });
      
    //确定按钮操作
    function doSelectRows(){
		var rows = $("#selectDdPersonsGrid").datagrid("getSelections");
    	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			art.dialog.data("ddPersons", rows);// 存储数据
			AppUtil.closeLayer();
		}
    }

	//查询操作
	function search(){
		var count = 0;
		AppUtil.gridDoSearch('selectDdPersonsToolbar','selectDdPersonsGrid');
		$('#selectDdPersonsGrid').datagrid({
			onLoadSuccess:function(){
				//确保初始化后只执行一次
				if(count == 0){
					var rows = $("#selectDdPersonsGrid").datagrid("getRows");
					if(rows.length==0){
						parent.art.dialog({
							content: "无匹配数据返回，查询记录为空！",
							icon:"warning",
							ok: true
						});
						count ++;
					}
				}
			}
		});
	}
	
	//险种类型格式化
	var xzlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'TypeOfInsurance'}
	});
	function xzlxformater(value,row,index){
		var json = xzlxObj.responseText;
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
	
	//参保状态格式化
	var cbztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredState'}
	});
	function cbztformater(value,row,index){
		var json = cbztObj.responseText;
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
	
	
</script>
</head>
<body class="eui-diabody" style="margin:0px;" >
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="selectDdPersonsToolbar">
				<form action="#" name="selectDdPersonsForm">
					<!--====================开始编写隐藏域============== -->
					
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">单位保险号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="aab999" /></td>
							<td style="width:120px;text-align:right;">单位名称：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="aab004" /></td>
							<td style="width:120px;text-align:right;">单位编号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="aab001"/></td>
							<td style="width:120px;text-align:right;">个人保险号：</td>
							<td style="width:135px;"><input class="eve-input"
							    type="text" name="bac503" /></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">证件号码：</td>
							<td style="width:135px;"><input class="eve-input"
							type="text" name="aac002" /></td>
							<td style="width:120px;text-align:right;">姓名：</td>
							<td style="width:135px;"><input class="eve-input"
							type="text" name="aac003" /></td>
							<td style="width:120px;text-align:right;">险种类型：</td>
							<td style="width:135px;">
								<input class="easyui-combobox" name="aae140"
	                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=TypeOfInsurance',
	                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
	                                panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="search()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('selectDdPersonsForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectDdPersonsGrid" fitColumns="false" toolbar="#selectDdPersonsToolbar"
				method="post"  checkOnSelect="true" selectOnCheck="true" 
				fit="true" border="false" nowrap="false"
				url="ybCxjmcbxbController.do?ddPersonsDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'aac002',align:'center'" width="10%">证件号码</th>
						<th data-options="field:'aac003',align:'center'" width="10%">姓名</th>
						<th data-options="field:'bac503',align:'center'" width="10%">个人保险号</th>
						<th data-options="field:'aac007',align:'center'" width="10%">参加工作日期</th>
						<th data-options="field:'bac506',align:'center'" width="10%">连续工龄</th>
						<th data-options="field:'aae140',align:'center',formatter:xzlxformater" width="10%">险种类型</th>
						<th data-options="field:'aac031',align:'center',formatter:cbztformater" width="10%">参保状态</th>
						<th data-options="field:'aab999',align:'center'" width="10%">单位保险号</th>
						<th data-options="field:'aab004',align:'center'" width="10%">单位名称</th>
						<th data-options="field:'aab001',align:'center'" width="10%">单位编号</th>
					</tr>
				</thead>
			</table>	
		</div>		
		<div data-options="region:'south',border:true" style="height:46px" >
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

