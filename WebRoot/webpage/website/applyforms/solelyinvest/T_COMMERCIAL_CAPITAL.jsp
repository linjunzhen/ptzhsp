<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="CAPITAL_FORM"  >	
	<div class="syj-title1 ">
		<span>投资人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
			<tr>
				<th><font class="syj-color2">*</font>姓名：</th>
				<td>
					<input type="text" class="syj-input1 validate[required]"
						name="INVESTOR_NAME" maxlength="16" placeholder="请输入姓名" value="${busRecord.INVESTOR_NAME}"/>
				</td>
				<th><font class="syj-color2">*</font>性别：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="sex"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择性别" name="INVESTOR_SEX" value="${busRecord.INVESTOR_SEX}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>出生日期：</th>
				<td>
					<input type="text" class="Wdate" readonly="readonly" id="birthday" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						name="INVESTOR_BIRTHDAY"  placeholder="请输入出生日期" value="${busRecord.INVESTOR_BIRTHDAY}"/>
				</td>
				<th><font class="syj-color2">*</font>民族：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="nation"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择民族" name="INVESTOR_NATION" value="${busRecord.INVESTOR_NATION}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>文化程度：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="degree"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择文化程度" name="INVESTOR_DEGREE" value="${busRecord.INVESTOR_DEGREE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>政治面貌：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="political"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择政治面貌" name="INVESTOR_POLITICAL" value="${busRecord.INVESTOR_POLITICAL}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" maxlength="11"
					name="INVESTOR_MOBILE" placeholder="请输入移动电话" value="${busRecord.INVESTOR_MOBILE}"/></td>
				<th> 电子邮箱：</th>
				<td><input type="text" class="syj-input1 validate[[],custom[email]]" maxlength="32"
					name="INVESTOR_EMAIL" placeholder="请输入电子邮箱" value="${busRecord.INVESTOR_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect" id="INVESTOR_IDTYPE"
					defaultEmptyText="请选择证件类型" name="INVESTOR_IDTYPE"  onchange="setZjValidate(this.value,'INVESTOR_IDCARD');"
					  value="${busRecord.INVESTOR_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="INVESTOR_IDCARD" maxlength="30" placeholder="请输入证件号码"  value="${busRecord.INVESTOR_IDCARD}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>居所：</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]" 
					name="INVESTOR_ADDR" maxlength="64"  placeholder="请输入居所" value="${busRecord.INVESTOR_ADDR}"/></td>
			</tr>
			<tr>
				
				<th><font class="syj-color2">*</font>邮政编码：</th>
				<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]"
					name="INVESTOR_POSTCODE" maxlength="6" placeholder="请输入邮政编码" value="${busRecord.INVESTOR_POSTCODE}"/></td>
				<th><font class="syj-color2">*</font>申请前职业状况：</th>
				<td >					
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="zyzk"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择职业状况" name="INVESTOR_JOB" value="${busRecord.INVESTOR_JOB}">
					</eve:eveselect>
				</td>
			</tr>
		</table>
	</div>	
	
	
</form>
