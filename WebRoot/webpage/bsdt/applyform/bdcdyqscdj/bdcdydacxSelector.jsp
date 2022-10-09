<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#bdcdydacxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>=allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		art.dialog.data("bdcdydacxInfo", rows);
		AppUtil.closeLayer();
    	
    	
    }
	
	
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bdcdydacxGrid');  
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
	$('#bdcdydacxGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	function bdcdydacxSearch(){
	    var validateResult =$("#bdcdydacxForm").validationEngine("validate");
	    if(validateResult){
	        var count = 0;
	        AppUtil.gridDoSearch('bdcdydacxToolbar','bdcdydacxGrid')
	        $('#bdcdydacxGrid').datagrid({
	            onLoadSuccess:function(){
	                //确保初始化后只执行一次
	                if(count == 0){
	                    var rows = $("#bdcdydacxGrid").datagrid("getRows");
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
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="bdcdydacxToolbar">
				<form action="#" name="bdcdydacxForm" id="bdcdydacxForm">
				<!--====================开始编写隐藏域============== -->
			 	
				<!--====================结束编写隐藏域============== -->
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<c:if test="${isBank eq 'true'}">
					<tr>
					   <td colspan="7" class="dddl-legend"><div class="eui-dddltit"></div></td>
					</tr>
					</c:if>
					<tr style="height:28px;">
						<td style="width:135px;text-align:right;"><c:if test="${isBank eq 'true'}"><font class="tab_color ">*</font></c:if>不动产单元号：</td>
						<td style="width:140px;"><input class="eve-input validate[${required}]"
							type="text" maxlength="50" style="width:180px;" id="bdcdyh"
							name="bdcdyh" /></td>
						<td style="width:135px;text-align:right;">不动产登记证明号：</td>
						<td style="width:140px;"><input class="eve-input"
							type="text" maxlength="50" style="width:180px;" id="bdcdjzmh"
							name="bdcdjzmh" /></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="bdcdydacxSearch()" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('bdcdydacxForm');" /></td>
					</tr>
				</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="bdcdydacxGrid" fitColumns="true" toolbar="#bdcdydacxToolbar"
				method="post" idField="YWH" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcDyqscdjController.do?datagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'YWH',hidden:true" width="80">业务号</th>
						<th data-options="field:'BDCQZH',hidden:true" width="80">不动产权证号</th>
						<th data-options="field:'QSZT',align:'left'" width="60">权属状态</th>
						<th data-options="field:'BDCDYH',align:'left'" width="120">不动产单元号</th>
						<th data-options="field:'BDCDJZMH',align:'left'" width="120">不动产登记证明号</th>
						<th data-options="field:'DYQR',align:'left'" width="200">抵押权人</th>
						<th data-options="field:'ZWR',align:'left'" width="60">债务人</th>
						<th data-options="field:'DYR',align:'left'" width="60">抵押人</th>
						
						<th data-options="field:'BDBZZQSE',align:'left'" width="120">被担保主债权数额</th>
						<th data-options="field:'ZGZQSE',align:'left'" width="80">最高债权额</th>
						<th data-options="field:'DJSJ',align:'left'" width="80">登记时间</th>
						
						<th data-options="field:'ZH',align:'left'" width="80">幢号</th>
						<th data-options="field:'HH',align:'left'" width="80">户号</th>
						
					</tr>
				</thead>
			</table>
	
		</div>
	</div>

	
</body>

