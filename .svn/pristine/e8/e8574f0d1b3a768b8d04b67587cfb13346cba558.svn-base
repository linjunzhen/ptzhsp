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
$(function(){
	<c:forEach items="${applyMaters}" var="applyMater">		
		$("#filetable tr[id='${applyMater.MATER_ID}']").each(function(i){
			if(i>0){
				$(this).remove();	
			}
			
		})
	</c:forEach>
	
	var sn = 1;
	$("td[name='sn']").each (function ()
    {
        this.innerHTML = sn;
        sn = sn+1;
    })
});

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
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="filetable">
	<tr>
		<th colspan="4">材料信息</th>
	</tr>
	<tr>
		<th style="width:80px">序号</th>
		<th style="width:350px">材料名称</th>
		<th >材料说明</th>
		<th style="width:200px">文件操作</th>
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
						<td name = "sn" style="text-align:center;">${varStatus.index+1}</td>				
						<td style="width:400px">
							${applyMater.MATER_NAME}(${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份)	
						</td>
						<td>			 
							${applyMater.MATER_DESC}
						</td>
						<td style="text-align:center;">
							<c:if test="${applyMater.MATER_PATH!=null && applyMater.MATER_PATH!=''}">
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
		<c:if test="${fn:contains(busRecord.CHANGE_OPTIONS,'03')&&!fn:contains(busRecord.CHANGE_OPTIONS,'07') }">
			<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
				<c:if test="${applyMater.MATER_NAME=='公司登记（备案）申请书' || applyMater.MATER_NAME=='营业执照（自行准备邮寄到行政服务中心）'
					|| ( applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1)
					|| ( applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1)
					|| ((havechairman || busRecord.COMPANY_NATURE=='02'||busRecord.COMPANY_NATURE=='04'||busRecord.COMPANY_NATURE=='07')&&applyMater.MATER_NAME=='董事会决议')
					|| fn:contains(applyMater.MATER_FILTER,busRecord.COMPANY_NATURE) }">
					<tr id="${applyMater.MATER_ID}">	
						<td name = "sn" style="text-align:center;">${varStatus.index+1}</td>			
						<td style="width:400px">
							${applyMater.MATER_NAME}(${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份)	
						</td>
						<td>			 
							${applyMater.MATER_DESC}
						</td>
						<td style="text-align:center;">
							<c:if test="${applyMater.MATER_PATH!=null && applyMater.MATER_PATH!=''}">
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
							<c:if test="${applyMater.MATER_PATH!=null && applyMater.MATER_PATH!='' && applyMater.MATER_NAME=='股权转让协议'}">
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
				<c:if test="${applyMater.MATER_NAME=='股东身份证黏贴页'  ||
				((!fn:contains(busRecord.CHANGE_OPTIONS,'09') && applyMater.MATER_NAME=='一人股东决定（原股东）' && fn:length(holderList)==1)
					|| (!fn:contains(busRecord.CHANGE_OPTIONS,'09') && applyMater.MATER_NAME=='股东会决议（原股东）' && fn:length(holderList)>1)) }" >
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
