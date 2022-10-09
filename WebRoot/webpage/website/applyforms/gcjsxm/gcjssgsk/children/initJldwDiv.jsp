<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${jldwList}" var="jldw" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${jldw.CORPNAME}" onblur="setJldwInfo();setSupervisionUnits();addJlryDiv();setSgxkjbxx();"
				name="${s.index}CORPNAME" maxlength="100" placeholder="请输入单位名称" />
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('jldwDiv','${s.index}CORPNAME','${s.index}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${jldw.CORPCREDITCODE}" onblur="setJldwInfo()"
				name="${s.index}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>监理单位资质等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="QUALLEVEL"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择监理单位资质等级" name="${s.index}QUALLEVEL" value="${jldw.QUALLEVEL}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>监理单位资质证书号</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}QUALCERTNO"  placeholder="请输入监理单位资质证书号"  maxlength="32"   value="${jldw.QUALCERTNO}"/></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${jldw.ORGCODE}"
				name="${s.index}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
				name="${s.index}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${jldw.LEGAL_NAME}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}JLDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${s.index}LEGAL_IDTYPE" value="${jldw.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}JLDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${jldw.LEGAL_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>总监理工程师</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSupervisionUnits();setSgxkjbxx();"
				name="${s.index}PERSONNAME"  placeholder="请输入总监理工程师"   maxlength="16" value="${jldw.PERSONNAME}"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
				name="${s.index}PERSONPHONE"  placeholder="请输入总监理工程师电话号码"   maxlength="16" value="${jldw.PERSONPHONE}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}JLDW_PERSONIDCARD');"
				defaultEmptyText="请选择总监理工程师证件类型" name="${s.index}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${s.index}JLDW_PERSONIDCARD"  placeholder="请输入总监理工程师证件号码"  maxlength="32"   value="${jldw.PERSONIDCARD}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>执业资格等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="JLZYZGDJ"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择执业资格等级" name="${s.index}PERSONQUALLEVEL"   value="${jldw.PERSONQUALLEVEL}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>执业资格证书号</th>
			<td>
				<input type="text" class="syj-input1 validate[required]"
				name="${s.index}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"   value="${jldw.PERSONCERT}"/>
			</td>
		</tr>
		<tr>
			<th>安全生产考核<br/>合格证书编号</th>
			<td>	
				<input type="text" class="syj-input1"
				name="${s.index}CERTNUM"  placeholder="请输入安全生产考核合格证书编号"  maxlength="32"   value="${jldw.CERTNUM}"/>
			</td>	
			<th  rowspan="3"><font class="syj-color2">*</font>电子照片：</th>
			<td rowspan="3">
				<c:choose>
					<c:when test="${jldw.PERSONPHOTO!=null&&jldw.PERSONPHOTO!=''}">
						<img id="${s.index}JLDW_IMAGE_PATH_IMG" src="${_file_Server}${jldw.PERSONPHOTO}"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('${s.index}JLDW_IMAGE_PATH_IMG','${s.index}JLDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</c:when>
					<c:otherwise>
						<img id="${s.index}JLDW_IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('${s.index}JLDW_IMAGE_PATH_IMG','${s.index}JLDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</c:otherwise>
				</c:choose>				
				<input type="hidden" class="validate[required]" name="${s.index}JLDW_PERSONPHOTO" value="${jldw.PERSONPHOTO}">
				<div style="color:red;width:105%;">注意：此处照片应为总监理工程师个人电子证件照。</div>
			</td>	
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>	
			<th>标段名称</th>
			<td>
				<input type="text" class="syj-input1 validate[]" name="${s.index}SECTION" maxlength="16" placeholder="请输入标段名称" value="${jldw.SECTION}"/>
			</td>
			<th><font class="syj-color2">*</font>合同编号</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" name="${s.index}CONTRACTNUMBER" maxlength="32" placeholder="请输入合同编号" onblur="setContractNumber(this.value,1);" value="${jldw.CONTRACTNUMBER}"/>
			</td>		
		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>监理合同价（万元）</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]"   value="${jldw.CONTRACTCOST}"
				name="${s.index}CONTRACTCOST" maxlength="16" placeholder="请输入监理合同价（万元）"  onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/></td>
			<th><font class="syj-color2">*</font>中标通知书时间</th>
			<td>
				<input type="text" class="Wdate validate[required]"  readonly="readonly" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${jldw.BIDDINGDATE}"
				name="${s.index}BIDDINGDATE"  placeholder="请选择中标通知书时间"  maxlength="16"/>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0 || fn:substring(busRecord.PROJECT_CODE, 15, 17)=='88'}">
	<a  href="javascript:void(0);" onclick="javascript:delJldwDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>