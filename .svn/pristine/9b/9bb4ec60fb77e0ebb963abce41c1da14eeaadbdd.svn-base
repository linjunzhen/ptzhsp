<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
	.w703{
		width: 703px!important;
	}
	.btn1{
		height: 40px;
		line-height: 40px;
	}
</style>
<form action="" id="COMPANY_FORM"  >

	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>

			<th><font class="syj-color2">*</font>企业名称：</th>
			<input type="hidden" class="syj-input1 w878 validate[required]" 
				   name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称" value="${busRecord.COMPANY_NAME}"/>
			<td colspan="3">
				<input type="hidden" name="MAIN_INDUSTRY_ID"  value="${busRecord.MAIN_INDUSTRY_ID}"/>
				<input type="hidden" id= "COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
				<input type="hidden" name= "CHILD_INDUSTRY_ID" value="${busRecord.CHILD_INDUSTRY_ID}"/>
				<input type="text" class="syj-input1 validate[required]" style="margin-bottom: 5px; width: 270px;"
					   name="AREA_NAME"  maxlength="64" placeholder="请输入行政区划" value="平潭综合实验区" readonly/>
				<input type="text" class="syj-input1 validate[required,maxSize[8],minSize[2]],custom[onlychinese],custom[verifyCompanyNameExist]"  style="margin-bottom: 5px;"
				 onchange="setCompanyNameOfMp()"
					   name="WORD_NUM"  maxlength="8" minlength="2"  placeholder="请输入字号" value="${busRecord.WORD_NUM}"/>
				<input type="text" class="syj-input1 validate[required]" style="margin-bottom: 5px; width: 270px;" onclick="selectChildIndustryView()" onchange="setCompanyNameOfMp()"
					   name="CHILD_INDUSTRY_NAME" maxlength="64" placeholder="请选择子行业" value="${busRecord.CHILD_INDUSTRY_NAME}"/>
				<eve:eveselect clazz="input-dropdown w703 validate[required] " dataParams="OrgTypeOfHhMp"
							   dataInterface="dictionaryService.findDatasForSelect" onchange="setCompanyNameOfMp()"
							   defaultEmptyText="请选择组织形式" name="ORG_TYPE"  value="${busRecord.ORG_TYPE}">
				</eve:eveselect>
				<a onclick="checkCompanyName();" class="btn1" style="width: 111px;" id="checkCompanyNameId">检验</a>
				<input type="hidden" name="IS_CHECKCOMPANYNAME"  value="0"/>
				<input type="hidden" name="COMPANYNAME_PASS"  value="0"/>
			</td>
		</tr>

	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>注册地址：</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" onchange="setSendLawAddr()"  readonly="readonly" style="width: 264px;"
								   name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/>
				<!-- <br> -->
				<a href="javascript:selectRegisterAddr();" class="btn1" style="width: 101px;margin:5px 0 0 0;">选择注册地址</a>
			</td>
			<th><font class="syj-color2">*</font>面积：</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" maxlength="16"
					   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
				<font style="font-size: 16px;margin-left: 10px;">㎡</font>
			</td>
		</tr>
		<tr style="display: none;" >
			<th><font class="syj-color2">*</font>辖区工商局：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878"  readonly="readonly"
								   name="LOCAL_AREA" maxlength="117"  placeholder="根据注册地址的乡镇自动回填" value="${busRecord.LOCAL_AREA}"/>
				</td>
		</tr>

	<tr>
		<th> <font class="syj-color2">*</font>法律文书送达地址(同注册地址)：</td>
		<td >
			<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="1"  onclick="setSendLawAddr('1');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
			<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
		</td>
		<th><font class="syj-color2">*</font>法律文书送达地址：</th>
		<td><input  type="text" class="syj-input1 validate[required]"
							   name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/></td>
		</td>
	</tr>
	<tr>
		<th> <font class="syj-color2">*</font>法律文书电子送达地址：</td>
		<td colspan="3">
			<div class="eui-list">
				<div class="info eui-input w878">
					<p class="border">邮箱</p><input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
						  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
				</div>
			</div>
			<div class="eui-list">
				<div class="info eui-input w878">
					<p class="border">传真</p><input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
						  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
				</div>
			</div>
			<div class="eui-list">
				<div class="info eui-input w878">
					<p class="border">即时通讯账号（如微信）</p><input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
								   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
				</div>
			</div>
		</td>
	</tr>

	<script type="text/javascript">
		function setSendLawAddr() {
			var val = $("[name='IS_REGISTER_ADDR']:checked").val();

			if(val=='1'){
				$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR']").val());
				$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
			}else{
				$("input[name='LAW_SEND_ADDR']").removeAttr("disabled");
			}
		}
		$(function () {
			setSendLawAddr();
		})

		function setLocalArea(val) {
			$.post("jurisdictionController/getLocalArea.do?",{
				registerAddr:val
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success) {
					$("input[name='LOCAL_AREA']").val( resultJson.msg);
				}
			});
		}
	</script>


	<tr>
			<th> <font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="REGISTERADD_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.REGISTERADD_OWNER}"/></td>
			<th> <font class="syj-color2">*</font>房屋所有权人移动电话：</td>
			<td><input type="text" class="syj-input1 validate[required],custom[mobilePhone]"
				name="REGISTERADD_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.REGISTERADD_TEL}"/></td>

		</tr>

		<tr>
			<th><font class="syj-color2">*</font>取得方式：</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="wayOfGet"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyValue="02" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否属于军队房产：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="armyHourse" defaultEmptyValue="01"
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
			<th><font class="syj-color2">*</font>是否另设生产经营地址：</th>
			<td colspan="3">
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="1"  disabled
					<c:if test="${busRecord.IS_OTHER_PLACE==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="0"  disabled
					<c:if test="${busRecord.IS_OTHER_PLACE!=1}"> checked="checked"</c:if>/>否
				</td>
		</tr>
	</table>
		<table id="bussinessAddrTable" style="display: none;"  cellpadding="0" cellspacing="0" class="syj-table2 "  >
		<tr>
			<th> <font class="syj-color2 IS_BUSSINESS_ADDR_CLASS">*</font>生产经营地址：</th>
			<td><input type="text" class="syj-input1"
								   name="BUSINESS_PLACE" maxlength="128"  placeholder="请输入生产经营地址"  value="${busRecord.BUSINESS_PLACE}"/></td>
			<th> <font class="syj-color2 IS_BUSSINESS_ADDR_CLASS">*</font>面积：</th>
			<td>
				<input type="text"  maxlength="16" name="BUSSINESS_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.BUSSINESS_SQUARE_ADDR}"/>
				<font style="font-size: 16px;margin-left: 10px;">㎡</font>
			</td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input disabled="false" type="text" class="syj-input1 validate[required]" 
				name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
			<th> <font class="syj-color2">*</font>房屋所有权人移动电话：</td>
			<td><input disabled="false" type="text" class="syj-input1 custom[mobilePhone]"
				name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>
		</tr>
			<tr>
				<th><font class="syj-color2">*</font>取得方式：</th>
				<td colspan="3">
					<eve:eveselect clazz="input-dropdown w280" dataParams="wayOfGet"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择取得方式" name="RESIDENCE_WAYOFGET"  value="${busRecord.RESIDENCE_WAYOFGET}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否属于军队房产：</th>
				<td >
					<eve:eveselect clazz="input-dropdown w280" dataParams="armyHourse"
								   dataInterface="dictionaryService.findDatasForSelect" onchange="setRequired(this.value,'ARMYHOURSE_REMARKS','03')"
								   defaultEmptyText="请选择军队房产情况" name="ARMY_HOURSE"  value="${busRecord.ARMY_HOURSE}">
					</eve:eveselect>
				</td>
				<th>其他证明：</th>
				<td >
					<input type="text" class="syj-input1" disabled
						   name="ARMYHOURSE_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REMARKS}"/>
				</td>
			</tr>
		</table>
	<table  cellpadding="0" cellspacing="0" class="syj-table2 " style="table-layout: auto;">
		<tr>
			<th><font class="syj-color2">*</font>邮政编码：</th>
			<td colspan="3"><input disabled="false" type="text" class="syj-input1 validate[required],custom[chinaZip]"
				name="POST_CODE" value="350400" maxlength="6"   placeholder="请输入邮政编码"/></td>
		</tr>
		
		<tr>
			<th><font class="syj-color2">*</font>企业联系电话：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[mobilePhone]" maxlength="32"
				name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>出资额：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required],custom[JustNumber] w878 inputBackgroundCcc"
					   readonly="readonly"
					name="CAPITAL_CONTRIBUTION" maxlength="16"  placeholder="根据投资总额自动回填"  value="${busRecord.CAPITAL_CONTRIBUTION}"/>万元
			</td>
		</tr>
		

		<tr>
			<th><font class="syj-color2">*</font>合伙企业类型：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878 inputBackgroundCcc validate[required]" readonly="readonly"
					name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
				<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
				<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>	
				<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>				
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>合伙期限：</th>
			<td style="width: 370px;">
				<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
				<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
			</td>
			<th> 合伙期限年数：</td>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
		</tr>

		<tr>
			<th><font class="syj-color2">*</font>主营范围：</th>
			<input type="hidden" class="syj-input1"
				   name="MAIN_BUSSINESS_ID"   value="${busRecord.MAIN_BUSSINESS_ID}" readonly/>
			<td colspan="3"><input type="text" class="syj-input1"  placeholder="选择主营范围后自动回填" readonly="readonly" style="width:685px;"
								   name="MAIN_BUSSINESS_NAME" value="${busRecord.MAIN_BUSSINESS_NAME}"/>
				<a href="javascript:showSelectMainBussiness();" class="btn1" style="width: 128px;">选择主营范围</a>
			</td>
		</tr>

		<tr>
			<th><font class="syj-color2">*</font>行业信息：</th>
			<td colspan="3" >
				<input type="text" class="syj-input1 validate[required]" style="width:140px;"
									name="ML_NAME"  placeholder="门类名称"  maxlength="128"  value="${busRecord.ML_NAME}" readonly/>
				<input type="text" class="syj-input1 validate[required]" style="width:140px;"
					   name="DL_NAME"  placeholder="大类名称" value="${busRecord.DL_NAME}" readonly/>
				<input type="text" class="syj-input1 validate[required]" style="width:140px;"
					   name="ZL_NAME"  placeholder="中类名称"  value="${busRecord.ZL_NAME}" readonly/>
				<input type="text" class="syj-input1 validate[required]" style="width:140px;"
					   name="XL_NAME"  placeholder="小类名称"  value="${busRecord.XL_NAME}" readonly/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea w878 validate[required],maxSize[2000]" readonly="readonly"
					style="height:100px;"  placeholder="请选择经营范围"  onclick="showSelectJyfwOfMp();">${busRecord.BUSSINESS_SCOPE}</textarea>
				<input type="hidden" name="BUSSINESS_SCOPE_ID"  value="${busRecord.BUSSINESS_SCOPE_ID}">
				<input type="hidden" name="FLOW_DEFID"  value="${EFLOW_FLOWDEF.DEF_KEY }">
				<a href="javascript:showSelectJyfwOfMp();" class="btn1">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请
				<br/>㈢，第一项经营范围必须与名称中的行业相一致</div>
				<div style="margin-top: 5px;margin-bottom: 5px;">
				<input type="radio" name="NO_ACCESS_MANAGE" value="1" <c:if test="${busRecord.NO_ACCESS_MANAGE==1}"> checked="checked"</c:if>>经营范围不涉及国家规定实施的准入特别管理措施
				</div>
			</td>
		</tr>
	</table>
</form>
