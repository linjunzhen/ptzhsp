<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${zbdwList}" var="zbdw" varStatus="s">
<div class="addBox">
	<script type="text/javascript">
	$(function() {	
		setTenderClassNum('${zbdw.TENDERCLASSNUM}','${s.index}');
		setTenderTypeNum('${zbdw.TENDERTYPENUM}','${s.index}');
	});
	</script>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>招标类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TENDERCLASSNUM"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setTenderClassNum(this.value,'${s.index}');"
				defaultEmptyText="请选择招标类型" name="${s.index}TENDERCLASSNUM" value="${zbdw.TENDERCLASSNUM}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>招标方式</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TENDERTYPENUM"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setTenderTypeNum(this.value,'${s.index}');"
				defaultEmptyText="请选择招标方式" name="${s.index}TENDERTYPENUM" value="${zbdw.TENDERTYPENUM}">
				</eve:eveselect>
			</td>
		</tr>
		<tr class="${s.index}_zbdwxx" style="display:none;">
			<th><font class="syj-color2">*</font>中标单位</th>
			<td><input type="text" class="syj-input1" value="${zbdw.BIDDINGUNITNAME}"
				name="${s.index}BIDDINGUNITNAME" maxlength="100" placeholder="请输入中标单位"/></td>				
			<th><font class="syj-color2">*</font>监理中标通知单编号</th>
			<td><input type="text" class="syj-input1"  value="${zbdw.BIDDINGNOTICENUMPROVINCE}"
				name="${s.index}BIDDINGNOTICENUMPROVINCE" maxlength="25" placeholder="请输入监理中标通知单编号"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>中标日期</th>
			<td>				
				<input type="text" class="Wdate validate[required]"  readonly="readonly"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${zbdw.TENDERRESULTDATE}"
				name="${s.index}TENDERRESULTDATE"  placeholder="请选择中标日期"  maxlength="16"/>
			</td>				
			<th><font class="syj-color2">*</font>中标金额（万元）</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" value="${zbdw.TENDERMONEY}"
				name="${s.index}TENDERMONEY" maxlength="16" placeholder="请输中标金额（万元）" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"/></td>
		</tr>
		<tr class="${s.index}_zbdldwxx">
			<th><font class="syj-color2">*</font>招标代理单位</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${zbdw.CORPNAME}"
				name="${s.index}CORPNAME" maxlength="100" placeholder="请输入单位名称"/>
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('zbdwDiv','${s.index}CORPNAME','${s.index}CORPCREDITCODE');">查询</a>
			</td>				
			<th><font class="syj-color2">*</font>统一社会信用代码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${zbdw.CORPCREDITCODE}"
				name="${s.index}CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
		</tr>
		<tr class="${s.index}_zbdldwxx">
			<th>招标单位资质等级</th>
			<td>
				<input type="text" class="syj-input1 " value="${zbdw.QUALLEVEL}"
				name="${s.index}QUALLEVEL"  placeholder="请输入招标单位资质等级"  maxlength="32" />
			</td>
			<th>招标单位资质证书号</th>
			<td><input type="text" class="syj-input1 " value="${zbdw.QUALCERTNO}"
				name="${s.index}QUALCERTNO"  placeholder="请输入招标单位资质证书号"  maxlength="32" /></td>
		</tr>
		<tr class="${s.index}_zbdldwxx">	
			<th><font class="syj-color2">*</font>组织机构代码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${zbdw.ORGCODE}"
				name="${s.index}ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${zbdw.LEGAL_NAME}"
				name="${s.index}LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
		
		</tr>
		<tr class="${s.index}_zbdldwxx">
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}ZBDW_LEGAL_IDNO');"
				defaultEmptyText="请选择法定代表人证件类型" name="${s.index}LEGAL_IDTYPE"   value="${zbdw.LEGAL_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${zbdw.LEGAL_IDNO}"
				name="${s.index}ZBDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
		</tr>
		<tr class="${s.index}_zbdldwxx">
			<th><font class="syj-color2">*</font>项目负责人</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${zbdw.PERSONNAME}"
				name="${s.index}PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16"/></td>
			<th><font class="syj-color2">*</font>电话号码</th>
			<td><input type="text" class="syj-input1 validate[required]"  value="${zbdw.PERSONPHONE}"
				name="${s.index}PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16"/></td>
		
		</tr>
		<tr class="${s.index}_zbdldwxx">
			<th><font class="syj-color2">*</font>证件类型</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'${s.index}ZBDW_PERSONIDCARD');"
				defaultEmptyText="请选择项目负责人证件类型" name="${s.index}IDCARDTYPENUM" value="1" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]" value="${zbdw.PERSONIDCARD}"
				name="${s.index}ZBDW_PERSONIDCARD"  placeholder="请输入项目负责人证件号码"  maxlength="32" /></td>
		</tr>
		<tr class="${s.index}_zbdldwxx">
			<th>项目负责人资格等级</th>
			<td>
				<input type="text" class="syj-input1 validate[]" value="${zbdw.PERSONQUALLEVEL}"
				name="${s.index}PERSONQUALLEVEL"  placeholder="请输入项目负责人资格等级"  maxlength="32"  />
			</td>
			<th>执业资格证书号</th>
			<td>
				<input type="text" class="syj-input1 validate[]" value="${zbdw.PERSONCERT}"
				name="${s.index}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"  />
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delZbdwDiv(this);" class="syj-closebtn"></a>
</div>
</c:forEach>