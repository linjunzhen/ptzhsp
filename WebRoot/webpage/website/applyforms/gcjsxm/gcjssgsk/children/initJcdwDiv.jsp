<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${jcdwList}" var="jcdw" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${jcdw.CORPNAME}"
				name="${s.index}CORPNAME" maxlength="100" placeholder="请输入单位名称" />
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('jcdwDiv','${s.index}CORPNAME','${s.index}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${jcdw.CORPCREDITCODE}"
				name="${s.index}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>检测单位资质等级</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" value="${jcdw.QUALLEVEL}"
				name="${s.index}QUALLEVEL"  placeholder="请输入检测单位资质等级"  maxlength="32" />
			</td>
			<th><font class="syj-color2">*</font>检测单位资质证书号</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}QUALCERTNO"  placeholder="请输入检测单位资质证书号"  maxlength="32"   value="${jcdw.QUALCERTNO}"/></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${jcdw.ORGCODE}"
				name="${s.index}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${jcdw.LEGAL_NAME}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}JCDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${s.index}LEGAL_IDTYPE" value="${jcdw.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}JCDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${jcdw.LEGAL_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>项目负责人</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${jcdw.PERSONNAME}"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16" value="${jcdw.PERSONPHONE}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}JCDW_PERSONIDCARD');"
				defaultEmptyText="请选择项目负责人证件类型" name="${s.index}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${s.index}JCDW_PERSONIDCARD"  placeholder="请输入项目负责人证件号码"  maxlength="32"   value="${jcdw.PERSONIDCARD}"/></td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delJcdwDiv(this);" class="syj-closebtn"></a>
</div>
</c:forEach>