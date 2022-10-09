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
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowview.js"></script>
<!-- 权力流程图相关脚本结束 -->
<!-- 审核相关脚本开始 -->	
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<!-- 审核相关脚本结束 -->
<script type="text/javascript"	src="<%=basePath%>/plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript"	src="<%=basePath%>/plug-in/urlparser/URI.js"></script>

<!-- 脚本编写开始 -->	
<script id="code" type="text/javascript">

function FLOW_SubmitFun(flowSubmitObj){
	//把意见回填给提交页面
	if($("#EFLOW_HANDLE_OPINION").val()){
		flowSubmitObj.EFLOW_HANDLE_OPINION = $("#EFLOW_HANDLE_OPINION").val();
	}	
	return flowSubmitObj;
}

function FLOW_TempSaveFun(flowSubmitObj){
		 return flowSubmitObj;
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}
    /**
    * 人员类别
    */
	function formatContactType(val,row){
		//0：业务处室，1:信息科室，2：系统开发商或负责人，3：经办，4：对接技术人员
		if(row.CONTACT_TYPE=="0"){
			return "<font color='blue'><b>业务处室</b></font>";
		}else if(row.CONTACT_TYPE=="1"){
			return "<font color='blue'><b>信息科室</b></font>";
		}else if(row.CONTACT_TYPE=="2"){
			return "<font color='blue'><b>系统开发商或负责人</b></font>";
		}else if(row.CONTACT_TYPE=="3"){
			return "<font color='blue'><b>经办</b></font>";
		}else if(row.CONTACT_TYPE=="4"){
			return "<font color='blue'><b>对接技术人员</b></font>";
		}
	};
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


$(function(){
    init("_MyFlowDiagram",true,1,"","","",false,nodeClickC,"",2);
    //myDiagram.div.style.height="500px";
	//myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
    var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}
	
	//为layout中的某个面板绑定事件
	/* $("#firstPanel").layout("panel","south").onCollapse(function(){
		alert("ddddddd");
	}); */
	
	var task_id = $("#task_id").val();
	if(task_id == 'null'){
		$("#firstPanel").layout("collapse","south");  
	}
}); 

