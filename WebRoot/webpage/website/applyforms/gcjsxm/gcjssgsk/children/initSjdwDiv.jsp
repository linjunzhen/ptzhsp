<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${sjdwList}" var="sjdw" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${sjdw.CORPNAME}"  onblur="setSgxkjbxx();"
				name="${s.index}CORPNAME" maxlength="100" placeholder="请输入单位名称"/>
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('sjdwDiv','${s.index}CORPNAME','${s.index}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${sjdw.CORPCREDITCODE}"
				name="${s.index}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>设计机构资质等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="sjjgzzdj"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择设计机构资质等级" name="${s.index}QUALLEVEL" value="${sjdw.QUALLEVEL}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>设计机构资质证书号</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}QUALCERTNO"  placeholder="请输入设计机构资质证书号"  maxlength="32"   value="${sjdw.QUALCERTNO}"/></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${sjdw.ORGCODE}"
				name="${s.index}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${sjdw.LEGAL_NAME}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}SJDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${s.index}LEGAL_IDTYPE" value="${sjdw.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}SJDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${sjdw.LEGAL_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>项目负责人</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${sjdw.PERSONNAME}"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
				name="${s.index}PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16" value="${sjdw.PERSONPHONE}"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}SJDW_PERSONIDCARD');"
				defaultEmptyText="请选择项目负责人证件类型" name="${s.index}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${s.index}SJDW_PERSONIDCARD"  placeholder="请输入项目负责人证件号码"  maxlength="32"   value="${sjdw.PERSONIDCARD}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delSjdwDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>