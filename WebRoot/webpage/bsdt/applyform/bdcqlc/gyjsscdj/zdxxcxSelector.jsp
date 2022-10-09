<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

	//确定
    function doSelectRows(){
    	var rows = $("#zxxxcxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		art.dialog.data("zdxxcxInfo", rows);
		AppUtil.closeLayer();		
    }
    
    //字符串去空
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#zxxxcxGrid');  
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
	$('#zxxxcxGrid').datagrid({
		onLoadSuccess: fixRownumber
	});

	/**返回时间格式YYYY-MM-DD*/
	function DateConvertFun(str){
		var time = "";
		if(str){
			/*var result=str.match(/\d+/g);
            var year = result[0];

            var day = result[2];
            time = year +"-";
            if(result[1]){
                var month = result[1];
                if(parseInt(month) > 9){
                    time = time + month +"-";
                }else {
                    time = time + "0"+ month + "-";
                }
            }
            if(result[2]){
                var day = result[2];
                if(parseInt(day) > 9){
                    time = time + day;
                }else {
                    time = time + "0"+ day;
                }
            }*/
			time=str;
		}
		return time;
	}
	
	//查询
	function zxxxcxSearch(){
	    var validateResult =$("#zxxxcxForm").validationEngine("validate");
	    if(validateResult){
		    var count = 0;
	        AppUtil.gridDoSearch('zxxxcxToolbar','zxxxcxGrid');
	        $('#zxxxcxGrid').datagrid({
	            onLoadSuccess:function(){
	                //确保初始化后只执行一次
	                if(count == 0){
	                    var rows = $("#zxxxcxGrid").datagrid("getRows");
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
			<div id="zxxxcxToolbar">
				<form action="#" name="zxxxcxForm" id="zxxxcxForm">
					<!--====================开始编写隐藏域============== -->
					
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr>
	                       <td colspan="6" class="dddl-legend"><div class="eui-dddltit"></div></td>
	                    </tr>
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;"><font class="tab_color ">*</font>不动产单元号：</td>
							<td style="width:135px;"><input class="eve-input validate[required]" 
								type="text" maxlength="50" style="width:180px;" id="bdcdyh"
								name="bdcdyh" /></td>
							<td style="width:120px;text-align:right;">宗地代码：</td>
							<td style="width:135px;"><input class="eve-input validate[]"
								type="text" maxlength="50" style="width:180px;" id="zddm"
								name="zddm" /></td>
							
						</tr>
						<tr>
							<td style="width:120px;text-align:right;">宗地坐落：</td>
							<td style="width:135px;"><input class="eve-input validate[]"
								type="text" maxlength="50" style="width:180px;" id="zl"
								name="zl" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="zxxxcxSearch()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('zxxxcxForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="zxxxcxGrid" fitColumns="true" toolbar="#zxxxcxToolbar"
				method="post" idField="YWH" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcGyjsscdjController.do?zdxxcxDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'BDCDYH',align:'left'" width="100">不动产单元号</th>
						<th data-options="field:'ZDDM',align:'left'" width="100">宗地代码</th>
						<th data-options="field:'ZL',align:'left'" width="100">坐落</th>
						<th data-options="field:'ZDMJ',align:'left'" width="100">宗地面积</th>						
						<th data-options="field:'QLLX',align:'left'" width="100">权利类型</th>
						<th data-options="field:'QLXZ',align:'left'" width="100">权利性质</th>
						
						<th data-options="field:'QLSDFS',align:'left',hidden:true" width="100">权利设定方式</th>
						<th data-options="field:'GYGK',align:'left',hidden:true" width="100">共有情况</th>
						<th data-options="field:'TD_SZ_D',align:'left',hidden:true" width="100">土地四至_东</th>
						<th data-options="field:'TD_SZ_N',align:'left',hidden:true" width="100">土地四至_南</th>
						<th data-options="field:'TD_SZ_X',align:'left',hidden:true" width="100">土地四至_西</th>
						<th data-options="field:'TD_SZ_B',align:'left',hidden:true" width="100">土地四至_北</th>
						<th data-options="field:'TDDJ',align:'left',hidden:true" width="100">土地等级</th>
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