//点击事件
function nodeClickC(data){
	var ndata=findJsonDataForKey(data.key);
	//alert(ndata.id);
	//刷新监察字段列表数据
	if(document.getElementById("SuperColumnDataGrid")){
		reloadRightDataGrid("SuperColumnDataGrid",ndata.id,"materialsdatagrid");
	}
	//刷新监察要素及规则
    if(document.getElementById("SuperElementsRulesGrid")){
    	reloadRightDataGrid("SuperElementsRulesGrid",ndata.id,"RuleDatagrid");
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
/**====权力流程图部分结束======*/	
</script>	
<!-- 脚本编写结束 -->
</head>
<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout bodySelectNone">
  <!-- ===页面隐藏域开始=== -->
  <input type="hidden" name="task_id" id="task_id" value="${taskId}"/>
  <input type="hidden" name="bus_unitcode" value="${busInfo.unitCode}" />
  <input type="hidden" name="bus_specialCode" value="${busInfo.busCode}" />
  <!-- ===页面隐藏域结束=== -->
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
    <!-- 开始编写右侧数据侧边栏 -->
	<div title="各项梳理材料" class="right-con" data-options="region:'east',collapsible:true" width="35%" split="true" border="false" >
	   <!-- 开始编写抽屉布局 -->
	   <div class="easyui-accordion" data-options="fit:true,border:true">
	      <!-- 开始编写基本信息 -->
	      <c:if test="${applyType eq 1 && applyStage eq 2}">
          <div title="基本信息及字段" data-options="selected:true" style="padding:5px;">
             <c:if test="${fn:length(sysList) > 1 }">
	             <div class="easyui-tabs eve-tabs" fit="true">
	                <c:forEach items="${sysList}" var="sys">
	             	<div title="${sys.SYSTEM_NAME}">
	             		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
						  		method="post" 
						  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
						  		fit="true" border="false" nowrap="false"
						  		url="busColumnBasicController.do?materialsdatagrid&columnType=1&processCode=${busInfo.busCode}&platCode=${sys.SYSTEM_CODE}&appliyId=${applyId}"> 
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
				  		url="busColumnBasicController.do?materialsdatagrid&columnType=1&processCode=${busInfo.busCode}&platCode=${sysList[0].SYSTEM_CODE}&appliyId=${applyId}"> 
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
          <!-- 结束编写基本信息 -->
          <!-- 开始编写监察字段 -->
          <div title="监察字段" style="padding:5px">
             <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
			  		method="post" id="SuperColumnDataGrid"
			  		idfield="SERIAL_ID" checkonselect="false" selectoncheck="false" 
			  		fit="true" border="false" nowrap="false"
			  		url="busColumnBasicController.do?materialsdatagrid&columnType=2&processCode=${defaultprocescode}&appliyId=${applyId}"> 
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
          </c:if>
          <!-- 结束编写监察字段 -->
          <!-- 开始编写要素及监察规则 -->
          <c:if test="${applyType eq 1 && applyStage eq 1}">
          <div title="监察要素" style="padding:5px">
              <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
			  		method="post" id="SuperElementsRulesGrid"
			  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
			  		fit="true" border="false" nowrap="false"
			  		url="allMaterialsController.do?RuleDatagrid&processCode=${defaultprocescode}&appliyId=${applyId}"> 
			   <thead> 
			    <tr> 
			     <th data-options="field:'RULE_ID',hidden:true" width="80">ID</th> 
			     <th data-options="field:'SUPER_ELEMENTS',align:'left'"  width="100">监察要素</th> 
			     <th data-options="field:'RULE_DESC',align:'left'"  width="150">描述</th> 
			    </tr> 
			   </thead> 
			 </table>
          </div>
          </c:if>
          <c:if test="${applyType eq 1 && applyStage eq 2}">
          <div title="监察要素及规则" style="padding:5px">
              <table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
			  		method="post" id="SuperElementsRulesGrid"
			  		idfield="RULE_ID" checkonselect="false" selectoncheck="false" 
			  		fit="true" border="false" nowrap="false"
			  		url="allMaterialsController.do?RuleDatagrid&processCode=${defaultprocescode}&appliyId=${applyId}"> 
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
          </c:if>
          <!-- 结束编写要素及监察规则 -->
          <!-- 开始编写联系人 -->
          <!--  <%--
          <div title="联系人" style="padding:5px">
          	  <c:if test="${fn:length(sysList) > 1 }">
	             <div class="easyui-tabs eve-tabs" fit="true">
	                <c:forEach items="${sysList}" var="sys">
	             	<div title="${sys.SYSTEM_NAME}">
	             		<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
					  		method="post" idfield="ID" 
					  		checkonselect="false" selectoncheck="false" 
					  		fit="true" border="false" 
					  		url="busContactController.do?datagrid"> 
					   <thead> 
					    <tr>
							<th data-options="field:'ID',hidden:true" width="80">ID</th>
							<th data-options="field:'CONTACT_TYPE',align:'center'" width="120" formatter="formatContactType">人员类型</th>
							<th data-options="field:'CONTACT_NAME',align:'left'" width="75">姓名</th>
							<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
							<th data-options="field:'CONTACT_MOBILE',align:'left'" width="150">手机号码</th>
							<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>						
						</tr>
					   </thead> 
					  </table>
	             	</div> 
	             	</c:forEach>            	
	             </div>
             </c:if>
             <c:if test="${fn:length(sysList) eq 1 }">
             	<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" 
			  		method="post" idfield="ID" 
			  		checkonselect="false" selectoncheck="false" 
			  		fit="true" border="false" 
			  		url="busContactController.do?datagrid"> 
			   <thead> 
			    <tr>
					<th data-options="field:'ID',hidden:true" width="80">ID</th>
					<th data-options="field:'CONTACT_TYPE',align:'center'" width="120" formatter="formatContactType">人员类型</th>
					<th data-options="field:'CONTACT_NAME',align:'left'" width="75">姓名</th>
					<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
					<th data-options="field:'CONTACT_MOBILE',align:'left'" width="150">手机号码</th>
					<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>						
				</tr>
			   </thead> 
			  </table>
             </c:if>
          </div>
          --%> -->
           <!-- 结束编写联系人 -->
           <!-- 开始编写部署图 -->
           <!--  <%--
          <div title="部署图" style="padding:5px">
              <table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<c:forEach items="${sysDeploys}" var="deploy">
					<tr style="height:29px;">
						<td colspan="1" class="dddl-legend" style="font-weight:bold;">${deploy.BUSSYS_PLATNAME}-部署网络拓扑</td>
					</tr>
					<tr>
						<td colspan="1"><span style="width: 100px;float:left;text-align:right;">对接方式：</span>
							<input type="text" style="width:300px;float:left;" maxlength="5"
							class="easyui-combobox validate[required]" value="${deploy.DEPLOY_TYPE}"
							name="DEPLOY_TYPE"
							data-options="url:'dictionaryController.do?load&typeCode=DEPLOYTYPE',
												editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
												panelWidth: 300,panelHeight: 'auto'" 
							/>
							<font class="dddl_platform_html_requiredFlag">*</font>
					    </td>					
					</tr>
					<tr>
						<td align="center">
							<div style="width:200px;  margin:0px auto;">
								<div class="divcss5">
									<c:if test="${deploy.DEPLOY_FILEPIC == null}">						    
										<img id="IMAGE_PATH_IMG" style="border:none; max-width:100%; max-height:100%;" src="webpage/flow/deploy/images/u5.png"/>
									</c:if>
									<c:if test="${deploy.DEPLOY_FILEPIC != null}">
										<img id="IMAGE_PATH_IMG" style="border:none; max-width:100%; max-height:100%;" src="${deploy.DEPLOY_FILEPIC}"/>
									</c:if>
								 </div>
							 </div>
						</td>					
					</tr>
				</c:forEach>
			</table>
          </div>
          --%> -->
          <!-- 开始编写业务估算 -->
          <!--  <%--
          <div title="业务估算" style="padding:5px">
              <table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
			    <c:forEach items="${sysEstimates}" var="estimate">
					<tr style="height:29px;">
						<td colspan="2" class="dddl-legend" style="font-weight:bold;">${estimate.ESTIMATE_PLATNAME}-估算信息</td>
					</tr>
					<tr>
						<td><span style="width: 130px;float:left;text-align:right;">面向个人总数：</span>
							<input type="text" style="width:50px;float:left;" maxlength="5"
							class="eve-input validate[required],custom[number]" value="${estimate.ESTIMATE_PERSONNUM}"
							name="ESTIMATE_PERSONNUM" />
							<font class="dddl_platform_html_requiredFlag">*</font>
						</td>
						<td><span style="width: 130px;float:left;text-align:right;">面向单位总数：</span>
							<input style="width:50px;float:left;" maxlength="5"
							class="eve-input validate[required],custom[number]"
							value="${estimate.ESTIMATE_DEPTNUM}" name="ESTIMATE_DEPTNUM"/>
							<font class="dddl_platform_html_requiredFlag">*</font>
					    </td>
					</tr>
					<tr>
						<td><span style="width: 130px;float:left;text-align:right;">年业务总数量：</span>
							<input type="text" style="width:50px;float:left;" maxlength="5"
							class="eve-input validate[required],custom[number]" value="${estimate.ESTIMATE_YEARNUM}"
							name="ESTIMATE_YEARNUM" />
							<font class="dddl_platform_html_requiredFlag">*</font>
					    </td>
						<td><span style="width: 130px;float:left;text-align:right;">年业务数据量大小：</span>
							<input type="text" style="width:50px;float:left;" maxlength="5"
							class="eve-input validate[required],custom[number]"
							value="${estimate.ESTIMATE_YEARDATASIZE}" />G<font
							class="dddl_platform_html_requiredFlag">*</font></td>
					</tr>				
					<tr>
						<td><span style="width: 130px;float:left;text-align:right;">非结构化数据量大小：</span>
							<input type="text" style="width:50px;float:left;" maxlength="5"
							class="eve-input validate[required],custom[number]"
							value="${estimate.ESTIMATE_UNSTRUCTSIZE}" name="ESTIMATE_UNSTRUCTSIZE" />G<font
							class="dddl_platform_html_requiredFlag">*</font>
						</td>
					</tr>
				</c:forEach>
			</table>
          </div>
          --%> -->
          <!--结束编写业务估算 -->
        </div>
        <!-- 结束编写抽屉布局 -->
	</div>
	<!-- 结束编写右侧数据侧边栏 -->
    <div data-options="region:'center'">
      <!-- 嵌套布局开始 -->
	  	<div class="easyui-layout" data-options="fit:true">
	  	  <!-- 权力流程图头部（环节信息）开始 -->
	      <div data-options="region:'north',collapsible:false"  class="right-con" title="专项名称：${busInfo.busName}" split="true" border="false"
			 class="design_titletool" style="height: 85px;">
			 <c:if test="${empty tacheInfoList}">暂无数据</c:if>
			 	<table style="margin-top: 10px;">
  				 <tr>
				  <c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
					<td style="word-break: break-all; word-wrap:break-word;">
						<c:choose>
						 <c:when test="${status.index==0}">
							<div id="span_${status.index}" onclick="loadFlow('${tacheInfo.tacheCode}');"  
								style="top:30px;left:10px;z-index:${status.index*10}; height: 46px;position:absolute;cursor:pointer;" >
							   <div 
								style="position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;">
										${tacheInfo.tacheName}
								</div>
								<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
							</div>
						 </c:when>
						 <c:otherwise>
							<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}');"  
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
</body>
</html>
