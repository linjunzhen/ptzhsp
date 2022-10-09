<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript">
	$(function (){
			//分中心默认-平潭
	        $("#fzc").combobox('setValue',"350128");
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
		}else{
			//获取单位的险种信息
			 $.ajax({
				type: "POST",
		        url: "ybGwyssfwqrController.do?xzxxQueryDatagrid&aab001="+rows[0].aab001,
		        async: true, //采用同步方式进行数据判断
		        success: function (responseText) {
		            var data=responseText.rows;
		        	if(responseText.total>0){
		                art.dialog.data("xzxxInfos", data);//单位险种信息
		                var rows = $("#selectUnitInfosGrid").datagrid("getChecked");
	        			art.dialog.data("dwInfos", rows);//单位基本信息
						AppUtil.closeLayer();
		        	}else{
		        		parent.art.dialog({
    						lock: true,
    						content: "该单位暂查无对应的险种信息!",
    						icon:"warning",
    						ok: true,
    						time:5
    					});
    				   //return ;
    				   var rows = $("#selectUnitInfosGrid").datagrid("getChecked");
	        		   art.dialog.data("dwInfos", rows);	
					   AppUtil.closeLayer();
		        	}		
		        }
			});	 
		
		}	
		
	}
	
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('selectUnitInfosToolbar','selectUnitInfosGrid');
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
        data:{typeCode:'UnitCategory'}
	});
	function formatUnitCategory(value){
		var json = unitCategoryObj.responseText;
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
									type="text" maxlength="50" style="width:200px;"
									name="aab999" /></td>
								<td style="width:120px;text-align:right;">单位名称：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:200px;"
									name="aab004" /></td>
								<td style="width:120px;text-align:right;">单位档案号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:200px;"
									name="bab511" /></td>						
							</tr>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">税号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="50" style="width:200px;"
									name="aab030" /></td>
								<td style="width:120px;text-align:right;">核对标记：</td>
								<td style="width:135px;">
									<input class="easyui-combobox"
		                                style="width:200px;" name="verified"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YESNO',
		                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
		                                panelWidth: 120,panelHeight: 'auto' " />
								</td>
								<td style="width:120px;text-align:right;"><font class="tab_color ">*</font>分中心：</td>
								<td style="width:135px;">
									<input class="easyui-combobox" id="fzc"
		                                style="width:200px;" name="orgcode"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=subCenter',
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
				method="post" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybGwyssfwqrController.do?departQueryDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'aab002',align:'center'" width="10%">社保登记证号码</th>
						<th data-options="field:'aab999',align:'center'" width="10%">单位保险号</th>
						<th data-options="field:'tbrqi0',align:'center'" width="10%">参保日期</th>
						<th data-options="field:'aab004',align:'center'" width="10%">单位名称</th>
						<th data-options="field:'aab511',align:'center'" width="10%">单位档案号</th>
						<th data-options="field:'aab019',align:'center',formatter:formatUnitType" width="10%">单位类型</th>
						<th data-options="field:'aab001',align:'center'" width="10%">单位编号</th>
						<th data-options="field:'aab030',align:'center'" width="10%">税号</th>
						<th data-options="field:'aab021',align:'center',formatter:formatAffiliation" width="10%">隶属关系</th>
						<th data-options="field:'aab020',align:'center',formatter:formatUnitCategory" width="10%">单位类别</th>	
						<th data-options="field:'verified',align:'center',formatter:formatYesNo" width="10%">核对标记</th>
						<!-- <th data-options="field:'LXRXM',align:'center'" width="10%">审核标志</th> -->
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

