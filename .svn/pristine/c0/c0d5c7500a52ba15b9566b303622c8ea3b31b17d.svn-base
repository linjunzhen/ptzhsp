<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="CAPITAL_FORM"  >
	<input type="hidden" name="PT_NAME" value="${busRecord.PT_NAME}"/>
	<input type="hidden" name="PT_HY" value="${busRecord.PT_HY}"/>
	<input type="hidden" name="PT_ZZXS" value="${busRecord.PT_ZZXS}"/>	
	<input type="hidden" name="IS_TEST" value="${busRecord.IS_TEST}"/>	
	<input type="hidden" name="SSSBLX" value="${sssblx}">
	<input type="hidden" name="PT_ID" value="${PT_ID}" />
	<input type="hidden" name="ISNEEDSIGN" value="0" />
	<input type="hidden" name="IS_CHECKCOMPANYNAME"  value="0"/>
	<input type="hidden" name="COMPANYNAME_PASS"  value="0"/>
	<input type="hidden" name="PUSH_STATUS_INTER"  value="0"/>
	<div class="syj-title1 ">
		<span>登记信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th ><font class="syj-color2">*</font>名称：</th>
				<input type="hidden" id= "INDIVIDUAL_ID" value="${busRecord.INDIVIDUAL_ID}"/>
				<td colspan="3"><input type="text" class="syj-input1 w878 validate[required],custom[onlychineseToFH],custom[verifyCompanyNameExist]"
					name="INDIVIDUAL_NAME" id="INDIVIDUAL_NAME" maxlength="256" value="${busRecord.INDIVIDUAL_NAME}" placeholder="请输入名称"/>
				</td>
			</tr>
			<tr id="wordNum" style="display:none;">
				<th ><font class="syj-color2">*</font>字号：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 validate[] w878" name="WORD_NUM" id="WORD_NUM" maxlength="128" value="${busRecord.WORD_NUM}" placeholder="请输入字号"/>						
					<eve:eveselect clazz="syj-input1 w280" dataParams="ZHNUM"
					   dataInterface="dictionaryService.findDatasForSelect" onchange="setIndividualName()"
					   defaultEmptyText="请选择字号数字" name="ZH_NUM"  value="${busRecord.ZH_NUM}">
					</eve:eveselect>
						<input type="text" class="syj-input1" style="width:150px;" name="WORD_NAME" id="WORD_NAME" maxlength="16" onblur="setIndividualName()" placeholder="请修改字号中的经营者名称"/>	
						<a onclick="checkCompanyName();"  class="btn1" style="width: 60px;display: none" id="checkCompanyNameId" href="javascript:void(0)">检验</a>
						<div id="checkNameDiv" style="color:red;"></div>						
					</span>
				</td>
			</tr>
			<tr class="oldtr">
				<th > 备选字号1：</th>
				<td colspan="3"><input type="text" class="syj-input1  w878"
					name="TAG_OPTIOIN1" maxlength="32" value="${busRecord.TAG_OPTIOIN1}" placeholder="请输入备选字号1"/></td>
			</tr>
			<tr class="oldtr">
				<th > 备选字号2：</th>
				<td colspan="3"><input type="text" class="syj-input1  w878"
					name="TAG_OPTIOIN2" maxlength="32"  value="${busRecord.TAG_OPTIOIN2}" placeholder="请输入备选字号2"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>经营形式：</th>
				<td colspan="3">
					<input type="radio" name="MANAGE_FORM" value="0" <c:if test="${busRecord.MANAGE_FORM!=1 }">checked="checked"</c:if> >个人经营
					<input type="radio" name="MANAGE_FORM" value="1" <c:if test="${busRecord.MANAGE_FORM==1 }">checked="checked"</c:if> >家庭经营
				</td>
			</tr>
		</table>
		<div  id="family" <c:if test="${busRecord.MANAGE_FORM!=1 }">style="display:none;"</c:if>>
			<div class="syj-title1 ">
				<a href="javascript:void(0);" onclick="javascript:addJtcyxxDiv();" class="syj-addbtn" id="addJtcyxx" >添 加</a><span>家庭成员信息</span>
			</div>
			<div id="jtcyxxDiv">
				<c:if test="${empty familyList}">
				<div style="position:relative;">	
					<table cellpadding="0" cellspacing="0" class="syj-table2 ">
						<tr>
							<th ><font class="syj-color2">*</font>姓名：</th>
							<td><input type="text" class="syj-input1 validate[required]" 
								name="FAMILY_NAME" maxlength="8" placeholder="请输入姓名"/></td>
							<th ><font class="syj-color2">*</font>身份证号码：</th>
							<td><input type="text" class="syj-input1 validate[required,custom[vidcard]]"
								name="FAMILY_IDNO" maxlength="32" placeholder="请输入身份证号码"/>
							</td>
						</tr>
					</table>
				</div>
				</c:if>				
				<jsp:include page="./initJtcyxxDiv.jsp"></jsp:include>
			</div>
		</div>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="table-layout: auto;">
							
			<tr class="hymlTr">
				<th><font class="syj-color2">*</font>主营范围：</th>
				<td colspan="3">
					<input type="text"  readonly="readonly" maxlength="16" id="MAIN_BUSSINESS_NAME"
						class="syj-input1 w878 validate[required]" placeholder="请选择主营范围" value="${busRecord.MAIN_BUSSINESS_NAME}" name="MAIN_BUSSINESS_NAME" />
					<input type="hidden" name="MAIN_BUSSINESS_ID" value="${busRecord.MAIN_BUSSINESS_ID}">
				</td>
			</tr>							
			<tr class="hymlTr">
				<th><font class="syj-color2">*</font>行业门类：</th>
				<td colspan="3">
						<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="ML_NAME"
						class="syj-input1 validate[required]" placeholder="行业门类" value="${busRecord.ML_NAME}" name="ML_NAME" />
						
						<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="DL_NAME"
						class="syj-input1 validate[required]" placeholder="行业大类" value="${busRecord.DL_NAME}" name="DL_NAME" />
						
						<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="ZL_NAME"
						class="syj-input1 validate[required]" placeholder="行业中类" value="${busRecord.ZL_NAME}" name="ZL_NAME" />
						
						<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="XL_NAME"
						class="syj-input1 validate[required]" placeholder="行业小类" value="${busRecord.XL_NAME}" name="XL_NAME" />
				</td>
			</tr>				
			<tr>
				<th ><font class="syj-color2">*</font>经营范围：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="BUSINESS_SCOPE" readonly="readonly"
						class="eve-textarea w878 validate[validate[required],maxSize[512]]"
						style="height:100px;" placeholder="请选择经营范围" >${busRecord.BUSINESS_SCOPE}</textarea> 
					<input type="hidden" name="BUSINESS_SCOPE_ID" value="${busRecord.BUSINESS_SCOPE_ID}">
					<a href="javascript:showSelectJyfwNew();" class="btn1" id="yjfwA">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请</div>
				</td>
			</tr>		
			<!--<tr>
				<th> 经营范围备注描述：</th>
				<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
					name="SCOPE_REMARK" maxlength="128" placeholder="请输入经营范围备注描述" value="${busRecord.SCOPE_REMARK}"/></td>
			</tr>-->					
			<tr class="newtr">
				<th >辖区分局：</th>
				<td colspan="3">
					<eve:eveselect clazz="syj-input1 w878 validate[]" dataParams="1"
					   dataInterface="jurisdictionService.findDatasForSelect"
					   defaultEmptyText="请选择辖区分局" name="XQ_NAME"  value="${busRecord.XQ_NAME}">
					</eve:eveselect>
				</td>
			</tr>	
			<tr>
				<th ><font class="syj-color2">*</font>经营场所地址：</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
						name="BUSINESS_PLACE" maxlength="60" value="${busRecord.BUSINESS_PLACE}" placeholder="请输入经营场所地址"/></td>
			</tr>
			<tr>
				<th> <font class="syj-color2">*</font>法律文书送达地址(同经营场所地址)：</th>
				<td >
					<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="1"  onclick="setSendLawAddr('1');"
							<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
							<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
				</td>
				<th><font class="syj-color2">*</font>法律文书送达地址：</th>
				<td>
					<input  type="text" class="syj-input1 validate[required]"
							name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/>
				</td>
			</tr>
			<tr>
				<th> <font class="syj-color2">*</font>法律文书电子送达地址：</th>
			</tr>
			<tr>	
				<th> <font class="syj-color2">*</font>邮箱：</th>
				<td>
					<input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
					 name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
				</td>
			</tr>
			<tr>	
				<th> <font class="syj-color2">*</font>传真：</th>
				<td>
					<input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
					name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
				</td>
			</tr>
			<tr>	
				<th> <font class="syj-color2">*</font>即时通讯账号（如微信）：</th>
				<td>
					<input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
					 name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
				</td>
			</tr>
			<script type="text/javascript">
				function setSendLawAddr() {
					var val = $("[name='IS_REGISTER_ADDR']:checked").val();
					if(val=='1'){
						$("input[name='LAW_SEND_ADDR']").val($("input[name='BUSINESS_PLACE']").val());
						$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
					}else{
						$("input[name='LAW_SEND_ADDR']").removeAttr("disabled");
					}
				}
				$(function () {
					setSendLawAddr();
				})
			</script>			
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>房屋所有权人名称：</th>
				<td><input  type="text" class="syj-input1  validate[required]"
							name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
				<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
				<td><input type="text" class="syj-input1 validate[required]"
						   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>

			</tr>
			<tr>
				<th><font class="syj-color2">*</font>取得方式：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="wayOfGet"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>面积：</th>
				<td>
					<input type="text" class="syj-input1 validate[required]" maxlength="16"
						   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否属于军队房产：</th>
				<td >
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="armyHourse"
								   dataInterface="dictionaryService.findDatasForSelect" onchange="setRequired(this.value,'ARMYHOURSE_REGISTER_REMARKS','03')"
								   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${busRecord.ARMY_REGISTER_HOURSE}">
					</eve:eveselect>
				</td>
				<th>其他证明：</th>
				<td >
					<input type="text" class="syj-input1" disabled
						   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REGISTER_REMARKS}"/>
				</td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>邮政编码：</th>
				<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]"
					name="PLACE_POSTCODE" maxlength="6" value="${busRecord.PLACE_POSTCODE}" placeholder="请输入邮政编码"/></td>
				<th > 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[],custom[fixPhoneWithAreaCode]"
					name="PLACE_PHONE" maxlength="16" value="${busRecord.PLACE_PHONE}" placeholder="请输入固定电话"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>从业人数（人）：</th>
				<td><input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="EMPLOYED_NUM" maxlength="8" value="${busRecord.EMPLOYED_NUM}" placeholder="请输入从业人数（人）"/></td>
				<th ><font class="syj-color2">*</font>资金数额（万元）：</th>
				<td><input type="text" class="syj-input1 validate[required,custom[JustNumber]]" 
					name="CAPITAL_AMOUNT" maxlength="8" value="${busRecord.CAPITAL_AMOUNT}" placeholder="请输入资金数额（万元）"/></td>
			</tr>
		</table>
	</div>
	<div id="lyydiv">
		<div class="syj-title1 " style="height:30px;">
			<span>联络员/委托人信息(<font color="#ff0101">*</font>为必填)</span>
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1   validate[required]"
						name="LIAISON_NAME" maxlength="16"  placeholder="请输入姓名" value="${busRecord.LIAISON_NAME}"/></td>
					<th><font class="syj-color2">*</font>手机号码：</th>
					<td>
						<input type="text" class="syj-input1   validate[required],custom[mobilePhoneLoose]" maxlength="11"
							   name="LIAISON_MOBILE" placeholder="请输入手机号码" value="${busRecord.LIAISON_MOBILE}"/>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>身份证号码：</td>
					<td><input  type="text" class="syj-input1    validate[required],custom[vidcard]"
								name="LIAISON_IDNO"  placeholder="请输入身份证号码"  maxlength="18"  value="${busRecord.LIAISON_IDNO}"/></td>
					<th><font class="syj-color2">*</font>住所：</td>
					<td><input type="text" class="syj-input1   validate[required]" 
							   name="LIAISON_ADDR"  placeholder="请输入住所"  maxlength="32"  value="${busRecord.LIAISON_ADDR}"/></td>

				</tr>
				<tr>
					<th><font class="syj-color2">*</font>身份证正面：</th>
					<td>							
						<c:choose>
							<c:when test="${busRecord.LIAISON_SFZZM!=null&&busRecord.LIAISON_SFZZM!=''}">
								<img id="LIAISON_SFZZM_PATH_IMG" src="${_file_Server}${busRecord.LIAISON_SFZZM}"
									height="140px" width="220px">
							</c:when>
							<c:otherwise>
								<img id="LIAISON_SFZZM_PATH_IMG" src="webpage/common/images/nopic.jpg"
									height="140px" width="220px">
							</c:otherwise>
						</c:choose>	
						<a href="javascript:void(0);" onclick="openGtptxxglImageUploadDialog('LIAISON_SFZZM')" id="sfzzmScA">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>			
						<input type="hidden" class="validate[required]" name="LIAISON_SFZZM" value="${busRecord.LIAISON_SFZZM}">
					</td>
					<th><font class="syj-color2">*</font>身份证反面：</th>
					<td>							
						<c:choose>
							<c:when test="${busRecord.LIAISON_SFZFM!=null&&busRecord.LIAISON_SFZFM!=''}">
								<img id="LIAISON_SFZFM_PATH_IMG" src="${_file_Server}${busRecord.LIAISON_SFZFM}"
									height="140px" width="220px">
							</c:when>
							<c:otherwise>
								<img id="LIAISON_SFZFM_PATH_IMG" src="webpage/common/images/nopic.jpg"
									height="140px" width="220px">
							</c:otherwise>
						</c:choose>		
						<a href="javascript:void(0);" onclick="openGtptxxglImageUploadDialog('LIAISON_SFZFM')" id="sfzfmScA">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>		
						<input type="hidden" class="validate[required]" name="LIAISON_SFZFM" value="${busRecord.LIAISON_SFZFM}">
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
