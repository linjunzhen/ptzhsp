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
		//核对标志格式化
		var yesNoObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'YESNO'}
		});
		function formatYesNo(value){
			var json = yesNoObj.responseText;
			return dataFormat(value,json);
		}
		//隶属关系格式化
		var affiliationObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'Affiliation'}
		});
		function formatAffiliation(value){
			var json = affiliationObj.responseText;
			return dataFormat(value,json);
		}
		//分中心格式化
		var subCenterObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'subCenter'}
		});
		function formatSubCenter(value){
			var json = subCenterObj.responseText;
			return dataFormat(value,json);
		}
		//单位类型格式化
		var unitTypeObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'TypeOfUnit'}
		});
		function formatUnitType(value){
			var json = unitTypeObj.responseText;
			return dataFormat(value,json);
		}
		//单位类别格式化
		var unitCategoryObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'DWLB'}
		});

		function formatUnitCategory(value){
			var json = unitCategoryObj.responseText;
			return dataFormat(value,json);
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
					DWRS: rows[0].aae516//单位人数
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
								<td style="width:135px;">
									<input class="eve-input" type="text" style="width:186px;" name="aab999" />
								</td>
								<td style="width:68px;text-align:right;">单位名称：</td>
								<td style="width:135px;">
									<input class="eve-input" type="text" style="width:186px;" name="aab004" />
								</td>
								<td style="width:68px;text-align:right;">单位档案号：</td>
								<td style="width:135px;">
									<input class="eve-input" type="text" style="width:186px;" name="bab511"/>
								</td>
								<td style="width:68px;text-align:right;">税号：</td>
								<td style="width:135px;">
									<input class="eve-input" type="text" style="width:186px;" name="aab030" />
								</td>
							</tr>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">核对标志：</td>
								<td style="width:135px;">
									<eve:eveselect clazz="tab_text validate[required]" dataParams="YESNO"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="选择核对标志" name="verified">
									</eve:eveselect>
								</td>
								<td style="width:120px;text-align:right;">分中心：</td>
								<td style="width:135px;">
									<eve:eveselect clazz="tab_text validate[required]" dataParams="subCenter"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="选择分中心" name="orgcode">
									</eve:eveselect>
								</td>
								<td colspan="2">
									<input type="button" value="查询" class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
										onclick="AppUtil.gridSearchReset('cbrForm');" />
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="departGrid" fitColumns="true" toolbar="#departToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybLhjyController.do?xbDepartQueryData">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'verified',align:'center',formatter:formatYesNo" width="5%">核对标志</th>
	                    <th data-options="field:'aae516',align:'center'" width="5%">单位人数</th>
	                    <th data-options="field:'aab002',align:'center'" width="10%">社保登记证编码</th>
	                    <th data-options="field:'aab999',align:'center'" width="10%">单位保险号</th>
	                    <th data-options="field:'tbrqi0',align:'center'" width="10%">参保日期</th> 
	                    <th data-options="field:'aab004',align:'center'" width="15%">单位名称</th>
	                    <th data-options="field:'aab511',align:'center'" width="15%">单位档案号</th>
	                    <th data-options="field:'aab019',align:'center',formatter:formatUnitType" width="10%">单位类型</th>
	                    <th data-options="field:'aab001',align:'center'" width="10%">单位编号</th>
	                    <th data-options="field:'aab030',align:'center'" width="10%">税号</th>
	                    <th data-options="field:'aab021',align:'center',formatter:formatAffiliation" width="5%">隶属关系</th>
						<th data-options="field:'aab020',align:'center',formatter:formatUnitCategory" width="8%">单位类别</th>
						<th data-options="field:'orgcode',align:'center',formatter:formatSubCenter" width="8%" >分中心</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons" style="text-align: right;">
				<input value="确认" type="button" onclick="doSelect();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
