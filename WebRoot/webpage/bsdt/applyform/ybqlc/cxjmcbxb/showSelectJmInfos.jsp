<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/cxjmcbxb/js/cxjmcbxb.js"></script>
<script type="text/javascript">

    $(function(){	
		//空数据，横向滚动
		$('#selectJmInfosGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});
    });

	//查询操作
	function search(){
		var count = 0;
		AppUtil.gridDoSearch('selectJmInfosToolbar','selectJmInfosGrid');
		$('#selectJmInfosGrid').datagrid({
			onLoadSuccess:function(){
				//确保初始化后只执行一次
				if(count == 0){
					var rows = $("#selectJmInfosGrid").datagrid("getRows");
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
	
	//确定
	function doSelectRows(){
		var rows = $("#selectJmInfosGrid").datagrid("getSelections");
    	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			art.dialog.data("jmInfos", rows);// 存储数据
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
	var cbsfObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredIdentity'}
	});
	function cbsfformat(value,row,index){
		var json = cbsfObj.responseText;
		return dataFormat(value,json);
	}	
	//性别格式化
	var xbObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybSex'}
	});
	function xbformat(value,row,index){
		var json = xbObj.responseText;
		return dataFormat(value,json);
	}
	//民族格式化
	var mzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybNation'}
	});
	function mzformat(value,row,index){
		var json = mzObj.responseText;
		return dataFormat(value,json);
	}	
</script>
</head>
<body class="eui-diabody" style="margin:0px;" >
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="selectJmInfosToolbar">
				<form action="#" name="selectJmInfosForm">
					<!--====================开始编写隐藏域============== -->
					
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">社区名称：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="bab506" /></td>
							<td style="width:120px;text-align:right;">社区编号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="bab505" /></td>
							<td style="width:120px;text-align:right;">个人保险号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="bac503" /></td>	
						</tr>
						<tr style="height:28px;">
						    <td style="width:120px;text-align:right;">姓名：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" name="aac003" /></td>
							<td style="width:120px;text-align:right;">参保身份：</td>
							<td colspan="2" style="width:135px;">
								<input class="easyui-combobox"
	                                style="width:128px;" name="aac066"
	                                data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=insuredIdentity',
	                                editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
	                                panelWidth: 128,panelHeight: 'auto' " />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">证件号码：</td>
							<td colspan="5"  style="width:135px;">
								<input class="eve-input" style="width:90%;" type="text" name="aac002s" />
								<br/>
								<font class="tab_color">支持批量查询多个身份证号码，请以符号"/"隔开！</font>
							</td>
						</tr>
						<tr style="height:28px;">
							<td colspan="6" style="text-align:center"><input type="button" value="查询"
								class="eve-button" onclick="search()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('selectJmInfosForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="selectJmInfosGrid" fitColumns="false" toolbar="#selectJmInfosToolbar"
				method="post" checkOnSelect="true" selectOnCheck="true" 
				fit="true" border="false" nowrap="false"
				url="ybCxjmcbxbController.do?jmInfosDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'bab507',align:'center',hidden:true">社区ID</th>
						<th data-options="field:'aac001',align:'center',hidden:true">个人编号</th>	
						<th data-options="field:'bab506',align:'center'" width="10%">社区名称</th>
						<th data-options="field:'bab505',align:'center'" width="10%">社区编号</th>
						<th data-options="field:'bac503',align:'center'" width="10%">个人保险号</th>
						<th data-options="field:'aac003',align:'center'" width="10%">姓名</th>
						<th data-options="field:'aac002',align:'center'" width="10%">证件号码</th>
						<th data-options="field:'aae030',align:'center'" width="10%">参保开始日期</th>
						<th data-options="field:'aac066',align:'center',formatter:cbsfformat" width="10%">参保身份</th>
						<th data-options="field:'aac004',align:'center',formatter:xbformat" width="10%">性别</th>
						<th data-options="field:'aac005',align:'center',formatter:mzformat" width="10%">民族</th>
						<th data-options="field:'aac006',align:'center'" width="10%">出生日期</th>
						<th data-options="field:'aae005',align:'center'" width="10%">联系电话</th>
						<th data-options="field:'bae528',align:'center'" width="10%">手机号</th>
						<th data-options="field:'aae007',align:'center'" width="10%">邮编</th>
						<th data-options="field:'aae006',align:'center'" width="22%">地址</th>
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

