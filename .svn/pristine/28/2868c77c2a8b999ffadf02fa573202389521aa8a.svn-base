<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

SysUser sysUser = AppUtil.getLoginUser();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta name="renderer" content="webkit">
<script type="text/javascript"
	src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
<eve:resources
	loadres="jquery,apputil,easyui,validationegine,artdialog,ztree,swfupload,layer,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<style>
.layout-split-south {
	border-top-width: 0px !important;
}

.eve_buttons {
	position: relative !important;
}
</style>
<script type="text/javascript">
    $(document).ready(function() {
    });

    $(function() {
        $('#ProjectItemGrid').datagrid({ pagination: false,
            onLoadSuccess: function (data) {
                if (data.rows.length > 0) {
                }
            }
        });
    });

function dosearchProjectItem(){
    $("#ProjectItemGrid").datagrid("clearChecked");
    AppUtil.gridDoSearch('ProjectItemToolbar','ProjectItemGrid');
}

function formatReCall(val,row){
    var htmlrc = "";
     htmlrc += "<input type='button' value='还原下架事项' class='eve-button' onclick=\"reduction('"+row.ID+"');\"/>"
    return htmlrc
};  

function reduction(id){
            art.dialog.confirm("您确定要还原该下架事项吗?", function() {
                var layload = layer.load('正在提交请求中…');
                $.post("flowTemplateController.do?reductionItem",{
                       ID:id
                    }, function(responseText, status, xhr) {
                        layer.close(layload);
                        var resultJson = $.parseJSON(responseText);
                        if(resultJson.success == true){
                            art.dialog({
                                content: resultJson.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                                $("#ProjectItemGrid").datagrid('reload');
                                $("#ProjectItemGrid").datagrid('clearSelections').datagrid('clearChecked');
                                parent.$("#flowConfigItemGrid").datagrid("reload");
                        }else{
                            parent.$("#flowConfigItemGrid").datagrid('reload');
                            art.dialog({
                                content: resultJson.msg,
                                icon:"error",
                                ok: true
                            });
                        }
                });
            });

}



</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true">
		<div data-options="region:'center',split:false">
			<div id="ProjectItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="Q_T.CATALOG_CODE_EQ" /> <input
					type="hidden" name="STAGE_ID" value="${STAGE_ID}" /> <input
					type="hidden" id="projectCode" value="${PROJECT_CODE}" />
				<!--====================结束编写隐藏域============== -->
				<form name="ProjectItemForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:40px;">
							<td style="width:80px;text-align:right;">事项编码</td>
							<td style="width:190px;"><input class="eve-input"
								type="text" maxlength="20" style="width:183px;"
								name="Q_F.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="40" style="width:280px;"
								name="Q_F.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button" onclick="dosearchProjectItem();" /> <input
								type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ProjectItemForm')" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="ProjectItemGrid" fitColumns="true" toolbar="#ProjectItemToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="true"
				url="flowTemplateController.do?itemDatagrid=350&Q_T.STAGE_ID_EQ=${STAGE_ID}&Q_T.IS_OFF_SHELVES_EQ=1">
				<thead>
					<tr>
						<th data-options="field:'ID',hidden:true" width="80">ID</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="25%">事项编码</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="50%">事项名称</th>
						<th data-options="field:'IS_KEY_ITEM',align:'left'" width="22%"
							formatter="formatReCall">操作</th>
					</tr>
				</thead>
			</table>
		</div>

		<div data-options="region:'south',split:true,border:false">
			<div class="eve_buttons" style="text-align: right;">
				<input value="关闭" type="button"
					class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>


</body>
</html>
