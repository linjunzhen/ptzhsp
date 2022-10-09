<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/cxjmcbxb/js/cxjmcbxb.js"></script>
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
		}	
		art.dialog.data("dwInfos", rows);	
		AppUtil.closeLayer();
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
	//有效标识格式化
	var yxbsObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'effectiveSign'}
	});
	function yxbsformater(value,row,index){
		var json = yxbsObj.responseText;
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
	
	
	//居民单位类别格式化
	var dwlbObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'UnitTypeOfJm'}
	});
	function dwlbformater(value,row,index){
		var json = dwlbObj.responseText;
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
	
	
	//隶属关系格式化
	var lsgxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'Affiliation'}
	});
	function lsgxformater(value,row,index){
		var json = lsgxObj.responseText;
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
	
	//所属地区格式化
	var ssdqObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'administrative'}
	});
	function ssdqformater(value,row,index){
		var json = ssdqObj.responseText;
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
	
	
	//居民单位人员清册查询
	function doSelectPerson(){
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
			var departNo = rows[0].DWBH;//单位编号
			$.dialog.open("ybCxjmcbxbController.do?ybSelectPersonInfos&departNo="+departNo, {
		        title : "居民单位花名册查询",
		        width: "90%",
		        height: "90%",
		        fixed: true,
		        lock : true,
		        resize : false
		    }, false);
		}			
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
								<td style="width:120px;text-align:right;">居民单位编号：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text"  style="width:135px;"name="bab505" /></td>
								<td style="width:120px;text-align:right;">居民单位名称：</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" style="width:135px;" name="bab506" /></td>
								<td style="width:120px;text-align:right;">居民单位类别：</td>
								<td style="width:135px;">
									<input class="easyui-combobox"
		                                style="width:128px;" name="bab508"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=UnitTypeOfJm',
		                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
		                                panelWidth: 128,panelHeight: 'auto' " />
								</td>
							</tr>
							<tr style="height:28px;">
								<td style="width:120px;text-align:right;">有效标识：</td>
								<td style="width:135px;">
									<input class="easyui-combobox"
		                                style="width:128px;" name="aae100"
		                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=effectiveSign',
		                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
		                                panelWidth: 128,panelHeight: 'auto' " />
								</td>
								<td style="width:120px;text-align:right;"><font class="tab_color ">*</font>分中心：</td>
								<td style="width:135px;">
									<input class="easyui-combobox" id="fzc"
		                                style="width:128px;" name="orgcode"
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
				method="post" checkOnSelect="true" selectOnCheck="true" 
				fit="true" border="false" nowrap="false"
				url="ybCxjmcbxbController.do?unitInfosDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
					    <th data-options="field:'bab507',hidden:true">居民单位ID</th>
					    <th data-options="field:'bab505',align:'center'" width="20%">居民单位编号</th>
						<th data-options="field:'bab506',align:'center'" width="20%">居民单位名称</th>
						<th data-options="field:'bab508',align:'center',formatter:dwlbformater" width="15%">居民单位类别</th>
						<th data-options="field:'orgcode',align:'center',formatter:fzxformater" width="15%">分中心</th>
						<th data-options="field:'aab021',align:'center',formatter:lsgxformater" width="10%">隶属关系</th>
						<th data-options="field:'aae100',align:'center',formatter:yxbsformater" width="10%">有效标识</th>
						<th data-options="field:'aab301',align:'center',formatter:ssdqformater" width="10%">所属地区</th>					  	  
						<!-- 
						<th data-options="field:'DWRS',align:'left'" width="10%">单位人数</th>
						<th data-options="field:'DWBH',align:'left'" width="10%">居民单位编号</th>
						<th data-options="field:'DWMC',align:'left'" width="10%">居民单位名称</th>
						<th data-options="field:'DWLB',align:'left',formatter:dwlbformater" width="10%">居民单位类别</th>
						<th data-options="field:'SQLB',align:'left'" width="10%">社区类别</th>
						<th data-options="field:'FRXM',align:'left'" width="10%">法人姓名</th>
						<th data-options="field:'LXRXM',align:'left'" width="10%">联系人姓名</th>
						<th data-options="field:'LXDH',align:'left'" width="10%">联系电话</th>
						<th data-options="field:'DZ',align:'left'" width="10%">地址</th>
						<th data-options="field:'YZBM',align:'left'" width="10%">邮政编码</th> -->
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
				<!-- <input value="人员清册查询" type="button" onclick="doSelectPerson();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> -->
			</div>
		</div>
	</div>	
</body>

