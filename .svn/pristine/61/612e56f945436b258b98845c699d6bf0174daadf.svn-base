<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">	

    function Accept(){
		var yctbsl = 1;
    	var rows = $("#QueueItemDetailGrid").datagrid("getChecked");
    	if(rows.length==0){
            afterDeal();
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
    		var defKey = rows[0].DEF_KEY;
			var itemCode = rows[0].ITEM_CODE;
			var itemName = rows[0].ITEM_NAME;
			if(defKey.indexOf('6')>-1){
				parent.art.dialog({
					content: "该事项审批流程未规范，请梳理后联系技术处（059123122315）协助重新配置。 ",
					icon:"error",
					ok: true
				});
				return null;
			}    			
			toUrl("executionController.do?goStart&defKey="
					+ defKey + "&itemCode=" + itemCode
					+ "&acceptway=" + yctbsl + "&itemdetail_id=" + rows[0].DETAIL_ID + "&subBusClass=" +rows[0].SUBBUS_CLASS
					+ "&lineCard=${lineCard}&lineId=${lineId}&zjlx=${zjlx}","${lineName}");
			
    	}
    	
    }
    
	function toUrl(url,lineName){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var lineNameInput="<input name='lineName' type='hidden' value='"+lineName+"' />";
		$("#ItemToolbar").append(ssoForm);
		ssoForm.append(lineNameInput);
		ssoForm.submit();
	}
	
	//咨询
	function dealzx(index) { 
    	var rows = $("#QueueItemDetailGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			$.dialog.open("newCallController.do?goYctbQueuingDealzx&detailId="
					+ rows[0].DETAIL_ID, {
				title : "办件处理",
				width : "500px",
				height : "300px",
				lock : true,
				resize : false,
				close : function (){
					afterDeal();
				}
			}, false);
		}
	}
	//领照
	function deallz(index) {  
    	var rows = $("#QueueItemDetailGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			$.dialog.open("newCallController.do?goYctbQueuingDeallz&detailId="
					+ rows[0].DETAIL_ID, {
				title : "办件处理",
				width : "500px",
				height : "300px",
				lock : true,
				resize : false,
				close : function (){
					afterDeal();
				}
			}, false);
		}
	}
	//取错事项
	function mistake(){  
    	var rows = $("#QueueItemDetailGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			$.dialog.open("newCallController.do?goYctbQueuingDealMistake&detailId="
					+ rows[0].DETAIL_ID, {
				title : "取错事项",
				width : "500px",
				height : "300px",
				lock : true,
				resize : false,
				close : function (){
					afterDeal();
				}
			}, false);
		}
	}
	
	function dosearchItem(){
		$("#QueueItemDetailGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('ItemToolbar','QueueItemDetailGrid');
	}
	
	function afterDeal(){
		AppUtil.ajaxNoProgress({
			url : "newCallController.do?checkLineItemDeal",
			params : {
				lineId : '${lineId}'
			},
			callback : function(resultJson) {
				if(resultJson.success){
					AppUtil.closeLayer();
				}else{
					dosearchItem();
				}
			}
		});
	}
	</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="ItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="QueueItemDetailGrid" fitColumns="true" toolbar="#ItemToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="newCallController.do?curSelectedItem&Q_i.RECORD_ID_EQ=${lineId}">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'BUSINESS_NAME',align:'left'" width="15%">所属业务类别</th>
	                    <th data-options="field:'ITEM_CODE',align:'left'" width="20%">事项编码</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="60%">事项名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="取错事项" type="button" onclick="mistake();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="受理" type="button" onclick="Accept();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="领照" type="button" onclick="deallz();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="咨询" type="button" onclick="dealzx();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
