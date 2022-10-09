<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>材料下载</title>
<meta name="renderer" content="webkit">
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<!--[if lte IE 6]> 
	<script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
<eve:resources loadres="artdialog,apputil"></eve:resources>

<script type="text/javascript">
/**
 * 下载附件
 * @param {} fileId
 */
function downLoadFile(fileId){
	var nodeName = '${EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES}';
	if(nodeName=='窗口办理'||nodeName=='开始'){		
		//获取流程信息对象JSON
		window.location.href="<%=path%>/domesticControllerController/downLoad.do?fileId="+fileId
				+"&exeId=${exeId}&itemCode=${itemCode}";
	}else{
		alert("事项未符合下载要求，请耐心等待！");
	}
}
/**
 * 预览附件
 * @param {} fileId
 */
function previewFile(fileId){
	var nodeName = '${EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES}';
	if(nodeName=='窗口办理'||nodeName=='开始'){		
		window.open("<%=path%>/domesticControllerController/pdfPreview.do?fileId="+fileId
				+"&exeId=${exeId}&itemCode=${itemCode}", "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
	}else{
		alert("事项未符合下载要求，请耐心等待！");
	}
}

function downloadApply(){		
	var exeId = '${exeId}';
	var itemCode = '${itemCode}';
	
	window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApplyFront.do?exeId="+exeId
		+"&itemCode="+itemCode;
}

/**
 * 预览附件(针对股权转让协议个性化处理)
 * @param {} fileId
 */
function previewGqzrFile(fileId){
	//跳转至多个股东转让界面
	$.dialog.open(encodeURI("domesticControllerController.do?goGqzrView&fileId="+fileId+"&exeId=${exeId}&itemCode=${itemCode}&type="+"front"), {
		title : "股权转让协议",
		width : "80%",
		height : "60%",
		lock : true,
		resize : false
	}, false);
}

$(function(){
	<c:forEach items="${applyMaters}" var="applyMater">		
		$("#filetable tr[id='${applyMater.MATER_ID}']").each(function(i){
			if(i>0){
				$(this).remove();	
			}
			
		})
	</c:forEach>
	
});
</script>

</head>

<body style="background: none; min-width:800px;">
	<table cellpadding="0" cellspacing="1" class="syj-table2 tmargin20" id="filetable">
		<tr>
			<th style="width:200px">材料名称</th>
			<th style="width:80px">材料份数</th>
			<th >材料说明</th>
			<th style="width:80px">文件操作</th>
		</tr>
		<c:if test="${fn:contains(busRecord.CHANGE_OPTIONS,'01') || fn:contains(busRecord.CHANGE_OPTIONS,'02')
			|| fn:contains(busRecord.CHANGE_OPTIONS,'05') || fn:contains(busRecord.CHANGE_OPTIONS,'06')
			|| fn:contains(busRecord.CHANGE_OPTIONS,'08') || fn:contains(busRecord.CHANGE_OPTIONS,'09') }">			
			<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
				<c:if test="${applyMater.MATER_NAME=='公司登记（备案）申请书' || applyMater.MATER_NAME=='营业执照（自行准备邮寄到行政服务中心）'
					|| (applyMater.MATER_NAME=='一人股东决定（新股东）' && fn:length(newHolderList)==1) 
					|| (applyMater.MATER_NAME=='股东会决议（新股东）' && fn:length(newHolderList)>1) 
					|| fn:contains(applyMater.MATER_FILTER,busRecord.COMPANY_NATURE)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'02') && applyMater.MATER_NAME=='福建省市场主体住所（经营场所）申报承诺表')}">
					<tr id="${applyMater.MATER_ID}">				
						<td style="width:400px">
							${applyMater.MATER_NAME}		
						</td>
						<td style="text-align:center;">
							${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份
						</td>
						<td>			 
							${applyMater.MATER_DESC}
						</td>
						<td style="text-align:center;">
							<c:if test="${applyMater.MATER_PATH!=null   
								&& (EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='窗口办理'||EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='开始')}">
								<a class="btn1" href="javascript:void(0);"
									onclick="previewFile('${applyMater.MATER_PATH}');"
									>预览</a>&nbsp;
								<a class="btn1" href="javascript:void(0);"
									onclick="downLoadFile('${applyMater.MATER_PATH}');"
									>下载</a>
							</c:if>
							<c:if test="${applyMater.MATER_PATH!=null
								&& EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='窗口办理' && EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='开始'}">
								<label style="font-size: 15px;color:#330099;">*未符合下载要求</label>
							</c:if>
							<c:if test="${applyMater.MATER_PATH==null }">
								<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
							</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>		
		<c:if test="${fn:contains(busRecord.CHANGE_OPTIONS,'03')&&!fn:contains(busRecord.CHANGE_OPTIONS,'07') }">
			<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
				<c:if test="${applyMater.MATER_NAME=='公司登记（备案）申请书' || applyMater.MATER_NAME=='营业执照（自行准备邮寄到行政服务中心）'
					|| (applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1)
					|| (applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1)
					|| ((havechairman || busRecord.COMPANY_NATURE=='02'||busRecord.COMPANY_NATURE=='04'||busRecord.COMPANY_NATURE=='07')&&applyMater.MATER_NAME=='董事会决议')
					|| fn:contains(applyMater.MATER_FILTER,busRecord.COMPANY_NATURE) }">
					<tr id="${applyMater.MATER_ID}">				
						<td style="width:400px">
							${applyMater.MATER_NAME}		
						</td>
						<td style="text-align:center;">
							${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份
						</td>
						<td>			 
							${applyMater.MATER_DESC}
						</td>
						<td style="text-align:center;">
							<c:if test="${applyMater.MATER_PATH!=null   
								&& (EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='窗口办理'||EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='开始')}">
								<a class="btn1" href="javascript:void(0);"
									onclick="previewFile('${applyMater.MATER_PATH}');"
									>预览</a>&nbsp;
								<a class="btn1" href="javascript:void(0);"
									onclick="downLoadFile('${applyMater.MATER_PATH}');"
									>下载</a>
							</c:if>
							<c:if test="${applyMater.MATER_PATH!=null
								&& EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='窗口办理' && EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='开始'}">
								<label style="font-size: 15px;color:#330099;">*未符合下载要求</label>
							</c:if>
							<c:if test="${applyMater.MATER_PATH==null }">
								<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
							</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		
		<c:if test="${fn:contains(busRecord.CHANGE_OPTIONS,'04')||fn:contains(busRecord.CHANGE_OPTIONS,'07')}">
			<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
				<c:if test="${applyMater.MATER_NAME=='公司登记（备案）申请书' || applyMater.MATER_NAME=='营业执照（自行准备邮寄到行政服务中心）'
					|| (applyMater.MATER_NAME=='股权转让协议' && isStockTransfer)
					|| ((!fn:contains(busRecord.CHANGE_OPTIONS,'10')&& !fn:contains(busRecord.CHANGE_OPTIONS,'11')&& !fn:contains(busRecord.CHANGE_OPTIONS,'12'))&&(
						(applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1)
						||(applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1)
						||(applyMater.MATER_NAME=='股东会决议（新股东）' && fn:length(newHolderList)>1)
						||(applyMater.MATER_NAME=='一人股东决定（新股东）' && fn:length(newHolderList)==1)
						||(applyMater.MATER_NAME=='董事会决议' && (havechairman || busRecord.COMPANY_NATURE=='02' || busRecord.COMPANY_NATURE=='04' || busRecord.COMPANY_NATURE=='07'))
						))
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'10') && applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'10') && applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'10') && applyMater.MATER_NAME=='一人股东决定（新股东）' && fn:length(newHolderList)==1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'10') && applyMater.MATER_NAME=='股东会决议（新股东）' && fn:length(newHolderList)>1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'10') && applyMater.MATER_NAME=='董事会决议' && (havechairman || busRecord.COMPANY_NATURE=='02' || busRecord.COMPANY_NATURE=='04' || busRecord.COMPANY_NATURE=='07'))
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'11') && applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'11') && applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'11') && applyMater.MATER_NAME=='一人股东决定（新股东）' && fn:length(newHolderList)==1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'11') && applyMater.MATER_NAME=='股东会决议（新股东）' && fn:length(newHolderList)>1)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'12') && applyMater.MATER_NAME=='董事会决议' && (havechairman || busRecord.COMPANY_NATURE=='02' || busRecord.COMPANY_NATURE=='04' || busRecord.COMPANY_NATURE=='07'))
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'12') && applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1 && !havechairman)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'12') && applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1 && !havechairman )
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'12') && applyMater.MATER_NAME=='一人股东决定（新股东）' && fn:length(newHolderList)==1 && !haveNewChairman)
					|| (fn:contains(busRecord.CHANGE_OPTIONS,'12') && applyMater.MATER_NAME=='股东会决议（新股东）' && fn:length(newHolderList)>1 && !haveNewChairman)
					|| fn:contains(applyMater.MATER_FILTER,busRecord.COMPANY_NATURE) }">
					<tr id="${applyMater.MATER_ID}">				
						<td style="width:400px">
							${applyMater.MATER_NAME}		
						</td>
						<td style="text-align:center;">
							${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份
						</td>
						<td>			 
							${applyMater.MATER_DESC}
						</td>
						<td style="text-align:center;">
							<c:if test="${applyMater.MATER_PATH!=null   
								&& (EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='窗口办理'||EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='开始')
								&& applyMater.MATER_NAME!='股权转让协议'}">
								<a class="btn1" href="javascript:void(0);"
									onclick="previewFile('${applyMater.MATER_PATH}');"
									>预览</a>&nbsp;
								<a class="btn1" href="javascript:void(0);"
									onclick="downLoadFile('${applyMater.MATER_PATH}');"
									>下载</a>
							</c:if>
							<c:if test="${applyMater.MATER_PATH!=null
								&& EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='窗口办理' && EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='开始'}">
								<label style="font-size: 15px;color:#330099;">*未符合下载要求</label>
							</c:if>
							<c:if test="${applyMater.MATER_PATH==null }">
								<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
							</c:if>
							<c:if test="${applyMater.MATER_PATH!=null   
								&& (EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='窗口办理'||EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES=='开始')
								&& applyMater.MATER_NAME=='股权转让协议'}">
								<a class="btn1" href="javascript:void(0);" onclick="previewGqzrFile('${applyMater.MATER_PATH}');"
								>预览</a>&nbsp;
							</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${fn:contains(busRecord.CHANGE_OPTIONS,'05')||fn:contains(busRecord.CHANGE_OPTIONS,'07') ||fn:contains(busRecord.CHANGE_OPTIONS,'09') }">
			<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
				<c:if test="${applyMater.MATER_NAME=='股东身份证黏贴页'}" >
					<tr id="${applyMater.MATER_ID}">			
						<td name = "sn" style="text-align:center;">${varStatus.index+1}</td>	
						<td style="width:400px">
							${applyMater.MATER_NAME}(${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份)		
						</td>
						<td>			 
							${applyMater.MATER_DESC}
						</td>
						<td style="text-align:center;">
							<c:if test="${applyMater.MATER_PATH!=null && applyMater.MATER_PATH!='' && applyMater.MATER_NAME!='股权转让协议'}">
								<a class="btn1" href="javascript:void(0);"
									onclick="previewFile('${applyMater.MATER_PATH}');"
									>预览</a>&nbsp;
								<a class="btn1" href="javascript:void(0);"
									onclick="downLoadFile('${applyMater.MATER_PATH}');"
									>下载PDF</a>&nbsp;
								<a class="btn1" href="javascript:void(0);"
									onclick="downLoadWordFile('${applyMater.MATER_PATH}');"
									>下载WORD</a>
							</c:if>
							<c:if test="${applyMater.MATER_PATH==null }">
								<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
							</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
	</table>

</body>
</html>
