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
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		var isAllClo = $("input[name='isAllClo']").val();
		if(isAllClo == 1){
	    	//表示接口全字段返回
			art.dialog.data("bdcdaxxcxInfo", rows);   
		}else{
			//默认返回以下几个字段
			var datas = [];
			for(var i = 0;i<rows.length;i++){	
			    datas.push({
			    	"POWERSOURCE_QLRMC":rows[i].QLRMC,
			    	"POWERSOURCE_CQZT":Trim(rows[i].CQZT),
			    	"POWERSOURCE_ZJLX":Trim(rows[i].ZJLX),
			    	"POWERSOURCE_ZJHM":Trim(rows[i].ZJHM),
			    	"FDZL":Trim(rows[i].FDZL),//房屋坐落
			    	"JZMJ":Trim(rows[i].JZMJ)//建筑面积
			    });		
			}
			art.dialog.data("bdcdaxxcxInfo", datas);
		}
		AppUtil.closeLayer();
    	
    	
    }
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	function searchReset() {
		$("input[name='Q_T.NEGATIVELIST_NAME_LIKE']").val("");
	}

	// function search() {
	// 	$("#bdcdaxxcxGrid").datagrid('clearChecked');
	// 	AppUtil.ajaxProgress({
	// 		url:"bdcGyjsjfwzydjController.do?checkBdcdyh",
	// 		params:{bdcdyh:$("#bdcdaxxcxToolbar [name='bdcdyh']").val()},
	// 		callback:function (data) {
	// 			if (data.success) {
	// 				art.dialog.confirm("确定?", function(){
	// 					AppUtil.gridDoSearch('bdcdaxxcxToolbar','bdcdaxxcxGrid')
	// 				})
	// 			}
	// 		}
	// 	})
	// }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
    <input type="hidden" name="isAllClo" value="${isAllClo}">
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
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="30" style="width:300px;"
								name="bdcdyh" /></td>
							<td style="width:120px;text-align:right;">不动产权证号：</td>
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="20" style="width:300px;"
								name="bdcqzh" /></td>
							<td style="width:120px;text-align:right;">权利人：</td>
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="20" style="width:300px;"
								name="qlrmc" /></td>
						</tr>
						<tr>
							<td style="width:120px;text-align:right;">证件号码：</td>
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="20" style="width:300px;"
								name="zjhm" /></td>
							<td style="width:120px;text-align:right;">房屋编码：</td>
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="20" style="width:300px;"
								name="fwbm" /></td>
<%--							<td colspan="2"><input type="button" value="查询"--%>
<%--								class="eve-button"--%>
<%--								onclick="AppUtil.gridDoSearchBdc('bdcdaxxcxToolbar','bdcdaxxcxGrid','bdcdyh');" />--%>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearchBdc('bdcdaxxcxToolbar','bdcdaxxcxGrid','bdcdyh');" />
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

