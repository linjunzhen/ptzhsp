<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,json2,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
var editIndex = undefined;
/**
 * 结束编辑模式
 */
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#approvalItemInfoGrid").datagrid("validateRow", editIndex)){
		var ed = $("#approvalItemInfoGrid").datagrid("getEditor", {index:editIndex,field:'current_state'});
		var DIC_NAME = $(ed.target).combobox("getText");
		$("#approvalItemInfoGrid").datagrid("getRows")[editIndex]["DIC_NAME"] = DIC_NAME;
		$("#approvalItemInfoGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#approvalItemInfoGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#approvalItemInfoGrid").datagrid("selectRow", editIndex);
		}
	}
}

function addFlowEvent(){
	if (endEditing()){
		$("#approvalItemInfoGrid").datagrid("appendRow",{});
		editIndex = $("#approvalItemInfoGrid").datagrid("getRows").length-1;
		$("#approvalItemInfoGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}

function delFlowEvent() {
	if (editIndex == undefined){
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#approvalItemInfoGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

function confirmFlowEvent(){
	
		$("#approvalItemInfoGrid").datagrid("acceptChanges");
		var rows = $("#approvalItemInfoGrid").datagrid("getRows");
		var tableInfoJson = "";
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"approval_itemid":rows[i].approval_itemid,
				"project_code":rows[i].project_code,
				"item_code":rows[i].item_code,
				"item_name":rows[i].item_name,
				"dept_name":rows[i].dept_name,
				"current_state":rows[i].current_state,
				"deal_time":rows[i].deal_time,
				"limit_days":rows[i].limit_days,
				"plan_finish_date":rows[i].plan_finish_date,
				"approval_number":rows[i].approval_number,
				"validity_date":rows[i].validity_date
			});
		}
		var projectJson = JSON2.stringify(datas);
	    var subObj = {};
	    subObj.CONTENT = projectJson;
	    subObj.PROJECT_CODE = $("#projectCode").val();
		subObj.OPER_USERNAME = $("#OPER_USERNAME").val();
		subObj.EXE_ID = $("#exeid").val();
		subObj.STATUS = 0;
	    var formData = $.param(subObj);
	    AppUtil.ajaxProgress({
			url : "shtzController.do?saveOrUpdate",
			params : formData,
			callback : function(resultJson) {
				if(resultJson.success){
				    parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			}
		});
}
function saveOrUpdate(){
	if (endEditing()){
		var  projectCode = $("#projectCode").val();
		var subObj = {};
		subObj.projectCode = projectCode;
		var formData = $.param(subObj);
		AppUtil.ajaxProgress({
			url : "shtzController.do?verification",
			params : formData,
			callback : function(resultJson) {
				if(resultJson.success){
					confirmFlowEvent();
				}else{
					art.dialog.confirm(resultJson.msg,function(){
						confirmFlowEvent();
					});
				}
			}
		});
	}else{
		alert("数据无效,请重新输入!");
	}
}
</script>

<div class="easyui-layout" fit="true" style="word-break: break-all;">
    <input type="hidden" id="projectCode" name="projectCode" value="${projectCode}"/>
    <%--
    <input type="hidden" id="OPER_USERNAME" name="OPER_USERNAME" value="${session111Scope.curLoginUser.fullname}"/>
     --%>
    <input type="hidden" id="exeid" name="exeid" value="${exeid}"/>
	<div region="center">
		<!-- =========================查询面板开始=========================-->
		<div id="approvalItemInfoToolBar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
								onclick="saveOrUpdate();">保存</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" nowrap="false" rownumbers="true" pagination="false"
			id="approvalItemInfoGrid" fitcolumns="true" toolbar="#approvalItemInfoToolBar"
			idfield="EVENT_ID" singleSelect="true" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			onClickRow="onClickRow" url="shtzController.do?datagrid&projectCode=${projectCode}"
			data-options="autoSave:true,method:'post',onClickRow: onClickRow"
			>
			<thead>
				<tr>
					<th data-options="field:'approval_itemid',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,200]'}}" width="100" width="80">项目审批事项ID</th>
					<th data-options="field:'project_code',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,200]'}}" width="100" >项目编号</th>
					<th data-options="field:'item_code',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,50]'}}" width="100" >事项编码</th>
					<th data-options="field:'item_name',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,128]'}}" width="150" >事项名称</th>
					<th data-options="field:'dept_name',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,128]'}}" width="150" >审批部门名称</th>
					<th data-options="field:'current_state',width:'60',align:'left',
					    formatter:function(value,row){
					        if(value=='A0100'){
					            return '已接件';
					        }else if(value=='A0101'){
					            return '已退件';					           
					        }else if(value=='A0200'){
					            return '办理中';	
					        }else if(value=='A0300'){
					            return '不予受理';	
					        }else if(value=='A0501'){
					            return '批复办结';	
					        }else if(value=='A0502'){
					            return '不予行政许可';	
					        }
						},
					    editor:{
							type:'combobox',
							options:{
							    editable:false,
								valueField:'DIC_CODE',
								textField:'DIC_NAME',
								url:'dictionaryController.do?load&typeCode=dqblzt',
								required:true
							}
						}
					"
					>办理状态</th>
					<th data-options="field:'deal_time',align:'left',editor:{type:'datebox',options:{required:false,validType:'length[1,20]'}}" width="80" >状态变更时间</th>
					<th data-options="field:'limit_days',align:'left',editor:{type:'validatebox',options:{required:false,validType:'length[1,20]'}}" width="65" >事项办理时限</th>
					<th data-options="field:'plan_finish_date',align:'left',editor:{type:'datebox',options:{required:false,validType:'length[1,20]'}}" width="100" >事项计划办结时间</th>
					<th data-options="field:'approval_number',align:'left',editor:{type:'validatebox',options:{required:false,validType:'length[1,32]'}}" width="45" >批复文号</th>
					<th data-options="field:'validity_date',align:'left',editor:{type:'datebox',options:{required:false,validType:'length[1,20]'}}" width="100" >批复文件有效期限</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
