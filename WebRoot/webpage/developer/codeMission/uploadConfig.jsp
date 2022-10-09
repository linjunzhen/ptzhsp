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
					panelHeight: 'auto',
					onSelect:function(rec){
					     if(rec.aliasName){
					         $('input[name=WIDGET_NAME]').val(rec.aliasName);
					     }else{
					         $('input[name=WIDGET_NAME]').val(rec.columnName);
					     }
					     $('input[name=CONTROL_NAME]').val(rec.comments);
					     $('input[name=LABLE_VALUE]').val(rec.comments);
					}
				" />
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">上传控件类型：</span>
				<input class="easyui-combobox" style="width:150px;" name="UPLOAD_TYPE" data-options="
					url:'dictionaryController.do?load&typeCode=UploadControlType',
					editable:false,
					method: 'post',
					valueField:'DIC_CODE',
					textField:'DIC_NAME',
					panelWidth: 150,
					panelHeight: 'auto',
					onSelect:function(rec){
					     if(rec.DIC_CODE=='1'){
					         $('input[name=UPLOAD_LIMITTYPES]').val('*.jpg;*.jpeg;*.gif;*.png;');
					     }else if(rec.DIC_CODE=='2'){
					         $('input[name=UPLOAD_LIMITTYPES]').val('*.docx;*.doc;');
					     }else if(rec.DIC_CODE=='3'){
					         $('input[name=UPLOAD_LIMITTYPES]').val('*.docx;*.doc;');
					     }
					}
				" />
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
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">上传限制类型：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="UPLOAD_LIMITTYPES" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">限制文件大小：</span>
				<input
					type="text" style="width:150px;float:left;" value="10 MB"
					 class="eve-input" name="UPLOAD_LIMITSIZE" /> 
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">上传路径：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="UPLOAD_PATH" /> 
				</td>
				<td>
				<!-- 
				<span style="width: 125px;float:left;text-align:right;">上传表格ID：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="UPLOAD_TABLEID" />
					 --> 
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">关系业务表名：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="BIND_TABLENAME" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">关系字段名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="WIDGET_NAME" /> 
				</td>
			</tr>
			
			
			
		</table>
		</div>
</body>
