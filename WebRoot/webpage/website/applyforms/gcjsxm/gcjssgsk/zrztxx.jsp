<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>

.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
</style>
<form action="" id="ZRZTXX_FORM"  >
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addJsdwDiv();" class="syj-addbtn" id="addJsdw" >添 加</a>
		<span>建设单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'JsdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="JsdwDiv">
		<c:if test="${empty jsdwList}">
		<div style="position:relative;">	
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr>
					<th><font class="syj-color2">*</font>单位名称</th>
					<td>
						<input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
						name="CORPNAME" maxlength="100" placeholder="请输入单位名称" />
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('JsdwDiv','CORPNAME','CORPCREDITCODE');">查询</a>
					</td>				
					<th><font class="syj-color2">*</font>统一社会信用代码</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
				</tr>
				<tr>	
					<th><font class="syj-color2">*</font>组织机构代码</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
					<th><font class="syj-color2">*</font>法定代表人姓名</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${busRecord.LEGAL_NAME}"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'LEGAL_IDNO');"
						defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${busRecord.LEGAL_IDTYPE}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.LEGAL_IDNO}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>项目负责人</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${busRecord.PERSONNAME}"/></td>
					<th><font class="syj-color2">*</font>负责人电话号码</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="PERSONPHONE"  placeholder="请输入负责人电话号码"   maxlength="16" value="${busRecord.PERSONPHONE}"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'PERSONIDCARD');"
						defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM" id="IDCARDTYPENUM"   value="${busRecord.IDCARDTYPENUM}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="PERSONIDCARD"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.PERSONIDCARD}"/></td>
				</tr>
			</table>
		</div>
		</c:if>
		<c:if test="${!empty jsdwList}">
			<jsp:include page="./children/initJsdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addDjdwDiv();" class="syj-addbtn">添 加</a><span>代建、工程总承包（EPC）、PPP等单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'DjdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="DjdwDiv">
		<c:if test="${!empty djdwList}">
			<jsp:include page="./children/initDjdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	
	<div class="syj-title1">
		<span>施工单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'sgdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="sgdwDiv">
		<c:if test="${empty sgdwList}">
		<div style="position:relative;">	
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr> 
					<th><font class="syj-color2">*</font>单位名称</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgdwInfo();setBuildUnits();addSgryDiv();setSgxkjbxx();"
						name="CORPNAME" maxlength="100" placeholder="请输入单位名称" />
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('sgdwDiv','CORPNAME','CORPCREDITCODE');">查询</a>
					</td>				
					<th><font class="syj-color2">*</font>统一社会信用代码</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgdwInfo();"
						name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
				</tr>
				<tr>	
					<th><font class="syj-color2">*</font>组织机构代码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
					<th><font class="syj-color2">*</font>法定代表人姓名</th>
					<td><input type="text" class="syj-input1 validate[required]" onblur="setSgxkjbxx();"
						name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${busRecord.LEGAL_NAME}"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'SGDW_LEGAL_IDNO');"
						defaultEmptyText="请选择法定代表人证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="SGDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${busRecord.LEGAL_IDNO}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>项目施工负责人<br/>（项目经理）</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setBuildUnits();setSgxkjbxx();"
						name="PERSONNAME"  placeholder="请输入项目施工负责人"   maxlength="16" value="${busRecord.PERSONNAME}"/></td>
					<th><font class="syj-color2">*</font>电话号码</th>
					<td><input type="text" class="syj-input1 validate[required]" onblur="setSgxkjbxx();"
						name="PERSONPHONE"  placeholder="请输入项目施工负责人电话号码"   maxlength="16" value="${busRecord.PERSONPHONE}"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'SGDW_PERSONIDCARD');"
						defaultEmptyText="请选择项目施工负责人证件类型" name="IDCARDTYPENUM" value="1" >
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
						name="SGDW_PERSONIDCARD"  placeholder="请输入项目施工负责人证件号码"  maxlength="32"   value="${busRecord.PERSONIDCARD}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>执业资格等级</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="PERSONQUALLEVEL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择执业资格等级" name="PERSONQUALLEVEL"   value="${busRecord.PERSONQUALLEVEL}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>执业资格证书号</th>
					<td>
						<input type="text" class="syj-input1 validate[required]"
						name="PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32" value="${busRecord.PERSONCERT}"/>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>安全生产考核<br/>合格证书编号</th>
					<td>	
						<input type="text" class="syj-input1 validate[required]"
						name="CERTNUM"  placeholder="请输入安全生产考核合格证书编号"  maxlength="32"/>
					</td>		
					<th  rowspan="3"><font class="syj-color2">*</font>电子照片：</th>
					<td rowspan="3">
						<img id="SGDW_IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('SGDW_IMAGE_PATH_IMG','SGDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>	
						<input type="hidden" class="validate[required]" name="SGDW_PERSONPHOTO">
						<div style="color:red;width:105%;">注意：此处照片应为项目经理个人电子证件照。</div>
					</td>	
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>	
					<th>标段名称</th>
					<td>
						<input type="text" class="syj-input1 validate[]" name="SECTION" maxlength="16" placeholder="请输入标段名称" />
					</td>
					<th><font class="syj-color2">*</font>合同编号</th>
					<td>
						<input type="text" class="syj-input1 validate[required]" name="CONTRACTNUMBER" maxlength="32" placeholder="请输入合同编号"  onblur="setContractNumber(this.value,2);"/>
					</td>		
				</tr>
			</table>
		</div>
		</c:if>
		<c:if test="${!empty sgdwList}">
			<jsp:include page="./children/initSgdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addJldwDiv();" class="syj-addbtn">添 加</a><span>监理单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'jldwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="jldwDiv">
		<c:if test="${empty jldwList}">
		<div style="position:relative;">	
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr>
					<th><font class="syj-color2">*</font>单位名称</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setJldwInfo();setSupervisionUnits();addJlryDiv();setSgxkjbxx();"
						name="CORPNAME" maxlength="100" placeholder="请输入单位名称" />
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('jldwDiv','CORPNAME','CORPCREDITCODE');">查询</a>
					</td>				
					<th><font class="syj-color2">*</font>统一社会信用代码</th>
					<td><input type="text" class="syj-input1 validate[required]"   onblur="setJldwInfo();"
						name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>监理单位资质等级</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="QUALLEVEL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择监理单位资质等级" name="QUALLEVEL" value="${jldw.QUALLEVEL}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>监理单位资质证书号</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="QUALCERTNO"  placeholder="请输入监理单位资质证书号"  maxlength="32"   value="${jldw.QUALCERTNO}"/></td>
				</tr>
				<tr>	
					<th><font class="syj-color2">*</font>组织机构代码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
					<th><font class="syj-color2">*</font>法定代表人姓名</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${busRecord.LEGAL_NAME}"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'SGDW_LEGAL_IDNO');"
						defaultEmptyText="请选择法定代表人证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="SGDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${busRecord.LEGAL_IDNO}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>总监理工程师</th>
					<td><input type="text" class="syj-input1 validate[required]" onblur="setSupervisionUnits();setSgxkjbxx();"
						name="PERSONNAME"  placeholder="请输入总监理工程师"   maxlength="16" value="${busRecord.PERSONNAME}"/></td>
					<th><font class="syj-color2">*</font>电话号码</th>
					<td><input type="text" class="syj-input1 validate[required]" onblur="setSgxkjbxx();"
						name="PERSONPHONE"  placeholder="请输入总监理工程师电话号码"   maxlength="16" value="${busRecord.PERSONPHONE}"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'SGDW_PERSONIDCARD');"
						defaultEmptyText="请选择总监理工程师证件类型" name="IDCARDTYPENUM" value="1" >
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
						name="SGDW_PERSONIDCARD"  placeholder="请输入总监理工程师证件号码"  maxlength="32"   value="${busRecord.PERSONIDCARD}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>执业资格等级</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="JLZYZGDJ"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择执业资格等级" name="PERSONQUALLEVEL"   value="1">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>执业资格证书号</th>
					<td>
						<input type="text" class="syj-input1 validate[required]"
						name="PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"   value="${busRecord.PERSONCERT}"/>
					</td>
				</tr>
				<tr>
					<th>安全生产考核<br/>合格证书编号</th>
					<td>	
						<input type="text" class="syj-input1"
						name="CERTNUM"  placeholder="请输入安全生产考核合格证书编号"  maxlength="32"   value="${busRecord.CERTNUM}"/>
					</td>		
					<th  rowspan="3"><font class="syj-color2">*</font>电子照片：</th>
					<td rowspan="3">
						<img id="JLDW_IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('JLDW_IMAGE_PATH_IMG','JLDW_PERSONPHOTO')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>	
						<input type="hidden" class="validate[required]" name="JLDW_PERSONPHOTO">
						<div style="color:red;width:105%;">注意：此处照片应为总监理工程师个人电子证件照。</div>
					</td>	
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>	
					<th>标段名称</th>
					<td>
						<input type="text" class="syj-input1 validate[]" name="${currentTime}SECTION" maxlength="16" placeholder="请输入标段名称"/>
					</td>
					<th><font class="syj-color2">*</font>合同编号</th>
					<td>
						<input type="text" class="syj-input1 validate[required]" name="${currentTime}CONTRACTNUMBER" maxlength="32" placeholder="请输入合同编号" onblur="setContractNumber(this.value,1);"/>
					</td>		
				</tr>	
				<tr>
					<th><font class="syj-color2">*</font>监理合同价（万元）</th>
					<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" 
						name="${currentTime}CONTRACTCOST" maxlength="16" placeholder="请输入监理合同价"  onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/></td>
					<th><font class="syj-color2">*</font>中标通知书时间</th>
					<td>
						<input type="text" class="Wdate validate[required]"  readonly="readonly"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
						name="${currentTime}BIDDINGDATE"  placeholder="请选择中标通知书时间"  maxlength="16"/>
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		<c:if test="${!empty jldwList}">
			<jsp:include page="./children/initJldwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addKcdwDiv();" class="syj-addbtn">添 加</a><span>勘察单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'kcdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="kcdwDiv">		
		<c:if test="${empty kcdwList}">	
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr>
					<th><font class="syj-color2">*</font>单位名称</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="CORPNAME" maxlength="100" placeholder="请输入单位名称" />
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('kcdwDiv','CORPNAME','CORPCREDITCODE');">查询</a>
					</td>				
					<th><font class="syj-color2">*</font>统一社会信用代码</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>勘察单位资质等级</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="KCDWZZDJ"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择勘察单位资质等级" name="QUALLEVEL">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>勘察单位资质证书号</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="QUALCERTNO"  placeholder="请输入勘察单位资质证书号"  maxlength="32" /></td>
				</tr>
				<tr>	
					<th><font class="syj-color2">*</font>组织机构代码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
					<th><font class="syj-color2">*</font>法定代表人姓名</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'KCDW_LEGAL_IDNO');"
						defaultEmptyText="请选择法定代表人证件类型" name="LEGAL_IDTYPE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="KCDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>项目负责人</th>
					<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
						name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16"/></td>
					<th><font class="syj-color2">*</font>电话号码</th>
					<td><input type="text" class="syj-input1 validate[required]" onblur="setSgxkjbxx();"
						name="PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'KCDW_PERSONIDCARD');"
						defaultEmptyText="请选择项目负责人证件类型" name="IDCARDTYPENUM" value="1" >
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required],custom[vidcard]" 
						name="KCDW_PERSONIDCARD"  placeholder="请输入项目负责人师证件号码"  maxlength="32" /></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>注册类型及等级</th>
					<td colspan="3">
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBSPECIALTYTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择注册类型及等级" name="SPECIALTYTYPNUM"  >
						</eve:eveselect>
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		<c:if test="${!empty kcdwList}">
			<jsp:include page="./children/initKcdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addSjdwDiv();" class="syj-addbtn">添 加</a><span>设计单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'sjdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="sjdwDiv">			
		<c:if test="${empty sjdwList}">	
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr>
					<th><font class="syj-color2">*</font>单位名称</th>
					<td><input type="text" class="syj-input1 validate[required]"  onblur="setSgxkjbxx();"
						name="CORPNAME" maxlength="100" placeholder="请输入单位名称" />
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('sjdwDiv','CORPNAME','CORPCREDITCODE');">查询</a>
					</td>				
					<th><font class="syj-color2">*</font>统一社会信用代码</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>设计机构资质等级</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="sjjgzzdj"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择设计机构资质等级" name="QUALLEVEL">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>设计机构资质证书号</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="QUALCERTNO"  placeholder="请输入设计机构资质证书号"  maxlength="32" /></td>
				</tr>
				<tr>	
					<th><font class="syj-color2">*</font>组织机构代码</th>
					<td><input type="text" class="syj-input1 validate[required]" value="${jldw.PERSONIDCARD}"
						name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/></td>
					<th><font class="syj-color2">*</font>法定代表人姓名</th>
					<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
						name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'SJDW_LEGAL_IDNO');"
						defaultEmptyText="请选择法定代表人证件类型" name="LEGAL_IDTYPE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="SJDW_LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" /></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>项目负责人</th>
					<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
						name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16"/></td>
					<th><font class="syj-color2">*</font>电话号码</th>
					<td><input type="text" class="syj-input1 validate[required]"   onblur="setSgxkjbxx();"
						name="PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16"/></td>
				
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'SJDW_PERSONIDCARD');"
						defaultEmptyText="请选择项目负责人证件类型" name="IDCARDTYPENUM" value="1" >
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码</th>
					<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
						name="SJDW_PERSONIDCARD"  placeholder="请输入项目负责人证件号码"  maxlength="32" /></td>
				</tr>
			</table>
		</div>
		</c:if>
		<c:if test="${!empty sjdwList}">
			<jsp:include page="./children/initSjdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addSgtscdwDiv();" class="syj-addbtn">添 加</a><span>施工图审查单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'sgtscdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="sgtscdwDiv">		
		<c:if test="${!empty sgtscdwList}">
			<jsp:include page="./children/initSgtscdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addKzjdwDiv();" class="syj-addbtn">添 加</a><span>控制价（预算价）计价文件编制单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'kzjdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="kzjdwDiv">		
		<c:if test="${!empty kzjdwList}">
			<jsp:include page="./children/initKzjdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addJcdwDiv();" class="syj-addbtn">添 加</a><span>检测单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'jcdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="jcdwDiv">		
		<c:if test="${!empty jcdwList}">
			<jsp:include page="./children/initJcdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	<div class="syj-title1">
		<a href="javascript:void(0);" onclick="javascript:addZbdwDiv();" class="syj-addbtn">添 加</a><span>招标代理单位</span>
		<a href="javascript:void(0);" onclick="javascript:showOrHide(this,'zbdwDiv');"  class="projectBtnA">收 起</a>
	</div>
	<div id="zbdwDiv">		
		<c:if test="${!empty zbdwList}">
			<jsp:include page="./children/initZbdwDiv.jsp"></jsp:include>		
		</c:if>
	</div>
</form>
