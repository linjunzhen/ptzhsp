<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%

%>
<div id="${serviceItem.ITEM_CODE}_MATER">
	<input type="hidden" name="${currentTime}applyMatersJson"  value="${applyMatersJson}">
	<div  class="tit" style="background-image: url(./webpage/website/project/images/wysb-i7.png);">
		<span>${serviceItem.ITEM_NAME}-材料信息(<font color="red">上传图片时建议上传200dpi的图片。</font>)</span>
	</div>
	<div class="eui-upload">
	<table >
		<c:if test="${handleTypes!=null&&fn:length(handleTypes)>0}">
			<tr>
				<th style="width:145px"> 业务办理子项：</th>
				<td>
					<c:forEach items="${handleTypes}" var="handleType">
						<input type="radio" name="${currentTime}EXE_SUBBUSCLASS" onclick="checkItemMaterHandleType(this.value,'${currentTime}')" value="${handleType.bus_handle_type }">${handleType.bus_handle_type }&nbsp;
					</c:forEach>
				</td>
			</tr>
		</c:if>
	</table>


	<table cellpadding="0" cellspacing="1" class="syj-table2 tmargin2" id="${currentTime}materCheckList" style="margin-top: 15px;">
		<thead>
			<th style="width:50px" id="xhTitle">序号</tthd>
			<th style="width:80px" id="sxzxTitle">事项子项</th>
			<th style="width:300px">材料名称</th>
			<th style="width:180px"
				<c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>
				注意事项</th>
			<th style="width:80px">材料说明</th>
			<th>附件</th>
			<c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
				<th style="width:100px">收取方式</th>
				<th style="width:250px">文件操作</th>
			</c:if>
			<c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
				<th style="width:100px">是否收取</th>
				<th style="width:200px">审核状态</th>
			</c:if>
		</thead>
		<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
			<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if>>
				<td class="materIndex" style="text-align: center;">${varStatus.index+1}</td>
				<td name="busClass"  style="text-align: center;" <c:if test="${applyMater.BUSCLASS_NAME!=null&&applyMater.BUSCLASS_NAME!=''}">class="busClass"</c:if>>${applyMater.BUSCLASS_NAME}
				</td>
				<td style="text-align: center;">
					<c:if test="${applyMater.MATER_ISNEED=='1'}">
					<i>*</i>
					</c:if> ${applyMater.MATER_NAME}
					<c:if test="${applyMater.MATER_PATH!=null}">
					<a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');" style="color:#F00;">[样本]</a>
					</c:if>

				<td >
						${applyMater.MATER_SQGZ}
				</td>
				<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX=='综合件'}">
					<td colspan="4" style="text-align: center;"><a class="materBtnA" style="color:#fff; float: right; margin-right: 15px;" href="javascript:showMater('${applyMater.MATER_CODE}')" id="${applyMater.MATER_CODE}_a">收起</a>
					</td>
				</c:if>
				<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!='综合件'}">
					<td style="text-align: center;">
						<c:if test="${applyMater.PAGECOPY_NUM==null&&applyMater.PAGE_NUM==null}">
							<c:if test="${applyMater.MATER_CLSMLX==null||applyMater.MATER_CLSMLX==''}">无</c:if>
							<c:if test="${applyMater.MATER_CLSMLX=='复印件'}">原件1份&nbsp;</c:if>
							<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!=''}">${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份</c:if>
						</c:if> <c:if test="${applyMater.PAGECOPY_NUM!=null||applyMater.PAGE_NUM!=null}">
						<c:if test="${applyMater.PAGECOPY_NUM!=null}">复印件${applyMater.PAGECOPY_NUM}份</c:if>
						<c:if test="${applyMater.PAGE_NUM!=null}">原件${applyMater.PAGE_NUM}份（${applyMater.GATHERORVER}）</c:if>
						<c:if test="${fn:indexOf(applyMater.MATER_CLSMLX, '电子文档') != -1}">电子文档</c:if>
					</c:if>
					</td>
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
					<c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
						<td style="text-align: center;"><select type="select" class="tab_text" style="width: 90px;" id="SQFS_${applyMater.MATER_CODE}" <c:if test="${applyType=='1'}">disabled="disabled"</c:if>
																onchange="chagediv('${applyMater.MATER_CODE}')">
							<option value="1"<c:if test="${applyMater.SQFS==1}">selected="true"</c:if>>上传</option>
							<option value="2"<c:if test="${applyMater.SQFS==2}">selected="true"</c:if>>纸质收取</option>
						</select>
						</td>
						<td>
							<div id="div1_${applyMater.MATER_CODE}" <c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>

								<div id="${applyMater.MATER_CODE}__SC" style="width: 100%;float: left;" >
									<a href="javascript:void(0);" onclick="AppUtil.openTitleFileUploadDialogForProject('${applyMater.MATER_TYPE}','${applyMater.MATER_CODE}','${serviceItem.FORM_KEY}')">
										<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
									</a>
									<a href="javascript:void(0);" onclick="selectShareMaterAndLicense('${applyMater.MATER_CODE}','${applyMater.PERSON_CREDIT_USE}')">
										<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/lscl.png" />
									</a>
									<c:if test="${applyMater.SUPPORT_WORD=='1'}">
								<span id="${applyMater.MATER_CODE}__ZXBJ" style="float: left;margin:0 5px 0 0;<c:if test="${applyMater.UPLOADED_SIZE>0}">display: none;</c:if>"
									  class="tab_btn_tj1 tab_tk_pro2btn" onclick="onlineWord('${applyMater.MATER_CODE}','${applyMater.MATER_PATH}','${applyMater.MATER_NAME}');">
																		在线编辑
								</span>
									</c:if>
								</div>

