<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${sgdwList}" var="sgdw" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${sgdw.CORPNAME}" onblur="setSgdwInfo();setBuildUnits();addSgryDiv();setSgxkjbxx();"
				name="${s.index}CORPNAME" maxlength="100" placeholder="请输入单位名称" />
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('sgdwDiv','${s.index}CORPNAME','${s.index}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${sgdw.CORPCREDITCODE}" onblur="setSgdwInfo()"
				name="${s.index}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${sgdw.ORGCODE}"
				name="${s.index}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${sgdw.LEGAL_NAME}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}SGDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${s.index}LEGAL_IDTYPE" value="${sgdw.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}SGDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${sgdw.LEGAL_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>项目施工负责人<br/>（项目经理）</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setBuildUnits();setSgxkjbxx();"
				name="${s.index}PERSONNAME"  placeholder="请输入项目施工负责人"   maxlength="16" value="${sgdw.PERSONNAME}"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}PERSONPHONE"  placeholder="请输入项目施工负责人电话号码"   maxlength="16" value="${sgdw.PERSONPHONE}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}SGDW_PERSONIDCARD');"
				defaultEmptyText="请选择项目施工负责人证件类型" name="${s.index}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${s.index}SGDW_PERSONIDCARD"  placeholder="请输入项目施工负责人证件号码"  maxlength="32"   value="${sgdw.PERSONIDCARD}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>执业资格等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="PERSONQUALLEVEL"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择执业资格等级" name="${s.index}PERSONQUALLEVEL"   value="${sgdw.PERSONQUALLEVEL}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>执业资格证书号</th>
			<td>
				<input type="text" class="syj-input1 validate[required]"
				name="${s.index}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"   value="${sgdw.PERSONCERT}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>安全生产考核<br/>合格证书编号</th>
			<td>	
				<input type="text" class="syj-input1 validate[required]"
				name="${s.index}CERTNUM"  placeholder="请输入安全生产考核合格证书编号"  maxlength="32"   value="${sgdw.CERTNUM}"/>
			</td>	
			<th  rowspan="3"><font class="syj-color2">*</font>电子照片：</th>
			<td rowspan="3">
				<c:choose>
					<c:when test="${sgdw.PERSONPHOTO!=null&&sgdw.PERSONPHOTO!=''}">
						<img id="${s.index}SGDW_IMAGE_PATH_IMG" src="${_file_Server}${sgdw.PERSONPHOTO}"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('${s.index}SGDW_IMAGE_PATH_IMG','${s.index}SGDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</c:when>
					<c:otherwise>
						<img id="${s.index}SGDW_IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('${s.index}SGDW_IMAGE_PATH_IMG','${s.index}SGDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</c:otherwise>
				</c:choose>				
				<input type="hidden" class="validate[required]" name="${s.index}SGDW_PERSONPHOTO" value="${sgdw.PERSONPHOTO}">
				<div style="color:red;">注意：此处照片应为项目经理个人电子证件照。</div>
			</td>	
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>	
			<th>标段名称</th>
			<td>
				<input type="text" class="syj-input1 validate[]" name="${s.index}SECTION" maxlength="16" placeholder="请输入标段名称" value="${sgdw.SECTION}"/>
			</td>
			<th><font class="syj-color2">*</font>合同编号</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" name="${s.index}CONTRACTNUMBER" maxlength="32" placeholder="请输入合同编号" onblur="setContractNumber(this.value,2);" value="${sgdw.CONTRACTNUMBER}"/>
			</td>		
		</tr>
	</table>
</div>
</c:forEach>