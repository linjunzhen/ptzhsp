<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("webRoot", basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<eve:resources loadres="jquery,apputil,easyui,validationegine,artdialog,ztree,swfupload,layer,json2"></eve:resources>
	<script type="text/javascript" src="<%=basePath%>/webpage/project/projectDetail/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/webpage/project/projectDetail/js/jquery.SuperSlide.2.1.3.js"></script>
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/project/projectDetail/css/projectDetail.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="ProjectNodeToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" id="projectCode" value="${projectCode}"/>
				<!--====================结束编写隐藏域============== -->
				
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="javascript:void(0);" class="easyui-linkbutton" reskey="ADD_Nodes"
									iconcls="eicon-plus" plain="true"
									onclick="showTicketsWindow();">新增</a> 
								<a href="javascript:void(0);" class="easyui-linkbutton" reskey="EDIT_Nodes"
									iconcls="eicon-pencil" plain="true"
									onclick="editTicketsGridRecord();">编辑</a> 
								<a href="javascript:void(0);" class="easyui-linkbutton" reskey="DEL_Nodes"
									iconcls="eicon-trash-o" plain="true"
									onclick="deleteTicketsGridRecord();">删除</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="ProjectNodeGrid" fitColumns="true" toolbar="#ProjectNodeToolbar"
				method="post" idField="NODE_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="true"
				url="projectFlowController.do??projectNodeList&projectCode=${projectCode}">
				<thead>
					<tr>
	                    <th data-options="field:'NODE_ID',hidden:true">NODE_ID</th>
	                    <th data-options="field:'CREATE_NAME',align:'left'" width="15%">创建人员</th>
	                    <th data-options="field:'NODE_CONTENT',align:'left'" width="70%" >便笺内容</th>
	                    <th data-options="field:'CREATE_TIME',align:'left'" width="15%" >创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>
	<script>
        jQuery(".easyui-layout").slide({
            trigger: "click"
        });
    </script>
</body>
</html>
