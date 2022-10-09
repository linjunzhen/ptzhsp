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
/**
 * 格式化
 */
function formatState(val,row){
	var html = '<a target="_blank" style="margin-left: 5px;" href="executionController.do?goDetail&isFiled='+row.ISFILED+'&exeId='+row.EXE_ID+'&categoryId='+row.CATEGORY_ID+'">查看</a>';
	if(val=="-1"){
		return "<font color='red'>未受理</font>";
	} else if(val=="0"){
		return "<font color='red'>草稿</font>"+html;
	} else if(val=="1"){
		return "<font color='green'>正在办理</font>"+html;
	} else if(val=="2"){
		return "<font color='blue'>已办结(正常结束)</font>"+html;
	} else if(val=="3"){
		return "<font >已办结(审核通过)</font>"+html;
	}else if(val=="4"){
		return "<font >已办结(审核不通过)</font>"+html;
	}else if(val=="5"){
		return "<font color='red'>已办结(退回)</font>"+html;
	}else if(val=="6"){
		return "<font color='black'>强制结束</font>"+html;
	}else if(val=="7"){
		return "<font color='red'>预审不通过</font>"+html;
	}
};	
function formatSubject(val,row){
	//获取事项编码
	var STATE = row.STATE;
	//获取事项编码
	var itemCode = row.ITEM_CODE;
	//获取流程KEY
	var defkey= row.DEF_KEY;
	if(STATE==-1 || STATE==4 || STATE==5 || STATE==6 || STATE==7){
		var href = "";
		href = '<a href="javascript:toUrl(\''+defkey+'\',\''+itemCode+'\',\'${PROJECT_CODE}\')"';
		href += '><span style="text-decoration:underline;color:green;" title="'+val+'">'+val+'</span></a>';
		return href;
	} else {
		return val;
	}
}
function formatIsKey(val,row){
	if(val=="0"){
		return "<font>否</font>";
	} else if(val=="1"){
		return "<font color='green'>是</font>";
	}
}
function toUrl(defkey,itemCode,code){
	var url = "executionController.do?goStart&defKey="+defkey+"&itemCode="+itemCode+"&PROJECT_CODE="+code;
	var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");
	$("#ProjectDetailItemToolbar").append(ssoForm);
	ssoForm.submit();
}
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="ProjectDetailItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
				<input type="hidden" id="projectCode" value="${PROJECT_CODE}"/>
				<!--====================结束编写隐藏域============== -->
			</div>
			<div class="hd">
	            <ul class="clearfix">
	            	<c:forEach var="stageInfo" items="${stageList}">
						<a href="${webRoot}projectDetailController/ItemDetailSelector.do?FLOW_CATE_ID=${FLOW_CATE_ID}&PROJECT_CODE=${PROJECT_CODE}&stageId=${stageInfo.ID}">
							<c:if test="${stageId == stageInfo.ID}">
								<li class="on">${stageInfo.NAME}</li>
							</c:if>
							<c:if test="${stageId != stageInfo.ID}">
								<li>${stageInfo.NAME}</li>
							</c:if>
						</a>
					</c:forEach>
	            </ul>
	        </div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="ProjectDetailItemGrid" fitColumns="true" toolbar="#ProjectDetailItemToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="true"
				url="projectDetailController.do?projectItemList&Q_T.FWSXMXLB_NEQ=2&Q_C.ID_EQ=${FLOW_CATE_ID}&PROJECT_CODE=${PROJECT_CODE}&Q_S.STAGE_ID_EQ=${stageId}&Q_L.IS_OFF_SHELVES_EQ=0">
				<thead>
					<tr>
	                    <th data-options="field:'DEF_KEY',hidden:true">DEF_KEY</th>
	                    <th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="35%" formatter="formatSubject">事项名称</th>
	                    <th data-options="field:'DEPART_NAME',align:'left'" width="11%" >所属部门</th>
	                    <th data-options="field:'CNQXGZR',align:'left'" width="10%" >承诺时限(工作日)</th>
	                    <th data-options="field:'IS_KEY_ITEM',align:'center'" width="5%" formatter="formatIsKey">是否必办</th>
	                    <th data-options="field:'CREATE_TIME',align:'left'" width="12%" >申请时间</th>
	                    <th data-options="field:'END_DATE',align:'left'" width="12%" >办理截止期限</th>
	                    <th data-options="field:'STATE',align:'left'" width="12%" formatter="formatState">状态</th>
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
