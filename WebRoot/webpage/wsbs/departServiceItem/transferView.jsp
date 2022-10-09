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

    function showParentSelectDeparts(){
        var departId = $("input[name='IMPL_DEPART_ID']").val();
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "划转部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='IMPL_DEPART_ID']").val(selectDepInfo.departIds);
                    $("input[name='IMPL_DEPART']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function showChildSelectDeparts(){
    	var departId = $("input[name='ZBCS_ID']").val();
		var parentId = $("input[name='IMPL_DEPART_ID']").val();
		if(parentId==null||parentId==""){
			parent.art.dialog({
				content: "请先选择划转部门",
				icon:"warning",
				ok: true
			});
			return false;
		}
        parent.$.dialog.open("departmentController/childSelector.do?rootParentId="+parentId+"&departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "划转子部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='ZBCS_ID']").val(selectDepInfo.departIds);
                    $("input[name='ZBCS']").val(selectDepInfo.departNames);
                }
    			art.dialog.removeData("selectDepInfo");
            }
        }, false);
    }
    
    function doTransfer(){    	
    	var ZBCS_ID = $("input[name='ZBCS_ID']").val();
		var IMPL_DEPART_ID = $("input[name='IMPL_DEPART_ID']").val();		
		if(IMPL_DEPART_ID==null||ZBCS_ID==""){
			parent.art.dialog({
				content: "请先选择划转部门和划转子部门",
				icon:"warning",
				ok: true
			});
			return false;
		}
    	var ZBCS = $("input[name='ZBCS']").val();
		var IMPL_DEPART = $("input[name='IMPL_DEPART']").val();
		
        var rows = $("#transferViewGrid").datagrid("getRows");  
        var itemIds = [];
        for(var i=0;i<rows.length;i++){
        	itemIds.push(rows[i].ITEM_ID);
        }
        console.log(itemIds.toString());
        if(itemIds.length>0){
            AppUtil.ajaxProgress({
                url:"departServiceItemController.do?doTransfer",
                params:{
                	itemIds:itemIds.toString(),
                	IMPL_DEPART_ID:IMPL_DEPART_ID,
                	IMPL_DEPART:IMPL_DEPART,
                	ZBCS_ID:ZBCS_ID,
                	ZBCS:ZBCS
                },
                callback:function(resultJson){
                	parent.art.dialog({
                         content: resultJson.msg,
                         icon:"succeed",
                         time:3,
                         ok: true
                    });
                    AppUtil.closeLayer();
                }
            })
        }
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="transferViewToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">划转部门</td>							
                            <td style="width:235px;"><input class="eve-input" onclick="showParentSelectDeparts();"
                                style="width:228px;" name="IMPL_DEPART" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="IMPL_DEPART_ID" value=""  />
                            </td>
                            <td style="width:68px;text-align:right;">划转子部门</td>
                            <td style="width:235px;"><input class="eve-input" onclick="showChildSelectDeparts();"
                                style="width:228px;" name="ZBCS" value="" readonly="readonly"/>
                                 <input type="hidden" name="ZBCS_ID" value=""  />
                            </td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
				id="transferViewGrid" fitColumns="true" toolbar="#transferViewToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="departServiceItemController.do?selectedForTransferData&itemIds=${itemIds }">
				<thead>
					<tr>
	                    <!-- <th field="ck" checkbox="true"></th> -->
						<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
	                    <th data-options="field:'ITEM_CODE',align:'left'" width="15%">事项编码</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="50%">事项名称</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
						<th data-options="field:'CHILD_DEPART_NAME',align:'left'" width="15%" >主办处室</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doTransfer();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
