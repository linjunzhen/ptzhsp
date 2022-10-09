<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${jsdwList}" var="jsdw" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}CORPNAME" maxlength="100" placeholder="请输入单位名称" value="${jsdw.CORPNAME}" />
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('JsdwDiv','${s.index}CORPNAME','${s.index}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
				name="${s.index}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码" value="${jsdw.CORPCREDITCODE}"/></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}ORGCODE" maxlength="32" placeholder="请输入组织机构代码" value="${jsdw.ORGCODE}"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
				name="${s.index}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${jsdw.LEGAL_NAME}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required] w280" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}JSDW_LEGAL_IDNO');"
				defaultEmptyText="请选择证件类型" name="${s.index}LEGAL_IDTYPE" id="${s.index}LEGAL_IDTYPE"   value="${jsdw.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}JSDW_LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${jsdw.LEGAL_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>项目负责人</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
				name="${s.index}PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${jsdw.PERSONNAME}"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
				name="${s.index}PERSONPHONE"  placeholder="请输入负责人电话号码"   maxlength="16" value="${jsdw.PERSONPHONE}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required] w280" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}JSDW_PERSONIDCARD');"
				defaultEmptyText="请选择证件类型" name="${s.index}IDCARDTYPENUM" id="${s.index}IDCARDTYPENUM"   value="${jsdw.IDCARDTYPENUM}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}JSDW_PERSONIDCARD"  placeholder="请输入证件号码"  maxlength="32"   value="${jsdw.PERSONIDCARD}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delJsdwDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>