<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${busSpecial.BUS_NAME }</title>
<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog"></eve:resources>
<!-- 相关样式css -->
<link type="text/css" rel="stylesheet" href="<%=path%>/webpage/common/css/common.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plug-in/easyui-1.4/themes/bootstrap/easyui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plug-in/easyui-1.4/themes/icon.css"/>	
<!-- 权力流程图相关脚本-->
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/gojs.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flow/allmaterials/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flow/allmaterials/js/flowview.js"></script>
<!-- 其他通用类脚本-->
<script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/urlparser/URI.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flow/busSpecial/BusExpSpecial.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flow/busSpecial/saveAsPng.js"></script>
<style type="text/css">
	.txt-five {
    position: relative;
    left: 0;
    bottom: 0px;
    width: 98%;
    height: 45px;
    line-height: 45px;
    text-align: center;
    color: #fff;
    background: rgba(0,0,0,0.3);
}
</style>
<script type="text/javascript">
	function formatFieldType(val,row,index){
		if(val == "0"){
			return "默认";
		}else{
			return "时间";
		}
	}
	function formatAr(value,row,index){
		if (row.IS_ARTIFICIAL==0){
			return "<b><font color=green>自动监察</font></b>";
		} else if(row.IS_ARTIFICIAL==1){
			return "<b><font color=blue>人工监察</font></b>";
		}else{
			return "<b><font ></font></b>";
		}
	}
	function formatAn(value,row,index){
		if (row.ANALYSIS_TYPE==1){
			return "<b><font >时限监察</font></b>";
		} else if(row.ANALYSIS_TYPE==2){
			return "<b><font >内容监察</font></b>";
		}else if(row.ANALYSIS_TYPE==3){
			return "<b><font >流程监察</font></b>";
		}else if(row.ANALYSIS_TYPE==4){
			return "<b><font >收费监察</font></b>";
		}else{
			return "<b><font ></font></b>";
		}
	}
	function downloadSvg(divId,fileName){
		saveSvgAsPng(document.getElementById(divId).getElementsByTagName("svg")[0], fileName+".png");
	}
	function expSpecialGridRecord(busCode){
		location.href ="busSpecialController.do?expExcel&Q_T.BUS_CODE_EQ="+busCode+"&limit=10000";
	}
		
</script>

