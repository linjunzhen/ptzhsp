<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript">
	
	//确定操作
	function doSelectRows(){
		var rows = $("#selectDjInfosGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if(rows.length>allowCount){
			art.dialog({
				content: "最多只能选择"+allowCount+"条记录!",
				icon:"warning",
			    ok: true
			});
			return;
		}else if(rows.length < allowCount){
			art.dialog({
				content: "至少选择一条记录进行操作！",
				icon:"warning",
			    ok: true
			});
			return;
		}	
		art.dialog.data("djInfos", rows);	
		AppUtil.closeLayer();
	}
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('selectDjInfosToolbar','selectDjInfosGrid')
		$('#selectDjInfosGrid').datagrid({
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
	//工作状态格式化
	var gzztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredIdentity'}
	});
	function gzztformater(value,row,index){
		var json = gzztObj.responseText;
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
	//性别格式化
	var sexObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybSex'}
	});
	function sexformater(value,row,index){
		var json = sexObj.responseText;
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
	//证件类型格式化
	var idcardObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'idCardType'}
	});
	function zjlxformater(value,row,index){
		var json = idcardObj.responseText;
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
	//民族格式化
	var mzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybNation'}
	});
	function mzformater(value,row,index){
		var json = mzObj.responseText;
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
			<div id="selectDjInfosToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="selectDjInfosForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">个人编号：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aac001" /></td>
								<td style="width:120px;text-align:right;">个人保险号：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="bac503" /></td>
								<td style="width:120px;text-align:right;">姓名：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aac003" /></td>
							</tr>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">单位编号：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aab001" /></td>
								<td style="width:120px;text-align:right;">单位名称：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aab004" /></td>
								<td style="width:120px;text-align:right;">公民身份证号码：</td>
								<td><input class="eve-input"
									type="text"  style="width:200px;"
									name="aac002" /></td>
							</tr>
							<tr style="height:28px;">
								<td colspan="6" style="text-align:center"><input type="button" value="查询"
									class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('selectDjInfosForm');" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectDjInfosGrid" fitColumns="false" toolbar="#selectDjInfosToolbar"
				method="post"  checkOnSelect="true" selectOnCheck="true" 
				fit="true" border="false" nowrap="false"
				url="ybGwybzshController.do?djInfosDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'aac001',hidden:true">个人编号</th>
						<th data-options="field:'aab001',hidden:true">单位编号</th>
						<th data-options="field:'aab999',hidden:true">单位保险号</th>
						<th data-options="field:'aae140',hidden:true">险种</th>
						<th data-options="field:'orgcode',hidden:true,formatter:fzxformater">分中心</th>
						<th data-options="field:'aac066',hidden:true,formatter:gzztformater">工作状态</th>						
						<th data-options="field:'bac503',align:'left'" width="15%">个人保险号</th>
						<th data-options="field:'aab004',align:'left'" width="15%">单位名称</th>
						<th data-options="field:'aac003',align:'left'" width="15%">姓名</th>
						<th data-options="field:'aac058',align:'left',formatter:zjlxformater" width="15%">身份证件类型</th>
						<th data-options="field:'aac002',align:'left'" width="15%">公民身份证件号码</th>
						<th data-options="field:'aac004',align:'left',formatter:sexformater" width="10%">性别</th>
						<th data-options="field:'aac005',align:'left',formatter:mzformater" width="10%">民族</th>
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

