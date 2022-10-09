<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#enterpriseGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		if(rows.length>0){  
			art.dialog.data("enterpriseInfo", rows);
			AppUtil.closeLayer();  
		}else{
			AppUtil.closeLayer();
		}    	
    	
    }
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#enterpriseGrid');  
		var options = grid.datagrid('getPager').data("pagination").options;
		var maxnum = options.pageNumber*options.pageSize;
		var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ maxnum + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '12px, Microsoft YaHei');
        var width = currentObj.width();
		var panel = grid.datagrid('getPanel');
        if(width>25){
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width+5);
			grid.datagrid("resize");
		}else{			
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
			grid.datagrid("resize");
		}
	}
	$('#enterpriseGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	function enterpriseSearch(){
		var validateResult =$("#enterpriseForm").validationEngine("validate");
		if(!validateResult){
			return false;
		}
		var count = 0;
		AppUtil.gridDoSearch('enterpriseToolbar','enterpriseGrid');
		$('#enterpriseGrid').datagrid({
			onLoadSuccess:function(){
				//确保初始化后只执行一次
				if(count == 0){
					var rows = $("#enterpriseGrid").datagrid("getRows");
					if(rows.length==0){
						parent.art.dialog({
							content: "无匹配数据返回，查询记录为空！",
							icon:"warning",
							ok: true
						});
						count ++;
					}
				}
				$('#enterpriseGrid').datagrid('clearSelections');
			}
		});
	}
	
	$(function(){
		//初始化验证引擎的配置
		$("#qyname").addClass("validate[required]");
		$("#enterpriseForm").validationEngine({
			promptPosition:"bottomLeft"
		});
	});
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
    <input type="hidden" name="noLike" value="${noLike}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="enterpriseToolbar">
				<form action="#" name="enterpriseForm" id="enterpriseForm">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr>
							<td style="width:120px;text-align:right;">企业名称：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="qyname" id="qyname"/></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button "
								onclick="enterpriseSearch()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('enterpriseForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="enterpriseGrid" fitColumns="true" toolbar="#enterpriseToolbar"
				method="post" idField="socialcreditcode" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="projectSgxkController.do?enterpriseDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'name',align:'left'" width="150">企业名称</th>
						<th data-options="field:'socialcreditcode',align:'left'" width="100">统一社会信用代码</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		
		<div data-options="region:'south',split:true,border:false"  >
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

