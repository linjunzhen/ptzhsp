<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<div id="${serviceItem.ITEM_CODE}_MATER">
	<input type="hidden" name="${currentTime}applyMatersJson" value="${applyMatersJson}">	
	<div class="syj-title1" style="height:30px;">
		<span>${serviceItem.ITEM_NAME}-材料信息(<font color="red">上传图片时建议上传200dpi的图片。</font>)</span>
	</div>
	
	<table cellpadding="0" cellspacing="1" class="syj-table2 tmargin2" id="${currentTime}materCheckList" style="margin-top: 15px;">
		<tr>
			<th style="width:10%" id="xhTitle">序号</tthd>
			<th style="width:50%">材料名称</th>
			<th style="width:20%">内容</th>
			<th style="width:20%">提交时间</th>
		</tr>
		<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
			<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if>>
				<td class="materIndex" style="text-align: center;">${varStatus.index+1}</td>
				<td style="text-align: center;">
					 <c:if test="${applyMater.MATER_ISNEED=='1'}">
						<font class="tab_color">*</font>
					</c:if> ${applyMater.MATER_NAME}
					<c:if test="${applyMater.MATER_PATH!=null}">
						<a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');" style="color:#F00;">[样本]</a>
					</c:if>
				</td>
				<%-- <td <c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>
					${applyMater.MATER_SQGZ}
				</td> --%>
				<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX=='综合件'}">
					<td colspan="4" style="text-align: center;"><a class="materBtnA" style="color:#fff; float: right; margin-right: 15px;" href="javascript:showMater('${applyMater.MATER_CODE}')" id="${applyMater.MATER_CODE}_a">收起</a>
					</td>
				</c:if>
				<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!='综合件'}">
					<td style="text-align: center;">
						<div id="UploadedFiles_${applyMater.MATER_CODE}" <c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
							<c:forEach var="uploadFile" items="${applyMater.uploadFiles}">
								<c:if test="${applyMater.UPLOADED_SIZE!=0}">
									<p id="${uploadFile.FILE_ID}">
										<a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');" style="cursor: pointer;"> 
											<font color="blue"> ${uploadFile.FILE_NAME} </font>
										</a>
										<c:if test="${uploadFile.FILE_TYPE=='etc'}">
											<a href="javascript:void(0);"
											   onclick="preViewEtc('${uploadFile.FILE_PATH}');">
												<font color="red">预览</font></a>
										</c:if>
										<c:if test="${EFLOW_IS_START_NODE!='false'&&isQueryDetail!='true'}">
											<a href="javascript:void(0);" onclick="AppUtil.delUploadMater('${uploadFile.FILE_ID}','${uploadFile.ATTACH_KEY}');"> <font color="red">删除</font></a>
										</c:if>
									</p>
								</c:if>
							</c:forEach>
						</div>
					</td>
				</c:if>
				<td style="text-align: center;" >			 
					${applyMater.CREATE_TIME}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
