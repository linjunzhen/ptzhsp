<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>

<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#SelectedBusScopeGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var ids = "";
			var busscopeNames = "";
			var induCodes = "";
			var induNames = "";
			var itemType="";
			var ybsxId="";
			var xksxId="";
			var ybsxName="";
			var xksxName="";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					induCodes+=",";
					induNames+=",";
				}
				induCodes+=rows[i].INDU_CODE;
				induNames+=rows[i].INDU_NAME;
				var id=rows[i].ID;
				var busscopeName=rows[i].BUSSCOPE_NAME;
				if(i==0){
					itemType=rows[i].ITEM_TYPE;
				}
				if("一般事项"==rows[i].ITEM_TYPE){
					ybsxId=ybsxId+id+",";
					ybsxName=ybsxName+busscopeName+",";

				}else{
					xksxId = xksxId + id+",";
					xksxName = xksxName + busscopeName+",";
				}
			}
			if(ybsxName!=''){
				ybsxName=ybsxName.substring(0,ybsxName.length-1); //去掉后面的逗号
				ybsxName="一般项目："+ybsxName+"（除依法须经批准的项目外，凭营业执照依法自主开展经营活动);  ";
			}
		    if(xksxName!=''){
		    	xksxName=xksxName.substring(0,xksxName.length-1);  //去掉后面的逗号
				xksxName="许可项目："+xksxName+"(依法须经批准的项目，经相关部门批准后方可开展经营活动，" +
						"具体经营项目以审批结果为准)" ;
			}
			if("一般事项"==itemType){
				busscopeNames=ybsxName+xksxName;
				ids=ybsxId+xksxId;
			}else{
				busscopeNames=xksxName+ybsxName;
				ids=xksxId+ybsxId;
			}
    		art.dialog.data("selectBusScopeInfo", {
    			ids:ids,
    			busscopeNames:busscopeNames,
    			induCodes:induCodes,
    			induNames:induNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }

	function colourformater(value,row,index){
	//一般事项  前置事项    后置事项
		if(row.ITEM_TYPE=='一般事项'){
			return "<font color='green'>"+value+"</font>";
		}else if(row.ITEM_TYPE=='前置事项'){
			return "<font color='red'>"+value+"</font>";
		}else if(row.ITEM_TYPE=='后置事项（我省）'){
			return "<font color='red'>"+value+"</font>";
		}else if(row.ITEM_TYPE=='后置事项'){
			return "<font color='orange'>"+value+"</font>";
		}
	}
	function doReSort(){
    	var rows = $("#SelectedBusScopeGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		$('#SelectedBusScopeGrid').datagrid('enableDnd');
    	}
	}
	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		$("#BusScopeGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedBusScopeGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedBusScopeGrid").datagrid("getRowIndex",row.ID);
				if(rowIndex==-1){
					$("#SelectedBusScopeGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedBusScopeGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedBusScopeGrid").datagrid("deleteRow",index);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="2500">
	<div class="easyui-layout eui-jh-box" fit="true" >
		
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="BusScopeToolbar">
				<!--====================开始编写隐藏域============== -->
			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;可选列表</img>
							   </div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="BusScopeForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">范围条目</td>
							<td style="width:68px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUSSCOPE_NAME_LIKE" /></td>
							<td style="width:38px;text-align:right;">代码</td>
							<td style="width:68px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUSSCOPE_CODE_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusScopeToolbar','BusScopeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusScopeForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="BusScopeGrid" fitColumns="true" toolbar="#BusScopeToolbar"
				method="post" idField="ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="domesticControllerController.do?businessScopeDatagrid&Q_T.IS_USABLE_EQ=1">
				<thead>
					<tr>
						<th data-options="field:'ID',hidden:true">ID</th>
						<th data-options="field:'BUSSCOPE_NAME',align:'left',formatter:colourformater" width="35%">经营范围条目</th>
						<th data-options="field:'BUSSCOPE_CODE',align:'left',formatter:colourformater" width="25%">条目代码</th>
						<th data-options="field:'ITEM_TYPE',align:'left',formatter:colourformater" width="25%">条目类型</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedBusScopeToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选列表（鼠标左键可双击删除）
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="BusScopeForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td><font class="dddl_platform_html_requiredFlag" >注：首次选择点击修改排序可拖动修改排序</font></td>
							<td><input type="button" value="修改排序"
								class="eve-button"
								onclick="doReSort();" />
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" resize="true" id="SelectedBusScopeGrid" striped="true" 
			    fitColumns="true" toolbar="#SelectedBusScopeToolbar" nowrap="false" 
			    method="post" idField="ID" checkOnSelect="false" 
				selectOnCheck="false" fit="false" height="420px" border="false" 
						data-options="onLoadSuccess:function(){
							     $(this).datagrid('enableDnd');
							}"
				url="domesticControllerController.do?businessScopeSelected&ids=${ids}">
				<thead>
					<tr>
						<th data-options="field:'ID',hidden:true">ID</th>
						<th data-options="field:'INDU_CODE',hidden:true">INDU_CODE</th>
						<th data-options="field:'INDU_NAME',hidden:true">INDU_NAME</th>
						<th data-options="field:'BUSSCOPE_NAME',align:'left',formatter:colourformater" width="35%">经营范围条目</th>
						<th data-options="field:'BUSSCOPE_CODE',align:'left',formatter:colourformater" width="25%">条目代码</th>
						<th data-options="field:'ITEM_TYPE',align:'left',formatter:colourformater" width="30%">条目类型</th>
					</tr>
				</thead>
			</table>
	
		</div>
		<div data-options="region:'south',split:true,border:false" style="height:46px;" >
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

