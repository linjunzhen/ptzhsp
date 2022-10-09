<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
  String applyType = request.getParameter("applyType");
  request.setAttribute("applyType", applyType);
%>
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
<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3">
	<tr>
		<th style="width:20px">序号</th>
		<th style="width:200px">材料名称</th>
		<th >材料说明</th>
		<th style="width:80px">文件操作</th>
	</tr>
	<c:forEach items="${applyMaters}" var="applyMater"
		varStatus="varStatus">
		
		<c:if test="${applyMater.MATER_FILTER==null||applyMater.MATER_FILTER==''
		||requestParams.COMPANY_SETNATURE==applyMater.MATER_FILTER
		||busRecord.COMPANY_SETNATURE==applyMater.MATER_FILTER}">
		<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if> >
			<td style="text-align:center;">${varStatus.index+1}</td>
			<td style="width:400px">
				${applyMater.MATER_NAME}(${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份)			
			</td>
			<td>			 
			    ${applyMater.MATER_DESC}
			</td>
			<td style="text-align:center;">
				<c:if test="${applyMater.MATER_PATH!=null}">
					<a class="btn1" href="javascript:void(0);"
						onclick="downLoadFile('${applyMater.MATER_PATH}');"
						>下载</a>
				</c:if>
				<c:if test="${applyMater.MATER_PATH==null}">
					<label style="font-size: 15px;color:#FF4500;">*请自行准备材料</label>
				</c:if>
			</td>
		</tr>
		</c:if>
	</c:forEach>
	<!-- 共性材料信息 -->
	<c:if test="${materListValue == true }">
		<tr>
			<td colspan="4">共性材料信息</th>
		</tr>
		<tr>
			<td class="tab_width1" width="50px">序号</td>
			<td class="tab_width1" colspan="2">材料名称</td>
	        <td class="tab_width1">上传时间</td>
		</tr>
		<c:forEach items="${materList}" var="materListInfo" varStatus="materst">
			<tr>
				<td>${materst.index+1}</td>
				<td colspan="2" title="${materListInfo.FILE_NAME}">
					<a style="color: blue;" title="${materListInfo.FILE_NAME}"
						href="${fileServer }download/fileDownload?attachId=${materListInfo.FILE_ID}&attachType=${materListInfo.ATTACH_TYPE}" >
						${materListInfo.FILE_NAME}
					</a>
				</td>
				<td>${materListInfo.CREATE_TIME}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
