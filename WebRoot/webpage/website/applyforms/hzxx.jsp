<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.system.service.FileAttachService"%>
<%@ page language="java" import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String exeId = request.getParameter("exeId");  
    if(StringUtils.isNotEmpty(exeId)){
        FileAttachService fileAttachService = (FileAttachService)AppUtil.getBean("fileAttachService");
    	List<Map<String,Object>> fileList = fileAttachService.findByExeId(exeId,"1");
    	int fileListSize = 0;
		if(null!=fileList){
			fileListSize = fileList.size();
		}
    	request.setAttribute("fileListSize", fileListSize);
		request.setAttribute("fileList", fileList);
    }
	
%>
<script type="text/javascript">

</script>
<c:if test="${fileListSize>0}">
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li style="background:none" class="on"><span>回执信息</span></li>
		</ul>
	</div>
	<div class="tab_height">
	</div>
	<table cellspacing="1" cellpadding="0" class="tab_tk_pro1">
		<tbody>
		<tr>
			<td width="50px" class="tab_width1">序号</td>
			<td class="tab_width1">材料名称</td>	
			<td width="200px" class="tab_width1">材料类型</td>
			<td width="200px" class="tab_width1">回执部门</td>
			
			
		</tr>
		<c:forEach items="${fileList}" var="file" varStatus="varStatus">
		<tr>
			<td>${varStatus.index+1}</td>
			<td>
			<a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${file.FILE_ID}');">
             <font color="blue">
             ${file.FILE_NAME}
             </font>
             </a>
			 </td>
			<td>${file.DIC_NAME}</td>			
			<td>${file.UPLOADER_DEPNAME}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>

</div>
</c:if>