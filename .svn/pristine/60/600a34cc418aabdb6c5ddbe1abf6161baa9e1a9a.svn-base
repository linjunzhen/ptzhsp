<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript">
	
	//确定操作
	function doSelectRows(){
		var rows = $("#selectYdjInfosGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if(rows.length>allowCount){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}else if(rows.length < allowCount){
			alert("至少选择一条记录！");
			return;
		}	
		art.dialog.data("djInfos", rows);	
		AppUtil.closeLayer();
	}
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('selectYdjInfosToolbar','selectYdjInfosGrid')
		$('#selectYdjInfosGrid').datagrid({
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
	//是否有效格式化
	var sfObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'YESNO'}
	});
	function sfformater(value,row,index){
		var json = sfObj.responseText;
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
	
	//危病种类别
	var wbzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'WBZBM'}
	});
	function wbzlbformater(value,row,index){
		var json = wbzObj.responseText;
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
	
	//分中心格式化
	var fzxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'subCenter'}
	});
	function fzxformater(value,row,index){
		var json = fzxObj.responseText;
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
	
	//特殊人员类别格式化
	var rylbObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'TSRYLB'}
	});
	function tsrylbformater(value,row,index){
		var json = rylbObj.responseText;
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
<body class="eui-diabody" style="margin:0px;">
	<input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >	
		<div data-options="region:'center',split:false">
			<div id="selectYdjInfosToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="selectYdjInfosForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:40px;">
								<td style="width:120px;text-align:right;">个人保险号：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="bac503" /></td>
								<td style="width:120px;text-align:right;">证件号码：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aac002" /></td>
								<td style="width:120px;text-align:right">姓名：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aac003" />
								</td>
							</tr>
							<tr style="height:40px;">
								<td style="width:120px;text-align:right;">单位保险号：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aab999" /></td>
								<td style="width:120px;text-align:right;">单位名称：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aab004" /></td>
								<td colspan="2" style="text-align:center"><input type="button" value="查询"
									class="eve-button" onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('selectYdjInfosForm');" /></td>
							</tr>	
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectYdjInfosGrid" fitColumns="false" toolbar="#selectYdjInfosToolbar"
				method="post"  checkOnSelect="true" selectOnCheck="true" 
				fit="true" border="false" nowrap="false"
				url="ybGwybzshController.do?yDjDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'baz518',hidden:true">特殊人员类别ID</th>
						<th data-options="field:'baz522',hidden:true">公务员危重病ID</th>
						<th data-options="field:'aac001',hidden:true">个人编号</th>
						<th data-options="field:'aae100',align:'left',formatter:sfformater" width="10%">是否有效</th>
						<th data-options="field:'bac503',align:'left'" width="10%">个人保险号</th>
						<th data-options="field:'aac002',align:'left'" width="10%">证件号码</th>
						<th data-options="field:'aac003',align:'left'" width="10%">姓名</th>
						<th data-options="field:'aab999',align:'left'" width="10%">单位保险号</th>
						<th data-options="field:'aab004',align:'left'" width="10%">单位名称</th>
						<th data-options="field:'bac508',align:'left',formatter:tsrylbformater" width="10%">特殊人员类别</th>
						<th data-options="field:'aae012',align:'left',formatter:wbzlbformater" width="10%">危重病类别</th>
						<th data-options="field:'aae041',align:'left'" width="10%">起始年月</th>
						<th data-options="field:'aae042',align:'left'" width="10%">终止年月</th>
						<th data-options="field:'aae009',align:'left'" width="10%">银行户名</th>
						<th data-options="field:'aae010',align:'left'" width="10%">银行账号</th>
						<th data-options="field:'orgcode',align:'left',formatter:fzxformater" width="10%">分中心</th>
						<th data-options="field:'aac058',align:'left'" width="10%">备注</th>
						<th data-options="field:'aae011',align:'left'" width="10%">经办人</th>
						<th data-options="field:'jbrq',align:'left'" width="10%">经办日期</th>
						<th data-options="field:'aae014',align:'left'" width="10%">审核人</th>
						<th data-options="field:'shrq',align:'left'" width="10%">审核日期</th>
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

