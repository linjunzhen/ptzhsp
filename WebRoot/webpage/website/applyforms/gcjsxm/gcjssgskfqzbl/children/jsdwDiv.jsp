<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${data.CORPNAME}"
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
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${data.ORGCODE}"
				name="${currentTime}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${data.LEGAL_NAME}"
				name="${currentTime}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}LEGAL_IDNO');"
				defaultEmptyText="请选择证件类型" name="${currentTime}LEGAL_IDTYPE" id="${currentTime}LEGAL_IDTYPE"   value="${data.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${data.LEGAL_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>项目负责人</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${data.PERSONNAME}"/></td>
			<th><font class="syj-color2">*</font>负责人电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONPHONE"  placeholder="请输入负责人电话号码"   maxlength="16" value="${data.PERSONPHONE}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}PERSONIDCARD');"
				defaultEmptyText="请选择证件类型" name="${currentTime}IDCARDTYPENUM" id="${currentTime}IDCARDTYPENUM"   value="${data.IDCARDTYPENUM}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}PERSONIDCARD"  placeholder="请输入证件号码"  maxlength="32"   value="${data.PERSONIDCARD}"/></td>
		</tr>
	</table>
	<c:if test="${isDel==1}">
	<a  href="javascript:void(0);" onclick="javascript:delJsdwDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>