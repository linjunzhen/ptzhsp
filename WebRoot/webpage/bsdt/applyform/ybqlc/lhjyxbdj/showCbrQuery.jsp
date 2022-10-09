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
			$('#cbrGrid').datagrid({
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
		    var zjhm = $("input[name='aac002']").val();
		    if(zjhm == null || zjhm == "" || zjhm == "undefined"){
		    	alert("请输入证件号码，模糊查询用\"*\"号代替！");
		    }else{
		    	 AppUtil.gridDoSearch('cbrToolbar','cbrGrid');
				$('#cbrGrid').datagrid({
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
		}
		
		//确定
		function doSelect(){
			var rows = $("#cbrGrid").datagrid("getSelections");
	    	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
				art.dialog({
					content: "请选择需要被操作的记录!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				art.dialog.data("cbrInfos", rows);// 存储数据
				AppUtil.closeLayer();
			}
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
		//参保身份格式化
		var htmlObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:' insuredIdentity'}
		});
		function cbsfformat(value,row,index){
			var json = htmlObj.responseText;
			return dataFormat(value,json);
		}
		//参保状态格式化
		var cbztObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'insuredState'}
		});
		function cbztformat(value,row,index){
			var json = cbztObj.responseText;
			return dataFormat(value,json);
		}
		//证件类型格式化
		var zjlxObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'idCardType'}
		});
		function zjlxformat(value,row,index){
			var json = zjlxObj.responseText;
			return dataFormat(value,json);
		}
		//性别格式化
		var sexObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'ybSex'}
		});
		function sexformat(value,row,index){
			var json = sexObj.responseText;
			return dataFormat(value,json);
		}
		//单位类型格式化
		var dwlxObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'TypeOfUnit'}
		});
		function dwlxformat(value,row,index){
			var json = dwlxObj.responseText;
			return dataFormat(value,json);
		}
		//特殊单位类别格式化
		var tsdwlbObj = $.ajax({
	        method:'post',
	        url:'dictionaryController.do?load',
	        async:false,
	        dataType:'json',
	        data:{typeCode:'UnitCategory'}
		});
		function tsdwlbformat(value,row,index){
			var json = tsdwlbObj.responseText;
			return dataFormat(value,json);
		}	
	</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div class="easyui-layout" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="cbrToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="cbrForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">单位保险号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab999" value="${DWBXH}"/>
								</td>
								<td style="width:120px;text-align:right;">单位名称：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab004" value="${DWMC }" /></td>
								<td style="width:120px;text-align:right;">单位编号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="aab001" value="${DWBH }" /></td>
								<td style="width:120px;text-align:right;">个人保险号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:180px;"
									name="bac503" />
								</td>
							</tr>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;"><font class="tab_color">*</font>证件号码：</td>
								<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:180px;" name="aac002" />
								</td>
					            <td style="width:120px;text-align:right;">姓名：</td>
								<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:180px;" name="aac003" />
								</td>
								<td style="width:120px;text-align:right;">参保身份：</td>
								<td style="width:135px;">
									<input class="easyui-combobox" style="width:128px;" name="aac066"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',
		                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
		                                panelWidth: 128,panelHeight: 'auto' " />
								</td>
								<td style="width:120px;text-align:right;">参保状态：</td>
								<td style="width:135px;">
									<input class="easyui-combobox" style="width:128px;" name="aac031"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',
		                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
		                                panelWidth: 128,panelHeight: 'auto' " />
								</td>
							</tr>		
							<tr style="height:28px;">
								<td colspan="5" ></td>
								<td colspan="3"  align="left">
									<input type="button" value="查询" class="eve-button"
									onclick="search();" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('cbrForm')" />
									<!-- <input type="button" value="查询勾选职工" class="eve-button"
										onclick="cxgxzgSearch()" />
									<input type="button" value="查询勾选居民" class="eve-button"
										onclick="cxgxjmSearch()" /> -->
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table rownumbers="true" pagination="false"
				id="cbrGrid" fitColumns="true" toolbar="#cbrToolbar"
				method="post" checkOnSelect="true" selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybLhjyController.do?cbrQueryData&aab999=${DWBXH}&aab004=${DWMC}&aab001=${DWBH}">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'aab999',align:'center'" width="10%">单位保险号</th>
	                    <th data-options="field:'aab004',align:'center'" width="15%">单位名称</th>
	                    <th data-options="field:'aab001',align:'center'" width="10%">单位编号</th>
	                    <th data-options="field:'bac503',align:'center'" width="15%">个人保险号</th>
	                    <th data-options="field:'aac002',align:'center'" width="15%">证件号码</th>
	                    <th data-options="field:'aac003',align:'center'" width="10%">姓名</th>
	                    <th data-options="field:'aac066',align:'center',formatter:cbsfformat" width="10%">参保身份</th>
	                    <th data-options="field:'aac031',align:'center',formatter:cbztformat" width="10%">参保状态</th>
	                    <th data-options="field:'aac058',align:'center',formatter:zjlxformat" width="15%">证件类型</th>
	                    <th data-options="field:'aac004',align:'center',formatter:sexformat" width="10%">性别</th>
	                    <th data-options="field:'bae528',align:'center'" width="10%">手机号码</th>
	                    <th data-options="field:'aae007',align:'center'" width="5%">邮政编码</th>
	                    <th data-options="field:'aac006',align:'center'" width="10%">通讯地址</th>
	                    <th data-options="field:'aac007',align:'center'" width="10%">参加工作日期</th>
	                    <th data-options="field:'bac506',align:'center'" width="10%">连续工龄</th>
	                    <th data-options="field:'aab019',align:'center',formatter:dwlxformat" width="10%">单位类型</th>
	                    <th data-options="field:'aab002',align:'center'" width="10%">社保编码</th>
	                    <th data-options="field:'aab065',align:'center',formatter:tsdwlbformat" width="10%">特殊单位类别</th>   
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
