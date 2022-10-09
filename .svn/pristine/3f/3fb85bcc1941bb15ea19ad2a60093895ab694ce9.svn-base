<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<script type="text/javascript">
	
</script>
<div class="bsbox clearfix">
	<div class="bsboxC">
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="cjgfzrztGrid" fitcolumns="true"
			method="post" idfield="ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectFinishManageController.do?cjgfzrztList&projectCode=${projectCode }">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true">ID</th>	
					<th data-options="field:'PROJECT_CODE',align:'left'" width="18%">参建各方责任主体类型</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="35%">参建各方责任主体名称</th>
					<th data-options="field:'APPLY_DATE',align:'left'" width="15%">法定代表人</th>				
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">项目负责人</th>
					<th data-options="field:'XMSFWQBJ',align:'left'" width="17%">项目负责人联系电话</th>
				</tr>
			</thead>
		</table>
	</div>
</div>