<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
  String applyType = request.getParameter("applyType");
  request.setAttribute("applyType", applyType);
%>

<script type="text/javascript">    
$ (function (){
	var sn = 1;
	$("td[name='sn']").each (function ()
    {
        this.innerHTML = sn;
        sn = sn+1;
    })
})
function downloadApply(){		
	var BANK_NAME = $("#BANK_NAME").val();
	if(null!=BANK_NAME && ''!=BANK_NAME){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		var exeId = flowSubmitObj.EFLOW_EXEID;
		var itemCode = '${serviceItem.ITEM_CODE}';
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApply.do?exeId="+exeId
			+"&itemCode="+itemCode;
	} else{
		alert("该办件没有银行信息，无法下载！");
	}
}
</script>
<style>
.materBtnA {
    background: #62a1cf none repeat scroll 0 0;
    color: #fff;
    display: inline-block;
    height: 26px;
    left: -1px;
    line-height: 26px;
    margin-left: 5px;
    position: relative;
    text-align: center;
    top: 1px;
    width: 59px;
}
</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">材料信息</th>
	</tr>
	<tr>
		<th style="width:80px">序号</th>
		<th style="width:350px">材料名称</th>
		<th >材料说明</th>
		<th style="width:200px">文件操作</th>
	</tr>
	<c:forEach items="${applyMaters}" var="applyMater"
		varStatus="varStatus">
		<c:if test="${applyMater.MATER_FILTER==null||applyMater.MATER_FILTER==''
		||requestParams.COMPANY_SETTYPE==applyMater.MATER_FILTER
		||busRecord.COMPANY_SETTYPE==applyMater.MATER_FILTER
		||requestParams.COMPANY_SETNATURE==applyMater.MATER_FILTER
		||busRecord.COMPANY_SETNATURE==applyMater.MATER_FILTER
		||busRecord.CANCEL_TYPE==applyMater.MATER_FILTER
		||fn:contains(applyMater.MATER_FILTER,busRecord.COMPANY_SETNATURE)}">
		
		<c:if test="${(applyMater.MATER_NAME=='一人股东决定' && fn:length(holderList)==1)
		||(applyMater.MATER_NAME=='股东会决议' && fn:length(holderList)>1)
		||(applyMater.MATER_NAME=='董事会决议' && (fn:length(directorList)>1 || fn:length(oldDirectorList)>1))}">
			<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if> >
				<td name = "sn" style="text-align:center;">${varStatus.index+1}</td>
				<td style="width:400px">
					${applyMater.MATER_NAME}(${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份)
						<c:if test="${applyMater.MATER_NAME=='福建省市场主体住所(经营场所)申报承诺表'}">
							<a href="javascript:void(0);"
								onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');"
								style="color:#F00;">[样本]</a>
						</c:if>				
				</td>
				<td>			 
					${applyMater.MATER_DESC}
				</td>
				<td style="text-align:center;">
					<c:if test="${applyMater.MATER_PATH!=null && applyMater.MATER_PATH!=''&& applyMater.MATER_NAME!='福建省市场主体住所(经营场所)申报承诺表' }">
						
						<a class="btn1" href="javascript:void(0);" onclick="previewFile('${applyMater.MATER_PATH}');"
						>预览</a>&nbsp;
						<a class="btn1" href="javascript:void(0);"
							onclick="downLoadFile('${applyMater.MATER_PATH}');"
							>下载PDF</a>&nbsp;
						<a class="btn1" href="javascript:void(0);"
							onclick="downLoadWordFile('${applyMater.MATER_PATH}');"
							>下载WORD</a>
					</c:if>
					<c:if test="${applyMater.MATER_PATH==null||applyMater.MATER_NAME=='福建省市场主体住所(经营场所)申报承诺表' }">
						<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
					</c:if>
				</td>
			</tr>
		</c:if>
		<c:if test="${applyMater.MATER_NAME!='一人股东决定' && applyMater.MATER_NAME!='股东会决议' && applyMater.MATER_NAME!='董事会决议'}">
			<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if> >
				<td name = "sn" style="text-align:center;">${varStatus.index+1}</td>
				<td style="width:400px">
					${applyMater.MATER_NAME}(${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份)
						<c:if test="${applyMater.MATER_NAME=='福建省市场主体住所(经营场所)申报承诺表'}">
							<a href="javascript:void(0);"
								onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');"
								style="color:#F00;">[样本]</a>
						</c:if>				
				</td>
				<td>			 
					${applyMater.MATER_DESC}
				</td>
				<td style="text-align:center;">
					<c:if test="${applyMater.MATER_PATH!=null && applyMater.MATER_PATH!=''&& applyMater.MATER_NAME!='福建省市场主体住所(经营场所)申报承诺表' }">
						
						<a class="btn1" href="javascript:void(0);" onclick="previewFile('${applyMater.MATER_PATH}');"
						>预览</a>&nbsp;
						<a class="btn1" href="javascript:void(0);"
							onclick="downLoadFile('${applyMater.MATER_PATH}');"
							>下载PDF</a>&nbsp;
						<a class="btn1" href="javascript:void(0);"
							onclick="downLoadWordFile('${applyMater.MATER_PATH}');"
							>下载WORD</a>
					</c:if>
					<c:if test="${applyMater.MATER_PATH==null||applyMater.MATER_NAME=='福建省市场主体住所(经营场所)申报承诺表' }">
						<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
					</c:if>
				</td>
			</tr>
		</c:if>
		
		</c:if>
	</c:forEach>
</table>
