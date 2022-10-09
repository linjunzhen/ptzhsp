<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<link type="text/css" href="<%=basePath%>/plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
<link type="text/css" href="<%=basePath%>/plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">	
<!-- 权力流程图相关脚本开始 -->
<script src="<%=path%>/webpage/flowchart/js/gojs.js"></script>
<%-- <script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowview.js"></script> --%>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/allmaterials/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/allmaterials/js/flowview.js"></script>
<!-- 权力流程图相关脚本结束 -->
<!-- 审核相关脚本开始 -->	
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<!-- 审核相关脚本结束 -->
<script type="text/javascript"	src="<%=basePath%>/plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript"	src="<%=basePath%>/plug-in/urlparser/URI.js"></script>
<!-- 脚本编写结束 -->
<script type="text/javascript">
    /**
    *流程提交
    */
	function FLOW_SubmitFun(flowSubmitObj){
		//把意见回填给提交页面
		if($("#EFLOW_HANDLE_OPINION").val()){
			flowSubmitObj.EFLOW_HANDLE_OPINION = $("#EFLOW_HANDLE_OPINION").val();
		}	
		return flowSubmitObj;
	}
	/**
    *流程暂存
    */
	function FLOW_TempSaveFun(flowSubmitObj){
		 return flowSubmitObj;
	}
	/**
    *流程回退（推回）
    */
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}

    /**
	* 字段类型
	*/
	function fieldTypeFormatter(val,row){
		if(row.FIELD_TYPE=="0"){
			return "<font color='blue'><b>默认</b></font>";
		}else if(row.FIELD_TYPE=="1"){
			return "<font color='blue'><b>时间类型</b></font>";
		}
	};
	/**
	* 是否显示
	*/
	function isShowFormatter(val,row){
		if(row.IS_SHOW=="0"){
			return "<font color='blue'><b>是</b></font>";
		}else if(row.IS_SHOW=="1"){
			return "<font color='blue'><b>否</b></font>";
		}
	};

	/**====权力流程图部分开始======*/

	var everoot='<%=path%>';
	var showflg=true;
	var tacheCode;
	var tacheId;
	var busiCode;
		 

	//点击事件
	function nodeClickC(data){
		var ndata=findJsonDataForKey(data.key);
		//刷新变更前监察字段列表数据
		if(document.getElementById("SuperColumnDataGrid")){
			reloadRightDataGrid("SuperColumnDataGrid",ndata.id,"materialsdatagrid");
		}		
		//刷新变更后监察字段列表数据
		if(document.getElementById("SuperColumnChangeDataGrid")){
		    reloadRightDataGrid("SuperColumnChangeDataGrid",ndata.id,"materialsChangedatagrid");
		}
		//刷新变更前监察要素及规则
		if(document.getElementById("SuperElementsRulesGrid")){
			reloadRightDataGrid("SuperElementsRulesGrid",ndata.id,"RuleDatagrid");
		}
		//刷新变更后监察要素及规则
		if(document.getElementById("SuperElementsChangeRulesGrid")){
			reloadRightDataGrid("SuperElementsChangeRulesGrid",ndata.id,"RuleChangeDatagrid");
		}
		//刷新变更前监察要素及规则
		if(document.getElementById("SuperOnlyRulesGrid")){
			reloadRightDataGrid("SuperOnlyRulesGrid",ndata.id,"RuleDatagrid");
		}
		//刷新变更后监察要素及规则
		if(document.getElementById("SuperOnlyChangeRulesGrid")){
			reloadRightDataGrid("SuperOnlyChangeRulesGrid",ndata.id,"RuleChangeDatagrid");
		}
	}

	//刷新右侧表格数据（监察字段，监察要素及规则）
	function reloadRightDataGrid(datagridId,processCode,pathStr){
		var options = $("#"+datagridId+"").datagrid("options");
		var url = options.url;
		var URL = URI(url);
		var path = URL.path();
		$("#"+datagridId+"").datagrid("options").url = path + "?"+ pathStr;
		var queryParams = options.queryParams;
		//赋初值
		var strs = URL.query().split("&");	
		for(var i=0;i<strs.length;i++){
			var str = strs[i];
			if(str.split("=").length>1){
				var attrs = str.split("=");
				queryParams[attrs[0]] = attrs[1];
			}
		}
		//改变过程编号的直到
		queryParams.processCode = processCode;
		$("#"+datagridId+"").datagrid("options").queryParams=queryParams;
		$("#"+datagridId+"").datagrid("reload");
	};

	function toChart(){
		window.open("allMaterialsController.do?changeinfo","_blank");
	}
	
	function setPanelTitle(id){
		var buttonDir = {north:'down',south:'up',east:'left',west:'right'};
		var popts = $("#"+id).panel('options');
	    var dir = popts.region;
	    var btnDir = buttonDir[dir];
	    if(!btnDir) return false;
	    setTimeout(function(){
	        var pDiv = $('.layout-button-'+btnDir).closest('.layout-expand').css({
	            textAlign:'center',lineHeight:'18px',fontWeight:'bold'
	        });
	        if(popts.title){
	            var vTitle = popts.title;
	            if(dir == "east" || dir == "west"){
	                var vTitle = popts.title.split('').join('<br/>');
	                pDiv.find('.panel-body').html(vTitle);
	            }else{
	                $('.layout-button-'+btnDir).closest('.layout-expand').find('.panel-title')
	                .css({textAlign:'left'})
	                .html(vTitle)
	            }
	        }    
	    },100);
	}

	function tmpSave(){
		var task_id = $("#task_id").val();
		AppUtil.ajaxProgress({
			url : "allMaterialsController.do?tempSave",
			params : {
				"task_id" : task_id,
				"option" : $("#EFLOW_HANDLE_OPINION").val()
			},	
			callback:function(result){
				if(result.success){
					$.messager.alert('提示',"保存成功！");
				}
			}
		});
	}
	
	$(function(){
		var task_id = $("#task_id").val();
		if(task_id == 'null'){
			$("#firstPanel").layout("collapse","south");  
		}
	});
	
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout bodySelectNone">
  <!-- ===页面隐藏域开始=== -->
  <input type="hidden" name="task_id" id="task_id" value="${taskId}"/>
  <input type="hidden" name="bus_unitcode" value="${busInfo.unitCode}" />
  <input type="hidden" name="bus_specialCode" value="${busInfo.busCode}" />
  <input type="hidden" id="flowFlag" name="flowFlag" value="${flowFlag}" />
  <!-- ===页面隐藏域结束=== -->
  <!-- 主体页面编写开始 -->
  <div class="easyui-layout" id="firstPanel" fit="true"> 
    <div id="firstSouthPanel" data-options="region:'south',collapsible:true,onCollapse:function(){setPanelTitle('firstSouthPanel')}"  
    	class="right-con" title="意见批注(字数限制2000)" height="30%" split="true" border="false" iconCls='icon-search'>
		<table style="width:100%;border-collapse:collapse;"	class="dddl-contentBorder dddl_table">
		 <tr>
			<td colspan="2"><span style="width: 100px;float:left;text-align:right;">内容：</span>
			       <textarea rows="7" cols="190" class="eve-textarea validate[required,maxSize[2000]]" maxlength="2000"
					 id="EFLOW_HANDLE_OPINION">${option}</textarea>
					 <c:if test="${taskId != 'null'}">
					 	<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-note-add" plain="true" 
                        	onclick="tmpSave();">暂存</a>
                     </c:if>
			</td>
		 </tr>
		</table>
		<!-- <div class="right-con"> 
	    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;"> 
	     <div class="e-toolbar-ct"> 
	      <div class="e-toolbar-overflow"> 
	         <div style="color:#005588;">         
	         	<img src="plug-in/easyui-1.4/themes/icons/large_chart.png"
									style="position: relative; top:7px; float:left;height:18px;" />&nbsp;专项信息提交详情表单
				<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-note-add" plain="true" 
                        onclick="toChart();">批注</a>
	         </div>
	      </div> 
	     </div> 
	    </div> 
	   </div> -->
	</div>   
    <!-- 针对单一要素的变更开始(左右布局) -->
    <c:if test="${pageType eq 1}">
      <!-- 基本信息字段变更开始 -->
      <c:if test="${basicFlag eq 1}">
        <div title="【变更前】基本信息字段" data-options="region:'west'" width="50%" split="false">
        	<c:if test="${fn:length(sysLastList) > 1 }">
	             <div class="easyui-tabs eve-tabs" fit="true">
	                <c:forEach items="${sysLastList}" var="sys">
	             	<div title="${sys.SYSTEM_NAME}">
	             		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
						  		method="post" 
						  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
						  		fit="true" border="false" nowrap="false"
						  		url="busColumnBasicController.do?materialsdatagrid&columnType=1&processCode=${buscode}&platCode=${sys.SYSTEM_CODE}&appliyId=${lastApplyId}"> 
						   <thead> 
						    <tr> 
						     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
						     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
						     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
						     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
						     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
						    </tr> 
						   </thead> 
						 </table>
	             	</div> 
	             	</c:forEach>            	
	             </div>
             </c:if>
             <c:if test="${fn:length(sysLastList) eq 1 }">
             	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
				  		method="post" 
				  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
				  		fit="true" border="false" nowrap="false"
				  		url="busColumnBasicController.do?materialsdatagrid&columnType=1&processCode=${buscode}&platCode=${sysLastList[0].SYSTEM_CODE}&appliyId=${lastApplyId}"> 
				   <thead> 
				    <tr> 
				     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
				     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
				     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
				     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
				     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
				    </tr> 
				   </thead> 
				 </table>
             </c:if>
        </div>
    	<div title="【变更后】基本信息字段" data-options="region:'center'">
    		<c:if test="${fn:length(sysList) > 1 }">
	             <div class="easyui-tabs eve-tabs" fit="true">
	               <c:forEach items="${sysList}" var="sys">
	             	<div title="${sys.SYSTEM_NAME}">
	             		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
						  		method="post" 
						  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
						  		fit="true" border="false" nowrap="false"
						  		url="busColumnBasicController.do?materialsChangedatagrid&columnType=1&processCode=${buscode}&platCode=${sys.SYSTEM_CODE}&appliyId=${applyId}"> 
						   <thead> 
						    <tr> 
						     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
						     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
						     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
						     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
						     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
						    </tr> 
						   </thead> 
						 </table>
	             	</div> 
	              </c:forEach>            	
	             </div>
             </c:if>
             <c:if test="${fn:length(sysList) eq 1 }">
             	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
				  		method="post" 
				  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
				  		fit="true" border="false" nowrap="false"
				  		url="busColumnBasicController.do?materialsChangedatagrid&columnType=1&processCode=${buscode}&platCode=${sysList[0].SYSTEM_CODE}&appliyId=${applyId}"> 
				   <thead> 
				    <tr> 
				     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
				     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
				     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
				     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
				     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
				    </tr> 
				   </thead> 
				 </table>
             </c:if>
    	</div>
      </c:if>
      <!-- 基本信息字段变更开始 -->
      <!-- 业务流程图变更开始 -->
      <c:if test="${flowFlag eq 1}">
	    <div title="变更前流程图" data-options="region:'west'" width="30%" split="false">
	       <div id="oldImgSvg" style="text-align: center">
	        <svg style="border: 1px solid black;" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/2000/svg" XK="0 0 300 500" height="550px" width="400px"><g clip-path="url(#mainClip)" transform="matrix(1, 0, 0, 1, 0, 0)"><g transform="matrix(0.5, 0, 0, 0.5, -126.75, -6.5)"><g transform="matrix(1, 0, 0, 1, 274.91472822011906, 15)"><g transform="matrix(1, 0, 0, 1, 0, 0)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#79C900" d="M 28.585271779880973,0 C 44.37248145367778,0 57.170543559761946,12.798062106084167 57.170543559761946,28.585271779880973 C 57.170543559761946,44.37248145367778 44.37248145367778,57.170543559761946 28.585271779880973,57.170543559761946 C 12.798062106084167,57.170543559761946 0,44.37248145367778 0,28.585271779880973 C 0,12.798062106084167 12.798062106084167,0 28.585271779880973,0 z"></path><text transform="matrix(1, 0, 0, 1, 14.418604795322864, 21.549999999999997)" stroke="none" fill="whitesmoke" text-anchor="start" style="font: bold 11pt Helvetica, Arial, sans-serif" y="12.675" x="0">开始</text></g><path transform="matrix(1, 0, 0, 1, 0.5, 26)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 49.67054355976194, 26)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 25.08527177988097, 51.5)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path></g><g transform="matrix(1, 0, 0, 1, 268.8333339691162, 118.35)"><g transform="matrix(1, 0, 0, 1, 0, 0)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#00A9C9" d="M 0,0 L 69.33333206176758,0 L 69.33333206176758,30.3 L 0,30.3 z"></path><text transform="matrix(1, 0, 0, 1, 8.5, 8.5)" stroke="none" fill="whitesmoke" text-anchor="start" style="font: bold 10pt Helvetica, Arial, sans-serif" y="10.725000000000001" x="0">指南发布</text></g><path transform="matrix(1, 0, 0, 1, 31.16666603088379, 0.5)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 0.5, 11.65)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 61.83333206176758, 11.65)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 31.16666603088379, 22.8)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path></g><g transform="matrix(1, 0, 0, 1, 260.8333339691162, 189.2)"><g transform="matrix(1, 0, 0, 1, 0, 0)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#00A9C9" d="M 42.66666603088379,0 L 0,30.3 L 42.66666603088379,60.6 L 85.33333206176758,30.3 z"></path><text transform="matrix(1, 0, 0, 1, 29.833333015441895, 23.650000000000002)" stroke="none" fill="whitesmoke" text-anchor="start" style="font: bold 10pt Helvetica, Arial, sans-serif" y="10.725000000000001" x="0">判断</text></g><path transform="matrix(1, 0, 0, 1, 39.16666603088379, 0.5)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 0.5, 26.8)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 77.83333206176758, 26.8)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 39.16666603088379, 53.1)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path></g><g transform="matrix(1, 0, 0, 1, 274.91472822011906, 353)"><g transform="matrix(1, 0, 0, 1, 0, 0)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#DC3C00" d="M 28.585271779880973,0 C 44.37248145367778,0 57.170543559761946,12.798062106084167 57.170543559761946,28.585271779880973 C 57.170543559761946,44.37248145367778 44.37248145367778,57.170543559761946 28.585271779880973,57.170543559761946 C 12.798062106084167,57.170543559761946 0,44.37248145367778 0,28.585271779880973 C 0,12.798062106084167 12.798062106084167,0 28.585271779880973,0 z"></path><text transform="matrix(1, 0, 0, 1, 14.418604795322864, 21.549999999999997)" stroke="none" fill="whitesmoke" text-anchor="start" style="font: bold 11pt Helvetica, Arial, sans-serif" y="12.675" x="0">结束</text></g><path transform="matrix(1, 0, 0, 1, 25.08527177988097, 0.5)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 0.5, 26)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 49.67054355976194, 26)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path></g><g transform="matrix(1, 0, 0, 1, 255.5, 278.35)"><g transform="matrix(1, 0, 0, 1, 0, 0)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#00A9C9" d="M 0,0 L 96,0 L 96,30.3 L 0,30.3 z"></path><text transform="matrix(1, 0, 0, 1, 8.5, 8.5)" stroke="none" fill="whitesmoke" text-anchor="start" style="font: bold 10pt Helvetica, Arial, sans-serif" y="10.725000000000001" x="0">申报规则公示</text></g><path transform="matrix(1, 0, 0, 1, 44.5, 0.5)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 0.5, 11.65)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 88.5, 11.65)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path><path transform="matrix(1, 0, 0, 1, 44.5, 22.8)" stroke="none" fill="transparent" d="M 4,0 C 6.209138999323175,0 8,1.7908610006768257 8,4 C 8,6.209138999323175 6.209138999323175,8 4,8 C 1.7908610006768257,8 0,6.209138999323175 0,4 C 0,1.7908610006768257 1.7908610006768257,0 4,0 z"></path></g><g transform="matrix(1, 0, 0, 1, 287.73857625084605, 74)"><path transform="matrix(1, 0, 0, 1, 16.26142374915397, 1)" stroke-miterlimit="10" stroke-linejoin="miter" stroke-linecap="butt" stroke-width="2" stroke="gray" fill="none" d="M 0,0 L 0,39.349999999999994"></path><path transform="matrix(0, 1, -1, 0, 20.26142374915397, 35.849999999999994)" stroke="none" fill="gray" d="M 0,0 L 8,4 L 0,8 L 2,4 z"></path></g><g transform="matrix(1, 0, 0, 1, 287.73857625084605, 148.65)"><path transform="matrix(1, 0, 0, 1, 16.26142374915397, 1)" stroke-miterlimit="10" stroke-linejoin="miter" stroke-linecap="butt" stroke-width="2" stroke="gray" fill="none" d="M 0,0 L 0,35.54999999999998"></path><path transform="matrix(0, 1, -1, 0, 20.26142374915397, 32.04999999999998)" stroke="none" fill="gray" d="M 0,0 L 8,4 L 0,8 L 2,4 z"></path></g><g transform="matrix(1, 0, 0, 1, 287.73857625084605, 249.79999999999998)"><path transform="matrix(1, 0, 0, 1, 16.26142374915397, 1)" stroke-miterlimit="10" stroke-linejoin="miter" stroke-linecap="butt" stroke-width="2" stroke="gray" fill="none" d="M 0,0 L 0,23.55000000000004"></path><path transform="matrix(0, 1, -1, 0, 20.26142374915397, 20.05000000000004)" stroke="none" fill="gray" d="M 0,0 L 8,4 L 0,8 L 2,4 z"></path><g transform="matrix(1, 0, 0, 1, 0, 4.363576250846037)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#F8F8F8" d="M 5,0 L 26.52284749830794,0 C 28.76142374915397,0 31.52284749830794,2.761423749153968 31.52284749830794,5 L 31.52284749830794,14.822847498307937 C 31.52284749830794,17.061423749153967 28.76142374915397,19.822847498307937 26.52284749830794,19.822847498307937 L 5,19.822847498307937 C 2.761423749153968,19.822847498307937 0,17.061423749153967 0,14.822847498307937 L 0,5 C 0,2.761423749153968 2.761423749153968,0 5,0 z"></path><text transform="matrix(1, 0, 0, 1, 3.261423749153968, 3.261423749153967)" stroke="none" fill="#333333" text-anchor="middle" style="font: 10pt helvetica, arial, sans-serif" y="10.725000000000001" x="13">是</text></g></g><g transform="matrix(1, 0, 0, 1, 287.73857625084605, 308.65000000000003)"><path transform="matrix(1, 0, 0, 1, 16.26142374915397, 1)" stroke-miterlimit="10" stroke-linejoin="miter" stroke-linecap="butt" stroke-width="2" stroke="gray" fill="none" d="M 0,0 L 0,39.349999999999966"></path><path transform="matrix(0, 1, -1, 0, 20.26142374915397, 35.849999999999966)" stroke="none" fill="gray" d="M 0,0 L 8,4 L 0,8 L 2,4 z"></path></g><g transform="matrix(1, 0, 0, 1, 332.085271779881, 219)"><path transform="matrix(1, 0, 0, 1, 1, 1)" stroke-miterlimit="10" stroke-linejoin="miter" stroke-linecap="butt" stroke-width="2" stroke="gray" fill="none" d="M 14.081394251002791,0 L 100.914728220119,0 Q 105.914728220119,0 105.914728220119,5 L 105.914728220119,153.5 Q 105.914728220119,155 104.414728220119,155 L 103.664728220119,155 Q 102.914728220119,155 102.914728220119,155.75 L 102.914728220119,159.375 Q 102.914728220119,163 99.289728220119,163 L 4,163"></path><path transform="matrix(-1, 0, 0, -1, 9.5, 168)" stroke="none" fill="gray" d="M 0,0 L 8,4 L 0,8 L 2,4 z"></path><g transform="matrix(1, 0, 0, 1, 90.65330447096503, 68.08857625084603)"><path transform="matrix(1, 0, 0, 1, 0.5, 0.5)" stroke="none" fill="#F8F8F8" d="M 5,0 L 26.52284749830794,0 C 28.76142374915397,0 31.52284749830794,2.761423749153968 31.52284749830794,5 L 31.52284749830794,14.822847498307937 C 31.52284749830794,17.061423749153967 28.76142374915397,19.822847498307937 26.52284749830794,19.822847498307937 L 5,19.822847498307937 C 2.761423749153968,19.822847498307937 0,17.061423749153967 0,14.822847498307937 L 0,5 C 0,2.761423749153968 2.761423749153968,0 5,0 z"></path><text transform="matrix(1, 0, 0, 1, 3.261423749153968, 3.261423749153967)" stroke="none" fill="#333333" text-anchor="middle" style="font: 10pt helvetica, arial, sans-serif" y="10.725000000000001" x="13">否</text></g></g></g></g><clipPath id="mainClip"><rect height="500px" width="300px" y="0" x="0"></rect></clipPath></svg>
	       </div>
	    </div>
	    <div title="变更后流程图" data-options="region:'center'">
	        <script type="text/javascript">
				$(function(){
				    init("_MyFlowDiagram",true,1,"","","",false,nodeClickC,"",2);
				    //myDiagram.div.style.height="500px";
					//myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
				    var span0=document.getElementById("span_0");
					if(typeof(span0)!="undefined"){
						span0.click();
					}
				});
	        </script>
	    	<!-- 嵌套布局开始 -->
		  	<div class="easyui-layout" data-options="fit:true">
		  	  <!-- 权力流程图头部（环节信息）开始 -->
		      <div data-options="region:'north',collapsible:false," title="${busInfo.busName}" split="true" border="false"
				 class="design_titletool" style="height: 85px;">
				 <c:if test="${empty tacheInfoList}">暂无数据</c:if>
				 	<table style="margin-top: 10px;">
	  				 <tr>
					  <c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
						<td style="word-break: break-all; word-wrap:break-word;">
							<c:choose>
							 <c:when test="${status.index==0}">
								<div id="span_${status.index}" onclick="loadFlow('${tacheInfo.tacheCode}','${isFlowChange}','${lastApplyId}','${applyId}','oldImgSvg');"  
									style="top:30px;left:10px;z-index:${status.index*10}; height: 46px;position:absolute;cursor:pointer;" >
								   <div 
									style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;">
											${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
								</div>
							 </c:when>
							 <c:otherwise>
								<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}','${isFlowChange}','${lastApplyId}','${applyId}','oldImgSvg');"  
										style="top:30px;left:${status.index*146+10}px;z-index:${status.index*10}; height: 56px;position:absolute;cursor:pointer;" >
								<div
										style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 46px;left:25px;width:126px;top:11px;white-space:normal;;word-break: break-all; word-wrap:break-word;">
										${tacheInfo.tacheName}
								</div>
								<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_go.png" style="vertical-align:middle;height: 46px;width:167px;top:0px;left:0px;"/>
								</div>
							 </c:otherwise>
						 </c:choose>
						</td>
					</c:forEach> 
				  </tr>
				</table>
				<!--<%-- <div id="courseDiv" style="margin:9px 0 0 20px;text-align: left;" >
					<c:if test="${empty tacheInfoList}">暂无数据</c:if>
					<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
					 <span style="cursor: pointer;width:auto; font-size: 13px;font-weight: bold;" id="span_${status.index}" 
					 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='#d8e4fe';"
					 onclick="loadFlow('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
					 <span style="">&clubs;></span>  <sapn>&nbsp;</span>
					</c:forEach>
				</div> --%>-->	
			  </div>			
			  <!-- 权力流程图头部（环节信息）结束 -->
			  <!-- 权力流程图头详细页面开始 -->
			   <div data-options="region:'center'" split="true" border="false">
						<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
				        <div id="_MyFlowDiagram" style="height: 720px"></div>
				        <textarea id="mySavedModel" style="width:100%;height:0px;visibility:hidden;" >
				{ "class": "go.GraphLinksModel",
				  "linkFromPortIdProperty": "fromPort",
				  "linkToPortIdProperty": "toPort",
				  "nodeDataArray": [ 
				  ],
				  "linkDataArray": [  ]}
				  </textarea>
				     <textarea id="mySavedModelBak" style="width:100%;height:0px;visibility: hidden;" >
				{ "class": "go.GraphLinksModel",
				  "linkFromPortIdProperty": "fromPort",
				  "linkToPortIdProperty": "toPort",
				  "nodeDataArray": [
				  {"key":-1, "category":"start", "loc":"520 50", "text":"开始"}
				 ],
				  "linkDataArray": [
				 ]}
				  </textarea>
		      </div>
		      <!-- 权力流程图头详细页面结束 -->
			</div>
		    <!-- 嵌套布局结束 -->
    	</div>
      </c:if>
      <!-- 业务流程图变更结束 -->
      <!-- 以下是监察字段/监察要素/监察规则相关的变更开始 -->
      <c:if test="${flowFlag eq 2}">
      	<div title="${busInfo.busName}" data-options="region:'west'" width="60%" split="false">
	        <script type="text/javascript">
				$(function(){
				    init("_MyFlowDiagram",true,1,"","","",false,nodeClickC,"",2);
				    //myDiagram.div.style.height="500px";
					//myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
				    var span0=document.getElementById("span_0");
					if(typeof(span0)!="undefined"){
						span0.click();
					}
				});
	        </script>
	    	<!-- 嵌套布局开始 -->
		  	<div class="easyui-layout" data-options="fit:true">
		  	  <!-- 权力流程图头部（环节信息）开始 -->
		      <div data-options="region:'north',collapsible:false" split="true" border="false"
				 class="design_titletool" height="75px">
				 <c:if test="${empty tacheInfoList}">暂无数据</c:if>
				 	<table style="margin-top: 5px;">
	  				 <tr>
					  <c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
						<td style="word-break: break-all; word-wrap:break-word;">
							<c:choose>
							 <c:when test="${status.index==0}">
								<div id="span_${status.index}" onclick="loadFlow('${tacheInfo.tacheCode}','${isFlowChange}','${lastApplyId}','${applyId}','oldImgSvg');"  
									style="top:15px;left:10px;z-index:${status.index*10}; height: 46px;position:absolute;cursor:pointer;" >
								   <div 
									style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;">
											${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
								</div>
							 </c:when>
							 <c:otherwise>
								<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}','${isFlowChange}','${lastApplyId}','${applyId}','oldImgSvg');"  
										style="top:15px;left:${status.index*146+10}px;z-index:${status.index*10}; height: 56px;position:absolute;cursor:pointer;" >
								<div
										style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 46px;left:25px;width:126px;top:11px;white-space:normal;;word-break: break-all; word-wrap:break-word;">
										${tacheInfo.tacheName}
								</div>
								<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_go.png" style="vertical-align:middle;height: 46px;width:167px;top:0px;left:0px;"/>
								</div>
							 </c:otherwise>
						 </c:choose>
						</td>
					</c:forEach> 
				  </tr>
				</table>
			  </div>			
			  <!-- 权力流程图头部（环节信息）结束 -->
			  <!-- 权力流程图头详细页面开始 -->
			   <div data-options="region:'center'" border="false">
						<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
				        <div id="_MyFlowDiagram" style="height: 720px"></div>
				        <textarea id="mySavedModel" style="width:100%;height:0px;visibility:hidden;" >
				{ "class": "go.GraphLinksModel",
				  "linkFromPortIdProperty": "fromPort",
				  "linkToPortIdProperty": "toPort",
				  "nodeDataArray": [ 
				  ],
				  "linkDataArray": [  ]}
				  </textarea>
				     <textarea id="mySavedModelBak" style="width:100%;height:0px;visibility: hidden;" >
				{ "class": "go.GraphLinksModel",
				  "linkFromPortIdProperty": "fromPort",
				  "linkToPortIdProperty": "toPort",
				  "nodeDataArray": [
				  {"key":-1, "category":"start", "loc":"520 50", "text":"开始"}
				 ],
				  "linkDataArray": [
				 ]}
				  </textarea>
		      </div>
		      <!-- 权力流程图头详细页面结束 -->
			</div>
		    <!-- 嵌套布局结束 -->
    	</div>
    	<div data-options="region:'center'">
	       <!-- 嵌套南北布局开始 -->
	       <div class="easyui-layout" data-options="fit:true">
	         <div title="【变更前】数据" data-options="region:'south',collapsible:true" height="50%">
	            <!-- 监察字段配置开始 -->
	            <c:if test="${columnFlag eq 1}">
	            	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" id="SuperColumnDataGrid"
					  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="busColumnBasicController.do?materialsdatagrid&columnType=2&processCode=${defaultprocescode}&appliyId=${lastApplyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
					     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
					     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
					     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
					     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
					    </tr> 
					   </thead> 
					 </table>
	            </c:if>
	            <!-- 监察字段配置结束 -->
	            <!-- 监察要素开始 -->
	         	<c:if test="${elementFlag eq 1}">
	         	  <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
			  		method="post" id="SuperElementsRulesGrid"
			  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
			  		fit="true" border="false" nowrap="false"
			  		url="allMaterialsController.do?RuleDatagrid&processCode=${defaultprocescode}&appliyId=${lastApplyId}"> 
				   <thead> 
				    <tr> 
				     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
				     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
				     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
				    </tr> 
				   </thead> 
				 </table>
	           </c:if>
	           <!-- 监察要素结束 -->
	           <!-- 监察规则开始 -->
	           <c:if test="${rulerFlag eq 1}">
	                <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
				  		method="post" id="SuperElementsRulesGrid"
				  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
				  		fit="true" border="false" nowrap="false"
				  		url="allMaterialsController.do?RuleDatagrid&processCode=${defaultprocescode}&appliyId=${lastApplyId}"> 
				   <thead> 
				    <tr> 
				     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
				     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
				     <th data-options="field:'JUDGE_DESC',align:'left'"  width="100">监察规则</th> 
				     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
				    </tr> 
				   </thead> 
				 </table>
	           </c:if>
	           <!-- 监察规则结束 -->
	         </div>
	       	 <div title="【变更后】数据" data-options="region:'center'" border="false">
	       	    <!-- 监察字段配置开始 -->
	            <c:if test="${columnFlag eq 1}">
	            	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" id="SuperColumnChangeDataGrid"
					  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="busColumnBasicController.do?materialsChangedatagrid&columnType=2&processCode=${defaultprocescode}&appliyId=${applyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
					     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
					     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
					     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
					     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
					    </tr> 
					   </thead> 
					 </table>
	            </c:if>
	            <!-- 监察字段配置结束 -->
	       	    <!-- 监察要素开始 -->
	       	 	<c:if test="${elementFlag eq 1}">
	         	  <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
			  		method="post" id="SuperElementsChangeRulesGrid"
			  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
			  		fit="true" border="false" nowrap="false"
			  		url="allMaterialsController.do?RuleChangeDatagrid&processCode=${defaultprocescode}&appliyId=${applyId}"> 
				   <thead> 
				    <tr> 
				     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
				     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
				     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
				    </tr> 
				   </thead> 
				 </table>
	           </c:if>
	           <!-- 监察要素结束 -->
	           <!-- 监察规则开始 -->
	           <c:if test="${rulerFlag eq 1}">
	                <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
				  		method="post" id="SuperElementsChangeRulesGrid"
				  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
				  		fit="true" border="false" nowrap="false"
				  		url="allMaterialsController.do?RuleChangeDatagrid&processCode=${defaultprocescode}&appliyId=${applyId}"> 
				   <thead> 
				    <tr> 
				     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
				     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
				     <th data-options="field:'JUDGE_DESC',align:'left'"  width="100">监察规则</th> 
				     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
				    </tr> 
				   </thead> 
				 </table>
	           </c:if>
	           <!-- 监察规则结束 -->
	       	 </div>
	       </div>
	       <!-- 嵌套南北布局结束 -->
	    </div>
      </c:if>
      <!-- 以下是监察字段/监察要素/监察规则相关的变更结束 -->
    </c:if>
     <!-- 针对单一要素的变更结束 -->
     <!-- ====================分割线========================= -->
     <!-- 针对多个要素的变更开始-->
     <c:if test="${pageType eq 2}">
     	<div title="${busInfo.busName}" data-options="region:'west'" width="60%" split="true">
	        <script type="text/javascript">
				$(function(){
				    init("_MyFlowDiagram",true,1,"","","",false,nodeClickC,"",2);
				    //myDiagram.div.style.height="500px";
					//myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
				    var span0=document.getElementById("span_0");
					if(typeof(span0)!="undefined"){
						span0.click();
					}
				});
	        </script>
	    	<!-- 嵌套布局开始 -->
		  	<div class="easyui-layout" data-options="fit:true">
		  	  <!-- 权力流程图头部（环节信息）开始 -->
		      <div data-options="region:'north',collapsible:false" split="true" border="false"
				 class="design_titletool" height="75px">
				 <c:if test="${empty tacheInfoList}">暂无数据</c:if>
				 	<table style="margin-top: 5px;">
	  				 <tr>
					  <c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
						<td style="word-break: break-all; word-wrap:break-word;">
							<c:choose>
							 <c:when test="${status.index==0}">
								<div id="span_${status.index}" onclick="loadFlow('${tacheInfo.tacheCode}','${isFlowChange}','${lastApplyId}','${applyId}','oldImgSvg');"  
									style="top:15px;left:10px;z-index:${status.index*10}; height: 46px;position:absolute;cursor:pointer;" >
								   <div 
									style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;">
											${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
								</div>
							 </c:when>
							 <c:otherwise>
								<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}','${isFlowChange}','${lastApplyId}','${applyId}','oldImgSvg');"  
										style="top:15px;left:${status.index*146+10}px;z-index:${status.index*10}; height: 56px;position:absolute;cursor:pointer;" >
								<div
										style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 46px;left:25px;width:126px;top:11px;white-space:normal;;word-break: break-all; word-wrap:break-word;">
										${tacheInfo.tacheName}
								</div>
								<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_go.png" style="vertical-align:middle;height: 46px;width:167px;top:0px;left:0px;"/>
								</div>
							 </c:otherwise>
						 </c:choose>
						</td>
					</c:forEach> 
				  </tr>
				</table>
			  </div>			
			  <!-- 权力流程图头部（环节信息）结束 -->
			  <!-- 权力流程图头详细页面开始 -->
			   <div data-options="region:'center'" border="false">
						<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
				        <div id="_MyFlowDiagram" style="height: 720px"></div>
				        <textarea id="mySavedModel" style="width:100%;height:0px;visibility:hidden;" >
				{ "class": "go.GraphLinksModel",
				  "linkFromPortIdProperty": "fromPort",
				  "linkToPortIdProperty": "toPort",
				  "nodeDataArray": [ 
				  ],
				  "linkDataArray": [  ]}
				  </textarea>
				     <textarea id="mySavedModelBak" style="width:100%;height:0px;visibility: hidden;" >
				{ "class": "go.GraphLinksModel",
				  "linkFromPortIdProperty": "fromPort",
				  "linkToPortIdProperty": "toPort",
				  "nodeDataArray": [
				  {"key":-1, "category":"start", "loc":"520 50", "text":"开始"}
				 ],
				  "linkDataArray": [
				 ]}
				  </textarea>
		      </div>
		      <!-- 权力流程图头详细页面结束 -->
			</div>
		    <!-- 嵌套布局结束 -->
    	</div>
    	<div data-options="region:'center'" border="false">
    	 <!-- 抽屉布局开始 -->
    	 <div class="easyui-accordion" data-options="fit:true,border:true">
    	   <!-- 基本信息字段开始 -->
    	   <c:if test="${basicFlag eq 1}">
    	 	<div title="基本信息及字段" data-options="selected:true" style="padding:5px;">
   	 		 <div class="easyui-layout" data-options="fit:true" border="false">
   	 		 	<div title="变更后" data-options="region:'center'">
	   	 		  <c:if test="${fn:length(sysList) > 1 }">
		             <div class="easyui-tabs eve-tabs" fit="true">
		               <c:forEach items="${sysList}" var="sys">
		             	<div title="${sys.SYSTEM_NAME}">
		             		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
							  		method="post" 
							  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
							  		fit="true" border="false" nowrap="false"
							  		url="busColumnBasicController.do?materialsChangedatagrid&columnType=1&processCode=${buscode}&platCode=${sys.SYSTEM_CODE}&appliyId=${applyId}"> 
							   <thead> 
							    <tr> 
							     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
							     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
							     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
							     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
							     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
							    </tr> 
							   </thead> 
							 </table>
		             	</div> 
		              </c:forEach>            	
		             </div>
	             </c:if>
	             <c:if test="${fn:length(sysList) eq 1 }">
	             	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" 
					  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="busColumnBasicController.do?materialsChangedatagrid&columnType=1&processCode=${buscode}&platCode=${sysList[0].SYSTEM_CODE}&appliyId=${applyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
					     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
					     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
					     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
					     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
					    </tr> 
					   </thead> 
					 </table>
	             </c:if> 
   	 		 	</div>
   	 		 	<div title="变更前" data-options="region:'north',collapsible:true" border="true" split="true" height="50%">
   	 		 		<c:if test="${fn:length(sysLastList) > 1 }">
			             <div class="easyui-tabs eve-tabs" fit="true">
			                <c:forEach items="${sysLastList}" var="sys">
			             	<div title="${sys.SYSTEM_NAME}">
			             		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
								  		method="post" 
								  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
								  		fit="true" border="false" nowrap="false"
								  		url="busColumnBasicController.do?materialsdatagrid&columnType=1&processCode=${buscode}&platCode=${sys.SYSTEM_CODE}&appliyId=${lastApplyId}"> 
								   <thead> 
								    <tr> 
								     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
								     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
								     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
								     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
								     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
								    </tr> 
								   </thead> 
								 </table>
			             	</div> 
			             	</c:forEach>            	
			             </div>
		             </c:if>
		             <c:if test="${fn:length(sysLastList) eq 1 }">
		             	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
						  		method="post" 
						  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
						  		fit="true" border="false" nowrap="false"
						  		url="busColumnBasicController.do?materialsdatagrid&columnType=1&processCode=${buscode}&platCode=${sysLastList[0].SYSTEM_CODE}&appliyId=${lastApplyId}"> 
						   <thead> 
						    <tr> 
						     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
						     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
						     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
						     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
						     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
						    </tr> 
						   </thead> 
						 </table>
		             </c:if>
   	 		 	</div>
   	 		 </div>
    	 	</div>
    	  </c:if>
    	  <!-- 基本信息字段结束 -->
    	  <!-- 权力流程图（变更前）开始 -->
    	  <c:if test="${isFlowChange eq true}">
    	  	<div title="权力流程图（变更前）" style="padding:5px;">
    	  	   	<div id="oldImgSvg" style="text-align: center" >流程图</div>
    	  	</div>
    	  </c:if>
    	  <!-- 权力流程图（变更前）结束 -->
    	  <!-- 监察字段开始 -->
    	  <c:if test="${columnFlag eq 1}">
    	 	<div title="监察字段" style="padding:5px;">
    	 	  <div class="easyui-layout" data-options="fit:true" border="false">
   	 		 	<div title="变更后" data-options="region:'center'">
	   	 		  <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" id="SuperColumnChangeDataGrid"
					  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="busColumnBasicController.do?materialsChangedatagrid&columnType=2&processCode=${defaultprocescode}&appliyId=${applyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
					     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
					     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
					     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
					     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
					    </tr> 
					   </thead> 
					 </table>
   	 		 	</div>
   	 		 	<div title="变更前" data-options="region:'north',collapsible:true" border="true" split="true" height="50%">
   	 		 	  <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" id="SuperColumnDataGrid"
					  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="busColumnBasicController.do?materialsdatagrid&columnType=2&processCode=${defaultprocescode}&appliyId=${lastApplyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'SERIAL_ID',hidden:true" width="80">SERIAL_ID</th> 
					     <th data-options="field:'COLUMN_NAME',align:'left'" width="200">字段名称</th> 
					     <th data-options="field:'FIELD_TYPE',align:'center'" formatter="fieldTypeFormatter" width="150">字段类型</th> 
					     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
					     <th data-options="field:'IS_SHOW',align:'center'" formatter="isShowFormatter" width="100">是否显示</th> 
					    </tr> 
					   </thead> 
					 </table>
   	 		 	</div>
	   	 	  </div>
    	 	</div>
    	 </c:if>
    	 <!-- 监察字段结束 -->
    	 <!-- 监察要素开始 -->
    	 <c:if test="${elementFlag eq 1}">
    	 	<div title="监察要素" style="padding:5px;">
    	 	  <div class="easyui-layout" data-options="fit:true" border="false">
   	 		 	<div title="变更后" data-options="region:'center'">
	  	 		 	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
				  		method="post" id="SuperElementsChangeRulesGrid"
				  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
				  		fit="true" border="false" nowrap="false"
				  		url="allMaterialsController.do?RuleChangeDatagrid&processCode=${defaultprocescode}&appliyId=${applyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
					     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
					     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
					    </tr> 
					   </thead> 
					 </table>
   	 		 	</div>
   	 		 	<div title="变更前" data-options="region:'north',collapsible:true" border="true" split="true" height="50%">
   	 		 		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
				  		method="post" id="SuperElementsRulesGrid"
				  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
				  		fit="true" border="false" nowrap="false"
				  		url="allMaterialsController.do?RuleDatagrid&processCode=${defaultprocescode}&appliyId=${lastApplyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
					     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
					     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
					    </tr> 
					   </thead> 
					 </table>
   	 		 	</div>
	   	 	  </div>
    	 	</div>
    	  </c:if>
    	  <!-- 监察要素结束 -->
    	  <!-- 监察规则开始 -->
    	  <c:if test="${rulerFlag eq 1}">
    	 	<div title="监察规则" style="padding:5px;">
    	 	  <div class="easyui-layout" data-options="fit:true" border="false">
   	 		 	<div title="变更后" data-options="region:'center'">
   	 		 	      <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" id="SuperOnlyChangeRulesGrid"
					  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="allMaterialsController.do?RuleChangeDatagrid&processCode=${defaultprocescode}&appliyId=${applyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
					     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
					     <th data-options="field:'JUDGE_DESC',align:'left'"  width="100">监察规则</th> 
					     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
					    </tr> 
					   </thead> 
					 </table>
   	 		 	</div>
   	 		 	<div title="变更前" data-options="region:'north',collapsible:false" border="true" split="true" height="50%">
   	 		 		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" id="SuperOnlyRulesGrid"
					  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" nowrap="false"
					  		url="allMaterialsController.do?RuleDatagrid&processCode=${defaultprocescode}&appliyId=${lastApplyId}"> 
					   <thead> 
					    <tr> 
					     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
					     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
					     <th data-options="field:'JUDGE_DESC',align:'left'"  width="100">监察规则</th> 
					     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
					    </tr> 
					   </thead> 
					 </table>
   	 		 	</div>
	   	 	  </div>
    	 	</div>
    	  </c:if>
    	  <!-- 监察规则结束 -->
    	 </div>
    	 <!-- 抽屉布局结束 -->
    	</div>
     </c:if>
     <!-- 针对多个要素的变更结束-->
  </div>
  <!-- 主体页面编写结束 -->
</body>
</html>
