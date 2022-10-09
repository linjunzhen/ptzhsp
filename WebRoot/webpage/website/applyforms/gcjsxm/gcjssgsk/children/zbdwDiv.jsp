<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>招标类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TENDERCLASSNUM"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setTenderClassNum(this.value,'${currentTime}');"
				defaultEmptyText="请选择招标类型" name="${currentTime}TENDERCLASSNUM">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>招标方式</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TENDERTYPENUM"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setTenderTypeNum(this.value,'${currentTime}');"
				defaultEmptyText="请选择招标方式" name="${currentTime}TENDERTYPENUM">
				</eve:eveselect>
			</td>
		</tr>
		<tr class="${currentTime}_zbdwxx" style="display:none;">
			<th><font class="syj-color2">*</font>中标单位</th>
			<td><input type="text" class="syj-input1"
				name="${currentTime}BIDDINGUNITNAME" maxlength="100" placeholder="请输入中标单位"/></td>				
			<th><font class="syj-color2">*</font>监理中标通知单编号</th>
			<td><input type="text" class="syj-input1" 
				name="${currentTime}BIDDINGNOTICENUMPROVINCE" maxlength="25" placeholder="请输入监理中标通知单编号"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>中标日期</th>
			<td>				
				<input type="text" class="Wdate validate[required]"  readonly="readonly"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
				name="${currentTime}TENDERRESULTDATE"  placeholder="请选择中标日期"  maxlength="16"/>
			</td>				
			<th><font class="syj-color2">*</font>中标金额（万元）</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" 
				name="${currentTime}TENDERMONEY" maxlength="16" placeholder="请输中标金额（万元）" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"/></td>
		</tr>
		<tr class="${currentTime}_zbdldwxx">
			<th><font class="syj-color2">*</font>招标代理单位</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}CORPNAME" maxlength="100" placeholder="请输入单位名称" />
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('zbdwDiv','${currentTime}CORPNAME','${currentTime}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr class="${currentTime}_zbdldwxx">
			<th>招标单位资质等级</th>
			<td>
				<input type="text" class="syj-input1"
				name="${currentTime}QUALLEVEL"  placeholder="请输入招标单位资质等级"  maxlength="32" />
			</td>
			<th>招标单位资质证书号</th>
			<td><input type="text" class="syj-input1 "
				name="${currentTime}QUALCERTNO"  placeholder="请输入招标单位资质证书号"  maxlength="32" /></td>
		</tr>
		<tr class="${currentTime}_zbdldwxx">	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
		
		</tr>
		<tr class="${currentTime}_zbdldwxx">
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}ZBDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${currentTime}LEGAL_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}ZBDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
		</tr>
		<tr class="${currentTime}_zbdldwxx">
			<th><font class="syj-color2">*</font>项目负责人</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16"/></td>
		
		</tr>
		<tr class="${currentTime}_zbdldwxx">
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${currentTime}ZBDW_PERSONIDCARD');"
				defaultEmptyText="请选择项目负责人证件类型" name="${currentTime}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
				name="${currentTime}ZBDW_PERSONIDCARD"  placeholder="请输入项目负责人证件号码"  maxlength="32" /></td>
		</tr>
		<tr class="${currentTime}_zbdldwxx">
			<th>项目负责人资格等级</th>
			<td>
				<input type="text" class="syj-input1 validate[]"
				name="${currentTime}PERSONQUALLEVEL"  placeholder="请输入项目负责人资格等级"  maxlength="32"  />
			</td>
			<th>执业资格证书号</th>
			<td>
				<input type="text" class="syj-input1 validate[]"
				name="${currentTime}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"  />
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delZbdwDiv(this);" class="syj-closebtn"></a>
</div>