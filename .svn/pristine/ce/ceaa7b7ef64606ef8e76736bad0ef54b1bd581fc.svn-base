<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,icheck"></eve:resources>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="CONTROL_TYPE" value="${CONTROL_TYPE}">
	    <input type="hidden" name="IS_FORQUERY" value="${IS_FORQUERY}">
	    <input type="hidden" name="CONTROL_NAME" value="文本框">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">来源数据源：</span>
				<input class="easyui-combobox" style="width:150px;" name="DATASOURCE" data-options="
					url:'controlConfigController.do?tableColumns&isForQuery=${IS_FORQUERY}&missionId=${MISSION_ID}&parentId=${PARENT_ID}',
					editable:false,
					method: 'post',
					valueField:'columnName',
					textField:'comments',
					panelWidth: 150,
					panelHeight: '200',
					onSelect:function(rec){
					     if(rec.aliasName){
					         $('input[name=WIDGET_NAME]').val(rec.aliasName);
					     }else{
					         $('input[name=WIDGET_NAME]').val(rec.columnName);
					     }
					     $('input[name=CONTROL_NAME]').val(rec.comments);
					     $('input[name=LABLE_VALUE]').val(rec.comments);
					     $('input[name=MAX_LENGTH]').val((rec.dataLength/2)-2);
					}
				" />
				</td>
				<td>
				<c:if test="${IS_FORQUERY=='1'}" >
				<span style="width: 125px;float:left;text-align:right;">查询规则：</span>
				<input class="easyui-combobox" style="width:150px;" name="QUERY_RULE" data-options="
					url:'dictionaryController.do?load&typeCode=QueryRule',
					editable:false,
					method: 'post',
					valueField:'DIC_CODE',
					textField:'DIC_NAME',
					panelWidth: 150,
					panelHeight: 'auto'
				" />
				</c:if>
				<c:if test="${IS_FORQUERY=='0'}" >
				<span style="width: 125px;float:left;text-align:right;">是否必填：</span>
				<input class="easyui-combobox" style="width:150px;" name="NOT_BLANK" data-options="
					url:'dictionaryController.do?load&typeCode=YesOrNo',
					editable:false,
					method: 'post',
					valueField:'DIC_CODE',
					textField:'DIC_NAME',
					panelWidth: 150,
					panelHeight: 'auto'
				" />
				</c:if>
				
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">标签值：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="LABLE_VALUE" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">组件命名：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="WIDGET_NAME" /> 
				</td>
			</tr>
			
			<c:if test="${IS_FORQUERY=='0'}" >
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">最大长度：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="MAX_LENGTH" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">验证规则：</span>
				<input class="easyui-combobox" style="width:150px;" name="VALIDATE_RULE" data-options="
					url:'dictionaryController.do?load&typeCode=ValidateRule',
					editable:false,
					method: 'post',
					valueField:'DIC_CODE',
					textField:'DIC_NAME',
					panelWidth: 150,
					panelHeight: '200'
				" />
				</td>
			</tr>
			</c:if>
			
		</table>
		</div>
</body>
