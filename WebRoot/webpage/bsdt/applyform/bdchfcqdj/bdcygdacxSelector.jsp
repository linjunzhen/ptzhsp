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
		//判断预告类型
		var flag=true;
		if("预售商品房抵押权预告登记" == rows[0].YGDJZL.trim() 
			|| "其它不动产抵押权预告登记" == rows[0].YGDJZL.trim()){
			/* $.ajax({
				type: "POST",
		        url: "bdcDyqscdjController.do?bdcygdacxDatagrid&bdcdyh="+rows[0].BDCDYH,
		        async: false, //采用同步方式进行数据判断
		        success: function (responseText) {
		            var data=responseText.rows;
		        	if(responseText.total>0){
		        		for(var i=0;i<data.length;i++){
		        			if("预售商品房买卖预告登记" == data[i].YGDJZL.trim() 
									|| "其它不动产买卖预告登记" == data[i].YGDJZL.trim()){	
								flag=true;
								break;	
		    				}else{
		    					flag = false;
		    				}
		        		}
		        		if(!flag){
		        			art.dialog({
	    						lock: true,
	    						content: "请注意,该不动产单元号预告类型不含'预售商品房买卖预告登记'或'其它不动产买卖预告登记'!",
	    						icon:"warning",
	    						ok: true
	    					});
	    				   return ;
		        		}else{
		        		   		art.dialog.data("bdcygdacxInfo", rows);
								AppUtil.closeLayer();
		        		}
		        	}else{
		        		art.dialog({
    						lock: true,
    						content: "暂查无对应的预告信息!",
    						icon:"warning",
    						ok: true
    					});
    				   return ;
		        	}		
		        }
			});	 */
			art.dialog.data("bdcygdacxInfo", rows);
			AppUtil.closeLayer();
		}else{
			parent.art.dialog({
				lock: true,
				content: "请选择预告类型为'预售商品房抵押权预告登记'或'其它不动产抵押权预告登记'的预告信息！",
				icon:"warning",
				ok: true
			});
			return ;
		}	
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
		var count = 0;
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
							<td style="width:120px;text-align:right;">不动产登记证明号：</td>
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

