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
			$('#departGrid').datagrid({
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
		    AppUtil.gridDoSearch('departToolbar','departGrid');
			$('#departGrid').datagrid({
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
	
	
	
		//单位类型格式化
		var htmlObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'TypeOfUnit'}
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
		
		//是与否格式化
		var yesNoObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'YESNO'}
		});
		function formatYesNo(value){
			var json = yesNoObj.responseText;
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
		
		//单位花名册查询
		function rosterQuery(){
			var rows = $("#departGrid").datagrid("getChecked");
	    	var entityIds = "";
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
				var departNo = rows[0].aab001;//单位编号
				$.dialog.open("ybLhjyController.do?rosterQuery&departNo="+departNo, {
			        title : "单位花名册查询",
			        width: "90%",
			        height: "90%",
			        fixed: true,
			        lock : true,
			        resize : false
			    }, false);
			}
		}
		
		//确定
		function doSelect(){
			var rows = $("#departGrid").datagrid("getChecked");
	    	var entityIds = "";
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
				art.dialog.data("selectDepart", {
					DWDAH : rows[0].aab511,//单位档案号
					DWBXH : rows[0].aab999,//单位保险号
					DWMC : rows[0].aab004,//单位名称
					DWBH : rows[0].aab001,//单位编号
				});// 存储数据
				AppUtil.closeLayer();
			}
		}
	</script>
</head>

<body class="eui-diabody" style="margin:0px;">

	<div class="easyui-layout" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="departToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="cbrForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:68px;text-align:right;">单位保险号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab999" /></td>
								<td style="width:68px;text-align:right;">单位名称：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab004" /></td>
								<td style="width:68px;text-align:right;">单位档案号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="bab511" /></td>
								<td style="width:68px;text-align:right;">单位类型：</td>
								<td style="width:135px;">
									<input class="easyui-combobox"
									style="width:180px;" name="aab019"
									data-options="
		url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=TypeOfUnit',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 180,panelHeight: '300' " />
								</td>
							</tr>
							<tr style="height:28px;">
								<td style="width:68px;text-align:right;">单位编号：</td>
								<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:180px;" name="aab001" />
								</td>
								<td style="width:68px;text-align:right;">分中心：</td>
								<td style="width:135px;">
									<eve:eveselect clazz="tab_text validate[]" dataParams="subCenter"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="350128"
										defaultEmptyText="请选择" name="orgcode">
									</eve:eveselect>
								</td>
								<td colspan="4">
									<input type="button" value="查询"
									class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('departForm')" /></td>
								</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table  rownumbers="true" pagination="false"
				id="departGrid" fitColumns="true" toolbar="#departToolbar"
				method="post"  checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybLhjyController.do?departQueryData">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'verified',align:'center',formatter:formatYesNo" width="5%">核对标志</th>
	                    <th data-options="field:'aae516',align:'center'" width="5%">单位人数</th>
	                    <th data-options="field:'aab002',align:'center'" width="10%">社保登记证编码</th>
	                    <th data-options="field:'aab999',align:'center'" width="10%">单位保险号</th>
	                    <th data-options="field:'tbrqi0',align:'center'" width="10%">参保日期</th>
	                    <th data-options="field:'aab004',align:'center'" width="20%">单位名称</th>
	                    <th data-options="field:'aab511',align:'center'" width="10%">单位档案号</th>
	                    <th data-options="field:'aab019',align:'center',formatter:rowformater" width="10%">单位类型</th>
	                    <th data-options="field:'aab001',align:'center'" width="10%">单位编号</th>
	                    <th data-options="field:'aab030',align:'center'" width="12%">税号</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons" style="text-align: right;">
				<input value="确认" type="button" onclick="doSelect()"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />				
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
				<input value="单位花名册查询" type="button" onclick="rosterQuery();"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
	</div>

	
</body>
</html>
