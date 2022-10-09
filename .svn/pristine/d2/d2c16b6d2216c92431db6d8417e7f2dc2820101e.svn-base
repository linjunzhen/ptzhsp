<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>

<script type="text/javascript">
	$(function (){
		//空数据，横向滚动
		$('#selectPersonInfosGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});
	});
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('selectPersonInfosToolbar','selectPersonInfosGrid')
		$('#selectPersonInfosGrid').datagrid({
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
	//参保身份格式化
	var cbsfObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredIdentity'}
	});
	function cbsfformater(value,row,index){
		var json = cbsfObj.responseText;
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

<body class="eui-diabody" style="margin:0px;" > 
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="selectPersonInfosToolbar">
				<form action="#" name="selectPersonInfosForm">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">个人保险号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:135px;"
								name="bdcdyh" /></td>
							<td style="width:120px;text-align:right;">姓名：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:135px;"
								name="bdcdjzmh" /></td>
							<td style="width:120px;text-align:right;">身份证件类型：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
	                                style="width:128px;" name="sfType"
	                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=idCardType',
	                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
	                                panelWidth: 128,panelHeight: 'auto' " />
							</td>						
						</tr>
						<tr style="height:28px;">
						    <td style="width:120px;text-align:right;">证件号码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:135px;"
								name="zjhm" /></td>
							<td style="width:120px;text-align:right;">参保身份：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
	                                style="width:135px;" name="cbsf"
	                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=insuredIdentity',
	                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
	                                panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="search();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('selectPersonInfosForm');" /></td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectPersonInfosGrid" fitColumns="true" toolbar="#selectPersonInfosToolbar"
				method="post" checkOnSelect="true" selectOnCheck="false" 
				fit="true" border="false" nowrap="false"
				url="ybCxjmcbxbController.do?personInfosDatagrid&DWBH=${departNo}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<!-- <th data-options="field:'YWH_ID',hidden:true">业务号</th> -->
						<th data-options="field:'DWMC',align:'left'" width="10%">单位名称</th>
						<th data-options="field:'DWBH',align:'left'" width="10%">单位编号</th>
						<th data-options="field:'GRBXH',align:'left'" width="10%">个人保险号</th>
						<th data-options="field:'XM',align:'left'" width="10%">姓名</th>
						<th data-options="field:'ZJLX',align:'left',formatter:zjlxformater" width="10%">身份证件类型</th>
						<th data-options="field:'ZJHM',align:'left'" width="10%">证件号码</th>
						<th data-options="field:'CBKSRQ',align:'left'" width="10%">参保开始日期</th>
						<th data-options="field:'CBSF',align:'left',formatter:cbsfformater" width="10%">参保身份</th>
						<th data-options="field:'XB',align:'left',formatter:sexformater" width="10%">性别</th>
						<th data-options="field:'MZ',align:'left',formatter:mzformater" width="10%">民族</th>
						<th data-options="field:'PHONE',align:'left'" width="10%">联系电话</th>
						<th data-options="field:'MOBILE',align:'left'" width="10%">手机号</th>
						<th data-options="field:'YB',align:'left'" width="10%">邮编</th>
						<th data-options="field:'ADDRESS',align:'left'" width="10%">地址</th>
						<th data-options="field:'FZX',align:'left',formatter:fzxformater" width="10%">分中心</th>
					</tr>
				</thead>
			</table>	
		</div>
	</div>	
</body>

