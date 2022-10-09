<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#bdcdaxxcxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>=allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		var datas = [];
		var ydydyh = "";
		var unaccept = false;
		for(var i = 0;i<rows.length;i++){			
			var cqzt = rows[i].CQZT;
			if(Trim(cqzt).indexOf("查封")>-1 || Trim(cqzt).indexOf("预查封")>-1 ||Trim(cqzt).indexOf("限制")>-1 || Trim(cqzt).indexOf("无效")>-1
					|| Trim(cqzt).indexOf("查封办文中")>-1){
				parent.art.dialog({
					content: "请注意，该不动产单元号（"+rows[i].BDCDYH+"）状态为"+cqzt+"，不可办理抵押权登记！",
					icon:"warning",
					ok: true
				});
				unaccept = true;
				break;
			}else if(Trim(cqzt).indexOf("抵押")>-1){
			    ydydyh += "（"+rows[i].BDCDYH+"）";				
				datas.push(rows[i]);
			}else if(Trim(cqzt) == "权属登记" || Trim(cqzt) == "正常"){
				datas.push(rows[i]);
			} else {
				parent.art.dialog({
					content: "请注意，该不动产单元号（"+rows[i].BDCDYH+"）状态为"+cqzt+"，不可办理抵押权登记！",
					icon:"warning",
					ok: true
				});
				unaccept = true;
				break;
			}						
		}
		if(unaccept){
			return false;
		}
		if(ydydyh.length>0){
			parent.art.dialog.confirm("不动产单元号"+ydydyh+"已抵押!是否继续办理?",function() {
				art.dialog.data("bdcdaxxcxInfo", datas);
				AppUtil.closeLayer();
			});
		}else{
			art.dialog.data("bdcdaxxcxInfo", datas);
			AppUtil.closeLayer();
		}
	
    }
    
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bdcdaxxcxGrid');  
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
	$('#bdcdaxxcxGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	
	function bdcdaxxcxSearch(){
		var count = 0;
		AppUtil.gridDoSearch('bdcdaxxcxToolbar','bdcdaxxcxGrid');
		$('#bdcdaxxcxGrid').datagrid({
			onLoadSuccess:function(){
				//确保初始化后只执行一次
				if(count == 0){
					var rows = $("#bdcdaxxcxGrid").datagrid("getRows");
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
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="bdcdaxxcxToolbar">
				<form action="#" name="bdcdaxxcxForm">
					<!--====================开始编写隐藏域============== -->
					
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">不动产单元号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="bdcdyh" /></td>
							<td style="width:120px;text-align:right;">不动产权证号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="bdcqzh" /></td>
							<td style="width:120px;text-align:right;">权利人：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="qlrmc" /></td>
						</tr>
						<tr>
							<td style="width:120px;text-align:right;">证件号码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="zjhm" /></td>
							<td style="width:120px;text-align:right;">房屋编码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:128px;"
								name="fwbm" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="bdcdaxxcxSearch()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('bdcdaxxcxForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="bdcdaxxcxGrid" fitColumns="true" toolbar="#bdcdaxxcxToolbar"
				method="post" idField="YWH" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcDyqscdjController.do?bdcdaxxcxDatagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'YWH',hidden:true" width="80">业务号</th>
						<th data-options="field:'CQZT',align:'left'" width="80">产权状态</th>
						<th data-options="field:'ZH',align:'left'" width="50">幢号</th>
						<th data-options="field:'HH',align:'left'" width="50">户号</th>
						<th data-options="field:'BDCQZH',hidden:true" width="100">不动产权证号</th>
						<th data-options="field:'BDCDYH',align:'left'" width="100">不动产单元号</th>
						<th data-options="field:'QLRMC',align:'left'" width="100">权利人</th>
						<th data-options="field:'QSZT',align:'left'" width="80">权属状态</th>
						<th data-options="field:'FWBM',align:'left'" width="100">房屋编码</th>
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

