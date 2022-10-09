<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位名称</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}CORPNAME" maxlength="100" placeholder="请输入单位名称"/>
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('kzjdwDiv','${currentTime}CORPNAME','${currentTime}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>造价机构资质等级</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="QUALLEVEL"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择造价机构资质等级" name="${currentTime}QUALLEVEL">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>造价机构资质证书号</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}QUALCERTNO"  placeholder="请输入造价机构资质证书号"  maxlength="32" /></td>
		</tr>
		<tr>	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}KZJDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${currentTime}LEGAL_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}KZJDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>造价工程师</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONNAME"  placeholder="请输入造价工程师"   maxlength="16"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONPHONE"  placeholder="请输入造价工程师电话号码"   maxlength="16"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}KZJDW_PERSONIDCARD');"
				defaultEmptyText="请选择造价工程师证件类型" name="${currentTime}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${currentTime}KZJDW_PERSONIDCARD"  placeholder="请输入造价工程师证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th>造价工程师资格等级</th>
			<td>
				<input type="text" class="syj-input1 validate[]"
				name="${currentTime}PERSONQUALLEVEL"  placeholder="请输入造价工程师资格等级"  maxlength="32"  />
			</td>
			<th>执业资格证书号</th>
			<td>
				<input type="text" class="syj-input1 validate[]"
				name="${currentTime}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"  />
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delKzjdwDiv(this);" class="syj-closebtn"></a>
</div>