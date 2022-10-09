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
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('sgtscdwDiv','${currentTime}CORPNAME','${currentTime}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>施工图审查单位资质等级</th>
			<td>
				<input type="text" class="syj-input1 validate[required]"
				name="${currentTime}QUALLEVEL"  placeholder="请输入施工图审查单位资质等级"  maxlength="32" />
			</td>
			<th><font class="syj-color2">*</font>施工图审查单位资质证书号</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}QUALCERTNO"  placeholder="请输入施工图审查单位资质证书号"  maxlength="32" /></td>
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
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}SGTSCDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${currentTime}LEGAL_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}SGTSCDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>施工图审查人</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONNAME"  placeholder="请输入施工图审查人"   maxlength="16"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONPHONE"  placeholder="请输入施工图审查人电话号码"   maxlength="16"/></td>
		
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}SGTSCDW_PERSONIDCARD');"
				defaultEmptyText="请选择施工图审查人证件类型" name="${currentTime}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${currentTime}SGTSCDW_PERSONIDCARD"  placeholder="请输入施工图审查人师证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>注册类型及等级</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBSPECIALTYTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择注册类型及等级" name="${currentTime}SPECIALTYTYPNUM"  >
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delSgtscdwDiv(this);" class="syj-closebtn"></a>
</div>