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
    	var rows = $("#bdcygdacxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		art.dialog.data("bdcygdacxInfo", rows);
		AppUtil.closeLayer();
    	
    }
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bdcygdacxGrid');  
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
	$('#bdcygdacxGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	function bdcygdacxSearch(){
		var noLike = $("input[name='noLike']").val();
		if(noLike == "1"){
			var validateResult =$("#bdcygdacxForm").validationEngine("validate");
			if(!validateResult){
				return false;
			}
		}
		var isKfsywsl = '${isKfsywsl}';
		var bdcdjzmh = $("input[name='bdcdjzmh']").val();
		if (isKfsywsl && isKfsywsl == '1') {
			if (!(bdcdjzmh && bdcdjzmh != "")) {
				art.dialog({
					content : "请填写不动产登记证明号。",
					icon : "warning",
					ok : true
				});
				return;
			}
		}
		var count = 0;
		$("#bdcygdacxGrid").datagrid('clearChecked');
		AppUtil.gridDoSearch('bdcygdacxToolbar','bdcygdacxGrid');
		$('#bdcygdacxGrid').datagrid({
			onLoadSuccess:function(){
				//确保初始化后只执行一次
				if(count == 0){
					var rows = $("#bdcygdacxGrid").datagrid("getRows");
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
	
	$(function(){
		var noLike = $("input[name='noLike']").val();
		if(noLike == "1"){
			//初始化验证引擎的配置
			$("#bdcdyh").addClass("validate[custom[estateNum]]");
			$("input[name='qlrzjh']").addClass("validate[custom[vidcard]]");
			$("input[name='ywrzjh']").addClass("validate[custom[vidcard]]");
			$("#bdcygdacxForm").validationEngine({
				promptPosition:"bottomLeft"
			});
		}
	});
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
    <input type="hidden" name="noLike" value="${noLike}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="bdcygdacxToolbar">
				<form action="#" name="bdcygdacxForm" id="bdcygdacxForm">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">不动产单元号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="bdcdyh" id="bdcdyh"/></td>
							<td style="width:120px;text-align:right;"><c:if test="${isKfsywsl == '1'}"><font class="tab_color">*</font></c:if>不动产登记证明号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="bdcdjzmh" /></td>
							<td style="width:120px;text-align:right;">权利人姓名：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="qlr" /></td>
						</tr>
						<tr>
							<td style="width:120px;text-align:right;">权利人证件号码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="qlrzjh" /></td>
							<td style="width:120px;text-align:right;">义务人：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="ywr" /></td>
							<td style="width:120px;text-align:right;">义务人证件号码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="ywrzjh" /></td>
						</tr>
						<tr>
							<td style="width:120px;text-align:right;">坐落：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="bdczl" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="bdcygdacxSearch()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('bdcygdacxForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="bdcygdacxGrid" fitColumns="true" toolbar="#bdcygdacxToolbar"
				method="post" idField="YWH" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcDyqscdjController.do?bdcygdacxDatagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'YWH',hidden:true" width="80">业务号</th>
						<c:if test="${noLike eq '1'}">
							<th data-options="field:'YGDJZL',align:'left'" width="100">预告登记种类</th>
						</c:if>
						<th data-options="field:'BDCDYH',align:'left'" width="150">不动产单元号</th>
						<th data-options="field:'BDCDJZMH',align:'left'" width="100">不动产登记证明号</th>
						<th data-options="field:'QSZT',align:'left'" width="80">权属状态</th>
						<th data-options="field:'YGDJZL',align:'left'" width="80">登记种类</th>
						<th data-options="field:'QLR',align:'left'" width="100">权利人</th>
						<th data-options="field:'QLRZJH',align:'left'" width="100">权利人证件号</th>
						<th data-options="field:'YWR',align:'left'" width="100">义务人</th>
						<th data-options="field:'YWRZJH',align:'left'" width="100">义务人证件号</th>
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

