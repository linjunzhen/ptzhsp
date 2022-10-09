<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,json2"></eve:resources>
	<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
	<script type="text/javascript">	
		$(function (){		
			//空数据，横向滚动
			$('#rosterGrid').datagrid({
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
		    AppUtil.gridDoSearch('rosterToolbar','rosterGrid');
			$('#rosterGrid').datagrid({
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
	
	
		//参保身份格式化
		var htmlObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'insuredIdentity'}
		});
		function rowformater(value,row,index){
			var json = htmlObj.responseText;
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
		//民族格式化
		var nationObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'ybNation'}
		});
		function nationformater(value,row,index){
			var json = nationObj.responseText;
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
		function idcardformater(value,row,index){
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
	</script>
</head>

<body class="eui-diabody" style="margin:0px;">

	<div class="easyui-layout" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="rosterToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="cbrForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:78px;text-align:right;">单位保险号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab999" /></td>
								<td style="width:78px;text-align:right;">单位名称：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab004" /></td>
								<td style="width:78px;text-align:right;">姓名：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aac003" /></td>									
								<td style="width:78px;text-align:right;">参保身份：</td>
								<td style="width:135px;">
								<input class="easyui-combobox"
									style="width:180px;" name="aac066"
									data-options="
		url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=insuredIdentity',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 180,panelHeight: '300' " />
								</td>
							</tr>
							<tr style="height:28px;">									
								<td style="width:78px;text-align:right;">证件类型：</td>
								<td style="width:135px;">
								<input class="easyui-combobox"
									style="width:180px;" name="aac058"
									data-options="
		url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=idCardType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 180,panelHeight: 'auto' " />
								</td>
								<td style="width:78px;text-align:right;">公民身份号码：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aac002" />
								</td>
								<td colspan="2">
									<input type="button" value="查询"
									class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('rosterForm')" /></td>
								</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table rownumbers="true" pagination="false"
				id="rosterGrid" fitColumns="true" toolbar="#rosterToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybLhjyController.do?rosterQueryData&aab001=${departNo}">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'aab999',align:'left'" width="10%">单位保险号</th>
	                    <th data-options="field:'aab004',align:'left'" width="20%">单位名称</th>
	                    <th data-options="field:'aac003',align:'left'" width="6%">姓名</th>
	                    <th data-options="field:'aac058',align:'left',formatter:idcardformater" width="15%">身份证件类型</th>
	                    <th data-options="field:'aac002',align:'left'" width="15%">公民身份号码</th>
	                    <th data-options="field:'aac066',align:'left',formatter:rowformater" width="15%">参保身份</th>
	                    <th data-options="field:'aac004',align:'left',formatter:sexformater" width="5%">性别</th>
	                    <th data-options="field:'aac005',align:'left',formatter:nationformater" width="10%">民族</th>
					</tr>
				</thead>
			</table>	
		</div>
		
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons" style="text-align: right;">
				<!-- <input value="确认" type="button" onclick=""
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> -->
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
