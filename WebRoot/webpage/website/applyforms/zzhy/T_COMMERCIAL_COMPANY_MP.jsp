<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
	.width131{
		width: 131px;
	}
</style>
<!--秒批--->
	<form action="" id="COMPANY_FORM"  >
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="table-layout: auto;">
			<tr>
				<th><font class="syj-color2">*</font>企业名称：</th>
				<input type="hidden" class="syj-input1 validate[required]" 
					   name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称，请按所登记的名称进行申报" value="${busRecord.COMPANY_NAME}"/>
				<td colspan="3">
					<input type="hidden" name="MAIN_INDUSTRY_ID"  value="${busRecord.MAIN_INDUSTRY_ID}"/>
					<input type="hidden" id= "COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
					<input type="hidden" name= "CHILD_INDUSTRY_ID" value="${busRecord.CHILD_INDUSTRY_ID}"/>
					<input type="text" class="syj-input1 validate[required]" style="width:223px;"
						   name="AREA_NAME"  maxlength="64" placeholder="请输入行政区划" value="平潭综合实验区" readonly/>
					<input type="text" class="syj-input1  validate[required,maxSize[8],minSize[2]],custom[onlychinese],custom[verifyCompanyNameExist]" style="width:140px;" onchange="setCompanyNameOfMp()"
						   name="WORD_NUM"  maxlength="8" minlength="2"  placeholder="请输入字号" value="${busRecord.WORD_NUM}"/>
					<input type="text" class="syj-input1 validate[required]" style="width:140px;" onclick="selectChildIndustryView()" onchange="setCompanyNameOfMp()"
						   name="CHILD_INDUSTRY_NAME" maxlength="64" placeholder="请选择子行业" value="${busRecord.CHILD_INDUSTRY_NAME}"/>
					<eve:eveselect clazz="input-dropdown validate[required] " dataParams="OrgTypeOfMp"
								   dataInterface="dictionaryService.findDatasForSelect" onchange="setCompanyNameOfMp()"
								   defaultEmptyText="请选择组织形式" name="ORG_TYPE"  value="${busRecord.ORG_TYPE}">
					</eve:eveselect>
					<a onclick="checkCompanyName();" class="btn1" style="width: 111px; height: 40px;line-height: 40px;vertical-align: middle;" id="checkCompanyNameId">检验</a>
					<input type="hidden" name="IS_CHECKCOMPANYNAME"  value="0"/>
					<input type="hidden" name="COMPANYNAME_PASS"  value="0"/>
				</td>
			</tr>
			<tr>
				<th>集团名称：</th>
				<td>
					<input type="text" class="syj-input1 " disabled="disabled"
						   name="GROUP_NAME"  maxlength="256" placeholder="" value="${busRecord.GROUP_NAME}"/>
				</td>
				<th>集团简称：</th>
				<td>
					<input type="text" class="syj-input1 " disabled="disabled"
						   name="GROUP_ABBRE"  maxlength="128" placeholder="" value="${busRecord.GROUP_ABBRE}"/>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">

			<tr>
				<th><font class="syj-color2">*</font>注册地址：</th>
				<td><input type="text" class="syj-input1 validate[required]" onchange="setSendLawAddr()" readonly="readonly" style="width: 254px;"
									   name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址,请按房产证上的地址申报" value="${busRecord.REGISTER_ADDR}"/>
					<a href="javascript:selectRegisterAddr();" class="btn1" style="width: 101px;height: 40px;line-height: 40px;vertical-align: middle;">选择注册地址</a>
				</td>
				<th><font class="syj-color2">*</font>面积：</th>
				<td>
					<input type="text" class="syj-input1 validate[required]" maxlength="16"
						   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
			</tr>

			<tr style="display: none;">
				<th><font class="syj-color2">*</font>辖区工商局：</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]" readonly="readonly"
									   name="LOCAL_AREA" maxlength="117"  placeholder="根据注册地址的乡镇自动回填" value="${busRecord.LOCAL_AREA}"/></td>
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
					<div class="eui-list" style="width: 830px;">
						<div class="info eui-input">
							<p class="border">邮箱</p><input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
								  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
						</div>
					</div>
					<div class="eui-list" style="width: 830px;">
						<div class="info eui-input">
							<p class="border">传真</p><input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
								  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
						</div>
					</div>
					<div class="eui-list" style="width: 830px;">
						<div class="info eui-input">
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
				<th><font class="syj-color2">*</font>房屋所有权人名称：</td>
				<td><input  type="text" class="syj-input1  validate[required]"
							name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
				<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
				<td><input type="text" class="syj-input1 validate[required,custom[mobilePhone]]"
						   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>

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
					<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="armyHourse" defaultEmptyValue="01"
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
				<th ><font class="syj-color2">*</font>是否有生产经营地址：</th>
				<td>
					<input type="radio" name="IS_BUSSINESS_ADDR" value="1"  <c:if test="${busRecord.IS_BUSSINESS_ADDR==1}"> checked="checked"</c:if> disabled>是
					<input type="radio" name="IS_BUSSINESS_ADDR" value="0" checked="checked" disabled>否
				</td>
			</tr>
		</table>
		<table cellpadding="0" id="bussinessAddrTable" cellspacing="0" class="syj-table2 " style="display: none;">
			<tr>
				<th> <font class="syj-color2 IS_BUSSINESS_ADDR_CLASS"
						>*</font>生产经营地址：</th>
				<td><input type="text" class="syj-input1 " name="BUSSINESS_ADDR" maxlength="128"  placeholder="请输入生产经营地址"  value="${busRecord.BUSSINESS_ADDR}"/></td>
				<th><font class="syj-color2 IS_BUSSINESS_ADDR_CLASS">*</font>面积：</th>
				<td> 
					<input type="text"  maxlength="16"
						   name="BUSSINESS_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.BUSSINESS_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>房屋所有权人名称：</td>
				<td><input  type="text" class="syj-input1  "
							name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
				<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
				<td><input type="text" class="syj-input1 validate[custom[mobilePhone]]"
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
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>企业联系电话：</th>
				<td><input type="text" class="syj-input1 validate[required,custom[mobilePhone]]" maxlength="32"
						   name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
				<th><font class="syj-color2">*</font>邮政编码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
						   name="POST_CODE" value="350400"  placeholder="请输入邮政编码"/></td>

			</tr>
			<tr>
				<th><font class="syj-color2">*</font>公司类型：</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
						   name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
					<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
					<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>
					<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>
				</td>
				<th><font class="syj-color2">*</font>企业规模：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjqyfl"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择企业规模" name="ENTERPRISE_STATEMENT"  value="${busRecord.ENTERPRISE_STATEMENT}">
					</eve:eveselect>
					&nbsp;<a target="_blank" class="answer" href="<%=path%>/contentController/view.do?contentId=996" title="企业规模帮助"></a>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>经营期限：</th>
				<td>
					<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
					<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
				</td>
				<th> 经营期限年数：</td>
				<td><input type="text" class="syj-input1 validate[required],custom[numberplus]"
						   name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>主营范围：</th>
				<input type="hidden" class="syj-input1"
					   name="MAIN_BUSSINESS_ID"   value="${busRecord.MAIN_BUSSINESS_ID}" readonly/>
				<td colspan="3"><input type="text" class="syj-input1 "  placeholder="选择主营范围后自动回填" readonly="readonly" style="width: 686px;"
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
						  class="eve-textarea validate[required],maxSize[1000] w878" readonly="readonly"
						  style="height:100px;"  placeholder="请选择经营范围"  onclick="showSelectJyfwOfMp();">${busRecord.BUSSINESS_SCOPE}</textarea>
					<input type="hidden" name="BUSSINESS_SCOPE_ID"  value="${busRecord.BUSSINESS_SCOPE_ID}">
					<input type="hidden" name="FLOW_DEFID"  value="${EFLOW_FLOWDEF.DEF_KEY }">
					<a href="javascript:showSelectJyfwOfMp();" class="btn1">选 择</a>
					<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
						<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
						中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
						可以参照政策文件、行业习惯或者专业文献等提出申请</div>
				</td>
			</tr>
			<tr>
				<th> 投资行业：</th>
				<td colspan="3">
				<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
						  class="eve-textarea validate[ validate[],maxSize[2000]] w878"
						  style="height:100px;"  placeholder="选择经营范围后投资行业自动回填"  >${busRecord.INVEST_INDUSTRY}</textarea>
					<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID}">
					<!--<a href="javascript:showSelectTzhy();" class="btn1">选 择</a>-->
				</td>
			</tr>
			<tr>
				<th>行业编码：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 inputBackgroundCcc w878"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
									   name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE}"/></td>
			</tr>
			<!--<tr>
			<th> 经营范围备注描述：</th>
			<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入经营范围备注描述" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr>-->
			<tr>
				<th><font class="syj-color2">*</font>职工人数/从业人数：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[numberplus]"
						   name="STAFF_NUM"  placeholder="请输入职工人数/从业人数"  maxlength="8" value="${busRecord.STAFF_NUM}"/></td>
				<th> 所在地行政区：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" name="LOCAL_REGION" value="福州市平潭县"   placeholder="请输入所在地行政区"
						   readonly="readonly"/></td>
			</tr>
		</table>
	</form>