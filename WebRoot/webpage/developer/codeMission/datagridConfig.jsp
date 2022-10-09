<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,icheck"></eve:resources>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="CONTROL_TYPE" value="${CONTROL_TYPE}">
	    <input type="hidden" name="BIND_ENTITYNAME" >
	    <input type="hidden" name="BIND_CHINESE">
	    <input type="hidden" name="BIND_TABLENAME">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">绑定实体类：</span>
				<input class="easyui-combobox" style="width:150px;" name="BIND_ENTITYNAME" data-options="
					url:'codeMissionController.do?entitys&missionId=${MISSION_ID}',
					editable:false,
					method: 'post',
					valueField:'className',
					textField:'className',
					panelWidth: 150,
					panelHeight: 'auto',
					onSelect:function(rec){
					     $('input[name=BIND_ENTITYNAME]').val(rec.className);
					     $('input[name=BIND_CHINESE]').val(rec.chineseName);
					     $('input[name=BIND_TABLENAME]').val(rec.tableName);
					}
				" />
				</td>
			</tr>
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">SQL语句：</span>
				<textarea rows="5" cols="5" style="width: 400px" name="SQL_CONTENT"></textarea>
				</td>
			</tr>
			
		</table>
		</div>
</body>
