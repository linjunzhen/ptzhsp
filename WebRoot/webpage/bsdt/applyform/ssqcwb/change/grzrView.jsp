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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
    /**
	 * 下载附件
	 * @param {} fileId
	 */
	function downLoadFile(fileId,index){
		//获取流程信息对象JSON
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			if(flowSubmitObj.busRecord){			
				window.location.href=__ctxPath+"/domesticControllerController/downLoad.do?fileId="+fileId
				+"&exeId="+flowSubmitObj.busRecord.EXE_ID
				+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE
				+"&index="+index;
			}else{
				art.dialog({
					content : "无法下载",
					icon : "error",
					ok : true
				});
			}
		}
	    var layload = layer.load('正在处理，请稍等…');
	    setTimeout(function(){
	        layer.close(layload);
	    },2000);
	}
	/**
	 * 下载附件
	 * @param {} fileId
	 */
	function downLoadWordFile(fileId,index){
		//获取流程信息对象JSON
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			if(flowSubmitObj.busRecord){			
				window.location.href=__ctxPath+"/domesticControllerController/downLoadWord.do?fileId="+fileId
				+"&exeId="+flowSubmitObj.busRecord.EXE_ID
				+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE
				+"&index="+index;
			}else{
				art.dialog({
					content : "无法下载",
					icon : "error",
					ok : true
				});
			}
		}
	    var layload = layer.load('正在处理，请稍等…');
	    setTimeout(function(){
	        layer.close(layload);
	    },2000);
	}
	
	/**
	 * 预览附件
	 * @param {} fileId
	 */
	function previewFile(fileId,index){
	    //获取股东转让协议信息
	 /*    var stockFromJson = $("input[name='stockFromJson']").val();
	    var stockFromObj = JSON.parse(stockFromJson);
	    var stockFrom = JSON.stringify(stockFromObj[index]); */
		//获取流程信息对象JSON
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			if(flowSubmitObj.busRecord){	
				window.open(__ctxPath+"/domesticControllerController/pdfPreview.do?fileId="+fileId
				+"&exeId="+flowSubmitObj.busRecord.EXE_ID+"&index="+index
				+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
			}else{
				art.dialog({
					content : "无法下载",
					icon : "error",
					ok : true
				});
			}
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
	<form id="GdzrForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div id="GdzrFormDiv" data-options="region:'center',split:true,border:false" >
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="fileId" value="${fileId}" />
			
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">股权转让协议信息列表</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr>
								<td style="text-align: center;font-weight:bold;">序号</td>
								<td style="text-align: center;font-weight:bold;">旧公司名称</td>
								<td style="text-align: center;font-weight:bold;">旧股东姓名</td>
								<td style="text-align: center;font-weight:bold;">受让新股东姓名</td>
								<td style="text-align: center;font-weight:bold;">总转让认缴出资额(万元)</td>
								<td style="text-align: center;font-weight:bold;">占原注册资本比例</td>
								<td style="text-align: center;font-weight:bold;">转让价格(万元)</td>
								<td style="text-align: center;font-weight:bold;">付款方式</td>
								<td style="text-align: center;font-weight:bold;">文件操作</td>
							</tr>
							<c:forEach var="stockFrom" items="${stockFromList}" varStatus="varStatus">
								<tr>
									<td style="text-align:center">
										<span >${varStatus.index+1}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.oldCompanyName}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.stockFrom}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.newHolderName}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.TRANSFER_CONTRIBUTIONS}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.OLD_PROPORTION}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.TRANSFER_PRICE}</span>
									</td>
									<td style="text-align:center">
										<span >${stockFrom.PAYMETHOD}</span>
									</td>
									<td style="text-align:center">
										<a class="btn1" href="javascript:void(0);" onclick="previewFile('${stockFrom.MATER_PATH}','${varStatus.index}');"
										>预览</a>&nbsp;
										<a class="btn1" href="javascript:void(0);"
											onclick="downLoadFile('${stockFrom.MATER_PATH}','${varStatus.index}');"
											>下载PDF</a>&nbsp;
										<a class="btn1" href="javascript:void(0);"
											onclick="downLoadWordFile('${stockFrom.MATER_PATH}','${varStatus.index}');"
											>下载WORD</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</form>

</body>

