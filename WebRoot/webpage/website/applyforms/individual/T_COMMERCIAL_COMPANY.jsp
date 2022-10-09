<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="COMPANY_FORM"  >
<div class="syj-title1 ">
	<span>经营者信息</span>
</div>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th ><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[onlychinese]" onblur="setIndividualName()"
				name="DEALER_NAME" maxlength="16" value="${busRecord.DEALER_NAME}" placeholder="请输入姓名"/></td>
			<!--<th  rowspan="3"><font class="syj-color2">*</font>照片：</th>
			<td rowspan="3">
				<c:choose>
					<c:when test="${busRecord.DEALER_PHOTO!=null&&busRecord.DEALER_PHOTO!=''}">
						<img id="IMAGE_PATH_IMG" src="${_file_Server}${busRecord.DEALER_PHOTO}"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openDealerPthotoFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</c:when>
					<c:otherwise>
						<img id="IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
							height="140px" width="125px">
						<a href="javascript:void(0);" onclick="openDealerPthotoFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</c:otherwise>
				</c:choose>				
				<input type="hidden" class="validate[required]" name="DEALER_PHOTO" value="${busRecord.DEALER_PHOTO}">
			</td>
		</tr>
		<tr>-->
			<th ><font class="syj-color2">*</font>性别：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="sex"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择性别" name="DEALER_SEX" value="${busRecord.DEALER_SEX}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>身份证号码：</th>										
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard],custom[mustUpper]"
				name="DEALER_IDCARD" maxlength="30" value="${busRecord.DEALER_IDCARD}" placeholder="请输入身份证号码"/></td>
			<th ><font class="syj-color2">*</font>住所：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="DEALER_ADDR" maxlength="62" value="${busRecord.DEALER_ADDR}" placeholder="请输入住所"/></td>
		</tr>
		
		<tr>
			<th ><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]"
				name="DEALER_POSTCODE" maxlength="6" value="${busRecord.DEALER_POSTCODE}" placeholder="请输入邮政编码"/></td>
			<th ><font class="syj-color2">*</font>移动电话：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[mobilePhoneLoose]"
				name="DEALER_MOBILE" maxlength="16" value="${busRecord.DEALER_MOBILE}" placeholder="请输入移动电话"/></td>
		</tr>
		<tr>
			<th > 固定电话：</th>
			<td><input type="text" class="syj-input1 validate[],custom[fixPhoneWithAreaCode]"
				name="DEALER_FIXEDLINE" maxlength="16" value="${busRecord.DEALER_FIXEDLINE}" placeholder="请输入固定电话"/></td>
			<th > <font class="syj-color2">*</font>电子邮箱：</th>
			<td><input type="text" class="syj-input1 validate[[required],custom[email]]"
				name="DEALER_EMAIL" maxlength="32" value="${busRecord.DEALER_EMAIL}" placeholder="请输入电子邮箱"/></td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>政治面貌：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="political"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择政治面貌" name="DEALER_POLITICAL" value="${busRecord.DEALER_POLITICAL}">
				</eve:eveselect>
			</td>
			<th ><font class="syj-color2">*</font>民族：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="nation"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择民族" name="DEALER_NATION" value="${busRecord.DEALER_NATION}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>文化程度：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="degree"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择文化程度" name="DEALER_DEGREE" value="${busRecord.DEALER_DEGREE}">
				</eve:eveselect>
			</td>
			<th ><font class="syj-color2">*</font>职业状况：</th>
			<td>								
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="zyzk"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择职业状况" name="DEALER_JOB" value="${busRecord.DEALER_JOB}">
				</eve:eveselect>
			</td>
		</tr>
	</table>
</div>

</form>
