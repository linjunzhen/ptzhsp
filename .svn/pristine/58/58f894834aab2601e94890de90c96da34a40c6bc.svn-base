<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${data.CORPNAME}"
				name="${currentTime}CORPNAME" maxlength="100" placeholder="请输入单位名称" style="width: 75%;"/>
				<c:if test="${isDel==1}">
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('${divId}','${currentTime}CORPNAME','${currentTime}CORPCREDITCODE');">查询</a>
				</c:if>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${data.CORPCREDITCODE}"
				name="${currentTime}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>监理单位资质等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="QUALLEVEL"
				dataInterface="dictionaryService.findDatasForSelect"  value="${data.QUALLEVEL}"
				defaultEmptyText="请选择监理单位资质等级" name="${currentTime}QUALLEVEL">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>监理单位资质证书号</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${data.QUALCERTNO}"
				name="${currentTime}QUALCERTNO"  placeholder="请输入监理单位资质证书号"  maxlength="32" /></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${data.ORGCODE}"
				name="${currentTime}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${data.LEGAL_NAME}"
				name="${currentTime}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="TBIDCARDTYPEDIC" value="${data.LEGAL_IDTYPE}"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}JLDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${currentTime}LEGAL_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${data.LEGAL_IDNO}"
				name="${currentTime}JLDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>总监理工程师</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${data.PERSONNAME}"
				name="${currentTime}PERSONNAME"  placeholder="请输入总监理工程师"   maxlength="16"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]"   value="${data.PERSONPHONE}"
				name="${currentTime}PERSONPHONE"  placeholder="请输入总监理工程师电话号码"   maxlength="16"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}JLDW_PERSONIDCARD');"
				defaultEmptyText="请选择总监理工程师证件类型" name="${currentTime}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]" value="${data.PERSONIDCARD}"
				name="${currentTime}JLDW_PERSONIDCARD"  placeholder="请输入总监理工程师证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>执业资格等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="JLZYZGDJ"
				dataInterface="dictionaryService.findDatasForSelect" value="${data.PERSONQUALLEVEL}"
				defaultEmptyText="请选择执业资格等级" name="${currentTime}PERSONQUALLEVEL"  >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>执业资格证书号</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" value="${data.PERSONCERT}"
				name="${currentTime}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"  />
			</td>
		</tr>
		<tr>
			<th>安全生产考核<br/>合格证书编号</th>
			<td>	
				<input type="text" class="syj-input1"
				name="${currentTime}CERTNUM"  placeholder="请输入安全生产考核合格证书编号" value="${data.CERTNUM}"  maxlength="32" />
			</td>	
			<th  rowspan="3"><font class="syj-color2">*</font>电子照片</th>
			<td rowspan="3">
				<c:choose>
					<c:when test="${data.PERSONPHOTO!=null&&data.PERSONPHOTO!=''}">
						<img id="${currentTime}JLDW_IMAGE_PATH_IMG" src="${_file_Server}${data.PERSONPHOTO}"
							height="140px" width="125px">
						<c:if test="${isDel==1}">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('${currentTime}JLDW_IMAGE_PATH_IMG','${currentTime}JLDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						</c:if>
					</c:when>
					<c:otherwise>
						<img id="${currentTime}JLDW_IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
							height="140px" width="125px">
							<c:if test="${isDel==1}">
							<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('${currentTime}JLDW_IMAGE_PATH_IMG','${currentTime}JLDW_PERSONPHOTO')">
								<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
							</a>
							</c:if>
					</c:otherwise>
				</c:choose>		
				<input type="hidden" class="validate[required]" name="${currentTime}JLDW_PERSONPHOTO"  value="${data.PERSONPHOTO}" />
				<div style="color:red;width:105%;">注意：此处照片应为总监理工程师个人电子证件照。</div>
			</td>	
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>	
			<th>标段名称</th>
			<td>
				<input type="text" class="syj-input1 validate[]" name="${currentTime}SECTION"  value="${data.SECTION}" maxlength="16" placeholder="请输入标段名称"/>
			</td>
			<th><font class="syj-color2">*</font>合同编号</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" name="${currentTime}CONTRACTNUMBER"  value="${data.CONTRACTNUMBER}" maxlength="32" placeholder="请输入合同编号"/>
			</td>		
		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>监理合同价（万元）</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]"   value="${data.CONTRACTCOST}" 
				name="${currentTime}CONTRACTCOST" maxlength="16" placeholder="请输入监理合同价"  onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/></td>
			<th><font class="syj-color2">*</font>中标通知书时间</th>
			<td>
				<input type="text" class="Wdate validate[required]"  readonly="readonly"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${data.BIDDINGDATE}" 
				name="${currentTime}BIDDINGDATE"  placeholder="请选择中标通知书时间"  maxlength="16"/>
			</td>
		</tr>
	</table>
	<c:if test="${isDel==1}">
	<a  href="javascript:void(0);" onclick="javascript:delJldwDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>