<%--								<div id="${applyMater.MATER_CODE}__SCAN"--%>
<%--									 <c:if test="${isWebsite=='2'}">style="float: left; width: 77px; margin-top: 5px;"</c:if>--%>
<%--									 <c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>--%>
<%--									<a href="javascript:chooseCtrl('${applyMater.MATER_CODE}','${applyMater.MATER_CLSM}')">--%>
<%--										<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>--%>
<%--									</a>--%>
<%--								</div>--%>
								<div id="${applyMater.MATER_CODE}__HISMAT"
									 <c:if test="${isWebsite=='2'}">style="float: left;width: 77px; margin-top: 5px;"</c:if>
									 <c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>
									<a href="javascript:HisMatList('${applyMater.MATER_CODE}')">
										<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/hismat.png" style="width:73px;height:27px;"/>
									</a>
								</div>
								<!--电子证照-->
								<!--电子证照前台用户，当材料勾选了电子证照才显示-->
								<c:if test="${fn:contains(applyMater.collect_method,'03')}">
									<div id="${applyMater.MATER_CODE}__LICENSE" style="float: left; width: 77px; margin-top: 5px;">
										<a href="javascript:LicenseList('${applyMater.MATER_CODE}')">
											<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/dzzz.png" style="width:73px;height:27px;"/>
										</a>
									</div>
								</c:if>

							</div>
							<div id="div2_${applyMater.MATER_CODE}"<c:if test="${applyMater.SQFS!=2}">style="display: none;"</c:if>>
								<select type="select" class="tab_text" style="width: 90px;float: left;" id="SFSQ_${applyMater.MATER_CODE}">
									<option value="1"<c:if test="${applyMater.SFSQ==1}">selected="true"</c:if>>已收取</option>
									<option value="-1"<c:if test="${applyMater.SFSQ==-1}">selected="true"</c:if>>未收取</option>
									<option value="3"<c:if test="${applyMater.SFSQ==3}">selected="true"</c:if>>已核验</option>
								</select>
								<!--电子证照-->
								<c:if test="${fn:contains(applyMater.collect_method,'03')}">
									<div id="${applyMater.MATER_CODE}__LICENSE1" style="float:left ">
										<a href="javascript:LicenseList('${applyMater.MATER_CODE}')">
											<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/dzzz.png" style="width:73px;height:27px;"/>
										</a>
									</div>
								</c:if>
							</div>

						</td>
					</c:if>
					<c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
						<td style="text-align: center;">
							<c:if test="${applyMater.SFSQ==1}">已收取</c:if>
							<c:if test="${applyMater.SFSQ==3}">已核验</c:if>
							<c:if test="${applyMater.SFSQ!=1}">未收取</c:if>
						</td>
						<td  style="text-align: center;" width="100px" id="materAudit_${applyMater.MATER_CODE}">
							<c:if test="${applyMater.FILE_SHZT==1}">审核通过</c:if>
							<c:if test="${applyMater.FILE_SHZT==-1}">审核未通过</c:if>
							<c:if test="${applyMater.FILE_SHZT!=1&&applyMater.FILE_SHZT!=-1}">未审核</c:if>
						</td>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	</div>
</div>