</head>
<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout bodySelectNone">
  <!-- ===页面隐藏域开始=== -->
  <input type="hidden" name="UNIT_CODE" value="${busSpecial.UNIT_CODE}" />
  <!-- ===页面隐藏域结束=== -->
  
  <!-- 主体页面编写开始 -->
  <div class="easyui-layout" fit="true"> 
  	
  	<div title="【${busSpecial.BUS_NAME }】流程图" data-options="region:'center'" width="98%" split="false">
  		<c:forEach items="${busTacheList}" var="busTache" varStatus="status">
  			<div id="imgSvg_${status.index}" style="margin:5px;border:1px solid #A8A8A8;text-align:center;display:block;float:left;height:auto; ">
  				<span class="txt-five" style="width:100%;font-size:18px;display:inline-block;color:#fffff;">${busTache.TACHE_NAME }
  				<a href="javascript:void;" style="float: right;" onclick="downloadSvg('imgSvg_${status.index}','${busTache.TACHE_NAME}')" class="easyui-linkbutton" data-options="iconCls:'icon-save'">下载</a></span><br />
  				<c:set var="str" value="${'mainClip'}${status.index}"></c:set> 
  				<c:set var="str" value="${fn:replace(busTache.FLOW_SVG,'mainClip',str) }"></c:set>
  				<c:set var="str" value="${fn:replace(str,'border: 1px solid black;','')}"></c:set>   
  				
  				${str }
  				
  			</div>
  		</c:forEach>
  		
	  	<div title="【${busSpecial.BUS_NAME }】相关信息"  width="98%" split="false">
		  	<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
		  		<tr style="height:35px;">
					<td colspan="2" class="" style="font-weight:bold;text-align:center;">
						<a href="#" style="margin:0 auto;"
								class="easyui-linkbutton" data-options="iconCls:'icon-save'"
								onclick="expSpecialGridRecord('${busSpecial.BUS_CODE}');">导出专项</a>
					</td>
				</tr>
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">专项信息
						
					</td>
				</tr>
				<tr>
					<td style="width: 98%;height:60px;">
						<table class="easyui-datagrid" rownumbers="true" pagination="false" fitcolumns="true" 
					  		method="post"  idfield="BUS_ID" selectoncheck="false" singleSelect="false" checkonselect="true"
					  		fit="true" border="false" nowrap="false"
					  		url="busSpecialController.do?datagrid&Q_T.BUS_CODE_EQ=${busSpecial.BUS_CODE}"> 
						   <thead> 
							    <tr> 
							     <th data-options="field:'BUS_CODE',align:'left'" width="150">专项编码</th> 
							     <th data-options="field:'BUS_NAME',align:'left'" width="250">专项名称</th> 
							     <th data-options="field:'UNIT_NAME',align:'left'" width="150">业务单位</th> 
							    </tr> 
							</thead> 
						</table>
					</td>
				</tr>
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">业务基本信息</td>
				</tr>
				<tr id="basic">
					<td style="width: 98%;height:${basicSize }px;"  >
						<table id="tt" class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" pageSize="10"
					  		method="post"  idfield="TACHE_ID" selectoncheck="false" singleSelect="false" checkonselect="true"
					  		fit="true" border="false" nowrap="false"
					  		url="busSpecialController.do?basicDatagrid&busCode=${busSpecial.BUS_CODE}"> 
						   <thead> 
							    <tr> 
							    <th data-options="field:'SYSTEM_NAME',align:'left'" width="150">系统名称</th> 
							     <th data-options="field:'COLUMN_NAME',align:'left'" width="150">字段名称</th> 
							     <th data-options="field:'COLUMN_CODE',align:'left'" width="250">字段编码</th> 
							     <th data-options="field:'FIELD_TYPE',align:'left'" formatter="formatFieldType" width="100">字段类型</th> 
							     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th> 
							    </tr> 
							</thead> 
						</table>				
					</td>
				</tr>
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">专项环节信息</td>
				</tr>
				<tr >
					<td style="width: 98%; height:${tacheSize }px;">
						<table class="easyui-datagrid" rownumbers="true" pagination="false" fitcolumns="true" 
					  		method="post"  idfield="TACHE_ID" selectoncheck="false" singleSelect="false" checkonselect="true"
					  		fit="true" border="false" nowrap="false"
					  		url="busSpecialController.do?tacheDatagrid&Q_T.BUS_CODE_EQ=${busSpecial.BUS_CODE}"> 
						   <thead> 
							    <tr> 
							     <th data-options="field:'TACHE_CODE',align:'left'" width="150">环节编码</th> 
							     <th data-options="field:'TACHE_NAME',align:'left'" width="250">环节名称</th> 
							     <th data-options="field:'TREE_SN',align:'left'" width="150">排序</th> 
							    </tr> 
							</thead> 
						</table>				
					</td>
				</tr>			
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">环节过程信息</td>
				</tr>
				<tr >
					<td style="width: 98%; height:${processSize }px;">
						<table class="easyui-datagrid" rownumbers="true" pagination="true" pageSize="10" fitcolumns="true" 
					  		method="post"  idfield="PROCESS_CODE" selectoncheck="false" singleSelect="false" checkonselect="true"
					  		fit="true" border="false" nowrap="false"
					  		url="busSpecialController.do?processDatagrid&Q_B.BUS_CODE_EQ=${busSpecial.BUS_CODE}"> 
						   <thead> 
							    <tr> 
							    <th data-options="field:'TACHE_NAME',align:'left'" width="150">环节名称</th> 
							     <th data-options="field:'PROCESS_CODE',align:'left'" width="150">过程编码</th> 
							     <th data-options="field:'PROCESS_NAME',align:'left'" width="250">过程名称</th> 
							     <th data-options="field:'IS_MONITORNODE',align:'left'" formatter="isMonFormatter" width="100">是否监察点</th> 
							     <th data-options="field:'TREE_SN',align:'left'" width="100">排序</th>
							    </tr> 
							</thead> 
						</table>				
					</td>
				</tr>
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">监察字段信息</td>
				</tr>
				<tr>
					<td style="width: 98%; height:${esuperSize }px;">
						<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" pageSize="10"
					  		method="post"  idfield="TACHE_ID" selectoncheck="false" singleSelect="false" checkonselect="true"
					  		fit="true" border="false"  nowrap="false"
					  		url="busSpecialController.do?esuperDatagrid&busCode=${busSpecial.BUS_CODE}"> 
						   <thead> 
							    <tr> 
							     <th data-options="field:'TACHE_NAME',align:'left'" width="150">环节名称</th> 
							     <th data-options="field:'PROCESS_NAME',align:'left'" width="150">过程名称</th> 
							     <th data-options="field:'COLUMN_NAME',align:'left'" width="250">字段名称</th> 
							     <th data-options="field:'COLUMN_CODE',align:'left'" width="100">字段编码</th>
							     <th data-options="field:'FIELD_TYPE',align:'left'" formatter="formatFieldType" width="100">字段类型</th>
							     <th data-options="field:'BUSSYS_SN',align:'left'" width="100">排序</th>  
							    </tr> 
							</thead> 
						</table>				
					</td>
				</tr>
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">监察规则信息</td>
				</tr>
				<tr >
					<td style="width: 98%;height:${ruleConfigSize }px;">
						<table class="easyui-datagrid" rownumbers="true" pagination="true" fitcolumns="true" pageSize="10"
					  		method="post"  idfield="TACHE_ID" selectoncheck="false" singleSelect="false" checkonselect="true"
					  		fit="true" border="false" nowrap="false"
					  		url="busRuleController.do?datagrid&Q_B.BUS_CODE_EQ=${busSpecial.BUS_CODE}"> 
						   <thead> 
							    <tr> 
							     <th data-options="field:'TACHE_NAME',align:'left'" width="150">环节名称</th> 
							     <th data-options="field:'PROCESS_NAME',align:'left'" width="150">监察点名称</th> 
							     <th data-options="field:'SUPER_ELEMENTS',align:'left'" width="250">监察要素</th> 
							     <th data-options="field:'ANALYSIS_TYPE',align:'left'" formatter="formatAn" width="100">监察类型</th>
							     <th data-options="field:'IS_ARTIFICIAL',align:'left'" formatter="formatAr" width="100">监察方式</th>
							     <th data-options="field:'JUDGE_DESC',align:'left'" width="100">规则表达式</th>  
							     <th data-options="field:'RULE_DESC',align:'left'" width="100">规则描述</th>  
							    </tr> 
							</thead> 
						</table>				
					</td>
				</tr>
			</table>
	  	</div>
  	</div>
  </div>  
  <!-- 主体页面编写结束 -->
</body>
</html>
