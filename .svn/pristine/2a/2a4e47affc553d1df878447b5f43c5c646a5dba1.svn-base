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

	function doSelectRows(){
		var rows = $("#SwbRecGrid").datagrid("getChecked");
		if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
		}else{
			var unid = "";
			for(var i = 0;i<rows.length;i++){
				unid=unid + "\'" +rows[i].UNID +"\'";
				if(i+1<rows.length){
					unid=unid + ",";
				}
			}
	        AppUtil.ajaxProgress({
	            url:"serviceItemController.do?receiveSwbItemByIds",
	            params:{
	            	unid:unid
	            },
	            callback:function(resultJson){
					if (resultJson.success) {
						parent.art.dialog({
							content: resultJson.msg,
							icon: "succeed",
							time: 3,
							ok: true
						});
					} else {
						parent.art.dialog({
							content: resultJson.msg,
							icon: "error",
							time: 3,
							ok: true
						});
					}

	                parent.$("#departServiceItemGrid").datagrid("reload");
	                AppUtil.closeLayer();
	            }
	        })
		}
		
	}
    function doDeleteSelectRows(){
    	var rows = $("#SwbRecGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
		}else{
    		var unid = "";
			for(var i = 0;i<rows.length;i++){
				unid=unid + "\'" +rows[i].UNID +"\'";
				if(i+1<rows.length){
					unid=unid + ",";
				}
			}
            AppUtil.ajaxProgress({
                url:"serviceItemController.do?deleteSwbItemByIds",
                params:{
                	unid:unid
                },
                callback:function(resultJson){
                      parent.art.dialog({
                            content: resultJson.msg,
                            icon:"succeed",
                            time:3,
                            ok: true
                        });
                   $("#SwbRecGrid").datagrid("reload");
                }
            })
    	}
    }
    function linkSwbItemCatalog() {
		var rows = $("#SwbRecGrid").datagrid("getChecked");
		if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
				icon:"warning",
				ok: true
			});
			return null;
		}else if (rows.length > 1) {
			art.dialog({
				content: "仅能选择一条记录!",
				icon: "warning",
				ok: true
			});
			return null;
		} else {
			var unid = rows[0].UNID;
			parent.$.dialog.open("serviceItemController.do?linkSwbItemCatalogView&unid=" + unid, {
				title: "关联目录",
				width: "600px",
				lock: true,
				resize: false,
				height: "200px",
				close: function () {
					var resultJson = art.dialog.data("resultJson");
					console.log(resultJson)
					if (resultJson && resultJson.success) {
						art.dialog({
							content: resultJson.msg,
							icon: "warning",
							ok: true
						});
					}
				}
			}, false);
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="SwbRecToolbar">
				<form action="#" name="SwbRecForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:68px;text-align:right;">部门</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.DEP_NAME_LIKE" /></td>
								<td style="width:68px;text-align:right;">事项名称</td>
								<td style="width:135px;"><input class="eve-input"
									type="text" maxlength="20" style="width:128px;"
									name="Q_T.ITEM_NAME_LIKE" /></td>
								<td colspan="2"><input type="button" value="查询"
									class="eve-button"
									onclick="AppUtil.gridDoSearch('SwbRecToolbar','SwbRecGrid')" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('SwbRecForm')" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="SwbRecGrid" fitColumns="true" toolbar="#SwbRecToolbar"
				method="post" idField="UNID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="serviceItemController.do?swbRecDatas">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'UNID',hidden:true" width="80">UNID</th>
	                    <th data-options="field:'DEPTNAME',align:'left'" width="120">所属部门</th>
						<th data-options="field:'SERVICENAME',align:'left'" width="120">事项名称</th>
						<th data-options="field:'CREATETIME',align:'left'" width="120">下发时间</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="无需同步移除" type="button" onclick="doDeleteSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
<%--				<input value="关联目录" type="button" onclick="linkSwbItemCatalog();"--%>
<%--					   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />--%>
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
